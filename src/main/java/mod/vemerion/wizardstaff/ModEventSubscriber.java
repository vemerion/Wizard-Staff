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
