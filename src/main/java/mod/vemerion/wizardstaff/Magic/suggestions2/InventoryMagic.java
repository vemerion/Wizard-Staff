package mod.vemerion.wizardstaff.Magic.suggestions2;

import mod.vemerion.wizardstaff.Magic.ContainerMagic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.capability.Wizard;
import mod.vemerion.wizardstaff.container.MagicContainer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class InventoryMagic extends ContainerMagic {

	public InventoryMagic(MagicType<? extends InventoryMagic> type) {
		super(type);
	}

	@Override
	protected AbstractContainerMenu getContainer(int id, Inventory playerInv, Player player, Level level, ItemStack staff,
			Wizard wizard) {
		return new MagicContainer(id, playerInv, wizard.getInventory(), staff, player.getUsedItemHand());
	}

	@Override
	protected SoundEvent getSound() {
		return SoundEvents.CHEST_OPEN;
	}
	
	@Override
	protected void addExtraData(FriendlyByteBuf buffer, Player player) {
		buffer.writeBoolean(player.getUsedItemHand() == InteractionHand.MAIN_HAND);
	}
}
