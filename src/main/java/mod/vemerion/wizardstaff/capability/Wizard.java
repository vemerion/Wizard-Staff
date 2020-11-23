package mod.vemerion.wizardstaff.capability;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.entity.GrapplingHookEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceContext.BlockMode;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

// Class for holding data on player related to magics
public class Wizard {
	@CapabilityInject(Wizard.class)
	public static final Capability<Wizard> CAPABILITY = null;

	GrapplingHookEntity grapplingHook;

	public Wizard() {

	}

	public boolean throwGrapplingHook(World world, PlayerEntity player) {
		if (!world.isRemote) {
			Vec3d start = player.getEyePosition(0.5f);
			Vec3d end = start.add(Vec3d.fromPitchYaw(player.getPitchYaw()).scale(10));
			BlockRayTraceResult raytrace = world
					.rayTraceBlocks(new RayTraceContext(start, end, BlockMode.OUTLINE, FluidMode.NONE, player));
			if (raytrace.getType() == Type.BLOCK) {
				GrapplingHookEntity hook = new GrapplingHookEntity(world, player);
				Vec3d pos = raytrace.getHitVec().subtract(end.subtract(start).normalize().scale(0.07));
				hook.setLocationAndAngles(pos.x, pos.y, pos.z, player.rotationYaw, player.rotationPitch);
				world.addEntity(hook);
				grapplingHook = hook;
				return true;
			}
		}
		return false;
	}

	public void reelGrapplingHook(World world, PlayerEntity player) {
		if (grapplingHook != null && grapplingHook.isAlive()) {
			Vec3d direction = grapplingHook.getPositionVec().subtract(player.getPositionVec()).normalize().scale(1.2);
			Vec3d motion = player.getMotion().add(direction);
			player.setMotion(motion);

			if (!world.isRemote)
				grapplingHook.remove();
		}
	}

	public void setGrapplingHook(GrapplingHookEntity hook) {
		this.grapplingHook = hook;
	}

	public static LazyOptional<Wizard> getWizard(PlayerEntity player) {
		return player.getCapability(CAPABILITY);
	}

	public CompoundNBT save() {
		CompoundNBT compound = new CompoundNBT();

		return compound;
	}

	public void load(CompoundNBT compound) {

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
			return instance.save();

		}

		@Override
		public void readNBT(Capability<Wizard> capability, Wizard instance, Direction side, INBT nbt) {
			instance.load((CompoundNBT) nbt);
		}
	}
}
