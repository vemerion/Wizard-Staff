package mod.vemerion.wizardstaff.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.vemerion.wizardstaff.entity.MagicWitherSkullEntity;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class MagicWitherSkullRenderer extends EntityRenderer<MagicWitherSkullEntity> {
	private static final ResourceLocation WITHER_TEXTURES = new ResourceLocation("minecraft",
			"textures/entity/wither/wither.png");
	private final SkullModel head;

	public MagicWitherSkullRenderer(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn);
		head = new SkullModel(renderManagerIn.bakeLayer(ModelLayers.WITHER_SKULL));
	}

	@Override
	protected int getBlockLightLevel(MagicWitherSkullEntity entityIn, BlockPos pos) {
		return 15;
	}

	@Override
	public void render(MagicWitherSkullEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn,
			MultiBufferSource bufferIn, int packedLightIn) {
		matrixStackIn.pushPose();
		matrixStackIn.scale(-1, -1, 1);
		VertexConsumer ivertexbuilder = bufferIn.getBuffer(head.renderType(this.getTextureLocation(entityIn)));
		head.setupAnim(0, -entityIn.getYRot() - 180, -entityIn.getXRot());
		head.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
		matrixStackIn.popPose();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getTextureLocation(MagicWitherSkullEntity entity) {
		return WITHER_TEXTURES;
	}
}