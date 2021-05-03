package mod.vemerion.wizardstaff.Magic.suggestions;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.capability.Wizard;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

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
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BLOCK;
	}

	@Override
	public void magicStart(World world, PlayerEntity player, ItemStack staff) {
		Wizard.getWizardOptional(player).ifPresent(wizard -> {
			if (wizard.throwGrapplingHook(world, player)) {
				if (!world.isRemote) {
					cost(player);
					playSoundServer(world, player, SoundEvents.ENTITY_FISHING_BOBBER_THROW, 1, soundPitch(player));
				}
			}
		});
	}

	@Override
	public void magicCancel(World world, PlayerEntity player, ItemStack staff, int timeLeft) {
		Wizard.getWizardOptional(player).ifPresent(wizard -> {
			wizard.reelGrapplingHook(world, player);
		});
	}

}
