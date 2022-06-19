package mod.vemerion.wizardstaff.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.entity.GrapplingHookEntity;
import mod.vemerion.wizardstaff.init.ModLayerLocations;
import mod.vemerion.wizardstaff.model.GrapplingHookModel;
import mod.vemerion.wizardstaff.staff.WizardStaffItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class GrapplingHookRenderer extends EntityRenderer<GrapplingHookEntity> {
	public static final ResourceLocation TEXTURES = new ResourceLocation(Main.MODID,
			"textures/entity/grappling_hook.png");
	private final GrapplingHookModel model;

	public GrapplingHookRenderer(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn);
		model = new GrapplingHookModel(renderManagerIn.bakeLayer(ModLayerLocations.GRAPPLING_HOOK));
	}

	@Override
	public void render(GrapplingHookEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn,
			MultiBufferSource bufferIn, int packedLightIn) {
		model.setupAnim(entityIn, 0, 0, entityIn.tickCount + partialTicks,
				(float) Math.toRadians(180 - entityIn.getViewYRot(partialTicks)),
				(float) Math.toRadians(-entityIn.getViewXRot(partialTicks)));
		VertexConsumer ivertexbuilder = bufferIn.getBuffer(this.model.renderType(getTextureLocation(entityIn)));
		model.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
		matrixStackIn.pushPose();

		// The rendering of the grappling line, derived from FishingHookRenderer
		Player shooter = entityIn.getCaster(entityIn.level);
		if (shooter != null) {
			float handOffset = shooter.getMainArm() == HumanoidArm.RIGHT ? 1 : -1;
			Vec3 shooterPos;
			if (!(shooter.getMainHandItem().getItem() instanceof WizardStaffItem))
				handOffset *= -1;

			if ((this.entityRenderDispatcher.options == null
					|| this.entityRenderDispatcher.options.getCameraType().isFirstPerson())
					&& shooter == Minecraft.getInstance().player) { // First person
				double fov = entityRenderDispatcher.options.fov / 100d;
				Vec3 fovOffset = new Vec3(-0.1D * fov * handOffset, 0.03D * fov, 0.3D);
				fovOffset = fovOffset.xRot((float) -Math.toRadians(shooter.getViewXRot(partialTicks)));
				fovOffset = fovOffset.yRot((float) -Math.toRadians(shooter.getViewYRot(partialTicks)));
				shooterPos = shooter.getEyePosition(partialTicks).subtract(entityIn.position()).add(fovOffset);
			} else { // Third person
				float renderYawOffset = Mth.lerp(partialTicks, shooter.yBodyRotO, shooter.yBodyRot)
						* ((float) Math.PI / 180F);
				float xOffset = Mth.sin(renderYawOffset);
				float yOffset = Mth.cos(renderYawOffset);
				Vec3 offset = new Vec3(-xOffset * 0.8D, 0.2, yOffset * 0.8D);
				shooterPos = shooter.getEyePosition(partialTicks).subtract(entityIn.position()).add(offset);
			}

			VertexConsumer builder = bufferIn.getBuffer(RenderType.lines());
			var pose = matrixStackIn.last();
			builder.vertex(pose.pose(), 0, 0.1f, 0).color(0, 0, 0, 255).normal(pose.normal(), 0, 0, 0).endVertex();
			builder.vertex(pose.pose(), (float) shooterPos.x, (float) shooterPos.y, (float) shooterPos.z)
					.color(0, 0, 0, 255).normal(pose.normal(), 0, 0, 0).endVertex();
		}

		matrixStackIn.popPose();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	protected boolean shouldShowName(GrapplingHookEntity entity) {
		return false;
	}

	@Override
	public ResourceLocation getTextureLocation(GrapplingHookEntity entity) {
		return TEXTURES;
	}
}
