package mod.vemerion.wizardstaff.Magic.suggestions2;

import mod.vemerion.wizardstaff.Magic.ContainerMagic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.capability.Wizard;
import mod.vemerion.wizardstaff.container.MagicContainer;
import mod.vemerion.wizardstaff.init.ModSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.items.wrapper.InvWrapper;

public class EnderChestMagic extends ContainerMagic {

	public EnderChestMagic(MagicType<? extends EnderChestMagic> type) {
		super(type);
	}

	@Override
	protected Container getContainer(int id, PlayerInventory playerInv, PlayerEntity player, World world,
			Wizard wizard) {
		return new MagicContainer(id, playerInv, new InvWrapper(player.getInventoryEnderChest()));
	}

	@Override
	protected SoundEvent getSound() {
		return ModSounds.WARP;
	}

}
