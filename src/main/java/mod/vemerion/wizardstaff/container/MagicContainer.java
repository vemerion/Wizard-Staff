package mod.vemerion.wizardstaff.container;


import mod.vemerion.wizardstaff.capability.Wizard;
import mod.vemerion.wizardstaff.init.ModContainers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class MagicContainer extends AbstractContainerMenu {

	public static MagicContainer createContainerClientSide(int id, Inventory inventory, FriendlyByteBuf buffer) {
		InteractionHand hand = buffer.readBoolean() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
		return new MagicContainer(id, inventory, new ItemStackHandler(Wizard.INVENTORY_SIZE), ItemStack.EMPTY, hand);
	}
	
	private ItemStack staff;
	private InteractionHand hand;

	public MagicContainer(int id, Inventory playerInv, IItemHandler magicInv, ItemStack staff, InteractionHand hand) {
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
	public ItemStack quickMoveStack(Player playerIn, int index) {
		Slot slot = slots.get(index);
		ItemStack copy = ItemStack.EMPTY;
		if (slot != null && slot.hasItem()) {
			ItemStack stack = slot.getItem();
			copy = stack.copy();

			if (stack == playerIn.getItemInHand(hand))
				return ItemStack.EMPTY;

			if (index < Wizard.INVENTORY_SIZE) {
				if (!moveItemStackTo(stack, Wizard.INVENTORY_SIZE, slots.size(), false))
					return ItemStack.EMPTY;
			} else if (!moveItemStackTo(stack, 0, Wizard.INVENTORY_SIZE, false)) {
				return ItemStack.EMPTY;
			}

			if (stack.isEmpty())
				slot.set(ItemStack.EMPTY);
			else
				slot.setChanged();
			slot.onTake(playerIn, stack);
		}

		return copy;
	}

	@Override
	public boolean stillValid(Player player) {
		return (player.getMainHandItem() == staff || player.getOffhandItem() == staff) && !staff.isEmpty();
	}

}
