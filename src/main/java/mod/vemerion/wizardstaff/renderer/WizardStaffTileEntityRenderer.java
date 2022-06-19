package mod.vemerion.wizardstaff.renderer;

import java.util.Random;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;

import mod.vemerion.wizardstaff.init.ModItems;
import mod.vemerion.wizardstaff.init.ModLayerLocations;
import mod.vemerion.wizardstaff.model.AbstractWizardStaffModel;
import mod.vemerion.wizardstaff.model.NetherWizardStaffModel;
import mod.vemerion.wizardstaff.model.WizardStaffModel;
import mod.vemerion.wizardstaff.staff.WizardStaffItem;
import mod.vemerion.wizardstaff.staff.WizardStaffItemHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LazyOptional;

public class WizardStaffTileEntityRenderer extends BlockEntityWithoutLevelRenderer {

	private final WizardStaffModel WIZARD_STAFF;
	private final NetherWizardStaffModel NETHER_WIZARD_STAFF;

	public WizardStaffTileEntityRenderer(BlockEntityRenderDispatcher pBlockEntityRenderDispatcher,
			EntityModelSet pEntityModelSet) {
		super(pBlockEntityRenderDispatcher, pEntityModelSet);
		WIZARD_STAFF = new WizardStaffModel(pEntityModelSet.bakeLayer(ModLayerLocations.WIZARD_STAFF));
		NETHER_WIZARD_STAFF = new NetherWizardStaffModel(
				pEntityModelSet.bakeLayer(ModLayerLocations.NETHER_WIZARD_STAFF));
	}

	@Override
	public void renderByItem(ItemStack itemStackIn, ItemTransforms.TransformType transformType, PoseStack matrixStackIn,
			MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		if (!shouldRender(itemStackIn))
			return;
		renderOnlyStaffNoPop(itemStackIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
		renderOnlyMagic(itemStackIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
		matrixStackIn.popPose();
	}

	protected AbstractWizardStaffModel getModel(ItemStack itemStackIn) {
		return itemStackIn.getItem() == ModItems.WIZARD_STAFF ? WIZARD_STAFF : NETHER_WIZARD_STAFF;
	}

	private boolean shouldRender(ItemStack staff) {
		LazyOptional<WizardStaffItemHandler> optInteractionHandler = WizardStaffItemHandler.getOptional(staff);
		return optInteractionHandler.isPresent() && optInteractionHandler.orElse(null).isVisible();
	}

	protected void renderOnlyStaffNoPop(ItemStack itemStackIn, PoseStack matrixStackIn, MultiBufferSource bufferIn,
			int combinedLightIn, int combinedOverlayIn) {
		if (!shouldRender(itemStackIn))
			return;
		matrixStackIn.pushPose();
		matrixStackIn.scale(1.0F, -1.0F, -1.0F);
		matrixStackIn.scale(0.5f, 0.5f, 0.5f);
		AbstractWizardStaffModel model = getModel(itemStackIn);
		VertexConsumer builder = ItemRenderer.getFoilBuffer(bufferIn, model.renderType(model.getTexture()), false,
				itemStackIn.hasFoil());
		model.renderToBuffer(matrixStackIn, builder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1F);
	}

	protected void renderOnlyMagic(ItemStack itemStackIn, PoseStack matrixStackIn, MultiBufferSource bufferIn,
			int combinedLightIn, int combinedOverlayIn) {
		ItemStack magic = WizardStaffItem.getMagic(itemStackIn);
		if (!shouldRender(itemStackIn))
			return;

		float ageInTicks = Minecraft.getInstance().player.tickCount + Minecraft.getInstance().getFrameTime();
		matrixStackIn.pushPose();
		matrixStackIn.translate(0, -3.1, 0);
		matrixStackIn.mulPose(new Quaternion(180, ageInTicks, 0, true));
		ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
		float magicScale = getModel(itemStackIn).getMagicScale();
		matrixStackIn.scale(magicScale, magicScale, magicScale);
		itemRenderer.renderStatic(magic, TransformType.GUI, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn,
				0);
		matrixStackIn.popPose();

	}

	public static void buildup(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration, ItemStack stack,
			PoseStack matrix, MultiBufferSource buffer, int light, int combinedOverlayIn, float partialTicks,
			HumanoidArm side) {
		float progress = duration / maxDuration;
		Random random = new Random((int) duration % 5 + 5);
		Vec3 offset = new Vec3((random.nextDouble() * 0.6 - 0.3) * progress,
				(random.nextDouble() * 0.6 - 0.3) * progress, (random.nextDouble() * 0.6 - 0.3) * progress);
		matrix.pushPose();
		matrix.translate(0 + Mth.clampedLerp(0, offset.x(), partialTicks),
				-1.4 + Mth.clampedLerp(0, offset.y(), partialTicks),
				-1.5 + Mth.clampedLerp(0, offset.z(), partialTicks));
		renderer.renderByItem(stack, ItemTransforms.TransformType.GUI, matrix, buffer, light, combinedOverlayIn);
		matrix.popPose();
	}

	public static void helicopter(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration,
			ItemStack stack, PoseStack matrix, MultiBufferSource buffer, int light, int combinedOverlayIn,
			float partialTicks, HumanoidArm hand) {
		matrix.pushPose();
		matrix.mulPose(new Quaternion(0, 0, (duration / 5f) * 360f, true));
		matrix.translate(0, -0.5, -1.5);
		renderer.renderByItem(stack, ItemTransforms.TransformType.GUI, matrix, buffer, light, combinedOverlayIn);
		matrix.popPose();
	}

	public static void forward(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration, ItemStack stack,
			PoseStack matrix, MultiBufferSource buffer, int light, int combinedOverlayIn, float partialTicks,
			HumanoidArm hand) {
		float offset = hand == HumanoidArm.RIGHT ? 1 : -1;
		float max = maxDuration > 20 ? 5 : maxDuration / 4;
		float progress = Mth.clamp(duration / max, 0, 1);
		matrix.pushPose();
		matrix.mulPose(new Quaternion((float) Mth.clampedLerp(0, -45, progress), 0,
				(float) Mth.clampedLerp(0, 35 * offset, progress), true));
		matrix.translate(offset, -0.5 - progress / 5, -1.2);
		renderer.renderByItem(stack, ItemTransforms.TransformType.GUI, matrix, buffer, light, combinedOverlayIn);
		matrix.popPose();
	}

	public static void spinMagic(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration,
			ItemStack stack, PoseStack matrix, MultiBufferSource buffer, int light, int combinedOverlayIn,
			float partialTicks, HumanoidArm hand) {
		matrix.pushPose();
		float progress = duration / 5;
		matrix.translate(hand == HumanoidArm.RIGHT ? 1 : -1, -1, -1.2);
		renderer.renderOnlyStaffNoPop(stack, matrix, buffer, light, combinedOverlayIn);
		matrix.mulPose(new Quaternion(0, progress * 360, 0, true));
		renderer.renderOnlyMagic(stack, matrix, buffer, light, combinedOverlayIn);
		matrix.popPose();
		matrix.popPose();
	}

	public static void buildupMagic(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration,
			ItemStack stack, PoseStack matrix, MultiBufferSource buffer, int light, int combinedOverlayIn,
			float partialTicks, HumanoidArm hand) {
		matrix.pushPose();
		float progress = duration / maxDuration;
		Random random = new Random((int) duration % 5 + 5);
		Vec3 offset = new Vec3((random.nextDouble() * 0.3 - 0.15) * progress,
				(random.nextDouble() * 0.3 - 0.15) * progress, (random.nextDouble() * 0.3 - 0.15) * progress);
		matrix.pushPose();
		matrix.translate(hand == HumanoidArm.RIGHT ? 1 : -1, -1, -1.2);
		renderer.renderOnlyStaffNoPop(stack, matrix, buffer, light, combinedOverlayIn);
		matrix.translate(0 + Mth.clampedLerp(0, offset.x(), partialTicks), Mth.clampedLerp(0, offset.y(), partialTicks),
				Mth.clampedLerp(0, offset.z(), partialTicks));
		renderer.renderOnlyMagic(stack, matrix, buffer, light, combinedOverlayIn);
		matrix.popPose();
		matrix.popPose();
	}

	public static void swinging(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration,
			ItemStack stack, PoseStack matrix, MultiBufferSource buffer, int light, int combinedOverlayIn,
			float partialTicks, HumanoidArm hand) {
		matrix.pushPose();
		matrix.mulPose(new Quaternion((float) Mth.sin((duration / 20) * (float) Math.PI * 2) * 30, 0, 0, true));
		matrix.translate(hand == HumanoidArm.RIGHT ? 1 : -1, -1, -1.2);
		renderer.renderByItem(stack, ItemTransforms.TransformType.GUI, matrix, buffer, light, combinedOverlayIn);
		matrix.popPose();
	}

	public static void forwardBuildup(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration,
			ItemStack stack, PoseStack matrix, MultiBufferSource buffer, int light, int combinedOverlayIn,
			float partialTicks, HumanoidArm hand) {
		float handOffset = hand == HumanoidArm.RIGHT ? 1 : -1;
		float max = maxDuration > 20 ? 5 : maxDuration / 4;
		float progress = Mth.clamp(duration / max, 0, 1);
		float buildupProgress = duration / maxDuration;
		Random random = new Random((int) duration % 5 + 5);
		Vec3 offset = new Vec3((random.nextDouble() * 0.5 - 0.25) * buildupProgress,
				(random.nextDouble() * 0.5 - 0.25) * buildupProgress,
				(random.nextDouble() * 0.5 - 0.25) * buildupProgress);

		matrix.pushPose();
		matrix.mulPose(new Quaternion((float) Mth.clampedLerp(0, -45, progress), 0,
				(float) Mth.clampedLerp(0, 35 * handOffset, progress), true));
		matrix.translate(handOffset + Mth.clampedLerp(0, offset.x(), partialTicks),
				-0.5 - progress / 5 + Mth.clampedLerp(0, offset.y(), partialTicks),
				-1.2 + Mth.clampedLerp(0, offset.z(), partialTicks));
		renderer.renderByItem(stack, ItemTransforms.TransformType.GUI, matrix, buffer, light, combinedOverlayIn);
		matrix.popPose();
	}

	public static void forwardWaving(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration,
			ItemStack stack, PoseStack matrix, MultiBufferSource buffer, int light, int combinedOverlayIn,
			float partialTicks, HumanoidArm hand) {
		float offset = hand == HumanoidArm.RIGHT ? 1 : -1;
		float maxForward = maxDuration > 20 ? 5 : maxDuration / 4;
		float forwardProgress = Mth.clamp(duration / maxForward, 0, 1);
		Vec3 forward = new Vec3(Mth.clampedLerp(0, -45, forwardProgress), 0,
				Mth.clampedLerp(0, 35 * offset, forwardProgress));
		Vec3 waving = new Vec3(Mth.cos(duration / 20 * (float) Math.PI * 2) * 10, 0,
				Mth.sin(duration / 20 * (float) Math.PI * 2) * 10);
		matrix.pushPose();
		matrix.translate(0, -0.5, 0);
		matrix.mulPose(
				new Quaternion((float) (forward.x + waving.x), (float) waving.y, (float) (forward.z + waving.z), true));
		matrix.translate(0, 0.5, 0);
		matrix.translate(offset, -0.5 - forwardProgress / 5, -1.2);
		renderer.renderByItem(stack, ItemTransforms.TransformType.GUI, matrix, buffer, light, combinedOverlayIn);
		matrix.popPose();
	}

	public static void circling(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration,
			ItemStack stack, PoseStack matrix, MultiBufferSource buffer, int light, int combinedOverlayIn,
			float partialTicks, HumanoidArm hand) {
		matrix.pushPose();
		matrix.translate(0, -1, 0);
		matrix.mulPose(new Quaternion((float) Mth.cos((duration / 10) * (float) Math.PI * 2) * 10, 0,
				(float) Mth.sin((duration / 10) * (float) Math.PI * 2) * 10, true));
		matrix.translate(0, 1, 0);
		matrix.translate(hand == HumanoidArm.RIGHT ? 1 : -1, -1, -1.2);
		renderer.renderByItem(stack, ItemTransforms.TransformType.GUI, matrix, buffer, light, combinedOverlayIn);
		matrix.popPose();
	}

	public static void surround(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration,
			ItemStack stack, PoseStack matrix, MultiBufferSource buffer, int light, int combinedOverlayIn,
			float partialTicks, HumanoidArm hand) {
		matrix.pushPose();
		matrix.mulPose(new Quaternion(0, (duration / 40) * 360f, 0, true));
		matrix.mulPose(new Quaternion(0, 0, (duration / 5) * 360f, true));
		matrix.translate(0, 0, -1);
		renderer.renderByItem(stack, ItemTransforms.TransformType.GUI, matrix, buffer, light, combinedOverlayIn);
		matrix.popPose();
	}

	public static void drill(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration, ItemStack stack,
			PoseStack matrix, MultiBufferSource buffer, int light, int combinedOverlayIn, float partialTicks,
			HumanoidArm hand) {
		float offset = hand == HumanoidArm.RIGHT ? 1 : -1;
		float max = maxDuration > 20 ? 5 : maxDuration / 4;
		float progress = Mth.clamp(duration / max, 0, 1);
		matrix.pushPose();
		matrix.translate(offset, -0.5 - progress / 5, -1.2);
		matrix.mulPose(new Quaternion((float) Mth.clampedLerp(0, -45, progress), 0,
				(float) Mth.clampedLerp(0, 35 * offset, progress), true));

		float maxRotate = maxDuration > 20 ? 20 : maxDuration;
		float rotation = duration < maxRotate ? duration * duration
				: maxRotate * maxRotate + maxRotate * 2 * (duration - maxRotate);
		matrix.mulPose(new Quaternion(0, rotation, 0, true));

		renderer.renderByItem(stack, ItemTransforms.TransformType.GUI, matrix, buffer, light, combinedOverlayIn);
		matrix.popPose();
	}

	public static void noRender(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration,
			ItemStack stack, PoseStack matrix, MultiBufferSource buffer, int light, int combinedOverlayIn,
			float partialTicks, HumanoidArm hand) {

	}

	@FunctionalInterface
	public static interface RenderFirstPersonMagic {
		public void render(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration, ItemStack stack,
				PoseStack matrix, MultiBufferSource buffer, int light, int combinedOverlayIn, float partialTicks,
				HumanoidArm hand);
	}
}
