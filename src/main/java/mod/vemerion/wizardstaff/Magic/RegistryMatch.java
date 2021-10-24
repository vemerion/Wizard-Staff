package mod.vemerion.wizardstaff.Magic;

import java.util.function.Predicate;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITagCollection;
import net.minecraft.tags.TagRegistryManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryManager;

public class RegistryMatch<T extends IForgeRegistryEntry<T>> implements Predicate<T> {

	private IForgeRegistry<T> registry;
	private T entry;
	private ResourceLocation tag;

	public RegistryMatch(T entry) {
		this.registry = RegistryManager.ACTIVE.getRegistry(entry.getRegistryType());
		this.entry = entry;
	}

	public RegistryMatch(IForgeRegistry<T> registry, ResourceLocation tag) {
		this.registry = registry;
		this.tag = tag;
	}

	public RegistryMatch(IForgeRegistry<T> registry, ITag.INamedTag<T> tag) {
		this.registry = registry;
		this.tag = tag.getName();
	}

	// TODO: Might be a better way to do this in the future
	@SuppressWarnings("unchecked")
	@Override
	public boolean test(T b) {
		if (entry != null)
			return entry == b;
		else
			return ((ITagCollection<T>) TagRegistryManager.get(registry.getRegistryName()).getCollection())
					.getTagByID(tag).contains(b);
	}

	public ITextComponent getName() {
		if (entry != null)
			return getEntryName(entry);
		else
			return new StringTextComponent(tag.getPath());
	}

	// TODO: Might be a better way to do this in the future
	// Hard to do in a generic way
	private ITextComponent getEntryName(T entry) {
		if (entry instanceof Block)
			return ((Block) entry).getTranslatedName();
		else if (entry instanceof EntityType<?>)
			return ((EntityType<?>) entry).getName();
		return new StringTextComponent(entry.getRegistryName().getPath());
	}

	public static <T extends IForgeRegistryEntry<T>> RegistryMatch<T> read(IForgeRegistry<T> registry,
			JsonObject json) {
		String field = registry.getRegistryName().getPath();
		if (JSONUtils.hasField(json, field))
			return new RegistryMatch<T>(MagicUtil.read(json, registry, field));
		else if (JSONUtils.hasField(json, "tag"))
			return new RegistryMatch<T>(registry, new ResourceLocation(JSONUtils.getString(json, "tag")));
		else
			throw new JsonSyntaxException("BlockMatch must have either block or tag member");
	}

	public JsonObject write() {
		String field = registry.getRegistryName().getPath();
		JsonObject json = new JsonObject();
		if (entry != null)
			MagicUtil.write(json, entry, field);
		else
			json.addProperty("tag", tag.toString());

		return json;
	}

	public static <T extends IForgeRegistryEntry<T>> RegistryMatch<T> decode(IForgeRegistry<T> registry,
			PacketBuffer buffer) {
		if (buffer.readBoolean())
			return new RegistryMatch<T>(MagicUtil.decode(buffer));
		else
			return new RegistryMatch<T>(registry, buffer.readResourceLocation());
	}

	public void encode(PacketBuffer buffer) {
		if (entry != null) {
			buffer.writeBoolean(true);
			MagicUtil.encode(buffer, entry);
		} else {
			buffer.writeBoolean(false);
			buffer.writeResourceLocation(tag);
		}
	}
}
