package mod.vemerion.wizardstaff.event;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.Magics;
import mod.vemerion.wizardstaff.capability.Experience;
import mod.vemerion.wizardstaff.capability.ScreenAnimations;
import mod.vemerion.wizardstaff.capability.Wizard;
import mod.vemerion.wizardstaff.staff.WizardStaffItem;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = Main.MODID, bus = Bus.FORGE)
public class ForgeEventSubscriber {

	@SubscribeEvent
	public static void addMagicsReloadListener(AddReloadListenerEvent event) {
		event.addListener(Magics.getInstance(false));
	}

	@SubscribeEvent
	public static void synchMagics(PlayerLoggedInEvent event) {
		Magics.getInstance(false).sendAllMagicMessage((ServerPlayer) event.getPlayer());
	}

	@SubscribeEvent
	public static void tickWizard(PlayerTickEvent event) {
		Player player = event.player;
		Wizard.getWizardOptional(player).ifPresent(w -> w.tick(player));
	}

	@SubscribeEvent
	public static void cloneCapabilities(PlayerEvent.Clone event) {
		Player original = event.getOriginal();
		Player player = event.getPlayer();
		copyCap(player, original, Experience.CAPABILITY);
		copyCap(player, original, ScreenAnimations.CAPABILITY);
		copyCap(player, original, Wizard.CAPABILITY);
	}

	private static <T extends Tag> void copyCap(Player dest, Player src,
			Capability<? extends INBTSerializable<T>> cap) {
		src.getCapability(cap).ifPresent(c1 -> {
			dest.getCapability(cap).ifPresent(c2 -> {
				c2.deserializeNBT(c1.serializeNBT());
			});
		});
	}

	@SubscribeEvent
	public static void preventBlockUse(RightClickBlock event) {
		ItemStack stack = event.getItemStack();
		if (event.getHand() != InteractionHand.MAIN_HAND || !(stack.getItem() instanceof WizardStaffItem)
				|| event.getHitVec().getType() != Type.BLOCK)
			return;
		if (WizardStaffItem.magicPreventOtherUse(event.getWorld(), event.getPlayer(), stack))
			event.setUseBlock(Result.DENY);
	}
}
