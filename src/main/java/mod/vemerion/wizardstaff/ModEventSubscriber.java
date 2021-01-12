package mod.vemerion.wizardstaff;

import com.mojang.serialization.Codec;

import mod.vemerion.wizardstaff.Magic.Magics;
import mod.vemerion.wizardstaff.capability.Experience;
import mod.vemerion.wizardstaff.capability.ScreenAnimations;
import mod.vemerion.wizardstaff.capability.Wizard;
import mod.vemerion.wizardstaff.entity.GrapplingHookEntity;
import mod.vemerion.wizardstaff.entity.MagicSoulSandArmEntity;
import mod.vemerion.wizardstaff.entity.MagicWitherSkullEntity;
import mod.vemerion.wizardstaff.entity.MushroomCloudEntity;
import mod.vemerion.wizardstaff.entity.NetherPortalEntity;
import mod.vemerion.wizardstaff.entity.PumpkinMagicEntity;
import mod.vemerion.wizardstaff.entity.WizardHatEntity;
import mod.vemerion.wizardstaff.item.DruidArmorItem;
import mod.vemerion.wizardstaff.item.WarlockArmorItem;
import mod.vemerion.wizardstaff.item.WizardArmorItem;
import mod.vemerion.wizardstaff.network.Network;
import mod.vemerion.wizardstaff.network.UpdateMagicsMessage;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.staff.WizardStaffContainer;
import mod.vemerion.wizardstaff.staff.WizardStaffItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;

@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventSubscriber {

	@SubscribeEvent
	public static void onRegisterItem(RegistryEvent.Register<Item> event) {
		Item.Properties staffProperties = new Item.Properties().maxStackSize(1).group(ItemGroup.COMBAT)
				.setISTER(() -> WizardStaffTileEntityRenderer::new);
		event.getRegistry().register(setup(new WizardStaffItem(staffProperties), "wizard_staff_item"));
		Item.Properties netherStaffProperties = new Item.Properties().maxStackSize(1).group(ItemGroup.COMBAT)
				.setISTER(() -> WizardStaffTileEntityRenderer::new).isImmuneToFire();
		event.getRegistry().register(setup(new WizardStaffItem(netherStaffProperties), "nether_wizard_staff_item"));

		event.getRegistry().register(setup(new DruidArmorItem(EquipmentSlotType.HEAD), "druid_helmet_item"));
		event.getRegistry().register(setup(new DruidArmorItem(EquipmentSlotType.CHEST), "druid_chestplate_item"));
		event.getRegistry().register(setup(new DruidArmorItem(EquipmentSlotType.LEGS), "druid_leggings_item"));
		event.getRegistry().register(setup(new DruidArmorItem(EquipmentSlotType.FEET), "druid_boots_item"));

		event.getRegistry().register(setup(new WarlockArmorItem(EquipmentSlotType.HEAD), "warlock_helmet_item"));
		event.getRegistry().register(setup(new WarlockArmorItem(EquipmentSlotType.CHEST), "warlock_chestplate_item"));
		event.getRegistry().register(setup(new WarlockArmorItem(EquipmentSlotType.LEGS), "warlock_leggings_item"));
		event.getRegistry().register(setup(new WarlockArmorItem(EquipmentSlotType.FEET), "warlock_boots_item"));

		event.getRegistry().register(setup(new WizardArmorItem(EquipmentSlotType.HEAD), "wizard_hat_item"));
		event.getRegistry().register(setup(new WizardArmorItem(EquipmentSlotType.CHEST), "wizard_chestplate_item"));
		event.getRegistry().register(setup(new WizardArmorItem(EquipmentSlotType.LEGS), "wizard_leggings_item"));
		event.getRegistry().register(setup(new WizardArmorItem(EquipmentSlotType.FEET), "wizard_boots_item"));
	}

	@SubscribeEvent
	public static void onRegisterBlock(RegistryEvent.Register<Block> event) {
		event.getRegistry()
				.register(setup(new Block(
						Block.Properties.create(Material.ROCK, MaterialColor.RED).hardnessAndResistance(2.0F, 6.0F)),
						"magic_bricks_block"));
	}

	@SubscribeEvent
	public static void onRegisterContainer(RegistryEvent.Register<ContainerType<?>> event) {
		event.getRegistry().register(setup(IForgeContainerType.create(WizardStaffContainer::createContainerClientSide),
				"wizard_staff_container"));
	}

	@SubscribeEvent
	public static void onRegisterEntity(RegistryEvent.Register<EntityType<?>> event) {
		EntityType<PumpkinMagicEntity> pumpkinMagicEntity = EntityType.Builder
				.<PumpkinMagicEntity>create(PumpkinMagicEntity::new, EntityClassification.MISC).size(1, 1F)
				.build("pumpkin_magic_entity");
		event.getRegistry().register(setup(pumpkinMagicEntity, "pumpkin_magic_entity"));

		EntityType<NetherPortalEntity> netherPortalEntity = EntityType.Builder
				.<NetherPortalEntity>create(NetherPortalEntity::new, EntityClassification.MISC).size(1, 2F)
				.immuneToFire().build("nether_portal_entity");
		event.getRegistry().register(setup(netherPortalEntity, "nether_portal_entity"));

		EntityType<MagicWitherSkullEntity> magicWitherSkullEntity = EntityType.Builder
				.<MagicWitherSkullEntity>create(MagicWitherSkullEntity::new, EntityClassification.MISC)
				.size(0.3125F, 0.3125F).immuneToFire().build("magic_wither_skull_entity");
		event.getRegistry().register(setup(magicWitherSkullEntity, "magic_wither_skull_entity"));

		EntityType<MagicSoulSandArmEntity> magicSoulSandArmEntity = EntityType.Builder
				.<MagicSoulSandArmEntity>create(MagicSoulSandArmEntity::new, EntityClassification.MISC).size(1, 1)
				.immuneToFire().build("magic_soul_sand_arm_entity");
		event.getRegistry().register(setup(magicSoulSandArmEntity, "magic_soul_sand_arm_entity"));

		EntityType<GrapplingHookEntity> grapplingHookEntity = EntityType.Builder
				.<GrapplingHookEntity>create(GrapplingHookEntity::new, EntityClassification.MISC).size(0.3f, 0.3f)
				.build("grappling_hook_entity");
		event.getRegistry().register(setup(grapplingHookEntity, "grappling_hook_entity"));

		EntityType<MushroomCloudEntity> mushroomCloudEntity = EntityType.Builder
				.<MushroomCloudEntity>create(MushroomCloudEntity::new, EntityClassification.MISC).size(3, 3)
				.build("mushroom_cloud_entity");
		event.getRegistry().register(setup(mushroomCloudEntity, "mushroom_cloud_entity"));

		EntityType<WizardHatEntity> wizardHatEntity = EntityType.Builder
				.<WizardHatEntity>create(WizardHatEntity::new, EntityClassification.MISC).size(1, 1)
				.build("wizard_hat_entity");
		event.getRegistry().register(setup(wizardHatEntity, "wizard_hat_entity"));
	}

	@SubscribeEvent
	public static void onIParticleTypeRegistration(RegistryEvent.Register<ParticleType<?>> event) {
		event.getRegistry().register(setup(new BasicParticleType(true), "magic_smoke_particle_type"));
		event.getRegistry().register(setup(new BasicParticleType(true), "magic_flame_particle_type"));
		event.getRegistry()
				.register(setup(new ParticleType<RedstoneParticleData>(true, RedstoneParticleData.DESERIALIZER) {
					@Override
					public Codec<RedstoneParticleData> func_230522_e_() {
						return RedstoneParticleData.field_239802_b_;
					}
				}, "magic_dust_particle_type"));

	}

	@SubscribeEvent
	public static void onRegisterSound(RegistryEvent.Register<SoundEvent> event) {
		SoundEvent clock_sound = new SoundEvent(new ResourceLocation(Main.MODID, "clock_sound"));
		event.getRegistry().register(setup(clock_sound, "clock_sound"));
		SoundEvent plop_sound = new SoundEvent(new ResourceLocation(Main.MODID, "plop_sound"));
		event.getRegistry().register(setup(plop_sound, "plop_sound"));
		SoundEvent pumpkin_magic_sound = new SoundEvent(new ResourceLocation(Main.MODID, "pumpkin_magic_sound"));
		event.getRegistry().register(setup(pumpkin_magic_sound, "pumpkin_magic_sound"));
		SoundEvent ray_sound = new SoundEvent(new ResourceLocation(Main.MODID, "ray_sound"));
		event.getRegistry().register(setup(ray_sound, "ray_sound"));
		SoundEvent scribble_sound = new SoundEvent(new ResourceLocation(Main.MODID, "scribble_sound"));
		event.getRegistry().register(setup(scribble_sound, "scribble_sound"));
		SoundEvent woosh_sound = new SoundEvent(new ResourceLocation(Main.MODID, "woosh_sound"));
		event.getRegistry().register(setup(woosh_sound, "woosh_sound"));
		SoundEvent burning_sound = new SoundEvent(new ResourceLocation(Main.MODID, "burning_sound"));
		event.getRegistry().register(setup(burning_sound, "burning_sound"));
		SoundEvent portal_sound = new SoundEvent(new ResourceLocation(Main.MODID, "portal_sound"));
		event.getRegistry().register(setup(portal_sound, "portal_sound"));
		SoundEvent radar_sound = new SoundEvent(new ResourceLocation(Main.MODID, "radar_sound"));
		event.getRegistry().register(setup(radar_sound, "radar_sound"));
		SoundEvent skeleton_sound = new SoundEvent(new ResourceLocation(Main.MODID, "skeleton_sound"));
		event.getRegistry().register(setup(skeleton_sound, "skeleton_sound"));
		SoundEvent sniffle_sound = new SoundEvent(new ResourceLocation(Main.MODID, "sniffle_sound"));
		event.getRegistry().register(setup(sniffle_sound, "sniffle_sound"));
		SoundEvent warp_sound = new SoundEvent(new ResourceLocation(Main.MODID, "warp_sound"));
		event.getRegistry().register(setup(warp_sound, "warp_sound"));
		SoundEvent gong_sound = new SoundEvent(new ResourceLocation(Main.MODID, "gong_sound"));
		event.getRegistry().register(setup(gong_sound, "gong_sound"));
		SoundEvent poof_sound = new SoundEvent(new ResourceLocation(Main.MODID, "poof_sound"));
		event.getRegistry().register(setup(poof_sound, "poof_sound"));
		SoundEvent teleport_sound = new SoundEvent(new ResourceLocation(Main.MODID, "teleport_sound"));
		event.getRegistry().register(setup(teleport_sound, "teleport_sound"));
		SoundEvent brick_sound = new SoundEvent(new ResourceLocation(Main.MODID, "brick_sound"));
		event.getRegistry().register(setup(brick_sound, "brick_sound"));
		SoundEvent chirp_sound = new SoundEvent(new ResourceLocation(Main.MODID, "chirp_sound"));
		event.getRegistry().register(setup(chirp_sound, "chirp_sound"));
		SoundEvent flap_sound = new SoundEvent(new ResourceLocation(Main.MODID, "flap_sound"));
		event.getRegistry().register(setup(flap_sound, "flap_sound"));
		SoundEvent spray_sound = new SoundEvent(new ResourceLocation(Main.MODID, "spray_sound"));
		event.getRegistry().register(setup(spray_sound, "spray_sound"));
		SoundEvent anvil_sound = new SoundEvent(new ResourceLocation(Main.MODID, "anvil_sound"));
		event.getRegistry().register(setup(anvil_sound, "anvil_sound"));
		SoundEvent cloth_sound = new SoundEvent(new ResourceLocation(Main.MODID, "cloth_sound"));
		event.getRegistry().register(setup(cloth_sound, "cloth_sound"));
		SoundEvent page_turn_sound = new SoundEvent(new ResourceLocation(Main.MODID, "page_turn_sound"));
		event.getRegistry().register(setup(page_turn_sound, "page_turn_sound"));
		SoundEvent deage_sound = new SoundEvent(new ResourceLocation(Main.MODID, "deage_sound"));
		event.getRegistry().register(setup(deage_sound, "deage_sound"));  
	}

	@SubscribeEvent
	public static void setup(FMLCommonSetupEvent event) {
		CapabilityManager.INSTANCE.register(ScreenAnimations.class, new ScreenAnimations.ScreenAnimationsStorage(),
				ScreenAnimations::new);
		CapabilityManager.INSTANCE.register(Experience.class, new Experience.ExperienceStorage(), Experience::new);
		CapabilityManager.INSTANCE.register(Wizard.class, new Wizard.WizardStorage(), Wizard::new);

		Network.INSTANCE.registerMessage(0, ScreenAnimations.class, ScreenAnimations::encode, ScreenAnimations::decode,
				ScreenAnimations::handle);
		Network.INSTANCE.registerMessage(1, UpdateMagicsMessage.class, UpdateMagicsMessage::encode,
				UpdateMagicsMessage::decode, UpdateMagicsMessage::handle);

		Magics.init();

	}

	public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final String name) {
		return setup(entry, new ResourceLocation(Main.MODID, name));
	}

	public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final ResourceLocation registryName) {
		entry.setRegistryName(registryName);
		return entry;
	}

}
