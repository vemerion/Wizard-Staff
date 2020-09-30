package mod.vemerion.wizardstaff.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.vemerion.wizardstaff.entity.MagicSoulSandArmEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

/**
 * Created using Tabula 8.0.0
 */
public class MagicSoulSandArmModel extends EntityModel<MagicSoulSandArmEntity> {
    public ModelRenderer arm1;
    public ModelRenderer palm;
    public ModelRenderer arm2;
    public ModelRenderer thumb1;
    public ModelRenderer index1;
    public ModelRenderer middle1;
    public ModelRenderer little1;
    public ModelRenderer thumb2;
    public ModelRenderer index2;
    public ModelRenderer index3;
    public ModelRenderer middle2;
    public ModelRenderer middle3;
    public ModelRenderer little2;
    public ModelRenderer little3;

    public MagicSoulSandArmModel() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.palm = new ModelRenderer(this, 20, 0);
        this.palm.setRotationPoint(0.0F, -32.0F, 0.0F);
        this.palm.addBox(-6.0F, -13.0F, -2.5F, 12.0F, 13.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(palm, 0.0F, 0.0F, 0.06981317007977318F);
        this.arm2 = new ModelRenderer(this, 0, 0);
        this.arm2.setRotationPoint(-1.0F, 0.0F, 0.0F);
        this.arm2.addBox(-4.0F, -32.0F, -1.5F, 3.0F, 32.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(arm2, 0.0F, 0.0F, 0.06981317007977318F);
        this.little1 = new ModelRenderer(this, 20, 18);
        this.little1.setRotationPoint(-4.0F, -13.0F, 0.0F);
        this.little1.addBox(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(little1, 0.24434609527920614F, 0.0F, -0.13962634015954636F);
        this.middle3 = new ModelRenderer(this, 15, 0);
        this.middle3.setRotationPoint(0.0F, -3.0F, 0.0F);
        this.middle3.addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(middle3, 0.19547687289441354F, 0.0F, 0.0F);
        this.thumb2 = new ModelRenderer(this, 36, 18);
        this.thumb2.setRotationPoint(0.0F, -6.0F, 0.0F);
        this.thumb2.addBox(-1.5F, -4.0F, -1.5F, 3.0F, 4.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(thumb2, 0.3127630032889644F, 0.0F, 0.0F);
        this.little2 = new ModelRenderer(this, 0, 37);
        this.little2.setRotationPoint(0.0F, -6.0F, 0.0F);
        this.little2.addBox(-1.5F, -3.0F, -1.5F, 3.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(little2, 0.19547687289441354F, 0.0F, 0.0F);
        this.middle2 = new ModelRenderer(this, 0, 37);
        this.middle2.setRotationPoint(0.0F, -6.0F, 0.0F);
        this.middle2.addBox(-1.5F, -3.0F, -1.5F, 3.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(middle2, 0.19547687289441354F, 0.0F, 0.0F);
        this.middle1 = new ModelRenderer(this, 20, 18);
        this.middle1.setRotationPoint(0.2F, -13.0F, 0.0F);
        this.middle1.addBox(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(middle1, 0.24434609527920614F, 0.0F, 0.0F);
        this.little3 = new ModelRenderer(this, 15, 0);
        this.little3.setRotationPoint(0.0F, -3.0F, 0.0F);
        this.little3.addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(little3, 0.19547687289441354F, 0.0F, 0.0F);
        this.index3 = new ModelRenderer(this, 15, 0);
        this.index3.setRotationPoint(0.0F, -3.0F, 0.0F);
        this.index3.addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(index3, 0.19547687289441354F, 0.0F, 0.0F);
        this.index1 = new ModelRenderer(this, 20, 18);
        this.index1.setRotationPoint(4.4F, -13.0F, 0.0F);
        this.index1.addBox(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(index1, 0.7916813593572721F, 0.0F, 0.1563815016444822F);
        this.index2 = new ModelRenderer(this, 0, 37);
        this.index2.setRotationPoint(0.0F, -6.0F, 0.0F);
        this.index2.addBox(-1.5F, -3.0F, -1.5F, 3.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(index2, 0.19547687289441354F, 0.0F, 0.0F);
        this.arm1 = new ModelRenderer(this, 0, 0);
        this.arm1.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.arm1.addBox(1.0F, -32.0F, -1.5F, 3.0F, 32.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(arm1, 0.0F, 0.0F, -0.03490658503988659F);
        this.thumb1 = new ModelRenderer(this, 20, 18);
        this.thumb1.setRotationPoint(6.0F, -6.0F, 0.0F);
        this.thumb1.addBox(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(thumb1, 0.392350007858842F, 0.0F, 0.7428121536172364F);
        this.arm1.addChild(this.palm);
        this.arm1.addChild(this.arm2);
        this.palm.addChild(this.little1);
        this.middle2.addChild(this.middle3);
        this.thumb1.addChild(this.thumb2);
        this.little1.addChild(this.little2);
        this.middle1.addChild(this.middle2);
        this.palm.addChild(this.middle1);
        this.little2.addChild(this.little3);
        this.index2.addChild(this.index3);
        this.palm.addChild(this.index1);
        this.index1.addChild(this.index2);
        this.palm.addChild(this.thumb1);        
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.arm1).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setRotationAngles(MagicSoulSandArmEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    	index1.rotateAngleX = (float) Math.abs(MathHelper.sin(((ageInTicks + 8) / 120) * (float) Math.PI * 2) * Math.toRadians(60));
    	index2.rotateAngleX = (float) Math.abs(MathHelper.sin(((ageInTicks + 8) / 120) * (float) Math.PI * 2) * Math.toRadians(30));
    	index3.rotateAngleX = (float) Math.abs(MathHelper.sin(((ageInTicks + 8) / 120) * (float) Math.PI * 2) * Math.toRadians(15));
    	
    	middle1.rotateAngleX = (float) Math.abs(MathHelper.sin(((ageInTicks + 2) / 120) * (float) Math.PI * 2) * Math.toRadians(60));
    	middle2.rotateAngleX = (float) Math.abs(MathHelper.sin(((ageInTicks + 2) / 120) * (float) Math.PI * 2) * Math.toRadians(30));
    	middle3.rotateAngleX = (float) Math.abs(MathHelper.sin(((ageInTicks + 2) / 120) * (float) Math.PI * 2) * Math.toRadians(15));
    	
    	little1.rotateAngleX = (float) Math.abs(MathHelper.sin(((ageInTicks - 6) / 120) * (float) Math.PI * 2) * Math.toRadians(60));
    	little2.rotateAngleX = (float) Math.abs(MathHelper.sin(((ageInTicks - 6) / 120) * (float) Math.PI * 2) * Math.toRadians(30));
    	little3.rotateAngleX = (float) Math.abs(MathHelper.sin(((ageInTicks - 6) / 120) * (float) Math.PI * 2) * Math.toRadians(15));
    	
    	thumb1.rotateAngleX = (float) Math.abs(MathHelper.sin(((ageInTicks + 4) / 120) * (float) Math.PI * 2) * Math.toRadians(60));
    	thumb2.rotateAngleX = (float) Math.abs(MathHelper.sin(((ageInTicks + 4) / 120) * (float) Math.PI * 2) * Math.toRadians(30));
    	
    	palm.rotateAngleX = MathHelper.sin(((ageInTicks + 0) / 120) * (float) Math.PI * 2) * (float) Math.toRadians(15);
    	
    	arm1.rotateAngleY = entityIn.getRotation();
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
