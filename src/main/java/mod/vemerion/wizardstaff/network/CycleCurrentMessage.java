package mod.vemerion.wizardstaff.network;

import java.util.function.Supplier;

import mod.vemerion.wizardstaff.staff.WizardStaffItemHandler;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class CycleCurrentMessage {

	public void encode(final PacketBuffer buffer) {
	}

	public static CycleCurrentMessage decode(final PacketBuffer buffer) {
		return new CycleCurrentMessage();
	}

	public void handle(final Supplier<NetworkEvent.Context> supplier) {
		final NetworkEvent.Context context = supplier.get();
		context.setPacketHandled(true);
		context.enqueueWork(() -> cycle(context.getSender()));
	}

	private void cycle(ServerPlayerEntity player) {
		if (player == null)
			return;

		ItemStack staff = player.getHeldItemMainhand();
		WizardStaffItemHandler.getOptional(staff).ifPresent(h -> {
			h.cycleCurrent();
		});
	}

}
