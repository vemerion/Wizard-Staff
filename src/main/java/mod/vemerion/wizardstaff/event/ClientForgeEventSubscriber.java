package mod.vemerion.wizardstaff.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.OptionalDouble;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mojang.math.Vector3f;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.Magics;
import mod.vemerion.wizardstaff.capability.Wizard;
import mod.vemerion.wizardstaff.init.ModMagics;
import mod.vemerion.wizardstaff.network.CycleCurrentMessage;
import mod.vemerion.wizardstaff.network.Network;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.staff.WizardStaffItem;
import mod.vemerion.wizardstaff.staff.WizardStaffItemHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.RenderProperties;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeEventSubscriber {

	@SubscribeEvent
	public static void wizardStaff(RenderHandEvent event) {
		AbstractClientPlayer player = Minecraft.getInstance().player;
		ItemStack itemStack = event.getItemStack();
		Item item = itemStack.getItem();
		float partialTicks = event.getPartialTicks();
		if (item instanceof WizardStaffItem && player.getUseItem().equals(itemStack)) {
			event.setCanceled(true);
			ItemStack magic = WizardStaffItem.getMagic(itemStack);
			HumanoidArm side = event.getHand() == InteractionHand.MAIN_HAND ? player.getMainArm()
					: player.getMainArm().getOpposite();
			WizardStaffTileEntityRenderer renderer = (WizardStaffTileEntityRenderer) RenderProperties.get(item)
					.getItemStackRenderer();
			int maxDuration = itemStack.getUseDuration();
			float duration = (float) maxDuration - ((float) player.getUseItemRemainingTicks() - partialTicks + 1.0f);
			Magics.getInstance(true).get(magic).firstPersonRenderer().render(renderer, duration, maxDuration, itemStack,
					event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(),
					OverlayTexture.NO_OVERLAY, partialTicks, side);
		}
	}

	@SubscribeEvent
	public static void cycleCurrent(PlayerInteractEvent.LeftClickEmpty event) {
		WizardStaffItemHandler.getOptional(event.getItemStack()).ifPresent(h -> {
			h.cycleCurrent();
			Network.INSTANCE.sendToServer(new CycleCurrentMessage());
		});
	}

	@SubscribeEvent
	public static void noLeftClickWithStaff(InputEvent.ClickInputEvent event) {
		Item item = Minecraft.getInstance().player.getItemInHand(event.getHand()).getItem();
		if (event.getHand() != InteractionHand.MAIN_HAND || !event.isAttack() || !(item instanceof WizardStaffItem))
			return;
		event.setSwingHand(false);
	}

	@SubscribeEvent
	public static void noStaffSlowdown(MovementInputUpdateEvent event) {
		if (!(event.getPlayer().getUseItem().getItem() instanceof WizardStaffItem))
			return;
		var movement = event.getInput();
		movement.leftImpulse *= 1 / 0.2;
		movement.forwardImpulse *= 1 / 0.2;
	}

	@SubscribeEvent
	public static void xRay(RenderLevelLastEvent event) {
		var mc = Minecraft.getInstance();
		var player = mc.player;

		if (!Wizard.isUsingMagic(ModMagics.X_RAY_MAGIC, player)) {
			Wizard.getWizardOptional(player).ifPresent(w -> {
				w.getXRayed().clear();
			});
			return;
		}

		Wizard.getWizardOptional(player).ifPresent(w -> {
			RenderSystem.disableDepthTest();
			RenderSystem.disableCull();

			var camera = mc.gameRenderer.getMainCamera();
			var source = Minecraft.getInstance().renderBuffers().bufferSource();
			var cameraOffset = camera.getPosition().reverse();

			w.getXRayed().forEach(p -> {
				AABB box = (new AABB(p, p.offset(1, 1, 1)));
				LevelRenderer.renderVoxelShape(event.getPoseStack(), source.getBuffer(RenderTypes.LINES),
						Shapes.create(box), cameraOffset.x, cameraOffset.y, cameraOffset.z, 1, 1, 1, 1);
			});
			source.endBatch();

			RenderSystem.enableDepthTest();
			RenderSystem.enableCull();

		});
	}

	static boolean shouldZoom(Minecraft mc) {
		return Wizard.isUsingMagic(ModMagics.ZOOM_MAGIC, mc.player) && mc.options.getCameraType().isFirstPerson();
	}

	static final Method renderSpyglassOverlay = ObfuscationReflectionHelper.findMethod(Gui.class, "m_168675_",
			float.class);

	@SubscribeEvent
	public static void magicSpyglass(RenderGameOverlayEvent.Pre event) {
		if (event.getType() != ElementType.ALL)
			return;

		var mc = Minecraft.getInstance();
		if (!shouldZoom(mc))
			return;

		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.disableDepthTest();

		float time = mc.level.getGameTime() + event.getPartialTicks();

		RenderSystem.setShaderColor(Math.abs(Mth.sin(time / 20f)) * 0.5f + 0.5f, 0,
				Math.abs(Mth.cos(time / 20f + 0.2f)) * 0.5f + 0.5f, 1.0F);
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		var poseStack = RenderSystem.getModelViewStack();
		poseStack.pushPose();
		poseStack.translate(event.getWindow().getGuiScaledWidth() / 2, event.getWindow().getGuiScaledHeight() / 2, 0);
		poseStack.mulPose(Vector3f.ZP.rotationDegrees(time));
		poseStack.translate(-event.getWindow().getGuiScaledWidth() / 2, -event.getWindow().getGuiScaledHeight() / 2, 0);
		RenderSystem.applyModelViewMatrix();

		try {
			renderSpyglassOverlay.invoke(mc.gui, 1.8f);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			Main.LOGGER.warn("Could not invoke 'renderSpyglassOverlay':" + e);
		}
		RenderSystem.restoreProjectionMatrix();
		poseStack.popPose();
		RenderSystem.applyModelViewMatrix();
	}

	@SubscribeEvent
	public static void zoom(EntityViewRenderEvent.FieldOfView event) {
		var mc = Minecraft.getInstance();
		var player = mc.player;

		if (!shouldZoom(mc))
			return;

		Wizard.getWizardOptional(player).ifPresent(w -> {
			float time = mc.level.getGameTime() + (float) event.getPartialTicks();
			var fov = w.getFov() + Mth.sin(time / 20f);
			event.setFOV(fov);
		});
	}

	// Hacky way to expose protected members and types in RenderType to be able to
	// create custom line type
	private static abstract class RenderTypes extends RenderType {

		private RenderTypes(String pName, VertexFormat pFormat, Mode pMode, int pBufferSize, boolean pAffectsCrumbling,
				boolean pSortOnUpload, Runnable pSetupState, Runnable pClearState) {
			super(pName, pFormat, pMode, pBufferSize, pAffectsCrumbling, pSortOnUpload, pSetupState, pClearState);
		}

		private static final RenderType LINES = create(Main.MODID + "_lines", DefaultVertexFormat.POSITION_COLOR_NORMAL,
				VertexFormat.Mode.LINES, 256, false, false,
				RenderType.CompositeState.builder().setShaderState(RENDERTYPE_LINES_SHADER)
						.setLineState(new RenderStateShard.LineStateShard(OptionalDouble.empty()))
						.setLayeringState(VIEW_OFFSET_Z_LAYERING).setTransparencyState(TRANSLUCENT_TRANSPARENCY)
						.setOutputState(ITEM_ENTITY_TARGET).setWriteMaskState(COLOR_DEPTH_WRITE).setCullState(NO_CULL)
						.setDepthTestState(NO_DEPTH_TEST).createCompositeState(false));

	}
}
