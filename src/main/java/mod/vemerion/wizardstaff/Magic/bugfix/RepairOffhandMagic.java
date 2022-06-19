package mod.vemerion.wizardstaff.Magic.bugfix;

import com.google.common.collect.ImmutableList;

import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.RepairItemsMagic;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RepairOffhandMagic extends RepairItemsMagic {

	public RepairOffhandMagic(MagicType<? extends RepairOffhandMagic> type) {
		super(type);
	}

	@Override
	protected Iterable<ItemStack> getItems(Level level, Player player, ItemStack staff, int count) {
		return ImmutableList
				.of(staff == player.getMainHandItem() ? player.getOffhandItem() : player.getMainHandItem());
	}

}
