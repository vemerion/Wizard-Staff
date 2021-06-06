package mod.vemerion.wizardstaff.staff;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class WizardStaffCapabilityProvider implements ICapabilitySerializable<INBT> {

	private ItemStack staff;
	private LazyOptional<IItemHandler> instance = LazyOptional.of(this::getHandler);
	private WizardStaffItemHandler handler;

	public WizardStaffCapabilityProvider(ItemStack staff) {
		this.staff = staff;
	}

	private WizardStaffItemHandler getHandler() {
		if (handler == null)
			handler = new WizardStaffItemHandler(staff);
		return handler;
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, instance);
	}

	@Override
	public INBT serializeNBT() {
		return getHandler().serializeNBT();
	}

	@Override
	public void deserializeNBT(INBT nbt) {
		if (nbt instanceof CompoundNBT)
			getHandler().deserializeNBT((CompoundNBT) nbt);
		else if (nbt instanceof ListNBT)
			legacyDeserialize((ListNBT) nbt);
	}

	private void legacyDeserialize(ListNBT nbt) {
		if (nbt.size() < 1)
			return;
		CompoundNBT compound = nbt.getCompound(0);
		getHandler().insertItem(0, ItemStack.read(compound), false);
	}

}
