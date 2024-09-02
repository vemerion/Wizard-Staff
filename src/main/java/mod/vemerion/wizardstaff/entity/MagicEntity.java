package mod.vemerion.wizardstaff.entity;

import java.util.UUID;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public abstract class MagicEntity extends Entity implements ICasted {

	private UUID casterId;

	public MagicEntity(EntityType<? extends MagicEntity> entityTypeIn, Level level) {
		super(entityTypeIn, level);
	}

	@Override
	public UUID getCasterUUID() {
		return casterId;
	}

	@Override
	public void setCasterUUID(UUID id) {
		casterId = id;
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag compound) {
		if (compound.contains("caster"))
			loadCaster(compound.getCompound("caster"));
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag compound) {
		compound.put("caster", saveCaster());
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
