package mod.vemerion.wizardstaff.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.vemerion.wizardstaff.entity.GrapplingHookEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

/**
 * Created using Tabula 8.0.0
 */
public class GrapplingHookModel extends EntityModel<GrapplingHookEntity> {
	public ModelPart base;
	public ModelPart hook1;
	public ModelPart hook3;
	public ModelPart hook5;
	public ModelPart hook2;
	public ModelPart hook4;
	public ModelPart hook6;

    public GrapplingHookModel(ModelPart parts) {
    	this.base = parts.getChild("base");
    	this.hook1 = base.getChild("hook1");
    	this.hook3 = base.getChild("hook3");
    	this.hook5 = base.getChild("hook5");
    	this.hook2 = hook1.getChild("hook2");
    	this.hook4 = hook3.getChild("hook4");
    	this.hook6 = hook5.getChild("hook6");
	}

    public static LayerDefinition createLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition parts = mesh.getRoot();
        PartDefinition base = parts.addOrReplaceChild("base", CubeListBuilder.create().texOffs(0, 14).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0, 0, 0));
        PartDefinition hook1 = base.addOrReplaceChild("hook1", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, -0.8726646259971648F, 0.0F, 0.0F));
        PartDefinition hook3 = base.addOrReplaceChild("hook3", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, -0.8726646259971648F, 0.0F, 2.0943951023931953F));
        PartDefinition hook5 = base.addOrReplaceChild("hook5", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, -0.8726646259971648F, 0.0F, -2.0943951023931953F));
        hook1.addOrReplaceChild("hook2", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, -0.2F, -2.6F, 1.1344640137963142F, 0.0F, 0.0F));
        hook3.addOrReplaceChild("hook4", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, -0.2F, -2.6F, 1.1344640137963142F, 0.0F, 0.0F));
        hook5.addOrReplaceChild("hook6", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, -0.2F, -2.6F, 1.1344640137963142F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 16, 16);
    }
    
	@Override
	public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn,
			float red, float green, float blue, float alpha) {
		ImmutableList.of(base).forEach((modelRenderer) -> {
			modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		});
	}

	@Override
	public void setupAnim(GrapplingHookEntity entityIn, float limbSwing, float limbSwingAmount,
			float ageInTicks, float netHeadYaw, float headPitch) {
		base.yRot = netHeadYaw;
		base.xRot = headPitch;
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
