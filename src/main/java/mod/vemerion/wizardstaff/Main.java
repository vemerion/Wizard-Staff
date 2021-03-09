package mod.vemerion.wizardstaff;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
import mod.vemerion.wizardstaff.item.WizardStaffItemGroup;
import mod.vemerion.wizardstaff.staff.WizardStaffContainer;
import mod.vemerion.wizardstaff.staff.WizardStaffItem;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod(Main.MODID)
public class Main {
	public static final String MODID = "wizard-staff";

	public static final Logger LOGGER = LogManager.getLogger();

	@ObjectHolder(Main.MODID + ":wizard_staff_container")
	public static final ContainerType<WizardStaffContainer> WIZARD_STAFF_CONTAINER = null;

	@ObjectHolder(Main.MODID + ":wizard_staff_item")
	public static final WizardStaffItem WIZARD_STAFF_ITEM = null;

	@ObjectHolder(Main.MODID + ":nether_wizard_staff_item")
	public static final WizardStaffItem NETHER_WIZARD_STAFF_ITEM = null;

	@ObjectHolder(Main.MODID + ":wizard_hat_item")
	public static final WizardArmorItem WIZARD_HAT_ITEM = null;

	@ObjectHolder(Main.MODID + ":wizard_chestplate_item")
	public static final WizardArmorItem WIZARD_CHESTPLATE_ITEM = null;

	@ObjectHolder(Main.MODID + ":wizard_leggings_item")
	public static final WizardArmorItem WIZARD_LEGGINGS_ITEM = null;

	@ObjectHolder(Main.MODID + ":wizard_boots_item")
	public static final WizardArmorItem WIZARD_BOOTS_ITEM = null;

	@ObjectHolder(Main.MODID + ":druid_helmet_item")
	public static final DruidArmorItem DRUID_HELMET_ITEM = null;

	@ObjectHolder(Main.MODID + ":druid_chestplate_item")
	public static final DruidArmorItem DRUID_CHESTPLATE_ITEM = null;

	@ObjectHolder(Main.MODID + ":druid_leggings_item")
	public static final DruidArmorItem DRUID_LEGGINGS_ITEM = null;

	@ObjectHolder(Main.MODID + ":druid_boots_item")
	public static final DruidArmorItem DRUID_BOOTS_ITEM = null;

	@ObjectHolder(Main.MODID + ":warlock_helmet_item")
	public static final WarlockArmorItem WARLOCK_HELMET_ITEM = null;

	@ObjectHolder(Main.MODID + ":warlock_chestplate_item")
	public static final WarlockArmorItem WARLOCK_CHESTPLATE_ITEM = null;

	@ObjectHolder(Main.MODID + ":warlock_leggings_item")
	public static final WarlockArmorItem WARLOCK_LEGGINGS_ITEM = null;

	@ObjectHolder(Main.MODID + ":warlock_boots_item")
	public static final WarlockArmorItem WARLOCK_BOOTS_ITEM = null;
	
	@ObjectHolder(Main.MODID + ":magic_bricks_block")
	public static final Block MAGIC_BRICKS_BLOCK = null;

	@ObjectHolder(Main.MODID + ":pumpkin_magic_entity")
	public static final EntityType<PumpkinMagicEntity> PUMPKIN_MAGIC_ENTITY = null;

	@ObjectHolder(Main.MODID + ":nether_portal_entity")
	public static final EntityType<NetherPortalEntity> NETHER_PORTAL_ENTITY = null;

	@ObjectHolder(Main.MODID + ":magic_wither_skull_entity")
	public static final EntityType<MagicWitherSkullEntity> MAGIC_WITHER_SKULL_ENTITY = null;

	@ObjectHolder(Main.MODID + ":magic_soul_sand_arm_entity")
	public static final EntityType<MagicSoulSandArmEntity> MAGIC_SOUL_SAND_ARM_ENTITY = null;

	@ObjectHolder(Main.MODID + ":grappling_hook_entity")
	public static final EntityType<GrapplingHookEntity> GRAPPLING_HOOK_ENTITY = null;
	
	@ObjectHolder(Main.MODID + ":mushroom_cloud_entity")
	public static final EntityType<MushroomCloudEntity> MUSHROOM_CLOUD_ENTITY = null;
	
	@ObjectHolder(Main.MODID + ":wizard_hat_entity")
	public static final EntityType<WizardHatEntity> WIZARD_HAT_ENTITY = null;

	@ObjectHolder(Main.MODID + ":magic_smoke_particle_type")
	public static final BasicParticleType MAGIC_SMOKE_PARTICLE_TYPE = null;

	@ObjectHolder(Main.MODID + ":magic_flame_particle_type")
	public static final BasicParticleType MAGIC_FLAME_PARTICLE_TYPE = null;

	@ObjectHolder(Main.MODID + ":magic_dust_particle_type")
	public static final ParticleType<RedstoneParticleData> MAGIC_DUST_PARTICLE_TYPE = null;

	public static final WizardStaffItemGroup WIZARD_STAFF_ITEM_GROUP = new WizardStaffItemGroup();
}
