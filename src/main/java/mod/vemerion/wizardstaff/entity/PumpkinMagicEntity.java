package mod.vemerion.wizardstaff.entity;

import java.util.Random;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.init.ModParticles;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class PumpkinMagicEntity extends MagicEntity {

	public PumpkinMagicEntity(EntityType<? extends PumpkinMagicEntity> entityTypeIn, Level levelIn) {
		super(entityTypeIn, levelIn);
		setNoGravity(true);
	}

	public PumpkinMagicEntity(EntityType<? extends PumpkinMagicEntity> entityTypeIn, Level levelIn,
			Player shooter) {
		super(entityTypeIn, levelIn);
		this.setCaster(shooter);
		setNoGravity(true);
	}

	@Override
	public void tick() {
		super.tick();

		move(MoverType.SELF, Vec3.directionFromRotation(getRotationVector()).scale(0.1));
		if (!level.isClientSide) {
			spawnParticles();
			collision();

			if (tickCount > 120)
				discard();
		}
	}

	private void collision() {
		Vec3 sideways = Vec3.directionFromRotation(0, getYRot() + 90);
		Vec3 pos = position().add(sideways.x * 3, 0, sideways.z * 3);
		Player shooter = getCaster(level);
		DamageSource source = shooter == null ? Magic.magicDamage() : Magic.magicDamage(this, shooter);
		for (int i = 0; i < 6; i++) {
			for (Entity e : level.getEntities(shooter, new AABB(pos, pos).inflate(1, 2, 1),
					(e) -> e instanceof LivingEntity)) {
				e.hurt(source, 1);
				LivingEntity living = (LivingEntity) e;
				living.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 10, 1));
			}
			pos = pos.add(-sideways.x, 0, -sideways.z);
		}
	}

	private void spawnParticles() {
		ServerLevel serverWorld = (ServerLevel) level;
		Vec3 sideways = Vec3.directionFromRotation(0, getYRot() + 90);
		Random random = new Random(0);

		// Eyes
		double radius = 1.7;
		for (int i = -1; i < 2; i += 2) {
			Vec3 pos = position().add(sideways.x * 2 * i, 2, sideways.z * 2 * i);
			for (int j = 0; j < 50; j++) {
				Vec3 offset = Vec3.directionFromRotation(random.nextFloat() * 360, getYRot() + 90)
						.scale((radius + random.nextDouble() * 0.4 - 0.2) * random.nextDouble());
				serverWorld.sendParticles(ModParticles.MAGIC_SMOKE_PARTICLE, pos.x + offset.x, pos.y + offset.y,
						pos.z + offset.z, 1, 0, 0, 0, 0);
			}
		}

		// Mouth
		Vec3 pos = position().add(sideways.x * 3, -1, sideways.z * 3);
		for (int i = 0; i < 50; i++) {
			serverWorld.sendParticles(ModParticles.MAGIC_SMOKE_PARTICLE, pos.x, pos.y + random.nextDouble() - 0.5,
					pos.z, 1, 0, 0, 0, 0);
			pos = pos.add(-sideways.x * 0.12, 0, -sideways.z * 0.12);
		}
	}

	@Override
	protected void defineSynchedData() {
	}
}
