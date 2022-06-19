package mod.vemerion.wizardstaff.Magic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.init.ModMagics;
import mod.vemerion.wizardstaff.network.Network;
import mod.vemerion.wizardstaff.network.UpdateMagicsMessage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.server.ServerLifecycleHooks;

public class Magics extends SimpleJsonResourceReloadListener {
	private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
	public static final String FOLDER_NAME = Main.MODID + "magics";

	private static Magics clientInstance;
	private static Magics serverInstance;

	private Map<ResourceLocation, Magic> magics; // The actual magics, as determined by the json magic files
	private Map<Item, ResourceLocation> cache; // Cache for faster lookup in get()

	private Magics() {
		super(GSON, FOLDER_NAME);
		this.magics = new HashMap<>();
		this.cache = new HashMap<>();
	}

	public Magic get(ItemStack stack) {
		Item item = stack.getItem();
		if (cache.containsKey(item) && magics.get(cache.get(item)).isMagicItem(stack)) {
			return magics.get(cache.get(item));
		} else {
			for (Entry<ResourceLocation, Magic> entry : magics.entrySet()) {
				if (entry.getValue().isMagicItem(stack)) {
					Magic magic = entry.getValue();
					cache.put(stack.getItem(), entry.getKey());
					return magic;
				}
			}
		}
		return ModMagics.NO_MAGIC.create(new ResourceLocation(Main.MODID, "no_magic"));
	}

	public static Magics getInstance(boolean isRemote) {
		return isRemote ? clientInstance : serverInstance;
	}

	public static Magics getInstance(Level level) {
		return getInstance(level.isClientSide);
	}

	public static void init() {
		clientInstance = new Magics();
		serverInstance = new Magics();
	}

	@Override
	protected void apply(Map<ResourceLocation, JsonElement> objectIn, ResourceManager resourceManagerIn,
			ProfilerFiller profilerIn) {
		Map<ResourceLocation, Magic> newMagics = new HashMap<>();
		for (Entry<ResourceLocation, JsonElement> entry : objectIn.entrySet()) {
			ResourceLocation key = entry.getKey();

			JsonObject json = GsonHelper.convertToJsonObject(entry.getValue(), "top element");
			ResourceLocation magicKey = toResourceLocation(GsonHelper.getAsString(json, "magic"));
			if (!ModMagics.getRegistry().containsKey(magicKey))
				throw new JsonSyntaxException("The magic " + magicKey + " does not exist");
			MagicType<?> type = ModMagics.getRegistry().getValue(magicKey);

			if (type == ModMagics.NO_MAGIC)
				continue;

			Magic magic = type.create(key);
			magic.read(json);
			newMagics.put(key, magic);
		}

		addMagics(newMagics);
		if (ServerLifecycleHooks.getCurrentServer() != null)
			sendMagicMessage(newMagics);
	}

	private ResourceLocation toResourceLocation(String string) {
		if (string.contains(":"))
			return new ResourceLocation(string);
		else
			return new ResourceLocation(Main.MODID, string);
	}

	private void sendMagicMessage(Map<ResourceLocation, Magic> newMagics) {
		Network.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateMagicsMessage(newMagics));
	}

	public void sendAllMagicMessage(ServerPlayer reciever) {
		Network.INSTANCE.send(PacketDistributor.PLAYER.with(() -> reciever), new UpdateMagicsMessage(magics));
	}

	public void addMagics(Map<ResourceLocation, Magic> newMagics) {
		cache = new HashMap<>();
		magics.putAll(newMagics);
	}

	public Set<ItemStack> getMagicItems() {
		var items = new HashSet<ItemStack>();
		for (Magic m : magics.values())
			for (ItemStack stack : m.getMatchingStacks())
				items.add(stack);
		return items;
	}
}
