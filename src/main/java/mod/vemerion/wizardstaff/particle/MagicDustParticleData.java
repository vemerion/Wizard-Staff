package mod.vemerion.wizardstaff.particle;

import mod.vemerion.wizardstaff.init.ModParticles;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.RedstoneParticleData;

public class MagicDustParticleData extends RedstoneParticleData {

	public MagicDustParticleData(float red, float green, float blue, float alpha) {
		super(red, green, blue, alpha);
	}
	
	@Override
	public ParticleType<RedstoneParticleData> getType() {
		return ModParticles.MAGIC_DUST_PARTICLE;
	}

}
