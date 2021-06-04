package mod.vemerion.wizardstaff.event;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.Magics;
import mod.vemerion.wizardstaff.capability.Experience;
import mod.vemerion.wizardstaff.capability.ScreenAnimations;
import mod.vemerion.wizardstaff.capability.Wizard;
import mod.vemerion.wizardstaff.network.JukeboxMagicMessage;
import mod.vemerion.wizardstaff.network.Network;
import mod.vemerion.wizardstaff.network.UpdateMagicsMessage;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventSubscriber {

	@SubscribeEvent
	public static void setup(FMLCommonSetupEvent event) {
		CapabilityManager.INSTANCE.register(ScreenAnimations.class, new ScreenAnimations.ScreenAnimationsStorage(),
				ScreenAnimations::new);
		CapabilityManager.INSTANCE.register(Experience.class, new Experience.ExperienceStorage(), Experience::new);
		CapabilityManager.INSTANCE.register(Wizard.class, new Wizard.WizardStorage(), Wizard::new);

		Network.INSTANCE.registerMessage(0, ScreenAnimations.class, ScreenAnimations::encode, ScreenAnimations::decode,
				ScreenAnimations::handle);
		Network.INSTANCE.registerMessage(1, UpdateMagicsMessage.class, UpdateMagicsMessage::encode,
				UpdateMagicsMessage::decode, UpdateMagicsMessage::handle);
		Network.INSTANCE.registerMessage(2, JukeboxMagicMessage.class, JukeboxMagicMessage::encode,
				JukeboxMagicMessage::decode, JukeboxMagicMessage::handle);

		Magics.init();

	}
}
