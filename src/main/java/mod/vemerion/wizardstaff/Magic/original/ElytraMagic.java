package mod.vemerion.wizardstaff.Magic.original;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderMagic;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class ElytraMagic extends Magic {

	@Override
	public int getUseDuration(ItemStack staff) {
		return HOUR;
	}

	@Override
	public boolean isMagicItem(Item item) {
		return item == Items.ELYTRA;
	}
	
	@Override
	public void magicTick(World world, PlayerEntity player, ItemStack staff, int count) {
		if (count % 5 == 0)
			player.playSound(Main.WOOSH_SOUND, 1, soundPitch(player));
		if (!world.isRemote) {
			cost(player, 1);
			Vector3d motion = player.getMotion();
			player.fallDistance = 0;
			Vector3d direction = Vector3d.fromPitchYaw(player.getPitchYaw()).scale(0.3);
			double x = motion.getX() < 3 ? direction.getX() : 0;
			double y = motion.getY() < 3 ? direction.getY() + 0.1 : 0;
			double z = motion.getZ() < 3 ? direction.getZ() : 0;
			player.addVelocity(x, y, z);
			player.velocityChanged = true;
		}
	}
	
	@Override
	public RenderMagic renderer() {
		return WizardStaffTileEntityRenderer::helicopter;
	}

}
