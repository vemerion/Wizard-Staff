package mod.vemerion.wizardstaff.datagen;

import mod.vemerion.wizardstaff.Main;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class EventSubscriber {

	@SubscribeEvent
	public static void onGatherData(GatherDataEvent event) {
		var generator = event.getGenerator();

		var existingFileHelper = event.getExistingFileHelper();

		generator.addProvider(event.includeServer(), new MagicProvider(generator.getPackOutput(), Main.MODID));
		generator.addProvider(event.includeServer(), new ModRecipeProvider(generator.getPackOutput()));
		generator.addProvider(event.includeServer(),
				new ModStructureTagsProvider(generator.getPackOutput(), event.getLookupProvider(), existingFileHelper));

		generator.addProvider(event.includeServer(),
				new ModDamageTypeProvider(generator.getPackOutput(), event.getLookupProvider()));

		generator.addProvider(event.includeClient(),
				new ModItemModelProvider(generator.getPackOutput(), existingFileHelper));
		generator.addProvider(event.includeClient(),
				new ModBlockStateProvider(generator.getPackOutput(), existingFileHelper));
		generator.addProvider(event.includeClient(), new ModLanguageProvider(generator.getPackOutput()));
		generator.addProvider(event.includeClient(),
				new ModSoundProvider(generator.getPackOutput(), existingFileHelper));
	}
}
