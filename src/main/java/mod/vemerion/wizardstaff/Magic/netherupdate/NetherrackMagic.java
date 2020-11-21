package mod.vemerion.wizardstaff.Magic.netherupdate;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.World;

public class NetherrackMagic extends Magic {

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::swinging;
	}

	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::swinging;
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
	}

	@Override
	public void magicTick(World world, PlayerEntity player, ItemStack staff, int count) {
		if (!world.isRemote && player.ticksExisted % 10 == 0) {
			if (rackify(world, player)) {
				cost(player);
				playSoundServer(world, player, SoundEvents.BLOCK_STONE_PLACE, 1, soundPitch(player));
			}
		}
	}

	// Derived from FrostWalkerEnchantment.freezeNearby()
	private boolean rackify(World world, PlayerEntity player) {
		boolean hasRackified = false;
		if (player.onGround) {
			BlockState netherrack = Blocks.NETHERRACK.getDefaultState();
			float range = 2;
			BlockPos start = player.getPosition().add(-range, -1, -range);
			BlockPos stop = player.getPosition().add(range, -1, range);
			for (BlockPos pos : BlockPos.getAllInBoxMutable(start, stop)) {
				if (canBeRackified(world, pos)) {
					if (world.func_226663_a_(netherrack, pos, ISelectionContext.dummy())
							&& !net.minecraftforge.event.ForgeEventFactory.onBlockPlace(player,
									new net.minecraftforge.common.util.BlockSnapshot(world, pos,
											world.getBlockState(pos)),
									net.minecraft.util.Direction.UP)) {
						world.setBlockState(pos, netherrack);
						hasRackified = true;
					}
				}
			}
		}
		return hasRackified;
	}

	private boolean canBeRackified(World world, BlockPos pos) {
		IFluidState fluidState = world.getFluidState(pos);
		return world.isAirBlock(pos.up()) && fluidState.isTagged(FluidTags.LAVA) && fluidState.isSource();
	}

}
