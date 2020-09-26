package mod.vemerion.wizardstaff.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.entity.NetherPortalEntity;
import mod.vemerion.wizardstaff.model.NetherPortalModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.util.ResourceLocation;

public class NetherPortalRenderer extends EntityRenderer<NetherPortalEntity> {
	public static final ResourceLocation TEXTURES = new ResourceLocation(Main.MODID,
			"textures/entity/nether_portal.png");
	private final NetherPortalModel model = new NetherPortalModel();

	public NetherPortalRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn);
	}

	@Override
	public void render(NetherPortalEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int packedLightIn) {
		matrixStackIn.push();
		matrixStackIn.scale(1.0F, -1.0F, 1.0F);
		matrixStackIn.rotate(new Quaternion(0, -entityYaw, 0, true));
		matrixStackIn.translate(0.0D, (double) -1.501F, 0.0D);

		IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.model.getRenderType(getEntityTexture(entityIn)));
		model.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		matrixStackIn.pop();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}
	

	@Override
	protected boolean canRenderName(NetherPortalEntity entity) {
		return false;
	}

	protected int getBlockLight(ShulkerBulletEntity entityIn, float partialTicks) {
		return 15;
	}

	/**
	 * Returns the location of an entity's texture.
	 */
	@Override
	public ResourceLocation getEntityTexture(NetherPortalEntity entity) {
		return TEXTURES;
	}
}
