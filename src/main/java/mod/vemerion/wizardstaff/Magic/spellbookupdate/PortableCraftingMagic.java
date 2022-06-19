package mod.vemerion.wizardstaff.Magic.spellbookupdate;

import mod.vemerion.wizardstaff.Magic.ContainerMagic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.capability.Wizard;
import mod.vemerion.wizardstaff.init.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PortableCraftingMagic extends ContainerMagic {

	public PortableCraftingMagic(MagicType<? extends PortableCraftingMagic> type) {
		super(type);
	}
	
	@Override
	protected AbstractContainerMenu getContainer(int id, Inventory playerInv, Player player, Level level, ItemStack staff, Wizard wizard) {
		return new PortableCrafterContainer(id, playerInv, ContainerLevelAccess.create(level, player.blockPosition()), staff);
	}

	@Override
	protected SoundEvent getSound() {
		return ModSounds.ANVIL;
	}

	private class PortableCrafterContainer extends CraftingMenu {
		
		private ItemStack staff;

		public PortableCrafterContainer(int syncid, Inventory playerInv, ContainerLevelAccess posCallable, ItemStack staff) {
			super(syncid, playerInv, posCallable);
			this.staff = staff;
		}

		@Override
		public boolean stillValid(Player player) {
			return (player.getMainHandItem() == staff || player.getOffhandItem() == staff) && !staff.isEmpty();
		}

	}
}
