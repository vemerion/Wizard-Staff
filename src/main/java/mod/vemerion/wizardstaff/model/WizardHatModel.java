package mod.vemerion.wizardstaff.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;

public class WizardHatModel<E extends Entity> extends EntityModel<E> {

	public ModelPart hat;
	public ModelPart top1;
	public ModelPart top2;
	public ModelPart top3;
	public ModelPart top4;

	public WizardHatModel(ModelPart parts) {
		this.hat = parts.getChild("hat");
		this.top1 = hat.getChild("top1");
		this.top2 = top1.getChild("top2");
		this.top3 = top2.getChild("top3");
		this.top4 = top3.getChild("top4");
	}

	public static void fillParts(PartDefinition root) {
		PartDefinition hat = root.addOrReplaceChild("hat",
				CubeListBuilder.create().texOffs(-18, 82).addBox(-9.0F, -8.0F, -9.0F, 18.0F, 0.0F, 18.0F, false),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0, 0, 0));
		PartDefinition top1 = hat.addOrReplaceChild("top1",
				CubeListBuilder.create().texOffs(0, 64).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 4.0F, 8.0F, false),
				PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0, 0, 0));
		PartDefinition top2 = top1.addOrReplaceChild("top2",
				CubeListBuilder.create().texOffs(32, 64).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 4.0F, 6.0F, false),
				PartPose.offsetAndRotation(0.0F, -3.6F, 0.0F, 0.17453292519943295F, 0.0F, 0.0F));
		PartDefinition top3 = top2.addOrReplaceChild("top3",
				CubeListBuilder.create().texOffs(28, 74).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, false),
				PartPose.offsetAndRotation(0.0F, -3.8F, 0.0F, 0.17453292519943295F, 0.0F, 0.0F));
		top3.addOrReplaceChild("top4",
				CubeListBuilder.create().texOffs(0, 64).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, false),
				PartPose.offsetAndRotation(0.0F, -4F, 0.0F, 0.17453292519943295F, 0.0F, 0.0F));
	}

	public static LayerDefinition createLayer() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();
		fillParts(root);
		return LayerDefinition.create(mesh, 64, 128);
	}

	public void setRotateAngle(ModelPart modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}

	public void setVisible(boolean b) {
		hat.visible = b;
	}

	@Override
	public void setupAnim(E entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn,
			float red, float green, float blue, float alpha) {
		hat.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}

}
