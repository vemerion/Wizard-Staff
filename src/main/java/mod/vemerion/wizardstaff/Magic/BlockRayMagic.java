package mod.vemerion.wizardstaff.Magic;

import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public abstract class BlockRayMagic extends Magic {

	public BlockRayMagic(MagicType<? extends BlockRayMagic> type) {
		super(type);
	}
	@Override
	public UseAnim getUseAnim(ItemStack stack) {
		return UseAnim.BLOCK;
	}

	@Override
	public ItemStack magicFinish(Level level, Player player, ItemStack staff) {
		if (!level.isClientSide) {
			Vec3 direction = Vec3.directionFromRotation(player.getRotationVector());
			Vec3 start = player.position().add(0, 1.5, 0).add(direction.scale(0.2));
			Vec3 stop = start.add(direction.scale(4.5));
			BlockHitResult result = level
					.clip(new ClipContext(start, stop, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
			if (result.getType() == HitResult.Type.BLOCK) {
				hitBlock(level, player, result.getBlockPos());
			}
		}
		return super.magicFinish(level, player, staff);
	}
	
	protected abstract void hitBlock(Level level, Player player, BlockPos pos);

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::forwardBuildup;
	}

	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::forwardShake;
	}

}
