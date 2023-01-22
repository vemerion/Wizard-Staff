package mod.vemerion.wizardstaff.datagen;

import java.util.concurrent.CompletableFuture;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.init.ModStructureTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModStructureTagsProvider extends TagsProvider<Structure> {

	public ModStructureTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> pProvider,
			ExistingFileHelper existingFileHelper) {
		super(packOutput, Registries.STRUCTURE, pProvider, Main.MODID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider pProvider) {
		this.tag(ModStructureTags.NETHER_FORTRESS).add(BuiltinStructures.FORTRESS);
	}

	@Override
	public String getName() {
		return Main.MODID + " Structure Feature Tags";
	}

}
