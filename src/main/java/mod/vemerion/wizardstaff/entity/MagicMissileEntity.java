package mod.vemerion.wizardstaff.entity;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.capability.Wizard;
import mod.vemerion.wizardstaff.init.ModEntities;
import mod.vemerion.wizardstaff.init.ModMagics;
import mod.vemerion.wizardstaff.init.ModSounds;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkHooks;

public class MagicMissileEntity extends AbstractArrow implements ItemSupplier {

	private static final EntityDataAccessor<ItemStack> STACK = SynchedEntityData.defineId(MagicMissileEntity.class,
			EntityDataSerializers.ITEM_STACK);

	private boolean released = false;

	public MagicMissileEntity(EntityType<? extends MagicMissileEntity> type, Level level) {
		super(type, level);
		this.setNoGravity(true);
		this.setBaseDamage(6);
	}

	public MagicMissileEntity(double x, double y, double z, Level level, int damage) {
		super(ModEntities.MAGIC_MISSILE_ENTITY.get(), x, y, z, level);
		this.setNoGravity(true);
		this.setBaseDamage(damage);
	}

	public void setItem(Item item) {
		this.entityData.set(STACK, item.getDefaultInstance());
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(STACK, ItemStack.EMPTY);
	}

	@Override
	public boolean shouldBeSaved() {
		return false;
	}

	@Override
	public void tick() {
		super.tick();

		if (!level.isClientSide) {
			var owner = getOwner();
			if (owner instanceof Player player) {
				if (!released && !Wizard.isUsingMagic(ModMagics.MISSILE_MAGIC.get(), player)) {
					released = true;
					shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 1, 0);
				}
			} else {
				discard();
			}
		}
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
			if (getOwner() instanceof Player player) {
				target.hurt(Magic.magicDamage(this, player), (float) getBaseDamage());
			}
		}
	}

	@Override
	public boolean isPickable() {
		return false;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		return false;
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected ItemStack getPickupItem() {
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack getItem() {
		return entityData.get(STACK);
	}

	@Override
	protected SoundEvent getDefaultHitGroundSoundEvent() {
		return ModSounds.IMPACT.get();
	}

}
