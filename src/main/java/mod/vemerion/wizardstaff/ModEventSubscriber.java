package mod.vemerion.wizardstaff;

import mod.vemerion.wizardstaff.entity.PumpkinMagicEntity;
import mod.vemerion.wizardstaff.item.WizardHatItem;
import mod.vemerion.wizardstaff.staff.WizardStaffContainer;
import mod.vemerion.wizardstaff.staff.WizardStaffItem;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.IForgeRegistryEntry;

@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventSubscriber {

	@SubscribeEvent
	public static void onRegisterItem(RegistryEvent.Register<Item> event) {
		event.getRegistry().register(setup(new WizardStaffItem(), "wizard_staff_item"));
		event.getRegistry().register(setup(new WizardHatItem(), "wizard_hat_item"));

	}

	@SubscribeEvent
	public static void onRegisterContainer(RegistryEvent.Register<ContainerType<?>> event) {
		event.getRegistry().register(setup(IForgeContainerType.create(WizardStaffContainer::createContainerClientSide), "wizard_staff_container"));
	}
	
	@SubscribeEvent
	public static void onRegisterEntity(RegistryEvent.Register<EntityType<?>> event) {
		EntityType<PumpkinMagicEntity> pumpkinMagicEntity = EntityType.Builder
				.<PumpkinMagicEntity>create(PumpkinMagicEntity::new, EntityClassification.MISC).size(1, 1F).build("pumpkin_magic_entity");
		event.getRegistry().register(setup(pumpkinMagicEntity, "pumpkin_magic_entity"));

	}
	
	@SubscribeEvent
	public static void onRegisterSound(RegistryEvent.Register<SoundEvent> event) {
		SoundEvent clock_sound = new SoundEvent(new ResourceLocation(Main.MODID, "clock_sound"));
		event.getRegistry().register(setup(clock_sound, "clock_sound"));
		SoundEvent plop_sound = new SoundEvent(new ResourceLocation(Main.MODID, "plop_sound"));
		event.getRegistry().register(setup(plop_sound, "plop_sound"));
		SoundEvent pumpkin_magic_sound = new SoundEvent(new ResourceLocation(Main.MODID, "pumpkin_magic_sound"));
		event.getRegistry().register(setup(pumpkin_magic_sound, "pumpkin_magic_sound"));
		SoundEvent ray_sound = new SoundEvent(new ResourceLocation(Main.MODID, "ray_sound"));
		event.getRegistry().register(setup(ray_sound, "ray_sound"));
		SoundEvent scribble_sound = new SoundEvent(new ResourceLocation(Main.MODID, "scribble_sound"));
		event.getRegistry().register(setup(scribble_sound, "scribble_sound"));
		SoundEvent woosh_sound = new SoundEvent(new ResourceLocation(Main.MODID, "woosh_sound"));
		event.getRegistry().register(setup(woosh_sound, "woosh_sound"));
		SoundEvent burning_sound = new SoundEvent(new ResourceLocation(Main.MODID, "burning_sound"));
		event.getRegistry().register(setup(burning_sound, "burning_sound"));

	}      

	public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final String name) {
		return setup(entry, new ResourceLocation(Main.MODID, name));
	}

	public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final ResourceLocation registryName) {
		entry.setRegistryName(registryName);
		return entry;
	}

}
