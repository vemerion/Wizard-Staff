package mod.vemerion.wizardstaff.entity;

import java.util.UUID;

import mod.vemerion.wizardstaff.capability.Wizard;
import mod.vemerion.wizardstaff.init.ModEntities;
import mod.vemerion.wizardstaff.staff.WizardStaffItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class GrapplingHookEntity extends MagicEntity implements IEntityAdditionalSpawnData {

	public GrapplingHookEntity(EntityType<? extends GrapplingHookEntity> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
		this.setNoGravity(true);
		this.ignoreFrustumCheck = true;
	}

	public GrapplingHookEntity(World worldIn, PlayerEntity shooter) {
		this(ModEntities.GRAPPLING_HOOK, worldIn);
		this.setCaster(shooter);
	}

	@Override
	public void tick() {
		super.tick();

		if (!world.isRemote) {
			PlayerEntity shooter = getCaster(world);
			if (shooter == null || this.getDistanceSq(shooter) > 100
					|| !(shooter.getActiveItemStack().getItem() instanceof WizardStaffItem))
				remove();
		}

		if (getCaster(world) != null && isAlive()) {
			Wizard.getWizardOptional(getCaster(world)).ifPresent(wizard -> wizard.setGrapplingHook(this));
		}
	}

	public boolean isInRangeToRenderDist(double distance) {
		return distance < 4096;
	}

	@Override
	protected void registerData() {

	}

	@Override
	public void writeSpawnData(PacketBuffer buffer) {
		UUID caster = getCasterUUID();
		if (caster == null) {
			buffer.writeBoolean(false);
		} else {
			buffer.writeBoolean(true);
			buffer.writeUniqueId(caster);
		}
	}

	@Override
	public void readSpawnData(PacketBuffer additionalData) {
		if (additionalData.readBoolean())
			setCasterUUID(additionalData.readUniqueId());
	}
}
