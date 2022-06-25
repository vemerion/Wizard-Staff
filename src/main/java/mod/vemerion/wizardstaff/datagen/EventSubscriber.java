package mod.vemerion.wizardstaff.datagen;

import mod.vemerion.wizardstaff.Main;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class EventSubscriber {

	@SubscribeEvent
	public static void onGatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();

		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

		if (event.includeServer()) {
			generator.addProvider(new MagicProvider(generator, Main.MODID));
			generator.addProvider(new ModRecipeProvider(generator));
			generator.addProvider(new ModConfiguredStructureTagsProvider(generator, Main.MODID, existingFileHelper));
		}
		if (event.includeClient()) {
			generator.addProvider(new ModItemModelProvider(generator, existingFileHelper));
			generator.addProvider(new ModBlockStateProvider(generator, existingFileHelper));
			generator.addProvider(new ModLanguageProvider(generator));
			generator.addProvider(new ModSoundProvider(generator, existingFileHelper));
		}
	}
}
