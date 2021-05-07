package mod.vemerion.wizardstaff.renderer;

import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.vemerion.wizardstaff.init.ModItems;
import mod.vemerion.wizardstaff.model.AbstractWizardStaffModel;
import mod.vemerion.wizardstaff.model.NetherWizardStaffModel;
import mod.vemerion.wizardstaff.model.WizardStaffModel;
import mod.vemerion.wizardstaff.staff.WizardStaffItem;
import mod.vemerion.wizardstaff.staff.WizardStaffItemHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.util.LazyOptional;

public class WizardStaffTileEntityRenderer extends ItemStackTileEntityRenderer {
	private final WizardStaffModel WIZARD_STAFF = new WizardStaffModel();
	private final NetherWizardStaffModel NETHER_WIZARD_STAFF = new NetherWizardStaffModel();

	@Override
	public void func_239207_a_(ItemStack itemStackIn, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn,
			int combinedLightIn, int combinedOverlayIn) {
		if (!shouldRender(itemStackIn))
			return;
		renderOnlyStaffNoPop(itemStackIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
		renderOnlyMagic(itemStackIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
		matrixStackIn.pop();
	}

	protected AbstractWizardStaffModel getModel(ItemStack itemStackIn) {
		return itemStackIn.getItem() == ModItems.WIZARD_STAFF ? WIZARD_STAFF : NETHER_WIZARD_STAFF;
	}
	
	private boolean shouldRender(ItemStack staff) {
		LazyOptional<WizardStaffItemHandler> optHandler = WizardStaffItemHandler.getOptional(staff);
		return optHandler.isPresent() && optHandler.orElse(null).isVisible();
	}

	protected void renderOnlyStaffNoPop(ItemStack itemStackIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn,
			int combinedLightIn, int combinedOverlayIn) {
		if (!shouldRender(itemStackIn))
			return;
		matrixStackIn.push();
		matrixStackIn.scale(1.0F, -1.0F, -1.0F);
		matrixStackIn.scale(0.5f, 0.5f, 0.5f);
		AbstractWizardStaffModel model = getModel(itemStackIn);
		IVertexBuilder builder = ItemRenderer.getBuffer(bufferIn,
				model.getRenderType(model.getTexture()), false, itemStackIn.hasEffect());
		model.render(matrixStackIn, builder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1F);
	}

	protected void renderOnlyMagic(ItemStack itemStackIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn,
			int combinedLightIn, int combinedOverlayIn) {
		ItemStack magic = WizardStaffItem.getMagic(itemStackIn);
		if (!shouldRender(itemStackIn))
			return;

		float ageInTicks = Minecraft.getInstance().player.ticksExisted
				+ Minecraft.getInstance().getRenderPartialTicks();
		matrixStackIn.push();
		matrixStackIn.translate(0, -3.1, 0);
		matrixStackIn.rotate(new Quaternion(180, ageInTicks, 0, true));
		ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
		float magicScale = getModel(itemStackIn).getMagicScale();
		matrixStackIn.scale(magicScale, magicScale, magicScale);
		itemRenderer.renderItem(magic, TransformType.GUI, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
		matrixStackIn.pop();

	}

	public static void buildup(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration, ItemStack stack,
			MatrixStack matrix, IRenderTypeBuffer buffer, int light, int combinedOverlayIn, float partialTicks,
			HandSide side) {
		float progress = duration / maxDuration;
		Random random = new Random((int) duration % 5 + 5);
		Vector3d offset = new Vector3d((random.nextDouble() * 0.6 - 0.3) * progress,
				(random.nextDouble() * 0.6 - 0.3) * progress, (random.nextDouble() * 0.6 - 0.3) * progress);
		matrix.push();
		matrix.translate(0 + MathHelper.clampedLerp(0, offset.getX(), partialTicks),
				-1.4 + MathHelper.clampedLerp(0, offset.getY(), partialTicks),
				-1.5 + MathHelper.clampedLerp(0, offset.getZ(), partialTicks));
		renderer.func_239207_a_(stack, ItemCameraTransforms.TransformType.GUI, matrix, buffer, light, combinedOverlayIn);
		matrix.pop();
	}

	public static void helicopter(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration,
			ItemStack stack, MatrixStack matrix, IRenderTypeBuffer buffer, int light, int combinedOverlayIn,
			float partialTicks, HandSide hand) {
		matrix.push();
		matrix.rotate(new Quaternion(0, 0, (duration / 5f) * 360f, true));
		matrix.translate(0, -0.5, -1.5);
		renderer.func_239207_a_(stack, ItemCameraTransforms.TransformType.GUI, matrix, buffer, light, combinedOverlayIn);
		matrix.pop();
	}

	public static void forward(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration, ItemStack stack,
			MatrixStack matrix, IRenderTypeBuffer buffer, int light, int combinedOverlayIn, float partialTicks,
			HandSide hand) {
		float offset = hand == HandSide.RIGHT ? 1 : -1;
		float max = maxDuration > 20 ? 5 : maxDuration / 4;
		float progress = MathHelper.clamp(duration / max, 0, 1);
		matrix.push();
		matrix.rotate(new Quaternion((float) MathHelper.clampedLerp(0, -45, progress), 0,
				(float) MathHelper.clampedLerp(0, 35 * offset, progress), true));
		matrix.translate(offset, -0.5 - progress / 5, -1.2);
		renderer.func_239207_a_(stack, ItemCameraTransforms.TransformType.GUI, matrix, buffer, light, combinedOverlayIn);
		matrix.pop();
	}

	public static void spinMagic(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration,
			ItemStack stack, MatrixStack matrix, IRenderTypeBuffer buffer, int light, int combinedOverlayIn,
			float partialTicks, HandSide hand) {
		matrix.push();
		float progress = duration / 5;
		matrix.translate(hand == HandSide.RIGHT ? 1 : -1, -1, -1.2);
		renderer.renderOnlyStaffNoPop(stack, matrix, buffer, light, combinedOverlayIn);
		matrix.rotate(new Quaternion(0, progress * 360, 0, true));
		renderer.renderOnlyMagic(stack, matrix, buffer, light, combinedOverlayIn);
		matrix.pop();
		matrix.pop();
	}

	public static void buildupMagic(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration,
			ItemStack stack, MatrixStack matrix, IRenderTypeBuffer buffer, int light, int combinedOverlayIn,
			float partialTicks, HandSide hand) {
		matrix.push();
		float progress = duration / maxDuration;
		Random random = new Random((int) duration % 5 + 5);
		Vector3d offset = new Vector3d((random.nextDouble() * 0.3 - 0.15) * progress,
				(random.nextDouble() * 0.3 - 0.15) * progress, (random.nextDouble() * 0.3 - 0.15) * progress);
		matrix.push();
		matrix.translate(hand == HandSide.RIGHT ? 1 : -1, -1, -1.2);
		renderer.renderOnlyStaffNoPop(stack, matrix, buffer, light, combinedOverlayIn);
		matrix.translate(0 + MathHelper.clampedLerp(0, offset.getX(), partialTicks),
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
		matrix.rotate(new Quaternion((float) MathHelper.sin((duration / 20) * (float) Math.PI * 2) * 30, 0, 0, true));
		matrix.translate(hand == HandSide.RIGHT ? 1 : -1, -1, -1.2);
		renderer.func_239207_a_(stack, ItemCameraTransforms.TransformType.GUI, matrix, buffer, light, combinedOverlayIn);
		matrix.pop();
	}

	public static void forwardBuildup(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration,
			ItemStack stack, MatrixStack matrix, IRenderTypeBuffer buffer, int light, int combinedOverlayIn,
			float partialTicks, HandSide hand) {
		float handOffset = hand == HandSide.RIGHT ? 1 : -1;
		float max = maxDuration > 20 ? 5 : maxDuration / 4;
		float progress = MathHelper.clamp(duration / max, 0, 1);
		float buildupProgress = duration / maxDuration;
		Random random = new Random((int) duration % 5 + 5);
		Vector3d offset = new Vector3d((random.nextDouble() * 0.5 - 0.25) * buildupProgress,
				(random.nextDouble() * 0.5 - 0.25) * buildupProgress,
				(random.nextDouble() * 0.5 - 0.25) * buildupProgress);

		matrix.push();
		matrix.rotate(new Quaternion((float) MathHelper.clampedLerp(0, -45, progress), 0,
				(float) MathHelper.clampedLerp(0, 35 * handOffset, progress), true));
		matrix.translate(handOffset + MathHelper.clampedLerp(0, offset.getX(), partialTicks),
				-0.5 - progress / 5 + MathHelper.clampedLerp(0, offset.getY(), partialTicks),
				-1.2 + MathHelper.clampedLerp(0, offset.getZ(), partialTicks));
		renderer.func_239207_a_(stack, ItemCameraTransforms.TransformType.GUI, matrix, buffer, light, combinedOverlayIn);
		matrix.pop();
	}

	public static void forwardWaving(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration,
			ItemStack stack, MatrixStack matrix, IRenderTypeBuffer buffer, int light, int combinedOverlayIn,
			float partialTicks, HandSide hand) {
		float offset = hand == HandSide.RIGHT ? 1 : -1;
		float maxForward = maxDuration > 20 ? 5 : maxDuration / 4;
		float forwardProgress = MathHelper.clamp(duration / maxForward, 0, 1);
		Vector3d forward = new Vector3d(MathHelper.clampedLerp(0, -45, forwardProgress), 0,
				MathHelper.clampedLerp(0, 35 * offset, forwardProgress));
		Vector3d waving = new Vector3d(MathHelper.cos(duration / 20 * (float) Math.PI * 2) * 10, 0,
				MathHelper.sin(duration / 20 * (float) Math.PI * 2) * 10);
		matrix.push();
		matrix.translate(0, -0.5, 0);
		matrix.rotate(
				new Quaternion((float) (forward.x + waving.x), (float) waving.y, (float) (forward.z + waving.z), true));
		matrix.translate(0, 0.5, 0);
		matrix.translate(offset, -0.5 - forwardProgress / 5, -1.2);
		renderer.func_239207_a_(stack, ItemCameraTransforms.TransformType.GUI, matrix, buffer, light, combinedOverlayIn);
		matrix.pop();
	}

	public static void circling(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration,
			ItemStack stack, MatrixStack matrix, IRenderTypeBuffer buffer, int light, int combinedOverlayIn,
			float partialTicks, HandSide hand) {
		matrix.push();
		matrix.translate(0, -1, 0);
		matrix.rotate(new Quaternion((float) MathHelper.cos((duration / 10) * (float) Math.PI * 2) * 10, 0,
				(float) MathHelper.sin((duration / 10) * (float) Math.PI * 2) * 10, true));
		matrix.translate(0, 1, 0);
		matrix.translate(hand == HandSide.RIGHT ? 1 : -1, -1, -1.2);
		renderer.func_239207_a_(stack, ItemCameraTransforms.TransformType.GUI, matrix, buffer, light, combinedOverlayIn);
		matrix.pop();
	}
	
	public static void surround(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration,
			ItemStack stack, MatrixStack matrix, IRenderTypeBuffer buffer, int light, int combinedOverlayIn,
			float partialTicks, HandSide hand) {
		matrix.push();
		matrix.rotate(new Quaternion(0, (duration / 40) * 360f, 0, true));
		matrix.rotate(new Quaternion(0, 0, (duration / 5) * 360f, true));
		matrix.translate(0, 0, -1);
		renderer.func_239207_a_(stack, ItemCameraTransforms.TransformType.GUI, matrix, buffer, light, combinedOverlayIn);
		matrix.pop();
	}
	
	public static void noRender(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration,
			ItemStack stack, MatrixStack matrix, IRenderTypeBuffer buffer, int light, int combinedOverlayIn,
			float partialTicks, HandSide hand) {
		
	}

	@FunctionalInterface
	public static interface RenderFirstPersonMagic {
		public void render(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration, ItemStack stack,
				MatrixStack matrix, IRenderTypeBuffer buffer, int light, int combinedOverlayIn, float partialTicks,
				HandSide hand);
	}
}
