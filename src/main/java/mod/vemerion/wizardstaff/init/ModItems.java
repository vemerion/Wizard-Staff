package mod.vemerion.wizardstaff.init;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.item.DruidArmorItem;
import mod.vemerion.wizardstaff.item.WarlockArmorItem;
import mod.vemerion.wizardstaff.item.WizardArmorItem;
import mod.vemerion.wizardstaff.staff.WizardStaffItem;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModItems {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);

	public static final RegistryObject<WizardStaffItem> WIZARD_STAFF = ITEMS.register("wizard_staff",
			() -> new WizardStaffItem(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<WizardStaffItem> NETHER_WIZARD_STAFF = ITEMS.register("nether_wizard_staff",
			() -> new WizardStaffItem(new Item.Properties().stacksTo(1).fireResistant()));
	public static final RegistryObject<WizardArmorItem> WIZARD_HAT = ITEMS.register("wizard_hat",
			() -> new WizardArmorItem(ArmorItem.Type.HELMET));
	public static final RegistryObject<WizardArmorItem> WIZARD_CHESTPLATE = ITEMS.register("wizard_chestplate",
			() -> new WizardArmorItem(ArmorItem.Type.CHESTPLATE));
	public static final RegistryObject<WizardArmorItem> WIZARD_LEGGINGS = ITEMS.register("wizard_leggings",
			() -> new WizardArmorItem(ArmorItem.Type.LEGGINGS));
	public static final RegistryObject<WizardArmorItem> WIZARD_BOOTS = ITEMS.register("wizard_boots",
			() -> new WizardArmorItem(ArmorItem.Type.BOOTS));
	public static final RegistryObject<DruidArmorItem> DRUID_HELMET = ITEMS.register("druid_helmet",
			() -> new DruidArmorItem(ArmorItem.Type.HELMET));
	public static final RegistryObject<DruidArmorItem> DRUID_CHESTPLATE = ITEMS.register("druid_chestplate",
			() -> new DruidArmorItem(ArmorItem.Type.CHESTPLATE));
	public static final RegistryObject<DruidArmorItem> DRUID_LEGGINGS = ITEMS.register("druid_leggings",
			() -> new DruidArmorItem(ArmorItem.Type.LEGGINGS));
	public static final RegistryObject<DruidArmorItem> DRUID_BOOTS = ITEMS.register("druid_boots",
			() -> new DruidArmorItem(ArmorItem.Type.BOOTS));
	public static final RegistryObject<WarlockArmorItem> WARLOCK_HELMET = ITEMS.register("warlock_helmet",
			() -> new WarlockArmorItem(ArmorItem.Type.HELMET));
	public static final RegistryObject<WarlockArmorItem> WARLOCK_CHESTPLATE = ITEMS.register("warlock_chestplate",
			() -> new WarlockArmorItem(ArmorItem.Type.CHESTPLATE));
	public static final RegistryObject<WarlockArmorItem> WARLOCK_LEGGINGS = ITEMS.register("warlock_leggings",
			() -> new WarlockArmorItem(ArmorItem.Type.LEGGINGS));
	public static final RegistryObject<WarlockArmorItem> WARLOCK_BOOTS = ITEMS.register("warlock_boots",
			() -> new WarlockArmorItem(ArmorItem.Type.BOOTS));

	@SubscribeEvent
	public static void onRegisterTabs(CreativeModeTabEvent.Register event) {
		event.registerCreativeModeTab(new ResourceLocation(Main.MODID, "tab"),
				builder -> builder.title(Component.translatable("itemGroup.wizardstaff"))
						.icon(() -> WIZARD_HAT.get().getDefaultInstance()).displayItems((parameters, output) -> {
							for (var item : ITEMS.getEntries())
								output.accept(item.get().getDefaultInstance());
						}));
	}
}
