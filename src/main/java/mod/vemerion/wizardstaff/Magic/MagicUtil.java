package mod.vemerion.wizardstaff.Magic;

import java.util.Collection;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.serialization.JsonOps;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class MagicUtil {

	// Forge Registries
	public static <T extends IForgeRegistryEntry<T>> T read(JsonObject json, IForgeRegistry<T> registry,
			String member) {
		ResourceLocation key = new ResourceLocation(GsonHelper.getAsString(json, member));
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

	public static <T extends IForgeRegistryEntry<T>> void write(JsonObject json, T obj, String member) {
		String key = obj.getRegistryName().toString();
		json.addProperty(member, key);
	}

	public static <T extends IForgeRegistryEntry<T>> void encode(FriendlyByteBuf buffer, T obj) {
		buffer.writeRegistryId(obj);
	}

	public static <T extends IForgeRegistryEntry<T>> T decode(FriendlyByteBuf buffer) {
		return buffer.readRegistryId();
	}
	// ---

	// Vanilla Registries
	public static <T> T read(JsonObject json, Registry<T> registry, String member) {
		ResourceLocation key = new ResourceLocation(GsonHelper.getAsString(json, member));
		Optional<T> value = registry.getOptional(key);
		if (value.isPresent()) {
			return value.get();
		} else {
			throw new JsonParseException("Invalid registry key " + key + " for registry "
					+ registry.key().location().getPath());
		}
	}

	public static <T> T read(JsonObject json, Registry<T> registry, String member, T fallback) {
		return json.has(member) ? read(json, registry, member) : fallback;
	}

	public static <T> void write(JsonObject json, T obj, Registry<T> registry, String member) {
		json.addProperty(member, registry.getKey(obj).toString());
	}

	public static <T> void encode(FriendlyByteBuf buffer, T obj, Registry<T> registry) {
		String key = registry.getKey(obj).toString();
		buffer.writeInt(key.length());
		buffer.writeUtf(key);
	}

	public static <T> T decode(FriendlyByteBuf buffer, Registry<T> registry) {
		int keyLen = buffer.readInt();
		return registry.get(new ResourceLocation(buffer.readUtf(keyLen)));
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

	public static void writeBlockPos(JsonObject json, String member, BlockPos pos) {
		BlockPos.CODEC.encodeStart(JsonOps.INSTANCE, pos).result().ifPresent(elem -> json.add(member, elem));
	}

	public static <T, C extends Collection<T>> C readColl(JsonObject json, String member, Function<JsonElement, T> f,
			C coll) {
		JsonArray array = GsonHelper.getAsJsonArray(json, member);
		array.forEach(e -> coll.add(f.apply(e)));
		return coll;
	}

	public static <T> void writeColl(JsonObject json, String member, Collection<T> coll, Function<T, JsonElement> f) {
		JsonArray array = new JsonArray();
		for (T t : coll)
			array.add(f.apply(t));
		json.add(member, array);
	}

	public static <T, C extends Collection<T>> C decodeColl(FriendlyByteBuf buffer, Function<FriendlyByteBuf, T> f, C coll) {
		int count = buffer.readInt();
		for (int i = 0; i < count; i++)
			coll.add(f.apply(buffer));
		return coll;
	}

	public static <T> void encodeColl(FriendlyByteBuf buffer, Collection<T> coll, BiConsumer<FriendlyByteBuf, T> f) {
		buffer.writeInt(coll.size());
		for (T t : coll)
			f.accept(buffer, t);
	}
}
