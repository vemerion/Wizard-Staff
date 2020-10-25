package mod.vemerion.wizardstaff.item;

import java.util.List;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.model.MagicArmorModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class MagicArmorItem extends DyeableArmorItem {
	protected static final int[] MAX_DAMAGE_ARRAY = new int[] { 13, 15, 16, 11 };

	@OnlyIn(Dist.CLIENT)
	protected MagicArmorModel<?> model;

	public MagicArmorItem(IArmorMaterial material, EquipmentSlotType slot) {
		super(material, slot, new Item.Properties().maxStackSize(1).group(ItemGroup.SEARCH));
	}
	
	@Override
	public int getColor(ItemStack stack) {
		return hasColor(stack) ? super.getColor(stack) : getDefaultColor();
	}
	
	protected abstract int getDefaultColor();
	protected abstract String getMagicArmorName();

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		if ("overlay".equals(type))
			return Main.MODID + ":textures/armor/" + getMagicArmorName() + "_overlay.png";
		else
			return Main.MODID + ":textures/armor/" + getMagicArmorName() + ".png";
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TranslationTextComponent("item.wizard-staff." + getMagicArmorName() + ".description")
				.applyTextStyle(TextFormatting.BLUE));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@SuppressWarnings("unchecked")
	@OnlyIn(Dist.CLIENT)
	@Override
	public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack,
			EquipmentSlotType armorSlot, A _default) {
		MagicArmorModel<?> model = getModel();

		model.setVisibility(armorSlot);

		model.isSitting = _default.isSitting;
		model.isSneak = _default.isSneak;
		model.isChild = _default.isChild;
		model.rightArmPose = _default.rightArmPose;
		model.leftArmPose = _default.leftArmPose;

		return (A) model;
	}

	@OnlyIn(Dist.CLIENT)
	protected abstract MagicArmorModel<?> getModel();

	private static class WizardArmorMaterial implements IArmorMaterial {

		@Override
		public int getDurability(EquipmentSlotType slotIn) {
			return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * 5;
		}

		@Override
		public int getDamageReductionAmount(EquipmentSlotType slotIn) {
			return 0;
		}

		@Override
		public int getEnchantability() {
			return 20;
		}

		@Override
		public SoundEvent getSoundEvent() {
			return SoundEvents.ITEM_ARMOR_EQUIP_LEATHER;
		}

		@Override
		public Ingredient getRepairMaterial() {
			return Ingredient.fromItems(Items.LEATHER);
		}

		@Override
		public String getName() {
			return Main.MODID + ":wizard_armor";
		}

		@Override
		public float getToughness() {
			return 0;
		}
	}

}
