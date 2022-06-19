package mod.vemerion.wizardstaff.Magic.suggestions2;

import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.RepairItemsMagic;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RepairArmorMagic extends RepairItemsMagic {
	
	public RepairArmorMagic(MagicType<? extends RepairArmorMagic> type) {
		super(type);
	}

	@Override
	protected Iterable<ItemStack> getItems(Level level, Player player, ItemStack staff, int count) {
		return player.getArmorSlots();
	}
}
