package mod.vemerion.wizardstaff.entity;

import java.util.UUID;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public interface ICasted {
	UUID getCasterUUID();

	void setCasterUUID(UUID id);

	default void setCaster(Player caster) {
		setCasterUUID(caster.getUUID());
	}

	default Player getCaster(Level level) {
		UUID id = getCasterUUID();
		if (id == null)
			return null;
		return level.getPlayerByUUID(id);
	}

	default CompoundTag saveCaster() {
		CompoundTag nbt = new CompoundTag();
		UUID id = getCasterUUID();
		if (id != null)
			nbt.putUUID("id", id);
		return nbt;
	}

	default void loadCaster(CompoundTag nbt) {
		if (nbt.hasUUID("id"))
			setCasterUUID(nbt.getUUID("id"));
	}
}
