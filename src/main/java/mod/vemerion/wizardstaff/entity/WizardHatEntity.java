package mod.vemerion.wizardstaff.entity;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.init.ModEntities;
import mod.vemerion.wizardstaff.init.ModItems;
import mod.vemerion.wizardstaff.init.ModSounds;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public class WizardHatEntity extends AbstractArrow {

	private float hatRotation, prevHatRotation;

	public WizardHatEntity(EntityType<? extends WizardHatEntity> entityTypeIn, Level level) {
		super(entityTypeIn, level);
		this.setBaseDamage(1);
		this.setSoundEvent(ModSounds.CLOTH);
	}

	public WizardHatEntity(double x, double y, double z, Level level) {
		super(ModEntities.WIZARD_HAT, x, y, z, level);
		this.setBaseDamage(1);
		this.setSoundEvent(ModSounds.CLOTH);
	}
	
	@Override
	protected SoundEvent getDefaultHitGroundSoundEvent() {
		return ModSounds.CLOTH;
	}

	@Override
	public void tick() {
		super.tick();
		prevHatRotation = hatRotation;
		hatRotation += 20;
	}

	public float getHatRotation(float partialTicks) {
		return Mth.lerp(partialTicks, prevHatRotation, hatRotation);
	}

	@Override
	protected void onHit(HitResult result) {
		super.onHit(result);
		if (!level.isClientSide) {
			discard();
		}
	}

	@Override
	protected void onHitEntity(EntityHitResult result) {
		if (!level.isClientSide) {
			Entity target = result.getEntity();
			if (getOwner() != null && getOwner() instanceof Player) { // getShooter()
				target.hurt(Magic.magicDamage(this, (Player) getOwner()),
						(float) getBaseDamage());
			} else {
				target.hurt(Magic.magicDamage(), (float) getBaseDamage());
			}
			playSound(getDefaultHitGroundSoundEvent(), 1, 0.8f + random.nextFloat() * 0.4f);

			if (target instanceof LivingEntity) {
				Vec3 Vec3 = getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale(2);
				if (Vec3.lengthSqr() > 0.0D)
					((LivingEntity) target).push(Vec3.x, 0.1D, Vec3.z);
			}
			if (random.nextFloat() < 0.1 && target instanceof Mob) {
				Mob mob = (Mob) target;
				if (mob.getItemBySlot(EquipmentSlot.HEAD).isEmpty())
					mob.setItemSlot(EquipmentSlot.HEAD, new ItemStack(ModItems.WIZARD_HAT));
			}
		}
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
