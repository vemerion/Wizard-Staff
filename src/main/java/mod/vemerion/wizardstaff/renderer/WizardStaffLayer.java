package mod.vemerion.wizardstaff.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;

import mod.vemerion.wizardstaff.staff.WizardStaffItem;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;

public class WizardStaffLayer
		extends LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> {
	private PlayerRenderer playerRenderer;

	public WizardStaffLayer(PlayerRenderer renderer) {
		super(renderer);
		this.playerRenderer = renderer;
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
		Item magic = ((WizardStaffItem) item).getMagic(activeItem).getItem();
		int maxDuration = activeItem.getUseDuration();
		float duration = (float) maxDuration - ((float) player.getItemInUseCount() - partialTicks + 1.0f);
		HandSide side = player.getActiveHand() == Hand.MAIN_HAND ? player.getPrimaryHand()
				: player.getPrimaryHand().opposite();
		ModelRenderer arm = side == HandSide.LEFT ? playerRenderer.getEntityModel().bipedLeftArm
				: playerRenderer.getEntityModel().bipedRightArm;
		arm.showModel = true;
		helicopter(renderer, duration, maxDuration, activeItem, matrixStackIn, bufferIn, packedLightIn,
				OverlayTexture.NO_OVERLAY, partialTicks, side, arm, player);

	}

	public void helicopter(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration,
			ItemStack stack, MatrixStack matrix, IRenderTypeBuffer buffer, int light, int combinedOverlayIn,
			float partialTicks, HandSide hand, ModelRenderer arm, AbstractClientPlayerEntity player) {
		matrix.push();
		arm.rotateAngleX = (float) Math.toRadians(-180);
		arm.render(matrix, buffer.getBuffer(RenderType.getEntitySolid(getEntityTexture(player))), light, combinedOverlayIn);
		matrix.pop();
		matrix.push();
		float offset = hand == HandSide.RIGHT ? 1 : -1;
		matrix.translate(-0.4 * offset, -0.5, 0);
		matrix.rotate(new Quaternion(90, 0, (duration / 5f) * 360f, true));
		renderer.render(stack, matrix, buffer, light, combinedOverlayIn);
		matrix.pop();
	}

}
