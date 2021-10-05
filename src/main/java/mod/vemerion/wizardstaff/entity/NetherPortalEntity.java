package mod.vemerion.wizardstaff.entity;

import java.util.function.Function;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.init.ModParticles;
import net.minecraft.block.Blocks;
import net.minecraft.block.PortalInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

// Not really a nether portal anymore, but a generic portal to any dimension
public class NetherPortalEntity extends Entity implements IEntityAdditionalSpawnData {

	private static final ResourceLocation DEFAULT_TEXTURE = new ResourceLocation(Main.MODID,
			"textures/entity/nether_portal.png");

	private static final int MAX_DURATION = 20 * 30;

	private int duration;
	private ResourceLocation texture;
	private RegistryKey<World> dimension;

	public NetherPortalEntity(EntityType<? extends NetherPortalEntity> entityTypeIn, World worldIn,
			RegistryKey<World> dimension, ResourceLocation texture) {
		super(entityTypeIn, worldIn);
		setNoGravity(true);
		this.texture = texture;
		this.dimension = dimension;
	}

	public NetherPortalEntity(EntityType<? extends NetherPortalEntity> entityTypeIn, World worldIn) {
		this(entityTypeIn, worldIn, World.THE_NETHER, DEFAULT_TEXTURE);
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
			serverWorld.spawnParticle(ModParticles.MAGIC_SMOKE_PARTICLE, pos.x, pos.y, pos.z, 1, 0, 0, 0, 0);
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
				ServerWorld portalWorld = ((ServerWorld) world).getServer().getWorld(dimension);
				if (portalWorld != null) {
					try {
						ObfuscationReflectionHelper.setPrivateValue(Entity.class, player,
								player.getPosition().toImmutable(), "field_242271_ac");
						player.changeDimension(portalWorld, new MagicTeleporter());
					} catch (RuntimeException e) {
						Main.LOGGER.warn("Unable to use spell to teleport to nether, reason: " + e);
					}
				} else {
					Main.LOGGER.debug("Can not find dimension " + dimension.getLocation().toString());
				}
			}
		}
		return ActionResultType.FAIL;
	}

	public ResourceLocation getTexture() {
		return texture;
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
		if (compound.contains("texture"))
			texture = new ResourceLocation(compound.getString("texture"));
	}

	@Override
	protected void writeAdditional(CompoundNBT compound) {
		compound.putString("texture", texture.toString());
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public void writeSpawnData(PacketBuffer buffer) {
		buffer.writeResourceLocation(texture);
	}

	@Override
	public void readSpawnData(PacketBuffer additionalData) {
		texture = additionalData.readResourceLocation();
	}

	private static class MagicTeleporter implements ITeleporter {
		@Override
		public PortalInfo getPortalInfo(Entity entity, ServerWorld destWorld,
				Function<ServerWorld, PortalInfo> defaultPortalInfo) {
			if (destWorld.getDimensionKey() == World.THE_END)
				return defaultPortalInfo.apply(destWorld);

			BlockPos p = destWorld.getSpawnPoint();
			if (destWorld.isAirBlock(p.down()))
				destWorld.setBlockState(p.down(), Blocks.STONE.getDefaultState());
			for (int i = 0; i < 2; i++)
				if (!destWorld.isAirBlock(p.up(i)))
					destWorld.setBlockState(p.up(i), Blocks.AIR.getDefaultState());
			return new PortalInfo(Vector3d.copyCenteredHorizontally(p), Vector3d.ZERO,
					destWorld.getRandom().nextFloat() * 360, 0);
		}
	}

}
