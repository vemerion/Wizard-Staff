package mod.vemerion.wizardstaff.Magic;

import java.util.function.Function;

import net.minecraftforge.registries.ForgeRegistryEntry;

public class MagicType extends ForgeRegistryEntry<MagicType> {

	private Function<MagicType, Magic> factory;

	public MagicType(Function<MagicType, Magic> factory) {
		this.factory = factory;
	}

	public Magic create() {
		return factory.apply(this);
	}
}
