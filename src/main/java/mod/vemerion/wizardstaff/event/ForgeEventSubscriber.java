package mod.vemerion.wizardstaff.event;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.Magics;
import mod.vemerion.wizardstaff.capability.Experience;
import mod.vemerion.wizardstaff.capability.ScreenAnimations;
import mod.vemerion.wizardstaff.capability.Wizard;
import mod.vemerion.wizardstaff.staff.WizardStaffItem;
import mod.vemerion.wizardstaff.staff.WizardStaffItemHandler;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
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
	public static void updateStaffAttributes(LivingEquipmentChangeEvent event) {
		LivingEntity entity = event.getEntityLiving();
		if (event.getSlot() != EquipmentSlot.MAINHAND || !(entity instanceof Player))
			return;

		if (event.getFrom().getItem() instanceof WizardStaffItem) {
			entity.getAttributes().removeAttributeModifiers(WizardStaffItem.getStaffModifiers());
		}

		if (event.getTo().getItem() instanceof WizardStaffItem) {
			entity.getAttributes().addTransientAttributeModifiers(WizardStaffItem.getStaffModifiers());
		}
	}

	// On both sides
	@SubscribeEvent
	public static void cycleCurrent(AttackEntityEvent event) {
		ItemStack stack = event.getPlayer().getMainHandItem();
		if (!(stack.getItem() instanceof WizardStaffItem))
			return;
		WizardStaffItemHandler.getOptional(stack).ifPresent(h -> {
			h.cycleCurrent();
		});
	}

	// On both sides
	@SubscribeEvent
	public static void cycleCurrent(PlayerInteractEvent.LeftClickBlock event) {
		ItemStack stack = event.getItemStack();
		if (event.getHand() != InteractionHand.MAIN_HAND || !(stack.getItem() instanceof WizardStaffItem))
			return;
		WizardStaffItemHandler.getOptional(stack).ifPresent(h -> {
			h.cycleCurrent();
		});
	}
}
