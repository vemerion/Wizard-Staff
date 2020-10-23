package mod.vemerion.wizardstaff.renderer;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

import com.mojang.blaze3d.matrix.MatrixStack;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.staff.WizardStaffHandler;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper.UnableToAccessFieldException;

// Class used to disable the normal HeldItemLayer when a wizard staff spell i currently being used
@EventBusSubscriber(modid = Main.MODID, bus = Bus.FORGE, value = Dist.CLIENT)
public class HeldItemFilterLayer
		extends HeldItemLayer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> {

	private static final Set<PlayerRenderer> injected = Collections
			.newSetFromMap(new WeakHashMap<PlayerRenderer, Boolean>());

	@SubscribeEvent
	public static void onPlayerRenderPre(RenderPlayerEvent.Pre event) {
		final PlayerRenderer renderer = event.getRenderer();
		if (renderer != null && !injected.contains(renderer)) {
			addFilterLayer(renderer);
			renderer.addLayer(new WizardStaffLayer(renderer));
			injected.add(renderer);
		}
	}
	
	// TODO: Make third person staff animations work with armor equipped. Perhaps filter layer for BipedArmorLayer?

	private static void addFilterLayer(PlayerRenderer renderer) {
		try {
			List<LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>>> layers = ObfuscationReflectionHelper
					.getPrivateValue(LivingRenderer.class, renderer, "field_177097_h");
			if (layers != null) {
				LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> heldItemLayer = null;
				for (LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> layerRenderer : layers) {
					if (layerRenderer instanceof HeldItemLayer && !(layerRenderer instanceof HeldItemFilterLayer)) {
						heldItemLayer = layerRenderer;
						break;
					}
				}
				if (heldItemLayer != null) {
					renderer.addLayer(new HeldItemFilterLayer(renderer, heldItemLayer));
					layers.remove(heldItemLayer);
				}
			}
		} catch (UnableToAccessFieldException e) {
			Main.LOGGER.warn("Unable to access RenderLivingBase.layerRenderers, reason: " + e);
		}
	}

	private LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> parent;

	public HeldItemFilterLayer(
			IEntityRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> renderer,
			LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> parent) {
		super(renderer);
		this.parent = parent;
	}

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn,
			AbstractClientPlayerEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks,
			float ageInTicks, float netHeadYaw, float headPitch) {

		ItemStack activeItem = entitylivingbaseIn.getActiveItemStack();
		setStaffVisibility(activeItem, false);
		parent.render(matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount,
				partialTicks, ageInTicks, netHeadYaw, headPitch);
		setStaffVisibility(activeItem, true);
	}

	private void setStaffVisibility(ItemStack itemStack, boolean visible) {
		WizardStaffHandler.getOptional(itemStack).ifPresent(handler -> handler.setVisible(visible));
	}

}
