package mod.vemerion.wizardstaff.Magic.cavesandcliffs;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.RayMagic;
import mod.vemerion.wizardstaff.Magic.RegistryMatch;
import mod.vemerion.wizardstaff.capability.Wizard;
import mod.vemerion.wizardstaff.particle.MagicDustParticleData;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class MountMagic extends RayMagic {

	private static Method jumpFromGround = ObfuscationReflectionHelper.findMethod(LivingEntity.class, "m_6135_");

	private RegistryMatch<EntityType<?>> match;

	public MountMagic(MagicType<? extends MountMagic> type) {
		super(type);
	}

	public MountMagic setAdditionalParams(RegistryMatch<EntityType<?>> match) {
		this.match = match;
		return this;
	}

	@Override
	protected Object[] getNameArgs() {
		return new Object[] { match.getName() };
	}

	@Override
	protected Object[] getDescrArgs() {
		return getNameArgs();
	}

	@Override
	protected void readAdditional(JsonObject json) {
		match = RegistryMatch.read(ForgeRegistries.ENTITIES, GsonHelper.getAsJsonObject(json, "match"));
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		json.add("match", match.write());
	}

	@Override
	protected void decodeAdditional(FriendlyByteBuf buffer) {
		match = RegistryMatch.decode(ForgeRegistries.ENTITIES, buffer);
	}

	@Override
	protected void encodeAdditional(FriendlyByteBuf buffer) {
		match.encode(buffer);
	}

	@Override
	protected ParticleOptions generateParticle(Level level, Player player, ItemStack staff, int count) {
		Random rand = player.getRandom();
		return new MagicDustParticleData(0.8f + rand.nextFloat() * 0.2f, 0.8f + rand.nextFloat() * 0.2f,
				0.8f + rand.nextFloat() * 0.2f, 1);
	}

	private boolean isValidEntity(Entity target) {
		return target != null && match.test(target.getType());
	}

	@Override
	protected void hitEntity(Level level, Player player, Entity target) {
		if (isValidEntity(target))
			player.startRiding(target);
	}

	@Override
	public void magicTick(Level level, Player player, ItemStack staff, int count) {
		if (isValidEntity(player.getVehicle())) {
			cost(player);
			controlMount(player);
		} else {
			super.magicTick(level, player, staff, count);
		}
	}

	private void controlMount(Player player) {
		if (player.getVehicle() instanceof LivingEntity living) {
			var movement = Vec3.directionFromRotation(0, player.getYRot()).scale(0.4).add(0,
					living.getDeltaMovement().y, 0);
			living.setDeltaMovement(movement);
			living.setYRot(player.getYRot());
			if (player.getXRot() < -30) {
				Wizard.getWizardOptional(player).ifPresent(w -> {
					if (w.mountJump()) {
						try {
							jumpFromGround.invoke(living);
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							Main.LOGGER.warn("Could not jump with goat, error: " + e);
						}
					}
				});
			}
		}
	}
}
