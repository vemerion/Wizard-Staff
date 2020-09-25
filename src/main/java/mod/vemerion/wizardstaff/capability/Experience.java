package mod.vemerion.wizardstaff.capability;

import mod.vemerion.wizardstaff.Main;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.DoubleNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;


// Class for holding fraction part of experience spell cost
public class Experience {
	@CapabilityInject(Experience.class)
	public static final Capability<Experience> CAPABILITY = null;

	private double exp;
	
	public int add(double value) {
		exp += value;
		int whole = (int) exp;
		exp -= whole;
		return whole;
	}
	
	public static int add(PlayerEntity player, double value) {
		return player.getCapability(CAPABILITY).orElse(new Experience()).add(value);
	}
	
	@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.FORGE)
	public static class ExperienceProvider implements ICapabilitySerializable<INBT> {

		private LazyOptional<Experience> instance = LazyOptional.of(CAPABILITY::getDefaultInstance);

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

		public static final ResourceLocation EXPERIENCE_LOCATION = new ResourceLocation(Main.MODID,
				"experience");

		@SubscribeEvent
		public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
			if (event.getObject() instanceof PlayerEntity)
				event.addCapability(EXPERIENCE_LOCATION, new ExperienceProvider());
		}
	}

	public static class ExperienceStorage implements IStorage<Experience> {

		@Override
		public INBT writeNBT(Capability<Experience> capability, Experience instance, Direction side) {
			return DoubleNBT.valueOf(instance.exp);

		}

		@Override
		public void readNBT(Capability<Experience> capability, Experience instance, Direction side,
				INBT nbt) {
			instance.exp = ((DoubleNBT) nbt).getDouble();
		}
	}
}
