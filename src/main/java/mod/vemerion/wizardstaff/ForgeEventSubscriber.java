package mod.vemerion.wizardstaff;

import mod.vemerion.wizardstaff.Magic.Magics;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;

@EventBusSubscriber(modid = Main.MODID, bus = Bus.FORGE)
public class ForgeEventSubscriber {

	@SubscribeEvent
	public static void setupServer(FMLServerAboutToStartEvent event) {
		event.getServer().getResourceManager().addReloadListener(Magics.getInstance());
	}

	@SubscribeEvent
	public static void synchMagics(PlayerLoggedInEvent event) {
		Magics.getInstance().sendAllMagicMessage((ServerPlayerEntity) event.getPlayer());
	}
}
