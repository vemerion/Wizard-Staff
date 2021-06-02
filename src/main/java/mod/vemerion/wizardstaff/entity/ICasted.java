package mod.vemerion.wizardstaff.entity;

import java.util.UUID;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;

public interface ICasted {
	UUID getCasterUUID();

	void setCasterUUID(UUID id);

	default void setCaster(PlayerEntity caster) {
		setCasterUUID(caster.getUniqueID());
	}

	default PlayerEntity getCaster(World world) {
		UUID id = getCasterUUID();
		if (id == null)
			return null;
		return world.getPlayerByUuid(id);
	}

	default CompoundNBT saveCaster() {
		CompoundNBT nbt = new CompoundNBT();
		UUID id = getCasterUUID();
		if (id != null)
			nbt.putUniqueId("id", id);
		return nbt;
	}

	default void loadCaster(CompoundNBT nbt) {
		if (nbt.hasUniqueId("id"))
			setCasterUUID(nbt.getUniqueId("id"));
	}
}
