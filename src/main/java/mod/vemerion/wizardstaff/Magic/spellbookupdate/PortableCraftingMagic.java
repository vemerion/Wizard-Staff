package mod.vemerion.wizardstaff.Magic.spellbookupdate;

import mod.vemerion.wizardstaff.Helper.Helper;
import mod.vemerion.wizardstaff.Magic.ContainerMagic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.capability.Wizard;
import mod.vemerion.wizardstaff.init.ModSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class PortableCraftingMagic extends ContainerMagic {

	public PortableCraftingMagic(MagicType<? extends PortableCraftingMagic> type) {
		super(type);
	}
	
	@Override
	protected Container getContainer(int id, PlayerInventory playerInv, PlayerEntity player, World world, Wizard wizard) {
		return new PortableCrafterContainer(id, playerInv, IWorldPosCallable.of(world, player.getPosition()));
	}

	@Override
	protected SoundEvent getSound() {
		return ModSounds.ANVIL;
	}

	private class PortableCrafterContainer extends WorkbenchContainer {

		public PortableCrafterContainer(int syncid, PlayerInventory playerInv, IWorldPosCallable posCallable) {
			super(syncid, playerInv, posCallable);
		}

		@Override
		public boolean canInteractWith(PlayerEntity playerIn) {
			return Helper.isHoldingStaff(playerIn);
		}

	}
}
