package mod.vemerion.wizardstaff.renderer;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

import com.mojang.blaze3d.vertex.PoseStack;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.staff.WizardStaffItemHandler;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper.UnableToAccessFieldException;

// Class used to disable the normal HeldItemLayer when a wizard staff spell is currently being used
@EventBusSubscriber(modid = Main.MODID, bus = Bus.FORGE, value = Dist.CLIENT)
public class HeldItemFilterLayer extends ItemInHandLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

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

	private static void addFilterLayer(PlayerRenderer renderer) {
		try {
			List<RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>> layers = ObfuscationReflectionHelper
					.getPrivateValue(LivingEntityRenderer.class, renderer, "f_115291_");
			if (layers != null) {
				RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> heldItemLayer = null;
				for (RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> layerRenderer : layers) {
					if (layerRenderer instanceof ItemInHandLayer && !(layerRenderer instanceof HeldItemFilterLayer)) {
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

	private RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> parent;

	public HeldItemFilterLayer(LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderer,
			RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> parent) {
		super(renderer);
		this.parent = parent;
	}

	@Override
	public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn,
			AbstractClientPlayer entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks,
			float ageInTicks, float netHeadYaw, float headPitch) {

		ItemStack activeItem = entitylivingbaseIn.getUseItem();
		setStaffVisibility(activeItem, false);
		parent.render(matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount,
				partialTicks, ageInTicks, netHeadYaw, headPitch);
		setStaffVisibility(activeItem, true);
	}

	private void setStaffVisibility(ItemStack itemStack, boolean visible) {
		WizardStaffItemHandler.getOptional(itemStack).ifPresent(handler -> handler.setVisible(visible));
	}

}
