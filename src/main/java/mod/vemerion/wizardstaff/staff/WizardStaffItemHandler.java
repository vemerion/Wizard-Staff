package mod.vemerion.wizardstaff.staff;

import net.minecraftforge.items.ItemStackHandler;

public class WizardStaffItemHandler extends ItemStackHandler {

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

	@Override
	public int getSlotLimit(int slot) {
		return 1;
	}
}
