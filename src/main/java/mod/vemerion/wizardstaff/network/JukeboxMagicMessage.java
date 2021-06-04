package mod.vemerion.wizardstaff.network;

import java.util.UUID;
import java.util.function.Supplier;

import mod.vemerion.wizardstaff.sound.WizardStaffTickableSound;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

public class JukeboxMagicMessage {

	private UUID id;

	public JukeboxMagicMessage(UUID id) {
		this.id = id;
	}

	public void encode(final PacketBuffer buffer) {
		buffer.writeUniqueId(id);
	}

	public static JukeboxMagicMessage decode(final PacketBuffer buffer) {
		return new JukeboxMagicMessage(buffer.readUniqueId());
	}

	public void handle(final Supplier<NetworkEvent.Context> supplier) {
		final NetworkEvent.Context context = supplier.get();
		context.setPacketHandled(true);
		context.enqueueWork(() -> DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> PlayMusic.play(id)));
	}

	private static class PlayMusic {
		private static DistExecutor.SafeRunnable play(UUID id) {
			return new DistExecutor.SafeRunnable() {
				private static final long serialVersionUID = 1L;

				@Override
				public void run() {
					WizardStaffTickableSound sound = new WizardStaffTickableSound(id);
					Minecraft.getInstance().getSoundHandler().play(sound);
				}
			};
		}
	}
}
