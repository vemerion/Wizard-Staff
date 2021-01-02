package mod.vemerion.wizardstaff.Magic.suggestions;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.entity.MushroomCloudEntity;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class MushroomCloudMagic extends Magic {

	public MushroomCloudMagic(String name) {
		super(name);
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::forwardWaving;
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
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		if (!world.isRemote) {
			MushroomCloudEntity cloud = new MushroomCloudEntity(world, player);
			Vector3d pos = player.getPositionVec().add(Vector3d.fromPitchYaw(player.getPitchYaw()).scale(3));
			cloud.setPosition(pos.x, pos.y, pos.z);
			world.addEntity(cloud);
			cost(player);
		}
		player.playSound(Main.SPRAY_SOUND, 1, soundPitch(player));
		return super.magicFinish(world, player, staff);
	}

}
