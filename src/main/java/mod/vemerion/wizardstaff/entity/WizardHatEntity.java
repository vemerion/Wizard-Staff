package mod.vemerion.wizardstaff.entity;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.init.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class WizardHatEntity extends AbstractArrowEntity {

	private float hatRotation, prevHatRotation;

	public WizardHatEntity(EntityType<? extends WizardHatEntity> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
		this.setDamage(1);
		this.setHitSound(ModSounds.CLOTH_SOUND);
	}

	public WizardHatEntity(double x, double y, double z, World world) {
		super(Main.WIZARD_HAT_ENTITY, x, y, z, world);
		this.setDamage(1);
		this.setHitSound(ModSounds.CLOTH_SOUND);
	}
	
	@Override
	protected SoundEvent getHitEntitySound() {
		return ModSounds.CLOTH_SOUND;
	}

	@Override
	public void tick() {
		super.tick();
		prevHatRotation = hatRotation;
		hatRotation += 20;
	}

	public float getHatRotation(float partialTicks) {
		return MathHelper.lerp(partialTicks, prevHatRotation, hatRotation);
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		super.onImpact(result);
		if (!world.isRemote) {
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
			playSound(getHitEntitySound(), 1, 0.8f + rand.nextFloat() * 0.4f);

			if (target instanceof LivingEntity) {
				Vector3d vector3d = getMotion().mul(1.0D, 0.0D, 1.0D).normalize().scale(2);
				if (vector3d.lengthSquared() > 0.0D)
					((LivingEntity) target).addVelocity(vector3d.x, 0.1D, vector3d.z);
			}
			if (rand.nextFloat() < 0.1 && target instanceof MobEntity) {
				MobEntity mob = (MobEntity) target;
				if (mob.getItemStackFromSlot(EquipmentSlotType.HEAD).isEmpty())
					mob.setItemStackToSlot(EquipmentSlotType.HEAD, new ItemStack(Main.WIZARD_HAT_ITEM));
			}
		}
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
