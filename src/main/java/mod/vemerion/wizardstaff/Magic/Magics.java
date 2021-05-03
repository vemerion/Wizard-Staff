package mod.vemerion.wizardstaff.Magic;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.init.ModMagics;
import mod.vemerion.wizardstaff.network.Network;
import mod.vemerion.wizardstaff.network.UpdateMagicsMessage;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class Magics extends JsonReloadListener {
	private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
	private static final String FOLDER_NAME = Main.MODID + "-magics";

	private static Magics clientInstance;
	private static Magics serverInstance;

	private Map<ResourceLocation, Magic> magics; // The actual magics, as determined by the json magic files
	private ImmutableSet<ItemStack> magicItems; // All items that have magic effect (used by spellbook)
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
		return ModMagics.NO_MAGIC.create();
	}

	public static Magics getInstance(boolean isRemote) {
		return isRemote ? clientInstance : serverInstance;
	}

	public static Magics getInstance(World world) {
		return getInstance(world.isRemote);
	}

	public static void init() {
		clientInstance = new Magics();
		serverInstance = new Magics();
	}

	@Override
	protected void apply(Map<ResourceLocation, JsonElement> objectIn, IResourceManager resourceManagerIn,
			IProfiler profilerIn) {
		Map<ResourceLocation, Magic> newMagics = new HashMap<>();
		for (Entry<ResourceLocation, JsonElement> entry : objectIn.entrySet()) {
			ResourceLocation key = entry.getKey();

			JsonObject json = JSONUtils.getJsonObject(entry.getValue(), "top element");
			ResourceLocation magicKey = toResourceLocation(JSONUtils.getString(json, "magic"));
			if (!ModMagics.REGISTRY.containsKey(magicKey))
				throw new JsonSyntaxException("The magic " + magicKey + " does not exist");
			MagicType type = ModMagics.REGISTRY.getValue(magicKey);

			if (type == ModMagics.NO_MAGIC)
				continue;

			Magic magic = type.create();
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

	public void sendAllMagicMessage(ServerPlayerEntity reciever) {
		Network.INSTANCE.send(PacketDistributor.PLAYER.with(() -> reciever), new UpdateMagicsMessage(magics));
	}

	public void addMagics(Map<ResourceLocation, Magic> newMagics) {
		cache = new HashMap<>();
		magics.putAll(newMagics);

		// Build magicItems set
		ImmutableSet.Builder<ItemStack> builder = ImmutableSet.builder();
		for (Magic m : newMagics.values())
			for (ItemStack stack : m.getMatchingStacks())
				builder.add(stack);
		magicItems = builder.build();
	}

	public ImmutableSet<ItemStack> getMagicItems() {
		return magicItems;
	}
}
