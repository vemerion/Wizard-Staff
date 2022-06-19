package mod.vemerion.wizardstaff.model;

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
public class DruidArmorModel<T extends LivingEntity> extends MagicArmorModel<T> {

	public ModelPart rightShoulder;
	public ModelPart rightSleeve;
	public ModelPart rightShoulderFur1;
	public ModelPart rightShoulderFur2;
	public ModelPart rightShoulderFur3;
	public ModelPart rightShoe1;
	public ModelPart rightPants;
	public ModelPart rightShoeSleeve;
	public ModelPart rightShoe3;
	public ModelPart rightShoe4;
	public ModelPart jaw;
	public ModelPart skull;
	public ModelPart leftHorn1;
	public ModelPart rightEye;
	public ModelPart leftEye;
	public ModelPart leftHorn2;
	public ModelPart leftHorn4;
	public ModelPart leftHorn3;
	public ModelPart leftHorn5;
	public ModelPart leftHorn6;
	public ModelPart robe;
	public ModelPart belt;
	public ModelPart belt1;
	public ModelPart leftShoulder;
	public ModelPart leftSleeve;
	public ModelPart leftShoulderFur1;
	public ModelPart leftShoulderFur2;
	public ModelPart leftShoulderFur3;
	public ModelPart leftShoe1;
	public ModelPart leftPants;
	public ModelPart leftShoeSleeve;
	public ModelPart leftShoe3;
	public ModelPart leftShoe4;
	public ModelPart rightHorn1;
	public ModelPart rightHorn2;
	public ModelPart rightHorn4;
	public ModelPart rightHorn3;
	public ModelPart rightHorn5;
	public ModelPart rightHorn6;

	public DruidArmorModel(ModelPart pRoot) {
		super(pRoot);
		this.rightShoulder = rightArm.getChild("rightShoulder");
		this.rightSleeve = rightArm.getChild("rightSleeve");
		this.rightShoulderFur1 = rightShoulder.getChild("rightShoulderFur1");
		this.rightShoulderFur2 = rightShoulder.getChild("rightShoulderFur2");
		this.rightShoulderFur3 = rightShoulder.getChild("rightShoulderFur3");
		this.rightShoe1 = rightLeg.getChild("rightShoe1");
		this.rightPants = rightLeg.getChild("rightPants");
		this.rightShoeSleeve = rightShoe1.getChild("rightShoeSleeve");
		this.rightShoe3 = rightShoe1.getChild("rightShoe3");
		this.rightShoe4 = rightShoe3.getChild("rightShoe4");
		this.jaw = head.getChild("jaw");
		this.skull = jaw.getChild("skull");
		this.leftHorn1 = skull.getChild("leftHorn1");
		this.rightEye = skull.getChild("rightEye");
		this.leftEye = skull.getChild("leftEye");
		this.leftHorn2 = leftHorn1.getChild("leftHorn2");
		this.leftHorn4 = leftHorn1.getChild("leftHorn4");
		this.leftHorn3 = leftHorn2.getChild("leftHorn3");
		this.leftHorn5 = leftHorn2.getChild("leftHorn5");
		this.leftHorn6 = leftHorn3.getChild("leftHorn6");
		this.robe = body.getChild("robe");
		this.belt = body.getChild("belt");
		this.belt1 = belt.getChild("belt1");
		this.leftShoulder = leftArm.getChild("leftShoulder");
		this.leftSleeve = leftArm.getChild("leftSleeve");
		this.leftShoulderFur1 = leftShoulder.getChild("leftShoulderFur1");
		this.leftShoulderFur2 = leftShoulder.getChild("leftShoulderFur2");
		this.leftShoulderFur3 = leftShoulder.getChild("leftShoulderFur3");
		this.leftShoe1 = leftLeg.getChild("leftShoe1");
		this.leftPants = leftLeg.getChild("leftPants");
		this.leftShoeSleeve = leftShoe1.getChild("leftShoeSleeve");
		this.leftShoe3 = leftShoe1.getChild("leftShoe3");
		this.leftShoe4 = leftShoe3.getChild("leftShoe4");
		this.rightHorn1 = skull.getChild("rightHorn1");
		this.rightHorn2 = rightHorn1.getChild("rightHorn2");
		this.rightHorn4 = rightHorn1.getChild("rightHorn4");
		this.rightHorn3 = rightHorn2.getChild("rightHorn3");
		this.rightHorn5 = rightHorn2.getChild("rightHorn5");
		this.rightHorn6 = rightHorn3.getChild("rightHorn6");
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

	    PartDefinition rightShoulder = bipedRightArm.addOrReplaceChild("rightShoulder", CubeListBuilder.create().texOffs(38, 64).mirror().addBox(-3.0F, -2.0F, -2.5F, 6.0F, 4.0F, 5.0F, deformation), PartPose.offsetAndRotation(-2.4F, -1.2F, 0.0F, 0.0F, 0.0F, -0.7853981633974483F));
	    bipedRightArm.addOrReplaceChild("rightSleeve", CubeListBuilder.create().texOffs(38, 73).mirror().addBox(-3.0F, -2.0F, -2.0F, 4.0F, 7.0F, 4.0F, deformation), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0, 0, 0));
	    rightShoulder.addOrReplaceChild("rightShoulderFur1", CubeListBuilder.create().texOffs(20, 55).mirror().addBox(-3.0F, -4.0F, -4.5F, 0.0F, 6.0F, 9.0F), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, -0.11728612207217244F, 0.0F, -0.19547687289441354F));
	    rightShoulder.addOrReplaceChild("rightShoulderFur2", CubeListBuilder.create().texOffs(20, 55).mirror().addBox(-3.0F, -4.0F, -4.5F, 0.0F, 6.0F, 9.0F), PartPose.offsetAndRotation(3.0F, 0.0F, 0.0F, 0.23457224414434488F, 0.0F, -0.0781907508222411F));
	    rightShoulder.addOrReplaceChild("rightShoulderFur3", CubeListBuilder.create().texOffs(20, 55).mirror().addBox(-3.0F, -4.0F, -4.5F, 0.0F, 6.0F, 9.0F), PartPose.offsetAndRotation(5.0F, 0.0F, 0.0F, 0.1563815016444822F, 0.0F, 0.19547687289441354F));
	    PartDefinition rightShoe1 = bipedRightLeg.addOrReplaceChild("rightShoe1", CubeListBuilder.create().texOffs(36, 116).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, deformation), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0, 0, 0));
	    bipedRightLeg.addOrReplaceChild("rightPants", CubeListBuilder.create().texOffs(38, 84).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 4.0F, 4.0F, deformation), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0, 0, 0));
	    rightShoe1.addOrReplaceChild("rightShoeSleeve", CubeListBuilder.create().texOffs(16, 116).mirror().addBox(-2.5F, 0.0F, -2.5F, 5.0F, 2.0F, 5.0F, deformation), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0, 0, 0));
	    PartDefinition rightShoe3 = rightShoe1.addOrReplaceChild("rightShoe3", CubeListBuilder.create().texOffs(0, 124).mirror().addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 1.0F, deformation), PartPose.offsetAndRotation(0.0F, 2.5F, -2.0F, 0, 0, 0));
	    rightShoe3.addOrReplaceChild("rightShoe4", CubeListBuilder.create().texOffs(14, 123).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 3.0F, 2.0F, deformation), PartPose.offsetAndRotation(0.0F, 0.4F, -1.0F, 0, 0, 0));
	    PartDefinition jaw = bipedHead.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 73).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, deformation), PartPose.offsetAndRotation(0.0F, -9.5F, -3.0F, 0, 0, 0));
	    PartDefinition skull = jaw.addOrReplaceChild("skull", CubeListBuilder.create().texOffs(0, 64).addBox(-2.5F, -2.0F, -2.5F, 5.0F, 4.0F, 5.0F, deformation), PartPose.offsetAndRotation(0.0F, -0.5F, 4.0F, 0, 0, 0));
	    PartDefinition leftHorn1 = skull.addOrReplaceChild("leftHorn1", CubeListBuilder.create().texOffs(0, 64).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F), PartPose.offsetAndRotation(2.1F, -1.9F, 1.3F, -0.27366763203903305F, 0.0F, 0.4300491170387584F));
	    skull.addOrReplaceChild("rightEye", CubeListBuilder.create().texOffs(0, 79).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 2.0F, 2.0F, deformation), PartPose.offsetAndRotation(-1.5F, 0.0F, -2.5F, 0, 0, 0));
	    skull.addOrReplaceChild("leftEye", CubeListBuilder.create().texOffs(0, 79).mirror().addBox(-1.0F, -2.0F, 0.0F, 2.0F, 2.0F, 2.0F, deformation), PartPose.offsetAndRotation(1.5F, 0.0F, -2.5F, 0, 0, 0));
	    PartDefinition leftHorn2 = leftHorn1.addOrReplaceChild("leftHorn2", CubeListBuilder.create().texOffs(0, 64).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.6646214111173737F, -0.35185837453889574F, 0.0F));
	    leftHorn1.addOrReplaceChild("leftHorn4", CubeListBuilder.create().texOffs(0, 64).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.3127630032889644F, 0.0F, -1.0946705281561322F));
	    PartDefinition leftHorn3 = leftHorn2.addOrReplaceChild("leftHorn3", CubeListBuilder.create().texOffs(0, 64).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 1.1728612040769677F, 0.5082398928281348F, 0.0F));
	    leftHorn2.addOrReplaceChild("leftHorn5", CubeListBuilder.create().texOffs(0, 64).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.46914448828868976F, 0.0F, -1.2510520131558576F));
	    leftHorn3.addOrReplaceChild("leftHorn6", CubeListBuilder.create().texOffs(0, 64).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F), PartPose.offsetAndRotation(0.0F, -1.5F, 0.0F, 0.19547687289441354F, 0.0F, -1.2901473511162753F));
	    bipedBody.addOrReplaceChild("robe", CubeListBuilder.create().texOffs(0, 100).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, deformation), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0, 0, 0));
	    PartDefinition belt = bipedBody.addOrReplaceChild("belt", CubeListBuilder.create().texOffs(0, 83).addBox(-4.5F, 0.0F, -2.5F, 9.0F, 2.0F, 5.0F, deformation), PartPose.offsetAndRotation(0.0F, 9.0F, 0.0F, 0, 0, 0));
	    belt.addOrReplaceChild("belt1", CubeListBuilder.create().texOffs(0, 90).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 1.0F, deformation), PartPose.offsetAndRotation(2.4F, 0.0F, -2.0F, 0, 0, 0));
	    PartDefinition leftShoulder = bipedLeftArm.addOrReplaceChild("leftShoulder", CubeListBuilder.create().texOffs(38, 64).addBox(-3.0F, -2.0F, -2.5F, 6.0F, 4.0F, 5.0F, deformation), PartPose.offsetAndRotation(2.4F, -1.2F, 0.0F, 0.0F, 0.0F, 0.7853981633974483F));
	    bipedLeftArm.addOrReplaceChild("leftSleeve", CubeListBuilder.create().texOffs(38, 73).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 7.0F, 4.0F, deformation), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0, 0, 0));
	    leftShoulder.addOrReplaceChild("leftShoulderFur1", CubeListBuilder.create().texOffs(20, 55).addBox(-3.0F, -4.0F, -4.5F, 0.0F, 6.0F, 9.0F), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, -0.11728612207217244F, 0.0F, -0.19547687289441354F));
	    leftShoulder.addOrReplaceChild("leftShoulderFur2", CubeListBuilder.create().texOffs(20, 55).addBox(-3.0F, -4.0F, -4.5F, 0.0F, 6.0F, 9.0F), PartPose.offsetAndRotation(3.0F, 0.0F, 0.0F, 0.23457224414434488F, 0.0F, -0.0781907508222411F));
	    leftShoulder.addOrReplaceChild("leftShoulderFur3", CubeListBuilder.create().texOffs(20, 55).addBox(-3.0F, -4.0F, -4.5F, 0.0F, 6.0F, 9.0F), PartPose.offsetAndRotation(5.0F, 0.0F, 0.0F, 0.1563815016444822F, 0.0F, 0.19547687289441354F));
	    PartDefinition leftShoe1 = bipedLeftLeg.addOrReplaceChild("leftShoe1", CubeListBuilder.create().texOffs(36, 116).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, deformation), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0, 0, 0));
	    bipedLeftLeg.addOrReplaceChild("leftPants", CubeListBuilder.create().texOffs(38, 84).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 4.0F, 4.0F, deformation), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0, 0, 0));
	    leftShoe1.addOrReplaceChild("leftShoeSleeve", CubeListBuilder.create().texOffs(16, 116).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 2.0F, 5.0F, deformation), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0, 0, 0));
	    PartDefinition leftShoe3 = leftShoe1.addOrReplaceChild("leftShoe3", CubeListBuilder.create().texOffs(0, 124).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 1.0F, deformation), PartPose.offsetAndRotation(0.0F, 2.5F, -2.0F, 0, 0, 0));
	    leftShoe3.addOrReplaceChild("leftShoe4", CubeListBuilder.create().texOffs(14, 123).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 3.0F, 2.0F, deformation), PartPose.offsetAndRotation(0.0F, 0.4F, -1.0F, 0, 0, 0));
	    PartDefinition rightHorn1 = skull.addOrReplaceChild("rightHorn1", CubeListBuilder.create().texOffs(0, 64).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F), PartPose.offsetAndRotation(-2.1F, -1.9F, 1.3F, -0.27366763203903305F, 0.0F, -0.4300491170387584F));
	    PartDefinition rightHorn2 = rightHorn1.addOrReplaceChild("rightHorn2", CubeListBuilder.create().texOffs(0, 64).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.6646214111173737F, 0.35185837453889574F, 0.0F));
	    rightHorn1.addOrReplaceChild("rightHorn4", CubeListBuilder.create().texOffs(0, 64).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.3127630032889644F, 0.0F, 1.0946705281561322F));
	    PartDefinition rightHorn3 = rightHorn2.addOrReplaceChild("rightHorn3", CubeListBuilder.create().texOffs(0, 64).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 1.1728612040769677F, -0.5082398928281348F, 0.0F));
	    rightHorn2.addOrReplaceChild("rightHorn5", CubeListBuilder.create().texOffs(0, 64).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.46914448828868976F, 0.0F, 1.2510520131558576F));
	    rightHorn3.addOrReplaceChild("rightHorn6", CubeListBuilder.create().texOffs(0, 64).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F), PartPose.offsetAndRotation(0.0F, -1.5F, 0.0F, 0.19547687289441354F, 0.0F, 1.2901473511162753F));
	    return LayerDefinition.create(mesh, 64, 128);
	}
	// @formatter:on

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
		rightSleeve.visible = armorSlot == EquipmentSlot.CHEST;
		leftSleeve.visible = armorSlot == EquipmentSlot.CHEST;
		robe.visible = armorSlot == EquipmentSlot.CHEST;

		jaw.visible = armorSlot == EquipmentSlot.HEAD;

		belt.visible = armorSlot == EquipmentSlot.LEGS;
		rightPants.visible = armorSlot == EquipmentSlot.LEGS;
		leftPants.visible = armorSlot == EquipmentSlot.LEGS;

		rightShoe1.visible = armorSlot == EquipmentSlot.FEET;
		leftShoe1.visible = armorSlot == EquipmentSlot.FEET;

	}
}
