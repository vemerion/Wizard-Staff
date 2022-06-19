package mod.vemerion.wizardstaff.Magic.original;

import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.Magic.BlockRayMagic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.MagicUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
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
	protected void decodeAdditional(FriendlyByteBuf buffer) {
		from = MagicUtil.decode(buffer);
		to = MagicUtil.decode(buffer);
	}

	@Override
	protected void encodeAdditional(FriendlyByteBuf buffer) {
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
		return new Object[] { from.getName(), to.getName() };
	}

	@Override
	protected Object[] getDescrArgs() {
		return new Object[] { from.getName(), to.getName() };
	}

	@Override
	protected void hitBlock(Level level, Player player, BlockPos pos) {
		if (level.getBlockState(pos).getBlock() == from) {
			level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.STONE_BREAK,
					SoundSource.PLAYERS, 1.5f, soundPitch(player));
			cost(player);
			level.setBlockAndUpdate(pos, to.defaultBlockState());
		}
	}

}
