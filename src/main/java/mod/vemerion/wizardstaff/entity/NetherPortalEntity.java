package mod.vemerion.wizardstaff.entity;

import java.util.function.Function;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.init.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.network.NetworkHooks;

// Not really a nether portal anymore, but a generic portal to any dimension
public class NetherPortalEntity extends Entity implements IEntityAdditionalSpawnData {

	private static final ResourceLocation DEFAULT_TEXTURE = new ResourceLocation(Main.MODID,
			"textures/entity/nether_portal.png");

	private static final int MAX_DURATION = 20 * 30;

	private int duration;
	private ResourceLocation texture;
	private ResourceKey<Level> dimension;

	public NetherPortalEntity(EntityType<? extends NetherPortalEntity> entityTypeIn, Level level,
			ResourceKey<Level> dimension, ResourceLocation texture) {
		super(entityTypeIn, level);
		setNoGravity(true);
		this.texture = texture;
		this.dimension = dimension;
	}

	public NetherPortalEntity(EntityType<? extends NetherPortalEntity> entityTypeIn, Level level) {
		this(entityTypeIn, level, Level.NETHER, DEFAULT_TEXTURE);
	}

	@Override
	public void tick() {
		super.tick();

		if (!level.isClientSide) {
			spawnParticles();

			if (duration++ > MAX_DURATION)
				discard();
		}
	}

	private void spawnParticles() {
		ServerLevel serverWorld = (ServerLevel) level;
		Vec3 sideways = Vec3.directionFromRotation(0, getYRot() + 90);
		for (int j = 0; j < 30; j++) {
			float rotation = random.nextFloat() * (float) Math.PI * 2;
			float offset = random.nextFloat() * 0.05f - 0.025f;
			Vec3 pos = position().add(sideways.x * Mth.cos(rotation) * (0.5 + offset),
					1 + Mth.sin(rotation) * (1 + offset), sideways.z * Mth.cos(rotation) * (0.5 + offset));
			serverWorld.sendParticles(ModParticles.MAGIC_SMOKE_PARTICLE, pos.x, pos.y, pos.z, 1, 0, 0, 0, 0);
		}
	}

	@Override
	public boolean isPickable() {
		return true;
	}

	@Override
	public InteractionResult interact(Player player, InteractionHand hand) {
		if (!player.level.isClientSide) {
			if (duration > 20) {
				Level level = player.level;
				player.setPortalCooldown(); // Reset portal cooldown
				ServerLevel portalWorld = ((ServerLevel) level).getServer().getLevel(dimension);
				if (portalWorld != null) {
					try {
						ObfuscationReflectionHelper.setPrivateValue(Entity.class, player,
								player.blockPosition().immutable(), "f_19819_");
						player.changeDimension(portalWorld, new MagicTeleporter());
					} catch (RuntimeException e) {
						Main.LOGGER.warn("Unable to use spell to teleport to nether, reason: " + e);
					}
				} else {
					Main.LOGGER.debug("Can not find dimension " + dimension.location().toString());
				}
			}
		}
		return InteractionResult.FAIL;
	}

	public ResourceLocation getTexture() {
		return texture;
	}

	@Override
	public float getBrightness() {
		return 15;
	}

	@Override
	protected void defineSynchedData() {
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag compound) {
		if (compound.contains("texture"))
			texture = new ResourceLocation(compound.getString("texture"));
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag compound) {
		compound.putString("texture", texture.toString());
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public void writeSpawnData(FriendlyByteBuf buffer) {
		buffer.writeResourceLocation(texture);
	}

	@Override
	public void readSpawnData(FriendlyByteBuf additionalData) {
		texture = additionalData.readResourceLocation();
	}

	private static class MagicTeleporter implements ITeleporter {
		@Override
		public PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld,
				Function<ServerLevel, PortalInfo> defaultPortalInfo) {
			if (destWorld.dimension() == Level.END)
				return defaultPortalInfo.apply(destWorld);

			BlockPos p = destWorld.getSharedSpawnPos();
			if (destWorld.isEmptyBlock(p.below()))
				destWorld.setBlockAndUpdate(p.below(), Blocks.STONE.defaultBlockState());
			for (int i = 0; i < 2; i++)
				if (!destWorld.isEmptyBlock(p.above(i)))
					destWorld.setBlockAndUpdate(p.above(i), Blocks.AIR.defaultBlockState());
			return new PortalInfo(Vec3.atBottomCenterOf(p), Vec3.ZERO, destWorld.getRandom().nextFloat() * 360, 0);
		}
	}

}
