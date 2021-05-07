package mod.vemerion.wizardstaff.item;

import java.awt.Color;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.model.MagicArmorModel;
import mod.vemerion.wizardstaff.model.WarlockArmorModel;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WarlockArmorItem extends MagicArmorItem {
	private static final int COLOR = Color.RED.getRGB();

	public WarlockArmorItem(EquipmentSlotType slot) {
		super(new WarlockArmorMaterial(), slot);
	}

	@Override
	protected int getDefaultColor() {
		return COLOR;
	}

	@Override
	protected String getMagicArmorName() {
		return "warlock_armor";
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	protected MagicArmorModel<?> getModel() {
		if (model == null) {
			model = new WarlockArmorModel<>(0.5f);
		}
		return model;
	}

	private static class WarlockArmorMaterial extends MagicArmorMaterial {
		@Override
		public Ingredient getRepairMaterial() {
			return Ingredient.fromItems(Items.IRON_INGOT);
		}

		@Override
		public String getName() {
			return Main.MODID + ":warlock_armor";
		}
	}
}
