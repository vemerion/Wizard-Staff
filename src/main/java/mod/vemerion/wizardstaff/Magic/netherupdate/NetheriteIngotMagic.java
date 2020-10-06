package mod.vemerion.wizardstaff.Magic.netherupdate;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderMagic;
import mod.vemerion.wizardstaff.staff.WizardStaffItem;
import mod.vemerion.wizardstaff.staff.WizardStaffItemHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class NetheriteIngotMagic extends Magic {
	private static final ResourceLocation NETHERITE_INGOT = new ResourceLocation("forge", "ingots/netherite");

	@Override
	public int getUseDuration(ItemStack staff) {
		return 40;
	}

	@Override
	public boolean isMagicItem(Item item) {
		return ItemTags.getCollection().getTagByID(NETHERITE_INGOT).contains(item);
	}

	@Override
	public RenderMagic renderer() {
		return WizardStaffTileEntityRenderer::buildupMagic;
	}
	
	@Override
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		player.playSound(Main.POOF_SOUND, 1, soundPitch(player));
		if (!world.isRemote) {
			cost(player, 100);
			WizardStaffItemHandler handler = WizardStaffItem.getHandler(staff);
			handler.extractItem(0, 1, false);
			ItemStack netherWizardStaff = new ItemStack(Main.NETHER_WIZARD_STAFF_ITEM);
			handler.insertItem(0, netherWizardStaff, false);
		}
		return super.magicFinish(world, player, staff);
	}

}
