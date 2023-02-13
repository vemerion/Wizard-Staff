package mod.vemerion.wizardstaff.datagen;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Helper.Helper;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.Magics;
import mod.vemerion.wizardstaff.Magic.RegistryMatch;
import mod.vemerion.wizardstaff.init.ModEntities;
import mod.vemerion.wizardstaff.init.ModItems;
import mod.vemerion.wizardstaff.init.ModMagics;
import mod.vemerion.wizardstaff.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

public class MagicProvider implements DataProvider {
	protected final PackOutput packOutput;
	private final String modid;

	public MagicProvider(PackOutput packOutput, String modid) {
		this.packOutput = packOutput;
		this.modid = modid;
	}

	@Override
	public CompletableFuture<?> run(CachedOutput cache) {
		Path folder = packOutput.getOutputFolder();
		var list = new ArrayList<CompletableFuture<?>>();
		registerMagics(magic -> {
			Path path = folder
					.resolve("data/" + modid + "/" + Magics.FOLDER_NAME + "/" + magic.getName().getPath() + ".json");
			list.add(DataProvider.saveStable(cache, magic.write(), path));
		});
		return CompletableFuture.allOf(list.toArray((length) -> {
			return new CompletableFuture[length];
		}));
	}

	// @formatter:off
	protected void registerMagics(Consumer<Magic> c) {
		c.accept(create(ModMagics.BLAZE_POWDER_MAGIC.get()).setParams(0.3f, -1, ing(Items.BLAZE_POWDER)));
		c.accept(create(ModMagics.BLUE_DYE_MAGIC.get()).setParams(30, 50, ing(Tags.Items.DYES_BLUE)));
		c.accept(create(ModMagics.BOOKSHELF_MAGIC.get()).setAdditionalParams(Items.BOOK, ModSounds.PAGE_TURN.get()).setParams(0.8f, -1, ing(Items.BOOKSHELF)));
		c.accept(create(ModMagics.TRANSMUTATION_MAGIC.get(), "bottle_magic").setAdditionalParams(Items.EXPERIENCE_BOTTLE, SoundEvents.BREWING_STAND_BREW).setParams(12, 15, ing(Items.GLASS_BOTTLE)));
		c.accept(create(ModMagics.BRICKS_MAGIC.get()).setParams(50, 30, ing(Items.BRICKS)));
		c.accept(create(ModMagics.BUILDER_MAGIC.get()).setAdditionalParams(new ResourceLocation(Main.MODID, "wizard_home"), Direction.SOUTH, new BlockPos(3, 3, 3), new BlockPos(0, 0, 5)).setParams(400, 80, ing(Items.COBBLESTONE)));
		c.accept(create(ModMagics.CARVED_PUMPKIN_MAGIC.get()).setParams(50, 40, ing(Items.CARVED_PUMPKIN)));
		c.accept(create(ModMagics.CLOCK_MAGIC.get()).setParams(1, -1, ing(Items.CLOCK)));
		c.accept(create(ModMagics.DEAGE_MAGIC.get()).setParams(30, 30, ing(Items.WHEAT_SEEDS)));
		magicArmor(c, Items.GOLDEN_BOOTS, ModItems.DRUID_BOOTS.get());
		magicArmor(c, Items.GOLDEN_CHESTPLATE, ModItems.DRUID_CHESTPLATE.get());
		magicArmor(c, Items.GOLDEN_HELMET, ModItems.DRUID_HELMET.get());
		magicArmor(c, Items.GOLDEN_LEGGINGS, ModItems.DRUID_LEGGINGS.get());
		magicArmor(c, Items.IRON_BOOTS, ModItems.WARLOCK_BOOTS.get());
		magicArmor(c, Items.IRON_CHESTPLATE, ModItems.WARLOCK_CHESTPLATE.get());
		magicArmor(c, Items.IRON_HELMET, ModItems.WARLOCK_HELMET.get());
		magicArmor(c, Items.IRON_LEGGINGS, ModItems.WARLOCK_LEGGINGS.get());
		magicArmor(c, Items.LEATHER_BOOTS, ModItems.WIZARD_BOOTS.get());
		magicArmor(c, Items.LEATHER_CHESTPLATE, ModItems.WIZARD_CHESTPLATE.get());
		magicArmor(c, Items.LEATHER_HELMET, ModItems.WIZARD_HAT.get());
		magicArmor(c, Items.LEATHER_LEGGINGS, ModItems.WIZARD_LEGGINGS.get());
		c.accept(create(ModMagics.EGG_MAGIC.get()).setAdditionalParams(ImmutableSet.of(new ResourceLocation("iceandfire", "fire_dragon"), new ResourceLocation("iceandfire", "ice_dragon"), new ResourceLocation("iceandfire", "lightning_dragon"))).setParams(200, 40, ing(Items.EGG)));
		c.accept(create(ModMagics.ELYTRA_MAGIC.get()).setParams(1, -1, ing(Items.ELYTRA)));
		c.accept(create(ModMagics.FEATHER_MAGIC.get()).setParams(0.05f, -1, ing(Items.FEATHER)));
		c.accept(create(ModMagics.GHAST_TEAR_MAGIC.get()).setParams(0.5f, -1, ing(Items.GHAST_TEAR)));
		c.accept(create(ModMagics.GLOWSTONE_DUST_MAGIC.get()).setParams(40, 40, ing(Tags.Items.DUSTS_GLOWSTONE)));
		c.accept(create(ModMagics.TRANSFORM_BLOCK_MAGIC.get(), "gold_magic").setAdditionalParams(Blocks.STONE, Blocks.GOLD_ORE).setParams(50, 25, ing(Tags.Items.INGOTS_GOLD)));
		c.accept(create(ModMagics.GOLD_NUGGET_MAGIC.get()).setParams(50, 20, ing(Tags.Items.NUGGETS_GOLD)));
		c.accept(create(ModMagics.GRAPPLING_HOOK_MAGIC.get()).setParams(5, -1, ing(Items.FISHING_ROD)));
		c.accept(create(ModMagics.JUKEBOX_MAGIC.get()).setAdditionalParams(ImmutableSet.of(ForgeRegistries.ENTITY_TYPES.getKey(EntityType.ENDER_DRAGON), ForgeRegistries.ENTITY_TYPES.getKey(EntityType.WITHER), ForgeRegistries.ENTITY_TYPES.getKey(EntityType.PLAYER)), 4, SoundEvents.MUSIC_DISC_STAL).setParams(6, -1, ing(Items.JUKEBOX)));
		c.accept(create(ModMagics.LODESTONE_MAGIC.get()).setAdditionalParams(Blocks.LODESTONE).setParams(500, 120, ing(Items.LODESTONE)));
		c.accept(create(ModMagics.MAP_MAGIC.get()).setParams(30, 40, ing(Items.MAP)));
		c.accept(create(ModMagics.MUSHROOM_CLOUD_MAGIC.get()).setParams(50, 40, ing(Items.MYCELIUM)));
		c.accept(create(ModMagics.NETHER_BRICK_MAGIC.get()).setParams(60, 20, ing(Tags.Items.INGOTS_NETHER_BRICK)));
		c.accept(create(ModMagics.TRANSMUTATION_MAGIC.get(), "netherite_ingot_magic").setAdditionalParams(ModItems.NETHER_WIZARD_STAFF.get(), ModSounds.POOF.get()).setParams(100, 40, ing(Tags.Items.INGOTS_NETHERITE)));
		c.accept(create(ModMagics.NETHERRACK_MAGIC.get()).setParams(3, -1, ing(Items.NETHERRACK)));
		c.accept(create(ModMagics.OBSIDIAN_MAGIC.get()).setAdditionalParams(Level.NETHER, new ResourceLocation(Main.MODID, "textures/entity/nether_portal.png")).setParams(100, 60, ing(Items.OBSIDIAN)));
		c.accept(create(ModMagics.PILLAR_MAGIC.get()).setAdditionalParams(Blocks.DIRT).setParams(0.5f, -1, ing(Items.DIRT)));
		c.accept(create(ModMagics.PORTABLE_CRAFTING_MAGIC.get()).setParams(10, 10, ing(Items.CRAFTING_TABLE)));
		c.accept(create(ModMagics.POTION_MAGIC.get(), "haste_magic").setAdditionalParams(2, 600, 2, MobEffects.DIG_SPEED, true, SoundEvents.BREWING_STAND_BREW).setParams(25, 25, ing(Items.WOODEN_PICKAXE)));
		c.accept(create(ModMagics.SHULKER_BULLET_MAGIC.get()).setParams(20, 25, ing(Items.SHULKER_SHELL)));
		c.accept(create(ModMagics.SMELTING_MAGIC.get()).setAdditionalParams(RecipeType.SMELTING, 20, SoundEvents.FURNACE_FIRE_CRACKLE, Blocks.FURNACE.getDescriptionId(), 1).setParams(1, -1, ing(Items.FURNACE)));
		c.accept(create(ModMagics.SMELTING_MAGIC.get(), "stonecutting_magic").setAdditionalParams(RecipeType.STONECUTTING, 2, SoundEvents.UI_STONECUTTER_TAKE_RESULT, Blocks.STONECUTTER.getDescriptionId(), 1).setParams(0.1f, -1, ing(Items.STONECUTTER)));
		c.accept(create(ModMagics.SURFACE_MAGIC.get()).setParams(0.5f, 20, ing(Items.STONE)));
		c.accept(create(ModMagics.BUCKET_MAGIC.get(), "water_bucket_magic").setAdditionalParams((BucketItem) Items.WATER_BUCKET).setParams(5, 10, ing(Items.WATER_BUCKET)));
		c.accept(create(ModMagics.PROJECTILE_MAGIC.get(), "wither_skull_magic").setAdditionalParams(ModEntities.MAGIC_WITHER_SKULL.get(), ModSounds.SKELETON.get(), 0.5f).setParams(20, 15, ing(Items.WITHER_SKELETON_SKULL)));
		c.accept(create(ModMagics.PROJECTILE_MAGIC.get(), "wizard_hat_throw_magic").setAdditionalParams(ModEntities.WIZARD_HAT.get(), ModSounds.CLOTH.get(), 1).setParams(20, 25, ing(ModItems.WIZARD_HAT.get())));
		c.accept(create(ModMagics.WIZARD_STAFF_MAGIC.get()).setParams(0, 20, ing(ModItems.WIZARD_STAFF.get())));
		c.accept(create(ModMagics.WRITABLE_BOOK_MAGIC.get()).setAdditionalParams(ImmutableList.of("All that glitters is gold", "Fear the old blood", "Every fleeing man must be caught. Every secret must be unearthed. Such is the conceit of the self-proclaimed seeker of truth.", "What we do in life echoes in eternity", "STEVEN LIVES", "What we've got here is... failure to communicate", "All those moments will be lost in time, like tears in rain", "A wizard is never late, nor is he early. He arrives precisely when he means to.")).setParams(10, 20, ing(Items.WRITABLE_BOOK)));
		c.accept(create(ModMagics.PUSH_BLOCK_MAGIC.get(), "push_spawner_magic").setAdditionalParams(Blocks.SPAWNER).setParams(15, 25, ing(Items.MOSSY_COBBLESTONE)));
		c.accept(create(ModMagics.REVERT_POSITION_MAGIC.get()).setParams(15, 15, ing(Items.CHORUS_FRUIT)));
		c.accept(create(ModMagics.REMOVE_FLUID_MAGIC.get(), "remove_water_magic").setAdditionalParams(new RegistryMatch<>(ForgeRegistries.FLUIDS, FluidTags.WATER)).setParams(0.2f, 10, ing(Items.SPONGE)));
		c.accept(create(ModMagics.PUSH_BUTTON_MAGIC.get()).setParams(1, -1, ing(Items.STONE_BUTTON)));
		c.accept(create(ModMagics.NAME_TAG_MAGIC.get()).setAdditionalParams(ImmutableList.of("ig", "nite", "syl", "la", "bles", "di", "vide", "un", "ex", "am", "ples", "dif", "fer", "ence", "re", "main", "der")).setParams(10, 20, ing(Items.NAME_TAG)));
		c.accept(create(ModMagics.LOCATE_SPAWN_MAGIC.get()).setParams(25, 20, ing(Items.COMPASS)));
		c.accept(create(ModMagics.TRANSFORM_ENTITY_MAGIC.get(), "cow_to_mooshroom_magic").setAdditionalParams(new RegistryMatch<>(ForgeRegistries.ENTITY_TYPES, EntityType.COW), EntityType.MOOSHROOM, Helper.color(200, 100, 100, 255)).setParams(90, 25, ing(Items.BROWN_MUSHROOM)));
		c.accept(create(ModMagics.INVENTORY_MAGIC.get()).setParams(10, 10, ing(Items.CHEST)));
		c.accept(create(ModMagics.ENDER_CHEST_MAGIC.get()).setParams(10, 10, ing(Items.ENDER_CHEST)));
		c.accept(create(ModMagics.DEFLECT_PROJECTILE_MAGIC.get()).setAdditionalParams(ImmutableSet.of()).setParams(0.8f, -1, ing(Items.SHIELD)));
		c.accept(create(ModMagics.REPAIR_ARMOR_MAGIC.get()).setAdditionalParams(1).setParams(1, -1, ing(Items.ANVIL)));
		c.accept(create(ModMagics.SUMMON_ENTITY_MAGIC.get(), "summon_friendly_vex_magic").setAdditionalParams(3).setAdditionalParams(ModEntities.MAGIC_VEX.get(), ModSounds.BELL.get()).setParams(40, 40, ing(Items.TOTEM_OF_UNDYING)));
		c.accept(create(ModMagics.MASS_HARVEST_MAGIC.get(), "chop_tree_magic").setAdditionalParams(new RegistryMatch<>(ForgeRegistries.BLOCKS, BlockTags.LOGS), 40).setParams(2, 40, ing(Items.DIAMOND_AXE)));
		c.accept(create(ModMagics.MASS_HARVEST_MAGIC.get(), "clear_leaves_magic").setAdditionalParams(new RegistryMatch<>(ForgeRegistries.BLOCKS, BlockTags.LEAVES), 50).setParams(0.1f, 20, ing(Items.SHEARS)));
		c.accept(create(ModMagics.FORCE_ENTITY_MAGIC.get(), "item_magnet_magic").setAdditionalParams(new RegistryMatch<>(ForgeRegistries.ENTITY_TYPES, EntityType.ITEM), 0.2f).setParams(0.1f, -1, ing(Items.IRON_BLOCK)));
		c.accept(create(ModMagics.FORCE_ENTITY_MAGIC.get(), "repel_zombie_magic").setAdditionalParams(new RegistryMatch<>(ForgeRegistries.ENTITY_TYPES, EntityType.ZOMBIE), -0.1f).setParams(0.5f, -1, ing(Items.GLISTERING_MELON_SLICE)));
		c.accept(create(ModMagics.SWAP_POSITION_MAGIC.get()).setAdditionalParams(ImmutableSet.of(ForgeRegistries.ENTITY_TYPES.getKey(EntityType.ENDER_DRAGON), ForgeRegistries.ENTITY_TYPES.getKey(EntityType.WITHER)), 5).setParams(25, 20, ing(Items.ARMOR_STAND)));
		c.accept(create(ModMagics.SWAP_HEALTH_FOOD_MAGIC.get()).setParams(20, 20, ing(Items.COOKED_BEEF)));
		c.accept(create(ModMagics.SWAP_TRADE_MAGIC.get()).setAdditionalParams(ing(Items.BELL)).setParams(400, 40, ing(Items.EMERALD)));
		c.accept(create(ModMagics.TRANSFORM_BLOCK_MAGIC.get(), "infest_stone_magic").setAdditionalParams(Blocks.STONE, Blocks.INFESTED_STONE).setParams(20, 20, ing(Tags.Items.DYES_LIGHT_GRAY)));
		c.accept(create(ModMagics.REPAIR_OFFHAND_MAGIC.get()).setAdditionalParams(1).setParams(1, -1, ing(Items.SMITHING_TABLE)));
		c.accept(create(ModMagics.WALL_CLIMB_MAGIC.get()).setAdditionalParams(0.2f).setParams(0.05f, -1, ing(Items.STRING)));
		c.accept(create(ModMagics.COBWEB_MAGIC.get()).setParams(10, 15, ing(Items.COBWEB)));
		c.accept(create(ModMagics.BEE_MAGIC.get()).setParams(20, 20, ing(Items.HONEYCOMB)));
		c.accept(create(ModMagics.SHAPED_CREATE_ENTITY_MAGIC.get(), "evoker_fangs_magic").setAdditionalParams(circlesShape()).setAdditionalParams(EntityType.EVOKER_FANGS, ModSounds.BELL.get()).setParams(30, 15, ing(Items.LAPIS_LAZULI)));
		c.accept(create(ModMagics.SHAPED_CREATE_ENTITY_MAGIC.get(), "soul_sand_magic").setAdditionalParams(soulSandMagicShape()).setAdditionalParams(ModEntities.MAGIC_SOUL_SAND_ARM.get(), ModSounds.PUMPKIN_MAGIC.get()).setParams(40, 40, ing(Items.SOUL_SAND)));
		c.accept(create(ModMagics.SHAPED_CREATE_ENTITY_MAGIC.get(), "tnt_magic").setAdditionalParams(tntShape()).setAdditionalParams(EntityType.TNT, ModSounds.CLOCK.get()).setParams(50, 15, ing(Items.TNT)));
		c.accept(create(ModMagics.MINING_MAGIC.get()).setAdditionalParams(new RegistryMatch<>(ForgeRegistries.BLOCKS, Tags.Blocks.ORES), 20).setParams(1, 10, ing(Items.DIAMOND_PICKAXE)));
		c.accept(create(ModMagics.SUMMON_ENTITY_MAGIC.get(), "summon_lightning_magic").setAdditionalParams(1).setAdditionalParams(EntityType.LIGHTNING_BOLT, SoundEvents.LIGHTNING_BOLT_IMPACT).setParams(40, 60, ing(Items.LIGHTNING_ROD)));
		c.accept(create(ModMagics.MOUNT_MAGIC.get(), "mount_goat_magic").setAdditionalParams(new RegistryMatch<>(ForgeRegistries.ENTITY_TYPES, EntityType.GOAT)).setParams(0.2f, 60, ing(Items.WHEAT)));
		c.accept(create(ModMagics.X_RAY_MAGIC.get()).setAdditionalParams(15, new RegistryMatch<>(ForgeRegistries.BLOCKS, Tags.Blocks.ORES)).setParams(3, -1, ing(Items.DIAMOND_BLOCK)));
		c.accept(create(ModMagics.LIGHT_MAGIC.get()).setParams(5, -1, ing(Items.GLOWSTONE)));
		c.accept(create(ModMagics.ZOOM_MAGIC.get()).setAdditionalParams(3).setParams(0.1f, -1, ing(Items.SPYGLASS)));
		c.accept(create(ModMagics.SUMMON_ENTITY_MAGIC.get(), "summon_mining_vex_magic").setAdditionalParams(3).setAdditionalParams(ModEntities.MAGIC_MINING_VEX.get(), ModSounds.BELL.get(), jsonWithSingleProp("minable", Tags.Blocks.ORES.location().toString())).setParams(60, 55, ing(Items.GOLDEN_PICKAXE)));
		c.accept(create(ModMagics.PLACE_LIGHT_RANDOM_MAGIC.get()).setAdditionalParams(10, 4, (BlockItem) Items.TORCH).setParams(0.4f, -1, ing(Items.TORCH)));
		c.accept(create(ModMagics.QUIET_MAGIC.get()).setParams(0.03f, -1, ing(Items.WHITE_WOOL)));
		c.accept(create(ModMagics.DEATH_TELEPORT_MAGIC.get()).setParams(100, 30, ing(Items.RECOVERY_COMPASS)));
		c.accept(create(ModMagics.GOAT_RAM_MAGIC.get()).setAdditionalParams(7).setParams(10, 30, ing(Items.GOAT_HORN)));
		c.accept(create(ModMagics.FROG_EAT_MAGIC.get()).setAdditionalParams(7).setParams(20, 30, ing(Items.ROTTEN_FLESH)));
		c.accept(create(ModMagics.ANGER_WARDEN_MAGIC.get()).setParams(100, 50, ing(Items.SCULK_SHRIEKER)));
		c.accept(create(ModMagics.LISTEN_MAGIC.get()).setParams(0.04f, -1, ing(Items.SPECTRAL_ARROW)));
		c.accept(create(ModMagics.CYCLE_VARIANT_MAGIC.get()).setParams(5, 10, ing(Items.MANGROVE_PROPAGULE)));
		c.accept(create(ModMagics.MISSILE_MAGIC.get()).setAdditionalParams(4, 7, Items.AMETHYST_SHARD).setParams(4, 20 * 10, ing(Items.AMETHYST_SHARD)));
	}
	// @formatter:on

	private void magicArmor(Consumer<Magic> c, Item from, Item created) {
		c.accept(create(ModMagics.TRANSMUTATION_MAGIC.get(), ForgeRegistries.ITEMS.getKey(created).getPath() + "_fashion_magic")
				.setAdditionalParams(created, ModSounds.PLOP.get()).setParams(30, 20, ing(from)));
	}

	private <T extends Magic> T create(MagicType<T> type, String name) {
		return type.create(name == null || name.isEmpty() ? ModMagics.getRegistry().getKey(type) : rl(name));
	}

	private <T extends Magic> T create(MagicType<T> type) {
		return create(type, "");
	}

	private ResourceLocation rl(String name) {
		return new ResourceLocation(Main.MODID, name);
	}

	private Ingredient ing(Item item) {
		return Ingredient.of(item);
	}

	private Ingredient ing(TagKey<Item> tag) {
		return Ingredient.of(tag);
	}

	private JsonObject jsonWithSingleProp(String key, String property) {
		var json = new JsonObject();
		json.addProperty(key, property);
		return json;
	}

	@Override
	public String getName() {
		return "Magics";
	}

	private List<String> shape(String... rows) {
		List<String> shape = new ArrayList<>();
		for (String s : rows) {
			shape.add(s);
		}
		return shape;
	}

	private List<String> circlesShape() {
		return shape("   x   ", " x   x ", "   x   ", "x xpx x", "   x   ", " x   x ", "   x   ");
	}

	private List<String> soulSandMagicShape() {
		return shape(" x ", "x  ", " x ", " x ", "  x", " x ", "  x", " x ", " p ");
	}

	private List<String> tntShape() {
		return shape("x", "x", "x", " ", "p");
	}
}
