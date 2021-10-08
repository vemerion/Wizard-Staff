package mod.vemerion.wizardstaff.Magic.suggestions2;

import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.RepairItemsMagic;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RepairArmorMagic extends RepairItemsMagic {
	
	public RepairArmorMagic(MagicType<? extends RepairArmorMagic> type) {
		super(type);
	}

	@Override
	protected Iterable<ItemStack> getItems(World world, PlayerEntity player, ItemStack staff, int count) {
		return player.getArmorInventoryList();
	}
}
