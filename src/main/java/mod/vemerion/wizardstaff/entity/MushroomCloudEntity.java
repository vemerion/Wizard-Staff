package mod.vemerion.wizardstaff.entity;

import java.util.UUID;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.init.ModEntities;
import mod.vemerion.wizardstaff.particle.MagicDustParticleData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class MushroomCloudEntity extends Entity {
	private static final int DURATION = 20 * 10;

	private UUID shooter;

	public MushroomCloudEntity(EntityType<? extends MushroomCloudEntity> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
		this.setNoGravity(true);
	}

	public MushroomCloudEntity(World worldIn, PlayerEntity shooter) {
		this(ModEntities.MUSHROOM_CLOUD, worldIn);
		this.shooter = shooter.getUniqueID();
	}

	@Override
	public void tick() {
		super.tick();

		if (!world.isRemote) {
			damageEntities();

			if (ticksExisted > DURATION)
				remove();
		} else {
			createParticles();
		}
	}

	private void createParticles() {
		for (int i = 0; i < 10; i++) {
			Vector3d pos = randomParticlePos();
			world.addParticle(new MagicDustParticleData(rand.nextFloat() * 0.2f, 0.8f + rand.nextFloat() * 0.2f,
					rand.nextFloat() * 0.2f, 1), pos.x, pos.y, pos.z, 0, 0.5, 0);
		}
	}

	private Vector3d randomParticlePos() {
		AxisAlignedBB box = getBoundingBox();
		double x = rand.nextDouble() * (box.maxX - box.minX) + box.minX;
		double y = rand.nextDouble() * (box.maxY - box.minY) + box.minY;
		double z = rand.nextDouble() * (box.maxZ - box.minZ) + box.minZ;
		return new Vector3d(x, y, z);
	}

	private void damageEntities() {
		PlayerEntity caster = getShooter();
		for (LivingEntity e : world.getEntitiesWithinAABB(LivingEntity.class, getBoundingBox(), e -> e != caster && e.isAlive())) {
			if (caster != null) {
				e.attackEntityFrom(Magic.magicDamage(this, caster), 2);
			} else {
				e.attackEntityFrom(Magic.magicDamage(), 2);
			}
			if (e.getHealth() <= 0) {
				spawnMushrooms(e.getEyePosition(1));
			}
		}
	}

	private void spawnMushrooms(Vector3d pos) {
		for (int i = 0; i < rand.nextInt(5) + 3; i++) {
			Vector3d spawnPos = pos.add(rand.nextDouble() - 0.5, rand.nextDouble() - 0.5, rand.nextDouble() - 0.5);
			ItemEntity mushroom = new ItemEntity(world, spawnPos.x, spawnPos.y, spawnPos.z,
					new ItemStack(rand.nextBoolean() ? Items.RED_MUSHROOM : Items.BROWN_MUSHROOM));
			world.addEntity(mushroom);
		}
	}

	private PlayerEntity getShooter() {
		if (shooter == null)
			return null;
		return world.getPlayerByUuid(shooter);
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
