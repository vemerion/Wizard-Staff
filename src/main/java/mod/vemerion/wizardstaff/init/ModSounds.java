package mod.vemerion.wizardstaff.init;

import mod.vemerion.wizardstaff.Main;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
	
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Main.MODID);

	public static final RegistryObject<SoundEvent> CLOCK = SOUNDS.register("clock", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Main.MODID, "clock")));
	public static final RegistryObject<SoundEvent> PLOP = SOUNDS.register("plop", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Main.MODID, "plop")));
	public static final RegistryObject<SoundEvent> PUMPKIN_MAGIC = SOUNDS.register("pumpkin_magic", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Main.MODID, "pumpkin_magic")));
	public static final RegistryObject<SoundEvent> RAY = SOUNDS.register("ray", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Main.MODID, "ray")));
	public static final RegistryObject<SoundEvent> SCRIBBLE = SOUNDS.register("scribble", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Main.MODID, "scribble")));
	public static final RegistryObject<SoundEvent> WOOSH = SOUNDS.register("woosh", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Main.MODID, "woosh")));
	public static final RegistryObject<SoundEvent> BURNING = SOUNDS.register("burning", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Main.MODID, "burning")));
	public static final RegistryObject<SoundEvent> PORTAL = SOUNDS.register("portal", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Main.MODID, "portal")));
	public static final RegistryObject<SoundEvent> RADAR = SOUNDS.register("radar", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Main.MODID, "radar")));
	public static final RegistryObject<SoundEvent> SKELETON = SOUNDS.register("skeleton", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Main.MODID, "skeleton")));
	public static final RegistryObject<SoundEvent> SNIFFLE = SOUNDS.register("sniffle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Main.MODID, "sniffle")));
	public static final RegistryObject<SoundEvent> WARP = SOUNDS.register("warp", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Main.MODID, "warp")));
	public static final RegistryObject<SoundEvent> GONG = SOUNDS.register("gong", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Main.MODID, "gong")));
	public static final RegistryObject<SoundEvent> POOF = SOUNDS.register("poof", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Main.MODID, "poof")));
	public static final RegistryObject<SoundEvent> TELEPORT = SOUNDS.register("teleport", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Main.MODID, "teleport")));
	public static final RegistryObject<SoundEvent> BRICK = SOUNDS.register("brick", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Main.MODID, "brick")));
	public static final RegistryObject<SoundEvent> CHIRP = SOUNDS.register("chirp", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Main.MODID, "chirp")));
	public static final RegistryObject<SoundEvent> FLAP = SOUNDS.register("flap", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Main.MODID, "flap")));
	public static final RegistryObject<SoundEvent> SPRAY = SOUNDS.register("spray", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Main.MODID, "spray")));
	public static final RegistryObject<SoundEvent> ANVIL = SOUNDS.register("anvil", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Main.MODID, "anvil")));
	public static final RegistryObject<SoundEvent> CLOTH = SOUNDS.register("cloth", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Main.MODID, "cloth")));
	public static final RegistryObject<SoundEvent> PAGE_TURN = SOUNDS.register("page_turn", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Main.MODID, "page_turn")));
	public static final RegistryObject<SoundEvent> DEAGE = SOUNDS.register("deage", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Main.MODID, "deage")));
	public static final RegistryObject<SoundEvent> BUILDING = SOUNDS.register("building", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Main.MODID, "building")));
	public static final RegistryObject<SoundEvent> EVAPORATE = SOUNDS.register("evaporate", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Main.MODID, "evaporate")));
	public static final RegistryObject<SoundEvent> IMPACT = SOUNDS.register("impact", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Main.MODID, "impact")));
	public static final RegistryObject<SoundEvent> PUSH = SOUNDS.register("push", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Main.MODID, "push")));
	public static final RegistryObject<SoundEvent> REVERT = SOUNDS.register("revert", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Main.MODID, "revert")));
	public static final RegistryObject<SoundEvent> TRANSFORM = SOUNDS.register("transform", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Main.MODID, "transform")));
	public static final RegistryObject<SoundEvent> BELL = SOUNDS.register("bell", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Main.MODID, "bell")));
	public static final RegistryObject<SoundEvent> SWAP = SOUNDS.register("swap", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Main.MODID, "swap")));
	public static final RegistryObject<SoundEvent> MAGNET = SOUNDS.register("magnet", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Main.MODID, "magnet")));
	public static final RegistryObject<SoundEvent> HEARTBEAT = SOUNDS.register("heartbeat", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Main.MODID, "heartbeat")));
}
