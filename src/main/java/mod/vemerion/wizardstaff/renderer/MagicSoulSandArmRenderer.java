package mod.vemerion.wizardstaff.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.entity.MagicSoulSandArmEntity;
import mod.vemerion.wizardstaff.model.MagicSoulSandArmModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class MagicSoulSandArmRenderer extends EntityRenderer<MagicSoulSandArmEntity> {
	public static final ResourceLocation TEXTURES = new ResourceLocation(Main.MODID,
			"textures/entity/soul_sand_magic_arm.png");
	private final MagicSoulSandArmModel model = new MagicSoulSandArmModel();

	public MagicSoulSandArmRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn);
	}

	@Override
	public void render(MagicSoulSandArmEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int packedLightIn) {
		matrixStackIn.push();
		matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
		matrixStackIn.scale(0.25f, 0.25f, 0.25f);
		matrixStackIn.translate(0.0D, (double) -1.501F, 0.0D);
		matrixStackIn.translate(0, entityIn.getY(partialTicks), 0);
		

		model.setRotationAngles(entityIn, 0, 0, entityIn.ticksExisted + partialTicks, 0, 0);
		IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.model.getRenderType(getEntityTexture(entityIn)));
		model.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		matrixStackIn.pop();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}
	

	@Override
	protected boolean canRenderName(MagicSoulSandArmEntity entity) {
		return false;
	}

	/**
	 * Returns the location of an entity's texture.
	 */
	@Override
	public ResourceLocation getEntityTexture(MagicSoulSandArmEntity entity) {
		return TEXTURES;
	}
}
