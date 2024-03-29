package mod.vemerion.wizardstaff.Magic;

import java.util.function.Function;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class MagicType<T extends Magic> extends ForgeRegistryEntry<MagicType<?>> {

	private Function<MagicType<T>, T> factory;

	public MagicType(Function<MagicType<T>, T> factory) {
		this.factory = factory;
	}

	public T create(ResourceLocation name) {
		T magic = factory.apply(this);
		magic.setName(name);
		return magic;
	}
	
}
