package mod.vemerion.wizardstaff.datagen;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.init.ModSounds;
import net.minecraft.data.DataGenerator;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

public class ModSoundProvider extends SoundDefinitionsProvider {

	protected ModSoundProvider(DataGenerator generator, ExistingFileHelper helper) {
		super(generator, Main.MODID, helper);
	}

	@Override
	public void registerSounds() {
		addWithSameName(ModSounds.ANVIL);
		addWithSameName(ModSounds.BELL);
		addWithSameName(ModSounds.BRICK);
		addWithSameName(ModSounds.BUILDING);
		addWithSameName(ModSounds.BURNING);
		addWithSameName(ModSounds.CHIRP);
		addWithSameName(ModSounds.CLOCK);
		addWithSameName(ModSounds.CLOTH);
		addWithSameName(ModSounds.DEAGE);
		addWithSameName(ModSounds.EVAPORATE);
		addWithSameName(ModSounds.FLAP);
		addWithSameName(ModSounds.GONG);
		addWithSameName(ModSounds.HEARTBEAT);
		addWithSameName(ModSounds.IMPACT);
		addWithSameName(ModSounds.MAGNET);
		addWithSameName(ModSounds.PAGE_TURN);
		addWithSameName(ModSounds.PLOP);
		addWithSameName(ModSounds.POOF);
		addWithSameName(ModSounds.PORTAL);
		addWithSameName(ModSounds.PUMPKIN_MAGIC);
		addWithSameName(ModSounds.PUSH);
		addWithSameName(ModSounds.RADAR);
		addWithSameName(ModSounds.RAY);
		addWithSameName(ModSounds.REVERT);
		addWithSameName(ModSounds.SCRIBBLE);
		addWithSameName(ModSounds.SKELETON);
		addWithSameName(ModSounds.SNIFFLE);
		addWithSameName(ModSounds.SPRAY);
		addWithSameName(ModSounds.SWAP);
		addWithSameName(ModSounds.TELEPORT);
		addWithSameName(ModSounds.TRANSFORM);
		addWithSameName(ModSounds.WARP);
		addWithSameName(ModSounds.WOOSH);
	}

	private void addWithSameName(SoundEvent sound) {
		add(sound, definition().with(sound(sound.getLocation())));
	}
}
