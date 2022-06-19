package mod.vemerion.wizardstaff.init;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.entity.GrapplingHookEntity;
import mod.vemerion.wizardstaff.entity.MagicSoulSandArmEntity;
import mod.vemerion.wizardstaff.entity.MagicVexEntity;
import mod.vemerion.wizardstaff.entity.MagicWitherSkullEntity;
import mod.vemerion.wizardstaff.entity.MushroomCloudEntity;
import mod.vemerion.wizardstaff.entity.NetherPortalEntity;
import mod.vemerion.wizardstaff.entity.PumpkinMagicEntity;
import mod.vemerion.wizardstaff.entity.WizardHatEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.monster.Vex;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(value = Main.MODID)
@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModEntities {

	public static final EntityType<PumpkinMagicEntity> PUMPKIN_MAGIC = null;
	public static final EntityType<NetherPortalEntity> NETHER_PORTAL = null;
	public static final EntityType<MagicWitherSkullEntity> MAGIC_WITHER_SKULL = null;
	public static final EntityType<MagicSoulSandArmEntity> MAGIC_SOUL_SAND_ARM = null;
	public static final EntityType<GrapplingHookEntity> GRAPPLING_HOOK = null;
	public static final EntityType<MushroomCloudEntity> MUSHROOM_CLOUD = null;
	public static final EntityType<WizardHatEntity> WIZARD_HAT = null;
	public static final EntityType<MagicVexEntity> MAGIC_VEX = null;

	@SubscribeEvent
	public static void onRegisterEntity(RegistryEvent.Register<EntityType<?>> event) {
		EntityType<PumpkinMagicEntity> pumpkinMagicEntity = EntityType.Builder
				.<PumpkinMagicEntity>of(PumpkinMagicEntity::new, MobCategory.MISC).sized(1, 1F).build("");
		event.getRegistry().register(Init.setup(pumpkinMagicEntity, "pumpkin_magic"));

		EntityType<NetherPortalEntity> netherPortalEntity = EntityType.Builder
				.<NetherPortalEntity>of(NetherPortalEntity::new, MobCategory.MISC).sized(1, 2F).fireImmune().build("");
		event.getRegistry().register(Init.setup(netherPortalEntity, "nether_portal"));

		EntityType<MagicWitherSkullEntity> magicWitherSkullEntity = EntityType.Builder
				.<MagicWitherSkullEntity>of(MagicWitherSkullEntity::new, MobCategory.MISC).sized(0.3125F, 0.3125F)
				.fireImmune().build("");
		event.getRegistry().register(Init.setup(magicWitherSkullEntity, "magic_wither_skull"));

		EntityType<MagicSoulSandArmEntity> magicSoulSandArmEntity = EntityType.Builder
				.<MagicSoulSandArmEntity>of(MagicSoulSandArmEntity::new, MobCategory.MISC).sized(1, 1).fireImmune()
				.build("");
		event.getRegistry().register(Init.setup(magicSoulSandArmEntity, "magic_soul_sand_arm"));

		EntityType<GrapplingHookEntity> grapplingHookEntity = EntityType.Builder
				.<GrapplingHookEntity>of(GrapplingHookEntity::new, MobCategory.MISC).sized(0.3f, 0.3f).build("");
		event.getRegistry().register(Init.setup(grapplingHookEntity, "grappling_hook"));

		EntityType<MushroomCloudEntity> mushroomCloudEntity = EntityType.Builder
				.<MushroomCloudEntity>of(MushroomCloudEntity::new, MobCategory.MISC).sized(3, 3).build("");
		event.getRegistry().register(Init.setup(mushroomCloudEntity, "mushroom_cloud"));

		EntityType<WizardHatEntity> wizardHatEntity = EntityType.Builder
				.<WizardHatEntity>of(WizardHatEntity::new, MobCategory.MISC).sized(1, 1).build("");
		event.getRegistry().register(Init.setup(wizardHatEntity, "wizard_hat"));

		EntityType<MagicVexEntity> magicVexEntity = EntityType.Builder
				.<MagicVexEntity>of(MagicVexEntity::new, MobCategory.MONSTER).fireImmune().sized(0.4f, 0.8f)
				.clientTrackingRange(8).build("");
		event.getRegistry().register(Init.setup(magicVexEntity, "magic_vex"));
	}

	@SubscribeEvent
	public static void onRegisterEntityAttributes(EntityAttributeCreationEvent event) {
		event.put(MAGIC_VEX, Vex.createAttributes().build());
	}
}
