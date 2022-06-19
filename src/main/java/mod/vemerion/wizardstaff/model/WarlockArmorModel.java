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
public class WarlockArmorModel<T extends LivingEntity> extends MagicArmorModel<T> {
	public ModelPart leftShoulder1;
	public ModelPart leftShoulder2;
	public ModelPart spike1;
	public ModelPart spike2;
	public ModelPart spike3;
	public ModelPart leftSleeve;
	public ModelPart leftShoulder3;
	public ModelPart spike12;
	public ModelPart spike22;
	public ModelPart spike32;
	public ModelPart rightShoe1;
	public ModelPart rightShoe2;
	public ModelPart rightShoe3;
	public ModelPart rightShoe4;
	public ModelPart cowl;
	public ModelPart cowlBack1;
	public ModelPart cowlBack2;
	public ModelPart cowlBack3;
	public ModelPart robe;
	public ModelPart dress;
	public ModelPart dressFront;
	public ModelPart dressBack;
	public ModelPart dressLeft;
	public ModelPart dressRight;
	public ModelPart rightShoulder1;
	public ModelPart rightShoulder2;
	public ModelPart spike1_1;
	public ModelPart spike2_1;
	public ModelPart spike3_1;
	public ModelPart leftSleeve_1;
	public ModelPart rightShoulder3;
	public ModelPart spike12_1;
	public ModelPart spike22_1;
	public ModelPart spike32_1;
	public ModelPart leftShoe1;
	public ModelPart leftShoe2;
	public ModelPart leftShoe3;
	public ModelPart leftShoe4;

	public WarlockArmorModel(ModelPart part) {
		super(part);
		this.leftShoulder1 = rightArm.getChild("leftShoulder1");
		this.leftShoulder2 = leftShoulder1.getChild("leftShoulder2");
		this.spike1 = leftShoulder1.getChild("spike1");
		this.spike2 = leftShoulder1.getChild("spike2");
		this.spike3 = leftShoulder1.getChild("spike3");
		this.leftSleeve = leftShoulder1.getChild("leftSleeve");
		this.leftShoulder3 = leftShoulder2.getChild("leftShoulder3");
		this.spike12 = spike1.getChild("spike12");
		this.spike22 = spike2.getChild("spike22");
		this.spike32 = spike3.getChild("spike32");
		this.rightShoe1 = rightLeg.getChild("rightShoe1");
		this.rightShoe2 = rightShoe1.getChild("rightShoe2");
		this.rightShoe3 = rightShoe2.getChild("rightShoe3");
		this.rightShoe4 = rightShoe3.getChild("rightShoe4");
		this.cowl = head.getChild("cowl");
		this.cowlBack1 = cowl.getChild("cowlBack1");
		this.cowlBack2 = cowlBack1.getChild("cowlBack2");
		this.cowlBack3 = cowlBack2.getChild("cowlBack3");
		this.robe = body.getChild("robe");
		this.dress = body.getChild("dress");
		this.dressFront = dress.getChild("dressFront");
		this.dressBack = dress.getChild("dressBack");
		this.dressLeft = dress.getChild("dressLeft");
		this.dressRight = dress.getChild("dressRight");
		this.rightShoulder1 = leftArm.getChild("rightShoulder1");
		this.rightShoulder2 = rightShoulder1.getChild("rightShoulder2");
		this.spike1_1 = rightShoulder1.getChild("spike1_1");
		this.spike2_1 = rightShoulder1.getChild("spike2_1");
		this.spike3_1 = rightShoulder1.getChild("spike3_1");
		this.leftSleeve_1 = rightShoulder1.getChild("leftSleeve_1");
		this.rightShoulder3 = rightShoulder2.getChild("rightShoulder3");
		this.spike12_1 = spike1_1.getChild("spike12_1");
		this.spike22_1 = spike2_1.getChild("spike22_1");
		this.spike32_1 = spike3_1.getChild("spike32_1");
		this.leftShoe1 = leftLeg.getChild("leftShoe1");
		this.leftShoe2 = leftShoe1.getChild("leftShoe2");
		this.leftShoe3 = leftShoe2.getChild("leftShoe3");
		this.leftShoe4 = leftShoe3.getChild("leftShoe4");
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

	    PartDefinition leftShoulder1 = bipedRightArm.addOrReplaceChild("leftShoulder1", CubeListBuilder.create().texOffs(0, 116).mirror().addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0, 0, 0));
	    PartDefinition leftShoulder2 = leftShoulder1.addOrReplaceChild("leftShoulder2", CubeListBuilder.create().texOffs(20, 121).mirror().addBox(0.0F, -2.0F, -1.5F, 1.0F, 4.0F, 3.0F), PartPose.offsetAndRotation(-3.2F, 0.5F, 0.0F, 0, 0, 0));
	    PartDefinition spike1 = leftShoulder1.addOrReplaceChild("spike1", CubeListBuilder.create().texOffs(59, 116).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F), PartPose.offsetAndRotation(-1.1F, -2.5F, 1.2F, -0.11728612207217244F, -0.23457224414434488F, -0.03909537541112055F));
	    PartDefinition spike2 = leftShoulder1.addOrReplaceChild("spike2", CubeListBuilder.create().texOffs(59, 116).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F), PartPose.offsetAndRotation(0.7F, -2.5F, -0.1F, -0.11728612207217244F, 1.7201964681550337F, -0.03909537541112055F));
	    PartDefinition spike3 = leftShoulder1.addOrReplaceChild("spike3", CubeListBuilder.create().texOffs(59, 116).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F), PartPose.offsetAndRotation(-1.1F, -2.5F, -1.3F, -0.11728612207217244F, -1.407433498155583F, -0.03909537541112055F));
	    leftShoulder1.addOrReplaceChild("leftSleeve", CubeListBuilder.create().texOffs(0, 90).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0, 0, 0));
	    leftShoulder2.addOrReplaceChild("leftShoulder3", CubeListBuilder.create().texOffs(15, 116).mirror().addBox(0.0F, -1.5F, -1.0F, 1.0F, 3.0F, 2.0F), PartPose.offsetAndRotation(-0.7F, 0.5F, 0.0F, 0, 0, 0));
	    spike1.addOrReplaceChild("spike12", CubeListBuilder.create().texOffs(59, 116).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.27366763203903305F, -0.6646214111173737F, -0.0781907508222411F));
	    spike2.addOrReplaceChild("spike22", CubeListBuilder.create().texOffs(59, 116).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.27366763203903305F, -0.6646214111173737F, -0.0781907508222411F));
	    spike3.addOrReplaceChild("spike32", CubeListBuilder.create().texOffs(59, 116).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.27366763203903305F, -0.6646214111173737F, -0.0781907508222411F));
	    PartDefinition rightShoe1 = bipedRightLeg.addOrReplaceChild("rightShoe1", CubeListBuilder.create().texOffs(44, 115).mirror().addBox(-2.5F, 0.0F, -2.5F, 5.0F, 8.0F, 5.0F), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0, 0, 0));
	    PartDefinition rightShoe2 = rightShoe1.addOrReplaceChild("rightShoe2", CubeListBuilder.create().texOffs(32, 123).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 3.0F, 2.0F), PartPose.offsetAndRotation(0.0F, 5.0F, -1.8F, 0, 0, 0));
	    PartDefinition rightShoe3 = rightShoe2.addOrReplaceChild("rightShoe3", CubeListBuilder.create().texOffs(36, 120).mirror().addBox(-1.5F, 0.0F, -1.0F, 3.0F, 2.0F, 1.0F), PartPose.offsetAndRotation(0.0F, 1.0F, -1.9F, 0, 0, 0));
	    rightShoe3.addOrReplaceChild("rightShoe4", CubeListBuilder.create().texOffs(43, 118).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 1.0F, 1.0F), PartPose.offsetAndRotation(0.0F, 1.0F, -0.6F, 0, 0, 0));
	    PartDefinition cowl = bipedHead.addOrReplaceChild("cowl", CubeListBuilder.create().texOffs(0, 73).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, deformation), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0, 0, 0));
	    PartDefinition cowlBack1 = cowl.addOrReplaceChild("cowlBack1", CubeListBuilder.create().texOffs(0, 64).addBox(-2.5F, -2.5F, 0.0F, 5.0F, 5.0F, 4.0F, deformation), PartPose.offsetAndRotation(0.0F, -5.6F, 3.0F, -0.6255260065779288F, 0.0F, 0.0F));
	    PartDefinition cowlBack2 = cowlBack1.addOrReplaceChild("cowlBack2", CubeListBuilder.create().texOffs(18, 64).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 2.0F, deformation), PartPose.offsetAndRotation(0.0F, 0.0F, 4.0F, -0.23457224414434488F, 0.0F, 0.0F));
	    cowlBack2.addOrReplaceChild("cowlBack3", CubeListBuilder.create().texOffs(0, 64).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 1.0F, deformation), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, -0.23457224414434488F, 0.0F, 0.0F));
	    bipedBody.addOrReplaceChild("robe", CubeListBuilder.create().texOffs(0, 100).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0, 0, 0));
	    PartDefinition dress = bipedBody.addOrReplaceChild("dress", CubeListBuilder.create().texOffs(0, 100).addBox(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), PartPose.offsetAndRotation(0.0F, 12.0F, 0.0F, 0, 0, 0));
	    dress.addOrReplaceChild("dressFront", CubeListBuilder.create().texOffs(48, 95).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 8.0F, 0.0F), PartPose.offsetAndRotation(0.0F, 0.0F, -2.0F, -0.4363323129985824F, 0.0F, 0.0F));
	    dress.addOrReplaceChild("dressBack", CubeListBuilder.create().texOffs(48, 95).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 8.0F, 0.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, 0.4363323129985824F, 0.0F, 0.0F));
	    dress.addOrReplaceChild("dressLeft", CubeListBuilder.create().texOffs(28, 99).addBox(0.0F, 0.0F, -2.0F, 0.0F, 8.0F, 4.0F), PartPose.offsetAndRotation(4.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2617993877991494F));
	    dress.addOrReplaceChild("dressRight", CubeListBuilder.create().texOffs(28, 99).addBox(0.0F, 0.0F, -2.0F, 0.0F, 8.0F, 4.0F), PartPose.offsetAndRotation(-4.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2617993877991494F));
	    PartDefinition rightShoulder1 = bipedLeftArm.addOrReplaceChild("rightShoulder1", CubeListBuilder.create().texOffs(0, 116).addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0, 0, 0));
	    PartDefinition rightShoulder2 = rightShoulder1.addOrReplaceChild("rightShoulder2", CubeListBuilder.create().texOffs(20, 121).addBox(0.0F, -2.0F, -1.5F, 1.0F, 4.0F, 3.0F), PartPose.offsetAndRotation(2.2F, 0.5F, 0.0F, 0, 0, 0));
	    PartDefinition spike1_1 = rightShoulder1.addOrReplaceChild("spike1_1", CubeListBuilder.create().texOffs(59, 117).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F), PartPose.offsetAndRotation(-0.6F, -2.5F, 1.2F, -0.11728612207217244F, -0.23457224414434488F, -0.03909537541112055F));
	    PartDefinition spike2_1 = rightShoulder1.addOrReplaceChild("spike2_1", CubeListBuilder.create().texOffs(59, 117).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F), PartPose.offsetAndRotation(1.6F, -2.5F, -0.4F, -0.11728612207217244F, 1.7201964681550337F, -0.03909537541112055F));
	    PartDefinition spike3_1 = rightShoulder1.addOrReplaceChild("spike3_1", CubeListBuilder.create().texOffs(59, 117).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F), PartPose.offsetAndRotation(-0.5F, -2.5F, -1.3F, -0.11728612207217244F, -1.407433498155583F, -0.03909537541112055F));
	    rightShoulder1.addOrReplaceChild("leftSleeve_1", CubeListBuilder.create().texOffs(0, 90).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0, 0, 0));
	    rightShoulder2.addOrReplaceChild("rightShoulder3", CubeListBuilder.create().texOffs(15, 116).addBox(0.0F, -1.5F, -1.0F, 1.0F, 3.0F, 2.0F), PartPose.offsetAndRotation(0.7F, 0.5F, 0.0F, 0, 0, 0));
	    spike1_1.addOrReplaceChild("spike12_1", CubeListBuilder.create().texOffs(59, 117).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.27366763203903305F, -0.6646214111173737F, -0.0781907508222411F));
	    spike2_1.addOrReplaceChild("spike22_1", CubeListBuilder.create().texOffs(59, 116).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.27366763203903305F, -0.6646214111173737F, -0.0781907508222411F));
	    spike3_1.addOrReplaceChild("spike32_1", CubeListBuilder.create().texOffs(59, 118).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.27366763203903305F, -0.6646214111173737F, -0.0781907508222411F));
	    PartDefinition leftShoe1 = bipedLeftLeg.addOrReplaceChild("leftShoe1", CubeListBuilder.create().texOffs(44, 115).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 8.0F, 5.0F), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0, 0, 0));
	    PartDefinition leftShoe2 = leftShoe1.addOrReplaceChild("leftShoe2", CubeListBuilder.create().texOffs(32, 123).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 3.0F, 2.0F), PartPose.offsetAndRotation(0.0F, 5.0F, -1.8F, 0, 0, 0));
	    PartDefinition leftShoe3 = leftShoe2.addOrReplaceChild("leftShoe3", CubeListBuilder.create().texOffs(36, 120).addBox(-1.5F, 0.0F, -1.0F, 3.0F, 2.0F, 1.0F), PartPose.offsetAndRotation(0.0F, 1.0F, -1.9F, 0, 0, 0));
	    leftShoe3.addOrReplaceChild("leftShoe4", CubeListBuilder.create().texOffs(43, 118).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 1.0F, 1.0F), PartPose.offsetAndRotation(0.0F, 1.0F, -0.6F, 0, 0, 0));
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
		rightShoulder1.visible = armorSlot == EquipmentSlot.CHEST;
		leftShoulder1.visible = armorSlot == EquipmentSlot.CHEST;
		robe.visible = armorSlot == EquipmentSlot.CHEST;

		cowl.visible = armorSlot == EquipmentSlot.HEAD;

		dress.visible = armorSlot == EquipmentSlot.LEGS;

		rightShoe1.visible = armorSlot == EquipmentSlot.FEET;
		leftShoe1.visible = armorSlot == EquipmentSlot.FEET;

	}
}
