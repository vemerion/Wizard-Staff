package mod.vemerion.wizardstaff.init;

import com.mojang.serialization.Codec;

import mod.vemerion.wizardstaff.Main;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(value = Main.MODID)
@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModParticles {

	public static final SimpleParticleType MAGIC_SMOKE_PARTICLE = null;
	public static final SimpleParticleType MAGIC_FLAME_PARTICLE = null;
	public static final ParticleType<DustParticleOptions> MAGIC_DUST_PARTICLE = null;

	@SubscribeEvent
	public static void onIParticleTypeRegistration(RegistryEvent.Register<ParticleType<?>> event) {
		event.getRegistry().register(Init.setup(new SimpleParticleType(true), "magic_smoke_particle"));
		event.getRegistry().register(Init.setup(new SimpleParticleType(true), "magic_flame_particle"));
		event.getRegistry()
				.register(Init.setup(new ParticleType<DustParticleOptions>(true, DustParticleOptions.DESERIALIZER) {
					@Override
					public Codec<DustParticleOptions> codec() {
						return DustParticleOptions.CODEC;
					}
				}, "magic_dust_particle"));

	}
}
