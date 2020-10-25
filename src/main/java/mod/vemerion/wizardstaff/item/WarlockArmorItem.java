package mod.vemerion.wizardstaff.item;

import java.awt.Color;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.model.MagicArmorModel;
import mod.vemerion.wizardstaff.model.WarlockArmorModel;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
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
			model = new WarlockArmorModel<>(0.3f);
		}
		return model;
	}

	private static class WarlockArmorMaterial implements IArmorMaterial {

		@Override
		public int getDurability(EquipmentSlotType slotIn) {
			return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * 15;
		}

		@Override
		public int getDamageReductionAmount(EquipmentSlotType slotIn) {
			switch (slotIn) {
			case FEET:
				return 1;
			case LEGS:
				return 2;
			case CHEST:
				return 3;
			case HEAD:
				return 1;
			default:
				break;
			}
			return 0;
		}

		@Override
		public int getEnchantability() {
			return 12;
		}

		@Override
		public SoundEvent getSoundEvent() {
			return SoundEvents.ITEM_ARMOR_EQUIP_LEATHER;
		}

		@Override
		public Ingredient getRepairMaterial() {
			return Ingredient.fromItems(Items.IRON_INGOT);
		}

		@Override
		public String getName() {
			return Main.MODID + "_warlock_armor";
		}

		@Override
		public float getToughness() {
			return 0;
		}
	}
}
