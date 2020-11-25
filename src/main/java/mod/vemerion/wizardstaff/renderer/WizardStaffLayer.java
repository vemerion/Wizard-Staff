package mod.vemerion.wizardstaff.renderer;

import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;

import mod.vemerion.wizardstaff.Magic.Magics;
import mod.vemerion.wizardstaff.staff.WizardStaffItem;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class WizardStaffLayer
		extends LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> {

	public WizardStaffLayer(PlayerRenderer renderer) {
		super(renderer);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn,
			AbstractClientPlayerEntity player, float limbSwing, float limbSwingAmount, float partialTicks,
			float ageInTicks, float netHeadYaw, float headPitch) {
		ItemStack activeItem = player.getActiveItemStack();
		Item item = activeItem.getItem();
		if (activeItem.isEmpty() || !(item instanceof WizardStaffItem))
			return;

		WizardStaffTileEntityRenderer renderer = (WizardStaffTileEntityRenderer) item.getItemStackTileEntityRenderer();
		ItemStack magic = ((WizardStaffItem) item).getMagic(activeItem);
		int maxDuration = activeItem.getUseDuration();
		float duration = (float) maxDuration - ((float) player.getItemInUseCount() - partialTicks + 1.0f);
		HandSide side = player.getActiveHand() == Hand.MAIN_HAND ? player.getPrimaryHand()
				: player.getPrimaryHand().opposite();

		Magics.getInstance().get(magic).thirdPersonRenderer().render(renderer, duration, maxDuration, activeItem,
				matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY, partialTicks, side);
	}

	public static void helicopter(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration,
			ItemStack stack, MatrixStack matrix, IRenderTypeBuffer buffer, int light, int combinedOverlayIn,
			float partialTicks, HandSide hand) {
		matrix.push();
		float offset = hand == HandSide.RIGHT ? 1 : -1;
		matrix.translate(-0.4 * offset, -0.5, 0);
		matrix.rotate(new Quaternion(90, 0, (duration / 5f) * 360f, true));
		renderer.render(stack, matrix, buffer, light, combinedOverlayIn);
		matrix.pop();
	}

	public static void buildup(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration, ItemStack stack,
			MatrixStack matrix, IRenderTypeBuffer buffer, int light, int combinedOverlayIn, float partialTicks,
			HandSide hand) {
		float sideOffset = hand == HandSide.RIGHT ? 1 : -1;

		float progress = duration / maxDuration;
		Random random = new Random((int) duration % 5 + 5);
		Vec3d offset = new Vec3d((random.nextDouble() * 0.6 - 0.3) * progress,
				(random.nextDouble() * 0.6 - 0.3) * progress, (random.nextDouble() * 0.6 - 0.3) * progress);
		matrix.push();
		matrix.translate(-0.1 * sideOffset + MathHelper.clampedLerp(0, offset.getX(), partialTicks),
				0.6 + MathHelper.clampedLerp(0, offset.getY(), partialTicks),
				-0.45 + MathHelper.clampedLerp(0, offset.getZ(), partialTicks));
		matrix.scale(-1, -1, 1);
		renderer.render(stack, matrix, buffer, light, combinedOverlayIn);
		matrix.pop();
	}

	public static void spinMagic(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration,
			ItemStack stack, MatrixStack matrix, IRenderTypeBuffer buffer, int light, int combinedOverlayIn,
			float partialTicks, HandSide hand) {
		matrix.push();
		float progress = duration / 5;
		matrix.scale(-1, -1, 1);
		matrix.translate(hand == HandSide.RIGHT ? 0.38 : -0.38, -0.60, -0);
		matrix.rotate(new Quaternion(-75, 0, 0, true));
		renderer.renderOnlyStaffNoPop(stack, matrix, buffer, light, combinedOverlayIn);
		matrix.rotate(new Quaternion(0, progress * 360, 0, true));
		renderer.renderOnlyMagic(stack, matrix, buffer, light, combinedOverlayIn);
		matrix.pop();
		matrix.pop();
	}

	public static void buildupMagic(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration,
			ItemStack stack, MatrixStack matrix, IRenderTypeBuffer buffer, int light, int combinedOverlayIn,
			float partialTicks, HandSide hand) {
		float sideOffset = hand == HandSide.RIGHT ? 1 : -1;

		float progress = duration / maxDuration;
		Random random = new Random((int) duration % 5 + 5);
		Vec3d offset = new Vec3d((random.nextDouble() * 0.6 - 0.3) * progress,
				(random.nextDouble() * 0.6 - 0.3) * progress, (random.nextDouble() * 0.6 - 0.3) * progress);
		matrix.push();
		matrix.translate(-0.14 * sideOffset, 0.6, -0.45);
		matrix.scale(-1, -1, 1);
		renderer.renderOnlyStaffNoPop(stack, matrix, buffer, light, combinedOverlayIn);
		matrix.translate(MathHelper.clampedLerp(0, offset.getX(), partialTicks),
				MathHelper.clampedLerp(0, offset.getY(), partialTicks),
				MathHelper.clampedLerp(0, offset.getZ(), partialTicks));
		renderer.renderOnlyMagic(stack, matrix, buffer, light, combinedOverlayIn);
		matrix.pop();
		matrix.pop();
	}

	public static void swinging(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration,
			ItemStack stack, MatrixStack matrix, IRenderTypeBuffer buffer, int light, int combinedOverlayIn,
			float partialTicks, HandSide hand) {

		matrix.push();
		matrix.scale(-1, -1, 1);
		matrix.rotate(
				new Quaternion((float) MathHelper.sin((duration / 20) * (float) Math.PI * 2) * 15 - 65, 0, 0, true));
		matrix.translate(hand == HandSide.RIGHT ? 0.4 : -0.4, -0.3, -0.65);
		renderer.render(stack, matrix, buffer, light, combinedOverlayIn);
		matrix.pop();
	}

	public static void forwardShake(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration,
			ItemStack stack, MatrixStack matrix, IRenderTypeBuffer buffer, int light, int combinedOverlayIn,
			float partialTicks, HandSide hand) {
		float offset = hand == HandSide.RIGHT ? 1 : -1;
		Random random = new Random((int) duration % 5 + 5);
		Vec3d shake = new Vec3d(random.nextDouble() * 0.2 - 0.1, random.nextDouble() * 0.2 - 0.1,
				random.nextDouble() * 0.2 - 0.1);

		matrix.push();
		matrix.scale(-1, -1, 1);
		matrix.translate(MathHelper.clampedLerp(0, shake.getX(), partialTicks),
				MathHelper.clampedLerp(0, shake.getY(), partialTicks),
				MathHelper.clampedLerp(0, shake.getZ(), partialTicks));
		matrix.translate(offset * 0.25, -0.7, -0.3);
		matrix.rotate(new Quaternion(-30, 10 * offset, 20 * offset, true));
		renderer.render(stack, matrix, buffer, light, combinedOverlayIn);

		matrix.pop();
	}

	public static void noRender(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration,
			ItemStack stack, MatrixStack matrix, IRenderTypeBuffer buffer, int light, int combinedOverlayIn,
			float partialTicks, HandSide hand) {

	}

	@FunctionalInterface
	public static interface RenderThirdPersonMagic {
		public void render(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration, ItemStack stack,
				MatrixStack matrix, IRenderTypeBuffer buffer, int light, int combinedOverlayIn, float partialTicks,
				HandSide hand);
	}

}
