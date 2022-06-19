package mod.vemerion.wizardstaff.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mod.vemerion.wizardstaff.Magic.Magic;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class MagicVexEntity extends Vex implements ICasted {

	private static final ResourceLocation[] MOD_WEAPONS = new ResourceLocation[] {
			new ResourceLocation("twilightforest", "fiery_sword"),
			new ResourceLocation("immersiveengineering", "revolver"),
			new ResourceLocation("iceandfire", "dragonbone_sword") };

	private UUID caster;

	public MagicVexEntity(EntityType<? extends MagicVexEntity> type, Level level) {
		super(type, level);
		this.xpReward = 0;
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor levelIn, DifficultyInstance difficultyIn,
			MobSpawnType reason, SpawnGroupData spawnDataIn, CompoundTag dataTag) {
		setLeftHanded(getRandom().nextBoolean());
		giveWeapon();
		return spawnDataIn;
	}

	private void giveWeapon() {
		List<Item> weapons = new ArrayList<>();
		weapons.add(Items.IRON_SWORD);
		weapons.add(Items.DIAMOND_HOE);
		weapons.add(Items.WOODEN_PICKAXE);

		for (ResourceLocation rl : MOD_WEAPONS)
			if (ForgeRegistries.ITEMS.containsKey(rl))
				weapons.add(ForgeRegistries.ITEMS.getValue(rl));

		setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(weapons.get(getRandom().nextInt(weapons.size()))));
		setDropChance(EquipmentSlot.OFFHAND, 0);
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
	protected boolean shouldDespawnInPeaceful() {
		return false;
	}

	@Override
	public boolean doHurtTarget(Entity entityIn) {
		Player caster = getCaster(level);
		float damage = (float) getAttributeValue(Attributes.ATTACK_DAMAGE);
		DamageSource source = caster == null ? Magic.magicDamage() : Magic.magicDamage(this, caster);
		return entityIn.hurt(source, damage);
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
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("shooter"))
			loadCaster(compound.getCompound("shooter"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.put("shooter", saveCaster());
	}

	// Clear the targetSelector, to prevent Vex from attacking player
	private void clearTargetSelector() {
		targetSelector.getAvailableGoals().clear();
	}

	private abstract static class SetAttackTargetGoal extends TargetGoal {

		private MagicVexEntity vex;
		private int timestamp;

		public SetAttackTargetGoal(MagicVexEntity vex) {
			super(vex, false);
			this.vex = vex;
		}

		public boolean canUse() {
			Player player = vex.getCaster(vex.level);
			if (player == null)
				return false;

			if (timestamp == getTimestamp(player))
				return false;

			Entity target = getTarget(player);
			if (target == null
					|| (target instanceof MagicVexEntity && ((MagicVexEntity) target).getCaster(vex.level) == player))
				return false;

			return true;
		}

		public void start() {
			Player player = vex.getCaster(vex.level);
			if (player == null)
				return;
			vex.setTarget(getTarget(player));
			timestamp = getTimestamp(player);
			super.start();
		}

		protected abstract LivingEntity getTarget(Player player);

		protected abstract int getTimestamp(Player player);
	}

	private static class CopyCasterTargetGoal extends SetAttackTargetGoal {

		public CopyCasterTargetGoal(MagicVexEntity vex) {
			super(vex);
		}

		@Override
		protected LivingEntity getTarget(Player player) {
			return player.getLastHurtMob();
		}

		@Override
		protected int getTimestamp(Player player) {
			return player.getLastHurtMobTimestamp();
		}
	}

	private static class DefendCasterGoal extends SetAttackTargetGoal {

		public DefendCasterGoal(MagicVexEntity vex) {
			super(vex);
		}

		@Override
		protected LivingEntity getTarget(Player player) {
			return player.getLastHurtByMob();
		}

		@Override
		protected int getTimestamp(Player player) {
			return player.getLastHurtByMobTimestamp();
		}
	}

	public static class UpdateOrigin extends Goal {

		private MagicVexEntity vex;

		public UpdateOrigin(MagicVexEntity vex) {
			this.vex = vex;
		}

		@Override
		public boolean canUse() {
			Player player = vex.getCaster(vex.level);
			if (player == null)
				return false;
			return vex.getRandom().nextInt(30) == 1 && player.distanceToSqr(vex) < 2000;
		}

		@Override
		public void start() {
			Player player = vex.getCaster(vex.level);
			if (player == null)
				return;
			vex.setBoundOrigin(player.blockPosition());
		}

	}
}
