package mod.vemerion.wizardstaff.entity;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.init.ModParticles;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
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
		Vector3d sideways = Vector3d.fromPitchYaw(0, rotationYaw + 90);
		for (int j = 0; j < 30; j++) {
			float rotation = rand.nextFloat() * (float) Math.PI * 2;
			float offset = rand.nextFloat() * 0.05f - 0.025f;
			Vector3d pos = getPositionVec().add(sideways.x * MathHelper.cos(rotation) * (0.5 + offset),
					1 + MathHelper.sin(rotation) * (1 + offset),
					sideways.z * MathHelper.cos(rotation) * (0.5 + offset));
			serverWorld.spawnParticle(ModParticles.MAGIC_SMOKE_PARTICLE_TYPE, pos.x, pos.y, pos.z, 1, 0, 0, 0, 0);
		}
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	public ActionResultType processInitialInteract(PlayerEntity player, Hand hand) {
		if (!player.world.isRemote) {
			if (duration > 20) {
				World world = player.world;
				player.func_242279_ag(); // Reset portal cooldown
				ServerWorld nether = ((ServerWorld) world).getServer().getWorld(World.THE_NETHER);
				if (nether != null) {
					try {
						ObfuscationReflectionHelper.setPrivateValue(Entity.class, player,
								player.getPosition().toImmutable(), "field_242271_ac");
						player.changeDimension(nether);
					} catch (RuntimeException e) {
						Main.LOGGER.warn("Unable to use spell to teleport to nether, reason: " + e);
					}
				}
			}
		}
		return ActionResultType.FAIL;
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
