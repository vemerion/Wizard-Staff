package mod.vemerion.wizardstaff.entity;

import java.util.Optional;
import java.util.UUID;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.capability.Wizard;
import mod.vemerion.wizardstaff.staff.WizardStaffItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class GrapplingHookEntity extends Entity {

	private static final DataParameter<Optional<UUID>> SHOOTER = EntityDataManager.createKey(GrapplingHookEntity.class,
			DataSerializers.OPTIONAL_UNIQUE_ID);

	public GrapplingHookEntity(EntityType<? extends GrapplingHookEntity> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
		this.setNoGravity(true);
		this.ignoreFrustumCheck = true;
	}

	public GrapplingHookEntity(World worldIn, PlayerEntity shooter) {
		this(Main.GRAPPLING_HOOK_ENTITY, worldIn);
		this.setShooter(shooter);
	}

	@Override
	public void tick() {
		super.tick();

		if (!world.isRemote) {
			PlayerEntity shooter = getShooter();
			if (shooter == null || this.getDistanceSq(shooter) > 100
					|| !(shooter.getActiveItemStack().getItem() instanceof WizardStaffItem))
				remove();
		}

		if (getShooter() != null && isAlive()) {
			Wizard.getWizardOptional(getShooter()).ifPresent(wizard -> wizard.setGrapplingHook(this));
		}
	}

	public boolean isInRangeToRenderDist(double distance) {
		return distance < 4096;
	}

	public void setShooter(PlayerEntity shooter) {
		dataManager.set(SHOOTER, Optional.of(shooter.getUniqueID()));
	}

	public PlayerEntity getShooter() {
		Optional<UUID> shooter = dataManager.get(SHOOTER);
		if (!shooter.isPresent())
			return null;
		return world.getPlayerByUuid(shooter.get());
	}

	@Override
	protected void registerData() {
		dataManager.register(SHOOTER, Optional.empty());
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void readAdditional(CompoundNBT compound) {

	}

	@Override
	protected void writeAdditional(CompoundNBT compound) {

	}

}
