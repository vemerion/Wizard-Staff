package mod.vemerion.wizardstaff.event;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.Magics;
import mod.vemerion.wizardstaff.capability.Experience;
import mod.vemerion.wizardstaff.capability.ScreenAnimations;
import mod.vemerion.wizardstaff.capability.Wizard;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.INBT;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
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
		Magics.getInstance(false).sendAllMagicMessage((ServerPlayerEntity) event.getPlayer());
	}

	@SubscribeEvent
	public static void tickWizard(PlayerTickEvent event) {
		PlayerEntity player = event.player;
		Wizard.getWizardOptional(player).ifPresent(w -> w.tick(player));
	}

	@SubscribeEvent
	public static void cloneCapabilities(PlayerEvent.Clone event) {
		PlayerEntity original = event.getOriginal();
		PlayerEntity player = event.getPlayer();
		copyCap(player, original, Experience.CAPABILITY);
		copyCap(player, original, ScreenAnimations.CAPABILITY);
		copyCap(player, original, Wizard.CAPABILITY);
	}

	private static <T extends INBT> void copyCap(PlayerEntity dest, PlayerEntity src,
			Capability<? extends INBTSerializable<T>> cap) {
		src.getCapability(cap).ifPresent(c1 -> {
			dest.getCapability(cap).ifPresent(c2 -> {
				c2.deserializeNBT(c1.serializeNBT());
			});
		});
	}
}
