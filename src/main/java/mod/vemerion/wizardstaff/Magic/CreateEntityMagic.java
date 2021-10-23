package mod.vemerion.wizardstaff.Magic;

import java.util.List;

import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.entity.MagicVexEntity;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EvokerFangsEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class CreateEntityMagic extends Magic {

	protected EntityType<?> entity;

	public CreateEntityMagic(MagicType<? extends CreateEntityMagic> type) {
		super(type);
	}

	public CreateEntityMagic setAdditionalParams(EntityType<?> entity) {
		this.entity = entity;
		return this;
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		MagicUtil.write(json, entity, "entity");
	}

	@Override
	protected void readAdditional(JsonObject json) {
		entity = MagicUtil.read(json, ForgeRegistries.ENTITIES, "entity");
	}

	@Override
	protected void encodeAdditional(PacketBuffer buffer) {
		MagicUtil.encode(buffer, entity);
	}

	@Override
	protected void decodeAdditional(PacketBuffer buffer) {
		entity = MagicUtil.decode(buffer, ForgeRegistries.ENTITIES);
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
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.CROSSBOW;
	}

	@Override
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		if (!world.isRemote) {
			List<Entity> entities = createEntities(world, player, staff);
			for (Entity e : entities) {
				if (e instanceof MobEntity)
					((MobEntity) e).onInitialSpawn((IServerWorld) world,
							world.getDifficultyForLocation(player.getPosition()), SpawnReason.MOB_SUMMONED, null, null);

				if (e instanceof MagicVexEntity) {
					MagicVexEntity vex = (MagicVexEntity) e;
					vex.setCaster(player);
					vex.setLimitedLife(20 * 20);
				}

				if (e instanceof EvokerFangsEntity)
					((EvokerFangsEntity) e).setCaster(player);

				world.addEntity(e);
			}

			if (entities.isEmpty()) {
				playSoundServer(world, player, ModSounds.POOF, 1, soundPitch(player));
			} else {
				playSoundServer(world, player, ModSounds.BELL, 1, soundPitch(player));
				cost(player);
			}
		}
		return super.magicFinish(world, player, staff);
	}

	protected abstract List<Entity> createEntities(World world, PlayerEntity player, ItemStack staff);

}
