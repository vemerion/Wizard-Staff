package mod.vemerion.wizardstaff.Magic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.fashionupdate.FashionMagic;
import mod.vemerion.wizardstaff.Magic.netherupdate.GhastTearMagic;
import mod.vemerion.wizardstaff.Magic.netherupdate.GlowstoneDustMagic;
import mod.vemerion.wizardstaff.Magic.netherupdate.NetherBrickMagic;
import mod.vemerion.wizardstaff.Magic.netherupdate.NetherrackMagic;
import mod.vemerion.wizardstaff.Magic.netherupdate.ObsidianMagic;
import mod.vemerion.wizardstaff.Magic.netherupdate.SoulSandMagic;
import mod.vemerion.wizardstaff.Magic.netherupdate.WitherSkullMagic;
import mod.vemerion.wizardstaff.Magic.original.BlazePowderMagic;
import mod.vemerion.wizardstaff.Magic.original.CarvedPumpkinMagic;
import mod.vemerion.wizardstaff.Magic.original.ClockMagic;
import mod.vemerion.wizardstaff.Magic.original.EggMagic;
import mod.vemerion.wizardstaff.Magic.original.ElytraMagic;
import mod.vemerion.wizardstaff.Magic.original.GoldMagic;
import mod.vemerion.wizardstaff.Magic.original.JukeboxMagic;
import mod.vemerion.wizardstaff.Magic.original.WizardStaffMagic;
import mod.vemerion.wizardstaff.Magic.original.WritableBookMagic;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class Magics extends JsonReloadListener {
	private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();

	private static Magics instance;

	private List<Magic> magics;
	private Map<Item, Integer> cache;
	private final NoMagic NO_MAGIC = new NoMagic();

	private Magics() {
		super(GSON, "magics");
		this.magics = new ArrayList<>();
		this.cache = new HashMap<>();
		this.addMagics();
	}

	public Magic get(Item item) {
		if (cache.containsKey(item)) {
			return magics.get(cache.get(item));
		} else {
			for (int i = 0; i < magics.size(); i++) {
				if (magics.get(i).isMagicItem(item)) {
					cache.put(item, i);
					return magics.get(i);
				}
			}
		}
		return NO_MAGIC;
	}

	private void addMagics() {
		magics.add(new BlazePowderMagic());
		magics.add(new CarvedPumpkinMagic());
		magics.add(new ClockMagic());
		magics.add(new EggMagic());
		magics.add(new ElytraMagic());
		magics.add(new GoldMagic());
		magics.add(new JukeboxMagic());
		magics.add(new WizardStaffMagic());
		magics.add(new WritableBookMagic());
		magics.add(new ObsidianMagic());
		magics.add(new GlowstoneDustMagic());
		magics.add(new NetherrackMagic());
		magics.add(new WitherSkullMagic());
		magics.add(new GhastTearMagic());
		magics.add(new NetherBrickMagic());
		magics.add(new SoulSandMagic());

		magics.add(new FashionMagic(Items.LEATHER_BOOTS, Main.WIZARD_BOOTS_ITEM));
		magics.add(new FashionMagic(Items.LEATHER_CHESTPLATE, Main.WIZARD_CHESTPLATE_ITEM));
		magics.add(new FashionMagic(Items.LEATHER_HELMET, Main.WIZARD_HAT_ITEM));
		magics.add(new FashionMagic(Items.LEATHER_LEGGINGS, Main.WIZARD_LEGGINGS_ITEM));
		magics.add(new FashionMagic(Items.GOLDEN_BOOTS, Main.DRUID_BOOTS_ITEM));
		magics.add(new FashionMagic(Items.GOLDEN_CHESTPLATE, Main.DRUID_CHESTPLATE_ITEM));
		magics.add(new FashionMagic(Items.GOLDEN_HELMET, Main.DRUID_HELMET_ITEM));
		magics.add(new FashionMagic(Items.GOLDEN_LEGGINGS, Main.DRUID_LEGGINGS_ITEM));
		magics.add(new FashionMagic(Items.IRON_BOOTS, Main.WARLOCK_BOOTS_ITEM));
		magics.add(new FashionMagic(Items.IRON_CHESTPLATE, Main.WARLOCK_CHESTPLATE_ITEM));
		magics.add(new FashionMagic(Items.IRON_HELMET, Main.WARLOCK_HELMET_ITEM));
		magics.add(new FashionMagic(Items.IRON_LEGGINGS, Main.WARLOCK_LEGGINGS_ITEM));

		// This should be last
		magics.add(new NoMagic());
	}

	public static Magics getInstance() {
		return instance;
	}

	public static void init() {
		instance = new Magics();
	}

	@Override
	protected void apply(Map<ResourceLocation, JsonObject> objectIn, IResourceManager resourceManagerIn,
			IProfiler profilerIn) {
		for (Entry<ResourceLocation, JsonObject> entry : objectIn.entrySet()) {
			JsonObject json = entry.getValue();
			System.out.println(entry.getKey());
			float cost = JSONUtils.getFloat(json, "cost");
			if (cost < 0) 
				throw new JsonSyntaxException("The cost of a magic can not be negative");
			int duration = JSONUtils.getInt(json, "duration");
			String magic = JSONUtils.getString(json, "magic");
			Ingredient ingredient = Ingredient.deserialize(json.get("ingredient"));
			System.out.println(cost);
			System.out.println(duration);
			System.out.println(magic);
			System.out.println(ingredient.test(new ItemStack(Items.COBBLESTONE)));
			System.out.println(ingredient.test(new ItemStack(Items.BLAZE_POWDER)));
			System.out.println(ingredient.test(new ItemStack(Items.ACACIA_BOAT)));
		}
	}
}
