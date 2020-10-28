package mod.vemerion.wizardstaff.Magic.original;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ClockMagic extends Magic {

	@Override
	public int getUseDuration(ItemStack staff) {
		return HOUR;
	}

	@Override
	public boolean isMagicItem(Item item) {
		return item == Items.CLOCK;
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
			cost(player, 1);
		}
		if (!world.isRemote) {
			((ServerWorld) world).func_241114_a_(world.getDayTime() + 40);
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
