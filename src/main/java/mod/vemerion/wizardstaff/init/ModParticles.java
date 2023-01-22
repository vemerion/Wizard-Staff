package mod.vemerion.wizardstaff.init;

import com.mojang.serialization.Codec;

import mod.vemerion.wizardstaff.Main;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {

	public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister
			.create(ForgeRegistries.PARTICLE_TYPES, Main.MODID);

	public static final RegistryObject<SimpleParticleType> MAGIC_SMOKE_PARTICLE = PARTICLES
			.register("magic_smoke_particle", () -> new SimpleParticleType(true));
	public static final RegistryObject<SimpleParticleType> MAGIC_FLAME_PARTICLE = PARTICLES
			.register("magic_flame_particle", () -> new SimpleParticleType(true));
	public static final RegistryObject<ParticleType<DustParticleOptions>> MAGIC_DUST_PARTICLE = PARTICLES.register(
			"magic_dust_particle", () -> new ParticleType<DustParticleOptions>(true, DustParticleOptions.DESERIALIZER) {
				@Override
				public Codec<DustParticleOptions> codec() {
					return DustParticleOptions.CODEC;
				}
			});
}
