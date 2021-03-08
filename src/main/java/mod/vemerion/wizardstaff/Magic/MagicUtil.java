package mod.vemerion.wizardstaff.Magic;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class MagicUtil {

	public static <T extends IForgeRegistryEntry<T>> T read(JsonObject json, IForgeRegistry<T> registry,
			String member) {
		ResourceLocation key = new ResourceLocation(JSONUtils.getString(json, member));
		if (registry.containsKey(key)) {
			return registry.getValue(key);
		} else {
			throw new JsonParseException(
					"Invalid registry key " + key + " for registry " + registry.getRegistryName().getPath());
		}
	}

	public static <T extends IForgeRegistryEntry<T>> T read(JsonObject json, IForgeRegistry<T> registry, String member,
			T fallback) {
		return json.has(member) ? read(json, registry, member) : fallback;
	}

	public static <T extends IForgeRegistryEntry<T>> void encode(PacketBuffer buffer, T obj) {
		String key = obj.getRegistryName().toString();
		buffer.writeInt(key.length());
		buffer.writeString(key);
	}

	public static <T extends IForgeRegistryEntry<T>> T decode(PacketBuffer buffer, IForgeRegistry<T> registry) {
		int keyLen = buffer.readInt();
		return registry.getValue(new ResourceLocation(buffer.readString(keyLen)));
	}
}
