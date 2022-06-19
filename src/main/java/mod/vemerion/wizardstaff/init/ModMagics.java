package mod.vemerion.wizardstaff.init;

import java.util.function.Supplier;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.NoMagic;
import mod.vemerion.wizardstaff.Magic.bugfix.BeeMagic;
import mod.vemerion.wizardstaff.Magic.bugfix.CobwebMagic;
import mod.vemerion.wizardstaff.Magic.bugfix.RepairOffhandMagic;
import mod.vemerion.wizardstaff.Magic.bugfix.ShapedCreateEntityMagic;
import mod.vemerion.wizardstaff.Magic.bugfix.WallClimbMagic;
import mod.vemerion.wizardstaff.Magic.fashionupdate.TransmutationMagic;
import mod.vemerion.wizardstaff.Magic.netherupdate.GhastTearMagic;
import mod.vemerion.wizardstaff.Magic.netherupdate.GlowstoneDustMagic;
import mod.vemerion.wizardstaff.Magic.netherupdate.GoldNuggetMagic;
import mod.vemerion.wizardstaff.Magic.netherupdate.LodestoneMagic;
import mod.vemerion.wizardstaff.Magic.netherupdate.NetherBrickMagic;
import mod.vemerion.wizardstaff.Magic.netherupdate.NetherrackMagic;
import mod.vemerion.wizardstaff.Magic.netherupdate.ObsidianMagic;
import mod.vemerion.wizardstaff.Magic.netherupdate.ProjectileMagic;
import mod.vemerion.wizardstaff.Magic.original.BlazePowderMagic;
import mod.vemerion.wizardstaff.Magic.original.CarvedPumpkinMagic;
import mod.vemerion.wizardstaff.Magic.original.ClockMagic;
import mod.vemerion.wizardstaff.Magic.original.EggMagic;
import mod.vemerion.wizardstaff.Magic.original.ElytraMagic;
import mod.vemerion.wizardstaff.Magic.original.JukeboxMagic;
import mod.vemerion.wizardstaff.Magic.original.TransformBlockMagic;
import mod.vemerion.wizardstaff.Magic.original.WizardStaffMagic;
import mod.vemerion.wizardstaff.Magic.original.WritableBookMagic;
import mod.vemerion.wizardstaff.Magic.restructuring.BuilderMagic;
import mod.vemerion.wizardstaff.Magic.restructuring.PillarMagic;
import mod.vemerion.wizardstaff.Magic.restructuring.PotionMagic;
import mod.vemerion.wizardstaff.Magic.restructuring.SmeltingMagic;
import mod.vemerion.wizardstaff.Magic.restructuring.SurfaceMagic;
import mod.vemerion.wizardstaff.Magic.spellbookupdate.BookshelfMagic;
import mod.vemerion.wizardstaff.Magic.spellbookupdate.DeageMagic;
import mod.vemerion.wizardstaff.Magic.spellbookupdate.MapMagic;
import mod.vemerion.wizardstaff.Magic.spellbookupdate.PortableCraftingMagic;
import mod.vemerion.wizardstaff.Magic.suggestions.BlueDyeMagic;
import mod.vemerion.wizardstaff.Magic.suggestions.BricksMagic;
import mod.vemerion.wizardstaff.Magic.suggestions.BucketMagic;
import mod.vemerion.wizardstaff.Magic.suggestions.FeatherMagic;
import mod.vemerion.wizardstaff.Magic.suggestions.GrapplingHookMagic;
import mod.vemerion.wizardstaff.Magic.suggestions.MushroomCloudMagic;
import mod.vemerion.wizardstaff.Magic.suggestions.ShulkerBulletMagic;
import mod.vemerion.wizardstaff.Magic.suggestions2.DeflectProjectileMagic;
import mod.vemerion.wizardstaff.Magic.suggestions2.EnderChestMagic;
import mod.vemerion.wizardstaff.Magic.suggestions2.InventoryMagic;
import mod.vemerion.wizardstaff.Magic.suggestions2.LocateSpawnMagic;
import mod.vemerion.wizardstaff.Magic.suggestions2.NameTagMagic;
import mod.vemerion.wizardstaff.Magic.suggestions2.PushBlockMagic;
import mod.vemerion.wizardstaff.Magic.suggestions2.PushButtonMagic;
import mod.vemerion.wizardstaff.Magic.suggestions2.RemoveFluidMagic;
import mod.vemerion.wizardstaff.Magic.suggestions2.RepairArmorMagic;
import mod.vemerion.wizardstaff.Magic.suggestions2.RevertPositionMagic;
import mod.vemerion.wizardstaff.Magic.suggestions2.SummonEntityMagic;
import mod.vemerion.wizardstaff.Magic.suggestions2.TransformEntityMagic;
import mod.vemerion.wizardstaff.Magic.swap.ForceEntityMagic;
import mod.vemerion.wizardstaff.Magic.swap.MassHarvestMagic;
import mod.vemerion.wizardstaff.Magic.swap.SwapHealthFoodMagic;
import mod.vemerion.wizardstaff.Magic.swap.SwapPositionMagic;
import mod.vemerion.wizardstaff.Magic.swap.SwapTradeMagic;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.RegistryBuilder;

@ObjectHolder(value = Main.MODID)
@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModMagics {

	private static Supplier<IForgeRegistry<MagicType<?>>> registry;

	public static final MagicType<BlazePowderMagic> BLAZE_POWDER_MAGIC = null;
	public static final MagicType<CarvedPumpkinMagic> CARVED_PUMPKIN_MAGIC = null;
	public static final MagicType<ClockMagic> CLOCK_MAGIC = null;
	public static final MagicType<EggMagic> EGG_MAGIC = null;
	public static final MagicType<ElytraMagic> ELYTRA_MAGIC = null;
	public static final MagicType<TransformBlockMagic> TRANSFORM_BLOCK_MAGIC = null;
	public static final MagicType<JukeboxMagic> JUKEBOX_MAGIC = null;
	public static final MagicType<WizardStaffMagic> WIZARD_STAFF_MAGIC = null;
	public static final MagicType<WritableBookMagic> WRITABLE_BOOK_MAGIC = null;
	public static final MagicType<ObsidianMagic> OBSIDIAN_MAGIC = null;
	public static final MagicType<GlowstoneDustMagic> GLOWSTONE_DUST_MAGIC = null;
	public static final MagicType<NetherrackMagic> NETHERRACK_MAGIC = null;
	public static final MagicType<ProjectileMagic> PROJECTILE_MAGIC = null;
	public static final MagicType<GhastTearMagic> GHAST_TEAR_MAGIC = null;
	public static final MagicType<NetherBrickMagic> NETHER_BRICK_MAGIC = null;
	public static final MagicType<GoldNuggetMagic> GOLD_NUGGET_MAGIC = null;
	public static final MagicType<LodestoneMagic> LODESTONE_MAGIC = null;
	public static final MagicType<TransmutationMagic> TRANSMUTATION_MAGIC = null;
	public static final MagicType<BlueDyeMagic> BLUE_DYE_MAGIC = null;
	public static final MagicType<BricksMagic> BRICKS_MAGIC = null;
	public static final MagicType<GrapplingHookMagic> GRAPPLING_HOOK_MAGIC = null;
	public static final MagicType<FeatherMagic> FEATHER_MAGIC = null;
	public static final MagicType<MushroomCloudMagic> MUSHROOM_CLOUD_MAGIC = null;
	public static final MagicType<ShulkerBulletMagic> SHULKER_BULLET_MAGIC = null;
	public static final MagicType<BucketMagic> BUCKET_MAGIC = null;
	public static final MagicType<PortableCraftingMagic> PORTABLE_CRAFTING_MAGIC = null;
	public static final MagicType<BookshelfMagic> BOOKSHELF_MAGIC = null;
	public static final MagicType<MapMagic> MAP_MAGIC = null;
	public static final MagicType<DeageMagic> DEAGE_MAGIC = null;
	public static final MagicType<SmeltingMagic> SMELTING_MAGIC = null;
	public static final MagicType<PillarMagic> PILLAR_MAGIC = null;
	public static final MagicType<SurfaceMagic> SURFACE_MAGIC = null;
	public static final MagicType<PotionMagic> POTION_MAGIC = null;
	public static final MagicType<BuilderMagic> BUILDER_MAGIC = null;
	public static final MagicType<RevertPositionMagic> REVERT_POSITION_MAGIC = null;
	public static final MagicType<PushBlockMagic> PUSH_BLOCK_MAGIC = null;
	public static final MagicType<RemoveFluidMagic> REMOVE_FLUID_MAGIC = null;
	public static final MagicType<PushButtonMagic> PUSH_BUTTON_MAGIC = null;
	public static final MagicType<NameTagMagic> NAME_TAG_MAGIC = null;
	public static final MagicType<LocateSpawnMagic> LOCATE_SPAWN_MAGIC = null;
	public static final MagicType<TransformEntityMagic> TRANSFORM_ENTITY_MAGIC = null;
	public static final MagicType<InventoryMagic> INVENTORY_MAGIC = null;
	public static final MagicType<EnderChestMagic> ENDER_CHEST_MAGIC = null;
	public static final MagicType<DeflectProjectileMagic> DEFLECT_PROJECTILE_MAGIC = null;
	public static final MagicType<RepairArmorMagic> REPAIR_ARMOR_MAGIC = null;
	public static final MagicType<SummonEntityMagic> SUMMON_ENTITY_MAGIC = null;
	public static final MagicType<MassHarvestMagic> MASS_HARVEST_MAGIC = null;
	public static final MagicType<ForceEntityMagic> FORCE_ENTITY_MAGIC = null;
	public static final MagicType<SwapPositionMagic> SWAP_POSITION_MAGIC = null;
	public static final MagicType<SwapHealthFoodMagic> SWAP_HEALTH_FOOD_MAGIC = null;
	public static final MagicType<SwapTradeMagic> SWAP_TRADE_MAGIC = null;
	public static final MagicType<RepairOffhandMagic> REPAIR_OFFHAND_MAGIC = null;
	public static final MagicType<WallClimbMagic> WALL_CLIMB_MAGIC = null;
	public static final MagicType<CobwebMagic> COBWEB_MAGIC = null;
	public static final MagicType<BeeMagic> BEE_MAGIC = null;
	public static final MagicType<ShapedCreateEntityMagic> SHAPED_CREATE_ENTITY_MAGIC = null;
	public static final MagicType<NoMagic> NO_MAGIC = null;

	@SubscribeEvent
	public static void onRegisterMagic(RegistryEvent.Register<MagicType<?>> event) {
		IForgeRegistry<MagicType<?>> reg = event.getRegistry();
		reg.register(Init.setup(new MagicType<>(BlazePowderMagic::new), "blaze_powder_magic"));
		reg.register(Init.setup(new MagicType<>(CarvedPumpkinMagic::new), "carved_pumpkin_magic"));
		reg.register(Init.setup(new MagicType<>(ClockMagic::new), "clock_magic"));
		reg.register(Init.setup(new MagicType<>(EggMagic::new), "egg_magic"));
		reg.register(Init.setup(new MagicType<>(ElytraMagic::new), "elytra_magic"));
		reg.register(Init.setup(new MagicType<>(TransformBlockMagic::new), "transform_block_magic"));
		reg.register(Init.setup(new MagicType<>(JukeboxMagic::new), "jukebox_magic"));
		reg.register(Init.setup(new MagicType<>(WizardStaffMagic::new), "wizard_staff_magic"));
		reg.register(Init.setup(new MagicType<>(WritableBookMagic::new), "writable_book_magic"));
		reg.register(Init.setup(new MagicType<>(ObsidianMagic::new), "obsidian_magic"));
		reg.register(Init.setup(new MagicType<>(GlowstoneDustMagic::new), "glowstone_dust_magic"));
		reg.register(Init.setup(new MagicType<>(NetherrackMagic::new), "netherrack_magic"));
		reg.register(Init.setup(new MagicType<>(ProjectileMagic::new), "projectile_magic"));
		reg.register(Init.setup(new MagicType<>(GhastTearMagic::new), "ghast_tear_magic"));
		reg.register(Init.setup(new MagicType<>(NetherBrickMagic::new), "nether_brick_magic"));
		reg.register(Init.setup(new MagicType<>(GoldNuggetMagic::new), "gold_nugget_magic"));
		reg.register(Init.setup(new MagicType<>(LodestoneMagic::new), "lodestone_magic"));
		reg.register(Init.setup(new MagicType<>(TransmutationMagic::new), "transmutation_magic"));
		reg.register(Init.setup(new MagicType<>(BlueDyeMagic::new), "blue_dye_magic"));
		reg.register(Init.setup(new MagicType<>(BricksMagic::new), "bricks_magic"));
		reg.register(Init.setup(new MagicType<>(GrapplingHookMagic::new), "grappling_hook_magic"));
		reg.register(Init.setup(new MagicType<>(FeatherMagic::new), "feather_magic"));
		reg.register(Init.setup(new MagicType<>(MushroomCloudMagic::new), "mushroom_cloud_magic"));
		reg.register(Init.setup(new MagicType<>(ShulkerBulletMagic::new), "shulker_bullet_magic"));
		reg.register(Init.setup(new MagicType<>(BucketMagic::new), "bucket_magic"));
		reg.register(Init.setup(new MagicType<>(PortableCraftingMagic::new), "portable_crafting_magic"));
		reg.register(Init.setup(new MagicType<>(BookshelfMagic::new), "bookshelf_magic"));
		reg.register(Init.setup(new MagicType<>(MapMagic::new), "map_magic"));
		reg.register(Init.setup(new MagicType<>(DeageMagic::new), "deage_magic"));
		reg.register(Init.setup(new MagicType<>(SmeltingMagic::new), "smelting_magic"));
		reg.register(Init.setup(new MagicType<>(PillarMagic::new), "pillar_magic"));
		reg.register(Init.setup(new MagicType<>(SurfaceMagic::new), "surface_magic"));
		reg.register(Init.setup(new MagicType<>(PotionMagic::new), "potion_magic"));
		reg.register(Init.setup(new MagicType<>(BuilderMagic::new), "builder_magic"));
		reg.register(Init.setup(new MagicType<>(RevertPositionMagic::new), "revert_position_magic"));
		reg.register(Init.setup(new MagicType<>(PushBlockMagic::new), "push_block_magic"));
		reg.register(Init.setup(new MagicType<>(RemoveFluidMagic::new), "remove_fluid_magic"));
		reg.register(Init.setup(new MagicType<>(PushButtonMagic::new), "push_button_magic"));
		reg.register(Init.setup(new MagicType<>(NameTagMagic::new), "name_tag_magic"));
		reg.register(Init.setup(new MagicType<>(LocateSpawnMagic::new), "locate_spawn_magic"));
		reg.register(Init.setup(new MagicType<>(TransformEntityMagic::new), "transform_entity_magic"));
		reg.register(Init.setup(new MagicType<>(InventoryMagic::new), "inventory_magic"));
		reg.register(Init.setup(new MagicType<>(EnderChestMagic::new), "ender_chest_magic"));
		reg.register(Init.setup(new MagicType<>(DeflectProjectileMagic::new), "deflect_projectile_magic"));
		reg.register(Init.setup(new MagicType<>(RepairArmorMagic::new), "repair_armor_magic"));
		reg.register(Init.setup(new MagicType<>(SummonEntityMagic::new), "summon_entity_magic"));
		reg.register(Init.setup(new MagicType<>(MassHarvestMagic::new), "mass_harvest_magic"));
		reg.register(Init.setup(new MagicType<>(ForceEntityMagic::new), "force_entity_magic"));
		reg.register(Init.setup(new MagicType<>(SwapPositionMagic::new), "swap_position_magic"));
		reg.register(Init.setup(new MagicType<>(SwapHealthFoodMagic::new), "swap_health_food_magic"));
		reg.register(Init.setup(new MagicType<>(SwapTradeMagic::new), "swap_trade_magic"));
		reg.register(Init.setup(new MagicType<>(RepairOffhandMagic::new), "repair_offhand_magic"));
		reg.register(Init.setup(new MagicType<>(WallClimbMagic::new), "wall_climb_magic"));
		reg.register(Init.setup(new MagicType<>(CobwebMagic::new), "cobweb_magic"));
		reg.register(Init.setup(new MagicType<>(BeeMagic::new), "bee_magic"));
		reg.register(Init.setup(new MagicType<>(ShapedCreateEntityMagic::new), "shaped_create_entity_magic"));
		reg.register(Init.setup(new MagicType<>(NoMagic::new), "no_magic"));
	}

	@SuppressWarnings("unchecked")
	@SubscribeEvent
	public static void onRegisterMagicRegistry(NewRegistryEvent event) {
		registry = event.create(new RegistryBuilder<MagicType<?>>().setName(new ResourceLocation(Main.MODID, "magics"))
				.setType((Class<MagicType<?>>) (Class<?>) MagicType.class));
	}
	
	public static IForgeRegistry<MagicType<?>> getRegistry() {
		return registry.get();
	}

}
