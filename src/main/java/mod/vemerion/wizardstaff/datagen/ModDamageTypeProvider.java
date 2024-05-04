package mod.vemerion.wizardstaff.datagen;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.init.ModDamageTypes;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.damagesource.DamageType;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

public class ModDamageTypeProvider extends DatapackBuiltinEntriesProvider {

	public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder().add(Registries.DAMAGE_TYPE,
			ModDamageTypeProvider::bootstrap);

	public ModDamageTypeProvider(PackOutput output, CompletableFuture<Provider> provider) {
		super(output, provider, BUILDER, Set.of(Main.MODID));
	}

	private static void bootstrap(BootstapContext<DamageType> context) {
		context.register(ModDamageTypes.MAGIC, new DamageType(Main.MODID + ".magic", 0));
	}

}
