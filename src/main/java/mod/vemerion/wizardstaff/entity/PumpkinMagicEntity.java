package mod.vemerion.wizardstaff.entity;

import java.util.Random;
import java.util.UUID;

import mod.vemerion.wizardstaff.Main;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

public class PumpkinMagicEntity extends Entity {

	private UUID shooter;

	public PumpkinMagicEntity(EntityType<? extends PumpkinMagicEntity> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
		setNoGravity(true);
	}

	public PumpkinMagicEntity(EntityType<? extends PumpkinMagicEntity> entityTypeIn, World worldIn,
			PlayerEntity shooter) {
		super(entityTypeIn, worldIn);
		this.shooter = shooter.getUniqueID();
		setNoGravity(true);
	}

	@Override
	public void tick() {
		super.tick();

		move(MoverType.SELF, Vec3d.fromPitchYaw(getPitchYaw()).scale(0.1));
		if (!world.isRemote) {
			spawnParticles();
			collision();

			if (ticksExisted > 120)
				remove();
		}
	}

	private PlayerEntity getShooter() {
		if (shooter == null)
			return null;
		return world.getPlayerByUuid(shooter);
	}

	private void collision() {
		Vec3d sideways = Vec3d.fromPitchYaw(0, rotationYaw + 90);
		Vec3d pos = getPositionVec().add(sideways.x * 3, 0, sideways.z * 3);
		PlayerEntity shooter = getShooter();
		DamageSource source = shooter == null ? DamageSource.MAGIC : DamageSource.causePlayerDamage(shooter);
		for (int i = 0; i < 6; i++) {
			for (Entity e : world.getEntitiesInAABBexcluding(shooter, new AxisAlignedBB(pos, pos).grow(1, 2, 1),
					(e) -> e instanceof LivingEntity)) {
				e.attackEntityFrom(source, 1);
				LivingEntity living = (LivingEntity) e;
				living.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 10, 1));
			}
			pos = pos.add(-sideways.x, 0, -sideways.z);
		}
	}

	private void spawnParticles() {
		ServerWorld serverWorld = (ServerWorld) world;
		Vec3d sideways = Vec3d.fromPitchYaw(0, rotationYaw + 90);
		Random random = new Random(0);

		// Eyes
		double radius = 1.7;
		for (int i = -1; i < 2; i += 2) {
			Vec3d pos = getPositionVec().add(sideways.x * 2 * i, 2, sideways.z * 2 * i);
			for (int j = 0; j < 50; j++) {
				Vec3d offset = Vec3d.fromPitchYaw(random.nextFloat() * 360, rotationYaw + 90)
						.scale((radius + random.nextDouble() * 0.4 - 0.2) * random.nextDouble());
				serverWorld.spawnParticle(Main.MAGIC_SMOKE_PARTICLE_TYPE, pos.x + offset.x, pos.y + offset.y, pos.z + offset.z, 1,
						0, 0, 0, 0);
			}
		}

		// Mouth
		Vec3d pos = getPositionVec().add(sideways.x * 3, -1, sideways.z * 3);
		for (int i = 0; i < 50; i++) {
			serverWorld.spawnParticle(Main.MAGIC_SMOKE_PARTICLE_TYPE, pos.x, pos.y + random.nextDouble() - 0.5, pos.z, 1, 0, 0, 0,
					0);
			pos = pos.add(-sideways.x * 0.12, 0, -sideways.z * 0.12);
		}
	}

	@Override
	protected void registerData() {
	}

	@Override
	protected void readAdditional(CompoundNBT compound) {
		if (compound.hasUniqueId("shooter"))
			shooter = compound.getUniqueId("shooter");
	}

	@Override
	protected void writeAdditional(CompoundNBT compound) {
		if (shooter != null)
			compound.putUniqueId("shooter", shooter);
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

}
