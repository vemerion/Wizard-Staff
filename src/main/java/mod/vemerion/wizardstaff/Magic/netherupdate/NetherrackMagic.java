package mod.vemerion.wizardstaff.Magic.netherupdate;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;

public class NetherrackMagic extends Magic {

	public NetherrackMagic(MagicType<? extends NetherrackMagic> type) {
		super(type);
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::swinging;
	}

	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::swinging;
	}

	@Override
	public UseAnim getUseAnim(ItemStack stack) {
		return UseAnim.NONE;
	}

	@Override
	public void magicTick(Level level, Player player, ItemStack staff, int count) {
		if (!level.isClientSide && player.tickCount % 10 == 0) {
			if (rackify(level, player)) {
				cost(player);
				playSoundServer(level, player, SoundEvents.STONE_PLACE, 1, soundPitch(player));
			}
		}
	}

	// Derived from FrostWalkerEnchantment.freezeNearby()
	private boolean rackify(Level level, Player player) {
		boolean hasRackified = false;
		if (player.isOnGround()) {
			BlockState netherrack = Blocks.NETHERRACK.defaultBlockState();
			float range = 2;
			BlockPos start = new BlockPos(player.position()).offset(-range, -1, -range);
			BlockPos stop = new BlockPos(player.position()).offset(range, -1, range);
			for (BlockPos pos : BlockPos.betweenClosed(start, stop)) {
				if (canBeRackified(level, pos)) {
					if (level.isUnobstructed(netherrack, pos, CollisionContext.empty())
							&& !net.minecraftforge.event.ForgeEventFactory.onBlockPlace(player,
									net.minecraftforge.common.util.BlockSnapshot.create(level.dimension(), level, pos),
									Direction.UP)) {
						level.setBlockAndUpdate(pos, netherrack);
						hasRackified = true;
					}
				}
			}
		}
		return hasRackified;
	}

	private boolean canBeRackified(Level level, BlockPos pos) {
		FluidState fluidState = level.getFluidState(pos);
		return level.isEmptyBlock(pos.above()) && fluidState.is(FluidTags.LAVA) && fluidState.isSource();
	}

}
