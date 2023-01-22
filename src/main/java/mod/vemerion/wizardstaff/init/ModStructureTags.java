package mod.vemerion.wizardstaff.init;

import mod.vemerion.wizardstaff.Main;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.structure.Structure;

public class ModStructureTags {
	public static final TagKey<Structure> NETHER_FORTRESS = TagKey.create(Registries.STRUCTURE,
			new ResourceLocation(Main.MODID, "nether_fortress"));
}
