package mod.vemerion.wizardstaff.item;

import java.awt.Color;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.model.MagicArmorModel;
import mod.vemerion.wizardstaff.model.WizardArmorModel;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WizardArmorItem extends MagicArmorItem {
	private static final int COLOR = Color.MAGENTA.getRGB();

	public WizardArmorItem(EquipmentSlotType slot) {
		super(new WizardArmorMaterial(), slot);
	}
	
	@Override
	protected int getDefaultColor() {
		return COLOR;
	}

	@Override
	protected String getMagicArmorName() {
		return "wizard_armor";
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	protected MagicArmorModel<?> getModel() {
		if (model == null) {
			model = new WizardArmorModel<>(0.3f);
		}
		return model;
	}

	private static class WizardArmorMaterial extends MagicArmorMaterial {

		@Override
		public Ingredient getRepairMaterial() {
			return Ingredient.fromItems(Items.LEATHER);
		}

		@Override
		public String getName() {
			return Main.MODID + ":wizard_armor";
		}
	}
}
