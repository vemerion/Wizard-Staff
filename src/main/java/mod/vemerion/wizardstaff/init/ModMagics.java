package mod.vemerion.wizardstaff.init;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.NoMagic;
import mod.vemerion.wizardstaff.Magic.fashionupdate.TransmutationMagic;
import mod.vemerion.wizardstaff.Magic.netherupdate.GhastTearMagic;
import mod.vemerion.wizardstaff.Magic.netherupdate.GlowstoneDustMagic;
import mod.vemerion.wizardstaff.Magic.netherupdate.GoldNuggetMagic;
import mod.vemerion.wizardstaff.Magic.netherupdate.LodestoneMagic;
import mod.vemerion.wizardstaff.Magic.netherupdate.NetherBrickMagic;
import mod.vemerion.wizardstaff.Magic.netherupdate.NetherrackMagic;
import mod.vemerion.wizardstaff.Magic.netherupdate.ObsidianMagic;
import mod.vemerion.wizardstaff.Magic.netherupdate.ProjectileMagic;
import mod.vemerion.wizardstaff.Magic.netherupdate.SoulSandMagic;
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
import mod.vemerion.wizardstaff.Magic.suggestions2.PushBlockMagic;
import mod.vemerion.wizardstaff.Magic.suggestions2.RevertPositionMagic;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.RegistryBuilder;

@ObjectHolder(value = Main.MODID)
@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModMagics {

	public static IForgeRegistry<MagicType> REGISTRY;
	
	public static final MagicType BLAZE_POWDER_MAGIC = null;
	public static final MagicType CARVED_PUMPKIN_MAGIC = null;
	public static final MagicType CLOCK_MAGIC = null;
	public static final MagicType EGG_MAGIC = null;
	public static final MagicType ELYTRA_MAGIC = null;
	public static final MagicType TRANSFORM_BLOCK_MAGIC = null;
	public static final MagicType JUKEBOX_MAGIC = null;
	public static final MagicType WIZARD_STAFF_MAGIC = null;
	public static final MagicType WRITABLE_BOOK_MAGIC = null;
	public static final MagicType OBSIDIAN_MAGIC = null;
	public static final MagicType GLOWSTONE_DUST_MAGIC = null;
	public static final MagicType NETHERRACK_MAGIC = null;
	public static final MagicType PROJECTILE_MAGIC = null;
	public static final MagicType GHAST_TEAR_MAGIC = null;
	public static final MagicType NETHER_BRICK_MAGIC = null;
	public static final MagicType SOUL_SAND_MAGIC = null;
	public static final MagicType GOLD_NUGGET_MAGIC = null;
	public static final MagicType LODESTONE_MAGIC = null;
	public static final MagicType TRANSMUTATION_MAGIC = null;
	public static final MagicType BLUE_DYE_MAGIC = null;
	public static final MagicType BRICKS_MAGIC = null;
	public static final MagicType GRAPPLING_HOOK_MAGIC = null;
	public static final MagicType FEATHER_MAGIC = null;
	public static final MagicType MUSHROOM_CLOUD_MAGIC = null;
	public static final MagicType SHULKER_BULLET_MAGIC = null;
	public static final MagicType BUCKET_MAGIC = null;
	public static final MagicType PORTABLE_CRAFTING_MAGIC = null;
	public static final MagicType BOOKSHELF_MAGIC = null;
	public static final MagicType MAP_MAGIC = null;
	public static final MagicType DEAGE_MAGIC = null;
	public static final MagicType SMELTING_MAGIC = null;
	public static final MagicType PILLAR_MAGIC = null;
	public static final MagicType SURFACE_MAGIC = null;
	public static final MagicType POTION_MAGIC = null;
	public static final MagicType BUILDER_MAGIC = null;
	public static final MagicType REVERT_POSITION_MAGIC = null;
	public static final MagicType PUSH_BLOCK_MAGIC = null;
	public static final MagicType NO_MAGIC = null;
	
	@SubscribeEvent
	public static void onRegisterMagic(RegistryEvent.Register<MagicType> event) {
		IForgeRegistry<MagicType> reg = event.getRegistry();
		reg.register(Init.setup(new MagicType(BlazePowderMagic::new), "blaze_powder_magic"));
		reg.register(Init.setup(new MagicType(CarvedPumpkinMagic::new), "carved_pumpkin_magic"));
		reg.register(Init.setup(new MagicType(ClockMagic::new), "clock_magic"));
		reg.register(Init.setup(new MagicType(EggMagic::new), "egg_magic"));
		reg.register(Init.setup(new MagicType(ElytraMagic::new), "elytra_magic"));
		reg.register(Init.setup(new MagicType(TransformBlockMagic::new), "transform_block_magic"));
		reg.register(Init.setup(new MagicType(JukeboxMagic::new), "jukebox_magic"));
		reg.register(Init.setup(new MagicType(WizardStaffMagic::new), "wizard_staff_magic"));
		reg.register(Init.setup(new MagicType(WritableBookMagic::new), "writable_book_magic"));
		reg.register(Init.setup(new MagicType(ObsidianMagic::new), "obsidian_magic"));
		reg.register(Init.setup(new MagicType(GlowstoneDustMagic::new), "glowstone_dust_magic"));
		reg.register(Init.setup(new MagicType(NetherrackMagic::new), "netherrack_magic"));
		reg.register(Init.setup(new MagicType(ProjectileMagic::new), "projectile_magic"));
		reg.register(Init.setup(new MagicType(GhastTearMagic::new), "ghast_tear_magic"));
		reg.register(Init.setup(new MagicType(NetherBrickMagic::new), "nether_brick_magic"));
		reg.register(Init.setup(new MagicType(SoulSandMagic::new), "soul_sand_magic"));
		reg.register(Init.setup(new MagicType(GoldNuggetMagic::new), "gold_nugget_magic"));
		reg.register(Init.setup(new MagicType(LodestoneMagic::new), "lodestone_magic"));
		reg.register(Init.setup(new MagicType(TransmutationMagic::new), "transmutation_magic"));
		reg.register(Init.setup(new MagicType(BlueDyeMagic::new), "blue_dye_magic"));
		reg.register(Init.setup(new MagicType(BricksMagic::new), "bricks_magic"));
		reg.register(Init.setup(new MagicType(GrapplingHookMagic::new), "grappling_hook_magic"));
		reg.register(Init.setup(new MagicType(FeatherMagic::new), "feather_magic"));
		reg.register(Init.setup(new MagicType(MushroomCloudMagic::new), "mushroom_cloud_magic"));
		reg.register(Init.setup(new MagicType(ShulkerBulletMagic::new), "shulker_bullet_magic"));
		reg.register(Init.setup(new MagicType(BucketMagic::new), "bucket_magic"));
		reg.register(Init.setup(new MagicType(PortableCraftingMagic::new), "portable_crafting_magic"));
		reg.register(Init.setup(new MagicType(BookshelfMagic::new), "bookshelf_magic"));
		reg.register(Init.setup(new MagicType(MapMagic::new), "map_magic"));
		reg.register(Init.setup(new MagicType(DeageMagic::new), "deage_magic"));
		reg.register(Init.setup(new MagicType(SmeltingMagic::new), "smelting_magic"));
		reg.register(Init.setup(new MagicType(PillarMagic::new), "pillar_magic"));
		reg.register(Init.setup(new MagicType(SurfaceMagic::new), "surface_magic"));
		reg.register(Init.setup(new MagicType(PotionMagic::new), "potion_magic"));
		reg.register(Init.setup(new MagicType(BuilderMagic::new), "builder_magic"));
		reg.register(Init.setup(new MagicType(RevertPositionMagic::new), "revert_position_magic"));
		reg.register(Init.setup(new MagicType(PushBlockMagic::new), "push_block_magic"));
		reg.register(Init.setup(new MagicType(NoMagic::new), "no_magic"));
	}

	@SubscribeEvent
	public static void onRegisterMagicRegistry(RegistryEvent.NewRegistry event) {
		REGISTRY = new RegistryBuilder<MagicType>().setName(new ResourceLocation(Main.MODID, "magics"))
				.setType(MagicType.class).create();
	}

}
