package mod.vemerion.wizardstaff.datagen;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.init.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockStateProvider extends BlockStateProvider {

	public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen, Main.MODID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		models();
		simpleBlock(ModBlocks.MAGIC_BRICKS.get(),
				models().cubeAll(ForgeRegistries.BLOCKS.getKey(ModBlocks.MAGIC_BRICKS.get()).getPath(),
						mcLoc(ModelProvider.BLOCK_FOLDER + "/" + "bricks")));
		simpleBlock(ModBlocks.MAGIC_LIGHT.get(),
				models().getBuilder(ForgeRegistries.BLOCKS.getKey(ModBlocks.MAGIC_LIGHT.get()).toString()));
	}

}