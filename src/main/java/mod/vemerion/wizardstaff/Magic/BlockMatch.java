package mod.vemerion.wizardstaff.Magic;

import java.util.function.Predicate;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import net.minecraft.block.Block;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockMatch implements Predicate<Block> {

	private Block block;
	private ResourceLocation name;

	public BlockMatch(Block block) {
		Preconditions.checkNotNull(block, "Block for BlockMatch cannot be null");
		this.block = block;
	}

	public BlockMatch(ResourceLocation tagName) {
		Preconditions.checkNotNull(tagName, "tagName for BlockMatch cannot be null");
		this.name = tagName;
	}

	public BlockMatch(ITag.INamedTag<Block> tag) {
		Preconditions.checkNotNull(tag, "tag for BlockMatch cannot be null");
		this.name = tag.getName();
	}

	@Override
	public boolean test(Block b) {
		if (block != null)
			return block == b;
		else
			return BlockTags.getCollection().getTagByID(name).contains(b);
	}

	public ITextComponent getName() {
		if (block != null)
			return block.getTranslatedName();
		else
			return new StringTextComponent(name.getPath());
	}

	public static BlockMatch read(JsonObject json) {
		if (JSONUtils.hasField(json, "block"))
			return new BlockMatch(MagicUtil.read(json, ForgeRegistries.BLOCKS, "block"));
		else if (JSONUtils.hasField(json, "tag"))
			return new BlockMatch(new ResourceLocation(JSONUtils.getString(json, "tag")));
		else
			throw new JsonSyntaxException("BlockMatch must have either block or tag member");
	}

	public JsonObject write() {
		JsonObject json = new JsonObject();
		if (block != null)
			MagicUtil.write(json, block, "block");
		else
			json.addProperty("tag", name.toString());

		return json;
	}

	public static BlockMatch decode(PacketBuffer buffer) {
		if (buffer.readBoolean())
			return new BlockMatch(MagicUtil.decode(buffer, ForgeRegistries.BLOCKS));
		else
			return new BlockMatch(buffer.readResourceLocation());
	}

	public void encode(PacketBuffer buffer) {
		if (block != null) {
			buffer.writeBoolean(true);
			MagicUtil.encode(buffer, block);
		} else {
			buffer.writeBoolean(false);
			buffer.writeResourceLocation(name);
		}
	}

}
