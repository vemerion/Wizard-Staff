package mod.vemerion.wizardstaff.init;

import mod.vemerion.wizardstaff.Main;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.RegistryEvent.MissingMappings.Mapping;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

// This class contains all the events to convert the registry names of all registry objects (except magics) from before the 1.6.0 Wizard Staff update
// To remove the suffixes (magic_bricks_block, etc), to be more in line with vanilla
@EventBusSubscriber(bus = Bus.FORGE, modid = Main.MODID)
public class ModMissingMappings {

	@SubscribeEvent
	public static void onMissingBlock(RegistryEvent.MissingMappings<Block> event) {
		for (Mapping<Block> mapping : event.getMappings(Main.MODID)) {
			if (mapping.key.getPath().equals("magic_bricks_block"))
				mapping.remap(ModBlocks.MAGIC_BRICKS);
		}
	}

	@SubscribeEvent
	public static void onMissingContainer(RegistryEvent.MissingMappings<ContainerType<?>> event) {
		for (Mapping<ContainerType<?>> mapping : event.getMappings(Main.MODID)) {
			if (mapping.key.getPath().equals("wizard_staff_container"))
				mapping.remap(ModContainers.WIZARD_STAFF);
		}
	}

	@SubscribeEvent
	public static void onMissingEntity(RegistryEvent.MissingMappings<EntityType<?>> event) {
		for (Mapping<EntityType<?>> mapping : event.getMappings(Main.MODID)) {
			if (mapping.key.getPath().equals("pumpkin_magic_entity"))
				mapping.remap(ModEntities.PUMPKIN_MAGIC);
			else if (mapping.key.getPath().equals("nether_portal_entity"))
				mapping.remap(ModEntities.NETHER_PORTAL);
			else if (mapping.key.getPath().equals("magic_wither_skull_entity"))
				mapping.remap(ModEntities.MAGIC_WITHER_SKULL);
			else if (mapping.key.getPath().equals("magic_soul_sand_arm_entity"))
				mapping.remap(ModEntities.MAGIC_SOUL_SAND_ARM);
			else if (mapping.key.getPath().equals("grappling_hook_entity"))
				mapping.remap(ModEntities.GRAPPLING_HOOK);
			else if (mapping.key.getPath().equals("mushroom_cloud_entity"))
				mapping.remap(ModEntities.MUSHROOM_CLOUD);
			else if (mapping.key.getPath().equals("wizard_hat_entity"))
				mapping.remap(ModEntities.WIZARD_HAT);
		}
	}
	
	@SubscribeEvent
	public static void onMissingItem(RegistryEvent.MissingMappings<Item> event) {
		for (Mapping<Item> mapping : event.getMappings(Main.MODID)) {
			if (mapping.key.getPath().equals("wizard_staff_item"))
				mapping.remap(ModItems.WIZARD_STAFF);
			else if (mapping.key.getPath().equals("nether_wizard_staff_item"))
				mapping.remap(ModItems.NETHER_WIZARD_STAFF);
			else if (mapping.key.getPath().equals("druid_helmet_item"))
				mapping.remap(ModItems.DRUID_HELMET);
			else if (mapping.key.getPath().equals("druid_chestplate_item"))
				mapping.remap(ModItems.DRUID_CHESTPLATE);
			else if (mapping.key.getPath().equals("druid_leggings_item"))
				mapping.remap(ModItems.DRUID_LEGGINGS);
			else if (mapping.key.getPath().equals("druid_boots_item"))
				mapping.remap(ModItems.DRUID_BOOTS);
			else if (mapping.key.getPath().equals("warlock_helmet_item"))
				mapping.remap(ModItems.WARLOCK_HELMET);
			else if (mapping.key.getPath().equals("warlock_chestplate_item"))
				mapping.remap(ModItems.WARLOCK_CHESTPLATE);
			else if (mapping.key.getPath().equals("warlock_leggings_item"))
				mapping.remap(ModItems.WARLOCK_LEGGINGS);
			else if (mapping.key.getPath().equals("warlock_boots_item"))
				mapping.remap(ModItems.WARLOCK_BOOTS);
			else if (mapping.key.getPath().equals("wizard_hat_item"))
				mapping.remap(ModItems.WIZARD_HAT);
			else if (mapping.key.getPath().equals("wizard_chestplate_item"))
				mapping.remap(ModItems.WIZARD_CHESTPLATE);
			else if (mapping.key.getPath().equals("wizard_leggings_item"))
				mapping.remap(ModItems.WIZARD_LEGGINGS);
			else if (mapping.key.getPath().equals("wizard_boots_item"))
				mapping.remap(ModItems.WIZARD_BOOTS);
		}
	}
}
