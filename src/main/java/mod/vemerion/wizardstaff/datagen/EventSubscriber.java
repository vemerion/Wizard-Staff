package mod.vemerion.wizardstaff.datagen;

import mod.vemerion.wizardstaff.Main;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class EventSubscriber {

	@SubscribeEvent
	public static void onGatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();

		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

		generator.addProvider(event.includeServer(), new MagicProvider(generator, Main.MODID));
		generator.addProvider(event.includeServer(), new ModRecipeProvider(generator));
		generator.addProvider(event.includeServer(), new ModStructureTagsProvider(generator, existingFileHelper));

		generator.addProvider(event.includeClient(), new ModItemModelProvider(generator, existingFileHelper));
		generator.addProvider(event.includeClient(), new ModBlockStateProvider(generator, existingFileHelper));
		generator.addProvider(event.includeClient(), new ModLanguageProvider(generator));
		generator.addProvider(event.includeClient(), new ModSoundProvider(generator, existingFileHelper));
	}
}