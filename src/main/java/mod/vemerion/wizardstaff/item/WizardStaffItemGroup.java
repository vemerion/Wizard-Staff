package mod.vemerion.wizardstaff.item;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.init.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.registries.ForgeRegistries;

public class WizardStaffItemGroup extends ItemGroup {
	
	public WizardStaffItemGroup() {
		super(Main.MODID);
	}

	@Override
	public ItemStack createIcon() {
		return new ItemStack(ModItems.WIZARD_HAT_ITEM);
	}

	@Override
	public void fill(NonNullList<ItemStack> items) {
		for (Item item : ForgeRegistries.ITEMS) {
			if (item != null)
				if (item.getRegistryName().getNamespace().equals(Main.MODID)) {
					item.fillItemGroup(ItemGroup.SEARCH, items);
				}
		}
	}
}
