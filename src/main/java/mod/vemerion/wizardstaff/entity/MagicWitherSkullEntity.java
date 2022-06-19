package mod.vemerion.wizardstaff.entity;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.init.ModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public class MagicWitherSkullEntity extends AbstractArrow {

	public MagicWitherSkullEntity(EntityType<? extends MagicWitherSkullEntity> type, Level leve) {
		super(type, leve);
		this.setNoGravity(true);
		this.setBaseDamage(6);
	}

	public MagicWitherSkullEntity(double x, double y, double z, Level leve) {
		super(ModEntities.MAGIC_WITHER_SKULL, x, y, z, leve);
		this.setNoGravity(true);
		this.setBaseDamage(6);
	}

	@Override
	public void tick() {
		super.tick();
		if (!level.isClientSide) {
			if (tickCount > 20 * 7 || isInWater()) {
				smoke();
				discard();
			}
		}
	}

	private void smoke() {
		playSound(SoundEvents.FIRE_EXTINGUISH, 1, 1);
		for (int i = 0; i < 10; i++) {
			Vec3 position = position().add(random.nextDouble() * 0.5 - 0.25, random.nextDouble() * 0.5 - 0.25,
					random.nextDouble() * 0.5 - 0.25);
			((ServerLevel) level).sendParticles(ParticleTypes.SMOKE, position.x, position.y, position.z, 1, 0, 0, 0, 0);
		}
	}

	@Override
	protected void onHit(HitResult result) {
		super.onHit(result);
		if (!level.isClientSide) {
			level.explode(this, this.getX(), this.getY(), this.getZ(), 1.0F, false, Explosion.BlockInteraction.DESTROY);
			discard();
		}

	}

	@Override
	protected void onHitEntity(EntityHitResult result) {
		if (!level.isClientSide) {
			Entity target = result.getEntity();
			if (getOwner() != null && getOwner() instanceof Player) {
				target.hurt(Magic.magicDamage(this, (Player) getOwner()), (float) getBaseDamage());
			} else {
				target.hurt(Magic.magicDamage(), (float) getBaseDamage());
			}

			if (target instanceof LivingEntity) {
				((LivingEntity) target).addEffect(new MobEffectInstance(MobEffects.WITHER, 60, 1));
			}
		}
	}

	@Override
	public boolean isPickable() {
		return false;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		return false;
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected ItemStack getPickupItem() {
		return ItemStack.EMPTY;
	}

}
