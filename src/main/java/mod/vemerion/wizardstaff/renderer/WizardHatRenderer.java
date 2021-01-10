package mod.vemerion.wizardstaff.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.entity.WizardHatEntity;
import mod.vemerion.wizardstaff.model.WizardHatModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;

public class WizardHatRenderer extends EntityRenderer<WizardHatEntity> {
	private static final ResourceLocation TEXTURES = new ResourceLocation(Main.MODID,
			"textures/armor/wizard_armor.png");
	private static final ResourceLocation OVERLAY = new ResourceLocation(Main.MODID,
			"textures/armor/wizard_armor_overlay.png");
	
	private final WizardHatModel<WizardHatEntity> model = new WizardHatModel<>(64, 128);

	public WizardHatRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn);
	}

	@Override
	public void render(WizardHatEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int packedLightIn) {
		matrixStackIn.push();
		matrixStackIn.scale(-1, -1, 1);
		matrixStackIn.translate(0, 0.5, 0);
		matrixStackIn.rotate(new Quaternion(0, entityIn.getHatRotation(partialTicks), 0, true));
		IVertexBuilder ivertexbuilder = bufferIn.getBuffer(model.getRenderType(this.getEntityTexture(entityIn)));
		model.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 0.1F, 0.8F,
				1.0F);
		ivertexbuilder = bufferIn.getBuffer(model.getRenderType(OVERLAY));
		model.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F,
				1.0F);
		matrixStackIn.pop();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getEntityTexture(WizardHatEntity entity) {
		return TEXTURES;
	}
}