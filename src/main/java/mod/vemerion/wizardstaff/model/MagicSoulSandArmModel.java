package mod.vemerion.wizardstaff.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.vemerion.wizardstaff.entity.MagicSoulSandArmEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

/**
 * Created using Tabula 8.0.0
 */
public class MagicSoulSandArmModel extends EntityModel<MagicSoulSandArmEntity> {
	public ModelPart arm1;
	public ModelPart palm;
	public ModelPart arm2;
	public ModelPart thumb1;
	public ModelPart index1;
	public ModelPart middle1;
	public ModelPart little1;
	public ModelPart thumb2;
	public ModelPart index2;
	public ModelPart index3;
	public ModelPart middle2;
	public ModelPart middle3;
	public ModelPart little2;
	public ModelPart little3;

	public MagicSoulSandArmModel(ModelPart parts) {
		this.arm1 = parts.getChild("arm1");
		this.palm = arm1.getChild("palm");
		this.arm2 = arm1.getChild("arm2");
		this.thumb1 = palm.getChild("thumb1");
		this.index1 = palm.getChild("index1");
		this.middle1 = palm.getChild("middle1");
		this.little1 = palm.getChild("little1");
		this.thumb2 = thumb1.getChild("thumb2");
		this.index2 = index1.getChild("index2");
		this.index3 = index2.getChild("index3");
		this.middle2 = middle1.getChild("middle2");
		this.middle3 = middle2.getChild("middle3");
		this.little2 = little1.getChild("little2");
		this.little3 = little2.getChild("little3");
	}

	public static LayerDefinition createLayer() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition parts = mesh.getRoot();
		PartDefinition arm1 = parts.addOrReplaceChild("arm1",
				CubeListBuilder.create().texOffs(0, 0).addBox(1.0F, -32.0F, -1.5F, 3.0F, 32.0F, 3.0F, false),
				PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, 0.0F, -0.03490658503988659F));
		PartDefinition palm = arm1.addOrReplaceChild("palm",
				CubeListBuilder.create().texOffs(20, 0).addBox(-6.0F, -13.0F, -2.5F, 12.0F, 13.0F, 5.0F, false),
				PartPose.offsetAndRotation(0.0F, -32.0F, 0.0F, 0.0F, 0.0F, 0.06981317007977318F));
		arm1.addOrReplaceChild("arm2",
				CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -32.0F, -1.5F, 3.0F, 32.0F, 3.0F, false),
				PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.06981317007977318F));
		PartDefinition thumb1 = palm.addOrReplaceChild("thumb1",
				CubeListBuilder.create().texOffs(20, 18).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 4.0F, false),
				PartPose.offsetAndRotation(6.0F, -6.0F, 0.0F, 0.392350007858842F, 0.0F, 0.7428121536172364F));
		PartDefinition index1 = palm.addOrReplaceChild("index1",
				CubeListBuilder.create().texOffs(20, 18).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 4.0F, false),
				PartPose.offsetAndRotation(4.4F, -13.0F, 0.0F, 0.7916813593572721F, 0.0F, 0.1563815016444822F));
		PartDefinition middle1 = palm.addOrReplaceChild("middle1",
				CubeListBuilder.create().texOffs(20, 18).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 4.0F, false),
				PartPose.offsetAndRotation(0.2F, -13.0F, 0.0F, 0.24434609527920614F, 0.0F, 0.0F));
		PartDefinition little1 = palm.addOrReplaceChild("little1",
				CubeListBuilder.create().texOffs(20, 18).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 4.0F, false),
				PartPose.offsetAndRotation(-4.0F, -13.0F, 0.0F, 0.24434609527920614F, 0.0F, -0.13962634015954636F));
		thumb1.addOrReplaceChild("thumb2",
				CubeListBuilder.create().texOffs(36, 18).addBox(-1.5F, -4.0F, -1.5F, 3.0F, 4.0F, 3.0F, false),
				PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0.3127630032889644F, 0.0F, 0.0F));
		PartDefinition index2 = index1.addOrReplaceChild("index2",
				CubeListBuilder.create().texOffs(0, 37).addBox(-1.5F, -3.0F, -1.5F, 3.0F, 3.0F, 3.0F, false),
				PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0.19547687289441354F, 0.0F, 0.0F));
		index2.addOrReplaceChild("index3",
				CubeListBuilder.create().texOffs(15, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, false),
				PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.19547687289441354F, 0.0F, 0.0F));
		PartDefinition middle2 = middle1.addOrReplaceChild("middle2",
				CubeListBuilder.create().texOffs(0, 37).addBox(-1.5F, -3.0F, -1.5F, 3.0F, 3.0F, 3.0F, false),
				PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0.19547687289441354F, 0.0F, 0.0F));
		middle2.addOrReplaceChild("middle3",
				CubeListBuilder.create().texOffs(15, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, false),
				PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.19547687289441354F, 0.0F, 0.0F));
		PartDefinition little2 = little1.addOrReplaceChild("little2",
				CubeListBuilder.create().texOffs(0, 37).addBox(-1.5F, -3.0F, -1.5F, 3.0F, 3.0F, 3.0F, false),
				PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0.19547687289441354F, 0.0F, 0.0F));
		little2.addOrReplaceChild("little3",
				CubeListBuilder.create().texOffs(15, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, false),
				PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.19547687289441354F, 0.0F, 0.0F));
		return LayerDefinition.create(mesh, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn,
			float red, float green, float blue, float alpha) {
		ImmutableList.of(this.arm1).forEach((modelRenderer) -> {
			modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		});
	}

	@Override
	public void setupAnim(MagicSoulSandArmEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) {
		index1.xRot = (float) Math.abs(Mth.sin(((ageInTicks + 8) / 120) * (float) Math.PI * 2) * Math.toRadians(60));
		index2.xRot = (float) Math.abs(Mth.sin(((ageInTicks + 8) / 120) * (float) Math.PI * 2) * Math.toRadians(30));
		index3.xRot = (float) Math.abs(Mth.sin(((ageInTicks + 8) / 120) * (float) Math.PI * 2) * Math.toRadians(15));

		middle1.xRot = (float) Math.abs(Mth.sin(((ageInTicks + 2) / 120) * (float) Math.PI * 2) * Math.toRadians(60));
		middle2.xRot = (float) Math.abs(Mth.sin(((ageInTicks + 2) / 120) * (float) Math.PI * 2) * Math.toRadians(30));
		middle3.xRot = (float) Math.abs(Mth.sin(((ageInTicks + 2) / 120) * (float) Math.PI * 2) * Math.toRadians(15));

		little1.xRot = (float) Math.abs(Mth.sin(((ageInTicks - 6) / 120) * (float) Math.PI * 2) * Math.toRadians(60));
		little2.xRot = (float) Math.abs(Mth.sin(((ageInTicks - 6) / 120) * (float) Math.PI * 2) * Math.toRadians(30));
		little3.xRot = (float) Math.abs(Mth.sin(((ageInTicks - 6) / 120) * (float) Math.PI * 2) * Math.toRadians(15));

		thumb1.xRot = (float) Math.abs(Mth.sin(((ageInTicks + 4) / 120) * (float) Math.PI * 2) * Math.toRadians(60));
		thumb2.xRot = (float) Math.abs(Mth.sin(((ageInTicks + 4) / 120) * (float) Math.PI * 2) * Math.toRadians(30));

		palm.xRot = Mth.sin(((ageInTicks + 0) / 120) * (float) Math.PI * 2) * (float) Math.toRadians(15);

		arm1.yRot = entityIn.getRotation();
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelPart modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}
