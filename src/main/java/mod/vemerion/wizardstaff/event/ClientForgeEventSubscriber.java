package mod.vemerion.wizardstaff.event;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.Magics;
import mod.vemerion.wizardstaff.network.CycleCurrentMessage;
import mod.vemerion.wizardstaff.network.Network;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.staff.WizardStaffItem;
import mod.vemerion.wizardstaff.staff.WizardStaffItemHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.MovementInput;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
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
		if (item instanceof WizardStaffItem && player.getActiveItemStack().equals(itemStack)) {
			event.setCanceled(true);
			ItemStack magic = WizardStaffItem.getMagic(itemStack);
			HandSide side = event.getHand() == Hand.MAIN_HAND ? player.getPrimaryHand()
					: player.getPrimaryHand().opposite();
			WizardStaffTileEntityRenderer renderer = (WizardStaffTileEntityRenderer) item
					.getItemStackTileEntityRenderer();
			int maxDuration = itemStack.getUseDuration();
			float duration = (float) maxDuration - ((float) player.getItemInUseCount() - partialTicks + 1.0f);
			Magics.getInstance(true).get(magic).firstPersonRenderer().render(renderer, duration, maxDuration, itemStack,
					event.getMatrixStack(), event.getBuffers(), event.getLight(), OverlayTexture.NO_OVERLAY,
					partialTicks, side);
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
		Item item = Minecraft.getInstance().player.getHeldItem(event.getHand()).getItem();
		if (event.getHand() != Hand.MAIN_HAND || !event.isAttack() || !(item instanceof WizardStaffItem))
			return;
		event.setSwingHand(false);
	}

	@SubscribeEvent
	public static void noStaffSlowdown(InputUpdateEvent event) {
		if (!(event.getPlayer().getActiveItemStack().getItem() instanceof WizardStaffItem))
			return;
		MovementInput movement = event.getMovementInput();
		movement.moveStrafe *= 1 / 0.2;
		movement.moveForward *= 1 / 0.2;
	}
}
