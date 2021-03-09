package mod.vemerion.wizardstaff.init;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.staff.WizardStaffContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(value = Main.MODID)
@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModContainers {

	public static final ContainerType<WizardStaffContainer> WIZARD_STAFF_CONTAINER = null;

	@SubscribeEvent
	public static void onRegisterContainer(RegistryEvent.Register<ContainerType<?>> event) {
		event.getRegistry().register(Init.setup(IForgeContainerType.create(WizardStaffContainer::createContainerClientSide),
				"wizard_staff_container"));
	}
}
