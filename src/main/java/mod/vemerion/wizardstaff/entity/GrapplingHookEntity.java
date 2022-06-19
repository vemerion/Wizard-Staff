package mod.vemerion.wizardstaff.entity;

import java.util.UUID;

import mod.vemerion.wizardstaff.capability.Wizard;
import mod.vemerion.wizardstaff.init.ModEntities;
import mod.vemerion.wizardstaff.staff.WizardStaffItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;

public class GrapplingHookEntity extends MagicEntity implements IEntityAdditionalSpawnData {

	public GrapplingHookEntity(EntityType<? extends GrapplingHookEntity> entityTypeIn, Level level) {
		super(entityTypeIn, level);
		this.setNoGravity(true);
		this.noCulling = true;
	}

	public GrapplingHookEntity(Level level, Player shooter) {
		this(ModEntities.GRAPPLING_HOOK, level);
		this.setCaster(shooter);
	}

	@Override
	public void tick() {
		super.tick();

		if (!level.isClientSide) {
			Player shooter = getCaster(level);
			if (shooter == null || this.distanceToSqr(shooter) > 100
					|| !(shooter.getUseItem().getItem() instanceof WizardStaffItem))
				discard();
		}

		if (getCaster(level) != null && isAlive()) {
			Wizard.getWizardOptional(getCaster(level)).ifPresent(wizard -> wizard.setGrapplingHook(this));
		}
	}

	public boolean shouldRenderAtSqrDistance(double distance) {
		return distance < 4096;
	}

	@Override
	protected void defineSynchedData() {

	}

	@Override
	public void writeSpawnData(FriendlyByteBuf buffer) {
		UUID caster = getCasterUUID();
		if (caster == null) {
			buffer.writeBoolean(false);
		} else {
			buffer.writeBoolean(true);
			buffer.writeUUID(caster);
		}
	}

	@Override
	public void readSpawnData(FriendlyByteBuf additionalData) {
		if (additionalData.readBoolean())
			setCasterUUID(additionalData.readUUID());
	}
}
