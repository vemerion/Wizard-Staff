package mod.vemerion.wizardstaff.Magic.suggestions;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class BlueDyeMagic extends Magic {

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::spinMagic;
	}

	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::spinMagic;
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
	}

	@Override
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		if (!world.isRemote) {
			cost(player);
			boolean isRaining = world.isRaining();
			int clearWeatherTime = isRaining ? 6000 : 0;
			int rainTime = isRaining ? 0 : 6000;
			((ServerWorld) world).func_241113_a_(clearWeatherTime, rainTime, !isRaining, !isRaining && player.getRNG().nextDouble() < 0.3);

		}
		player.playSound(Main.CHIRP_SOUND, 1, soundPitch(player));

		return super.magicFinish(world, player, staff);
	}

}
