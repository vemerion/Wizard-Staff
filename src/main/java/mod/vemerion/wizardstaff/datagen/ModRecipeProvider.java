package mod.vemerion.wizardstaff.datagen;

import java.util.function.Consumer;

import mod.vemerion.wizardstaff.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraftforge.common.Tags;

public class ModRecipeProvider extends RecipeProvider {

	public ModRecipeProvider(PackOutput packOutput) {
		super(packOutput);
	}

	@Override
	protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
		ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.WIZARD_STAFF.get()).define('s', Tags.Items.COBBLESTONE)
				.define('l', Tags.Items.RODS_WOODEN).pattern("s s").pattern("sls").pattern(" l ")
				.unlockedBy("has_cobblestone", has(Tags.Items.COBBLESTONE)).save(consumer);
	}

}
