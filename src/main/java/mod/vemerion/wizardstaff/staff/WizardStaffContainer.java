package mod.vemerion.wizardstaff.staff;

import mod.vemerion.wizardstaff.Main;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.SlotItemHandler;

public class WizardStaffContainer extends Container {

	private WizardStaffHandler handler;
	private ItemStack heldItem;
	private boolean shouldAnimate;

	public static WizardStaffContainer createContainerClientSide(int id, PlayerInventory inventory,
			PacketBuffer buffer) {
		return new WizardStaffContainer(id, inventory, new WizardStaffHandler(), ItemStack.EMPTY, buffer.readBoolean());
	}

	protected WizardStaffContainer(int id, PlayerInventory inventory, WizardStaffHandler handler, ItemStack heldItem, boolean shouldAnimate) {
		super(Main.WIZARD_STAFF_CONTAINER, id);
		this.handler = handler;
		this.heldItem = heldItem;
		this.shouldAnimate = shouldAnimate;

		// Staff slot
		addSlot(new SlotItemHandler(handler, 0, 80, 32));

		// Player inventory
		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 9; ++x) {
				this.addSlot(new Slot(inventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
			}
		}

		// Player hotbar
		for (int x = 0; x < 9; ++x) {
			this.addSlot(new Slot(inventory, x, 8 + x * 18, 142));
		}
	}
	
	public boolean shouldAnimate() {
		return shouldAnimate;
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return playerIn.getHeldItemMainhand().equals(heldItem);
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		Slot slot = this.inventorySlots.get(index);
		ItemStack stack = slot.getStack();
		ItemStack copy = stack.copy();
		if (slot != null && slot.getHasStack()) {
			if (index == 0) {
				if (!mergeItemStack(stack, 1, 1 + 9 * 4, false))
					return ItemStack.EMPTY;
			} else if (index > 0 && index < 1 + 9 * 4) {
				if (!mergeItemStack(stack, 0, 1, false))
					return ItemStack.EMPTY;
			} else {
				return ItemStack.EMPTY;
			}
		} else {
			return ItemStack.EMPTY;
		}
		
		if (stack.getCount() == 0)
			slot.putStack(ItemStack.EMPTY);
		else
			slot.onSlotChanged();
		slot.onTake(playerIn, stack);
		
		return copy;
	}

	@Override
	public void detectAndSendChanges() {
		if (handler.isDirty()) {
			CompoundNBT tag = heldItem.getOrCreateTag();
			tag.putBoolean("dirty", !tag.getBoolean("dirty"));
			heldItem.setTag(tag);
		}
		super.detectAndSendChanges();
	}

}
