package mod.vemerion.wizardstaff;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import mod.vemerion.wizardstaff.init.ModBlocks;
import mod.vemerion.wizardstaff.init.ModContainers;
import mod.vemerion.wizardstaff.init.ModEntities;
import mod.vemerion.wizardstaff.init.ModItems;
import mod.vemerion.wizardstaff.init.ModMagics;
import mod.vemerion.wizardstaff.init.ModParticles;
import mod.vemerion.wizardstaff.init.ModSounds;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Main.MODID)
public class Main {
	public static final String MODID = "wizardstaff";

	public static final Logger LOGGER = LogUtils.getLogger();
	
	public Main() {
		var bus = FMLJavaModLoadingContext.get().getModEventBus();
		ModBlocks.BLOCKS.register(bus);
		ModContainers.MENUS.register(bus);
		ModEntities.ENTITIES.register(bus);
		ModItems.ITEMS.register(bus);
		ModMagics.MAGICS.register(bus);
		ModParticles.PARTICLES.register(bus);
		ModSounds.SOUNDS.register(bus);
	}
}
