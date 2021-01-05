package mod.vemerion.wizardstaff.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class WizardHatModel<E extends Entity> extends EntityModel<E> {
	
	public ModelRenderer hat;
	public ModelRenderer top1;
	public ModelRenderer top2;
	public ModelRenderer top3;
	public ModelRenderer top4;
	
	public WizardHatModel(int textureWidthIn, int textureHeightIn) {
		this.textureWidth = textureWidthIn;
		this.textureHeight = textureHeightIn;
		this.top1 = new ModelRenderer(this, 0, 64);
		this.top1.setRotationPoint(0.0F, -8.0F, 0.0F);
		this.top1.addBox(-4.0F, -4.0F, -4.0F, 8.0F, 4.0F, 8.0F, 0.0F, 0.0F, 0.0F);
		this.top2 = new ModelRenderer(this, 32, 64);
		this.top2.setRotationPoint(0.0F, -3.6F, 0.0F);
		this.top2.addBox(-3.0F, -4.0F, -3.0F, 6.0F, 4.0F, 6.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(top2, 0.17453292519943295F, 0.0F, 0.0F);
		this.top3 = new ModelRenderer(this, 28, 74);
		this.top3.setRotationPoint(0.0F, -3.8F, 0.0F);
		this.top3.addBox(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(top3, 0.17453292519943295F, 0.0F, 0.0F);
		this.top4 = new ModelRenderer(this, 0, 64);
		this.top4.setRotationPoint(0.0F, -4F, 0.0F);
		this.top4.addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(top4, 0.17453292519943295F, 0.0F, 0.0F);
		this.hat = new ModelRenderer(this, -18, 82);
		this.hat.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.hat.addBox(-9.0F, -8.0F, -9.0F, 18.0F, 0.0F, 18.0F, 0.0F, 0.0F, 0.0F);
		this.top3.addChild(this.top4);
		this.top2.addChild(this.top3);
		this.top1.addChild(this.top2);
		this.hat.addChild(this.top1);
	}
	
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
	
	public void setVisible(boolean b) {
		hat.showModel = b;
	}

	@Override
	public void setRotationAngles(E entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) {
		
	}

	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
			float red, float green, float blue, float alpha) {
		hat.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}

}
