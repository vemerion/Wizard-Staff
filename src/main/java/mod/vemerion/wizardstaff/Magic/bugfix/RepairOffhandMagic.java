package mod.vemerion.wizardstaff.Magic.bugfix;

import com.google.common.collect.ImmutableList;

import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.RepairItemsMagic;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RepairOffhandMagic extends RepairItemsMagic {

	public RepairOffhandMagic(MagicType<? extends RepairOffhandMagic> type) {
		super(type);
	}

	@Override
	protected Iterable<ItemStack> getItems(World world, PlayerEntity player, ItemStack staff, int count) {
		return ImmutableList
				.of(staff == player.getHeldItemMainhand() ? player.getHeldItemOffhand() : player.getHeldItemMainhand());
	}

}
