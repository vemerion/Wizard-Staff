package mod.vemerion.wizardstaff;

import mod.vemerion.wizardstaff.Magic.Magics;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = Main.MODID, bus = Bus.FORGE)
public class ForgeEventSubscriber {

	@SubscribeEvent
	public static void addMagicsReloadListener(AddReloadListenerEvent event) {
		event.addListener(Magics.getInstance());
	}

	@SubscribeEvent
	public static void synchMagics(PlayerLoggedInEvent event) {
		Magics.getInstance().sendAllMagicMessage((ServerPlayerEntity) event.getPlayer());
	}
}
