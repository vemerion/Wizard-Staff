package mod.vemerion.wizardstaff.model;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

public abstract class MagicArmorModel<T extends LivingEntity> extends BipedModel<T> {

	public MagicArmorModel(float modelSize, float yOffsetIn, int textureWidthIn, int textureHeightIn) {
		super(modelSize, yOffsetIn, textureWidthIn, textureHeightIn);
	}
	
	public abstract void setVisibility(EquipmentSlotType armorSlot);
	
	// FIXME: Fix armor stand rotations
}
