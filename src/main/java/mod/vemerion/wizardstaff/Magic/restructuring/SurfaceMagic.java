package mod.vemerion.wizardstaff.Magic.restructuring;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.capability.Wizard;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class SurfaceMagic extends Magic {

	public SurfaceMagic(MagicType<? extends SurfaceMagic> type) {
		super(type);
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::buildupMagic;
	}

	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::spinMagic;
	}

	@Override
	public UseAnim getUseAnim(ItemStack stack) {
		return UseAnim.NONE;
	}
	
	@Override
	public InteractionResult magicInteractBlock(UseOnContext context) {
		Player player = context.getPlayer();
		if (!context.getLevel().isClientSide)
			Wizard.getWizardOptional(player).ifPresent(w -> w.setSurfacePos(context.getClickedPos()));
		player.playSound(SoundEvents.STONE_PLACE, 1, soundPitch(player));
		return InteractionResult.SUCCESS;
	}
	
	@Override
	public ItemStack magicFinish(Level level, Player player, ItemStack staff) {
		if (!level.isClientSide)
			Wizard.getWizardOptional(player).ifPresent(w -> {
				int count = w.createSurface(level, player);
				if (count > 0)
					cost(player, count);
				else
					playSoundServer(level, player, ModSounds.POOF, 1, soundPitch(player));
			});
		return super.magicFinish(level, player, staff);
	}

}
