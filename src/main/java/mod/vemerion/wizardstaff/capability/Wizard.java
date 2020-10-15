package mod.vemerion.wizardstaff.capability;

import mod.vemerion.wizardstaff.Main;
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
import net.minecraft.util.math.GlobalPos;
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

	private GlobalPos lodestonePos;
	private boolean lodestoneTracked;

	public Wizard() {

	}
	
	public static Wizard getWizard(PlayerEntity player) {
		return player.getCapability(CAPABILITY).orElseThrow(() -> new IllegalArgumentException("Player is missing wizard capability"));
	}

	public boolean lodestoneTeleport(ServerPlayerEntity player) {
		if (lodestoneTracked && lodestonePos != null && player.world.getDimensionKey() == lodestonePos.getDimension()
				&& (player.world.getBlockState(lodestonePos.getPos()).getBlock() == Blocks.LODESTONE)) {
			lodestoneTracked = false;
			BlockPos destination = lodestonePos.getPos();
			player.teleport(player.getServerWorld(), destination.getX(), destination.getY() + 1, destination.getZ(), player.rotationYaw, player.rotationPitch);
			player.world.destroyBlock(destination, false);
			return true;
		}
		return false;
	}

	public void trackLodestone(World world, BlockPos pos) {
		if (world.getBlockState(pos).getBlock() == Blocks.LODESTONE) {
			lodestoneTracked = true;
			lodestonePos = GlobalPos.getPosition(world.getDimensionKey(), pos.toImmutable());
		}
	}

	public CompoundNBT save() {
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

	public void load(CompoundNBT compound) {
		lodestoneTracked = compound.getBoolean("lodestoneTracked");
		if (lodestoneTracked) {
			lodestonePos = GlobalPos.CODEC.parse(NBTDynamicOps.INSTANCE, compound.get("lodestonePos")).result()
					.orElse(null);
		}
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
