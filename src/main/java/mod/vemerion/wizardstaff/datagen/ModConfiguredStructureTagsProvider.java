package mod.vemerion.wizardstaff.datagen;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.init.ModConfiguredStructureTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.ConfiguredStructureTagsProvider;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModConfiguredStructureTagsProvider extends ConfiguredStructureTagsProvider {

	public ModConfiguredStructureTagsProvider(DataGenerator generator, String modId,
			ExistingFileHelper existingFileHelper) {
		super(generator, modId, existingFileHelper);
	}

	@Override
	protected void addTags() {
		this.tag(ModConfiguredStructureTags.NETHER_FORTRESS).add(BuiltinStructures.FORTRESS);
	}

	@Override
	public String getName() {
		return Main.MODID + " Configured Structure Feature Tags";
	}

}
