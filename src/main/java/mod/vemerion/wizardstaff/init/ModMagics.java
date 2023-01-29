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
import mod.vemerion.wizardstaff.Magic.cavesandcliffs.LightMagic;
import mod.vemerion.wizardstaff.Magic.cavesandcliffs.MiningMagic;
import mod.vemerion.wizardstaff.Magic.cavesandcliffs.MountMagic;
import mod.vemerion.wizardstaff.Magic.cavesandcliffs.PlaceLightRandomMagic;
import mod.vemerion.wizardstaff.Magic.cavesandcliffs.XRayMagic;
import mod.vemerion.wizardstaff.Magic.cavesandcliffs.ZoomMagic;
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
import mod.vemerion.wizardstaff.Magic.wild.DeathTeleportMagic;
import mod.vemerion.wizardstaff.Magic.wild.QuietMagic;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModMagics {
	
	private static final ResourceLocation REGISTRY_NAME = new ResourceLocation(Main.MODID, "magics");

	private static Supplier<IForgeRegistry<MagicType<?>>> registry;
	
	public static final DeferredRegister<MagicType<?>> MAGICS = DeferredRegister.create(REGISTRY_NAME, Main.MODID);

	public static final RegistryObject<MagicType<BlazePowderMagic>> BLAZE_POWDER_MAGIC = MAGICS.register("blaze_powder_magic", () -> new MagicType<>(BlazePowderMagic::new));
	public static final RegistryObject<MagicType<CarvedPumpkinMagic>> CARVED_PUMPKIN_MAGIC = MAGICS.register("carved_pumpkin_magic", () -> new MagicType<>(CarvedPumpkinMagic::new));
	public static final RegistryObject<MagicType<ClockMagic>> CLOCK_MAGIC = MAGICS.register("clock_magic", () -> new MagicType<>(ClockMagic::new));
	public static final RegistryObject<MagicType<EggMagic>> EGG_MAGIC = MAGICS.register("egg_magic", () -> new MagicType<>(EggMagic::new));
	public static final RegistryObject<MagicType<ElytraMagic>> ELYTRA_MAGIC = MAGICS.register("elytra_magic", () -> new MagicType<>(ElytraMagic::new));
	public static final RegistryObject<MagicType<TransformBlockMagic>> TRANSFORM_BLOCK_MAGIC = MAGICS.register("transform_block_magic", () -> new MagicType<>(TransformBlockMagic::new));
	public static final RegistryObject<MagicType<JukeboxMagic>> JUKEBOX_MAGIC = MAGICS.register("jukebox_magic", () -> new MagicType<>(JukeboxMagic::new));
	public static final RegistryObject<MagicType<WizardStaffMagic>> WIZARD_STAFF_MAGIC = MAGICS.register("wizard_staff_magic", () -> new MagicType<>(WizardStaffMagic::new));
	public static final RegistryObject<MagicType<WritableBookMagic>> WRITABLE_BOOK_MAGIC = MAGICS.register("writable_book_magic", () -> new MagicType<>(WritableBookMagic::new));
	public static final RegistryObject<MagicType<ObsidianMagic>> OBSIDIAN_MAGIC = MAGICS.register("obsidian_magic", () -> new MagicType<>(ObsidianMagic::new));
	public static final RegistryObject<MagicType<GlowstoneDustMagic>> GLOWSTONE_DUST_MAGIC = MAGICS.register("glowstone_dust_magic", () -> new MagicType<>(GlowstoneDustMagic::new));
	public static final RegistryObject<MagicType<NetherrackMagic>> NETHERRACK_MAGIC = MAGICS.register("netherrack_magic", () -> new MagicType<>(NetherrackMagic::new));
	public static final RegistryObject<MagicType<ProjectileMagic>> PROJECTILE_MAGIC = MAGICS.register("projectile_magic", () -> new MagicType<>(ProjectileMagic::new));
	public static final RegistryObject<MagicType<GhastTearMagic>> GHAST_TEAR_MAGIC = MAGICS.register("ghast_tear_magic", () -> new MagicType<>(GhastTearMagic::new));
	public static final RegistryObject<MagicType<NetherBrickMagic>> NETHER_BRICK_MAGIC = MAGICS.register("nether_brick_magic", () -> new MagicType<>(NetherBrickMagic::new));
	public static final RegistryObject<MagicType<GoldNuggetMagic>> GOLD_NUGGET_MAGIC = MAGICS.register("gold_nugget_magic", () -> new MagicType<>(GoldNuggetMagic::new));
	public static final RegistryObject<MagicType<LodestoneMagic>> LODESTONE_MAGIC = MAGICS.register("lodestone_magic", () -> new MagicType<>(LodestoneMagic::new));
	public static final RegistryObject<MagicType<TransmutationMagic>> TRANSMUTATION_MAGIC = MAGICS.register("transmutation_magic", () -> new MagicType<>(TransmutationMagic::new));
	public static final RegistryObject<MagicType<BlueDyeMagic>> BLUE_DYE_MAGIC = MAGICS.register("blue_dye_magic", () -> new MagicType<>(BlueDyeMagic::new));
	public static final RegistryObject<MagicType<BricksMagic>> BRICKS_MAGIC = MAGICS.register("bricks_magic", () -> new MagicType<>(BricksMagic::new));
	public static final RegistryObject<MagicType<GrapplingHookMagic>> GRAPPLING_HOOK_MAGIC = MAGICS.register("grappling_hook_magic", () -> new MagicType<>(GrapplingHookMagic::new));
	public static final RegistryObject<MagicType<FeatherMagic>> FEATHER_MAGIC = MAGICS.register("feather_magic", () -> new MagicType<>(FeatherMagic::new));
	public static final RegistryObject<MagicType<MushroomCloudMagic>> MUSHROOM_CLOUD_MAGIC = MAGICS.register("mushroom_cloud_magic", () -> new MagicType<>(MushroomCloudMagic::new));
	public static final RegistryObject<MagicType<ShulkerBulletMagic>> SHULKER_BULLET_MAGIC = MAGICS.register("shulker_bullet_magic", () -> new MagicType<>(ShulkerBulletMagic::new));
	public static final RegistryObject<MagicType<BucketMagic>> BUCKET_MAGIC = MAGICS.register("bucket_magic", () -> new MagicType<>(BucketMagic::new));
	public static final RegistryObject<MagicType<PortableCraftingMagic>> PORTABLE_CRAFTING_MAGIC = MAGICS.register("portable_crafting_magic", () -> new MagicType<>(PortableCraftingMagic::new));
	public static final RegistryObject<MagicType<BookshelfMagic>> BOOKSHELF_MAGIC = MAGICS.register("bookshelf_magic", () -> new MagicType<>(BookshelfMagic::new));
	public static final RegistryObject<MagicType<MapMagic>> MAP_MAGIC = MAGICS.register("map_magic", () -> new MagicType<>(MapMagic::new));
	public static final RegistryObject<MagicType<DeageMagic>> DEAGE_MAGIC = MAGICS.register("deage_magic", () -> new MagicType<>(DeageMagic::new));
	public static final RegistryObject<MagicType<SmeltingMagic>> SMELTING_MAGIC = MAGICS.register("smelting_magic", () -> new MagicType<>(SmeltingMagic::new));
	public static final RegistryObject<MagicType<PillarMagic>> PILLAR_MAGIC = MAGICS.register("pillar_magic", () -> new MagicType<>(PillarMagic::new));
	public static final RegistryObject<MagicType<SurfaceMagic>> SURFACE_MAGIC = MAGICS.register("surface_magic", () -> new MagicType<>(SurfaceMagic::new));
	public static final RegistryObject<MagicType<PotionMagic>> POTION_MAGIC = MAGICS.register("potion_magic", () -> new MagicType<>(PotionMagic::new));
	public static final RegistryObject<MagicType<BuilderMagic>> BUILDER_MAGIC = MAGICS.register("builder_magic", () -> new MagicType<>(BuilderMagic::new));
	public static final RegistryObject<MagicType<RevertPositionMagic>> REVERT_POSITION_MAGIC = MAGICS.register("revert_position_magic", () -> new MagicType<>(RevertPositionMagic::new));
	public static final RegistryObject<MagicType<PushBlockMagic>> PUSH_BLOCK_MAGIC = MAGICS.register("push_block_magic", () -> new MagicType<>(PushBlockMagic::new));
	public static final RegistryObject<MagicType<RemoveFluidMagic>> REMOVE_FLUID_MAGIC = MAGICS.register("remove_fluid_magic", () -> new MagicType<>(RemoveFluidMagic::new));
	public static final RegistryObject<MagicType<PushButtonMagic>> PUSH_BUTTON_MAGIC = MAGICS.register("push_button_magic", () -> new MagicType<>(PushButtonMagic::new));
	public static final RegistryObject<MagicType<NameTagMagic>> NAME_TAG_MAGIC = MAGICS.register("name_tag_magic", () -> new MagicType<>(NameTagMagic::new));
	public static final RegistryObject<MagicType<LocateSpawnMagic>> LOCATE_SPAWN_MAGIC = MAGICS.register("locate_spawn_magic", () -> new MagicType<>(LocateSpawnMagic::new));
	public static final RegistryObject<MagicType<TransformEntityMagic>> TRANSFORM_ENTITY_MAGIC = MAGICS.register("transform_entity_magic", () -> new MagicType<>(TransformEntityMagic::new));
	public static final RegistryObject<MagicType<InventoryMagic>> INVENTORY_MAGIC = MAGICS.register("inventory_magic", () -> new MagicType<>(InventoryMagic::new));
	public static final RegistryObject<MagicType<EnderChestMagic>> ENDER_CHEST_MAGIC = MAGICS.register("ender_chest_magic", () -> new MagicType<>(EnderChestMagic::new));
	public static final RegistryObject<MagicType<DeflectProjectileMagic>> DEFLECT_PROJECTILE_MAGIC = MAGICS.register("deflect_projectile_magic", () -> new MagicType<>(DeflectProjectileMagic::new));
	public static final RegistryObject<MagicType<RepairArmorMagic>> REPAIR_ARMOR_MAGIC = MAGICS.register("repair_armor_magic", () -> new MagicType<>(RepairArmorMagic::new));
	public static final RegistryObject<MagicType<SummonEntityMagic>> SUMMON_ENTITY_MAGIC = MAGICS.register("summon_entity_magic", () -> new MagicType<>(SummonEntityMagic::new));
	public static final RegistryObject<MagicType<MassHarvestMagic>> MASS_HARVEST_MAGIC = MAGICS.register("mass_harvest_magic", () -> new MagicType<>(MassHarvestMagic::new));
	public static final RegistryObject<MagicType<ForceEntityMagic>> FORCE_ENTITY_MAGIC = MAGICS.register("force_entity_magic", () -> new MagicType<>(ForceEntityMagic::new));
	public static final RegistryObject<MagicType<SwapPositionMagic>> SWAP_POSITION_MAGIC = MAGICS.register("swap_position_magic", () -> new MagicType<>(SwapPositionMagic::new));
	public static final RegistryObject<MagicType<SwapHealthFoodMagic>> SWAP_HEALTH_FOOD_MAGIC = MAGICS.register("swap_health_food_magic", () -> new MagicType<>(SwapHealthFoodMagic::new));
	public static final RegistryObject<MagicType<SwapTradeMagic>> SWAP_TRADE_MAGIC = MAGICS.register("swap_trade_magic", () -> new MagicType<>(SwapTradeMagic::new));
	public static final RegistryObject<MagicType<RepairOffhandMagic>> REPAIR_OFFHAND_MAGIC = MAGICS.register("repair_offhand_magic", () -> new MagicType<>(RepairOffhandMagic::new));
	public static final RegistryObject<MagicType<WallClimbMagic>> WALL_CLIMB_MAGIC = MAGICS.register("wall_climb_magic", () -> new MagicType<>(WallClimbMagic::new));
	public static final RegistryObject<MagicType<CobwebMagic>> COBWEB_MAGIC = MAGICS.register("cobweb_magic", () -> new MagicType<>(CobwebMagic::new));
	public static final RegistryObject<MagicType<BeeMagic>> BEE_MAGIC = MAGICS.register("bee_magic", () -> new MagicType<>(BeeMagic::new));
	public static final RegistryObject<MagicType<ShapedCreateEntityMagic>> SHAPED_CREATE_ENTITY_MAGIC = MAGICS.register("shaped_create_entity_magic", () -> new MagicType<>(ShapedCreateEntityMagic::new));
	public static final RegistryObject<MagicType<MiningMagic>> MINING_MAGIC = MAGICS.register("mining_magic", () -> new MagicType<>(MiningMagic::new));
	public static final RegistryObject<MagicType<MountMagic>> MOUNT_MAGIC = MAGICS.register("mount_magic", () -> new MagicType<>(MountMagic::new));
	public static final RegistryObject<MagicType<XRayMagic>> X_RAY_MAGIC = MAGICS.register("x_ray_magic", () -> new MagicType<>(XRayMagic::new));
	public static final RegistryObject<MagicType<LightMagic>> LIGHT_MAGIC = MAGICS.register("light_magic", () -> new MagicType<>(LightMagic::new));
	public static final RegistryObject<MagicType<ZoomMagic>> ZOOM_MAGIC = MAGICS.register("zoom_magic", () -> new MagicType<>(ZoomMagic::new));
	public static final RegistryObject<MagicType<PlaceLightRandomMagic>> PLACE_LIGHT_RANDOM_MAGIC = MAGICS.register("place_light_random_magic", () -> new MagicType<>(PlaceLightRandomMagic::new));
	public static final RegistryObject<MagicType<QuietMagic>> QUIET_MAGIC = MAGICS.register("quiet_magic", () -> new MagicType<>(QuietMagic::new));
	public static final RegistryObject<MagicType<DeathTeleportMagic>> DEATH_TELEPORT_MAGIC = MAGICS.register("death_teleport_magic", () -> new MagicType<>(DeathTeleportMagic::new));
	public static final RegistryObject<MagicType<NoMagic>> NO_MAGIC = MAGICS.register("no_magic", () -> new MagicType<>(NoMagic::new));

	@SubscribeEvent
	public static void onRegisterMagicRegistry(NewRegistryEvent event) {
		registry = event.create(new RegistryBuilder<MagicType<?>>().setName(REGISTRY_NAME));
	}

	public static IForgeRegistry<MagicType<?>> getRegistry() {
		return registry.get();
	}

}
