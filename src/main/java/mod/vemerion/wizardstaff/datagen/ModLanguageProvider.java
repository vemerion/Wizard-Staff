package mod.vemerion.wizardstaff.datagen;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.init.ModBlocks;
import mod.vemerion.wizardstaff.init.ModEntities;
import mod.vemerion.wizardstaff.init.ModItems;
import mod.vemerion.wizardstaff.init.ModMagics;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {

	public ModLanguageProvider(PackOutput packOutput) {
		super(packOutput, Main.MODID, "en_us");
	}

	// @formatter:off
	@Override
	protected void addTranslations() {
		add("itemGroup.wizardstaff", "Wizard Staff");
		
		add("key." + Main.MODID + ".cycleMagicKey", "Cycle Magic");
		add("key." + Main.MODID + ".category", "Wizard Staff");

		add(ModItems.WARLOCK_HELMET.get(), "Warlock Cowl");
		add(ModItems.WARLOCK_CHESTPLATE.get(), "Warlock Robe");
		add(ModItems.WARLOCK_LEGGINGS.get(), "Warlock Chainmail");
		add(ModItems.WARLOCK_BOOTS.get(), "Warlock Boots");
		add(ModItems.DRUID_HELMET.get(), "Druid Skull");
		add(ModItems.DRUID_CHESTPLATE.get(), "Druid Robe");
		add(ModItems.DRUID_LEGGINGS.get(), "Druid Pants");
		add(ModItems.DRUID_BOOTS.get(), "Druid Boots");
		add(ModItems.WIZARD_HAT.get(), "Wizard Hat");
		add(ModItems.WIZARD_CHESTPLATE.get(), "Wizard Robe");
		add(ModItems.WIZARD_LEGGINGS.get(), "Wizard Skirt");
		add(ModItems.WIZARD_BOOTS.get(), "Wizard Slippers");
		add(ModItems.WIZARD_STAFF.get(), "Wizard Staff");
		add(ModItems.NETHER_WIZARD_STAFF.get(), "Nether Wizard Staff");

		add(ModEntities.GRAPPLING_HOOK.get(), "Grappling Hook");
		add(ModEntities.MAGIC_SOUL_SAND_ARM.get(), "Magic Soul Sand Arm");
		add(ModEntities.MAGIC_WITHER_SKULL.get(), "Magic Wither Skull");
		add(ModEntities.MUSHROOM_CLOUD.get(), "Mushroom Cloud");
		add(ModEntities.NETHER_PORTAL.get(), "Magic Nether Portal");
		add(ModEntities.PUMPKIN_MAGIC.get(), "Magic Pumpkin");
		add(ModEntities.WIZARD_HAT.get(), "Wizard Hat");
		add(ModEntities.MAGIC_VEX.get(), "Friendly Vex");
		add(ModEntities.MAGIC_MINING_VEX.get(), "Mining Vex");

		add("item.wizardstaff.wizard_armor.description", "-10% spell cost");
		add("item.wizardstaff.druid_armor.description", "-10% spell cost");
		add("item.wizardstaff.warlock_armor.description", "-10% spell cost");

		add(ModBlocks.MAGIC_BRICKS.get(), "Magic Bricks");
		add(ModBlocks.MAGIC_LIGHT.get(), "Magic Light");

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
		addMagicName(ModMagics.NO_MAGIC.get(), "No Magic");
		addMagicDescription(ModMagics.NO_MAGIC.get(), "No Magic");
		addMagicName(ModMagics.BLAZE_POWDER_MAGIC.get(), "Flame Thrower");
		addMagicDescription(ModMagics.BLAZE_POWDER_MAGIC.get(), "Incinerate your enemies!");		
		addMagicName(ModMagics.CARVED_PUMPKIN_MAGIC.get(), "Pumpkin Smoke");		
		addMagicDescription(ModMagics.CARVED_PUMPKIN_MAGIC.get(), "Create a large slow moving shadow that damages and weakens everything it touches.");	
		addMagicName(ModMagics.CLOCK_MAGIC.get(), "Time Dilation");		
		addMagicDescription(ModMagics.CLOCK_MAGIC.get(), "Speed up time!");		
		addMagicName(ModMagics.EGG_MAGIC.get(), "Eggization");		
		addMagicDescription(ModMagics.EGG_MAGIC.get(), "Shoot a purple ray of magic at entities, trapping them inside an egg.");
		addMagicName(ModMagics.ELYTRA_MAGIC.get(), "Magic Helicopter");		
		addMagicDescription(ModMagics.ELYTRA_MAGIC.get(), "Rotate your staff quickly enough to lift from the ground.");	
		addMagicName(ModMagics.TRANSFORM_BLOCK_MAGIC.get(), "Transform %s into %s");		
		addMagicDescription(ModMagics.TRANSFORM_BLOCK_MAGIC.get(), "Turn %s into a %s block.");	
		addMagicName(ModMagics.JUKEBOX_MAGIC.get(), "Magic Music");		
		addMagicDescription(ModMagics.JUKEBOX_MAGIC.get(), "Emit an irresistible tune from the staff, forcing enemies within %s blocks to dance.");	
		addMagicName(ModMagics.WIZARD_STAFF_MAGIC.get(), "Staff-ception");		
		addMagicDescription(ModMagics.WIZARD_STAFF_MAGIC.get(), "Very dangerous!");	
		addMagicName(ModMagics.WRITABLE_BOOK_MAGIC.get(), "Arcane Knowledge");		
		addMagicDescription(ModMagics.WRITABLE_BOOK_MAGIC.get(), "What knowledge will you unveil?");	
		addMagicName(ModMagics.OBSIDIAN_MAGIC.get(), "Portal to '%s'");		
		addMagicDescription(ModMagics.OBSIDIAN_MAGIC.get(), "Open a portal to the dimension '%s'. WARNING: One way only!");	
		addMagicName(ModMagics.GLOWSTONE_DUST_MAGIC.get(), "Magic Radar");		
		addMagicDescription(ModMagics.GLOWSTONE_DUST_MAGIC.get(), "Locate nearby entities.");	
		addMagicName(ModMagics.NETHERRACK_MAGIC.get(), "Walk on Lava");		
		addMagicDescription(ModMagics.NETHERRACK_MAGIC.get(), "Like Frost Walker but for lava, simple as that.");	
		addMagicName(ModMagics.PROJECTILE_MAGIC.get(), "Launch %s");		
		addMagicDescription(ModMagics.PROJECTILE_MAGIC.get(), "Conjure and fire a %s in front of you.");	
		addMagicName(ModMagics.GHAST_TEAR_MAGIC.get(), "Fire Immunity");		
		addMagicDescription(ModMagics.GHAST_TEAR_MAGIC.get(), "Use magic to enhance your crying, immediately extinguishing flames from you.");	
		addMagicName(ModMagics.NETHER_BRICK_MAGIC.get(), "Fortress Locator");		
		addMagicDescription(ModMagics.NETHER_BRICK_MAGIC.get(), "Use your magic to sense the direction of the nearest Nether Fortress.");	
		addMagicName(ModMagics.GOLD_NUGGET_MAGIC.get(), "Magic Bartering");		
		addMagicDescription(ModMagics.GOLD_NUGGET_MAGIC.get(), "Use magic to compel nearby Piglins to give you their items.");	
		addMagicName(ModMagics.LODESTONE_MAGIC.get(), "Magical Translocator");		
		addMagicDescription(ModMagics.LODESTONE_MAGIC.get(), "Right click a %s block with the staff, setting your waypoint to that block. Then, activating the staff anywhere in the world will return you to the waypoint, consuming the block in the process.");	
		addMagicName(ModMagics.TRANSMUTATION_MAGIC.get(), "Conjure %s");		
		addMagicDescription(ModMagics.TRANSMUTATION_MAGIC.get(), "Transmutate the item in the staff into the item %s.");	
		addMagicName(ModMagics.BLUE_DYE_MAGIC.get(), "Weather Controller");		
		addMagicDescription(ModMagics.BLUE_DYE_MAGIC.get(), "Control the weather!");	
		addMagicName(ModMagics.BRICKS_MAGIC.get(), "Portable Wall");		
		addMagicDescription(ModMagics.BRICKS_MAGIC.get(), "Surround yourself by magic bricks, creating a safe space.");	
		addMagicName(ModMagics.GRAPPLING_HOOK_MAGIC.get(), "Grappling Hook");		
		addMagicDescription(ModMagics.GRAPPLING_HOOK_MAGIC.get(), "Use the staff as a grappling hook.");	
		addMagicName(ModMagics.FEATHER_MAGIC.get(), "Slow Fall");		
		addMagicDescription(ModMagics.FEATHER_MAGIC.get(), "Use magic to dampen the effects of gravity, slowing your fall.");	
		addMagicName(ModMagics.MUSHROOM_CLOUD_MAGIC.get(), "Mushroom Cloud");		
		addMagicDescription(ModMagics.MUSHROOM_CLOUD_MAGIC.get(), "Create a magic poison cloud in from of you, damaging anyone that enters it, slowly turning them into mushrooms.");	
		addMagicName(ModMagics.SHULKER_BULLET_MAGIC.get(), "Shulker Bullet");		
		addMagicDescription(ModMagics.SHULKER_BULLET_MAGIC.get(), "Use magic to replicate a shulker bullet, targeting a nearby entity.");	
		addMagicName(ModMagics.BUCKET_MAGIC.get(), "Magic %s");		
		addMagicDescription(ModMagics.BUCKET_MAGIC.get(), "Use magic to replicate the usage of a %s.");	
		addMagicName(ModMagics.PORTABLE_CRAFTING_MAGIC.get(), "Portable Crafting");		
		addMagicDescription(ModMagics.PORTABLE_CRAFTING_MAGIC.get(), "Channel your magic to access a full 3x3 crafting table.");	
		addMagicName(ModMagics.BOOKSHELF_MAGIC.get(), "%s Generator");		
		addMagicDescription(ModMagics.BOOKSHELF_MAGIC.get(), "Use your magic to generate %s.");	
		addMagicName(ModMagics.MAP_MAGIC.get(), "Treasure Map!");		
		addMagicDescription(ModMagics.MAP_MAGIC.get(), "Transform the item in the staff into a treasure map.");	
		addMagicName(ModMagics.DEAGE_MAGIC.get(), "Deage Magic");		
		addMagicDescription(ModMagics.DEAGE_MAGIC.get(), "Shoot a de-age ray at an entity, turning it into a child.");	
		addMagicName(ModMagics.SMELTING_MAGIC.get(), "Magical %s");		
		addMagicDescription(ModMagics.SMELTING_MAGIC.get(), "Magical item processing, replicating the functionality of the %s.");	
		addMagicName(ModMagics.PILLAR_MAGIC.get(), "%s Pillar");		
		addMagicDescription(ModMagics.PILLAR_MAGIC.get(), "Stand still and use the staff to automatically propel yourself upwards, while creating a %s tower below you.");	
		addMagicName(ModMagics.SURFACE_MAGIC.get(), "Magic Surface Builder");		
		addMagicDescription(ModMagics.SURFACE_MAGIC.get(), "Click on two blocks in the world with the staff, and then use the staff to automatically build a surface between those two blocks. Only works for axis-aligned surfaces, so no diagonals! It will build the surface using blocks from your inventory, of the same type as the block you clicked first. Costs experience based on amount of blocks placed.");	
		addMagicName(ModMagics.POTION_MAGIC.get(), "Potion: %s %s");		
		addMagicDescription(ModMagics.POTION_MAGIC.get(), "Apply %s %s for %s seconds to%s%s.");	
		addMagicOther(ModMagics.POTION_MAGIC.get(), "affect_caster", " yourself%s");	
		addMagicOther(ModMagics.POTION_MAGIC.get(), "glue", " and");	
		addMagicOther(ModMagics.POTION_MAGIC.get(), "affect_others", " others around you");	
		addMagicName(ModMagics.BUILDER_MAGIC.get(), "Build '%s'");		
		addMagicDescription(ModMagics.BUILDER_MAGIC.get(), "Use the magic of the staff to construct a '%s' building in a flash of an eye.");
		
		addMagicName(ModMagics.DEFLECT_PROJECTILE_MAGIC.get(), "Deflect Projectiles");		
		addMagicDescription(ModMagics.DEFLECT_PROJECTILE_MAGIC.get(), "Use your staff as a protective layer around you, deflecting projectiles.");	
		addMagicName(ModMagics.ENDER_CHEST_MAGIC.get(), "Portable Ender Chest");		
		addMagicDescription(ModMagics.ENDER_CHEST_MAGIC.get(), "Use your staff to give you remote access to your ender storage.");	
		addMagicName(ModMagics.INVENTORY_MAGIC.get(), "Portable Chest");		
		addMagicDescription(ModMagics.INVENTORY_MAGIC.get(), "Gain access to magic storage.");	
		addMagicName(ModMagics.LOCATE_SPAWN_MAGIC.get(), "Magic Compass");		
		addMagicDescription(ModMagics.LOCATE_SPAWN_MAGIC.get(), "Channel your inner powers to gain a sense of which direction your spawn point lies.");	
		addMagicName(ModMagics.NAME_TAG_MAGIC.get(), "Random Name Generator");		
		addMagicDescription(ModMagics.NAME_TAG_MAGIC.get(), "Conjure a name tag with a random name.");	
		addMagicName(ModMagics.PUSH_BLOCK_MAGIC.get(), "%s Mover");		
		addMagicDescription(ModMagics.PUSH_BLOCK_MAGIC.get(), "Use your magic to push %s blocks.");	
		addMagicName(ModMagics.PUSH_BUTTON_MAGIC.get(), "Long Range Button Pushing");		
		addMagicDescription(ModMagics.PUSH_BUTTON_MAGIC.get(), "Activate buttons from a large distance.");	
		addMagicName(ModMagics.REMOVE_FLUID_MAGIC.get(), "Fluid Remover: '%s'");		
		addMagicDescription(ModMagics.REMOVE_FLUID_MAGIC.get(), "Use your magic to remove fluid of type '%s' around you.");	
		addMagicName(ModMagics.REPAIR_ARMOR_MAGIC.get(), "Repair Armor");		
		addMagicDescription(ModMagics.REPAIR_ARMOR_MAGIC.get(), "Use magic to repair the armor you are wearing.");	
		addMagicName(ModMagics.REVERT_POSITION_MAGIC.get(), "Revert Position");		
		addMagicDescription(ModMagics.REVERT_POSITION_MAGIC.get(), "Revert your position to where you were five seconds ago.");	
		addMagicName(ModMagics.TRANSFORM_ENTITY_MAGIC.get(), "Transform Entity");		
		addMagicDescription(ModMagics.TRANSFORM_ENTITY_MAGIC.get(), "Shot a transformation ray that turns %s entities into %s entities.");
		addMagicName(ModMagics.SUMMON_ENTITY_MAGIC.get(), "Summon %s");		
		addMagicDescription(ModMagics.SUMMON_ENTITY_MAGIC.get(), "Use your magic to summon %s %s entities.");
		
		addMagicName(ModMagics.MASS_HARVEST_MAGIC.get(), "Mass Harvest %s");
		addMagicDescription(ModMagics.MASS_HARVEST_MAGIC.get(), "Use your magic to harvest up to %s adjacent %s.");
		addMagicName(ModMagics.FORCE_ENTITY_MAGIC.get(), "%s %s");
		addMagicDescription(ModMagics.FORCE_ENTITY_MAGIC.get(), "%s %s entities with you magic.");
		addMagicOther(ModMagics.FORCE_ENTITY_MAGIC.get(), "repel", "Repel");
		addMagicOther(ModMagics.FORCE_ENTITY_MAGIC.get(), "attract", "Attract");
		addMagicName(ModMagics.SWAP_POSITION_MAGIC.get(), "Swap Position");
		addMagicDescription(ModMagics.SWAP_POSITION_MAGIC.get(), "Shoot a teleportation ray at an entity, swapping place with it.");
		addMagicName(ModMagics.SWAP_HEALTH_FOOD_MAGIC.get(), "Swap Health and Hunger");
		addMagicDescription(ModMagics.SWAP_HEALTH_FOOD_MAGIC.get(), "Magically swap your health and hunger.");
		addMagicName(ModMagics.SWAP_TRADE_MAGIC.get(), "Swap Trades");
		addMagicDescription(ModMagics.SWAP_TRADE_MAGIC.get(), "Use your magic to compel a villager to swap the buy and sell item of trades. The swapped trades are put on cooldown in the process. Some trades can not be swapped.");
	
		addGui("haste_magic.name", "Work Ethic");
		addGui("infest_stone_magic.name", "Infest %s");
		addGui("infest_stone_magic.description", "Infest a %s block with silverfish.");
		addMagicName(ModMagics.REPAIR_OFFHAND_MAGIC.get(), "Repair Offhand");
		addMagicDescription(ModMagics.REPAIR_OFFHAND_MAGIC.get(), "Channel your magical powers to slowly repair the item held in your other hand.");
		addMagicName(ModMagics.WALL_CLIMB_MAGIC.get(), "Wall Climbing");
		addMagicDescription(ModMagics.WALL_CLIMB_MAGIC.get(), "Become Spider-Man!");
		addMagicName(ModMagics.COBWEB_MAGIC.get(), "Spider Web");
		addMagicDescription(ModMagics.COBWEB_MAGIC.get(), "Shoot cobweb at your enemies!");
		addMagicName(ModMagics.BEE_MAGIC.get(), "Evacuate Hive");
		addMagicDescription(ModMagics.BEE_MAGIC.get(), "Use your magic to force bees out of their hive.");
		addMagicName(ModMagics.SHAPED_CREATE_ENTITY_MAGIC.get(), "Shaped Summon: %s");
		addMagicDescription(ModMagics.SHAPED_CREATE_ENTITY_MAGIC.get(), "Summon a number of %s entities in a special shape around the player.");
		addGui("soul_sand_magic.name", "Hands from Below");		
		addGui("soul_sand_magic.description", "Summon skeletal hands from the ground in front of your, damaging and slowing entities they touch.");	
		addGui("tnt_magic.name", "T.N.T.");		
		addGui("tnt_magic.description", "It's dynamite.");	
		
		addMagicName(ModMagics.MINING_MAGIC.get(), "Magic Mining");
		addMagicDescription(ModMagics.MINING_MAGIC.get(), "Use your magic to harvest up to %s nearby %s and put the loot immediately in your inventory. Benefits from enchantments.");
		addGui("summon_lightning_magic.name", "Human Lightning Rod");		
		addGui("summon_lightning_magic.description", "Magically summon a lightning strike at your position!");	
		addMagicName(ModMagics.MOUNT_MAGIC.get(), "Ride %s");
		addMagicDescription(ModMagics.MOUNT_MAGIC.get(), "Use your magic to make it possible to ride certain entities. While riding you can use this spell to steer the entity.");
		addMagicName(ModMagics.X_RAY_MAGIC.get(), "X-Ray %s");
		addMagicDescription(ModMagics.X_RAY_MAGIC.get(), "Magically locate blocks of type %s in a range of %s blocks. Note that it might take a while for all blocks to be discovered.");
		addMagicName(ModMagics.LIGHT_MAGIC.get(), "Let there be light");
		addMagicDescription(ModMagics.LIGHT_MAGIC.get(), "Conjure an invisible light source that disappears after a while.");
		addMagicName(ModMagics.ZOOM_MAGIC.get(), "Super Zoom");
		addMagicDescription(ModMagics.ZOOM_MAGIC.get(), "Enhance your eyes!");
		addMagicName(ModMagics.PLACE_LIGHT_RANDOM_MAGIC.get(), "Lighten Up!");
		addMagicDescription(ModMagics.PLACE_LIGHT_RANDOM_MAGIC.get(), "Randomly place %s blocks in a radius of %s around you if the light level is below %s.");
		
		addMagicName(ModMagics.QUIET_MAGIC.get(), "Shhhh!");
		addMagicDescription(ModMagics.QUIET_MAGIC.get(), "Magically suppress any sound you make, preventing the Warden from noticing you.");
		addMagicName(ModMagics.DEATH_TELEPORT_MAGIC.get(), "Exhume");
		addMagicDescription(ModMagics.DEATH_TELEPORT_MAGIC.get(), "Return to the place of your death.");


	}
	// @formatter:on

	private void addMagicName(MagicType<?> type, String text) {
		addGui(ModMagics.getRegistry().getKey(type).getPath() + ".name", text);
	}

	private void addMagicDescription(MagicType<?> type, String text) {
		addGui(ModMagics.getRegistry().getKey(type).getPath() + ".description", text);
	}

	private void addMagicOther(MagicType<?> type, String suffix, String text) {
		addGui(ModMagics.getRegistry().getKey(type).getPath() + "." + suffix, text);
	}

	private void addGui(String suffix, String text) {
		add("gui." + Main.MODID + "." + suffix, text);
	}

}
