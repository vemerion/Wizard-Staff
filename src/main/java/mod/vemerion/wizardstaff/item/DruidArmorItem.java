package mod.vemerion.wizardstaff.item;

import java.awt.Color;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.model.DruidArmorModel;
import mod.vemerion.wizardstaff.model.MagicArmorModel;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DruidArmorItem extends MagicArmorItem {
	private static final int COLOR = Color.GREEN.getRGB();

	public DruidArmorItem(EquipmentSlotType slot) {
		super(new DruidArmorMaterial(), slot);
	}
	
	@Override
	protected int getDefaultColor() {
		return COLOR;
	}

	@Override
	protected String getMagicArmorName() {
		return "druid_armor";
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	protected MagicArmorModel<?> getModel() {
		if (model == null) {
			model = new DruidArmorModel<>(0.3f);
		}
		return model;
	}

	private static class DruidArmorMaterial extends MagicArmorMaterial {

		@Override
		public Ingredient getRepairMaterial() {
			return Ingredient.fromItems(Items.GOLD_INGOT);
		}

		@Override
		public String getName() {
			return Main.MODID + ":druid_armor";
		}
	}
}
