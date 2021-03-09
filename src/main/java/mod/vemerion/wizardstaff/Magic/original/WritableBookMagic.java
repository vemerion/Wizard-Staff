package mod.vemerion.wizardstaff.Magic.original;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import mod.vemerion.wizardstaff.staff.WizardStaffItemHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.world.World;

public class WritableBookMagic extends Magic {

	public WritableBookMagic(String name) {
		super(name);
	}

	private String[] wisdoms = { "All that glitters is gold", "Fear the old blood",
			"Every fleeing man must be caught. Every secret must be unearthed. Such is the conceit of the self-proclaimed seeker of truth.",
			"What we do in life echoes in eternity", "STEVEN LIVES", "What we've got here is... failure to communicate",
			"All those moments will be lost in time, like tears in rain",
			"A wizard is never late, nor is he early. He arrives precisely when he means to." };

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
	}

	@Override
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		String wisdom = wisdoms[player.getRNG().nextInt(wisdoms.length)];
		player.playSound(ModSounds.SCRIBBLE_SOUND, 1, soundPitch(player));
		if (!world.isRemote) {
			cost(player);
			WizardStaffItemHandler handler = WizardStaffItemHandler.get(staff);
			ItemStack book = handler.extractItem(0, 1, false);
			CompoundNBT tag = book.getOrCreateTag();
			ListNBT pages = new ListNBT();
			pages.add(StringNBT.valueOf(wisdom));
			tag.put("pages", pages);
			book = new ItemStack(Items.WRITABLE_BOOK);
			book.setTag(tag);
			handler.insertItem(0, book, false);
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
}
