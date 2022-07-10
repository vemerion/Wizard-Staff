package mod.vemerion.wizardstaff.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mod.vemerion.wizardstaff.Magic.CreateEntityMagic;
import mod.vemerion.wizardstaff.Magic.Magic;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class MagicVexEntity extends Vex implements ICasted {

	private UUID caster;

	public MagicVexEntity(EntityType<? extends MagicVexEntity> type, Level level) {
		super(type, level);
		this.xpReward = 0;
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor levelIn, DifficultyInstance difficultyIn,
			MobSpawnType reason, SpawnGroupData spawnDataIn, CompoundTag dataTag) {
		setLeftHanded(getRandom().nextBoolean());
		var tools = generateTools();
		setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(tools.get(getRandom().nextInt(tools.size()))));
		setDropChance(EquipmentSlot.OFFHAND, 0);
		return spawnDataIn;
	}

	protected abstract List<Item> generateTools();

	public abstract int lifetime();

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

	private static class MiningGoal extends MoveToBlockGoal {

		private static final int MINING_DURATION = 50;

		private int duration;
		private MagicVexEntity.Mining vex;

		public MiningGoal(MagicVexEntity.Mining vex, double pSpeedModifier, int pSearchRange,
				int pVerticalSearchRange) {
			super(vex, pSpeedModifier, pSearchRange, pVerticalSearchRange);
			this.vex = vex;
		}

		@Override
		public double acceptedDistance() {
			return 2;
		}

		@Override
		protected boolean isValidTarget(LevelReader pLevel, BlockPos pPos) {
			return vex.canMine(pLevel.getBlockState(pPos));
		}

		@Override
		protected int nextStartTick(PathfinderMob pCreature) {
			return 20 * 5 + mob.getRandom().nextInt(20 * 5);
		}

		@Override
		public void tick() {
			super.tick();
			if (isReachedTarget()) {
				if (duration++ > MINING_DURATION)
					mineBlock();
				else {
					if (duration % 7 == 0) {
						mob.playSound(mob.level.getBlockState(blockPos).getSoundType().getHitSound(), 1,
								mob.getVoicePitch());
						mob.swing(InteractionHand.OFF_HAND);
					}
				}
			} else {
				mob.getMoveControl().setWantedPosition(blockPos.getX() + 0.5, blockPos.getY() + 0.5,
						blockPos.getZ() + 0.5, 0.25);
			}
		}

		private void mineBlock() {
			if (isValidTarget(mob.level, blockPos)) {
				mob.level.destroyBlock(blockPos, true);
			}
		}

		@Override
		protected BlockPos getMoveToTarget() {
			return blockPos;
		}

		public void start() {
			super.start();
			duration = 0;
		}
	}

	public static class Attack extends MagicVexEntity {

		private static final ResourceLocation[] MOD_WEAPONS = new ResourceLocation[] {
				new ResourceLocation("twilightforest", "fiery_sword"),
				new ResourceLocation("immersiveengineering", "revolver"),
				new ResourceLocation("iceandfire", "dragonbone_sword") };

		public Attack(EntityType<? extends Attack> type, Level level) {
			super(type, level);
		}

		@Override
		protected List<Item> generateTools() {
			List<Item> weapons = new ArrayList<>();
			weapons.add(Items.IRON_SWORD);
			weapons.add(Items.DIAMOND_HOE);
			weapons.add(Items.WOODEN_PICKAXE);

			for (ResourceLocation rl : MOD_WEAPONS)
				if (ForgeRegistries.ITEMS.containsKey(rl))
					weapons.add(ForgeRegistries.ITEMS.getValue(rl));

			return weapons;
		}

		@Override
		protected void registerGoals() {
			super.registerGoals();

			targetSelector.addGoal(0, new CopyCasterTargetGoal(this));
			targetSelector.addGoal(1, new DefendCasterGoal(this));
		}

		@Override
		public int lifetime() {
			return 20 * 20;
		}

	}

	public static class Mining extends MagicVexEntity {

		private TagKey<Block> minable = Tags.Blocks.ORES;

		public Mining(EntityType<? extends Mining> type, Level level) {
			super(type, level);
		}

		@Override
		protected List<Item> generateTools() {
			List<Item> weapons = new ArrayList<>();
			weapons.add(Items.DIAMOND_PICKAXE);
			weapons.add(Items.GOLDEN_PICKAXE);
			weapons.add(Items.IRON_PICKAXE);
			weapons.add(Items.STONE_PICKAXE);

			return weapons;
		}

		@Override
		protected void registerGoals() {
			super.registerGoals();
			goalSelector.addGoal(1, new MiningGoal(this, 1, 12, 6));
		}

		public boolean canMine(BlockState state) {
			return state.is(minable);
		}

		@Override
		public SpawnGroupData finalizeSpawn(ServerLevelAccessor levelIn, DifficultyInstance difficultyIn,
				MobSpawnType reason, SpawnGroupData spawnDataIn, CompoundTag dataTag) {
			if (spawnDataIn != null && spawnDataIn instanceof CreateEntityMagic.JsonData data
					&& data.json.has("minable")) {
				minable = ForgeRegistries.BLOCKS.tags()
						.createTagKey(new ResourceLocation(GsonHelper.getAsString(data.json, "minable")));
			}

			return super.finalizeSpawn(levelIn, difficultyIn, reason, spawnDataIn, dataTag);
		}

		@Override
		public void readAdditionalSaveData(CompoundTag compound) {
			super.readAdditionalSaveData(compound);

			if (compound.contains("minable"))
				minable = ForgeRegistries.BLOCKS.tags()
						.createTagKey(new ResourceLocation(compound.getString("minable")));
		}

		@Override
		public void addAdditionalSaveData(CompoundTag compound) {
			super.addAdditionalSaveData(compound);

			compound.putString("minable", minable.location().toString());
		}

		@Override
		public int lifetime() {
			return 20 * 40;
		}

	}
}
