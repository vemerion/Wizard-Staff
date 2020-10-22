package mod.vemerion.wizardstaff.staff;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class WizardStaffHandler extends ItemStackHandler {

	private boolean isVisible = true; // Used on client to determine if staff + item should render

	private boolean isDirty = true;

	public boolean isDirty() {
		boolean dirty = isDirty;
		isDirty = false;
		return dirty;
	}

	@Override
	protected void onContentsChanged(int slot) {
		isDirty = true;
	}

	public void setVisible(boolean visible) {
		isVisible = visible;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public static LazyOptional<WizardStaffHandler> getOptional(ItemStack staff) {
		LazyOptional<IItemHandler> itemHandlerOpt = staff.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
		if (itemHandlerOpt.isPresent()) {
			IItemHandler handler = itemHandlerOpt.orElse(null);
			if (handler instanceof WizardStaffHandler)
				return LazyOptional.of(() -> (WizardStaffHandler) handler);
		}
		return LazyOptional.empty();
	}
	
	// Use this on itemstacks that are guaranteed to have capability
	public static WizardStaffHandler get(ItemStack staff) {
		IItemHandler handler = staff.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(() -> new IllegalArgumentException("ItemStack is missing wizard staff capability"));
		return (WizardStaffHandler) handler;
	}
}
