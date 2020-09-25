package mod.vemerion.wizardstaff.Magic.original;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderMagic;
import mod.vemerion.wizardstaff.staff.WizardStaffHandler;
import mod.vemerion.wizardstaff.staff.WizardStaffItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;

public class LeatherHelmetMagic extends Magic {

	@Override
	public int getUseDuration(ItemStack staff) {
		return 20;
	}

	@Override
	public boolean isMagicItem(Item item) {
		return item == Items.LEATHER_HELMET;
	}

	@Override
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		player.playSound(Main.PLOP_SOUND, 1, soundPitch(player));
		if (!world.isRemote) {
			cost(player, 30);
			WizardStaffHandler handler = WizardStaffItem.getHandler(staff);
			ItemStack helmet = handler.extractItem(0, 1, false);
			ItemStack wizardHat = new ItemStack(Main.WIZARD_HAT_ITEM);
			CompoundNBT tag = helmet.getOrCreateTag();
			if (tag.contains("display")) {
				CompoundNBT display = tag.getCompound("display");
				int color = display.getInt("color");

				tag = wizardHat.getOrCreateTag();
				display = wizardHat.getOrCreateChildTag("display");
				display.putInt("color", color);
			}
			handler.insertItem(0, wizardHat, false);
		}
		return super.magicFinish(world, player, staff);
	}
	
	@Override
	public RenderMagic renderer() {
		return WizardStaffTileEntityRenderer::forwardBuildup;
	}

}
