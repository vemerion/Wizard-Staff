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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
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

	public CreateEntityMagic(MagicType<? extends CreateEntityMagic> type) {
		super(type);
	}

	public CreateEntityMagic setAdditionalParams(EntityType<?> entity, SoundEvent sound) {
		this.entity = entity;
		this.sound = sound;
		return this;
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		MagicUtil.write(json, entity, "entity");
		MagicUtil.write(json, sound, "sound");
	}

	@Override
	protected void readAdditional(JsonObject json) {
		entity = MagicUtil.read(json, ForgeRegistries.ENTITIES, "entity");
		sound = MagicUtil.read(json, ForgeRegistries.SOUND_EVENTS, "sound");
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
				if (e instanceof Mob)
					((Mob) e).finalizeSpawn((ServerLevelAccessor) level,
							level.getCurrentDifficultyAt(player.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);

				if (e instanceof MagicVexEntity) {
					MagicVexEntity vex = (MagicVexEntity) e;
					vex.setCaster(player);
					vex.setLimitedLife(20 * 20);
				}

				if (e instanceof EvokerFangs)
					((EvokerFangs) e).setOwner(player);
				
				if (e instanceof ICasted)
					((ICasted) e).setCaster(player);

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

}
