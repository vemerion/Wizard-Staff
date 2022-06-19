package mod.vemerion.wizardstaff.entity;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.init.ModEntities;
import mod.vemerion.wizardstaff.particle.MagicDustParticleData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class MushroomCloudEntity extends MagicEntity {
	private static final int DURATION = 20 * 10;


	public MushroomCloudEntity(EntityType<? extends MushroomCloudEntity> entityTypeIn, Level levelIn) {
		super(entityTypeIn, levelIn);
		this.setNoGravity(true);
	}

	public MushroomCloudEntity(Level levelIn, Player shooter) {
		this(ModEntities.MUSHROOM_CLOUD, levelIn);
		this.setCaster(shooter);
	}

	@Override
	public void tick() {
		super.tick();

		if (!level.isClientSide) {
			damageEntities();

			if (tickCount > DURATION)
				discard();
		} else {
			createParticles();
		}
	}

	private void createParticles() {
		for (int i = 0; i < 10; i++) {
			Vec3 pos = randomParticlePos();
			level.addParticle(new MagicDustParticleData(random.nextFloat() * 0.2f, 0.8f + random.nextFloat() * 0.2f,
					random.nextFloat() * 0.2f, 1), pos.x, pos.y, pos.z, 0, 0.5, 0);
		}
	}

	private Vec3 randomParticlePos() {
		AABB box = getBoundingBox();
		double x = random.nextDouble() * (box.maxX - box.minX) + box.minX;
		double y = random.nextDouble() * (box.maxY - box.minY) + box.minY;
		double z = random.nextDouble() * (box.maxZ - box.minZ) + box.minZ;
		return new Vec3(x, y, z);
	}

	private void damageEntities() {
		Player caster = getCaster(level);
		for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, getBoundingBox(), e -> e != caster && e.isAlive())) {
			if (caster != null) {
				e.hurt(Magic.magicDamage(this, caster), 2);
			} else {
				e.hurt(Magic.magicDamage(), 2);
			}
			if (e.getHealth() <= 0) {
				spawnMushrooms(e.getEyePosition(1));
			}
		}
	}

	private void spawnMushrooms(Vec3 pos) {
		for (int i = 0; i < random.nextInt(5) + 3; i++) {
			Vec3 spawnPos = pos.add(random.nextDouble() - 0.5, random.nextDouble() - 0.5, random.nextDouble() - 0.5);
			ItemEntity mushroom = new ItemEntity(level, spawnPos.x, spawnPos.y, spawnPos.z,
					new ItemStack(random.nextBoolean() ? Items.RED_MUSHROOM : Items.BROWN_MUSHROOM));
			level.addFreshEntity(mushroom);
		}
	}

	@Override
	protected void defineSynchedData() {
	}
}
