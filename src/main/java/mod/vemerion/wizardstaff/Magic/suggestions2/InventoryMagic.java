package mod.vemerion.wizardstaff.Magic.suggestions2;

import mod.vemerion.wizardstaff.Magic.ContainerMagic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.capability.Wizard;
import mod.vemerion.wizardstaff.container.MagicContainer;
import mod.vemerion.wizardstaff.init.ModSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class InventoryMagic extends ContainerMagic {

	public InventoryMagic(MagicType<? extends InventoryMagic> type) {
		super(type);
	}

	@Override
	protected Container getContainer(int id, PlayerInventory playerInv, PlayerEntity player, World world, ItemStack staff,
			Wizard wizard) {
		return new MagicContainer(id, playerInv, wizard.getInventory(), staff, player.getActiveHand());
	}

	@Override
	protected SoundEvent getSound() {
		return ModSounds.CLOTH;
	}
	
	@Override
	protected void addExtraData(PacketBuffer buffer, PlayerEntity player) {
		buffer.writeBoolean(player.getActiveHand() == Hand.MAIN_HAND);
	}
}
