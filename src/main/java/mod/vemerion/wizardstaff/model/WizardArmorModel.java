package mod.vemerion.wizardstaff.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

/**
 * Created using Tabula 8.0.0
 */

public class WizardArmorModel<T extends LivingEntity> extends MagicArmorModel<T> {
	public ModelRenderer rightShoulder;
	public ModelRenderer rightArm1;
	public ModelRenderer rightArm2;
	public ModelRenderer rightArm3;
	public ModelRenderer rightArm3_1;
	public ModelRenderer rightShoe1;
	public ModelRenderer rightShoe2;
	public ModelRenderer rightShoe3;
	public ModelRenderer rightShoe4;
	public ModelRenderer rightShoe5;
	public ModelRenderer hat;
	public ModelRenderer top1;
	public ModelRenderer top2;
	public ModelRenderer top3;
	public ModelRenderer top4;
	public ModelRenderer robe;
	public ModelRenderer dress;
	public ModelRenderer dressFront;
	public ModelRenderer dressBack;
	public ModelRenderer dressFrontLeft;
	public ModelRenderer deessFrontRight;
	public ModelRenderer dressBackLeft;
	public ModelRenderer dressBackRight;
	public ModelRenderer leftShoulder;
	public ModelRenderer leftArm1;
	public ModelRenderer leftArm2;
	public ModelRenderer leftArm3;
	public ModelRenderer leftArm3_1;
	public ModelRenderer leftShoe1;
	public ModelRenderer leftShoe2;
	public ModelRenderer leftShoe3;
	public ModelRenderer leftShoe4;
	public ModelRenderer leftShoe5;

	public WizardArmorModel(float modelSize) {
		super(modelSize, 0, 64, 128);
		this.textureWidth = 64;
		this.textureHeight = 128;
		this.rightShoe5 = new ModelRenderer(this, 36, 82);
		this.rightShoe5.setRotationPoint(0.0F, 0.0F, -0.8F);
		this.rightShoe5.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(rightShoe5, -0.3490658503988659F, 0.0F, 0.0F);
		this.dressBack = new ModelRenderer(this, 36, 95);
		this.dressBack.setRotationPoint(0.0F, 0.0F, 2.0F);
		this.dressBack.addBox(-4.0F, 0.0F, 0.0F, 8.0F, 8.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(dressBack, 0.3490658503988659F, 0.0F, 0.0F);
		this.rightShoe3 = new ModelRenderer(this, 36, 91);
		this.rightShoe3.setRotationPoint(0.0F, 0.5F, -1.5F);
		this.rightShoe3.addBox(-1.5F, 0.0F, -2.0F, 3.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(rightShoe3, -0.3490658503988659F, 0.0F, 0.0F);
		this.top1 = new ModelRenderer(this, 0, 64);
		this.top1.setRotationPoint(0.0F, -8.0F, 0.0F);
		this.top1.addBox(-4.0F, -4.0F, -4.0F, 8.0F, 4.0F, 8.0F, 0.0F, 0.0F, 0.0F);
		this.rightShoulder = new ModelRenderer(this, 0, 116);
		this.rightShoulder.mirror = true;
		this.rightShoulder.setRotationPoint(-1.0F, 0.0F, 0.0F);
		this.rightShoulder.addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, modelSize);
		this.dressBackLeft = new ModelRenderer(this, 52, 100);
		this.dressBackLeft.setRotationPoint(4.0F, 0.0F, -4.0F);
		this.dressBackLeft.addBox(0.0F, -1.0F, 0.0F, 0.0F, 9.0F, 4.0F, 0.0F, 0.0F, 0.0F);
		this.robe = new ModelRenderer(this, 0, 100);
		this.robe.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.robe.addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, modelSize);
		this.top3 = new ModelRenderer(this, 28, 74);
		this.top3.setRotationPoint(0.0F, -4.0F, 0.0F);
		this.top3.addBox(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(top3, 0.17453292519943295F, 0.0F, 0.0F);
		this.leftShoe4 = new ModelRenderer(this, 36, 84);
		this.leftShoe4.setRotationPoint(0.0F, 0.5F, -1.5F);
		this.leftShoe4.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(leftShoe4, -0.3490658503988659F, 0.0F, 0.0F);
		this.dressFront = new ModelRenderer(this, 36, 95);
		this.dressFront.setRotationPoint(0.0F, 0.0F, -2.0F);
		this.dressFront.addBox(-4.0F, 0.0F, 0.0F, 8.0F, 8.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(dressFront, -0.3490658503988659F, 0.0F, 0.0F);
		this.rightArm3_1 = new ModelRenderer(this, 0, 116);
		this.rightArm3_1.setRotationPoint(0.0F, -3.0F, -0.5F);
		this.rightArm3_1.addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
		this.deessFrontRight = new ModelRenderer(this, 52, 91);
		this.deessFrontRight.setRotationPoint(-4.01F, 0.0F, 0.0F);
		this.deessFrontRight.addBox(0.0F, -1.0F, 0.0F, 0.0F, 9.0F, 4.0F, 0.0F, 0.0F, 0.0F);
		this.rightShoe2 = new ModelRenderer(this, 52, 82);
		this.rightShoe2.setRotationPoint(0.0F, 0.0F, -3.0F);
		this.rightShoe2.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(rightShoe2, -0.3490658503988659F, 0.0F, 0.0F);
		this.top2 = new ModelRenderer(this, 32, 64);
		this.top2.setRotationPoint(0.0F, -4.0F, 0.0F);
		this.top2.addBox(-3.0F, -4.0F, -3.0F, 6.0F, 4.0F, 6.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(top2, 0.17453292519943295F, 0.0F, 0.0F);
		this.leftArm3_1 = new ModelRenderer(this, 0, 116);
		this.leftArm3_1.setRotationPoint(0.0F, -3.0F, -0.5F);
		this.leftArm3_1.addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
		this.dressBackRight = new ModelRenderer(this, 52, 100);
		this.dressBackRight.setRotationPoint(-4.0F, 0.0F, -4.0F);
		this.dressBackRight.addBox(0.0F, -1.0F, 0.0F, 0.0F, 9.0F, 4.0F, 0.0F, 0.0F, 0.0F);
		this.leftShoulder = new ModelRenderer(this, 0, 116);
		this.leftShoulder.setRotationPoint(1.0F, 0.0F, 0.0F);
		this.leftShoulder.addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, modelSize);
		this.leftShoe5 = new ModelRenderer(this, 36, 82);
		this.leftShoe5.setRotationPoint(0.0F, 0.0F, -0.8F);
		this.leftShoe5.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(leftShoe5, -0.3490658503988659F, 0.0F, 0.0F);
		this.dressFrontLeft = new ModelRenderer(this, 52, 91);
		this.dressFrontLeft.setRotationPoint(4.01F, 0.0F, 0.0F);
		this.dressFrontLeft.addBox(0.0F, -1.0F, 0.0F, 0.0F, 9.0F, 4.0F, 0.0F, 0.0F, 0.0F);
		this.leftArm3 = new ModelRenderer(this, 15, 116);
		this.leftArm3.setRotationPoint(0.0F, -3.0F, -0.5F);
		this.leftArm3.addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.rightShoe1 = new ModelRenderer(this, 36, 82);
		this.rightShoe1.setRotationPoint(0.0F, 9.0F, 0.0F);
		this.rightShoe1.addBox(-2.5F, 0.0F, -3.5F, 5.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
		this.rightArm2 = new ModelRenderer(this, 24, 112);
		this.rightArm2.setRotationPoint(0.0F, 6.0F, 3.5F);
		this.rightArm2.addBox(-1.5F, -3.0F, -1.5F, 3.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
		this.leftShoe1 = new ModelRenderer(this, 36, 82);
		this.leftShoe1.setRotationPoint(0.0F, 9.0F, 0.0F);
		this.leftShoe1.addBox(-2.5F, 0.0F, -3.5F, 5.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
		this.leftArm2 = new ModelRenderer(this, 24, 112);
		this.leftArm2.setRotationPoint(0.0F, 6.0F, 3.5F);
		this.leftArm2.addBox(-1.5F, -3.0F, -1.5F, 3.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
		this.leftShoe2 = new ModelRenderer(this, 52, 82);
		this.leftShoe2.setRotationPoint(0.0F, 0.0F, -3.0F);
		this.leftShoe2.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(leftShoe2, -0.3490658503988659F, 0.0F, 0.0F);
		this.leftShoe3 = new ModelRenderer(this, 36, 91);
		this.leftShoe3.setRotationPoint(0.0F, 0.5F, -1.5F);
		this.leftShoe3.addBox(-1.5F, 0.0F, -2.0F, 3.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(leftShoe3, -0.3490658503988659F, 0.0F, 0.0F);
		this.rightShoe4 = new ModelRenderer(this, 36, 84);
		this.rightShoe4.setRotationPoint(0.0F, 0.5F, -1.5F);
		this.rightShoe4.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(rightShoe4, -0.3490658503988659F, 0.0F, 0.0F);
		this.hat = new ModelRenderer(this, -18, 82);
		this.hat.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.hat.addBox(-9.0F, -8.0F, -9.0F, 18.0F, 0.0F, 18.0F, 0.0F, 0.0F, 0.0F);
		this.rightArm1 = new ModelRenderer(this, 24, 100);
		this.rightArm1.setRotationPoint(0.0F, 4.0F, 0.0F);
		this.rightArm1.addBox(-2.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, modelSize);
		this.dress = new ModelRenderer(this, 0, 100);
		this.dress.setRotationPoint(0.0F, 10.5F, 0.0F);
		this.dress.addBox(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		this.leftArm1 = new ModelRenderer(this, 24, 100);
		this.leftArm1.setRotationPoint(0.0F, 4.0F, 0.0F);
		this.leftArm1.addBox(-2.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, modelSize);
		this.rightArm3 = new ModelRenderer(this, 15, 116);
		this.rightArm3.setRotationPoint(0.0F, -3.0F, -0.5F);
		this.rightArm3.addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.top4 = new ModelRenderer(this, 0, 64);
		this.top4.setRotationPoint(0.0F, -4.0F, 0.0F);
		this.top4.addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(top4, 0.17453292519943295F, 0.0F, 0.0F);
		this.rightShoe4.addChild(this.rightShoe5);
		this.dress.addChild(this.dressBack);
		this.rightShoe2.addChild(this.rightShoe3);
		this.hat.addChild(this.top1);
		this.bipedRightArm.addChild(this.rightShoulder);
		this.dressBack.addChild(this.dressBackLeft);
		this.bipedBody.addChild(this.robe);
		this.top2.addChild(this.top3);
		this.leftShoe3.addChild(this.leftShoe4);
		this.dress.addChild(this.dressFront);
		this.rightArm3.addChild(this.rightArm3_1);
		this.dressFront.addChild(this.deessFrontRight);
		this.rightShoe1.addChild(this.rightShoe2);
		this.top1.addChild(this.top2);
		this.leftArm3.addChild(this.leftArm3_1);
		this.dressBack.addChild(this.dressBackRight);
		this.bipedLeftArm.addChild(this.leftShoulder);
		this.leftShoe4.addChild(this.leftShoe5);
		this.dressFront.addChild(this.dressFrontLeft);
		this.leftArm2.addChild(this.leftArm3);
		this.bipedRightLeg.addChild(this.rightShoe1);
		this.rightArm1.addChild(this.rightArm2);
		this.bipedLeftLeg.addChild(this.leftShoe1);
		this.leftArm1.addChild(this.leftArm2);
		this.leftShoe1.addChild(this.leftShoe2);
		this.leftShoe2.addChild(this.leftShoe3);
		this.rightShoe3.addChild(this.rightShoe4);
		this.bipedHead.addChild(this.hat);
		this.rightShoulder.addChild(this.rightArm1);
		this.bipedBody.addChild(this.dress);
		this.leftShoulder.addChild(this.leftArm1);
		this.rightArm2.addChild(this.rightArm3);
		this.top3.addChild(this.top4);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
			float red, float green, float blue, float alpha) {
		dressBack.rotateAngleX = (float) Math.max(Math.toRadians(20),
				Math.max(bipedLeftLeg.rotateAngleX, bipedRightLeg.rotateAngleX) + Math.toRadians(15));
		dressFront.rotateAngleX = (float) Math.min(Math.toRadians(-20),
				Math.min(bipedLeftLeg.rotateAngleX, bipedRightLeg.rotateAngleX) - Math.toRadians(15));
		super.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public void setVisibility(EquipmentSlotType armorSlot) {
		rightShoulder.showModel = armorSlot == EquipmentSlotType.CHEST;
		leftShoulder.showModel = armorSlot == EquipmentSlotType.CHEST;
		robe.showModel = armorSlot == EquipmentSlotType.CHEST;

		hat.showModel = armorSlot == EquipmentSlotType.HEAD;

		dress.showModel = armorSlot == EquipmentSlotType.LEGS;

		rightShoe1.showModel = armorSlot == EquipmentSlotType.FEET;
		leftShoe1.showModel = armorSlot == EquipmentSlotType.FEET;
	}
}
