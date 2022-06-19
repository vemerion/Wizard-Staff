package mod.vemerion.wizardstaff.renderer;

import java.util.Random;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;

import mod.vemerion.wizardstaff.Magic.Magics;
import mod.vemerion.wizardstaff.staff.WizardStaffItem;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.RenderProperties;

public class WizardStaffLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

	public WizardStaffLayer(PlayerRenderer renderer) {
		super(renderer);
	}

	@Override
	public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn,
			AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
			float netHeadYaw, float headPitch) {
		ItemStack activeItem = player.getUseItem();
		Item item = activeItem.getItem();
		if (activeItem.isEmpty() || !(item instanceof WizardStaffItem))
			return;

		WizardStaffTileEntityRenderer renderer = (WizardStaffTileEntityRenderer) RenderProperties.get(item)
				.getItemStackRenderer();
		ItemStack magic = WizardStaffItem.getMagic(activeItem);
		int maxDuration = activeItem.getUseDuration();
		float duration = (float) maxDuration - ((float) player.getUseItemRemainingTicks() - partialTicks + 1.0f);
		HumanoidArm side = player.getUsedItemHand() == InteractionHand.MAIN_HAND ? player.getMainArm()
				: player.getMainArm().getOpposite();

		Magics.getInstance(true).get(magic).thirdPersonRenderer().render(renderer, duration, maxDuration, activeItem,
				matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY, partialTicks, side);
	}

	public static void helicopter(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration,
			ItemStack stack, PoseStack matrix, MultiBufferSource buffer, int light, int combinedOverlayIn,
			float partialTicks, HumanoidArm hand) {
		matrix.pushPose();
		float offset = hand == HumanoidArm.RIGHT ? 1 : -1;
		matrix.translate(-0.4 * offset, -0.5, 0);
		matrix.mulPose(new Quaternion(90, 0, (duration / 5f) * 360f, true));
		renderer.renderByItem(stack, TransformType.GUI, matrix, buffer, light, combinedOverlayIn);
		matrix.popPose();
	}

	public static void surround(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration,
			ItemStack stack, PoseStack matrix, MultiBufferSource buffer, int light, int combinedOverlayIn,
			float partialTicks, HumanoidArm hand) {
		matrix.pushPose();
		matrix.translate(0, 0.15, 0);
		matrix.mulPose(new Quaternion(0, (duration / 15f) * 360f, 0, true));
		matrix.mulPose(new Quaternion(0, 0, (duration / 5f) * 360f, true));
		matrix.translate(0, -0.5, -0.5);
		renderer.renderByItem(stack, TransformType.GUI, matrix, buffer, light, combinedOverlayIn);
		matrix.popPose();
	}

	public static void buildup(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration, ItemStack stack,
			PoseStack matrix, MultiBufferSource buffer, int light, int combinedOverlayIn, float partialTicks,
			HumanoidArm hand) {
		float sideOffset = hand == HumanoidArm.RIGHT ? 1 : -1;

		float progress = duration / maxDuration;
		Random random = new Random((int) duration % 5 + 5);
		Vec3 offset = new Vec3((random.nextDouble() * 0.6 - 0.3) * progress,
				(random.nextDouble() * 0.6 - 0.3) * progress, (random.nextDouble() * 0.6 - 0.3) * progress);
		matrix.pushPose();
		matrix.translate(-0.1 * sideOffset + Mth.clampedLerp(0, offset.x(), partialTicks),
				0.6 + Mth.clampedLerp(0, offset.y(), partialTicks),
				-0.45 + Mth.clampedLerp(0, offset.z(), partialTicks));
		matrix.scale(-1, -1, 1);
		renderer.renderByItem(stack, TransformType.GUI, matrix, buffer, light, combinedOverlayIn);
		matrix.popPose();
	}

	public static void spinMagic(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration,
			ItemStack stack, PoseStack matrix, MultiBufferSource buffer, int light, int combinedOverlayIn,
			float partialTicks, HumanoidArm hand) {
		matrix.pushPose();
		float progress = duration / 5;
		matrix.scale(-1, -1, 1);
		matrix.translate(hand == HumanoidArm.RIGHT ? 0.38 : -0.38, -0.60, -0);
		matrix.mulPose(new Quaternion(-75, 0, 0, true));
		renderer.renderOnlyStaffNoPop(stack, matrix, buffer, light, combinedOverlayIn);
		matrix.mulPose(new Quaternion(0, progress * 360, 0, true));
		renderer.renderOnlyMagic(stack, matrix, buffer, light, combinedOverlayIn);
		matrix.popPose();
		matrix.popPose();
	}

	public static void buildupMagic(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration,
			ItemStack stack, PoseStack matrix, MultiBufferSource buffer, int light, int combinedOverlayIn,
			float partialTicks, HumanoidArm hand) {
		float sideOffset = hand == HumanoidArm.RIGHT ? 1 : -1;

		float progress = duration / maxDuration;
		Random random = new Random((int) duration % 5 + 5);
		Vec3 offset = new Vec3((random.nextDouble() * 0.6 - 0.3) * progress,
				(random.nextDouble() * 0.6 - 0.3) * progress, (random.nextDouble() * 0.6 - 0.3) * progress);
		matrix.pushPose();
		matrix.translate(-0.14 * sideOffset, 0.6, -0.45);
		matrix.scale(-1, -1, 1);
		renderer.renderOnlyStaffNoPop(stack, matrix, buffer, light, combinedOverlayIn);
		matrix.translate(Mth.clampedLerp(0, offset.x(), partialTicks), Mth.clampedLerp(0, offset.y(), partialTicks),
				Mth.clampedLerp(0, offset.z(), partialTicks));
		renderer.renderOnlyMagic(stack, matrix, buffer, light, combinedOverlayIn);
		matrix.popPose();
		matrix.popPose();
	}

	public static void swinging(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration,
			ItemStack stack, PoseStack matrix, MultiBufferSource buffer, int light, int combinedOverlayIn,
			float partialTicks, HumanoidArm hand) {

		matrix.pushPose();
		matrix.scale(-1, -1, 1);
		matrix.mulPose(new Quaternion((float) Mth.sin((duration / 20) * (float) Math.PI * 2) * 15 - 65, 0, 0, true));
		matrix.translate(hand == HumanoidArm.RIGHT ? 0.4 : -0.4, -0.3, -0.65);
		renderer.renderByItem(stack, TransformType.GUI, matrix, buffer, light, combinedOverlayIn);
		matrix.popPose();
	}

	public static void forwardShake(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration,
			ItemStack stack, PoseStack matrix, MultiBufferSource buffer, int light, int combinedOverlayIn,
			float partialTicks, HumanoidArm hand) {
		float offset = hand == HumanoidArm.RIGHT ? 1 : -1;
		Random random = new Random((int) duration % 5 + 5);
		Vec3 shake = new Vec3(random.nextDouble() * 0.2 - 0.1, random.nextDouble() * 0.2 - 0.1,
				random.nextDouble() * 0.2 - 0.1);

		matrix.pushPose();
		matrix.scale(-1, -1, 1);
		matrix.translate(Mth.clampedLerp(0, shake.x(), partialTicks), Mth.clampedLerp(0, shake.y(), partialTicks),
				Mth.clampedLerp(0, shake.z(), partialTicks));
		matrix.translate(offset * 0.25, -0.7, -0.3);
		matrix.mulPose(new Quaternion(-30, 10 * offset, 20 * offset, true));
		renderer.renderByItem(stack, TransformType.GUI, matrix, buffer, light, combinedOverlayIn);

		matrix.popPose();
	}

	public static void noRender(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration,
			ItemStack stack, PoseStack matrix, MultiBufferSource buffer, int light, int combinedOverlayIn,
			float partialTicks, HumanoidArm hand) {

	}

	@FunctionalInterface
	public static interface RenderThirdPersonMagic {
		public void render(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration, ItemStack stack,
				PoseStack matrix, MultiBufferSource buffer, int light, int combinedOverlayIn, float partialTicks,
				HumanoidArm hand);
	}

}
