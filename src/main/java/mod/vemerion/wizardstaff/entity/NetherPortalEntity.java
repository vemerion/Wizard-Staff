package mod.vemerion.wizardstaff.entity;

import javax.annotation.Nullable;

import mod.vemerion.wizardstaff.Main;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

public class NetherPortalEntity extends Entity {

	private static final int MAX_DURATION = 20 * 30;

	private int duration;

	public NetherPortalEntity(EntityType<? extends NetherPortalEntity> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
		setNoGravity(true);
	}

	@Override
	public void tick() {
		super.tick();

		if (!world.isRemote) {
			spawnParticles();

			if (duration++ > MAX_DURATION)
				remove();
		}
	}

	private void spawnParticles() {
		ServerWorld serverWorld = (ServerWorld) world;
		Vec3d sideways = Vec3d.fromPitchYaw(0, rotationYaw + 90);
		for (int j = 0; j < 30; j++) {
			float rotation = rand.nextFloat() * (float) Math.PI * 2;
			float offset = rand.nextFloat() * 0.05f - 0.025f;
			Vec3d pos = getPositionVec().add(sideways.x * MathHelper.cos(rotation) * (0.5 + offset),
					1 + MathHelper.sin(rotation) * (1 + offset),
					sideways.z * MathHelper.cos(rotation) * (0.5 + offset));
			serverWorld.spawnParticle(Main.MAGIC_SMOKE_PARTICLE_TYPE, pos.x, pos.y, pos.z, 1, 0, 0, 0, 0);
		}
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	public boolean processInitialInteract(PlayerEntity player, Hand hand) {
		if (!player.world.isRemote) {
			if (duration > 20) {
				player.timeUntilPortal = player.getPortalCooldown();
				player.changeDimension(DimensionType.THE_NETHER);
			}
		}
		return false;
	}

	@Override
	public float getBrightness() {
		return 15;
	}
	
	

	@Override
	protected void registerData() {
	}

	@Override
	protected void readAdditional(CompoundNBT compound) {
	}

	@Override
	protected void writeAdditional(CompoundNBT compound) {
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

}
