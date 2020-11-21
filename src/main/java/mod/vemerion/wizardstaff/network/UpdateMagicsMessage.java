package mod.vemerion.wizardstaff.network;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;

import mod.vemerion.wizardstaff.Magic.Magics;
import mod.vemerion.wizardstaff.Magic.Magics.MagicParams;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

public class UpdateMagicsMessage {

	private Map<ResourceLocation, MagicParams> magicParams;

	public UpdateMagicsMessage(Map<ResourceLocation, MagicParams> magicParams) {
		this.magicParams = magicParams;
	}

	public void encode(final PacketBuffer buffer) {
		buffer.writeInt(magicParams.size());
		for (Entry<ResourceLocation, MagicParams> entry : magicParams.entrySet()) {
			buffer.writeResourceLocation(entry.getKey());
			entry.getValue().encode(buffer);
		}
	}

	public static UpdateMagicsMessage decode(final PacketBuffer buffer) {
		Map<ResourceLocation, MagicParams> magicParams = new HashMap<>();
		int size = buffer.readInt();
		for (int i = 0; i < size; i++) {
			ResourceLocation key = buffer.readResourceLocation();
			magicParams.put(key, MagicParams.decode(buffer));
		}
		return new UpdateMagicsMessage(magicParams);
	}

	public void handle(final Supplier<NetworkEvent.Context> supplier) {
		final NetworkEvent.Context context = supplier.get();
		context.setPacketHandled(true);
		context.enqueueWork(() -> DistExecutor.runWhenOn(Dist.CLIENT, () -> UpdateMagics.update(magicParams)));
	}

	private static class UpdateMagics {
		private static Runnable update(Map<ResourceLocation, MagicParams> magicParams) {
			return new Runnable() {
				@Override
				public void run() {
					if (Magics.getInstance() != null) {
						Magics.getInstance().addMagics(magicParams);
					}
				}
			};
		}
	}
}
