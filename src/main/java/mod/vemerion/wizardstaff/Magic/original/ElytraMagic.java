package mod.vemerion.wizardstaff.Magic.original;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ElytraMagic extends Magic {
	
	public ElytraMagic(MagicType<? extends ElytraMagic> type) {
		super(type);
	}

	@Override
	public UseAnim getUseAnim(ItemStack stack) {
		return UseAnim.SPEAR;
	}
	
	@Override
	public void magicTick(Level level, Player player, ItemStack staff, int count) {
		if (count % 5 == 0)
			player.playSound(ModSounds.WOOSH, 1, soundPitch(player));
		if (!level.isClientSide) {
			Vec3 motion = player.getDeltaMovement();
			cost(player);
			player.fallDistance = 0;
			Vec3 direction = Vec3.directionFromRotation(player.getRotationVector()).scale(0.3);
			double x = motion.x() < 3 ? direction.x() : 0;
			double y = motion.y() < 3 ? direction.y() + 0.1 : 0;
			double z = motion.z() < 3 ? direction.z() : 0;
			player.push(x, y, z);
			player.hurtMarked = true;
		}
	}
	
	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::helicopter;
	}
	
	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::helicopter;
	}

}
