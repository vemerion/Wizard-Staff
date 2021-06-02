package mod.vemerion.wizardstaff.entity;

import mod.vemerion.wizardstaff.Magic.Magic;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class MagicSoulSandArmEntity extends MagicEntity {
	private static final int MAX_DURATION = 20 * 10;

	private int duration;
	private float rotation;

	public MagicSoulSandArmEntity(EntityType<? extends MagicSoulSandArmEntity> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
		this.setNoGravity(true);
		this.rotation = rand.nextFloat() * 360;
	}

	public MagicSoulSandArmEntity(EntityType<? extends MagicSoulSandArmEntity> entityTypeIn, World worldIn,
			PlayerEntity caster) {
		this(entityTypeIn, worldIn);
		this.setCaster(caster);
	}

	@Override
	public void tick() {
		super.tick();

		duration++;
		if (!world.isRemote) {
			if (duration > MAX_DURATION)
				remove();

			if (duration % 10 == 0) {
				PlayerEntity caster = getCaster(world);
				for (LivingEntity e : world.getEntitiesWithinAABB(LivingEntity.class, getBoundingBox())) {
					if (e != caster) {
						e.attackEntityFrom(caster == null ? Magic.magicDamage() : Magic.magicDamage(this, caster), 2);
						e.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 30, 1));
					}
				}
			}
		}
	}

	public float getY(float partialTicks) {
		return MathHelper.cos(((duration + partialTicks) / (float) MAX_DURATION) * (float) Math.PI * 2) * 2 + 2f;
	}

	public float getRotation() {
		return rotation;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	protected void registerData() {
	}

	@Override
	protected void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		if (compound.contains("duration"))
			duration = compound.getInt("duration");
	}

	@Override
	protected void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putInt("duration", duration);
	}
}
