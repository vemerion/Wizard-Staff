package mod.vemerion.wizardstaff.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.entity.WizardHatEntity;
import mod.vemerion.wizardstaff.init.ModLayerLocations;
import mod.vemerion.wizardstaff.model.WizardHatModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class WizardHatRenderer extends EntityRenderer<WizardHatEntity> {
	private static final ResourceLocation TEXTURES = new ResourceLocation(Main.MODID,
			"textures/armor/wizard_armor.png");
	private static final ResourceLocation OVERLAY = new ResourceLocation(Main.MODID,
			"textures/armor/wizard_armor_overlay.png");

	private final WizardHatModel<WizardHatEntity> model;

	public WizardHatRenderer(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn);
		model = new WizardHatModel<>(renderManagerIn.bakeLayer(ModLayerLocations.WIZARD_HAT));
	}

	@Override
	public void render(WizardHatEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn,
			MultiBufferSource bufferIn, int packedLightIn) {
		matrixStackIn.pushPose();
		matrixStackIn.scale(-1, -1, 1);
		matrixStackIn.translate(0, 0.5, 0);
		matrixStackIn.mulPose(new Quaternion(0, entityIn.getHatRotation(partialTicks), 0, true));
		VertexConsumer ivertexbuilder = bufferIn.getBuffer(model.renderType(this.getTextureLocation(entityIn)));
		model.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 0.1f, 0.8f, 1);
		ivertexbuilder = bufferIn.getBuffer(model.renderType(OVERLAY));
		model.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
		matrixStackIn.popPose();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getTextureLocation(WizardHatEntity entity) {
		return TEXTURES;
	}
}