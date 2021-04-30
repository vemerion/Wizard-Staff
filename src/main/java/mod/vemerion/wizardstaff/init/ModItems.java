package mod.vemerion.wizardstaff.init;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.item.DruidArmorItem;
import mod.vemerion.wizardstaff.item.WarlockArmorItem;
import mod.vemerion.wizardstaff.item.WizardArmorItem;
import mod.vemerion.wizardstaff.item.WizardStaffItemGroup;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.staff.WizardStaffItem;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(value = Main.MODID)
@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModItems {

	public static final WizardStaffItem WIZARD_STAFF = null;
	public static final WizardStaffItem NETHER_WIZARD_STAFF = null;
	public static final WizardArmorItem WIZARD_HAT = null;
	public static final WizardArmorItem WIZARD_CHESTPLATE = null;
	public static final WizardArmorItem WIZARD_LEGGINGS = null;
	public static final WizardArmorItem WIZARD_BOOTS = null;
	public static final DruidArmorItem DRUID_HELMET = null;
	public static final DruidArmorItem DRUID_CHESTPLATE = null;
	public static final DruidArmorItem DRUID_LEGGINGS = null;
	public static final DruidArmorItem DRUID_BOOTS = null;
	public static final WarlockArmorItem WARLOCK_HELMET = null;
	public static final WarlockArmorItem WARLOCK_CHESTPLATE = null;
	public static final WarlockArmorItem WARLOCK_LEGGINGS = null;
	public static final WarlockArmorItem WARLOCK_BOOTS = null;

	public static WizardStaffItemGroup WIZARD_STAFF_ITEM_GROUP = new WizardStaffItemGroup();

	@SubscribeEvent
	public static void onRegisterItem(RegistryEvent.Register<Item> event) {
		Item.Properties staffProperties = new Item.Properties().maxStackSize(1).group(ItemGroup.COMBAT)
				.setISTER(() -> WizardStaffTileEntityRenderer::new);
		event.getRegistry().register(Init.setup(new WizardStaffItem(staffProperties), "wizard_staff"));
		Item.Properties netherStaffProperties = new Item.Properties().maxStackSize(1).group(ItemGroup.COMBAT)
				.setISTER(() -> WizardStaffTileEntityRenderer::new).isImmuneToFire();
		event.getRegistry().register(Init.setup(new WizardStaffItem(netherStaffProperties), "nether_wizard_staff"));

		event.getRegistry().register(Init.setup(new DruidArmorItem(EquipmentSlotType.HEAD), "druid_helmet"));
		event.getRegistry().register(Init.setup(new DruidArmorItem(EquipmentSlotType.CHEST), "druid_chestplate"));
		event.getRegistry().register(Init.setup(new DruidArmorItem(EquipmentSlotType.LEGS), "druid_leggings"));
		event.getRegistry().register(Init.setup(new DruidArmorItem(EquipmentSlotType.FEET), "druid_boots"));

		event.getRegistry().register(Init.setup(new WarlockArmorItem(EquipmentSlotType.HEAD), "warlock_helmet"));
		event.getRegistry().register(Init.setup(new WarlockArmorItem(EquipmentSlotType.CHEST), "warlock_chestplate"));
		event.getRegistry().register(Init.setup(new WarlockArmorItem(EquipmentSlotType.LEGS), "warlock_leggings"));
		event.getRegistry().register(Init.setup(new WarlockArmorItem(EquipmentSlotType.FEET), "warlock_boots"));

		event.getRegistry().register(Init.setup(new WizardArmorItem(EquipmentSlotType.HEAD), "wizard_hat"));
		event.getRegistry().register(Init.setup(new WizardArmorItem(EquipmentSlotType.CHEST), "wizard_chestplate"));
		event.getRegistry().register(Init.setup(new WizardArmorItem(EquipmentSlotType.LEGS), "wizard_leggings"));
		event.getRegistry().register(Init.setup(new WizardArmorItem(EquipmentSlotType.FEET), "wizard_boots"));
	}

}
