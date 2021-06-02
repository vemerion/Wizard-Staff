package mod.vemerion.wizardstaff.entity;

import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class MagicEntity extends Entity implements ICasted {

	private UUID casterId;

	public MagicEntity(EntityType<? extends MagicEntity> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
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
	protected void readAdditional(CompoundNBT compound) {
		if (compound.contains("caster"))
			loadCaster(compound.getCompound("caster"));
	}

	@Override
	protected void writeAdditional(CompoundNBT compound) {
		compound.put("caster", saveCaster());
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
