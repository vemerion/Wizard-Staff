package mod.vemerion.wizardstaff.Magic.suggestions;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.capability.Wizard;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class GrapplingHookMagic extends Magic {

	public GrapplingHookMagic(MagicType<? extends GrapplingHookMagic> type) {
		super(type);
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::forward;
	}

	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::forwardShake;
	}

	@Override
	public UseAnim getUseAnim(ItemStack stack) {
		return UseAnim.BLOCK;
	}

	@Override
	public void magicStart(Level level, Player player, ItemStack staff) {
		Wizard.getWizardOptional(player).ifPresent(wizard -> {
			if (wizard.throwGrapplingHook(level, player)) {
				if (!level.isClientSide) {
					cost(player);
					playSoundServer(level, player, SoundEvents.FISHING_BOBBER_THROW, 1, soundPitch(player));
				}
			}
		});
	}

	@Override
	public void magicCancel(Level level, Player player, ItemStack staff, int timeLeft) {
		Wizard.getWizardOptional(player).ifPresent(wizard -> {
			wizard.reelGrapplingHook(level, player);
		});
	}

}
