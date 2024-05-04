package mod.vemerion.wizardstaff.datagen;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItemModelProvider extends ItemModelProvider {

	public ModItemModelProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
		super(packOutput, Main.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		magicArmor(ModItems.DRUID_BOOTS.get());
		magicArmor(ModItems.DRUID_CHESTPLATE.get());
		magicArmor(ModItems.DRUID_HELMET.get());
		magicArmor(ModItems.DRUID_LEGGINGS.get());
		magicArmor(ModItems.WARLOCK_BOOTS.get());
		magicArmor(ModItems.WARLOCK_CHESTPLATE.get());
		magicArmor(ModItems.WARLOCK_HELMET.get());
		magicArmor(ModItems.WARLOCK_LEGGINGS.get());
		magicArmor(ModItems.WIZARD_BOOTS.get());
		magicArmor(ModItems.WIZARD_CHESTPLATE.get());
		magicArmor(ModItems.WIZARD_HAT.get());
		magicArmor(ModItems.WIZARD_LEGGINGS.get());
		wizardStaff(ModItems.NETHER_WIZARD_STAFF.get());
		wizardStaff(ModItems.WIZARD_STAFF.get());
	}

	private void wizardStaff(Item item) {
		String name = ForgeRegistries.ITEMS.getKey(item).getPath();
		getBuilder(name).parent(new UncheckedModelFile(mcLoc("builtin/entity"))).transforms()
				.transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).translation(8, 0, 9).scale(1).end()
				.transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).translation(-8, 0, 9).scale(1).end()
				.transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).rotation(-10, 5, 0).translation(10, 0, -1)
				.scale(1).end().transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).rotation(-10, 5, 0)
				.translation(-3, 0, 3).scale(1).end().transform(ItemDisplayContext.GUI).rotation(0, 45, -45)
				.translation(4, -3, 0).scale(0.5f).end().transform(ItemDisplayContext.GROUND).translation(10, 16, 10)
				.scale(1).end().end();
	}

	private void magicArmor(Item item) {
		String name = ForgeRegistries.ITEMS.getKey(item).getPath();
		String loc = ITEM_FOLDER + "/" + name;
		singleTexture(name, mcLoc(ITEM_FOLDER + "/generated"), "layer0", modLoc(loc)).texture("layer1",
				modLoc(loc + "_overlay"));
	}
}
