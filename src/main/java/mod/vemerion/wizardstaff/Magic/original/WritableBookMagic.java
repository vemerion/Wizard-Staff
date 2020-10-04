package mod.vemerion.wizardstaff.Magic.original;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderMagic;
import mod.vemerion.wizardstaff.staff.WizardStaffItemHandler;
import mod.vemerion.wizardstaff.staff.WizardStaffItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.world.World;

public class WritableBookMagic extends Magic {

	private String[] wisdoms = { "All that glitters is gold", "Fear the old blood",
			"Every fleeing man must be caught. Every secret must be unearthed. Such is the conceit of the self-proclaimed seeker of truth.",
			"What we do in life echoes in eternity", "STEVEN LIVES", "What we've got here is... failure to communicate",
			"All those moments will be lost in time, like tears in rain",
			"A wizard is never late, nor is he early. He arrives precisely when he means to." };

	@Override
	public int getUseDuration(ItemStack stack) {
		return 20;
	}

	@Override
	public boolean isMagicItem(Item item) {
		return item == Items.WRITABLE_BOOK;
	}

	@Override
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		String wisdom = wisdoms[player.getRNG().nextInt(wisdoms.length)];
		player.playSound(Main.SCRIBBLE_SOUND, 1, soundPitch(player));
		if (!world.isRemote) {
			cost(player, 10);
			WizardStaffItemHandler handler = WizardStaffItem.getHandler(staff);
			ItemStack book = handler.extractItem(0, 1, false);
			CompoundNBT tag = book.getOrCreateTag();
			ListNBT pages = new ListNBT();
			pages.add(StringNBT.valueOf(wisdom));
			tag.put("pages", pages);
			book.setTag(tag);
			handler.insertItem(0, book, false);
		}
		return super.magicFinish(world, player, staff);
	}
	
	@Override
	public RenderMagic renderer() {
		return WizardStaffTileEntityRenderer::buildupMagic;
	}
}
