package mod.vemerion.wizardstaff.Magic.suggestions2;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.capability.Wizard;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class RevertPositionMagic extends Magic {

	public RevertPositionMagic(MagicType<? extends RevertPositionMagic> type) {
		super(type);
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::buildupMagic;
	}

	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::buildupMagic;
	}

	@Override
	public UseAnim getUseAnim(ItemStack stack) {
		return UseAnim.NONE;
	}

	@Override
	public ItemStack magicFinish(Level level, Player player, ItemStack staff) {
		if (!level.isClientSide) {
			Wizard.getWizardOptional(player).ifPresent(w -> {
				BlockPos revert = w.revertPosition(player);
				if (revert != null) {
					player.fallDistance = 0;
					playSoundServer(level, player, ModSounds.REVERT, 1, soundPitch(player));
					cost(player);
					((ServerPlayer) player).teleportTo((ServerLevel) level, revert.getX(), revert.getY(),
							revert.getZ(), player.getYRot(), player.getXRot());
				} else {
					playSoundServer(level, player, ModSounds.POOF, 1, soundPitch(player));
				}
			});
		}

		return super.magicFinish(level, player, staff);
	}

}
