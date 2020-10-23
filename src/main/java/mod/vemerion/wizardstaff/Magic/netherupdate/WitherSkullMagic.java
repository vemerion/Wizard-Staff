package mod.vemerion.wizardstaff.Magic.netherupdate;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.entity.MagicWitherSkullEntity;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class WitherSkullMagic extends Magic {

	@Override
	public int getUseDuration(ItemStack staff) {
		return 15;
	}

	@Override
	public boolean isMagicItem(Item item) {
		return item == Items.WITHER_SKELETON_SKULL;
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::forwardBuildup;
	}
	
	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::forwardShake;
	}
	
	@Override
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		player.playSound(Main.SKELETON_SOUND, 0.85f, soundPitch(player));
		if (!world.isRemote) {
			cost(player, 20);			
			Vec3d direction = Vec3d.fromPitchYaw(player.getPitchYaw());
			Vec3d position = player.getPositionVec().add(direction.getX() * 1, 1.2, direction.getZ() * 1);
			MagicWitherSkullEntity skull = new MagicWitherSkullEntity(position.getX(), position.getY(),
					position.getZ(), world);
			skull.setShooter(player);
			skull.shoot(player, player.rotationPitch, player.rotationYaw, 0, 0.5f, 0);
			world.addEntity(skull);
		}
		
		return super.magicFinish(world, player, staff);
	}

}
