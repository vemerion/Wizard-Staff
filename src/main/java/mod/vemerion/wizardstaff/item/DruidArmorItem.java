package mod.vemerion.wizardstaff.item;

import java.awt.Color;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.init.ModLayerLocations;
import mod.vemerion.wizardstaff.model.DruidArmorModel;
import mod.vemerion.wizardstaff.model.MagicArmorModel;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class DruidArmorItem extends MagicArmorItem {
	private static final int COLOR = Color.GREEN.getRGB();

	public DruidArmorItem(EquipmentSlot slot) {
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

	@Override
	protected RenderProperties getRenderProperties() {
		return new DruidRenderProperties();
	}

	private static class DruidArmorMaterial extends MagicArmorMaterial {

		@Override
		public Ingredient getRepairIngredient() {
			return Ingredient.of(Items.GOLD_INGOT);
		}

		@Override
		public String getName() {
			return Main.MODID + ":druid_armor";
		}
	}

	private static class DruidRenderProperties extends RenderProperties {

		@Override
		protected MagicArmorModel<?> getModel() {
			return new DruidArmorModel<>(
					Minecraft.getInstance().getEntityModels().bakeLayer(ModLayerLocations.DRUID_ARMOR));
		}

	}
}
