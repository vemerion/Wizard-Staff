package mod.vemerion.wizardstaff.entity;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.init.ModEntities;
import mod.vemerion.wizardstaff.particle.MagicDustParticleData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class MushroomCloudEntity extends MagicEntity {
	private static final int DURATION = 20 * 10;


	public MushroomCloudEntity(EntityType<? extends MushroomCloudEntity> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
		this.setNoGravity(true);
	}

	public MushroomCloudEntity(World worldIn, PlayerEntity shooter) {
		this(ModEntities.MUSHROOM_CLOUD, worldIn);
		this.setCaster(shooter);
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
		PlayerEntity caster = getCaster(world);
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

	@Override
	protected void registerData() {
	}
}
