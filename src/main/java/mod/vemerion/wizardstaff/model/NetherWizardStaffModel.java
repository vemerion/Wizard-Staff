package mod.vemerion.wizardstaff.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.vemerion.wizardstaff.Main;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;

// Made with Blockbench 3.6.6

public class NetherWizardStaffModel extends AbstractWizardStaffModel {
	public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(Main.MODID,
			"textures/entity/nether_wizard_staff.png");

	
	private final ModelRenderer wizardStaff;
	private final ModelRenderer handle;

	public NetherWizardStaffModel() {
		super(RenderType::getEntityCutoutNoCull);
		textureWidth = 128;
		textureHeight = 128;

		wizardStaff = new ModelRenderer(this);
		wizardStaff.setRotationPoint(0.0F, 24.0F, 0.0F);
		wizardStaff.setTextureOffset(26, 0).addBox(3.0F, -81.0F, 0.0F, 9.0F, 20.0F, 0.0F, 0.0F, false);
		wizardStaff.setTextureOffset(26, 0).addBox(-12.0F, -81.0F, 0.0F, 9.0F, 20.0F, 0.0F, 0.0F, true);
		wizardStaff.setTextureOffset(26, 31).addBox(0.0F, -81.0F, 3.0F, 0.0F, 20.0F, 9.0F, 0.0F, false);
		wizardStaff.setTextureOffset(26, 11).addBox(0.0F, -81.0F, -12.0F, 0.0F, 20.0F, 9.0F, 0.0F, true);

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
		handle.setTextureOffset(16, 8).addBox(2.0F, -64.0F, -1.0F, 1.0F, 7.0F, 2.0F, 0.0F, false);
		handle.setTextureOffset(22, 7).addBox(2.0F, -57.0F, -0.5F, 1.0F, 6.0F, 1.0F, 0.0F, false);
		handle.setTextureOffset(16, 8).addBox(-3.0F, -64.0F, -1.0F, 1.0F, 7.0F, 2.0F, 0.0F, false);
		handle.setTextureOffset(22, 7).addBox(-3.0F, -57.0F, -0.5F, 1.0F, 6.0F, 1.0F, 0.0F, false);
		handle.setTextureOffset(16, 0).addBox(-1.0F, -64.0F, 2.0F, 2.0F, 7.0F, 1.0F, 0.0F, false);
		handle.setTextureOffset(22, 0).addBox(-0.5F, -57.0F, -3.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);
		handle.setTextureOffset(16, 0).addBox(-1.0F, -64.0F, -3.0F, 2.0F, 7.0F, 1.0F, 0.0F, false);
		handle.setTextureOffset(22, 0).addBox(-0.5F, -57.0F, 2.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);     
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
		return 0.7f;
	}
}