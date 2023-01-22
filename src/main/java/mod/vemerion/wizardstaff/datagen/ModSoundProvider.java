package mod.vemerion.wizardstaff.datagen;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.init.ModSounds;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

public class ModSoundProvider extends SoundDefinitionsProvider {

	protected ModSoundProvider(PackOutput packOutput, ExistingFileHelper helper) {
		super(packOutput, Main.MODID, helper);
	}

	@Override
	public void registerSounds() {
		addWithSameName(ModSounds.ANVIL.get());
		addWithSameName(ModSounds.BELL.get());
		addWithSameName(ModSounds.BRICK.get());
		addWithSameName(ModSounds.BUILDING.get());
		addWithSameName(ModSounds.BURNING.get());
		addWithSameName(ModSounds.CHIRP.get());
		addWithSameName(ModSounds.CLOCK.get());
		addWithSameName(ModSounds.CLOTH.get());
		addWithSameName(ModSounds.DEAGE.get());
		addWithSameName(ModSounds.EVAPORATE.get());
		addWithSameName(ModSounds.FLAP.get());
		addWithSameName(ModSounds.GONG.get());
		addWithSameName(ModSounds.HEARTBEAT.get());
		addWithSameName(ModSounds.IMPACT.get());
		addWithSameName(ModSounds.MAGNET.get());
		addWithSameName(ModSounds.PAGE_TURN.get());
		addWithSameName(ModSounds.PLOP.get());
		addWithSameName(ModSounds.POOF.get());
		addWithSameName(ModSounds.PORTAL.get());
		addWithSameName(ModSounds.PUMPKIN_MAGIC.get());
		addWithSameName(ModSounds.PUSH.get());
		addWithSameName(ModSounds.RADAR.get());
		addWithSameName(ModSounds.RAY.get());
		addWithSameName(ModSounds.REVERT.get());
		addWithSameName(ModSounds.SCRIBBLE.get());
		addWithSameName(ModSounds.SKELETON.get());
		addWithSameName(ModSounds.SNIFFLE.get());
		addWithSameName(ModSounds.SPRAY.get());
		addWithSameName(ModSounds.SWAP.get());
		addWithSameName(ModSounds.TELEPORT.get());
		addWithSameName(ModSounds.TRANSFORM.get());
		addWithSameName(ModSounds.WARP.get());
		addWithSameName(ModSounds.WOOSH.get());
	}

	private void addWithSameName(SoundEvent sound) {
		add(sound, definition().with(sound(sound.getLocation())));
	}
}
