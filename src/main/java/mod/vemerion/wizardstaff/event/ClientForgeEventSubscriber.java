package mod.vemerion.wizardstaff.event;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.Magics;
import mod.vemerion.wizardstaff.network.CycleCurrentMessage;
import mod.vemerion.wizardstaff.network.Network;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.staff.WizardStaffItem;
import mod.vemerion.wizardstaff.staff.WizardStaffItemHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.RenderProperties;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

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
}
