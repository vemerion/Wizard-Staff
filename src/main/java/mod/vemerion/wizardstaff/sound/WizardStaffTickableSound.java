package mod.vemerion.wizardstaff.sound;

import mod.vemerion.wizardstaff.Main;
import net.minecraft.client.audio.TickableSound;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;

public class WizardStaffTickableSound extends TickableSound {
	
	private PlayerEntity player;
	private ItemStack staff;

	public WizardStaffTickableSound(PlayerEntity player, ItemStack staff) {
		super(SoundEvents.MUSIC_DISC_STAL, SoundCategory.PLAYERS);
		if (staff.getItem() != Main.WIZARD_STAFF_ITEM)
			throw new IllegalArgumentException("ItemStack does not contain a wizard staff");
		this.player = player;
		this.staff = staff;
	}

	@Override
	public void tick() {
		if (Main.WIZARD_STAFF_ITEM.getMagic(staff).getItem() == Items.JUKEBOX && player.getActiveItemStack().equals(staff)) {
			this.x = (float) player.getPosX();
			this.y = (float) player.getPosY();
			this.z = (float) player.getPosZ();
		} else {
			donePlaying = true;
		}
		
	}

}
