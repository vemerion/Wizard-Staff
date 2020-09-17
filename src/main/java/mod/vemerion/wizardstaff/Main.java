package mod.vemerion.wizardstaff;

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
	
	@ObjectHolder(Main.MODID + ":wizard_staff_container")
	public static final ContainerType<WizardStaffContainer> WIZARD_STAFF_CONTAINER = null;
	
	@ObjectHolder(Main.MODID + ":wizard_staff_item")
	public static final WizardStaffItem WIZARD_STAFF_ITEM = null;
	
	@ObjectHolder(Main.MODID + ":wizard_hat_item")
	public static final WizardHatItem WIZARD_HAT_ITEM = null;

	@ObjectHolder(Main.MODID + ":pumpkin_magic_entity")
	public static final EntityType<PumpkinMagicEntity> PUMPKIN_MAGIC_ENTITY = null;
	
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
}
