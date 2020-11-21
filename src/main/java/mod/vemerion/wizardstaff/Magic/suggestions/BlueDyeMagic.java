package mod.vemerion.wizardstaff.Magic.suggestions;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;

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
			WorldInfo info = world.getWorldInfo();
			cost(player);
			boolean isRaining = info.isRaining();
			int clearWeatherTime = isRaining ? 6000 : 0;
			int rainTime = isRaining ? 0 : 6000;
			info.setClearWeatherTime(clearWeatherTime);
			info.setRainTime(rainTime);
			info.setThunderTime(rainTime);
			info.setRaining(!isRaining);
			info.setThundering(!isRaining && player.getRNG().nextDouble() < 0.3);
		}

		return super.magicFinish(world, player, staff);
	}

}
