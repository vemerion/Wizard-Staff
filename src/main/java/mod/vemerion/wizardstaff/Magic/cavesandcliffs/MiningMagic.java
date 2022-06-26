package mod.vemerion.wizardstaff.Magic.cavesandcliffs;

import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.MassHandleBlockMagic;
import mod.vemerion.wizardstaff.staff.WizardStaffItemHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.items.ItemHandlerHelper;

public class MiningMagic extends MassHandleBlockMagic {

	public MiningMagic(MagicType<? extends MiningMagic> type) {
		super(type);
	}
	
	@Override
	protected void handleBlock(Level level, BlockPos pos, Player player, ItemStack staff) {
		WizardStaffItemHandler.getOptional(staff).ifPresent(h -> {
			for (var item : Block.getDrops(level.getBlockState(pos), (ServerLevel) level, pos,
					level.getBlockEntity(pos), player, h.getCurrent())) {
				ItemHandlerHelper.giveItemToPlayer(player, item);
			}
		});
		FluidState fluidstate = level.getFluidState(pos);
		level.setBlockAndUpdate(pos, fluidstate.createLegacyBlock());
	}

	@Override
	protected Vec3i searchOffset() {
		return new Vec3i(2, 2, 2);
	}
}
