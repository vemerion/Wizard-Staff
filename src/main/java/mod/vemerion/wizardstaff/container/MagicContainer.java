package mod.vemerion.wizardstaff.container;

import mod.vemerion.wizardstaff.capability.Wizard;
import mod.vemerion.wizardstaff.init.ModContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class MagicContainer extends Container {

	public static MagicContainer createContainerClientSide(int id, PlayerInventory inventory, PacketBuffer buffer) {
		Hand hand = buffer.readBoolean() ? Hand.MAIN_HAND : Hand.OFF_HAND;
		return new MagicContainer(id, inventory, new ItemStackHandler(Wizard.INVENTORY_SIZE), ItemStack.EMPTY, hand);
	}

	private ItemStack staff;
	private Hand hand;

	public MagicContainer(int id, PlayerInventory playerInv, IItemHandler magicInv, ItemStack staff, Hand hand) {
		super(ModContainers.MAGIC, id);
		this.staff = staff;
		this.hand = hand;

		// Magic slots
		for (int i = 0; i < Wizard.INVENTORY_SIZE; i++)
			addSlot(new SlotItemHandler(magicInv, i, 8 + (i % 9) * 18, 18 + (i / 9) * 18));

		// Player inventory
		for (int i = 0; i < 3 * 9; i++)
			addSlot(new Slot(playerInv, i + 9, 8 + (i % 9) * 18, 85 + (i / 9) * 18));

		// Player hotbar
		for (int i = 0; i < 9; i++)
			addSlot(new Slot(playerInv, i, 8 + i * 18, 143));
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		Slot slot = inventorySlots.get(index);
		ItemStack copy = ItemStack.EMPTY;
		if (slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			copy = stack.copy();

			if (stack == playerIn.getHeldItem(hand))
				return ItemStack.EMPTY;

			if (index < Wizard.INVENTORY_SIZE) {
				if (!mergeItemStack(stack, Wizard.INVENTORY_SIZE, inventorySlots.size(), false))
					return ItemStack.EMPTY;
			} else if (!mergeItemStack(stack, 0, Wizard.INVENTORY_SIZE, false)) {
				return ItemStack.EMPTY;
			}

			if (stack.isEmpty())
				slot.putStack(ItemStack.EMPTY);
			else
				slot.onSlotChanged();
			slot.onTake(playerIn, stack);
		}

		return copy;
	}

	@Override
	public boolean canInteractWith(PlayerEntity player) {
		return (player.getHeldItemMainhand() == staff || player.getHeldItemOffhand() == staff) && !staff.isEmpty();
	}

}
