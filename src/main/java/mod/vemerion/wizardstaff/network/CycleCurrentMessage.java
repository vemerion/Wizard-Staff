package mod.vemerion.wizardstaff.network;

import java.util.function.Supplier;

import mod.vemerion.wizardstaff.staff.WizardStaffItemHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

public class CycleCurrentMessage {

	public void encode(final FriendlyByteBuf buffer) {
	}

	public static CycleCurrentMessage decode(final FriendlyByteBuf buffer) {
		return new CycleCurrentMessage();
	}

	public void handle(final Supplier<NetworkEvent.Context> supplier) {
		final NetworkEvent.Context context = supplier.get();
		context.setPacketHandled(true);
		context.enqueueWork(() -> cycle(context.getSender()));
	}

	private void cycle(ServerPlayer player) {
		if (player == null)
			return;

		ItemStack staff = player.getMainHandItem();
		WizardStaffItemHandler.getOptional(staff).ifPresent(h -> {
			h.cycleCurrent();
		});
	}

}
