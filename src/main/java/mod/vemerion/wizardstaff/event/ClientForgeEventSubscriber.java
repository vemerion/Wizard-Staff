package mod.vemerion.wizardstaff.event;

import java.util.OptionalDouble;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;

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
import net.minecraft.client.player.AbstractClientPlayer;
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
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent.Stage;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeEventSubscriber {

	@SubscribeEvent
	public static void wizardStaff(RenderHandEvent event) {
		var mc = Minecraft.getInstance();
		AbstractClientPlayer player = mc.player;
		ItemStack itemStack = event.getItemStack();
		Item item = itemStack.getItem();
		float partialTicks = event.getPartialTick();
		if (item instanceof WizardStaffItem && player.getUseItem().equals(itemStack)) {
			event.setCanceled(true);
			ItemStack magic = WizardStaffItem.getMagic(itemStack);
			HumanoidArm side = event.getHand() == InteractionHand.MAIN_HAND ? player.getMainArm()
					: player.getMainArm().getOpposite();
			WizardStaffTileEntityRenderer renderer = (WizardStaffTileEntityRenderer) IClientItemExtensions.of(item).getCustomRenderer();
			int maxDuration = itemStack.getUseDuration();
			float duration = (float) maxDuration - ((float) player.getUseItemRemainingTicks() - partialTicks + 1.0f);
			Magics.getInstance(true).get(magic).firstPersonRenderer().render(renderer, duration, maxDuration, itemStack,
					event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(),
					OverlayTexture.NO_OVERLAY, partialTicks, side);
		}
	}

	@SubscribeEvent
	public static void cycleMagicKey(InputEvent.Key event) {
		if (!ClientModEventSubscriber.cycleMagicKey.consumeClick())
			return;

		var mc = Minecraft.getInstance();
		WizardStaffItemHandler.getOptional(mc.player.getMainHandItem()).ifPresent(h -> {
			h.cycleCurrent();
			Network.INSTANCE.sendToServer(new CycleCurrentMessage());
		});
	}

	@SubscribeEvent
	public static void noStaffSlowdown(MovementInputUpdateEvent event) {
		if (!(event.getEntity().getUseItem().getItem() instanceof WizardStaffItem))
			return;
		var movement = event.getInput();
		movement.leftImpulse *= 1 / 0.2;
		movement.forwardImpulse *= 1 / 0.2;
	}

	@SubscribeEvent
	public static void xRay(RenderLevelStageEvent event) {
		if (event.getStage() != Stage.AFTER_PARTICLES)
			return;
		
		var mc = Minecraft.getInstance();
		var player = mc.player;

		if (!Wizard.isUsingMagic(ModMagics.X_RAY_MAGIC.get(), player)) {
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

	@SubscribeEvent
	public static void zoom(ViewportEvent.ComputeFov event) {
		var mc = Minecraft.getInstance();
		var player = mc.player;

		if (!(Wizard.isUsingMagic(ModMagics.ZOOM_MAGIC.get(), mc.player) && mc.options.getCameraType().isFirstPerson()))
			return;

		Wizard.getWizardOptional(player).ifPresent(w -> {
			float time = mc.level.getGameTime() + (float) event.getPartialTick();
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
