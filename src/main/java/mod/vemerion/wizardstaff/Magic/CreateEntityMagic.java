package mod.vemerion.wizardstaff.Magic;

import java.util.List;

import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.entity.ICasted;
import mod.vemerion.wizardstaff.entity.MagicVexEntity;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.EvokerFangs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class CreateEntityMagic extends Magic {

	protected EntityType<?> entity;
	protected SoundEvent sound;
	private JsonObject additionalData;

	public CreateEntityMagic(MagicType<? extends CreateEntityMagic> type) {
		super(type);
	}
	
	public CreateEntityMagic setAdditionalParams(EntityType<?> entity, SoundEvent sound) {
		return setAdditionalParams(entity, sound, null);
	}

	public CreateEntityMagic setAdditionalParams(EntityType<?> entity, SoundEvent sound, JsonObject additionalData) {
		this.entity = entity;
		this.sound = sound;
		this.additionalData = additionalData;
		return this;
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		MagicUtil.write(json, entity, "entity");
		MagicUtil.write(json, sound, "sound");
		json.add("additional_data", additionalData);
	}

	@Override
	protected void readAdditional(JsonObject json) {
		entity = MagicUtil.read(json, ForgeRegistries.ENTITIES, "entity");
		sound = MagicUtil.read(json, ForgeRegistries.SOUND_EVENTS, "sound");
		additionalData = GsonHelper.getAsJsonObject(json, "additional_data", null);
	}

	@Override
	protected void encodeAdditional(FriendlyByteBuf buffer) {
		MagicUtil.encode(buffer, entity);
	}

	@Override
	protected void decodeAdditional(FriendlyByteBuf buffer) {
		entity = MagicUtil.decode(buffer);
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::buildup;
	}

	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::buildup;
	}

	@Override
	public UseAnim getUseAnim(ItemStack stack) {
		return UseAnim.CROSSBOW;
	}

	@Override
	public ItemStack magicFinish(Level level, Player player, ItemStack staff) {
		if (!level.isClientSide) {
			List<Entity> entities = createEntities(level, player, staff);
			for (Entity e : entities) {
				if (e instanceof Mob mob)
					mob.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(player.blockPosition()),
							MobSpawnType.MOB_SUMMONED, additionalData == null ? null : new JsonData(additionalData),
							null);

				if (e instanceof MagicVexEntity vex) {
					vex.setCaster(player);
					vex.setLimitedLife(vex.lifetime());
				}

				if (e instanceof EvokerFangs fang)
					fang.setOwner(player);

				if (e instanceof ICasted casted)
					casted.setCaster(player);

				level.addFreshEntity(e);
			}

			if (entities.isEmpty()) {
				playSoundServer(level, player, ModSounds.POOF, 1, soundPitch(player));
			} else {
				playSoundServer(level, player, sound, 1, soundPitch(player));
				cost(player);
			}
		}
		return super.magicFinish(level, player, staff);
	}

	protected abstract List<Entity> createEntities(Level level, Player player, ItemStack staff);

	public static class JsonData implements SpawnGroupData {
		public JsonObject json;

		public JsonData(JsonObject json) {
			this.json = json;
		}
	}

}
