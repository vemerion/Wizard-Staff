package mod.vemerion.wizardstaff.network;

import java.util.UUID;
import java.util.function.Supplier;

import mod.vemerion.wizardstaff.sound.WizardStaffTickableSound;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

public class JukeboxMagicMessage {

	private UUID id;
	private SoundEvent music;

	public JukeboxMagicMessage(UUID id, SoundEvent music) {
		this.id = id;
		this.music = music;
	}

	public void encode(final PacketBuffer buffer) {
		buffer.writeUniqueId(id);
		buffer.writeRegistryId(music);
	}

	public static JukeboxMagicMessage decode(final PacketBuffer buffer) {
		return new JukeboxMagicMessage(buffer.readUniqueId(), buffer.readRegistryId());
	}

	public void handle(final Supplier<NetworkEvent.Context> supplier) {
		final NetworkEvent.Context context = supplier.get();
		context.setPacketHandled(true);
		context.enqueueWork(() -> DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> PlayMusic.play(id, music)));
	}

	private static class PlayMusic {
		private static DistExecutor.SafeRunnable play(UUID id, SoundEvent music) {
			return new DistExecutor.SafeRunnable() {
				private static final long serialVersionUID = 1L;

				@Override
				public void run() {
					WizardStaffTickableSound sound = new WizardStaffTickableSound(id, music);
					Minecraft.getInstance().getSoundHandler().play(sound);
				}
			};
		}
	}
}
