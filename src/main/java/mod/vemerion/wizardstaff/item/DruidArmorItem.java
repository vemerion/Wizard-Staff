package mod.vemerion.wizardstaff.item;

import java.awt.Color;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.model.DruidArmorModel;
import mod.vemerion.wizardstaff.model.MagicArmorModel;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DruidArmorItem extends MagicArmorItem {
	private static final int color = Color.GREEN.getRGB();

	public DruidArmorItem(EquipmentSlotType slot) {
		super(new DruidArmorMaterial(), slot);
	}
	
	@Override
	protected int getDefaultColor() {
		return color;
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

	private static class DruidArmorMaterial implements IArmorMaterial {

		@Override
		public int getDurability(EquipmentSlotType slotIn) {
			return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * 10;
		}

		@Override
		public int getDamageReductionAmount(EquipmentSlotType slotIn) {
			return 0;
		}

		@Override
		public int getEnchantability() {
			return 15;
		}

		@Override
		public SoundEvent getSoundEvent() {
			return SoundEvents.ITEM_ARMOR_EQUIP_LEATHER;
		}

		@Override
		public Ingredient getRepairMaterial() {
			return Ingredient.fromItems(Items.GOLD_INGOT);
		}

		@Override
		public String getName() {
			return Main.MODID + "_druid_armor";
		}

		@Override
		public float getToughness() {
			return 0;
		}
	}
}
