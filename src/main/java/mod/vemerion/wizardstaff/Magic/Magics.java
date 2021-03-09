package mod.vemerion.wizardstaff.Magic;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.fashionupdate.TransmutationMagic;
import mod.vemerion.wizardstaff.Magic.netherupdate.GhastTearMagic;
import mod.vemerion.wizardstaff.Magic.netherupdate.GlowstoneDustMagic;
import mod.vemerion.wizardstaff.Magic.netherupdate.GoldNuggetMagic;
import mod.vemerion.wizardstaff.Magic.netherupdate.LodestoneMagic;
import mod.vemerion.wizardstaff.Magic.netherupdate.NetherBrickMagic;
import mod.vemerion.wizardstaff.Magic.netherupdate.NetherrackMagic;
import mod.vemerion.wizardstaff.Magic.netherupdate.ObsidianMagic;
import mod.vemerion.wizardstaff.Magic.netherupdate.ProjectileMagic;
import mod.vemerion.wizardstaff.Magic.netherupdate.SoulSandMagic;
import mod.vemerion.wizardstaff.Magic.original.BlazePowderMagic;
import mod.vemerion.wizardstaff.Magic.original.CarvedPumpkinMagic;
import mod.vemerion.wizardstaff.Magic.original.ClockMagic;
import mod.vemerion.wizardstaff.Magic.original.EggMagic;
import mod.vemerion.wizardstaff.Magic.original.ElytraMagic;
import mod.vemerion.wizardstaff.Magic.original.JukeboxMagic;
import mod.vemerion.wizardstaff.Magic.original.TransformBlockMagic;
import mod.vemerion.wizardstaff.Magic.original.WizardStaffMagic;
import mod.vemerion.wizardstaff.Magic.original.WritableBookMagic;
import mod.vemerion.wizardstaff.Magic.spellbookupdate.BookshelfMagic;
import mod.vemerion.wizardstaff.Magic.spellbookupdate.DeageMagic;
import mod.vemerion.wizardstaff.Magic.spellbookupdate.MapMagic;
import mod.vemerion.wizardstaff.Magic.spellbookupdate.PortableCraftingMagic;
import mod.vemerion.wizardstaff.Magic.suggestions.BlueDyeMagic;
import mod.vemerion.wizardstaff.Magic.suggestions.BricksMagic;
import mod.vemerion.wizardstaff.Magic.suggestions.FeatherMagic;
import mod.vemerion.wizardstaff.Magic.suggestions.GrapplingHookMagic;
import mod.vemerion.wizardstaff.Magic.suggestions.MushroomCloudMagic;
import mod.vemerion.wizardstaff.Magic.suggestions.ShulkerBulletMagic;
import mod.vemerion.wizardstaff.Magic.suggestions.WaterBucketMagic;
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
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class Magics extends JsonReloadListener {
	private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
	private static final String FOLDER_NAME = Main.MODID + "-magics";

	private static Magics instance;

	private Map<String, Supplier<Magic>> registry; // All possible magics, registered in initMagicNames()
	private Map<ResourceLocation, Magic> magics; // The actual magics, as determined by the json magic files
	private ImmutableSet<ItemStack> magicItems; // All items that have magic effect (used by spellbook)
	private Map<Item, ResourceLocation> cache; // Cache for faster lookup in get()

	private final NoMagic NO_MAGIC = new NoMagic();

	private Magics() {
		super(GSON, FOLDER_NAME);
		this.registry = new HashMap<>();
		this.magics = new HashMap<>();
		this.cache = new HashMap<>();
		this.registerMagics();
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
		return NO_MAGIC;
	}

	private void registerMagics() {
		register("blaze_powder_magic", (s) -> () -> new BlazePowderMagic(s));
		register("carved_pumpkin_magic", (s) -> () -> new CarvedPumpkinMagic(s));
		register("clock_magic", (s) -> () -> new ClockMagic(s));
		register("egg_magic", (s) -> () -> new EggMagic(s));
		register("elytra_magic", (s) -> () -> new ElytraMagic(s));
		register("transform_block_magic", (s) -> () -> new TransformBlockMagic(s));
		register("jukebox_magic", (s) -> () -> new JukeboxMagic(s));
		register("wizard_staff_magic", (s) -> () -> new WizardStaffMagic(s));
		register("writable_book_magic", (s) -> () -> new WritableBookMagic(s));
		register("obsidian_magic", (s) -> () -> new ObsidianMagic(s));
		register("glowstone_dust_magic", (s) -> () -> new GlowstoneDustMagic(s));
		register("netherrack_magic", (s) -> () -> new NetherrackMagic(s));
		register("projectile_magic", (s) -> () -> new ProjectileMagic(s));
		register("ghast_tear_magic", (s) -> () -> new GhastTearMagic(s));
		register("nether_brick_magic", (s) -> () -> new NetherBrickMagic(s));
		register("soul_sand_magic", (s) -> () -> new SoulSandMagic(s));
		register("gold_nugget_magic", (s) -> () -> new GoldNuggetMagic(s));
		register("lodestone_magic", (s) -> () -> new LodestoneMagic(s));
		register("transmutation_magic", (s) -> () -> new TransmutationMagic(s));
		register("blue_dye_magic", (s) -> () -> new BlueDyeMagic(s));
		register("bricks_magic", (s) -> () -> new BricksMagic(s));
		register("grappling_hook_magic", (s) -> () -> new GrapplingHookMagic(s));
		register("feather_magic", (s) -> () -> new FeatherMagic(s));
		register("mushroom_cloud_magic", (s) -> () -> new MushroomCloudMagic(s));
		register("shulker_bullet_magic", (s) -> () -> new ShulkerBulletMagic(s));
		register("water_bucket_magic", (s) -> () -> new WaterBucketMagic(s));
		register("portable_crafting_magic", (s) -> () -> new PortableCraftingMagic(s));
		register("bookshelf_magic", (s) -> () -> new BookshelfMagic(s));
		register("map_magic", (s) -> () -> new MapMagic(s));
		register("deage_magic", (s) -> () -> new DeageMagic(s));
		register("no_magic", (s) -> () -> NO_MAGIC);
	}

	private void register(String name, Function<String, Supplier<Magic>> magic) {
		registry.put(name, magic.apply(name));
	}

	public static Magics getInstance() {
		return instance;
	}

	public static void init() {
		instance = new Magics();
	}

	@Override
	protected void apply(Map<ResourceLocation, JsonElement> objectIn, IResourceManager resourceManagerIn,
			IProfiler profilerIn) {
		Map<ResourceLocation, Magic> newMagics = new HashMap<>();
		for (Entry<ResourceLocation, JsonElement> entry : objectIn.entrySet()) {
			ResourceLocation key = entry.getKey();
			if (!ModList.get().isLoaded(key.getNamespace())) // Skip magics from mods that are not present
				continue;

			JsonObject json = JSONUtils.getJsonObject(entry.getValue(), "top element");
			String magicName = JSONUtils.getString(json, "magic");
			if (!registry.containsKey(magicName))
				throw new JsonSyntaxException("The magic " + magicName + " does not exist");
			Magic magic = registry.get(magicName).get();

			if (magic == NO_MAGIC)
				continue;

			magic.read(json);
			newMagics.put(key, magic);
		}

		addMagics(newMagics);
		if (ServerLifecycleHooks.getCurrentServer() != null)
			sendMagicMessage(newMagics);
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

	public Magic getFromName(String name) {
		return registry.get(name).get();
	}

}
