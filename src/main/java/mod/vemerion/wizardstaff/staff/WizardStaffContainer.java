package mod.vemerion.wizardstaff.staff;

import mod.vemerion.wizardstaff.init.ModContainers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class WizardStaffContainer extends AbstractContainerMenu {

	private static final int STAFF_SLOT_COUNT = WizardStaffItemHandler.SLOT_COUNT;

	private ItemStack staff;
	private InteractionHand hand;
	private boolean shouldAnimate;

	public static WizardStaffContainer createContainerClientSide(int id, Inventory inventory, FriendlyByteBuf buffer) {
		InteractionHand hand = buffer.readBoolean() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
		return new WizardStaffContainer(id, inventory, new WizardStaffItemHandler(ItemStack.EMPTY),
				ItemStack.EMPTY, buffer.readBoolean(), hand);
	}

	protected WizardStaffContainer(int id, Inventory inventory, WizardStaffItemHandler handler,
			ItemStack staff, boolean shouldAnimate, InteractionHand hand) {
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
	public boolean stillValid(Player player) {
		return (player.getMainHandItem() == staff || player.getOffhandItem() == staff) && !staff.isEmpty();
	}

	@Override
	public ItemStack quickMoveStack(Player playerIn, int index) {
		Slot slot = this.slots.get(index);
		ItemStack stack = slot.getItem();
		ItemStack copy = stack.copy();

		if (slot != null && slot.hasItem() && stack != playerIn.getItemInHand(hand)) {
			if (index < STAFF_SLOT_COUNT) {
				if (!moveItemStackTo(stack, STAFF_SLOT_COUNT, STAFF_SLOT_COUNT + 9 * 4, false))
					return ItemStack.EMPTY;
			} else if (index >= STAFF_SLOT_COUNT && index < STAFF_SLOT_COUNT + 9 * 4) {
				if (!moveItemStackTo(stack, 0, STAFF_SLOT_COUNT, false))
					return ItemStack.EMPTY;
			} else {
				return ItemStack.EMPTY;
			}
		} else {
			return ItemStack.EMPTY;
		}

		if (stack.getCount() == 0)
			slot.set(ItemStack.EMPTY);
		else
			slot.setChanged();
		slot.onTake(playerIn, stack);

		return copy;
	}
}
