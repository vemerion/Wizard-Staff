package mod.vemerion.wizardstaff.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.entity.GrapplingHookEntity;
import mod.vemerion.wizardstaff.model.GrapplingHookModel;
import mod.vemerion.wizardstaff.staff.WizardStaffItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;

public class GrapplingHookRenderer extends EntityRenderer<GrapplingHookEntity> {
	public static final ResourceLocation TEXTURES = new ResourceLocation(Main.MODID,
			"textures/entity/grappling_hook.png");
	private final GrapplingHookModel model = new GrapplingHookModel();

	public GrapplingHookRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn);
	}

	@Override
	public void render(GrapplingHookEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int packedLightIn) {
		model.setRotationAngles(entityIn, 0, 0, entityIn.ticksExisted + partialTicks,
				(float) Math.toRadians(180 - entityIn.getYaw(partialTicks)),
				(float) Math.toRadians(-entityIn.getPitch(partialTicks)));
		IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.model.getRenderType(getEntityTexture(entityIn)));
		model.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		matrixStackIn.push();

		// The rendering of the grappling line, derived from FishRenderer
		PlayerEntity shooter = entityIn.getCaster(entityIn.world);
		if (shooter != null) {
			float handOffset = shooter.getPrimaryHand() == HandSide.RIGHT ? 1 : -1;
			Vector3d shooterPos;
			if (!(shooter.getHeldItemMainhand().getItem() instanceof WizardStaffItem))
				handOffset *= -1;

			if ((this.renderManager.options == null || this.renderManager.options.getPointOfView().func_243192_a())
					&& shooter == Minecraft.getInstance().player) { // First person
				double fov = renderManager.options.fov / 100d;
				Vector3d fovOffset = new Vector3d(-0.1D * fov * handOffset, 0.03D * fov, 0.3D);
				fovOffset = fovOffset.rotatePitch((float) -Math.toRadians(shooter.getPitch(partialTicks)));
				fovOffset = fovOffset.rotateYaw((float) -Math.toRadians(shooter.getYaw(partialTicks)));
				shooterPos = shooter.getEyePosition(partialTicks).subtract(entityIn.getPositionVec()).add(fovOffset);
			} else { // Third person
				float renderYawOffset = MathHelper.lerp(partialTicks, shooter.prevRenderYawOffset,
						shooter.renderYawOffset) * ((float) Math.PI / 180F);
				float xOffset = MathHelper.sin(renderYawOffset);
				float yOffset = MathHelper.cos(renderYawOffset);
				Vector3d offset = new Vector3d(-xOffset * 0.8D, 0.2, yOffset * 0.8D);
				shooterPos = shooter.getEyePosition(partialTicks).subtract(entityIn.getPositionVec()).add(offset);
			}

			IVertexBuilder builder = bufferIn.getBuffer(RenderType.getLines());
			Matrix4f matrix = matrixStackIn.getLast().getMatrix();
			builder.pos(matrix, 0, 0.1f, 0).color(0, 0, 0, 255).endVertex();
			builder.pos(matrix, (float) shooterPos.x, (float) shooterPos.y, (float) shooterPos.z).color(0, 0, 0, 255)
					.endVertex();
		}

		matrixStackIn.pop();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	protected boolean canRenderName(GrapplingHookEntity entity) {
		return false;
	}

	/**
	 * Returns the location of an entity's texture.
	 */
	@Override
	public ResourceLocation getEntityTexture(GrapplingHookEntity entity) {
		return TEXTURES;
	}
}
