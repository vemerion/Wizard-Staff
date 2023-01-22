package mod.vemerion.wizardstaff.Magic;

import java.util.function.Function;

import net.minecraft.resources.ResourceLocation;

public class MagicType<T extends Magic> {

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
