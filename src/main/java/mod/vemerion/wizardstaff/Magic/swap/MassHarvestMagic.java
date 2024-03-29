package mod.vemerion.wizardstaff.Magic.swap;

import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.MassHandleBlockMagic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class MassHarvestMagic extends MassHandleBlockMagic {

	public MassHarvestMagic(MagicType<? extends MassHarvestMagic> type) {
		super(type);
	}

	protected void handleBlock(Level level, BlockPos pos, Player player, ItemStack staff) {
		FluidState fluidstate = level.getFluidState(pos);
		BlockState state = level.getBlockState(pos);
		BlockEntity tileentity = state.hasBlockEntity() ? level.getBlockEntity(pos) : null;
		Block.dropResources(state, level, pos, tileentity, null, ItemStack.EMPTY);
		level.setBlockAndUpdate(pos, fluidstate.createLegacyBlock());
	}

	protected Vec3i searchOffset() {
		return new Vec3i(1, 1, 1);
	}

}
