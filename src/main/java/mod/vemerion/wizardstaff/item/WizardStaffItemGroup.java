package mod.vemerion.wizardstaff.item;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.init.ModItems;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class WizardStaffItemGroup extends CreativeModeTab {
	
	public WizardStaffItemGroup() {
		super(Main.MODID);
	}

	@Override
	public ItemStack makeIcon() {
		return new ItemStack(ModItems.WIZARD_HAT);
	}

	@Override
	public void fillItemList(NonNullList<ItemStack> items) {
		for (Item item : ForgeRegistries.ITEMS) {
			if (item != null)
				if (item.getRegistryName().getNamespace().equals(Main.MODID)) {
					item.fillItemCategory(CreativeModeTab.TAB_SEARCH, items);
				}
		}
	}
}
