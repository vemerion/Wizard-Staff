package mod.vemerion.wizardstaff.datagen;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.init.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {

	public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen, Main.MODID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		models();
		simpleBlock(ModBlocks.MAGIC_BRICKS, models().cubeAll(ModBlocks.MAGIC_BRICKS.getRegistryName().getPath(),
				mcLoc(ModelProvider.BLOCK_FOLDER + "/" + "bricks")));
		simpleBlock(ModBlocks.MAGIC_LIGHT, models().getBuilder(ModBlocks.MAGIC_LIGHT.getRegistryName().toString()));
	}

}
