package mod.vemerion.wizardstaff.Magic.original;

import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.Magic.BlockRayMagic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.MagicUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

public class TransformBlockMagic extends BlockRayMagic {

	private Block from;
	private Block to;

	public TransformBlockMagic(MagicType<? extends TransformBlockMagic> type) {
		super(type);
	}

	public TransformBlockMagic setAdditionalParams(Block from, Block to) {
		this.from = from;
		this.to = to;
		return this;
	}

	@Override
	protected void decodeAdditional(PacketBuffer buffer) {
		from = MagicUtil.decode(buffer, ForgeRegistries.BLOCKS);
		to = MagicUtil.decode(buffer, ForgeRegistries.BLOCKS);
	}

	@Override
	protected void encodeAdditional(PacketBuffer buffer) {
		MagicUtil.encode(buffer, from);
		MagicUtil.encode(buffer, to);
	}

	@Override
	protected void readAdditional(JsonObject json) {
		from = MagicUtil.read(json, ForgeRegistries.BLOCKS, "from");
		to = MagicUtil.read(json, ForgeRegistries.BLOCKS, "to");
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		MagicUtil.write(json, from, "from");
		MagicUtil.write(json, to, "to");
	}

	@Override
	protected Object[] getNameArgs() {
		return new Object[] { from.getTranslatedName(), to.getTranslatedName() };
	}

	@Override
	protected Object[] getDescrArgs() {
		return new Object[] { from.getTranslatedName(), to.getTranslatedName() };
	}

	@Override
	protected void hitBlock(World world, PlayerEntity player, BlockPos pos) {
		if (world.getBlockState(pos).getBlock() == from) {
			world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_STONE_BREAK,
					SoundCategory.PLAYERS, 1.5f, soundPitch(player));
			cost(player);
			world.setBlockState(pos, to.getDefaultState());
		}
	}

}
