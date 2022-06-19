package mod.vemerion.wizardstaff.Magic;

import java.util.function.Predicate;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RegistryMatch<T extends IForgeRegistryEntry<T>> implements Predicate<T> {

	private IForgeRegistry<T> registry;
	private T entry;
	private TagKey<T> tag;

	public RegistryMatch(IForgeRegistry<T> registry, T entry) {
		this.registry = registry;
		this.entry = entry;
	}

	public RegistryMatch(IForgeRegistry<T> registry, TagKey<T> tag) {
		this.registry = registry;
		this.tag = tag;
	}

	@Override
	public boolean test(T b) {
		if (entry != null)
			return entry == b;
		else
			return registry.tags().getTag(tag).contains(b);
	}

	public Component getName() {
		if (entry != null)
			return getEntryName(entry);
		else
			return new TextComponent(tag.location().getPath());
	}

	// TODO: Might be a better way to do this in the future
	// Hard to do in a generic way
	private Component getEntryName(T entry) {
		if (entry instanceof Block block)
			return block.getName();
		else if (entry instanceof EntityType<?> entityType)
			return entityType.getDescription();
		return new TextComponent(entry.getRegistryName().getPath());
	}

	public static <T extends IForgeRegistryEntry<T>> RegistryMatch<T> read(IForgeRegistry<T> registry,
			JsonObject json) {
		String field = registry.getRegistryName().getPath();
		if (GsonHelper.isValidNode(json, field))
			return new RegistryMatch<T>(registry, MagicUtil.read(json, registry, field));
		else if (GsonHelper.isValidNode(json, "tag"))
			return new RegistryMatch<T>(registry,
					registry.tags().createTagKey(new ResourceLocation(GsonHelper.getAsString(json, "tag"))));
		else
			throw new JsonSyntaxException("BlockMatch must have either block or tag member");
	}

	public JsonObject write() {
		String field = registry.getRegistryName().getPath();
		JsonObject json = new JsonObject();
		if (entry != null)
			MagicUtil.write(json, entry, field);
		else
			json.addProperty("tag", tag.location().toString());

		return json;
	}

	public static <T extends IForgeRegistryEntry<T>> RegistryMatch<T> decode(IForgeRegistry<T> registry,
			FriendlyByteBuf buffer) {
		if (buffer.readBoolean())
			return new RegistryMatch<T>(registry, MagicUtil.<T>decode(buffer));
		else
			return new RegistryMatch<T>(registry, registry.tags().createTagKey(buffer.readResourceLocation()));
	}

	public void encode(FriendlyByteBuf buffer) {
		if (entry != null) {
			buffer.writeBoolean(true);
			MagicUtil.encode(buffer, entry);
		} else {
			buffer.writeBoolean(false);
			buffer.writeResourceLocation(tag.location());
		}
	}
}
