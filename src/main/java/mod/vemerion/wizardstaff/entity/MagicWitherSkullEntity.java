package mod.vemerion.wizardstaff.entity;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.init.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

public class MagicWitherSkullEntity extends AbstractArrowEntity {

	public MagicWitherSkullEntity(EntityType<? extends MagicWitherSkullEntity> type, World world) {
		super(type, world);
		this.setNoGravity(true);
		this.setDamage(6);
	}

	public MagicWitherSkullEntity(double x, double y, double z, World world) {
		super(ModEntities.MAGIC_WITHER_SKULL, x, y, z, world);
		this.setNoGravity(true);
		this.setDamage(6);
	}

	@Override
	public void tick() {
		super.tick();
		if (!world.isRemote) {
			if (ticksExisted > 20 * 7 || isInWater()) {
				smoke();
				remove();
			}
		}
	}

	private void smoke() {
		playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 1, 1);
		for (int i = 0; i < 10; i++) {
			net.minecraft.util.math.vector.Vector3d position = getPositionVec().add(rand.nextDouble() * 0.5 - 0.25, rand.nextDouble() * 0.5 - 0.25,
					rand.nextDouble() * 0.5 - 0.25);
			((ServerWorld) world).spawnParticle(ParticleTypes.SMOKE, position.x, position.y, position.z, 1, 0, 0, 0, 0);
		}
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		super.onImpact(result);
		if (!world.isRemote) {
			world.createExplosion(this, this.getPosX(), this.getPosY(), this.getPosZ(), 1.0F, false,
					Explosion.Mode.DESTROY);
			remove();
		}

	}

	@Override
	protected void onEntityHit(EntityRayTraceResult result) {
		if (!world.isRemote) {
			Entity target = result.getEntity();
			if (func_234616_v_() != null && func_234616_v_() instanceof PlayerEntity) { // getShooter()
				target.attackEntityFrom(Magic.magicDamage(this, (PlayerEntity) func_234616_v_()),
						(float) getDamage());
			} else {
				target.attackEntityFrom(Magic.magicDamage(), (float) getDamage());
			}

			if (target instanceof LivingEntity) {
				((LivingEntity) target).addPotionEffect(new EffectInstance(Effects.WITHER, 60, 1));
			}
		}
	}

	@Override
	public boolean canBeCollidedWith() {
		return false;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		return false;
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected ItemStack getArrowStack() {
		return ItemStack.EMPTY;
	}

}
