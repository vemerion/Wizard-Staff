package mod.vemerion.wizardstaff.init;

import com.mojang.serialization.Codec;

import mod.vemerion.wizardstaff.Main;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(value = Main.MODID)
@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModParticles {

	public static final BasicParticleType MAGIC_SMOKE_PARTICLE = null;
	public static final BasicParticleType MAGIC_FLAME_PARTICLE = null;
	public static final ParticleType<RedstoneParticleData> MAGIC_DUST_PARTICLE = null;

	@SubscribeEvent
	public static void onIParticleTypeRegistration(RegistryEvent.Register<ParticleType<?>> event) {
		event.getRegistry().register(Init.setup(new BasicParticleType(true), "magic_smoke_particle"));
		event.getRegistry().register(Init.setup(new BasicParticleType(true), "magic_flame_particle"));
		event.getRegistry()
				.register(Init.setup(new ParticleType<RedstoneParticleData>(true, RedstoneParticleData.DESERIALIZER) {
					@Override
					public Codec<RedstoneParticleData> func_230522_e_() {
						return RedstoneParticleData.field_239802_b_;
					}
				}, "magic_dust_particle"));

	}
}
