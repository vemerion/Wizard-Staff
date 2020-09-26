package mod.vemerion.wizardstaff.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.vemerion.wizardstaff.entity.NetherPortalEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

/**
 * Created using Tabula 8.0.0
 */
public class NetherPortalModel extends EntityModel<NetherPortalEntity> {
	public ModelRenderer portal;

	public NetherPortalModel() {
		super(RenderType::getEntityCutoutNoCull);
        this.textureWidth = 512;
        this.textureHeight = 1024;
        this.portal = new ModelRenderer(this, 0, 0);
        this.portal.setTextureSize(32, 32);
        this.portal.setRotationPoint(0.0F, 8.0F, 0.0F);
        this.portal.addBox(-8.0F, -16.0F, 0.0F, 16.0F, 32.0F, 0.0F, 0.0F, 0.0F, 0.0F); 
	}
	

	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
			float red, float green, float blue, float alpha) {
		ImmutableList.of(this.portal).forEach((modelRenderer) -> {
			modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		});
	}

	@Override
	public void setRotationAngles(NetherPortalEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) {
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
