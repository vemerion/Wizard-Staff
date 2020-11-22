package mod.vemerion.wizardstaff.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.vemerion.wizardstaff.entity.GrapplingHookEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

/**
 * Created using Tabula 8.0.0
 */
public class GrapplingHookModel extends EntityModel<GrapplingHookEntity> {
	public ModelRenderer hook1;
	public ModelRenderer hook3;
	public ModelRenderer hook5;
	public ModelRenderer hook2;
	public ModelRenderer hook4;
	public ModelRenderer hook6;

	public GrapplingHookModel() {
		this.textureWidth = 16;
		this.textureHeight = 16;
		this.hook1 = new ModelRenderer(this, 0, 0);
		this.hook1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.hook1.addBox(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(hook1, -0.8726646259971648F, 0.0F, 0.0F);
		this.hook4 = new ModelRenderer(this, 0, 0);
		this.hook4.setRotationPoint(0.0F, -0.2F, -2.6F);
		this.hook4.addBox(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(hook4, 1.1344640137963142F, 0.0F, 0.0F);
		this.hook6 = new ModelRenderer(this, 0, 0);
		this.hook6.setRotationPoint(0.0F, -0.2F, -2.6F);
		this.hook6.addBox(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(hook6, 1.1344640137963142F, 0.0F, 0.0F);
		this.hook3 = new ModelRenderer(this, 0, 0);
		this.hook3.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.hook3.addBox(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(hook3, -0.8726646259971648F, 0.0F, 2.0943951023931953F);
		this.hook5 = new ModelRenderer(this, 0, 0);
		this.hook5.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.hook5.addBox(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(hook5, -0.8726646259971648F, 0.0F, -2.0943951023931953F);
		this.hook2 = new ModelRenderer(this, 0, 0);
		this.hook2.setRotationPoint(0.0F, -0.2F, -2.6F);
		this.hook2.addBox(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(hook2, 1.1344640137963142F, 0.0F, 0.0F);
		this.hook3.addChild(this.hook4);
		this.hook5.addChild(this.hook6);
		this.hook1.addChild(this.hook2);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
			float red, float green, float blue, float alpha) {
		ImmutableList.of(this.hook1, this.hook3, this.hook5).forEach((modelRenderer) -> {
			modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		});
	}

	@Override
	public void setRotationAngles(GrapplingHookEntity entityIn, float limbSwing, float limbSwingAmount,
			float ageInTicks, float netHeadYaw, float headPitch) {
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
