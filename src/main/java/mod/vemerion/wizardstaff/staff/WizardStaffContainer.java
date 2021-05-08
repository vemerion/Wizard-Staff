package mod.vemerion.wizardstaff.staff;

import mod.vemerion.wizardstaff.init.ModContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.items.SlotItemHandler;

public class WizardStaffContainer extends Container {

	private WizardStaffItemHandler handler;
	private ItemStack staff;
	private Hand hand;
	private boolean shouldAnimate;

	public static WizardStaffContainer createContainerClientSide(int id, PlayerInventory inventory,
			PacketBuffer buffer) {
		Hand hand = buffer.readBoolean() ? Hand.MAIN_HAND : Hand.OFF_HAND;
		return new WizardStaffContainer(id, inventory, new WizardStaffItemHandler(), ItemStack.EMPTY,
				buffer.readBoolean(), hand);
	}

	protected WizardStaffContainer(int id, PlayerInventory inventory, WizardStaffItemHandler handler, ItemStack staff,
			boolean shouldAnimate, Hand hand) {
		super(ModContainers.WIZARD_STAFF, id);
		this.handler = handler;
		this.staff = staff;
		this.shouldAnimate = shouldAnimate;
		this.hand = hand;

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
	public boolean canInteractWith(PlayerEntity player) {
		return (player.getHeldItemMainhand() == staff || player.getHeldItemOffhand() == staff) && !staff.isEmpty();
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		Slot slot = this.inventorySlots.get(index);
		ItemStack stack = slot.getStack();
		ItemStack copy = stack.copy();

		if (slot != null && slot.getHasStack() && stack != playerIn.getHeldItem(hand)) {
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
			CompoundNBT tag = staff.getOrCreateTag();
			tag.putBoolean("dirty", !tag.getBoolean("dirty"));
			staff.setTag(tag);
		}
		super.detectAndSendChanges();
	}

}
