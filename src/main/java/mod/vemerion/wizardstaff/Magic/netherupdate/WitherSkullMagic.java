package mod.vemerion.wizardstaff.Magic.netherupdate;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.entity.MagicWitherSkullEntity;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderMagic;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class WitherSkullMagic extends Magic {

	@Override
	public int getUseDuration(ItemStack staff) {
		return 30;
	}

	@Override
	public boolean isMagicItem(Item item) {
		return item == Items.WITHER_SKELETON_SKULL;
	}

	@Override
	public RenderMagic renderer() {
		return WizardStaffTileEntityRenderer::forwardBuildup;
	}
	
	@Override
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		if (!world.isRemote) {
			cost(player, 100);			
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
