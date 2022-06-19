package mod.vemerion.wizardstaff.Magic.original;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.MagicUtil;
import mod.vemerion.wizardstaff.Magic.RayMagic;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.particle.MagicDustParticleData;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.Level;

public class EggMagic extends RayMagic {

	private Set<ResourceLocation> blacklist;

	public EggMagic(MagicType<? extends EggMagic> type) {
		super(type);
	}

	public EggMagic setAdditionalParams(Set<ResourceLocation> blacklist) {
		this.blacklist = blacklist;
		return this;
	}

	@Override
	protected void readAdditional(JsonObject json) {
		blacklist = MagicUtil.readColl(json, "blacklist",
				e -> new ResourceLocation(GsonHelper.convertToString(e, "entity name")), new HashSet<>());
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		MagicUtil.writeColl(json, "blacklist", blacklist, r -> new JsonPrimitive(r.toString()));
	}

	@Override
	public void hitEntity(Level level, Player player, Entity target) {
		if (blacklist.contains(target.getType().getRegistryName())) {
			target.playSound(ModSounds.POOF, 1, soundPitch(player));
			return;
		}

		SpawnEggItem egg = null;
		for (SpawnEggItem e : SpawnEggItem.eggs()) {
			if (e.getType(null) == target.getType()) {
				egg = e;
				break;
			}
		}
		if (egg != null) {
			target.playSound(ModSounds.PLOP, 1, soundPitch(player));
			ItemEntity eggEntity = new ItemEntity(level, target.getX(), target.getY(), target.getZ(),
					new ItemStack(egg));
			level.addFreshEntity(eggEntity);
			target.discard();
			cost(player);
		} else {
			target.playSound(ModSounds.POOF, 1, soundPitch(player));
		}
	}

	@Override
	protected ParticleOptions generateParticle(Level level, Player player, ItemStack staff, int count) {
		Random rand = player.getRandom();
		return new MagicDustParticleData(0.8f + rand.nextFloat() * 0.2f, rand.nextFloat() * 0.2f,
				0.8f + rand.nextFloat() * 0.2f, 1);
	}
}
