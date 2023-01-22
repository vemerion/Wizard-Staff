package mod.vemerion.wizardstaff.datagen;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.init.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {

	public ModBlockStateProvider(PackOutput packOutput, ExistingFileHelper exFileHelper) {
		super(packOutput, Main.MODID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		models();
		simpleBlock(ModBlocks.MAGIC_BRICKS.get(), models().cubeAll("magic_bricks",
				mcLoc(ModelProvider.BLOCK_FOLDER + "/" + "bricks")));
		simpleBlock(ModBlocks.MAGIC_LIGHT.get(), models().getBuilder("magic_light"));
	}

}
