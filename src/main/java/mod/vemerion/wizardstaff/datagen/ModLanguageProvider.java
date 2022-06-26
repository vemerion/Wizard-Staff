package mod.vemerion.wizardstaff.datagen;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.init.ModBlocks;
import mod.vemerion.wizardstaff.init.ModEntities;
import mod.vemerion.wizardstaff.init.ModItems;
import mod.vemerion.wizardstaff.init.ModMagics;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {

	public ModLanguageProvider(DataGenerator gen) {
		super(gen, Main.MODID, "en_us");
	}

	// @formatter:off
	@Override
	protected void addTranslations() {
		add("itemGroup.wizardstaff", "Wizard Staff");

		add(ModItems.WARLOCK_HELMET, "Warlock Cowl");
		add(ModItems.WARLOCK_CHESTPLATE, "Warlock Robe");
		add(ModItems.WARLOCK_LEGGINGS, "Warlock Chainmail");
		add(ModItems.WARLOCK_BOOTS, "Warlock Boots");
		add(ModItems.DRUID_HELMET, "Druid Skull");
		add(ModItems.DRUID_CHESTPLATE, "Druid Robe");
		add(ModItems.DRUID_LEGGINGS, "Druid Pants");
		add(ModItems.DRUID_BOOTS, "Druid Boots");
		add(ModItems.WIZARD_HAT, "Wizard Hat");
		add(ModItems.WIZARD_CHESTPLATE, "Wizard Robe");
		add(ModItems.WIZARD_LEGGINGS, "Wizard Skirt");
		add(ModItems.WIZARD_BOOTS, "Wizard Slippers");
		add(ModItems.WIZARD_STAFF, "Wizard Staff");
		add(ModItems.NETHER_WIZARD_STAFF, "Nether Wizard Staff");

		add(ModEntities.GRAPPLING_HOOK, "Grappling Hook");
		add(ModEntities.MAGIC_SOUL_SAND_ARM, "Magic Soul Sand Arm");
		add(ModEntities.MAGIC_WITHER_SKULL, "Magic Wither Skull");
		add(ModEntities.MUSHROOM_CLOUD, "Mushroom Cloud");
		add(ModEntities.NETHER_PORTAL, "Magic Nether Portal");
		add(ModEntities.PUMPKIN_MAGIC, "Magic Pumpkin");
		add(ModEntities.WIZARD_HAT, "Wizard Hat");
		add(ModEntities.MAGIC_VEX, "Friendly Vex");

		add("item.wizardstaff.wizard_armor.description", "-10% spell cost");
		add("item.wizardstaff.druid_armor.description", "-10% spell cost");
		add("item.wizardstaff.warlock_armor.description", "-10% spell cost");

		add(ModBlocks.MAGIC_BRICKS, "Magic Bricks");

		add("death.attack.wizardstaff.magicplayer", "%1$s was magicked to death by %2$s");
		add("death.attack.wizardstaff.magicindirect", "%1$s was magicked to death by %2$s");
		add("death.attack.wizardstaff.magic", "%1$s was magicked to death");

		addGui("toggle_animations", "Toggle GUI animations");
		addGui("toggle_spellbook", "Toggle Spellbook");
		addGui("cost", "Cost");
		addGui("duration", "Duration");
		addGui("exp", "exp");
		addGui("infinity", "\u221E");
		addGui("seconds", "s");
		addGui("search_hint", "Search Magic...");

		magics();

	}

	private void magics() {
		addMagicName(ModMagics.NO_MAGIC, "No Magic");
		addMagicDescription(ModMagics.NO_MAGIC, "No Magic");
		addMagicName(ModMagics.BLAZE_POWDER_MAGIC, "Flame Thrower");
		addMagicDescription(ModMagics.BLAZE_POWDER_MAGIC, "Incinerate your enemies!");		
		addMagicName(ModMagics.CARVED_PUMPKIN_MAGIC, "Pumpkin Smoke");		
		addMagicDescription(ModMagics.CARVED_PUMPKIN_MAGIC, "Create a large slow moving shadow that damages and weakens everything it touches.");	
		addMagicName(ModMagics.CLOCK_MAGIC, "Time Dilation");		
		addMagicDescription(ModMagics.CLOCK_MAGIC, "Speed up time!");		
		addMagicName(ModMagics.EGG_MAGIC, "Eggization");		
		addMagicDescription(ModMagics.EGG_MAGIC, "Shoot a purple ray of magic at entities, trapping them inside an egg.");
		addMagicName(ModMagics.ELYTRA_MAGIC, "Magic Helicopter");		
		addMagicDescription(ModMagics.ELYTRA_MAGIC, "Rotate your staff quickly enough to lift from the ground.");	
		addMagicName(ModMagics.TRANSFORM_BLOCK_MAGIC, "Transform %s into %s");		
		addMagicDescription(ModMagics.TRANSFORM_BLOCK_MAGIC, "Turn %s into a %s block.");	
		addMagicName(ModMagics.JUKEBOX_MAGIC, "Magic Music");		
		addMagicDescription(ModMagics.JUKEBOX_MAGIC, "Emit an irresistible tune from the staff, forcing enemies within %s blocks to dance.");	
		addMagicName(ModMagics.WIZARD_STAFF_MAGIC, "Staff-ception");		
		addMagicDescription(ModMagics.WIZARD_STAFF_MAGIC, "Very dangerous!");	
		addMagicName(ModMagics.WRITABLE_BOOK_MAGIC, "Arcane Knowledge");		
		addMagicDescription(ModMagics.WRITABLE_BOOK_MAGIC, "What knowledge will you unveil?");	
		addMagicName(ModMagics.OBSIDIAN_MAGIC, "Portal to '%s'");		
		addMagicDescription(ModMagics.OBSIDIAN_MAGIC, "Open a portal to the dimension '%s'. WARNING: One way only!");	
		addMagicName(ModMagics.GLOWSTONE_DUST_MAGIC, "Magic Radar");		
		addMagicDescription(ModMagics.GLOWSTONE_DUST_MAGIC, "Locate nearby entities.");	
		addMagicName(ModMagics.NETHERRACK_MAGIC, "Walk on Lava");		
		addMagicDescription(ModMagics.NETHERRACK_MAGIC, "Like Frost Walker but for lava, simple as that.");	
		addMagicName(ModMagics.PROJECTILE_MAGIC, "Launch %s");		
		addMagicDescription(ModMagics.PROJECTILE_MAGIC, "Conjure and fire a %s in front of you.");	
		addMagicName(ModMagics.GHAST_TEAR_MAGIC, "Fire Immunity");		
		addMagicDescription(ModMagics.GHAST_TEAR_MAGIC, "Use magic to enhance your crying, immediately extinguishing flames from you.");	
		addMagicName(ModMagics.NETHER_BRICK_MAGIC, "Fortress Locator");		
		addMagicDescription(ModMagics.NETHER_BRICK_MAGIC, "Use your magic to sense the direction of the nearest Nether Fortress.");	
		addMagicName(ModMagics.GOLD_NUGGET_MAGIC, "Magic Bartering");		
		addMagicDescription(ModMagics.GOLD_NUGGET_MAGIC, "Use magic compel nearby Piglins to give you their items.");	
		addMagicName(ModMagics.LODESTONE_MAGIC, "Magical Translocator");		
		addMagicDescription(ModMagics.LODESTONE_MAGIC, "Right click a %s block with the staff, setting your waypoint to that block. Then, activating the staff anywhere in the world will return you to the waypoint, consuming the block in the process.");	
		addMagicName(ModMagics.TRANSMUTATION_MAGIC, "Conjure %s");		
		addMagicDescription(ModMagics.TRANSMUTATION_MAGIC, "Transmutate the item in the staff into the item %s.");	
		addMagicName(ModMagics.BLUE_DYE_MAGIC, "Weather Controller");		
		addMagicDescription(ModMagics.BLUE_DYE_MAGIC, "Control the weather!");	
		addMagicName(ModMagics.BRICKS_MAGIC, "Portable Wall");		
		addMagicDescription(ModMagics.BRICKS_MAGIC, "Surround yourself by magic bricks, creating a safe space.");	
		addMagicName(ModMagics.GRAPPLING_HOOK_MAGIC, "Grappling Hook");		
		addMagicDescription(ModMagics.GRAPPLING_HOOK_MAGIC, "Use the staff as a grappling hook.");	
		addMagicName(ModMagics.FEATHER_MAGIC, "Slow Fall");		
		addMagicDescription(ModMagics.FEATHER_MAGIC, "Use magic to dampen the effects of gravity, slowing your fall.");	
		addMagicName(ModMagics.MUSHROOM_CLOUD_MAGIC, "Mushroom Cloud");		
		addMagicDescription(ModMagics.MUSHROOM_CLOUD_MAGIC, "Create a magic poison cloud in from of you, damaging anyone that enters it, slowly turning them into mushrooms.");	
		addMagicName(ModMagics.SHULKER_BULLET_MAGIC, "Shulker Bullet");		
		addMagicDescription(ModMagics.SHULKER_BULLET_MAGIC, "Use magic to replicate a shulker bullet, targeting a nearby entity.");	
		addMagicName(ModMagics.BUCKET_MAGIC, "Magic %s");		
		addMagicDescription(ModMagics.BUCKET_MAGIC, "Use magic to replicate the usage of a %s.");	
		addMagicName(ModMagics.PORTABLE_CRAFTING_MAGIC, "Portable Crafting");		
		addMagicDescription(ModMagics.PORTABLE_CRAFTING_MAGIC, "Channel your magic to access a full 3x3 crafting table.");	
		addMagicName(ModMagics.BOOKSHELF_MAGIC, "%s Generator");		
		addMagicDescription(ModMagics.BOOKSHELF_MAGIC, "Use your magic to generate %s.");	
		addMagicName(ModMagics.MAP_MAGIC, "Treasure Map!");		
		addMagicDescription(ModMagics.MAP_MAGIC, "Transform the item in the staff into a treasure map.");	
		addMagicName(ModMagics.DEAGE_MAGIC, "Deage Magic");		
		addMagicDescription(ModMagics.DEAGE_MAGIC, "Shoot a de-age ray at an entity, turning it into a child.");	
		addMagicName(ModMagics.SMELTING_MAGIC, "Magical %s");		
		addMagicDescription(ModMagics.SMELTING_MAGIC, "Magical item processing, replicating the functionality of the %s.");	
		addMagicName(ModMagics.PILLAR_MAGIC, "%s Pillar");		
		addMagicDescription(ModMagics.PILLAR_MAGIC, "Stand still and use the staff to automatically propel yourself upwards, while creating a %s tower below you.");	
		addMagicName(ModMagics.SURFACE_MAGIC, "Magic Surface Builder");		
		addMagicDescription(ModMagics.SURFACE_MAGIC, "Click on two blocks in the world with the staff, and then use the staff to automatically build a surface between those two blocks. Only works for axis-aligned surfaces, so no diagonals! It will build the surface using blocks from your inventory, of the same type as the block you clicked first. Costs experience based on amount of blocks placed.");	
		addMagicName(ModMagics.POTION_MAGIC, "Potion: %s %s");		
		addMagicDescription(ModMagics.POTION_MAGIC, "Apply %s %s for %s seconds to%s%s.");	
		addMagicOther(ModMagics.POTION_MAGIC, "affect_caster", " yourself%s");	
		addMagicOther(ModMagics.POTION_MAGIC, "glue", " and");	
		addMagicOther(ModMagics.POTION_MAGIC, "affect_others", " others around you");	
		addMagicName(ModMagics.BUILDER_MAGIC, "Build '%s'");		
		addMagicDescription(ModMagics.BUILDER_MAGIC, "Use the magic of the staff to construct a '%s' building in a flash of an eye.");
		
		addMagicName(ModMagics.DEFLECT_PROJECTILE_MAGIC, "Deflect Projectiles");		
		addMagicDescription(ModMagics.DEFLECT_PROJECTILE_MAGIC, "Use your staff as a protective layer around you, deflecting projectiles.");	
		addMagicName(ModMagics.ENDER_CHEST_MAGIC, "Portable Ender Chest");		
		addMagicDescription(ModMagics.ENDER_CHEST_MAGIC, "Use your staff to give you remote access to your ender storage.");	
		addMagicName(ModMagics.INVENTORY_MAGIC, "Portable Chest");		
		addMagicDescription(ModMagics.INVENTORY_MAGIC, "Gain access to magic storage.");	
		addMagicName(ModMagics.LOCATE_SPAWN_MAGIC, "Magic Compass");		
		addMagicDescription(ModMagics.LOCATE_SPAWN_MAGIC, "Channel your inner powers to gain a sense of which direction your spawn point lies.");	
		addMagicName(ModMagics.NAME_TAG_MAGIC, "Random Name Generator");		
		addMagicDescription(ModMagics.NAME_TAG_MAGIC, "Conjure a name tag with a random name.");	
		addMagicName(ModMagics.PUSH_BLOCK_MAGIC, "%s Mover");		
		addMagicDescription(ModMagics.PUSH_BLOCK_MAGIC, "Use your magic to push %s blocks.");	
		addMagicName(ModMagics.PUSH_BUTTON_MAGIC, "Long Range Button Pushing");		
		addMagicDescription(ModMagics.PUSH_BUTTON_MAGIC, "Activate buttons from a large distance.");	
		addMagicName(ModMagics.REMOVE_FLUID_MAGIC, "Fluid Remover: '%s'");		
		addMagicDescription(ModMagics.REMOVE_FLUID_MAGIC, "Use your magic to remove fluid of type '%s' around you.");	
		addMagicName(ModMagics.REPAIR_ARMOR_MAGIC, "Repair Armor");		
		addMagicDescription(ModMagics.REPAIR_ARMOR_MAGIC, "Use magic to repair the armor you are wearing.");	
		addMagicName(ModMagics.REVERT_POSITION_MAGIC, "Revert Position");		
		addMagicDescription(ModMagics.REVERT_POSITION_MAGIC, "Revert your position to where you were five seconds ago.");	
		addMagicName(ModMagics.TRANSFORM_ENTITY_MAGIC, "Transform Entity");		
		addMagicDescription(ModMagics.TRANSFORM_ENTITY_MAGIC, "Shot a transformation ray that turns %s entities into %s entities.");
		addMagicName(ModMagics.SUMMON_ENTITY_MAGIC, "Summon %s");		
		addMagicDescription(ModMagics.SUMMON_ENTITY_MAGIC, "Use your magic to summon %s %s entities.");
		
		addMagicName(ModMagics.MASS_HARVEST_MAGIC, "Mass Harvest %s");
		addMagicDescription(ModMagics.MASS_HARVEST_MAGIC, "Use your magic to harvest up to %s adjacent %s.");
		addMagicName(ModMagics.FORCE_ENTITY_MAGIC, "%s %s");
		addMagicDescription(ModMagics.FORCE_ENTITY_MAGIC, "%s %s entities with you magic.");
		addMagicOther(ModMagics.FORCE_ENTITY_MAGIC, "repel", "Repel");
		addMagicOther(ModMagics.FORCE_ENTITY_MAGIC, "attract", "Attract");
		addMagicName(ModMagics.SWAP_POSITION_MAGIC, "Swap Position");
		addMagicDescription(ModMagics.SWAP_POSITION_MAGIC, "Shoot a teleportation ray at an entity, swapping place with it.");
		addMagicName(ModMagics.SWAP_HEALTH_FOOD_MAGIC, "Swap Health and Hunger");
		addMagicDescription(ModMagics.SWAP_HEALTH_FOOD_MAGIC, "Magically swap your health and hunger.");
		addMagicName(ModMagics.SWAP_TRADE_MAGIC, "Swap Trades");
		addMagicDescription(ModMagics.SWAP_TRADE_MAGIC, "Use your magic to compel a villager to swap the buy and sell item of trades. The swapped trades are put on cooldown in the process. Some trades can not be swapped.");
	
		addGui("haste_magic.name", "Work Ethic");
		addGui("infest_stone_magic.name", "Infest %s");
		addGui("infest_stone_magic.description", "Infest a %s block with silverfish.");
		addMagicName(ModMagics.REPAIR_OFFHAND_MAGIC, "Repair Offhand");
		addMagicDescription(ModMagics.REPAIR_OFFHAND_MAGIC, "Channel your magical powers to slowly repair the item held in your other hand.");
		addMagicName(ModMagics.WALL_CLIMB_MAGIC, "Wall Climbing");
		addMagicDescription(ModMagics.WALL_CLIMB_MAGIC, "Become Spider-Man!");
		addMagicName(ModMagics.COBWEB_MAGIC, "Spider Web");
		addMagicDescription(ModMagics.COBWEB_MAGIC, "Shoot cobweb at your enemies!");
		addMagicName(ModMagics.BEE_MAGIC, "Evacuate Hive");
		addMagicDescription(ModMagics.BEE_MAGIC, "Use your magic to force bees out of their hive.");
		addMagicName(ModMagics.SHAPED_CREATE_ENTITY_MAGIC, "Shaped Summon: %s");
		addMagicDescription(ModMagics.SHAPED_CREATE_ENTITY_MAGIC, "Summon a number of %s entities in a special shape around the player.");
		addGui("soul_sand_magic.name", "InteractionHands from Below");		
		addGui("soul_sand_magic.description", "Summon skeletal hands from the ground in front of your, damaging and slowing entities they touch.");	
		addGui("tnt_magic.name", "T.N.T.");		
		addGui("tnt_magic.description", "It's dynamite.");	
		
		addMagicName(ModMagics.MINING_MAGIC, "Magic Mining");
		addMagicDescription(ModMagics.MINING_MAGIC, "Use your magic to harvest up to %s nearby %s and put the loot immediately in your inventory. Benefits from enchantments.");
	}
	// @formatter:on

	private void addMagicName(MagicType<?> type, String text) {
		addGui(type.getRegistryName().getPath() + ".name", text);
	}

	private void addMagicDescription(MagicType<?> type, String text) {
		addGui(type.getRegistryName().getPath() + ".description", text);
	}

	private void addMagicOther(MagicType<?> type, String suffix, String text) {
		addGui(type.getRegistryName().getPath() + "." + suffix, text);
	}

	private void addGui(String suffix, String text) {
		add("gui." + Main.MODID + "." + suffix, text);
	}

}
