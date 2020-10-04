package mod.vemerion.wizardstaff;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mod.vemerion.wizardstaff.entity.MagicSoulSandArmEntity;
import mod.vemerion.wizardstaff.entity.MagicWitherSkullEntity;
import mod.vemerion.wizardstaff.entity.NetherPortalEntity;
import mod.vemerion.wizardstaff.entity.PumpkinMagicEntity;
import mod.vemerion.wizardstaff.item.WizardHatItem;
import mod.vemerion.wizardstaff.staff.WizardStaffContainer;
import mod.vemerion.wizardstaff.staff.WizardStaffItem;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.SoundEvent;
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
	public static final WizardHatItem WIZARD_HAT_ITEM = null;

	@ObjectHolder(Main.MODID + ":pumpkin_magic_entity")
	public static final EntityType<PumpkinMagicEntity> PUMPKIN_MAGIC_ENTITY = null;
	
	@ObjectHolder(Main.MODID + ":nether_portal_entity")
	public static final EntityType<NetherPortalEntity> NETHER_PORTAL_ENTITY = null;
	
	@ObjectHolder(Main.MODID + ":magic_wither_skull_entity")
	public static final EntityType<MagicWitherSkullEntity> MAGIC_WITHER_SKULL_ENTITY = null;
	
	@ObjectHolder(Main.MODID + ":magic_soul_sand_arm_entity")
	public static final EntityType<MagicSoulSandArmEntity> MAGIC_SOUL_SAND_ARM_ENTITY = null;
	
	@ObjectHolder(Main.MODID + ":magic_smoke_particle_type")
	public static final BasicParticleType MAGIC_SMOKE_PARTICLE_TYPE = null;
	
	@ObjectHolder(Main.MODID + ":magic_flame_particle_type")
	public static final BasicParticleType MAGIC_FLAME_PARTICLE_TYPE = null;

	@ObjectHolder(Main.MODID + ":magic_dust_particle_type")
	public static final ParticleType<RedstoneParticleData> MAGIC_DUST_PARTICLE_TYPE = null;
	
	@ObjectHolder("wizard-staff:clock_sound")
	public static final SoundEvent CLOCK_SOUND = null;

	@ObjectHolder("wizard-staff:plop_sound")
	public static final SoundEvent PLOP_SOUND = null;

	@ObjectHolder("wizard-staff:pumpkin_magic_sound")
	public static final SoundEvent PUMPKIN_MAGIC_SOUND = null;

	@ObjectHolder("wizard-staff:ray_sound")
	public static final SoundEvent RAY_SOUND = null;

	@ObjectHolder("wizard-staff:scribble_sound")
	public static final SoundEvent SCRIBBLE_SOUND = null;

	@ObjectHolder("wizard-staff:woosh_sound")
	public static final SoundEvent WOOSH_SOUND = null;
	
	@ObjectHolder("wizard-staff:burning_sound")
	public static final SoundEvent BURNING_SOUND = null;
	
	@ObjectHolder(Main.MODID + ":portal_sound")
	public static final SoundEvent PORTAL_SOUND = null;

	@ObjectHolder(Main.MODID + ":radar_sound")
	public static final SoundEvent RADAR_SOUND = null;

	@ObjectHolder(Main.MODID + ":skeleton_sound")
	public static final SoundEvent SKELETON_SOUND = null;

	@ObjectHolder(Main.MODID + ":sniffle_sound")
	public static final SoundEvent SNIFFLE_SOUND = null;

	@ObjectHolder(Main.MODID + ":warp_sound")
	public static final SoundEvent WARP_SOUND = null;
}
