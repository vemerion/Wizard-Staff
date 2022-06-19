package mod.vemerion.wizardstaff.capability;

import java.util.function.Supplier;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.network.Network;
import net.minecraft.core.Direction;
import net.minecraft.nbt.ByteTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

public class ScreenAnimations implements INBTSerializable<ByteTag> {

	public static final Capability<ScreenAnimations> CAPABILITY = CapabilityManager
			.get(new CapabilityToken<ScreenAnimations>() {
			});

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

	public void encode(final FriendlyByteBuf buffer) {
		buffer.writeBoolean(shouldAnimate);
	}

	public static ScreenAnimations decode(final FriendlyByteBuf buffer) {
		return new ScreenAnimations(buffer.readBoolean());
	}

	public void handle(final Supplier<NetworkEvent.Context> supplier) {
		final NetworkEvent.Context context = supplier.get();
		context.setPacketHandled(true);
		context.enqueueWork(() -> {
			Player player = context.getSender();
			if (player != null) {
				ScreenAnimations screenAnimations = getScreenAnimations(player);
				screenAnimations.setShouldAnimate(shouldAnimate);
			}
		});
	}

	public static void sendMessage(Player player, boolean shouldAnimate) {
		Network.INSTANCE.send(PacketDistributor.SERVER.noArg(), new ScreenAnimations(shouldAnimate));

	}

	public static ScreenAnimations getScreenAnimations(Player player) {
		return player.getCapability(CAPABILITY).orElse(new ScreenAnimations());
	}

	@Override
	public ByteTag serializeNBT() {
		return ByteTag.valueOf(shouldAnimate);
	}

	@Override
	public void deserializeNBT(ByteTag nbt) {
		shouldAnimate = nbt.getAsByte() == 1;
	}

	@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.FORGE)
	public static class ScreenAnimationsProvider implements ICapabilitySerializable<ByteTag> {

		private LazyOptional<ScreenAnimations> instance = LazyOptional.of(ScreenAnimations::new);

		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
			return CAPABILITY.orEmpty(cap, instance);
		}

		@Override
		public ByteTag serializeNBT() {
			return instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!"))
					.serializeNBT();
		}

		@Override
		public void deserializeNBT(ByteTag nbt) {
			instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!"))
					.deserializeNBT(nbt);
		}

		public static final ResourceLocation SCREEN_ANIMATIONS_LOCATION = new ResourceLocation(Main.MODID,
				"screenanimations");

		@SubscribeEvent
		public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
			if (event.getObject() instanceof Player)
				event.addCapability(SCREEN_ANIMATIONS_LOCATION, new ScreenAnimationsProvider());
		}
	}
}
