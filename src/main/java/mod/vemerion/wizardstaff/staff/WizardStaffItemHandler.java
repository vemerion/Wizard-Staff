package mod.vemerion.wizardstaff.staff;

import mod.vemerion.wizardstaff.Main;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class WizardStaffItemHandler extends ItemStackHandler {

	public static final int SLOT_COUNT = 3;

	private boolean isVisible = true; // Used on client to determine if staff + item should render
	private ItemStack staff;
	private int current;

	public WizardStaffItemHandler(ItemStack staff) {
		super(SLOT_COUNT);
		this.staff = staff;
	}

	public void cycleCurrent() {
		int start = current;
		do {
			current = (current + 1) % SLOT_COUNT;
		} while (getCurrent().isEmpty() && current != start);
	}

	public ItemStack extractCurrent() {
		return extractItem(current, 1, false);
	}

	public ItemStack insertCurrent(ItemStack insert) {
		return insertItem(current, insert, false);
	}

	public ItemStack getCurrent() {
		return getStackInSlot(current);
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag nbt = super.serializeNBT();
		nbt.putInt("current", current);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		super.deserializeNBT(nbt);
		if (nbt.contains("current"))
			current = nbt.getInt("current");
	}

	@Override
	public int getSlotLimit(int slot) {
		return 1;
	}

	@Override
	protected void onContentsChanged(int slot) {
		CompoundTag nbt = staff.getOrCreateTag();
		nbt.putBoolean(Main.MODID + "-dirty", !nbt.getBoolean(Main.MODID + "-dirty"));
	}

	public void setVisible(boolean visible) {
		isVisible = visible;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public static WizardStaffItemHandler orNull(ItemStack staff) {
		return (WizardStaffItemHandler) staff.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
	}

	public static LazyOptional<WizardStaffItemHandler> getOptional(ItemStack staff) {
		LazyOptional<IItemHandler> itemInteractionHandlerOpt = staff.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
		if (itemInteractionHandlerOpt.isPresent()) {
			IItemHandler handler = itemInteractionHandlerOpt.orElse(null);
			if (handler instanceof WizardStaffItemHandler)
				return LazyOptional.of(() -> (WizardStaffItemHandler) handler);
		}
		return LazyOptional.empty();
	}

	// Use this on itemstacks that are guaranteed to have capability
	public static WizardStaffItemHandler get(ItemStack staff) {
		IItemHandler handler = staff.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
				.orElseThrow(() -> new IllegalArgumentException("ItemStack is missing wizard staff capability"));
		return (WizardStaffItemHandler) handler;
	}
}
