package mod.vemerion.wizardstaff.item;

import java.awt.Color;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.init.ModLayerLocations;
import mod.vemerion.wizardstaff.model.MagicArmorModel;
import mod.vemerion.wizardstaff.model.WizardArmorModel;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class WizardArmorItem extends MagicArmorItem {
	private static final int COLOR = Color.MAGENTA.getRGB();

	public WizardArmorItem(EquipmentSlot slot) {
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

	@Override
	protected RenderProperties getRenderProperties() {
		return new WizardRenderProperties();
	}

	private static class WizardArmorMaterial extends MagicArmorMaterial {

		@Override
		public Ingredient getRepairIngredient() {
			return Ingredient.of(Items.LEATHER);
		}

		@Override
		public String getName() {
			return Main.MODID + ":wizard_armor";
		}
	}

	private static class WizardRenderProperties extends RenderProperties {

		@Override
		protected MagicArmorModel<?> getModel() {
			return new WizardArmorModel<>(
					Minecraft.getInstance().getEntityModels().bakeLayer(ModLayerLocations.WIZARD_ARMOR));
		}

	}
}
