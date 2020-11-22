package mod.vemerion.wizardstaff.entity;

import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class GrapplingHookEntity extends Entity {

	private UUID shooter;

	public GrapplingHookEntity(EntityType<? extends GrapplingHookEntity> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
		setNoGravity(true);
	}

	public GrapplingHookEntity(EntityType<? extends GrapplingHookEntity> entityTypeIn, World worldIn,
			PlayerEntity shooter) {
		super(entityTypeIn, worldIn);
		this.shooter = shooter.getUniqueID();
		setNoGravity(true);
	}

	@Override
	public void tick() {
		super.tick();

		if (!world.isRemote) {
			if (getShooter() == null)
				remove();
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
