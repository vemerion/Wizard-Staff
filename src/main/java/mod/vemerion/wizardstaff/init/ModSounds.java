package mod.vemerion.wizardstaff.init;

import mod.vemerion.wizardstaff.Main;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(value = Main.MODID)
@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModSounds {

	public static final SoundEvent CLOCK = null;
	public static final SoundEvent PLOP = null;
	public static final SoundEvent PUMPKIN_MAGIC = null;
	public static final SoundEvent RAY = null;
	public static final SoundEvent SCRIBBLE = null;
	public static final SoundEvent WOOSH = null;
	public static final SoundEvent BURNING = null;
	public static final SoundEvent PORTAL = null;
	public static final SoundEvent RADAR = null;
	public static final SoundEvent SKELETON = null;
	public static final SoundEvent SNIFFLE = null;
	public static final SoundEvent WARP = null;
	public static final SoundEvent GONG = null;
	public static final SoundEvent POOF = null;
	public static final SoundEvent TELEPORT = null;
	public static final SoundEvent BRICK = null;
	public static final SoundEvent CHIRP = null;
	public static final SoundEvent FLAP = null;
	public static final SoundEvent SPRAY = null;
	public static final SoundEvent ANVIL = null;
	public static final SoundEvent CLOTH = null;
	public static final SoundEvent PAGE_TURN = null;
	public static final SoundEvent DEAGE = null;
	public static final SoundEvent BUILDING = null;

	@SubscribeEvent
	public static void onRegisterSound(RegistryEvent.Register<SoundEvent> event) {
		SoundEvent clock = new SoundEvent(new ResourceLocation(Main.MODID, "clock"));
		event.getRegistry().register(Init.setup(clock, "clock"));
		SoundEvent plop = new SoundEvent(new ResourceLocation(Main.MODID, "plop"));
		event.getRegistry().register(Init.setup(plop, "plop"));
		SoundEvent pumpkin_magic = new SoundEvent(new ResourceLocation(Main.MODID, "pumpkin_magic"));
		event.getRegistry().register(Init.setup(pumpkin_magic, "pumpkin_magic"));
		SoundEvent ray = new SoundEvent(new ResourceLocation(Main.MODID, "ray"));
		event.getRegistry().register(Init.setup(ray, "ray"));
		SoundEvent scribble = new SoundEvent(new ResourceLocation(Main.MODID, "scribble"));
		event.getRegistry().register(Init.setup(scribble, "scribble"));
		SoundEvent woosh = new SoundEvent(new ResourceLocation(Main.MODID, "woosh"));
		event.getRegistry().register(Init.setup(woosh, "woosh"));
		SoundEvent burning = new SoundEvent(new ResourceLocation(Main.MODID, "burning"));
		event.getRegistry().register(Init.setup(burning, "burning"));
		SoundEvent portal = new SoundEvent(new ResourceLocation(Main.MODID, "portal"));
		event.getRegistry().register(Init.setup(portal, "portal"));
		SoundEvent radar = new SoundEvent(new ResourceLocation(Main.MODID, "radar"));
		event.getRegistry().register(Init.setup(radar, "radar"));
		SoundEvent skeleton = new SoundEvent(new ResourceLocation(Main.MODID, "skeleton"));
		event.getRegistry().register(Init.setup(skeleton, "skeleton"));
		SoundEvent sniffle = new SoundEvent(new ResourceLocation(Main.MODID, "sniffle"));
		event.getRegistry().register(Init.setup(sniffle, "sniffle"));
		SoundEvent warp = new SoundEvent(new ResourceLocation(Main.MODID, "warp"));
		event.getRegistry().register(Init.setup(warp, "warp"));
		SoundEvent gong = new SoundEvent(new ResourceLocation(Main.MODID, "gong"));
		event.getRegistry().register(Init.setup(gong, "gong"));
		SoundEvent poof = new SoundEvent(new ResourceLocation(Main.MODID, "poof"));
		event.getRegistry().register(Init.setup(poof, "poof"));
		SoundEvent teleport = new SoundEvent(new ResourceLocation(Main.MODID, "teleport"));
		event.getRegistry().register(Init.setup(teleport, "teleport"));
		SoundEvent brick = new SoundEvent(new ResourceLocation(Main.MODID, "brick"));
		event.getRegistry().register(Init.setup(brick, "brick"));
		SoundEvent chirp = new SoundEvent(new ResourceLocation(Main.MODID, "chirp"));
		event.getRegistry().register(Init.setup(chirp, "chirp"));
		SoundEvent flap = new SoundEvent(new ResourceLocation(Main.MODID, "flap"));
		event.getRegistry().register(Init.setup(flap, "flap"));
		SoundEvent spray = new SoundEvent(new ResourceLocation(Main.MODID, "spray"));
		event.getRegistry().register(Init.setup(spray, "spray"));
		SoundEvent anvil = new SoundEvent(new ResourceLocation(Main.MODID, "anvil"));
		event.getRegistry().register(Init.setup(anvil, "anvil"));
		SoundEvent cloth = new SoundEvent(new ResourceLocation(Main.MODID, "cloth"));
		event.getRegistry().register(Init.setup(cloth, "cloth"));
		SoundEvent page_turn = new SoundEvent(new ResourceLocation(Main.MODID, "page_turn"));
		event.getRegistry().register(Init.setup(page_turn, "page_turn"));
		SoundEvent deage = new SoundEvent(new ResourceLocation(Main.MODID, "deage"));
		event.getRegistry().register(Init.setup(deage, "deage"));
		SoundEvent building = new SoundEvent(new ResourceLocation(Main.MODID, "building"));
		event.getRegistry().register(Init.setup(building, "building"));
	}

}
