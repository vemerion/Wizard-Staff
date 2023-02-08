package mod.vemerion.wizardstaff.network;

import java.util.function.Supplier;

import mod.vemerion.wizardstaff.capability.Wizard;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

public class ListenMagicMessage {

	private BlockPos position;

	public ListenMagicMessage(BlockPos position) {
		this.position = position;
	}

	public void encode(final FriendlyByteBuf buffer) {
		buffer.writeBlockPos(position);
	}

	public static ListenMagicMessage decode(final FriendlyByteBuf buffer) {
		return new ListenMagicMessage(buffer.readBlockPos());
	}

	public void handle(final Supplier<NetworkEvent.Context> supplier) {
		final NetworkEvent.Context context = supplier.get();
		context.setPacketHandled(true);
		context.enqueueWork(() -> DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> PlayMusic.play(position)));
	}

	private static class PlayMusic {
		private static DistExecutor.SafeRunnable play(BlockPos position) {
			return new DistExecutor.SafeRunnable() {
				private static final long serialVersionUID = 1L;

				@Override
				public void run() {
					var mc = Minecraft.getInstance();
					var player = mc.player;
					if (player == null)
						return;

					Wizard.getWizardOptional(player).ifPresent(w -> {
						w.getXRayed().add(position);
					});
				}
			};
		}
	}

}
