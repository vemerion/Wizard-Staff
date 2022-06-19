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

// Made with Blockbench 3.6.6

public class NetherWizardStaffModel extends AbstractWizardStaffModel {
	public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(Main.MODID,
			"textures/entity/nether_wizard_staff.png");

	private final ModelPart wizardStaff;

	public NetherWizardStaffModel(ModelPart parts) {
		super(RenderType::entityCutoutNoCull);
    	this.wizardStaff = parts.getChild("wizardStaff");
	}

	public static LayerDefinition createLayer() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition parts = mesh.getRoot();
		
        PartDefinition wizardStaff = parts.addOrReplaceChild("wizardStaff", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0, 0, 0));
        wizardStaff.addOrReplaceChild("part0", CubeListBuilder.create().texOffs(26, 0).addBox(3.0F, -81.0F, 0.0F, 9.0F, 20.0F, 0.0F, false), PartPose.ZERO);
        wizardStaff.addOrReplaceChild("part1", CubeListBuilder.create().texOffs(26, 0).addBox(-12.0F, -81.0F, 0.0F, 9.0F, 20.0F, 0.0F, true), PartPose.ZERO);
        wizardStaff.addOrReplaceChild("part2", CubeListBuilder.create().texOffs(26, 31).addBox(0.0F, -81.0F, 3.0F, 0.0F, 20.0F, 9.0F, false), PartPose.ZERO);
        wizardStaff.addOrReplaceChild("part3", CubeListBuilder.create().texOffs(26, 11).addBox(0.0F, -81.0F, -12.0F, 0.0F, 20.0F, 9.0F, true), PartPose.ZERO);
        
        PartDefinition handle = wizardStaff.addOrReplaceChild("handle", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0, 0, 0));
        handle.addOrReplaceChild("part4", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -64.0F, -2.0F, 4.0F, 16.0F, 4.0F, false), PartPose.ZERO);
        handle.addOrReplaceChild("part5", CubeListBuilder.create().texOffs(6, 20).addBox(1.0F, -48.0F, -1.0F, 1.0F, 16.0F, 2.0F, false), PartPose.ZERO);
        handle.addOrReplaceChild("part6", CubeListBuilder.create().texOffs(6, 20).addBox(-2.0F, -48.0F, -1.0F, 1.0F, 16.0F, 2.0F, false), PartPose.ZERO);
        handle.addOrReplaceChild("part7", CubeListBuilder.create().texOffs(0, 20).addBox(-1.0F, -48.0F, -2.0F, 2.0F, 16.0F, 1.0F, false), PartPose.ZERO);
        handle.addOrReplaceChild("part8", CubeListBuilder.create().texOffs(0, 20).addBox(-1.0F, -48.0F, 1.0F, 2.0F, 16.0F, 1.0F, false), PartPose.ZERO);
        handle.addOrReplaceChild("part9", CubeListBuilder.create().texOffs(0, 38).addBox(-1.0F, -32.0F, -1.0F, 2.0F, 16.0F, 2.0F, false), PartPose.ZERO);
        handle.addOrReplaceChild("part10", CubeListBuilder.create().texOffs(0, 56).addBox(-0.5F, -16.0F, -0.5F, 1.0F, 16.0F, 1.0F, false), PartPose.ZERO);
        handle.addOrReplaceChild("part11", CubeListBuilder.create().texOffs(16, 8).addBox(2.0F, -64.0F, -1.0F, 1.0F, 7.0F, 2.0F, false), PartPose.ZERO);
        handle.addOrReplaceChild("part12", CubeListBuilder.create().texOffs(22, 7).addBox(2.0F, -57.0F, -0.5F, 1.0F, 6.0F, 1.0F, false), PartPose.ZERO);
        handle.addOrReplaceChild("part13", CubeListBuilder.create().texOffs(16, 8).addBox(-3.0F, -64.0F, -1.0F, 1.0F, 7.0F, 2.0F, false), PartPose.ZERO);
        handle.addOrReplaceChild("part14", CubeListBuilder.create().texOffs(22, 7).addBox(-3.0F, -57.0F, -0.5F, 1.0F, 6.0F, 1.0F, false), PartPose.ZERO);
        handle.addOrReplaceChild("part15", CubeListBuilder.create().texOffs(16, 0).addBox(-1.0F, -64.0F, 2.0F, 2.0F, 7.0F, 1.0F, false), PartPose.ZERO);
        handle.addOrReplaceChild("part16", CubeListBuilder.create().texOffs(22, 0).addBox(-0.5F, -57.0F, -3.0F, 1.0F, 6.0F, 1.0F, false), PartPose.ZERO);
        handle.addOrReplaceChild("part17", CubeListBuilder.create().texOffs(16, 0).addBox(-1.0F, -64.0F, -3.0F, 2.0F, 7.0F, 1.0F, false), PartPose.ZERO);
        handle.addOrReplaceChild("part18", CubeListBuilder.create().texOffs(22, 0).addBox(-0.5F, -57.0F, 2.0F, 1.0F, 6.0F, 1.0F, false), PartPose.ZERO);
		
		return LayerDefinition.create(mesh, 128, 128);
	}

	@Override
	public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
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
		return 0.7f;
	}
}