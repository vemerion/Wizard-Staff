package mod.vemerion.wizardstaff.sound;

import java.util.UUID;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.Magics;
import mod.vemerion.wizardstaff.Magic.original.JukeboxMagic;
import mod.vemerion.wizardstaff.staff.WizardStaffItemHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.TickableSound;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;

public class WizardStaffTickableSound extends TickableSound {

	private UUID id;
	private boolean started;

	public WizardStaffTickableSound(UUID id) {
		super(SoundEvents.MUSIC_DISC_STAL, SoundCategory.PLAYERS);
		this.id = id;
		this.volume = 0;
	}

	@Override
	public void tick() {
		if (isDonePlaying())
			return;
		Minecraft mc = Minecraft.getInstance();
		PlayerEntity player = mc.world == null ? null : mc.world.getPlayerByUuid(id);
		ItemStack stack = player == null ? ItemStack.EMPTY : player.getActiveItemStack();
		WizardStaffItemHandler handler = WizardStaffItemHandler.orNull(stack);
		if (player == null || handler == null || !(getMagic(handler) instanceof JukeboxMagic)) {
			if (started)
				finishPlaying();
		} else {
			started = true;
			volume = 1;
			x = player.getPosX();
			y = player.getPosY();
			z = player.getPosZ();
		}
	}

	@Override
	public boolean canBeSilent() {
		return true;
	}

	private static Magic getMagic(WizardStaffItemHandler handler) {
		return Magics.getInstance(true).get(handler.getStackInSlot(0));
	}

}
