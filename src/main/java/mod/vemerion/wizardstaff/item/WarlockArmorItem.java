package mod.vemerion.wizardstaff.item;

import java.awt.Color;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.init.ModLayerLocations;
import mod.vemerion.wizardstaff.model.MagicArmorModel;
import mod.vemerion.wizardstaff.model.WarlockArmorModel;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class WarlockArmorItem extends MagicArmorItem {
	private static final int COLOR = Color.RED.getRGB();

	public WarlockArmorItem(EquipmentSlot slot) {
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

	@Override
	protected ClientItemExtensions getClientItemExtensions() {
		return new WarlockClientItemExtensions();
	}

	private static class WarlockArmorMaterial extends MagicArmorMaterial {
		@Override
		public Ingredient getRepairIngredient() {
			return Ingredient.of(Items.IRON_INGOT);
		}

		@Override
		public String getName() {
			return Main.MODID + ":warlock_armor";
		}
	}

	private static class WarlockClientItemExtensions extends ClientItemExtensions {

		@Override
		protected MagicArmorModel<?> getModel() {
			return new WarlockArmorModel<>(
					Minecraft.getInstance().getEntityModels().bakeLayer(ModLayerLocations.WARLOCK_ARMOR));
		}

	}
}
