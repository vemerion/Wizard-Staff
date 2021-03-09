package mod.vemerion.wizardstaff.Magic.original;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.entity.PumpkinMagicEntity;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.world.World;

public class CarvedPumpkinMagic extends Magic {
	
	public CarvedPumpkinMagic(String name) {
		super(name);
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BLOCK;
	}

	@Override
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		player.playSound(ModSounds.PUMPKIN_MAGIC_SOUND, 0.2f, soundPitch(player));
		if (!world.isRemote) {
			cost(player);
			PumpkinMagicEntity entity = new PumpkinMagicEntity(Main.PUMPKIN_MAGIC_ENTITY, world, player);
			entity.setPositionAndRotation(player.getPosX(), player.getPosY() + 2, player.getPosZ(), player.rotationYaw,
					0);
			world.addEntity(entity);
		}
		return super.magicFinish(world, player, staff);
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::forward;
	}
	
	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::forwardShake;
	}

}
