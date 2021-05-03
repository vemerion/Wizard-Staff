package mod.vemerion.wizardstaff.datagen;

import mod.vemerion.wizardstaff.Main;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class EventSubscriber {

	@SubscribeEvent
	public static void onGatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();

//		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

		if (event.includeServer()) {
			generator.addProvider(new MagicProvider(generator, Main.MODID));
		}
		if (event.includeClient()) {
		}
	}
}
