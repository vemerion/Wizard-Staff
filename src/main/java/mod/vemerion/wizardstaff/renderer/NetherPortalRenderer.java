package mod.vemerion.wizardstaff.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;

import mod.vemerion.wizardstaff.entity.NetherPortalEntity;
import mod.vemerion.wizardstaff.init.ModLayerLocations;
import mod.vemerion.wizardstaff.model.NetherPortalModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class NetherPortalRenderer extends EntityRenderer<NetherPortalEntity> {
	private final NetherPortalModel model;

	public NetherPortalRenderer(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn);
		model = new NetherPortalModel(renderManagerIn.bakeLayer(ModLayerLocations.NETHER_PORTAL));
	}

	@Override
	public void render(NetherPortalEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn,
			MultiBufferSource bufferIn, int packedLightIn) {
		matrixStackIn.pushPose();
		matrixStackIn.scale(1.0F, -1.0F, 1.0F);
		matrixStackIn.mulPose(new Quaternion(0, -entityYaw, 0, true));
		matrixStackIn.translate(0.0D, (double) -1.501F, 0.0D);

		VertexConsumer ivertexbuilder = bufferIn.getBuffer(this.model.renderType(getTextureLocation(entityIn)));
		model.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
		matrixStackIn.popPose();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	protected boolean shouldShowName(NetherPortalEntity entity) {
		return false;
	}

	protected int getBlockLight(NetherPortalEntity entityIn, float partialTicks) {
		return 15;
	}

	@Override
	public ResourceLocation getTextureLocation(NetherPortalEntity entity) {
		return entity.getTexture();
	}
}
