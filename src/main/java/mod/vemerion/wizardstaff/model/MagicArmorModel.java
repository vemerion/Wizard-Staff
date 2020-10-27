package mod.vemerion.wizardstaff.model;

import net.minecraft.client.renderer.entity.model.ArmorStandArmorModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.inventory.EquipmentSlotType;

public abstract class MagicArmorModel<T extends LivingEntity> extends BipedModel<T> {
	private ArmorStandArmorModel armorStandModel;

	public MagicArmorModel(float modelSize, float yOffsetIn, int textureWidthIn, int textureHeightIn) {
		super(modelSize, yOffsetIn, textureWidthIn, textureHeightIn);
		this.armorStandModel = new ArmorStandArmorModel(modelSize);
	}

	@Override
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) {
		if (entityIn instanceof ArmorStandEntity) {
			ArmorStandEntity armorStand = (ArmorStandEntity) entityIn;
			armorStandModel.setRotationAngles(armorStand, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			bipedHead.copyModelAngles(armorStandModel.bipedHead);
			bipedBody.copyModelAngles(armorStandModel.bipedBody);
			bipedLeftLeg.copyModelAngles(armorStandModel.bipedLeftLeg);
			bipedRightLeg.copyModelAngles(armorStandModel.bipedRightLeg);
			bipedLeftArm.copyModelAngles(armorStandModel.bipedLeftArm);
			bipedRightArm.copyModelAngles(armorStandModel.bipedRightArm);
			bipedHeadwear.copyModelAngles(bipedHead);
		} else {
			super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		}
	}

	public abstract void setVisibility(EquipmentSlotType armorSlot);

}
