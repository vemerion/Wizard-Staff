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

	public static final SoundEvent CLOCK_SOUND = null;
	public static final SoundEvent PLOP_SOUND = null;
	public static final SoundEvent PUMPKIN_MAGIC_SOUND = null;
	public static final SoundEvent RAY_SOUND = null;
	public static final SoundEvent SCRIBBLE_SOUND = null;
	public static final SoundEvent WOOSH_SOUND = null;
	public static final SoundEvent BURNING_SOUND = null;
	public static final SoundEvent PORTAL_SOUND = null;
	public static final SoundEvent RADAR_SOUND = null;
	public static final SoundEvent SKELETON_SOUND = null;
	public static final SoundEvent SNIFFLE_SOUND = null;
	public static final SoundEvent WARP_SOUND = null;
	public static final SoundEvent GONG_SOUND = null;
	public static final SoundEvent POOF_SOUND = null;
	public static final SoundEvent TELEPORT_SOUND = null;
	public static final SoundEvent BRICK_SOUND = null;
	public static final SoundEvent CHIRP_SOUND = null;
	public static final SoundEvent FLAP_SOUND = null;
	public static final SoundEvent SPRAY_SOUND = null;
	public static final SoundEvent ANVIL_SOUND = null;
	public static final SoundEvent CLOTH_SOUND = null;
	public static final SoundEvent PAGE_TURN_SOUND = null;
	public static final SoundEvent DEAGE_SOUND = null;
	public static final SoundEvent BUILDING_SOUND = null;

	@SubscribeEvent
	public static void onRegisterSound(RegistryEvent.Register<SoundEvent> event) {
		SoundEvent clock_sound = new SoundEvent(new ResourceLocation(Main.MODID, "clock_sound"));
		event.getRegistry().register(Init.setup(clock_sound, "clock_sound"));
		SoundEvent plop_sound = new SoundEvent(new ResourceLocation(Main.MODID, "plop_sound"));
		event.getRegistry().register(Init.setup(plop_sound, "plop_sound"));
		SoundEvent pumpkin_magic_sound = new SoundEvent(new ResourceLocation(Main.MODID, "pumpkin_magic_sound"));
		event.getRegistry().register(Init.setup(pumpkin_magic_sound, "pumpkin_magic_sound"));
		SoundEvent ray_sound = new SoundEvent(new ResourceLocation(Main.MODID, "ray_sound"));
		event.getRegistry().register(Init.setup(ray_sound, "ray_sound"));
		SoundEvent scribble_sound = new SoundEvent(new ResourceLocation(Main.MODID, "scribble_sound"));
		event.getRegistry().register(Init.setup(scribble_sound, "scribble_sound"));
		SoundEvent woosh_sound = new SoundEvent(new ResourceLocation(Main.MODID, "woosh_sound"));
		event.getRegistry().register(Init.setup(woosh_sound, "woosh_sound"));
		SoundEvent burning_sound = new SoundEvent(new ResourceLocation(Main.MODID, "burning_sound"));
		event.getRegistry().register(Init.setup(burning_sound, "burning_sound"));
		SoundEvent portal_sound = new SoundEvent(new ResourceLocation(Main.MODID, "portal_sound"));
		event.getRegistry().register(Init.setup(portal_sound, "portal_sound"));
		SoundEvent radar_sound = new SoundEvent(new ResourceLocation(Main.MODID, "radar_sound"));
		event.getRegistry().register(Init.setup(radar_sound, "radar_sound"));
		SoundEvent skeleton_sound = new SoundEvent(new ResourceLocation(Main.MODID, "skeleton_sound"));
		event.getRegistry().register(Init.setup(skeleton_sound, "skeleton_sound"));
		SoundEvent sniffle_sound = new SoundEvent(new ResourceLocation(Main.MODID, "sniffle_sound"));
		event.getRegistry().register(Init.setup(sniffle_sound, "sniffle_sound"));
		SoundEvent warp_sound = new SoundEvent(new ResourceLocation(Main.MODID, "warp_sound"));
		event.getRegistry().register(Init.setup(warp_sound, "warp_sound"));
		SoundEvent gong_sound = new SoundEvent(new ResourceLocation(Main.MODID, "gong_sound"));
		event.getRegistry().register(Init.setup(gong_sound, "gong_sound"));
		SoundEvent poof_sound = new SoundEvent(new ResourceLocation(Main.MODID, "poof_sound"));
		event.getRegistry().register(Init.setup(poof_sound, "poof_sound"));
		SoundEvent teleport_sound = new SoundEvent(new ResourceLocation(Main.MODID, "teleport_sound"));
		event.getRegistry().register(Init.setup(teleport_sound, "teleport_sound"));
		SoundEvent brick_sound = new SoundEvent(new ResourceLocation(Main.MODID, "brick_sound"));
		event.getRegistry().register(Init.setup(brick_sound, "brick_sound"));
		SoundEvent chirp_sound = new SoundEvent(new ResourceLocation(Main.MODID, "chirp_sound"));
		event.getRegistry().register(Init.setup(chirp_sound, "chirp_sound"));
		SoundEvent flap_sound = new SoundEvent(new ResourceLocation(Main.MODID, "flap_sound"));
		event.getRegistry().register(Init.setup(flap_sound, "flap_sound"));
		SoundEvent spray_sound = new SoundEvent(new ResourceLocation(Main.MODID, "spray_sound"));
		event.getRegistry().register(Init.setup(spray_sound, "spray_sound"));
		SoundEvent anvil_sound = new SoundEvent(new ResourceLocation(Main.MODID, "anvil_sound"));
		event.getRegistry().register(Init.setup(anvil_sound, "anvil_sound"));
		SoundEvent cloth_sound = new SoundEvent(new ResourceLocation(Main.MODID, "cloth_sound"));
		event.getRegistry().register(Init.setup(cloth_sound, "cloth_sound"));
		SoundEvent page_turn_sound = new SoundEvent(new ResourceLocation(Main.MODID, "page_turn_sound"));
		event.getRegistry().register(Init.setup(page_turn_sound, "page_turn_sound"));
		SoundEvent deage_sound = new SoundEvent(new ResourceLocation(Main.MODID, "deage_sound"));
		event.getRegistry().register(Init.setup(deage_sound, "deage_sound"));
		SoundEvent building_sound = new SoundEvent(new ResourceLocation(Main.MODID, "building_sound"));
		event.getRegistry().register(Init.setup(building_sound, "building_sound"));
	}

}
