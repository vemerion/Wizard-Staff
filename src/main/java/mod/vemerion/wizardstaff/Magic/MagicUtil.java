package mod.vemerion.wizardstaff.Magic;

import java.util.Optional;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.serialization.JsonOps;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class MagicUtil {

	// Forge Registries
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
	// ---

	// Vanilla Registries
	public static <T> T read(JsonObject json, Registry<T> registry, String member) {
		ResourceLocation key = new ResourceLocation(JSONUtils.getString(json, member));
		Optional<T> value = registry.getOptional(key);
		if (value.isPresent()) {
			return value.get();
		} else {
			throw new JsonParseException("Invalid registry key " + key + " for registry "
					+ registry.getRegistryKey().getLocation().getPath());
		}
	}

	public static <T> T read(JsonObject json, Registry<T> registry, String member, T fallback) {
		return json.has(member) ? read(json, registry, member) : fallback;
	}

	public static <T> void encode(PacketBuffer buffer, T obj, Registry<T> registry) {
		String key = registry.getKey(obj).toString();
		buffer.writeInt(key.length());
		buffer.writeString(key);
	}

	public static <T> T decode(PacketBuffer buffer, Registry<T> registry) {
		int keyLen = buffer.readInt();
		return registry.getOrDefault(new ResourceLocation(buffer.readString(keyLen)));
	}
	// ---

	// Other
	public static BlockPos readBlockPos(JsonObject json, String member) {
		Optional<BlockPos> pos = BlockPos.CODEC.parse(JsonOps.INSTANCE, json.get(member)).result();
		if (!pos.isPresent()) {
			throw new JsonParseException("Missing or invalid BlockPos for member " + member);
		} else {
			return pos.get();
		}
	}
}
