package mod.vemerion.wizardstaff.init;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.entity.GrapplingHookEntity;
import mod.vemerion.wizardstaff.entity.MagicMissileEntity;
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
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModEntities {

	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES,
			Main.MODID);

	public static final RegistryObject<EntityType<PumpkinMagicEntity>> PUMPKIN_MAGIC = ENTITIES
			.register("pumpkin_magic", () -> EntityType.Builder
					.<PumpkinMagicEntity>of(PumpkinMagicEntity::new, MobCategory.MISC).sized(1, 1F).build(""));
	public static final RegistryObject<EntityType<NetherPortalEntity>> NETHER_PORTAL = ENTITIES.register(
			"nether_portal", () -> EntityType.Builder.<NetherPortalEntity>of(NetherPortalEntity::new, MobCategory.MISC)
					.sized(1, 2F).fireImmune().build(""));
	public static final RegistryObject<EntityType<MagicWitherSkullEntity>> MAGIC_WITHER_SKULL = ENTITIES.register(
			"magic_wither_skull",
			() -> EntityType.Builder.<MagicWitherSkullEntity>of(MagicWitherSkullEntity::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F).fireImmune().build(""));
	public static final RegistryObject<EntityType<MagicSoulSandArmEntity>> MAGIC_SOUL_SAND_ARM = ENTITIES.register(
			"magic_soul_sand_arm",
			() -> EntityType.Builder.<MagicSoulSandArmEntity>of(MagicSoulSandArmEntity::new, MobCategory.MISC)
					.sized(1, 1).fireImmune().build(""));
	public static final RegistryObject<EntityType<GrapplingHookEntity>> GRAPPLING_HOOK = ENTITIES
			.register("grappling_hook", () -> EntityType.Builder
					.<GrapplingHookEntity>of(GrapplingHookEntity::new, MobCategory.MISC).sized(0.3f, 0.3f).build(""));
	public static final RegistryObject<EntityType<MushroomCloudEntity>> MUSHROOM_CLOUD = ENTITIES
			.register("mushroom_cloud", () -> EntityType.Builder
					.<MushroomCloudEntity>of(MushroomCloudEntity::new, MobCategory.MISC).sized(3, 3).build(""));
	public static final RegistryObject<EntityType<WizardHatEntity>> WIZARD_HAT = ENTITIES.register("wizard_hat",
			() -> EntityType.Builder.<WizardHatEntity>of(WizardHatEntity::new, MobCategory.MISC).sized(1, 1).build(""));
	public static final RegistryObject<EntityType<MagicVexEntity.Attack>> MAGIC_VEX = ENTITIES.register("magic_vex",
			() -> EntityType.Builder.<MagicVexEntity.Attack>of(MagicVexEntity.Attack::new, MobCategory.MONSTER)
					.fireImmune().sized(0.4f, 0.8f).clientTrackingRange(8).build(""));
	public static final RegistryObject<EntityType<MagicVexEntity.Mining>> MAGIC_MINING_VEX = ENTITIES.register(
			"magic_mining_vex",
			() -> EntityType.Builder.<MagicVexEntity.Mining>of(MagicVexEntity.Mining::new, MobCategory.MONSTER)
					.fireImmune().sized(0.4f, 0.8f).clientTrackingRange(8).build(""));
	public static final RegistryObject<EntityType<MagicMissileEntity>> MAGIC_MISSILE_ENTITY = ENTITIES.register(
			"magic_missile_entity",
			() -> EntityType.Builder.<MagicMissileEntity>of(MagicMissileEntity::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F).fireImmune().build(""));

	@SubscribeEvent
	public static void onRegisterEntityAttributes(EntityAttributeCreationEvent event) {
		event.put(MAGIC_VEX.get(), Vex.createAttributes().build());
		event.put(MAGIC_MINING_VEX.get(), Vex.createAttributes().build());
	}
}
