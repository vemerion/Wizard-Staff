package mod.vemerion.wizardstaff.entity;

import mod.vemerion.wizardstaff.Magic.Magic;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class MagicSoulSandArmEntity extends MagicEntity {
	private static final int MAX_DURATION = 20 * 10;

	private int duration;
	private float rotation;

	public MagicSoulSandArmEntity(EntityType<? extends MagicSoulSandArmEntity> entityTypeIn, Level levelIn) {
		super(entityTypeIn, levelIn);
		this.setNoGravity(true);
		this.rotation = random.nextFloat() * 360;
	}

	public MagicSoulSandArmEntity(EntityType<? extends MagicSoulSandArmEntity> entityTypeIn, Level levelIn,
			Player caster) {
		this(entityTypeIn, levelIn);
		this.setCaster(caster);
	}

	@Override
	public void tick() {
		super.tick();

		duration++;
		if (!level.isClientSide) {
			if (duration > MAX_DURATION)
				discard();

			if (duration % 10 == 0) {
				Player caster = getCaster(level);
				for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, getBoundingBox())) {
					if (e != caster) {
						e.hurt(caster == null ? Magic.magicDamage() : Magic.magicDamage(this, caster), 2);
						e.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30, 1));
					}
				}
			}
		}
	}

	public float getY(float partialTicks) {
		return Mth.cos(((duration + partialTicks) / (float) MAX_DURATION) * (float) Math.PI * 2) * 2 + 2f;
	}

	public float getRotation() {
		return rotation;
	}

	@Override
	public boolean isPickable() {
		return true;
	}

	@Override
	protected void defineSynchedData() {
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("duration"))
			duration = compound.getInt("duration");
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("duration", duration);
	}
}
