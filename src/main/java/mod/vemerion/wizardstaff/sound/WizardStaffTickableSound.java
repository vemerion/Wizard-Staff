package mod.vemerion.wizardstaff.sound;

import java.util.UUID;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.Magics;
import mod.vemerion.wizardstaff.Magic.original.JukeboxMagic;
import mod.vemerion.wizardstaff.staff.WizardStaffItemHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class WizardStaffTickableSound extends AbstractTickableSoundInstance {

	private UUID id;
	private boolean started;

	public WizardStaffTickableSound(UUID id, SoundEvent music) {
		super(music, SoundSource.PLAYERS);
		this.id = id;
		this.volume = 0;
		this.looping = true;
	}

	@Override
	public void tick() {
		if (isStopped())
			return;
		Minecraft mc = Minecraft.getInstance();
		Player player = mc.level == null ? null : mc.level.getPlayerByUUID(id);
		ItemStack stack = player == null ? ItemStack.EMPTY : player.getUseItem();
		WizardStaffItemHandler handler = WizardStaffItemHandler.orNull(stack);
		if (player == null || handler == null || !(getMagic(handler) instanceof JukeboxMagic)) {
			if (started)
				stop();
		} else {
			started = true;
			volume = 1;
			x = player.getX();
			y = player.getY();
			z = player.getZ();
		}
	}

	@Override
	public boolean canStartSilent() {
		return true;
	}

	private static Magic getMagic(WizardStaffItemHandler handler) {
		return Magics.getInstance(true).get(handler.getCurrent());
	}

}
