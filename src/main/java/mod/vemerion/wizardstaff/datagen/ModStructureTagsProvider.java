package mod.vemerion.wizardstaff.datagen;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.netherupdate.NetherBrickMagic;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModStructureTagsProvider extends TagsProvider<Structure> {
	public ModStructureTagsProvider(DataGenerator pGenerator, ExistingFileHelper existingFileHelper) {
		super(pGenerator, BuiltinRegistries.STRUCTURES, Main.MODID, existingFileHelper);
	}

	protected void addTags() {
		tag(NetherBrickMagic.NETHER_FORTRESS).add(BuiltinStructures.FORTRESS);
	}
}
