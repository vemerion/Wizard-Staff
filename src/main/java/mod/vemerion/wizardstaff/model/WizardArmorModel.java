package mod.vemerion.wizardstaff.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

/**
 * Created using Tabula 8.0.0
 */

public class WizardArmorModel<T extends LivingEntity> extends MagicArmorModel<T> {
	public ModelPart rightShoulder;
	public ModelPart rightArm1;
	public ModelPart rightArm2;
	public ModelPart rightArm3;
	public ModelPart rightArm3_1;
	public ModelPart rightShoe1;
	public ModelPart rightShoe2;
	public ModelPart rightShoe3;
	public ModelPart rightShoe4;
	public ModelPart rightShoe5;
	public ModelPart robe;
	public ModelPart dress;
	public ModelPart dressFront;
	public ModelPart dressBack;
	public ModelPart dressFrontLeft;
	public ModelPart deessFrontRight;
	public ModelPart dressBackLeft;
	public ModelPart dressBackRight;
	public ModelPart leftShoulder;
	public ModelPart leftArm1;
	public ModelPart leftArm2;
	public ModelPart leftArm3;
	public ModelPart leftArm3_1;
	public ModelPart leftShoe1;
	public ModelPart leftShoe2;
	public ModelPart leftShoe3;
	public ModelPart leftShoe4;
	public ModelPart leftShoe5;

	public WizardHatModel<T> hatModel;

	public WizardArmorModel(ModelPart pRoot) {
		super(pRoot);
		this.rightShoulder = rightArm.getChild("rightShoulder");
		this.rightArm1 = rightShoulder.getChild("rightArm1");
		this.rightArm2 = rightArm1.getChild("rightArm2");
		this.rightArm3 = rightArm2.getChild("rightArm3");
		this.rightArm3_1 = rightArm3.getChild("rightArm3_1");
		this.rightShoe1 = rightLeg.getChild("rightShoe1");
		this.rightShoe2 = rightShoe1.getChild("rightShoe2");
		this.rightShoe3 = rightShoe2.getChild("rightShoe3");
		this.rightShoe4 = rightShoe3.getChild("rightShoe4");
		this.rightShoe5 = rightShoe4.getChild("rightShoe5");
		this.robe = body.getChild("robe");
		this.dress = body.getChild("dress");
		this.dressFront = dress.getChild("dressFront");
		this.dressBack = dress.getChild("dressBack");
		this.dressFrontLeft = dressFront.getChild("dressFrontLeft");
		this.deessFrontRight = dressFront.getChild("deessFrontRight");
		this.dressBackLeft = dressBack.getChild("dressBackLeft");
		this.dressBackRight = dressBack.getChild("dressBackRight");
		this.leftShoulder = leftArm.getChild("leftShoulder");
		this.leftArm1 = leftShoulder.getChild("leftArm1");
		this.leftArm2 = leftArm1.getChild("leftArm2");
		this.leftArm3 = leftArm2.getChild("leftArm3");
		this.leftArm3_1 = leftArm3.getChild("leftArm3_1");
		this.leftShoe1 = leftLeg.getChild("leftShoe1");
		this.leftShoe2 = leftShoe1.getChild("leftShoe2");
		this.leftShoe3 = leftShoe2.getChild("leftShoe3");
		this.leftShoe4 = leftShoe3.getChild("leftShoe4");
		this.leftShoe5 = leftShoe4.getChild("leftShoe5");

		hatModel = new WizardHatModel<T>(head);
	}

	// @formatter:off
	public static LayerDefinition createLayer() {
		var deformation = new CubeDeformation(0.3f);
		MeshDefinition mesh = HumanoidModel.createMesh(deformation, 0);
		PartDefinition parts = mesh.getRoot();
		PartDefinition bipedRightArm = parts.getChild("right_arm");
		PartDefinition bipedRightLeg = parts.getChild("right_leg");
		PartDefinition bipedHead = parts.getChild("head");
		PartDefinition bipedBody = parts.getChild("body");
		PartDefinition bipedLeftArm = parts.getChild("left_arm");
		PartDefinition bipedLeftLeg = parts.getChild("left_leg");
		
		WizardHatModel.fillParts(bipedHead);

	    PartDefinition rightShoulder = bipedRightArm.addOrReplaceChild("rightShoulder", CubeListBuilder.create().texOffs(0, 116).mirror().addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, deformation), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0, 0, 0));
	    PartDefinition rightArm1 = rightShoulder.addOrReplaceChild("rightArm1", CubeListBuilder.create().texOffs(24, 100).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, deformation), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0, 0, 0));
	    PartDefinition rightArm2 = rightArm1.addOrReplaceChild("rightArm2", CubeListBuilder.create().texOffs(24, 112).addBox(-1.5F, -3.0F, -1.5F, 3.0F, 3.0F, 3.0F), PartPose.offsetAndRotation(0.0F, 6.0F, 3.5F, 0, 0, 0));
	    PartDefinition rightArm3 = rightArm2.addOrReplaceChild("rightArm3", CubeListBuilder.create().texOffs(15, 116).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F), PartPose.offsetAndRotation(0.0F, -3.0F, -0.5F, 0, 0, 0));
	    rightArm3.addOrReplaceChild("rightArm3_1", CubeListBuilder.create().texOffs(0, 116).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F), PartPose.offsetAndRotation(0.0F, -3.0F, -0.5F, 0, 0, 0));
	    PartDefinition rightShoe1 = bipedRightLeg.addOrReplaceChild("rightShoe1", CubeListBuilder.create().texOffs(36, 82).addBox(-2.5F, 0.0F, -3.5F, 5.0F, 3.0F, 6.0F), PartPose.offsetAndRotation(0.0F, 9.01F, 0.0F, 0, 0, 0));
	    PartDefinition rightShoe2 = rightShoe1.addOrReplaceChild("rightShoe2", CubeListBuilder.create().texOffs(52, 82).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 3.0F, 2.0F), PartPose.offsetAndRotation(0.0F, 0.0F, -3.0F, -0.3490658503988659F, 0.0F, 0.0F));
	    PartDefinition rightShoe3 = rightShoe2.addOrReplaceChild("rightShoe3", CubeListBuilder.create().texOffs(36, 91).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 2.0F, 2.0F), PartPose.offsetAndRotation(0.0F, 0.5F, -1.5F, -0.3490658503988659F, 0.0F, 0.0F));
	    PartDefinition rightShoe4 = rightShoe3.addOrReplaceChild("rightShoe4", CubeListBuilder.create().texOffs(36, 84).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 1.0F, 1.0F), PartPose.offsetAndRotation(0.0F, 0.5F, -1.5F, -0.3490658503988659F, 0.0F, 0.0F));
	    rightShoe4.addOrReplaceChild("rightShoe5", CubeListBuilder.create().texOffs(36, 82).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F), PartPose.offsetAndRotation(0.0F, 0.0F, -0.8F, -0.3490658503988659F, 0.0F, 0.0F));
	    bipedBody.addOrReplaceChild("robe", CubeListBuilder.create().texOffs(0, 100).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, deformation), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0, 0, 0));
	    PartDefinition dress = bipedBody.addOrReplaceChild("dress", CubeListBuilder.create().texOffs(0, 100).addBox(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), PartPose.offsetAndRotation(0.0F, 10.5F, 0.0F, 0, 0, 0));
	    PartDefinition dressFront = dress.addOrReplaceChild("dressFront", CubeListBuilder.create().texOffs(36, 95).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 8.0F, 0.0F), PartPose.offsetAndRotation(0.0F, 0.0F, -2.0F, -0.3490658503988659F, 0.0F, 0.0F));
	    PartDefinition dressBack = dress.addOrReplaceChild("dressBack", CubeListBuilder.create().texOffs(36, 95).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 8.0F, 0.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, 0.3490658503988659F, 0.0F, 0.0F));
	    dressFront.addOrReplaceChild("dressFrontLeft", CubeListBuilder.create().texOffs(52, 91).addBox(0.0F, -1.0F, 0.0F, 0.0F, 9.0F, 4.0F), PartPose.offsetAndRotation(4.01F, 0.0F, 0.0F, 0, 0, 0));
	    dressFront.addOrReplaceChild("deessFrontRight", CubeListBuilder.create().texOffs(52, 91).addBox(0.0F, -1.0F, 0.0F, 0.0F, 9.0F, 4.0F), PartPose.offsetAndRotation(-4.01F, 0.0F, 0.0F, 0, 0, 0));
	    dressBack.addOrReplaceChild("dressBackLeft", CubeListBuilder.create().texOffs(52, 100).addBox(0.0F, -1.0F, 0.0F, 0.0F, 9.0F, 4.0F), PartPose.offsetAndRotation(4.0F, 0.0F, -4.0F, 0, 0, 0));
	    dressBack.addOrReplaceChild("dressBackRight", CubeListBuilder.create().texOffs(52, 100).addBox(0.0F, -1.0F, 0.0F, 0.0F, 9.0F, 4.0F), PartPose.offsetAndRotation(-4.0F, 0.0F, -4.0F, 0, 0, 0));
	    PartDefinition leftShoulder = bipedLeftArm.addOrReplaceChild("leftShoulder", CubeListBuilder.create().texOffs(0, 116).addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, deformation), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0, 0, 0));
	    PartDefinition leftArm1 = leftShoulder.addOrReplaceChild("leftArm1", CubeListBuilder.create().texOffs(24, 100).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, deformation), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0, 0, 0));
	    PartDefinition leftArm2 = leftArm1.addOrReplaceChild("leftArm2", CubeListBuilder.create().texOffs(24, 112).addBox(-1.5F, -3.0F, -1.5F, 3.0F, 3.0F, 3.0F), PartPose.offsetAndRotation(0.0F, 6.0F, 3.5F, 0, 0, 0));
	    PartDefinition leftArm3 = leftArm2.addOrReplaceChild("leftArm3", CubeListBuilder.create().texOffs(15, 116).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F), PartPose.offsetAndRotation(0.0F, -3.0F, -0.5F, 0, 0, 0));
	    leftArm3.addOrReplaceChild("leftArm3_1", CubeListBuilder.create().texOffs(0, 116).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F), PartPose.offsetAndRotation(0.0F, -3.0F, -0.5F, 0, 0, 0));
	    PartDefinition leftShoe1 = bipedLeftLeg.addOrReplaceChild("leftShoe1", CubeListBuilder.create().texOffs(36, 82).addBox(-2.5F, 0.0F, -3.5F, 5.0F, 3.0F, 6.0F), PartPose.offsetAndRotation(0.0F, 9.01F, 0.0F, 0, 0, 0));
	    PartDefinition leftShoe2 = leftShoe1.addOrReplaceChild("leftShoe2", CubeListBuilder.create().texOffs(52, 82).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 3.0F, 2.0F), PartPose.offsetAndRotation(0.0F, 0.0F, -3.0F, -0.3490658503988659F, 0.0F, 0.0F));
	    PartDefinition leftShoe3 = leftShoe2.addOrReplaceChild("leftShoe3", CubeListBuilder.create().texOffs(36, 91).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 2.0F, 2.0F), PartPose.offsetAndRotation(0.0F, 0.5F, -1.5F, -0.3490658503988659F, 0.0F, 0.0F));
	    PartDefinition leftShoe4 = leftShoe3.addOrReplaceChild("leftShoe4", CubeListBuilder.create().texOffs(36, 84).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 1.0F, 1.0F), PartPose.offsetAndRotation(0.0F, 0.5F, -1.5F, -0.3490658503988659F, 0.0F, 0.0F));
	    leftShoe4.addOrReplaceChild("leftShoe5", CubeListBuilder.create().texOffs(36, 82).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F), PartPose.offsetAndRotation(0.0F, 0.0F, -0.8F, -0.3490658503988659F, 0.0F, 0.0F));
	    return LayerDefinition.create(mesh, 64, 128);
	}
	// @formatter:on

	@Override
	public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn,
			float red, float green, float blue, float alpha) {
		dressBack.xRot = (float) Math.max(Math.toRadians(20),
				Math.max(leftLeg.xRot, rightLeg.xRot) + Math.toRadians(15));
		dressFront.xRot = (float) Math.min(Math.toRadians(-20),
				Math.min(leftLeg.xRot, rightLeg.xRot) - Math.toRadians(15));
		if (crouching) {
			dress.xRot = (float) Math.toRadians(-30);
			dress.y = 9f;
			dressBack.xRot += Math.toRadians(20);
			dressFront.xRot -= Math.toRadians(20);
		} else {
			dress.y = 10.5f;
			dress.xRot = 0;
		}
		super.renderToBuffer(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelPart modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}

	@Override
	public void setVisibility(EquipmentSlot armorSlot) {
		rightShoulder.visible = armorSlot == EquipmentSlot.CHEST;
		leftShoulder.visible = armorSlot == EquipmentSlot.CHEST;
		robe.visible = armorSlot == EquipmentSlot.CHEST;

		hatModel.setVisible(armorSlot == EquipmentSlot.HEAD);

		dress.visible = armorSlot == EquipmentSlot.LEGS;

		rightShoe1.visible = armorSlot == EquipmentSlot.FEET;
		leftShoe1.visible = armorSlot == EquipmentSlot.FEET;
	}
}
