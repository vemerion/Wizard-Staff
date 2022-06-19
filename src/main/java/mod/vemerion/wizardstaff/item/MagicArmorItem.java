package mod.vemerion.wizardstaff.item;

import java.util.List;
import java.util.function.Consumer;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.model.MagicArmorModel;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.IItemRenderProperties;

public abstract class MagicArmorItem extends DyeableArmorItem {

	public MagicArmorItem(ArmorMaterial material, EquipmentSlot slot) {
		super(material, slot, new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_SEARCH));
	}

	@Override
	public int getColor(ItemStack stack) {
		return hasCustomColor(stack) ? super.getColor(stack) : getDefaultColor();
	}

	protected abstract int getDefaultColor();

	protected abstract String getMagicArmorName();

	protected abstract RenderProperties getRenderProperties();

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		if ("overlay".equals(type))
			return Main.MODID + ":textures/armor/" + getMagicArmorName() + "_overlay.png";
		else
			return Main.MODID + ":textures/armor/" + getMagicArmorName() + ".png";
	}

	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(new TranslatableComponent("item.wizardstaff." + getMagicArmorName() + ".description")
				.withStyle(ChatFormatting.BLUE));
		super.appendHoverText(stack, level, tooltip, flagIn);
	}

	public static int countMagicArmorPieces(Player player) {
		int count = 0;
		for (ItemStack armor : player.getArmorSlots()) {
			if (armor.getItem() instanceof MagicArmorItem)
				count++;
		}
		return count;
	}

	@Override
	public void initializeClient(Consumer<IItemRenderProperties> consumer) {
		consumer.accept(getRenderProperties());
	}

	protected static abstract class MagicArmorMaterial implements ArmorMaterial {
		protected static final int[] MAX_DAMAGE_ARRAY = new int[] { 13, 15, 16, 11 };
		protected static final int[] PROTECTION_ARRAY = new int[] { 1, 2, 3, 1 };

		@Override
		public int getDurabilityForSlot(EquipmentSlot slotIn) {
			return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * 5;
		}

		@Override
		public int getDefenseForSlot(EquipmentSlot slotIn) {
			return PROTECTION_ARRAY[slotIn.getIndex()];
		}

		@Override
		public int getEnchantmentValue() {
			return 20;
		}

		@Override
		public SoundEvent getEquipSound() {
			return SoundEvents.ARMOR_EQUIP_LEATHER;
		}

		@Override
		public float getToughness() {
			return 0;
		}

		@Override
		public float getKnockbackResistance() {
			return 0;
		}
	}

	protected static abstract class RenderProperties implements IItemRenderProperties {

		MagicArmorModel<?> model;

		@Override
		public HumanoidModel<?> getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot,
				HumanoidModel<?> _default) {
			if (model == null)
				model = getModel();

			model.setVisibility(armorSlot);

			model.riding = _default.riding;
			model.crouching = _default.crouching;
			model.young = _default.young;
			model.rightArmPose = _default.rightArmPose;
			model.leftArmPose = _default.leftArmPose;

			return model;
		}

		protected abstract MagicArmorModel<?> getModel();
	}
}
