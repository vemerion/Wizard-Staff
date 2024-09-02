package mod.vemerion.wizardstaff.init;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.item.DruidArmorItem;
import mod.vemerion.wizardstaff.item.WarlockArmorItem;
import mod.vemerion.wizardstaff.item.WizardArmorItem;
import mod.vemerion.wizardstaff.item.WizardStaffItemGroup;
import mod.vemerion.wizardstaff.staff.WizardStaffItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModItems {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);
	
	public static WizardStaffItemGroup WIZARD_STAFF_ITEM_GROUP = new WizardStaffItemGroup();

	public static final RegistryObject<WizardStaffItem> WIZARD_STAFF = ITEMS.register("wizard_staff",
			() -> new WizardStaffItem(new Item.Properties().tab(WIZARD_STAFF_ITEM_GROUP).stacksTo(1)));
	public static final RegistryObject<WizardStaffItem> NETHER_WIZARD_STAFF = ITEMS.register("nether_wizard_staff",
			() -> new WizardStaffItem(new Item.Properties().tab(WIZARD_STAFF_ITEM_GROUP).stacksTo(1).fireResistant()));
	public static final RegistryObject<WizardArmorItem> WIZARD_HAT = ITEMS.register("wizard_hat",
			() -> new WizardArmorItem(EquipmentSlot.HEAD));
	public static final RegistryObject<WizardArmorItem> WIZARD_CHESTPLATE = ITEMS.register("wizard_chestplate",
			() -> new WizardArmorItem(EquipmentSlot.CHEST));
	public static final RegistryObject<WizardArmorItem> WIZARD_LEGGINGS = ITEMS.register("wizard_leggings",
			() -> new WizardArmorItem(EquipmentSlot.LEGS));
	public static final RegistryObject<WizardArmorItem> WIZARD_BOOTS = ITEMS.register("wizard_boots",
			() -> new WizardArmorItem(EquipmentSlot.FEET));
	public static final RegistryObject<DruidArmorItem> DRUID_HELMET = ITEMS.register("druid_helmet",
			() -> new DruidArmorItem(EquipmentSlot.HEAD));
	public static final RegistryObject<DruidArmorItem> DRUID_CHESTPLATE = ITEMS.register("druid_chestplate",
			() -> new DruidArmorItem(EquipmentSlot.CHEST));
	public static final RegistryObject<DruidArmorItem> DRUID_LEGGINGS = ITEMS.register("druid_leggings",
			() -> new DruidArmorItem(EquipmentSlot.LEGS));
	public static final RegistryObject<DruidArmorItem> DRUID_BOOTS = ITEMS.register("druid_boots",
			() -> new DruidArmorItem(EquipmentSlot.FEET));
	public static final RegistryObject<WarlockArmorItem> WARLOCK_HELMET = ITEMS.register("warlock_helmet",
			() -> new WarlockArmorItem(EquipmentSlot.HEAD));
	public static final RegistryObject<WarlockArmorItem> WARLOCK_CHESTPLATE = ITEMS.register("warlock_chestplate",
			() -> new WarlockArmorItem(EquipmentSlot.CHEST));
	public static final RegistryObject<WarlockArmorItem> WARLOCK_LEGGINGS = ITEMS.register("warlock_leggings",
			() -> new WarlockArmorItem(EquipmentSlot.LEGS));
	public static final RegistryObject<WarlockArmorItem> WARLOCK_BOOTS = ITEMS.register("warlock_boots",
			() -> new WarlockArmorItem(EquipmentSlot.FEET));
	}
