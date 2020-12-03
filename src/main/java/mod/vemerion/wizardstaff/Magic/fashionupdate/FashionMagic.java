package mod.vemerion.wizardstaff.Magic.fashionupdate;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import mod.vemerion.wizardstaff.staff.WizardStaffItemHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;

public class FashionMagic extends Magic {
	
	private Item toArmorPiece;
	
	public FashionMagic(Item toArmorPiece) {
		this.toArmorPiece = toArmorPiece;
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::buildupMagic;
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
		player.playSound(Main.PLOP_SOUND, 1, soundPitch(player));
		if (!world.isRemote) {
			cost(player);
			WizardStaffItemHandler handler = WizardStaffItemHandler.get(staff);
			ItemStack helmet = handler.extractItem(0, 1, false);
			ItemStack toArmorStack = new ItemStack(toArmorPiece);
			CompoundNBT tag = helmet.getOrCreateTag();
			if (tag.contains("display")) {
				CompoundNBT display = tag.getCompound("display");
				int color = display.getInt("color");

				tag = toArmorStack.getOrCreateTag();
				display = toArmorStack.getOrCreateChildTag("display");
				display.putInt("color", color);
			}
			handler.insertItem(0, toArmorStack, false);
		}
		return super.magicFinish(world, player, staff);
	}

}
