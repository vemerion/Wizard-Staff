package mod.vemerion.wizardstaff.datagen;

import java.util.function.Consumer;

import mod.vemerion.wizardstaff.init.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraftforge.common.Tags;

public class ModRecipeProvider extends RecipeProvider {

	public ModRecipeProvider(DataGenerator generatorIn) {
		super(generatorIn);
	}

	@Override
	protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
		ShapedRecipeBuilder.shapedRecipe(ModItems.WIZARD_STAFF).key('s', Tags.Items.COBBLESTONE)
				.key('l', Tags.Items.RODS_WOODEN).patternLine("s s").patternLine("sls").patternLine(" l ")
				.addCriterion("has_cobblestone", hasItem(Tags.Items.COBBLESTONE)).build(consumer);
	}

}
