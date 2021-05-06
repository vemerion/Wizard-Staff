package mod.vemerion.wizardstaff.datagen;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.init.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelBuilder.Perspective;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {

	public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Main.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		magicArmor(ModItems.DRUID_BOOTS);
		magicArmor(ModItems.DRUID_CHESTPLATE);
		magicArmor(ModItems.DRUID_HELMET);
		magicArmor(ModItems.DRUID_LEGGINGS);
		magicArmor(ModItems.WARLOCK_BOOTS);
		magicArmor(ModItems.WARLOCK_CHESTPLATE);
		magicArmor(ModItems.WARLOCK_HELMET);
		magicArmor(ModItems.WARLOCK_LEGGINGS);
		magicArmor(ModItems.WIZARD_BOOTS);
		magicArmor(ModItems.WIZARD_CHESTPLATE);
		magicArmor(ModItems.WIZARD_HAT);
		magicArmor(ModItems.WIZARD_LEGGINGS);
		wizardStaff(ModItems.NETHER_WIZARD_STAFF);
		wizardStaff(ModItems.WIZARD_STAFF);
	}

	private void wizardStaff(Item item) {
		String name = item.getRegistryName().getPath();
		getBuilder(name).parent(new UncheckedModelFile(mcLoc("builtin/entity"))).transforms()
				.transform(Perspective.THIRDPERSON_RIGHT).translation(8, 0, 9).scale(1).end()
				.transform(Perspective.THIRDPERSON_LEFT).translation(-8, 0, 9).scale(1).end()
				.transform(Perspective.FIRSTPERSON_RIGHT).rotation(-10, 5, 0).translation(10, 0, -1).scale(1).end()
				.transform(Perspective.FIRSTPERSON_LEFT).rotation(-10, 5, 0).translation(-3, 0, 3).scale(1).end()
				.transform(Perspective.GUI).rotation(0, 45, -45).translation(4, -3, 0).scale(0.5f).end()
				.transform(Perspective.GROUND).translation(10, 16, 10).scale(1).end().end();
	}

	private void magicArmor(Item item) {
		String name = item.getRegistryName().getPath();
		String loc = ITEM_FOLDER + "/" + name;
		singleTexture(name, mcLoc(ITEM_FOLDER + "/generated"), "layer0", modLoc(loc)).texture("layer1",
				modLoc(loc + "_overlay"));
	}
}
