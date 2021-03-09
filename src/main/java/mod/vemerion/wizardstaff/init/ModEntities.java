package mod.vemerion.wizardstaff.init;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.entity.GrapplingHookEntity;
import mod.vemerion.wizardstaff.entity.MagicSoulSandArmEntity;
import mod.vemerion.wizardstaff.entity.MagicWitherSkullEntity;
import mod.vemerion.wizardstaff.entity.MushroomCloudEntity;
import mod.vemerion.wizardstaff.entity.NetherPortalEntity;
import mod.vemerion.wizardstaff.entity.PumpkinMagicEntity;
import mod.vemerion.wizardstaff.entity.WizardHatEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(value = Main.MODID)
@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModEntities {

	public static final EntityType<PumpkinMagicEntity> PUMPKIN_MAGIC_ENTITY = null;
	public static final EntityType<NetherPortalEntity> NETHER_PORTAL_ENTITY = null;
	public static final EntityType<MagicWitherSkullEntity> MAGIC_WITHER_SKULL_ENTITY = null;
	public static final EntityType<MagicSoulSandArmEntity> MAGIC_SOUL_SAND_ARM_ENTITY = null;
	public static final EntityType<GrapplingHookEntity> GRAPPLING_HOOK_ENTITY = null;
	public static final EntityType<MushroomCloudEntity> MUSHROOM_CLOUD_ENTITY = null;
	public static final EntityType<WizardHatEntity> WIZARD_HAT_ENTITY = null;

	@SubscribeEvent
	public static void onRegisterEntity(RegistryEvent.Register<EntityType<?>> event) {
		EntityType<PumpkinMagicEntity> pumpkinMagicEntity = EntityType.Builder
				.<PumpkinMagicEntity>create(PumpkinMagicEntity::new, EntityClassification.MISC).size(1, 1F)
				.build("pumpkin_magic_entity");
		event.getRegistry().register(Init.setup(pumpkinMagicEntity, "pumpkin_magic_entity"));

		EntityType<NetherPortalEntity> netherPortalEntity = EntityType.Builder
				.<NetherPortalEntity>create(NetherPortalEntity::new, EntityClassification.MISC).size(1, 2F)
				.immuneToFire().build("nether_portal_entity");
		event.getRegistry().register(Init.setup(netherPortalEntity, "nether_portal_entity"));

		EntityType<MagicWitherSkullEntity> magicWitherSkullEntity = EntityType.Builder
				.<MagicWitherSkullEntity>create(MagicWitherSkullEntity::new, EntityClassification.MISC)
				.size(0.3125F, 0.3125F).immuneToFire().build("magic_wither_skull_entity");
		event.getRegistry().register(Init.setup(magicWitherSkullEntity, "magic_wither_skull_entity"));

		EntityType<MagicSoulSandArmEntity> magicSoulSandArmEntity = EntityType.Builder
				.<MagicSoulSandArmEntity>create(MagicSoulSandArmEntity::new, EntityClassification.MISC).size(1, 1)
				.immuneToFire().build("magic_soul_sand_arm_entity");
		event.getRegistry().register(Init.setup(magicSoulSandArmEntity, "magic_soul_sand_arm_entity"));

		EntityType<GrapplingHookEntity> grapplingHookEntity = EntityType.Builder
				.<GrapplingHookEntity>create(GrapplingHookEntity::new, EntityClassification.MISC).size(0.3f, 0.3f)
				.build("grappling_hook_entity");
		event.getRegistry().register(Init.setup(grapplingHookEntity, "grappling_hook_entity"));

		EntityType<MushroomCloudEntity> mushroomCloudEntity = EntityType.Builder
				.<MushroomCloudEntity>create(MushroomCloudEntity::new, EntityClassification.MISC).size(3, 3)
				.build("mushroom_cloud_entity");
		event.getRegistry().register(Init.setup(mushroomCloudEntity, "mushroom_cloud_entity"));

		EntityType<WizardHatEntity> wizardHatEntity = EntityType.Builder
				.<WizardHatEntity>create(WizardHatEntity::new, EntityClassification.MISC).size(1, 1)
				.build("wizard_hat_entity");
		event.getRegistry().register(Init.setup(wizardHatEntity, "wizard_hat_entity"));
	}
}
