package mod.vemerion.wizardstaff.datagen;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.BiConsumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Helper.Helper;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.Magics;
import mod.vemerion.wizardstaff.init.ModEntities;
import mod.vemerion.wizardstaff.init.ModItems;
import mod.vemerion.wizardstaff.init.ModMagics;
import mod.vemerion.wizardstaff.init.ModSounds;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Effects;
import net.minecraft.tags.ITag;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.Tags;

public class MagicProvider implements IDataProvider {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final Logger LOGGER = LogManager.getLogger();

	protected final DataGenerator generator;
	private final String modid;

	public MagicProvider(DataGenerator generator, String modid) {
		this.generator = generator;
		this.modid = modid;
	}

	@Override
	public void act(DirectoryCache cache) throws IOException {
		Path path = generator.getOutputFolder();
		registerMagics((magic, s) -> {
			ResourceLocation key = magic.getRegistryName();
			String name = s == null || s.isEmpty() ? key.getPath() : s;
			saveMagic(cache, magic.write(),
					path.resolve("data/" + modid + "/" + Magics.FOLDER_NAME + "/" + name + ".json"));
		});
	}

	private void saveMagic(DirectoryCache cache, JsonObject json, Path path) {
		try {
			String content = GSON.toJson((JsonElement) json);
			String hash = HASH_FUNCTION.hashUnencodedChars(content).toString();
			if (!Objects.equals(cache.getPreviousHash(path), hash) || !Files.exists(path)) {
				Files.createDirectories(path.getParent());

				try (BufferedWriter writer = Files.newBufferedWriter(path)) {
					writer.write(content);
				}
			}

			cache.recordHash(path, hash);
		} catch (IOException e) {
			LOGGER.error("Couldn't save magic {}", path, e);
		}

	}

	protected void registerMagics(BiConsumer<Magic, String> c) {
		c.accept(ModMagics.BLAZE_POWDER_MAGIC.create().setParams(0.3f, -1, ing(Items.BLAZE_POWDER)), "");
		c.accept(ModMagics.BLUE_DYE_MAGIC.create().setParams(30, 50, ing(Tags.Items.DYES_BLUE)), "");
		c.accept(ModMagics.BOOKSHELF_MAGIC.create().setParams(0.8f, -1, ing(Items.BOOKSHELF)), "");
		c.accept(ModMagics.TRANSMUTATION_MAGIC.create().setAdditionalParams(Items.EXPERIENCE_BOTTLE, SoundEvents.BLOCK_BREWING_STAND_BREW).setParams(12, 15, ing(Items.GLASS_BOTTLE)), "bottle_magic");
		c.accept(ModMagics.BRICKS_MAGIC.create().setParams(50, 30, ing(Items.BRICKS)), "");
		c.accept(ModMagics.BUILDER_MAGIC.create().setAdditionalParams(new ResourceLocation(Main.MODID, "wizard_home"), Direction.SOUTH, new BlockPos(3, 3, 3), new BlockPos(0, 0, 5)).setParams(400, 80, ing(Items.COBBLESTONE)),"");
		c.accept(ModMagics.CARVED_PUMPKIN_MAGIC.create().setParams(50, 40, ing(Items.CARVED_PUMPKIN)), "");
		c.accept(ModMagics.CLOCK_MAGIC.create().setParams(1, -1, ing(Items.CLOCK)), "");
		c.accept(ModMagics.DEAGE_MAGIC.create().setParams(30, 30, ing(Items.WHEAT_SEEDS)), "");
		magicArmor(c, Items.GOLDEN_BOOTS, ModItems.DRUID_BOOTS);
		magicArmor(c, Items.GOLDEN_CHESTPLATE, ModItems.DRUID_CHESTPLATE);
		magicArmor(c, Items.GOLDEN_HELMET, ModItems.DRUID_HELMET);
		magicArmor(c, Items.GOLDEN_LEGGINGS, ModItems.DRUID_LEGGINGS);
		magicArmor(c, Items.IRON_BOOTS, ModItems.WARLOCK_BOOTS);
		magicArmor(c, Items.IRON_CHESTPLATE, ModItems.WARLOCK_CHESTPLATE);
		magicArmor(c, Items.IRON_HELMET, ModItems.WARLOCK_HELMET);
		magicArmor(c, Items.IRON_LEGGINGS, ModItems.WARLOCK_LEGGINGS);
		magicArmor(c, Items.LEATHER_BOOTS, ModItems.WIZARD_BOOTS);
		magicArmor(c, Items.LEATHER_CHESTPLATE, ModItems.WIZARD_CHESTPLATE);
		magicArmor(c, Items.LEATHER_HELMET, ModItems.WIZARD_HAT);
		magicArmor(c, Items.LEATHER_LEGGINGS, ModItems.WIZARD_LEGGINGS);
		c.accept(ModMagics.EGG_MAGIC.create().setAdditionalParams(ImmutableSet.of(new ResourceLocation("iceandfire", "fire_dragon"), new ResourceLocation("iceandfire", "ice_dragon"), new ResourceLocation("iceandfire", "lightning_dragon"))).setParams(200, 40, ing(Items.EGG)), "");
		c.accept(ModMagics.ELYTRA_MAGIC.create().setParams(1, -1, ing(Items.ELYTRA)), "");
		c.accept(ModMagics.FEATHER_MAGIC.create().setParams(0.05f, -1, ing(Items.FEATHER)), "");
		c.accept(ModMagics.GHAST_TEAR_MAGIC.create().setParams(0.5f, -1, ing(Items.GHAST_TEAR)), "");
		c.accept(ModMagics.GLOWSTONE_DUST_MAGIC.create().setParams(40, 40, ing(Tags.Items.DUSTS_GLOWSTONE)), "");
		c.accept(ModMagics.TRANSFORM_BLOCK_MAGIC.create().setAdditionalParams(Blocks.STONE, Blocks.GOLD_ORE).setParams(50, 25, ing(Tags.Items.INGOTS_GOLD)), "gold_magic");
		c.accept(ModMagics.GOLD_NUGGET_MAGIC.create().setParams(50, 20, ing(Tags.Items.NUGGETS_GOLD)), "");
		c.accept(ModMagics.GRAPPLING_HOOK_MAGIC.create().setParams(5, -1, ing(Items.FISHING_ROD)), "");
		c.accept(ModMagics.JUKEBOX_MAGIC.create().setParams(6, -1, ing(Items.JUKEBOX)), "");
		c.accept(ModMagics.LODESTONE_MAGIC.create().setAdditionalParams(Blocks.LODESTONE).setParams(500, 120, ing(Items.LODESTONE)), "");
		c.accept(ModMagics.MAP_MAGIC.create().setParams(30, 40, ing(Items.MAP)), "");
		c.accept(ModMagics.MUSHROOM_CLOUD_MAGIC.create().setParams(50, 40, ing(Items.MYCELIUM)), "");
		c.accept(ModMagics.NETHER_BRICK_MAGIC.create().setParams(60, 20, ing(Tags.Items.INGOTS_NETHER_BRICK)), "");
		c.accept(ModMagics.TRANSMUTATION_MAGIC.create().setAdditionalParams(ModItems.NETHER_WIZARD_STAFF, ModSounds.POOF).setParams(100, 40, ing(Tags.Items.INGOTS_NETHERITE)), "netherite_ingot_magic");
		c.accept(ModMagics.NETHERRACK_MAGIC.create().setParams(3, -1, ing(Items.NETHERRACK)), "");
		c.accept(ModMagics.OBSIDIAN_MAGIC.create().setParams(100, 60, ing(Items.OBSIDIAN)), "");
		c.accept(ModMagics.PILLAR_MAGIC.create().setAdditionalParams(Blocks.DIRT).setParams(0.5f, -1, ing(Items.DIRT)), "");
		c.accept(ModMagics.PORTABLE_CRAFTING_MAGIC.create().setParams(10, 10, ing(Items.CRAFTING_TABLE)), "");
		c.accept(ModMagics.POTION_MAGIC.create().setAdditionalParams(2, 600, 2, Effects.HASTE, true, SoundEvents.BLOCK_BREWING_STAND_BREW).setParams(25, 25, ing(Items.WOODEN_PICKAXE)), "");
		c.accept(ModMagics.SHULKER_BULLET_MAGIC.create().setParams(20, 25, ing(Items.SHULKER_SHELL)), "");
		c.accept(ModMagics.SMELTING_MAGIC.create().setAdditionalParams(IRecipeType.SMELTING, 20, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, Blocks.FURNACE.getTranslationKey()).setParams(1, -1, ing(Items.FURNACE)), "");
		c.accept(ModMagics.SOUL_SAND_MAGIC.create().setParams(40, 40, ing(Items.SOUL_SAND)), "");
		c.accept(ModMagics.SMELTING_MAGIC.create().setAdditionalParams(IRecipeType.STONECUTTING, 2, SoundEvents.UI_STONECUTTER_TAKE_RESULT, Blocks.STONECUTTER.getTranslationKey()).setParams(0.1f, -1, ing(Items.STONECUTTER)), "stonecutting_magic");
		c.accept(ModMagics.SURFACE_MAGIC.create().setParams(0.5f, 20, ing(Items.STONE)), "");
		c.accept(ModMagics.BUCKET_MAGIC.create().setAdditionalParams((BucketItem) Items.WATER_BUCKET).setParams(5, 10, ing(Items.WATER_BUCKET)), "water_bucket_magic");
		c.accept(ModMagics.PROJECTILE_MAGIC.create().setAdditionalParams(ModEntities.MAGIC_WITHER_SKULL, ModSounds.SKELETON, 0.5f).setParams(20, 15, ing(Items.WITHER_SKELETON_SKULL)), "wither_skull_magic");
		c.accept(ModMagics.PROJECTILE_MAGIC.create().setAdditionalParams(ModEntities.WIZARD_HAT, ModSounds.CLOTH, 1).setParams(20, 25, ing(ModItems.WIZARD_HAT)), "wizard_hat_throw_magic");
		c.accept(ModMagics.WIZARD_STAFF_MAGIC.create().setParams(0, 20, ing(ModItems.WIZARD_STAFF)), "");
		c.accept(ModMagics.WRITABLE_BOOK_MAGIC.create().setAdditionalParams(ImmutableList.of("All that glitters is gold", "Fear the old blood", "Every fleeing man must be caught. Every secret must be unearthed. Such is the conceit of the self-proclaimed seeker of truth.", "What we do in life echoes in eternity", "STEVEN LIVES", "What we've got here is... failure to communicate", "All those moments will be lost in time, like tears in rain", "A wizard is never late, nor is he early. He arrives precisely when he means to.")).setParams(10, 20, ing(Items.WRITABLE_BOOK)), "");
		c.accept(ModMagics.PUSH_BLOCK_MAGIC.create().setAdditionalParams(Blocks.SPAWNER).setParams(15, 25, ing(Items.MOSSY_COBBLESTONE)), "push_spawner_magic");
		c.accept(ModMagics.REVERT_POSITION_MAGIC.create().setParams(15, 15, ing(Items.CHORUS_FRUIT)), "");
		c.accept(ModMagics.REMOVE_FLUID_MAGIC.create().setAdditionalParams(Fluids.WATER).setParams(0.2f, 10, ing(Items.SPONGE)), "remove_water_magic");
		c.accept(ModMagics.PUSH_BUTTON_MAGIC.create().setParams(1, -1, ing(Items.STONE_BUTTON)), "");
		c.accept(ModMagics.NAME_TAG_MAGIC.create().setAdditionalParams(ImmutableList.of("ig", "nite", "syl", "la", "bles", "di", "vide", "un", "ex", "am", "ples", "dif", "fer", "ence", "re", "main", "der")).setParams(10, 20, ing(Items.NAME_TAG)), "");
		c.accept(ModMagics.LOCATE_SPAWN_MAGIC.create().setParams(25, 20, ing(Items.COMPASS)), "");
		c.accept(ModMagics.TRANSFORM_ENTITY_MAGIC.create().setAdditionalParams(EntityType.COW, EntityType.MOOSHROOM, Helper.color(200, 100, 100, 255)).setParams(90, 25, ing(Items.BROWN_MUSHROOM)), "cow_to_mooshroom_magic");
		c.accept(ModMagics.INVENTORY_MAGIC.create().setParams(10, 10, ing(Items.CHEST)), "");
		c.accept(ModMagics.ENDER_CHEST_MAGIC.create().setParams(10, 10, ing(Items.ENDER_CHEST)), "");
		c.accept(ModMagics.DEFLECT_PROJECTILE_MAGIC.create().setAdditionalParams(ImmutableSet.of()).setParams(0.8f, -1, ing(Items.SHIELD)), "");
		c.accept(ModMagics.REPAIR_ARMOR_MAGIC.create().setAdditionalParams(1).setParams(1, -1, ing(Items.ANVIL)), "");
		c.accept(ModMagics.SUMMON_ENTITY_MAGIC.create().setAdditionalParams(ModEntities.MAGIC_VEX, 3).setParams(40, 40, ing(Items.TOTEM_OF_UNDYING)), "summon_friendly_vex_magic");
	}
	
	private void magicArmor(BiConsumer<Magic, String> c, Item from, Item created) {
		c.accept(ModMagics.TRANSMUTATION_MAGIC.create().setAdditionalParams(created, ModSounds.PLOP).setParams(30, 20, ing(from)), created.getRegistryName().getPath() + "_fashion_magic");
	}

	private Ingredient ing(Item item) {
		return Ingredient.fromItems(item);
	}

	private Ingredient ing(ITag<Item> tag) {
		return Ingredient.fromTag(tag);
	}

	@Override
	public String getName() {
		return "Magics";
	}
}
