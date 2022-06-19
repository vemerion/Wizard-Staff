package mod.vemerion.wizardstaff.init;

import mod.vemerion.wizardstaff.Main;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;

public class ModConfiguredStructureTags {
	public static final TagKey<ConfiguredStructureFeature<?, ?>> NETHER_FORTRESS = TagKey.create(
			Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation(Main.MODID, "nether_fortress"));
}
