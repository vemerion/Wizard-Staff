package mod.vemerion.wizardstaff.Magic.original;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ElytraMagic extends Magic {
	
	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.SPEAR;
	}
	
	@Override
	public void magicTick(World world, PlayerEntity player, ItemStack staff, int count) {
		if (count % 5 == 0)
			player.playSound(Main.WOOSH_SOUND, 1, soundPitch(player));
		if (!world.isRemote) {
			cost(player);
			Vec3d motion = player.getMotion();
			player.fallDistance = 0;
			Vec3d direction = Vec3d.fromPitchYaw(player.getPitchYaw()).scale(0.3);
			double x = motion.getX() < 3 ? direction.getX() : 0;
			double y = motion.getY() < 3 ? direction.getY() + 0.1 : 0;
			double z = motion.getZ() < 3 ? direction.getZ() : 0;
			player.addVelocity(x, y, z);
			player.velocityChanged = true;
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
