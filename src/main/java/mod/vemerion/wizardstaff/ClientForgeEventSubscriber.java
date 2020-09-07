package mod.vemerion.wizardstaff;

import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.staff.WizardStaffItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeEventSubscriber {

	@SubscribeEvent
	public static void wizardStaff(RenderHandEvent event) {
		AbstractClientPlayerEntity player = Minecraft.getInstance().player;
		ItemStack itemStack = event.getItemStack();
		Item item = itemStack.getItem();
		float partialTicks = event.getPartialTicks();
		if (item.equals(Main.WIZARD_STAFF_ITEM) && player.getActiveItemStack().equals(itemStack)) {
			event.setCanceled(true);
			Item magic = ((WizardStaffItem) item).getMagic(itemStack).getItem();
			HandSide side = event.getHand() == Hand.MAIN_HAND ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
			WizardStaffTileEntityRenderer renderer = new WizardStaffTileEntityRenderer();
			int maxDuration = itemStack.getUseDuration();
			float duration = (float)maxDuration
					- ((float) player.getItemInUseCount() - partialTicks + 1.0f);
			if (magic.equals(Items.ELYTRA)) {
				renderer.helicopter(duration, maxDuration, itemStack, event.getMatrixStack(), event.getBuffers(), event.getLight(),
						OverlayTexture.NO_OVERLAY, partialTicks, side);
			} else if (magic.equals(Main.WIZARD_STAFF_ITEM)) {
				renderer.buildup(duration, maxDuration, itemStack, event.getMatrixStack(), event.getBuffers(), event.getLight(),
						OverlayTexture.NO_OVERLAY, partialTicks, side);
			} else if (magic.equals(Items.CLOCK)) {
				renderer.spinMagic(duration, maxDuration, itemStack, event.getMatrixStack(), event.getBuffers(), event.getLight(),
						OverlayTexture.NO_OVERLAY, partialTicks, side);
			} else if (magic.equals(Items.WRITABLE_BOOK)) {
				renderer.buildupMagic(duration, maxDuration, itemStack, event.getMatrixStack(), event.getBuffers(), event.getLight(),
						OverlayTexture.NO_OVERLAY, partialTicks, side);
			} else if (magic.equals(Items.CARVED_PUMPKIN)) {
				renderer.forward(duration, maxDuration, itemStack, event.getMatrixStack(), event.getBuffers(), event.getLight(),
						OverlayTexture.NO_OVERLAY, partialTicks, side);
			} else if (magic.equals(Items.JUKEBOX)) {
				renderer.swinging(duration, maxDuration, itemStack, event.getMatrixStack(), event.getBuffers(), event.getLight(),
						OverlayTexture.NO_OVERLAY, partialTicks, side);
			} else if (magic.equals(Items.BLAZE_POWDER)) {
				renderer.forward(duration, maxDuration, itemStack, event.getMatrixStack(), event.getBuffers(), event.getLight(),
						OverlayTexture.NO_OVERLAY, partialTicks, side);
			} else if (magic.equals(Items.LEATHER_HELMET)) {
				renderer.buildupMagic(duration, maxDuration, itemStack, event.getMatrixStack(), event.getBuffers(), event.getLight(),
						OverlayTexture.NO_OVERLAY, partialTicks, side);
			} else if (magic.equals(Items.EGG)) {
				renderer.forward(duration, maxDuration, itemStack, event.getMatrixStack(), event.getBuffers(), event.getLight(),
						OverlayTexture.NO_OVERLAY, partialTicks, side);
			} else if (magic.equals(Items.GOLD_INGOT)) {
				renderer.forwardBuildup(duration, maxDuration, itemStack, event.getMatrixStack(), event.getBuffers(), event.getLight(),
						OverlayTexture.NO_OVERLAY, partialTicks, side);
			}
		}
	}
}
