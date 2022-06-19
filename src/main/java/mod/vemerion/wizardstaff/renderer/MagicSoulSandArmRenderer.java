package mod.vemerion.wizardstaff.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.entity.MagicSoulSandArmEntity;
import mod.vemerion.wizardstaff.init.ModLayerLocations;
import mod.vemerion.wizardstaff.model.MagicSoulSandArmModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class MagicSoulSandArmRenderer extends EntityRenderer<MagicSoulSandArmEntity> {
	public static final ResourceLocation TEXTURES = new ResourceLocation(Main.MODID,
			"textures/entity/soul_sand_magic_arm.png");
	private final MagicSoulSandArmModel model;

	public MagicSoulSandArmRenderer(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn);
		model = new MagicSoulSandArmModel(renderManagerIn.bakeLayer(ModLayerLocations.MAGIC_SOUL_SAND_ARM));
	}

	@Override
	public void render(MagicSoulSandArmEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn,
			MultiBufferSource bufferIn, int packedLightIn) {
		matrixStackIn.pushPose();
		matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
		matrixStackIn.scale(0.25f, 0.25f, 0.25f);
		matrixStackIn.translate(0.0D, (double) -1.501F, 0.0D);
		matrixStackIn.translate(0, entityIn.getY(partialTicks), 0);

		model.setupAnim(entityIn, 0, 0, entityIn.tickCount + partialTicks, 0, 0);
		VertexConsumer ivertexbuilder = bufferIn.getBuffer(this.model.renderType(getTextureLocation(entityIn)));
		model.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
		matrixStackIn.popPose();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	protected boolean shouldShowName(MagicSoulSandArmEntity entity) {
		return false;
	}

	@Override
	public ResourceLocation getTextureLocation(MagicSoulSandArmEntity entity) {
		return TEXTURES;
	}
}
