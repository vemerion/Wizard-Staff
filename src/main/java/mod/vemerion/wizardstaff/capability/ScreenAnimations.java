package mod.vemerion.wizardstaff.capability;

import java.util.function.Supplier;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.network.Network;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.ByteNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

public class ScreenAnimations implements INBTSerializable<ByteNBT> {

	@CapabilityInject(ScreenAnimations.class)
	public static final Capability<ScreenAnimations> CAPABILITY = null;

	private boolean shouldAnimate;

	public ScreenAnimations() {
		shouldAnimate = true;
	}

	public ScreenAnimations(boolean shouldAnimate) {
		this.shouldAnimate = shouldAnimate;
	}

	public boolean shouldAnimate() {
		return shouldAnimate;
	}

	public void setShouldAnimate(boolean value) {
		shouldAnimate = value;
	}

	public void encode(final PacketBuffer buffer) {
		buffer.writeBoolean(shouldAnimate);
	}

	public static ScreenAnimations decode(final PacketBuffer buffer) {
		return new ScreenAnimations(buffer.readBoolean());
	}

	public void handle(final Supplier<NetworkEvent.Context> supplier) {
		final NetworkEvent.Context context = supplier.get();
		context.setPacketHandled(true);
		context.enqueueWork(() -> {
			PlayerEntity player = context.getSender();
			if (player != null) {
				ScreenAnimations screenAnimations = getScreenAnimations(player);
				screenAnimations.setShouldAnimate(shouldAnimate);
			}
		});
	}

	public static void sendMessage(PlayerEntity player, boolean shouldAnimate) {
		Network.INSTANCE.send(PacketDistributor.SERVER.noArg(), new ScreenAnimations(shouldAnimate));

	}

	public static ScreenAnimations getScreenAnimations(PlayerEntity player) {
		return player.getCapability(CAPABILITY).orElse(new ScreenAnimations());
	}
	
	@Override
	public ByteNBT serializeNBT() {
		return ByteNBT.valueOf(shouldAnimate);
	}

	@Override
	public void deserializeNBT(ByteNBT nbt) {
		shouldAnimate = nbt.getByte() == 1;
	}

	@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.FORGE)
	public static class ScreenAnimationsProvider implements ICapabilitySerializable<INBT> {

		private LazyOptional<ScreenAnimations> instance = LazyOptional.of(CAPABILITY::getDefaultInstance);

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

		public static final ResourceLocation SCREEN_ANIMATIONS_LOCATION = new ResourceLocation(Main.MODID,
				"screenanimations");

		@SubscribeEvent
		public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
			if (event.getObject() instanceof PlayerEntity)
				event.addCapability(SCREEN_ANIMATIONS_LOCATION, new ScreenAnimationsProvider());
		}
	}

	public static class ScreenAnimationsStorage implements IStorage<ScreenAnimations> {

		@Override
		public INBT writeNBT(Capability<ScreenAnimations> capability, ScreenAnimations instance, Direction side) {
			return instance.serializeNBT();

		}

		@Override
		public void readNBT(Capability<ScreenAnimations> capability, ScreenAnimations instance, Direction side,
				INBT nbt) {
			instance.deserializeNBT((ByteNBT) nbt);
		}
	}
}
