package mod.vemerion.wizardstaff.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.vemerion.wizardstaff.Main;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;

// Made with Blockbench 3.6.5



public class WizardStaffModel extends AbstractWizardStaffModel {
	public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(Main.MODID,
			"textures/entity/wizard_staff.png");

	
	private final ModelRenderer wizardStaff;
	private final ModelRenderer handle;
	private final ModelRenderer claw;

	public WizardStaffModel() {
		super(RenderType::getEntityTranslucent);
		textureWidth = 128;
		textureHeight = 128;

		wizardStaff = new ModelRenderer(this);
		wizardStaff.setRotationPoint(0.0F, 24.0F, 0.0F);
		

		handle = new ModelRenderer(this);
		handle.setRotationPoint(0.0F, 0.0F, 0.0F);
		wizardStaff.addChild(handle);
		handle.setTextureOffset(0, 0).addBox(-2.0F, -64.0F, -2.0F, 4.0F, 16.0F, 4.0F, 0.0F, false);
		handle.setTextureOffset(6, 20).addBox(1.0F, -48.0F, -1.0F, 1.0F, 16.0F, 2.0F, 0.0F, false);
		handle.setTextureOffset(6, 20).addBox(-2.0F, -48.0F, -1.0F, 1.0F, 16.0F, 2.0F, 0.0F, false);
		handle.setTextureOffset(0, 20).addBox(-1.0F, -48.0F, -2.0F, 2.0F, 16.0F, 1.0F, 0.0F, false);
		handle.setTextureOffset(0, 20).addBox(-1.0F, -48.0F, 1.0F, 2.0F, 16.0F, 1.0F, 0.0F, false);
		handle.setTextureOffset(0, 38).addBox(-1.0F, -32.0F, -1.0F, 2.0F, 16.0F, 2.0F, 0.0F, false);
		handle.setTextureOffset(0, 56).addBox(-0.5F, -16.0F, -0.5F, 1.0F, 16.0F, 1.0F, 0.0F, false);
		handle.setTextureOffset(16, 7).addBox(2.0F, -63.0F, -1.0F, 1.0F, 6.0F, 2.0F, 0.0F, false);
		handle.setTextureOffset(22, 7).addBox(2.0F, -57.0F, -0.5F, 1.0F, 6.0F, 1.0F, 0.0F, false);
		handle.setTextureOffset(16, 7).addBox(-3.0F, -63.0F, -1.0F, 1.0F, 6.0F, 2.0F, 0.0F, false);
		handle.setTextureOffset(22, 7).addBox(-3.0F, -57.0F, -0.5F, 1.0F, 6.0F, 1.0F, 0.0F, false);
		handle.setTextureOffset(16, 0).addBox(-1.0F, -63.0F, 2.0F, 2.0F, 6.0F, 1.0F, 0.0F, false);
		handle.setTextureOffset(22, 0).addBox(-0.5F, -57.0F, -3.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);
		handle.setTextureOffset(16, 0).addBox(-1.0F, -63.0F, -3.0F, 2.0F, 6.0F, 1.0F, 0.0F, false);
		handle.setTextureOffset(22, 0).addBox(-0.5F, -57.0F, 2.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);

		claw = new ModelRenderer(this);
		claw.setRotationPoint(0.0F, 0.0F, 0.0F);
		wizardStaff.addChild(claw);
		claw.setTextureOffset(0, 2).addBox(-1.0F, -70.0F, -8.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(0.0F, -70.0F, -8.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(0.0F, -69.0F, -8.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 0).addBox(-1.0F, -69.0F, -8.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(0.0F, -68.0F, -8.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 0).addBox(-1.0F, -68.0F, -8.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(0.0F, -67.0F, -7.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 0).addBox(-1.0F, -67.0F, -7.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(0.0F, -66.0F, -6.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(-1.0F, -66.0F, -6.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 0).addBox(0.0F, -65.0F, -5.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(-1.0F, -65.0F, -5.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(0.0F, -64.0F, -3.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 0).addBox(-1.0F, -64.0F, -3.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(0.0F, -65.0F, -4.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(-8.0F, -70.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 0).addBox(-8.0F, -70.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(-8.0F, -69.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(-8.0F, -69.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(-8.0F, -68.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(-8.0F, -68.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 0).addBox(-7.0F, -67.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(-7.0F, -67.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(-6.0F, -66.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 0).addBox(-6.0F, -66.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 0).addBox(-5.0F, -65.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(-5.0F, -65.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(-4.0F, -65.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(-4.0F, -65.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 0).addBox(-3.0F, -64.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 0).addBox(-3.0F, -64.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(-1.0F, -70.0F, 7.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(0.0F, -70.0F, 7.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(-1.0F, -69.0F, 7.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 0).addBox(0.0F, -69.0F, 7.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(-1.0F, -68.0F, 7.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 0).addBox(0.0F, -68.0F, 7.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(-1.0F, -67.0F, 6.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(0.0F, -67.0F, 6.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 0).addBox(-1.0F, -66.0F, 5.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(0.0F, -66.0F, 5.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(-1.0F, -65.0F, 4.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(0.0F, -65.0F, 4.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(-1.0F, -65.0F, 3.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 0).addBox(0.0F, -65.0F, 3.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(-1.0F, -64.0F, 2.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(0.0F, -64.0F, 2.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(7.0F, -70.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 0).addBox(7.0F, -70.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(7.0F, -69.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(7.0F, -69.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(7.0F, -68.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(7.0F, -68.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 0).addBox(6.0F, -67.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(6.0F, -67.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(5.0F, -66.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(5.0F, -66.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(4.0F, -65.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(4.0F, -65.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 0).addBox(3.0F, -65.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(2.0F, -64.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(3.0F, -65.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(2.0F, -64.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		claw.setTextureOffset(0, 2).addBox(-1.0F, -65.0F, -4.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);

	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		wizardStaff.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
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