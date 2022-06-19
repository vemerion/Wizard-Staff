package mod.vemerion.wizardstaff.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.vemerion.wizardstaff.Main;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

// Made with Blockbench 3.6.5



public class WizardStaffModel extends AbstractWizardStaffModel {
	public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(Main.MODID,
			"textures/entity/wizard_staff.png");
	
	private final ModelPart wizardStaff;

	public WizardStaffModel(ModelPart parts) {
		super(RenderType::entityTranslucent);
    	this.wizardStaff = parts.getChild("wizardStaff");
	}
	
	public static LayerDefinition createLayer() {
	    MeshDefinition mesh = new MeshDefinition();
	    PartDefinition parts = mesh.getRoot();
	    
        PartDefinition wizardStaff = parts.addOrReplaceChild("wizardStaff", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0, 0, 0));
        
        PartDefinition handle = wizardStaff.addOrReplaceChild("handle", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0, 0, 0));
        handle.addOrReplaceChild("part0", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -64.0F, -2.0F, 4.0F, 16.0F, 4.0F, false), PartPose.ZERO);
        handle.addOrReplaceChild("part1", CubeListBuilder.create().texOffs(6, 20).addBox(1.0F, -48.0F, -1.0F, 1.0F, 16.0F, 2.0F, false), PartPose.ZERO);
        handle.addOrReplaceChild("part2", CubeListBuilder.create().texOffs(6, 20).addBox(-2.0F, -48.0F, -1.0F, 1.0F, 16.0F, 2.0F, false), PartPose.ZERO);
        handle.addOrReplaceChild("part3", CubeListBuilder.create().texOffs(0, 20).addBox(-1.0F, -48.0F, -2.0F, 2.0F, 16.0F, 1.0F, false), PartPose.ZERO);
        handle.addOrReplaceChild("part4", CubeListBuilder.create().texOffs(0, 20).addBox(-1.0F, -48.0F, 1.0F, 2.0F, 16.0F, 1.0F, false), PartPose.ZERO);
        handle.addOrReplaceChild("part5", CubeListBuilder.create().texOffs(0, 38).addBox(-1.0F, -32.0F, -1.0F, 2.0F, 16.0F, 2.0F, false), PartPose.ZERO);
        handle.addOrReplaceChild("part6", CubeListBuilder.create().texOffs(0, 56).addBox(-0.5F, -16.0F, -0.5F, 1.0F, 16.0F, 1.0F, false), PartPose.ZERO);
        handle.addOrReplaceChild("part7", CubeListBuilder.create().texOffs(16, 7).addBox(2.0F, -63.0F, -1.0F, 1.0F, 6.0F, 2.0F, false), PartPose.ZERO);
        handle.addOrReplaceChild("part8", CubeListBuilder.create().texOffs(22, 7).addBox(2.0F, -57.0F, -0.5F, 1.0F, 6.0F, 1.0F, false), PartPose.ZERO);
        handle.addOrReplaceChild("part9", CubeListBuilder.create().texOffs(16, 7).addBox(-3.0F, -63.0F, -1.0F, 1.0F, 6.0F, 2.0F, false), PartPose.ZERO);
        handle.addOrReplaceChild("part10", CubeListBuilder.create().texOffs(22, 7).addBox(-3.0F, -57.0F, -0.5F, 1.0F, 6.0F, 1.0F, false), PartPose.ZERO);
        handle.addOrReplaceChild("part11", CubeListBuilder.create().texOffs(16, 0).addBox(-1.0F, -63.0F, 2.0F, 2.0F, 6.0F, 1.0F, false), PartPose.ZERO);
        handle.addOrReplaceChild("part12", CubeListBuilder.create().texOffs(22, 0).addBox(-0.5F, -57.0F, -3.0F, 1.0F, 6.0F, 1.0F, false), PartPose.ZERO);
        handle.addOrReplaceChild("part13", CubeListBuilder.create().texOffs(16, 0).addBox(-1.0F, -63.0F, -3.0F, 2.0F, 6.0F, 1.0F, false), PartPose.ZERO);
        handle.addOrReplaceChild("part14", CubeListBuilder.create().texOffs(22, 0).addBox(-0.5F, -57.0F, 2.0F, 1.0F, 6.0F, 1.0F, false), PartPose.ZERO);
        
        
        PartDefinition claw = wizardStaff.addOrReplaceChild("claw", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0, 0, 0));
        claw.addOrReplaceChild("part15", CubeListBuilder.create().texOffs(0, 2).addBox(-1.0F, -70.0F, -8.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part16", CubeListBuilder.create().texOffs(0, 2).addBox(0.0F, -70.0F, -8.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part17", CubeListBuilder.create().texOffs(0, 2).addBox(0.0F, -69.0F, -8.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part18", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -69.0F, -8.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part19", CubeListBuilder.create().texOffs(0, 2).addBox(0.0F, -68.0F, -8.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part20", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -68.0F, -8.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part21", CubeListBuilder.create().texOffs(0, 2).addBox(0.0F, -67.0F, -7.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part22", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -67.0F, -7.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part23", CubeListBuilder.create().texOffs(0, 2).addBox(0.0F, -66.0F, -6.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part24", CubeListBuilder.create().texOffs(0, 2).addBox(-1.0F, -66.0F, -6.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part25", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -65.0F, -5.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part26", CubeListBuilder.create().texOffs(0, 2).addBox(-1.0F, -65.0F, -5.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part27", CubeListBuilder.create().texOffs(0, 2).addBox(0.0F, -64.0F, -3.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part28", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -64.0F, -3.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part29", CubeListBuilder.create().texOffs(0, 2).addBox(0.0F, -65.0F, -4.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part30", CubeListBuilder.create().texOffs(0, 2).addBox(-8.0F, -70.0F, -1.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part31", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -70.0F, 0.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part32", CubeListBuilder.create().texOffs(0, 2).addBox(-8.0F, -69.0F, -1.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part33", CubeListBuilder.create().texOffs(0, 2).addBox(-8.0F, -69.0F, 0.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part34", CubeListBuilder.create().texOffs(0, 2).addBox(-8.0F, -68.0F, -1.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part35", CubeListBuilder.create().texOffs(0, 2).addBox(-8.0F, -68.0F, 0.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part36", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -67.0F, -1.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part37", CubeListBuilder.create().texOffs(0, 2).addBox(-7.0F, -67.0F, 0.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part38", CubeListBuilder.create().texOffs(0, 2).addBox(-6.0F, -66.0F, -1.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part39", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -66.0F, 0.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part40", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -65.0F, -1.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part41", CubeListBuilder.create().texOffs(0, 2).addBox(-5.0F, -65.0F, 0.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part42", CubeListBuilder.create().texOffs(0, 2).addBox(-4.0F, -65.0F, -1.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part43", CubeListBuilder.create().texOffs(0, 2).addBox(-4.0F, -65.0F, 0.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part44", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -64.0F, -1.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part45", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -64.0F, 0.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part46", CubeListBuilder.create().texOffs(0, 2).addBox(-1.0F, -70.0F, 7.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part47", CubeListBuilder.create().texOffs(0, 2).addBox(0.0F, -70.0F, 7.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part48", CubeListBuilder.create().texOffs(0, 2).addBox(-1.0F, -69.0F, 7.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part49", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -69.0F, 7.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part50", CubeListBuilder.create().texOffs(0, 2).addBox(-1.0F, -68.0F, 7.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part51", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -68.0F, 7.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part52", CubeListBuilder.create().texOffs(0, 2).addBox(-1.0F, -67.0F, 6.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part53", CubeListBuilder.create().texOffs(0, 2).addBox(0.0F, -67.0F, 6.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part54", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -66.0F, 5.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part55", CubeListBuilder.create().texOffs(0, 2).addBox(0.0F, -66.0F, 5.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part56", CubeListBuilder.create().texOffs(0, 2).addBox(-1.0F, -65.0F, 4.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part57", CubeListBuilder.create().texOffs(0, 2).addBox(0.0F, -65.0F, 4.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part58", CubeListBuilder.create().texOffs(0, 2).addBox(-1.0F, -65.0F, 3.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part59", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -65.0F, 3.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part60", CubeListBuilder.create().texOffs(0, 2).addBox(-1.0F, -64.0F, 2.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part61", CubeListBuilder.create().texOffs(0, 2).addBox(0.0F, -64.0F, 2.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part62", CubeListBuilder.create().texOffs(0, 2).addBox(7.0F, -70.0F, 0.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part63", CubeListBuilder.create().texOffs(0, 0).addBox(7.0F, -70.0F, -1.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part64", CubeListBuilder.create().texOffs(0, 2).addBox(7.0F, -69.0F, 0.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part65", CubeListBuilder.create().texOffs(0, 2).addBox(7.0F, -69.0F, -1.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part66", CubeListBuilder.create().texOffs(0, 2).addBox(7.0F, -68.0F, 0.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part67", CubeListBuilder.create().texOffs(0, 2).addBox(7.0F, -68.0F, -1.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part68", CubeListBuilder.create().texOffs(0, 0).addBox(6.0F, -67.0F, -1.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part69", CubeListBuilder.create().texOffs(0, 2).addBox(6.0F, -67.0F, 0.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part70", CubeListBuilder.create().texOffs(0, 2).addBox(5.0F, -66.0F, 0.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part71", CubeListBuilder.create().texOffs(0, 2).addBox(5.0F, -66.0F, -1.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part72", CubeListBuilder.create().texOffs(0, 2).addBox(4.0F, -65.0F, 0.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part73", CubeListBuilder.create().texOffs(0, 2).addBox(4.0F, -65.0F, -1.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part74", CubeListBuilder.create().texOffs(0, 0).addBox(3.0F, -65.0F, 0.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part75", CubeListBuilder.create().texOffs(0, 2).addBox(2.0F, -64.0F, 0.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part76", CubeListBuilder.create().texOffs(0, 2).addBox(3.0F, -65.0F, -1.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part77", CubeListBuilder.create().texOffs(0, 2).addBox(2.0F, -64.0F, -1.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
        claw.addOrReplaceChild("part78", CubeListBuilder.create().texOffs(0, 2).addBox(-1.0F, -65.0F, -4.0F, 1.0F, 1.0F, 1.0F, true), PartPose.ZERO);
	    
	    return LayerDefinition.create(mesh, 128, 128);
	}

	@Override
	public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		wizardStaff.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}

	@Override
	public ResourceLocation getTexture() {
		return TEXTURE_LOCATION;
	}

	@Override
	public float getMagicScale() {
		return 0.9f;
	}
}