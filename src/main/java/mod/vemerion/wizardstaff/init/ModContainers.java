package mod.vemerion.wizardstaff.init;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.container.MagicContainer;
import mod.vemerion.wizardstaff.staff.WizardStaffContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModContainers {
	
	public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Main.MODID);

	public static final RegistryObject<MenuType<WizardStaffContainer>> WIZARD_STAFF = MENUS.register("wizard_staff", () -> IForgeMenuType.create(WizardStaffContainer::createContainerClientSide));
	public static final RegistryObject<MenuType<MagicContainer>> MAGIC = MENUS.register("magic", () -> IForgeMenuType.create(MagicContainer::createContainerClientSide));
}
