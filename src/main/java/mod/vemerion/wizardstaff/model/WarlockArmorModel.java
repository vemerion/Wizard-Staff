package mod.vemerion.wizardstaff.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

/**
 * Created using Tabula 8.0.0
 */
public class WarlockArmorModel<T extends LivingEntity> extends MagicArmorModel<T> {
	public ModelRenderer leftShoulder1;
	public ModelRenderer leftShoulder2;
	public ModelRenderer spike1;
	public ModelRenderer spike2;
	public ModelRenderer spike3;
	public ModelRenderer leftSleeve;
	public ModelRenderer leftShoulder3;
	public ModelRenderer spike12;
	public ModelRenderer spike22;
	public ModelRenderer spike32;
	public ModelRenderer rightShoe1;
	public ModelRenderer rightShoe2;
	public ModelRenderer rightShoe3;
	public ModelRenderer rightShoe4;
	public ModelRenderer cowl;
	public ModelRenderer cowlBack1;
	public ModelRenderer cowlBack2;
	public ModelRenderer cowlBack3;
	public ModelRenderer robe;
	public ModelRenderer dress;
	public ModelRenderer dressFront;
	public ModelRenderer dressBack;
	public ModelRenderer dressLeft;
	public ModelRenderer dressRight;
	public ModelRenderer rightShoulder1;
	public ModelRenderer rightShoulder2;
	public ModelRenderer spike1_1;
	public ModelRenderer spike2_1;
	public ModelRenderer spike3_1;
	public ModelRenderer leftSleeve_1;
	public ModelRenderer rightShoulder3;
	public ModelRenderer spike12_1;
	public ModelRenderer spike22_1;
	public ModelRenderer spike32_1;
	public ModelRenderer leftShoe1;
	public ModelRenderer leftShoe2;
	public ModelRenderer leftShoe3;
	public ModelRenderer leftShoe4;

	public WarlockArmorModel(float modelSize) {
		super(modelSize, 0, 64, 128);
		this.textureWidth = 64;
		this.textureHeight = 128;
		this.spike32 = new ModelRenderer(this, 59, 116);
		this.spike32.setRotationPoint(0.0F, -2.0F, 0.0F);
		this.spike32.addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F);
		this.setRotateAngle(spike32, 0.27366763203903305F, -0.6646214111173737F, -0.0781907508222411F);
		this.leftShoe1 = new ModelRenderer(this, 44, 115);
		this.leftShoe1.setRotationPoint(0.0F, 4.0F, 0.0F);
		this.leftShoe1.addBox(-2.5F, 0.0F, -2.5F, 5.0F, 8.0F, 5.0F, modelSize);
		this.rightShoe4 = new ModelRenderer(this, 43, 118);
		this.rightShoe4.mirror = true;
		this.rightShoe4.setRotationPoint(0.0F, 1.0F, -0.6F);
		this.rightShoe4.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 1.0F, 1.0F, modelSize);
		this.leftSleeve_1 = new ModelRenderer(this, 0, 90);
		this.leftSleeve_1.setRotationPoint(0.0F, 2.0F, 0.0F);
		this.leftSleeve_1.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, modelSize);
		this.spike2_1 = new ModelRenderer(this, 59, 117);
		this.spike2_1.setRotationPoint(1.6F, -2.5F, -0.4F);
		this.spike2_1.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F);
		this.setRotateAngle(spike2_1, -0.11728612207217244F, 1.7201964681550337F, -0.03909537541112055F);
		this.robe = new ModelRenderer(this, 0, 100);
		this.robe.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.robe.addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, modelSize);
		this.rightShoulder1 = new ModelRenderer(this, 0, 116);
		this.rightShoulder1.setRotationPoint(1.0F, 0.0F, 0.0F);
		this.rightShoulder1.addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, modelSize);
		this.spike32_1 = new ModelRenderer(this, 59, 118);
		this.spike32_1.setRotationPoint(0.0F, -2.0F, 0.0F);
		this.spike32_1.addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F);
		this.setRotateAngle(spike32_1, 0.27366763203903305F, -0.6646214111173737F, -0.0781907508222411F);
		this.leftShoe3 = new ModelRenderer(this, 36, 120);
		this.leftShoe3.setRotationPoint(0.0F, 1.0F, -1.9F);
		this.leftShoe3.addBox(-1.5F, 0.0F, -1.0F, 3.0F, 2.0F, 1.0F, modelSize);
		this.cowlBack1 = new ModelRenderer(this, 0, 64);
		this.cowlBack1.setRotationPoint(0.0F, -5.6F, 3.0F);
		this.cowlBack1.addBox(-2.5F, -2.5F, 0.0F, 5.0F, 5.0F, 4.0F, modelSize);
		this.setRotateAngle(cowlBack1, -0.6255260065779288F, 0.0F, 0.0F);
		this.spike12 = new ModelRenderer(this, 59, 116);
		this.spike12.setRotationPoint(0.0F, -2.0F, 0.0F);
		this.spike12.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F);
		this.setRotateAngle(spike12, 0.27366763203903305F, -0.6646214111173737F, -0.0781907508222411F);
		this.spike1 = new ModelRenderer(this, 59, 116);
		this.spike1.setRotationPoint(-1.1F, -2.5F, 1.2F);
		this.spike1.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F);
		this.setRotateAngle(spike1, -0.11728612207217244F, -0.23457224414434488F, -0.03909537541112055F);
		this.leftShoulder2 = new ModelRenderer(this, 20, 121);
		this.leftShoulder2.mirror = true;
		this.leftShoulder2.setRotationPoint(-3.2F, 0.5F, 0.0F);
		this.leftShoulder2.addBox(0.0F, -2.0F, -1.5F, 1.0F, 4.0F, 3.0F, modelSize);
		this.rightShoe3 = new ModelRenderer(this, 36, 120);
		this.rightShoe3.mirror = true;
		this.rightShoe3.setRotationPoint(0.0F, 1.0F, -1.9F);
		this.rightShoe3.addBox(-1.5F, 0.0F, -1.0F, 3.0F, 2.0F, 1.0F, modelSize);
		this.spike2 = new ModelRenderer(this, 59, 116);
		this.spike2.setRotationPoint(0.7F, -2.5F, -0.1F);
		this.spike2.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F);
		this.setRotateAngle(spike2, -0.11728612207217244F, 1.7201964681550337F, -0.03909537541112055F);
		this.cowl = new ModelRenderer(this, 0, 73);
		this.cowl.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.cowl.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, modelSize);
		this.dressLeft = new ModelRenderer(this, 28, 99);
		this.dressLeft.setRotationPoint(4.0F, 0.0F, 0.0F);
		this.dressLeft.addBox(0.0F, 0.0F, -2.0F, 0.0F, 8.0F, 4.0F);
		this.setRotateAngle(dressLeft, 0.0F, 0.0F, -0.2617993877991494F);
		this.leftShoulder1 = new ModelRenderer(this, 0, 116);
		this.leftShoulder1.mirror = true;
		this.leftShoulder1.setRotationPoint(-1.0F, 0.0F, 0.0F);
		this.leftShoulder1.addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, modelSize);
		this.spike22_1 = new ModelRenderer(this, 59, 116);
		this.spike22_1.setRotationPoint(0.0F, -2.0F, 0.0F);
		this.spike22_1.addBox(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F);
		this.setRotateAngle(spike22_1, 0.27366763203903305F, -0.6646214111173737F, -0.0781907508222411F);
		this.leftShoe4 = new ModelRenderer(this, 43, 118);
		this.leftShoe4.setRotationPoint(0.0F, 1.0F, -0.6F);
		this.leftShoe4.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 1.0F, 1.0F, modelSize);
		this.dressRight = new ModelRenderer(this, 28, 99);
		this.dressRight.setRotationPoint(-4.0F, 0.0F, 0.0F);
		this.dressRight.addBox(0.0F, 0.0F, -2.0F, 0.0F, 8.0F, 4.0F);
		this.setRotateAngle(dressRight, 0.0F, 0.0F, 0.2617993877991494F);
		this.rightShoulder3 = new ModelRenderer(this, 15, 116);
		this.rightShoulder3.setRotationPoint(0.7F, 0.5F, 0.0F);
		this.rightShoulder3.addBox(0.0F, -1.5F, -1.0F, 1.0F, 3.0F, 2.0F, modelSize);
		this.leftSleeve = new ModelRenderer(this, 0, 90);
		this.leftSleeve.setRotationPoint(0.0F, 2.0F, 0.0F);
		this.leftSleeve.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, modelSize);
		this.leftShoulder3 = new ModelRenderer(this, 15, 116);
		this.leftShoulder3.mirror = true;
		this.leftShoulder3.setRotationPoint(-0.7F, 0.5F, 0.0F);
		this.leftShoulder3.addBox(0.0F, -1.5F, -1.0F, 1.0F, 3.0F, 2.0F, modelSize);
		this.spike3_1 = new ModelRenderer(this, 59, 117);
		this.spike3_1.setRotationPoint(-0.5F, -2.5F, -1.3F);
		this.spike3_1.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F);
		this.setRotateAngle(spike3_1, -0.11728612207217244F, -1.407433498155583F, -0.03909537541112055F);
		this.dress = new ModelRenderer(this, 0, 100);
		this.dress.setRotationPoint(0.0F, 12.0F, 0.0F);
		this.dress.addBox(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		this.spike12_1 = new ModelRenderer(this, 59, 117);
		this.spike12_1.setRotationPoint(0.0F, -2.0F, 0.0F);
		this.spike12_1.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F);
		this.setRotateAngle(spike12_1, 0.27366763203903305F, -0.6646214111173737F, -0.0781907508222411F);
		this.spike22 = new ModelRenderer(this, 59, 116);
		this.spike22.setRotationPoint(0.0F, -2.0F, 0.0F);
		this.spike22.addBox(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F);
		this.setRotateAngle(spike22, 0.27366763203903305F, -0.6646214111173737F, -0.0781907508222411F);
		this.spike3 = new ModelRenderer(this, 59, 116);
		this.spike3.setRotationPoint(-1.1F, -2.5F, -1.3F);
		this.spike3.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F);
		this.setRotateAngle(spike3, -0.11728612207217244F, -1.407433498155583F, -0.03909537541112055F);
		this.spike1_1 = new ModelRenderer(this, 59, 117);
		this.spike1_1.setRotationPoint(-0.6F, -2.5F, 1.2F);
		this.spike1_1.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F);
		this.setRotateAngle(spike1_1, -0.11728612207217244F, -0.23457224414434488F, -0.03909537541112055F);
		this.rightShoe2 = new ModelRenderer(this, 32, 123);
		this.rightShoe2.mirror = true;
		this.rightShoe2.setRotationPoint(0.0F, 5.0F, -1.8F);
		this.rightShoe2.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 3.0F, 2.0F, modelSize);
		this.rightShoulder2 = new ModelRenderer(this, 20, 121);
		this.rightShoulder2.setRotationPoint(2.2F, 0.5F, 0.0F);
		this.rightShoulder2.addBox(0.0F, -2.0F, -1.5F, 1.0F, 4.0F, 3.0F, modelSize);
		this.leftShoe2 = new ModelRenderer(this, 32, 123);
		this.leftShoe2.setRotationPoint(0.0F, 5.0F, -1.8F);
		this.leftShoe2.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 3.0F, 2.0F, modelSize);
		this.cowlBack3 = new ModelRenderer(this, 0, 64);
		this.cowlBack3.setRotationPoint(0.0F, 0.0F, 2.0F);
		this.cowlBack3.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 1.0F, modelSize);
		this.setRotateAngle(cowlBack3, -0.23457224414434488F, 0.0F, 0.0F);
		this.dressBack = new ModelRenderer(this, 48, 95);
		this.dressBack.setRotationPoint(0.0F, 0.0F, 2.0F);
		this.dressBack.addBox(-4.0F, 0.0F, 0.0F, 8.0F, 8.0F, 0.0F);
		this.setRotateAngle(dressBack, 0.4363323129985824F, 0.0F, 0.0F);
		this.rightShoe1 = new ModelRenderer(this, 44, 115);
		this.rightShoe1.mirror = true;
		this.rightShoe1.setRotationPoint(0.0F, 4.0F, 0.0F);
		this.rightShoe1.addBox(-2.5F, 0.0F, -2.5F, 5.0F, 8.0F, 5.0F, modelSize);
		this.dressFront = new ModelRenderer(this, 48, 95);
		this.dressFront.setRotationPoint(0.0F, 0.0F, -2.0F);
		this.dressFront.addBox(-4.0F, 0.0F, 0.0F, 8.0F, 8.0F, 0.0F);
		this.setRotateAngle(dressFront, -0.4363323129985824F, 0.0F, 0.0F);
		this.cowlBack2 = new ModelRenderer(this, 18, 64);
		this.cowlBack2.setRotationPoint(0.0F, 0.0F, 4.0F);
		this.cowlBack2.addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 2.0F, modelSize);
		this.setRotateAngle(cowlBack2, -0.23457224414434488F, 0.0F, 0.0F);
		this.spike3.addChild(this.spike32);
		this.bipedLeftLeg.addChild(this.leftShoe1);
		this.rightShoe3.addChild(this.rightShoe4);
		this.rightShoulder1.addChild(this.leftSleeve_1);
		this.rightShoulder1.addChild(this.spike2_1);
		this.bipedBody.addChild(this.robe);
		this.bipedLeftArm.addChild(this.rightShoulder1);
		this.spike3_1.addChild(this.spike32_1);
		this.leftShoe2.addChild(this.leftShoe3);
		this.cowl.addChild(this.cowlBack1);
		this.spike1.addChild(this.spike12);
		this.leftShoulder1.addChild(this.spike1);
		this.leftShoulder1.addChild(this.leftShoulder2);
		this.rightShoe2.addChild(this.rightShoe3);
		this.leftShoulder1.addChild(this.spike2);
		this.bipedHead.addChild(this.cowl);
		this.dress.addChild(this.dressLeft);
		this.bipedRightArm.addChild(this.leftShoulder1);
		this.spike2_1.addChild(this.spike22_1);
		this.leftShoe3.addChild(this.leftShoe4);
		this.dress.addChild(this.dressRight);
		this.rightShoulder2.addChild(this.rightShoulder3);
		this.leftShoulder1.addChild(this.leftSleeve);
		this.leftShoulder2.addChild(this.leftShoulder3);
		this.rightShoulder1.addChild(this.spike3_1);
		this.bipedBody.addChild(this.dress);
		this.spike1_1.addChild(this.spike12_1);
		this.spike2.addChild(this.spike22);
		this.leftShoulder1.addChild(this.spike3);
		this.rightShoulder1.addChild(this.spike1_1);
		this.rightShoe1.addChild(this.rightShoe2);
		this.rightShoulder1.addChild(this.rightShoulder2);
		this.leftShoe1.addChild(this.leftShoe2);
		this.cowlBack2.addChild(this.cowlBack3);
		this.dress.addChild(this.dressBack);
		this.bipedRightLeg.addChild(this.rightShoe1);
		this.dress.addChild(this.dressFront);
		this.cowlBack1.addChild(this.cowlBack2);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
			float red, float green, float blue, float alpha) {
		dressBack.rotateAngleX = (float) Math.max(Math.toRadians(20),
				Math.max(bipedLeftLeg.rotateAngleX, bipedRightLeg.rotateAngleX) + Math.toRadians(15));
		dressFront.rotateAngleX = (float) Math.min(Math.toRadians(-20),
				Math.min(bipedLeftLeg.rotateAngleX, bipedRightLeg.rotateAngleX) - Math.toRadians(15));
		if (isSneak) {
			dress.rotateAngleX = (float) Math.toRadians(-30);
			dress.rotationPointY = 9f;
			dressBack.rotateAngleX += Math.toRadians(20);
			dressFront.rotateAngleX -= Math.toRadians(20);
		} else {
			dress.rotationPointY = 10.5f;
			dress.rotateAngleX = 0;
		}
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
		rightShoulder1.showModel = armorSlot == EquipmentSlotType.CHEST;
		leftShoulder1.showModel = armorSlot == EquipmentSlotType.CHEST;
		robe.showModel = armorSlot == EquipmentSlotType.CHEST;

		cowl.showModel = armorSlot == EquipmentSlotType.HEAD;

		dress.showModel = armorSlot == EquipmentSlotType.LEGS;

		rightShoe1.showModel = armorSlot == EquipmentSlotType.FEET;
		leftShoe1.showModel = armorSlot == EquipmentSlotType.FEET;

	}
}
