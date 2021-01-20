package mod.vemerion.wizardstaff.entity;

import mod.vemerion.wizardstaff.Magic.Magic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class MagicSoulSandArmEntity extends Entity {
	private static final int MAX_DURATION = 20 * 10;

	private int duration;
	private float rotation;
	private PlayerEntity caster;

	public MagicSoulSandArmEntity(EntityType<? extends MagicSoulSandArmEntity> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
		this.setNoGravity(true);
		this.rotation = rand.nextFloat() * 360;
	}

	public MagicSoulSandArmEntity(EntityType<? extends MagicSoulSandArmEntity> entityTypeIn, World worldIn,
			PlayerEntity caster) {
		this(entityTypeIn, worldIn);
		this.caster = caster;
	}

	@Override
	public void tick() {
		super.tick();

		duration++;
		if (!world.isRemote) {
			if (duration > MAX_DURATION)
				remove();

			if (duration % 10 == 0) {
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
		if (compound.contains("duration"))
			duration = compound.getInt("duration");
	}

	@Override
	protected void writeAdditional(CompoundNBT compound) {
		compound.putInt("duration", duration);
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
