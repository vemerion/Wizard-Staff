package mod.vemerion.wizardstaff.Magic;

import mod.vemerion.wizardstaff.Helper.Helper;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;

public abstract class BlockRayMagic extends Magic {

	public BlockRayMagic(MagicType<? extends BlockRayMagic> type) {
		super(type);
	}
	@Override
	public UseAnim getUseAnim(ItemStack stack) {
		return UseAnim.BLOCK;
	}
	
	@Override
	public boolean magicPreventOtherUse(Level level, Player player, ItemStack staff) {
		var result = Helper.blockRay(level, player, 4.5f);
		return result.getType() == Type.BLOCK;
	}

	@Override
	public ItemStack magicFinish(Level level, Player player, ItemStack staff) {
		if (!level.isClientSide) {
			var result = Helper.blockRay(level, player, 4.5f);
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
