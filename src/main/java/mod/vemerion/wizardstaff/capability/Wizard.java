package mod.vemerion.wizardstaff.capability;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.entity.GrapplingHookEntity;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceContext.BlockMode;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

// Class for holding data on player related to magics
public class Wizard implements INBTSerializable<CompoundNBT> {
	@CapabilityInject(Wizard.class)
	public static final Capability<Wizard> CAPABILITY = null;

	private GlobalPos lodestonePos;
	private boolean lodestoneTracked;
	private GrapplingHookEntity grapplingHook;

	public Wizard() {

	}

	public static Wizard getWizard(PlayerEntity player) {
		return player.getCapability(CAPABILITY)
				.orElseThrow(() -> new IllegalArgumentException("Player is missing wizard capability"));
	}

	public static LazyOptional<Wizard> getWizardOptional(PlayerEntity player) {
		return player.getCapability(CAPABILITY);
	}

	public boolean lodestoneTeleport(ServerPlayerEntity player) {
		if (lodestoneTracked && lodestonePos != null && player.world.getDimensionKey() == lodestonePos.getDimension()
				&& (player.world.getBlockState(lodestonePos.getPos()).getBlock() == Blocks.LODESTONE)) {
			lodestoneTracked = false;
			BlockPos destination = lodestonePos.getPos();
			player.teleport(player.getServerWorld(), destination.getX(), destination.getY() + 1, destination.getZ(),
					player.rotationYaw, player.rotationPitch);
			player.world.destroyBlock(destination, false);
			return true;
		}
		return false;
	}

	public boolean throwGrapplingHook(World world, PlayerEntity player) {
		if (!world.isRemote) {
			Vector3d start = player.getEyePosition(0.5f);
			Vector3d end = start.add(Vector3d.fromPitchYaw(player.getPitchYaw()).scale(10));
			BlockRayTraceResult raytrace = world
					.rayTraceBlocks(new RayTraceContext(start, end, BlockMode.OUTLINE, FluidMode.NONE, player));
			if (raytrace.getType() == Type.BLOCK) {
				GrapplingHookEntity hook = new GrapplingHookEntity(world, player);
				Vector3d pos = raytrace.getHitVec().subtract(end.subtract(start).normalize().scale(0.07));
				hook.setLocationAndAngles(pos.x, pos.y, pos.z, player.rotationYaw, player.rotationPitch);
				world.addEntity(hook);
				grapplingHook = hook;
				return true;
			}
		}
		return false;
	}

	public void trackLodestone(World world, BlockPos pos) {
		if (world.getBlockState(pos).getBlock() == Blocks.LODESTONE) {
			lodestoneTracked = true;
			lodestonePos = GlobalPos.getPosition(world.getDimensionKey(), pos.toImmutable());
		}
	}

	public void reelGrapplingHook(World world, PlayerEntity player) {
		if (grapplingHook != null && grapplingHook.isAlive()) {
			Vector3d direction = grapplingHook.getPositionVec().subtract(player.getPositionVec()).normalize()
					.scale(1.2);
			Vector3d motion = player.getMotion().add(direction);
			player.setMotion(motion);

			if (!world.isRemote)
				grapplingHook.remove();
		}
	}

	public void setGrapplingHook(GrapplingHookEntity hook) {
		this.grapplingHook = hook;
	}

	public void deserializeNBT(CompoundNBT compound) {
		lodestoneTracked = compound.getBoolean("lodestoneTracked");
		if (lodestoneTracked) {
			lodestonePos = GlobalPos.CODEC.parse(NBTDynamicOps.INSTANCE, compound.get("lodestonePos")).result()
					.orElse(null);
		}
	}

	public CompoundNBT serializeNBT() {
		CompoundNBT compound = new CompoundNBT();
		if (lodestoneTracked) {
			GlobalPos.CODEC.encodeStart(NBTDynamicOps.INSTANCE, lodestonePos).resultOrPartial(s -> {
			}).ifPresent((lodestonePos) -> {
				compound.put("lodestonePos", lodestonePos);
			});
		}
		compound.putBoolean("lodestoneTracked", lodestoneTracked);
		return compound;
	}

	@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.FORGE)
	public static class WizardProvider implements ICapabilitySerializable<INBT> {

		private LazyOptional<Wizard> instance = LazyOptional.of(CAPABILITY::getDefaultInstance);

		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
			return CAPABILITY.orEmpty(cap, instance);
		}

		@Override
		public INBT serializeNBT() {
			return CAPABILITY.getStorage().writeNBT(CAPABILITY,
					instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null);
		}

		@Override
		public void deserializeNBT(INBT nbt) {
			CAPABILITY.getStorage().readNBT(CAPABILITY,
					instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null,
					nbt);
		}

		public static final ResourceLocation LOCATION = new ResourceLocation(Main.MODID, "wizard");

		@SubscribeEvent
		public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
			if (event.getObject() instanceof PlayerEntity)
				event.addCapability(LOCATION, new WizardProvider());
		}
	}

	public static class WizardStorage implements IStorage<Wizard> {

		@Override
		public INBT writeNBT(Capability<Wizard> capability, Wizard instance, Direction side) {
			return instance.serializeNBT();

		}

		@Override
		public void readNBT(Capability<Wizard> capability, Wizard instance, Direction side, INBT nbt) {
			instance.deserializeNBT((CompoundNBT) nbt);
		}
	}
}
