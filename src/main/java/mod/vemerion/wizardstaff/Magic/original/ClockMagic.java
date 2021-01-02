package mod.vemerion.wizardstaff.Magic.original;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ClockMagic extends Magic {
	
	public ClockMagic(String name) {
		super(name);
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
	}

	@Override
	public void magicTick(World world, PlayerEntity player, ItemStack staff, int count) {
		if (count % 7 == 0)
			player.playSound(Main.CLOCK_SOUND, 1, soundPitch(player));
		if (!world.isRemote) {
			cost(player);
		}
		if (!world.isRemote) {
			((ServerWorld) world).setDayTime(world.getDayTime() + 40);
		} else {
			((ClientWorld) world).setDayTime(world.getDayTime() + 40);
		}
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::spinMagic;
	}
	
	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::spinMagic;
	}
}
