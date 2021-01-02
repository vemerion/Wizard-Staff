package mod.vemerion.wizardstaff.Magic.netherupdate;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import mod.vemerion.wizardstaff.staff.WizardStaffItem;
import mod.vemerion.wizardstaff.staff.WizardStaffItemHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.world.World;

public class NetheriteIngotMagic extends Magic {
	
	public NetheriteIngotMagic(String name) {
		super(name);
	}

	@Override
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		player.playSound(Main.POOF_SOUND, 1, soundPitch(player));
		if (!world.isRemote) {
			cost(player);
			WizardStaffItemHandler handler = WizardStaffItem.getHandler(staff);
			handler.extractItem(0, 1, false);
			ItemStack netherWizardStaff = new ItemStack(Main.NETHER_WIZARD_STAFF_ITEM);
			handler.insertItem(0, netherWizardStaff, false);
		}
		return super.magicFinish(world, player, staff);
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

}
