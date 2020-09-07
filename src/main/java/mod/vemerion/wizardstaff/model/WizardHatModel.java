package mod.vemerion.wizardstaff.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

/**
 * Created using Tabula 8.0.0
 */
public class WizardHatModel<T extends LivingEntity> extends BipedModel<T> {
    public ModelRenderer hat;
    public ModelRenderer top1;
    public ModelRenderer top2;
    public ModelRenderer top3;
    public ModelRenderer top4;   

    public WizardHatModel() {
    	super(0, 0, 64, 64);
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.bipedHead = new ModelRenderer(this, 0, 36);
        this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.top3 = new ModelRenderer(this, 28, 10);
        this.top3.setRotationPoint(0.0F, -4.0F, 0.0F);
        this.top3.addBox(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(top3, 0.17453292519943295F, 0.0F, 0.0F);
        this.hat = new ModelRenderer(this, -18, 18);
        this.hat.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.hat.addBox(-9.0F, -8.0F, -9.0F, 18.0F, 0.0F, 18.0F, 0.0F, 0.0F, 0.0F);
        this.top1 = new ModelRenderer(this, 0, 0);
        this.top1.setRotationPoint(0.0F, -8.0F, 0.0F);
        this.top1.addBox(-4.0F, -4.0F, -4.0F, 8.0F, 4.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.top2 = new ModelRenderer(this, 32, 0);
        this.top2.setRotationPoint(0.0F, -4.0F, 0.0F);
        this.top2.addBox(-3.0F, -4.0F, -3.0F, 6.0F, 4.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(top2, 0.17453292519943295F, 0.0F, 0.0F);
        this.top4 = new ModelRenderer(this, 0, 0);
        this.top4.setRotationPoint(0.0F, -4.0F, 0.0F);
        this.top4.addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(top4, 0.17453292519943295F, 0.0F, 0.0F);
        this.top2.addChild(this.top3);
        this.bipedHead.addChild(this.hat);
        this.hat.addChild(this.top1);
        this.top1.addChild(this.top2);
        this.top3.addChild(this.top4);   
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.bipedHead).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
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
