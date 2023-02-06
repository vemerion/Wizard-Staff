package mod.vemerion.wizardstaff.Magic.wild;

import java.util.List;
import java.util.Optional;

import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.RayMagic;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.particle.MagicDustParticleData;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class AngerWardenMagic extends RayMagic {

	public AngerWardenMagic(MagicType<? extends AngerWardenMagic> type) {
		super(type);
	}

	@Override
	protected Optional<ParticleOptions> generateParticle(Level level, Player player, ItemStack staff, int count,
			Vec3 pos, Entity target) {
		var rand = player.getRandom();
		var totalDist = player.distanceToSqr(target);
		var particleDist = target.distanceToSqr(pos);

		return Optional.ofNullable(particleDist / totalDist > (float) count / duration ? null
				: new MagicDustParticleData(0.8f + rand.nextFloat() * 0.2f, rand.nextFloat() * 0.2f,
						0.8f + rand.nextFloat() * 0.2f, 1));
	}

	@Override
	protected void hitEntity(Level level, Player player, Entity target) {
		if (!(target instanceof Warden warden)) {
			playSoundServer(level, player, ModSounds.POOF.get(), 1, soundPitch(player));
			return;
		}

		List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class,
				new AABB(target.blockPosition()).inflate(6), e -> warden.canTargetEntity(e) && e != player);

		if (entities.isEmpty()) {
			playSoundServer(level, player, ModSounds.POOF.get(), 1, soundPitch(player));
			return;
		}

		cost(player);
		var angry = entities.get(player.getRandom().nextInt(entities.size()));
		for (int i = 0; i < player.getRandom().nextInt(3, 6); i++) {
			warden.increaseAngerAt(angry);
		}
	}

}
