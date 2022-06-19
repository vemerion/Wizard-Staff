package mod.vemerion.wizardstaff.staff;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class WizardStaffCapabilityProvider implements ICapabilitySerializable<Tag> {

	private ItemStack staff;
	private LazyOptional<IItemHandler> instance = LazyOptional.of(this::getInteractionHandler);
	private WizardStaffItemHandler handler;

	public WizardStaffCapabilityProvider(ItemStack staff) {
		this.staff = staff;
	}

	private WizardStaffItemHandler getInteractionHandler() {
		if (handler == null)
			handler = new WizardStaffItemHandler(staff);
		return handler;
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, instance);
	}

	@Override
	public Tag serializeNBT() {
		return getInteractionHandler().serializeNBT();
	}

	@Override
	public void deserializeNBT(Tag nbt) {
		if (nbt instanceof CompoundTag)
			getInteractionHandler().deserializeNBT((CompoundTag) nbt);
		else if (nbt instanceof ListTag)
			legacyDeserialize((ListTag) nbt);
	}

	private void legacyDeserialize(ListTag nbt) {
		if (nbt.size() < 1)
			return;
		CompoundTag compound = nbt.getCompound(0);
		getInteractionHandler().insertItem(0, ItemStack.of(compound), false);
	}

}
