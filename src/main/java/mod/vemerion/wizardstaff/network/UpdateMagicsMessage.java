package mod.vemerion.wizardstaff.network;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.Magics;
import mod.vemerion.wizardstaff.init.ModMagics;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.DistExecutor.SafeRunnable;
import net.minecraftforge.fml.network.NetworkEvent;

public class UpdateMagicsMessage {

	private Map<ResourceLocation, Magic> magics;

	public UpdateMagicsMessage(Map<ResourceLocation, Magic> magics) {
		this.magics = magics;
	}

	public void encode(final PacketBuffer buffer) {
		buffer.writeInt(magics.size());
		for (Entry<ResourceLocation, Magic> entry : magics.entrySet()) {
			Magic m = entry.getValue();
			buffer.writeResourceLocation(entry.getKey());
			buffer.writeResourceLocation(m.getRegistryName());
			m.encode(buffer);
		}
	}

	public static UpdateMagicsMessage decode(final PacketBuffer buffer) {
		Map<ResourceLocation, Magic> magics = new HashMap<>();
		int size = buffer.readInt();
		for (int i = 0; i < size; i++) {
			ResourceLocation key = buffer.readResourceLocation();
			ResourceLocation name = buffer.readResourceLocation();
			Magic magic = ModMagics.REGISTRY.getValue(name).create(key);
			magic.decode(buffer);
			magics.put(key, magic);
		}
		return new UpdateMagicsMessage(magics);
	}

	public void handle(final Supplier<NetworkEvent.Context> supplier) {
		final NetworkEvent.Context context = supplier.get();
		context.setPacketHandled(true);
		context.enqueueWork(() -> DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> UpdateMagics.update(magics)));
	}

	private static class UpdateMagics {
		private static SafeRunnable update(Map<ResourceLocation, Magic> magics) {
			return new SafeRunnable() {
				private static final long serialVersionUID = 1L;

				@Override
				public void run() {
					if (Magics.getInstance(true) != null) {
						Magics.getInstance(true).addMagics(magics);
					}
				}
			};
		}
	}
}
