package mod.vemerion.wizardstaff.particle;

import com.mojang.math.Vector3f;

import mod.vemerion.wizardstaff.init.ModParticles;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleType;

public class MagicDustParticleData extends DustParticleOptions {

	public MagicDustParticleData(float red, float green, float blue, float alpha) {
		super(new Vector3f(red, green, blue), alpha);
	}
	
	@Override
	public ParticleType<DustParticleOptions> getType() {
		return ModParticles.MAGIC_DUST_PARTICLE;
	}

}
