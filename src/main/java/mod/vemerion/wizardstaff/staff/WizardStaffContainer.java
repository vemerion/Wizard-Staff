package mod.vemerion.wizardstaff.staff;

import mod.vemerion.wizardstaff.init.ModContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.items.SlotItemHandler;

public class WizardStaffContainer extends Container {

	private static final int STAFF_SLOT_COUNT = WizardStaffItemHandler.SLOT_COUNT;

	private ItemStack staff;
	private Hand hand;
	private boolean shouldAnimate;

	public static WizardStaffContainer createContainerClientSide(int id, PlayerInventory inventory,
			PacketBuffer buffer) {
		Hand hand = buffer.readBoolean() ? Hand.MAIN_HAND : Hand.OFF_HAND;
		return new WizardStaffContainer(id, inventory, new WizardStaffItemHandler(ItemStack.EMPTY), ItemStack.EMPTY,
				buffer.readBoolean(), hand);
	}

	protected WizardStaffContainer(int id, PlayerInventory inventory, WizardStaffItemHandler handler, ItemStack staff,
			boolean shouldAnimate, Hand hand) {
		super(ModContainers.WIZARD_STAFF, id);
		this.staff = staff;
		this.shouldAnimate = shouldAnimate;
		this.hand = hand;

		// Staff slots
		for (int i = 0; i < WizardStaffItemHandler.SLOT_COUNT; i++) {
			addSlot(new SlotItemHandler(handler, i, 80 - 18 + i * 18, 32));
		}

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
	public boolean canInteractWith(PlayerEntity player) {
		return (player.getHeldItemMainhand() == staff || player.getHeldItemOffhand() == staff) && !staff.isEmpty();
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		Slot slot = this.inventorySlots.get(index);
		ItemStack stack = slot.getStack();
		ItemStack copy = stack.copy();

		if (slot != null && slot.getHasStack() && stack != playerIn.getHeldItem(hand)) {
			if (index < STAFF_SLOT_COUNT) {
				if (!mergeItemStack(stack, STAFF_SLOT_COUNT, STAFF_SLOT_COUNT + 9 * 4, false))
					return ItemStack.EMPTY;
			} else if (index >= STAFF_SLOT_COUNT && index < STAFF_SLOT_COUNT + 9 * 4) {
				if (!mergeItemStack(stack, 0, STAFF_SLOT_COUNT, false))
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
}
