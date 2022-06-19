package mod.vemerion.wizardstaff.capability;

import mod.vemerion.wizardstaff.Main;
import net.minecraft.core.Direction;
import net.minecraft.nbt.DoubleTag;
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

// Class for holding fraction part of experience spell cost
public class Experience implements INBTSerializable<DoubleTag> {

	public static final Capability<Experience> CAPABILITY = CapabilityManager.get(new CapabilityToken<Experience>(){});

	private double exp;

	public int add(double value) {
		exp += value;
		int whole = (int) exp;
		exp -= whole;
		return whole;
	}

	public static int add(Player player, double value) {
		return player.getCapability(CAPABILITY).orElse(new Experience()).add(value);
	}

	@Override
	public DoubleTag serializeNBT() {
		return DoubleTag.valueOf(exp);
	}

	@Override
	public void deserializeNBT(DoubleTag nbt) {
		exp = nbt.getAsDouble();
	}

	@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.FORGE)
	public static class ExperienceProvider implements ICapabilitySerializable<DoubleTag> {

		private LazyOptional<Experience> instance = LazyOptional.of(Experience::new);

		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
			return CAPABILITY.orEmpty(cap, instance);
		}

		@Override
		public DoubleTag serializeNBT() {
			return instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!"))
					.serializeNBT();
		}

		@Override
		public void deserializeNBT(DoubleTag nbt) {
			instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!"))
					.deserializeNBT(nbt);
		}

		public static final ResourceLocation EXPERIENCE_LOCATION = new ResourceLocation(Main.MODID, "experience");

		@SubscribeEvent
		public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
			if (event.getObject() instanceof Player)
				event.addCapability(EXPERIENCE_LOCATION, new ExperienceProvider());
		}
	}
}
