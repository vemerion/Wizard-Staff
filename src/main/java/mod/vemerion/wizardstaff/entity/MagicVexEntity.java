package mod.vemerion.wizardstaff.entity;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.UUID;

import mod.vemerion.wizardstaff.Main;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.monster.VexEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

// FIXME: The damage scales based on difficulty when attacking players, which is bad
public class MagicVexEntity extends VexEntity implements ICasted {

	private static Field goals;

	private UUID caster;

	public MagicVexEntity(EntityType<? extends MagicVexEntity> type, World world) {
		super(type, world);
		this.experienceValue = 0;
	}
	
	@Override
	public UUID getCasterUUID() {
		return caster;
	}

	@Override
	public void setCasterUUID(UUID id) {
		caster = id;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		goalSelector.addGoal(0, new UpdateOrigin(this));

		clearTargetSelector();

		targetSelector.addGoal(0, new CopyCasterTargetGoal(this));
		targetSelector.addGoal(1, new DefendCasterGoal(this));
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		if (compound.contains("shooter"))
			loadCaster(compound.getCompound("shooter"));
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.put("shooter", saveCaster());
	}

	// Use reflection to clear the targetSelector, to prevent Vex from attacking
	// player
	private void clearTargetSelector() {
		if (goals == null)
			goals = ObfuscationReflectionHelper.findField(GoalSelector.class, "field_220892_d");

		try {
			((Set<?>) goals.get(targetSelector)).clear();
		} catch (IllegalArgumentException | IllegalAccessException e) {
			Main.LOGGER.warn("Failed to clear targetSelector: " + e.getMessage());
		}
	}

	private abstract static class SetAttackTargetGoal extends TargetGoal {

		private MagicVexEntity vex;
		private int timestamp;

		public SetAttackTargetGoal(MagicVexEntity vex) {
			super(vex, false);
			this.vex = vex;
		}

		public boolean shouldExecute() {
			PlayerEntity player = vex.getCaster(vex.world);
			if (player == null)
				return false;

			if (timestamp == getTimestamp(player))
				return false;

			Entity target = getTarget(player);
			if (target == null || (target instanceof MagicVexEntity && ((MagicVexEntity) target).getCaster(vex.world) == player))
				return false;

			return true;
		}

		public void startExecuting() {
			PlayerEntity player = vex.getCaster(vex.world);
			if (player == null)
				return;
			vex.setAttackTarget(getTarget(player));
			timestamp = getTimestamp(player);
			super.startExecuting();
		}

		protected abstract LivingEntity getTarget(PlayerEntity player);

		protected abstract int getTimestamp(PlayerEntity player);
	}

	private static class CopyCasterTargetGoal extends SetAttackTargetGoal {

		public CopyCasterTargetGoal(MagicVexEntity vex) {
			super(vex);
		}

		@Override
		protected LivingEntity getTarget(PlayerEntity player) {
			return player.getLastAttackedEntity();
		}

		@Override
		protected int getTimestamp(PlayerEntity player) {
			return player.getLastAttackedEntityTime();
		}
	}

	private static class DefendCasterGoal extends SetAttackTargetGoal {

		public DefendCasterGoal(MagicVexEntity vex) {
			super(vex);
		}

		@Override
		protected LivingEntity getTarget(PlayerEntity player) {
			return player.getRevengeTarget();
		}

		@Override
		protected int getTimestamp(PlayerEntity player) {
			return player.getRevengeTimer();
		}
	}

	public static class UpdateOrigin extends Goal {

		private MagicVexEntity vex;

		public UpdateOrigin(MagicVexEntity vex) {
			this.vex = vex;
		}

		@Override
		public boolean shouldExecute() {
			PlayerEntity player = vex.getCaster(vex.world);
			if (player == null)
				return false;
			return vex.getRNG().nextInt(30) == 1 && player.getDistanceSq(vex) < 2000;
		}

		@Override
		public void startExecuting() {
			PlayerEntity player = vex.getCaster(vex.world);
			if (player == null)
				return;
			vex.setBoundOrigin(player.getPosition());
		}

	}
}
