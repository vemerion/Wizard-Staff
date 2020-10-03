package mod.vemerion.wizardstaff.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.vemerion.wizardstaff.entity.MagicWitherSkullEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.GenericHeadModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class MagicWitherSkullRenderer extends EntityRenderer<MagicWitherSkullEntity> {
	private static final ResourceLocation WITHER_TEXTURES = new ResourceLocation("minecraft",
			"textures/entity/wither/wither.png");
	private final GenericHeadModel head = new GenericHeadModel();

	public MagicWitherSkullRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn);
	}

	@Override
	protected int getBlockLight(MagicWitherSkullEntity entityIn, BlockPos pos) {
		return 15;
	}

	@Override
	public void render(MagicWitherSkullEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int packedLightIn) {
		matrixStackIn.push();
		matrixStackIn.scale(-1, -1, 1);
		IVertexBuilder ivertexbuilder = bufferIn.getBuffer(head.getRenderType(this.getEntityTexture(entityIn)));
		head.func_225603_a_(0, -entityIn.rotationYaw - 180, -entityIn.rotationPitch);
		head.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F,
				1.0F);
		matrixStackIn.pop();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getEntityTexture(MagicWitherSkullEntity entity) {
		return WITHER_TEXTURES;
	}
}