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
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

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
				e -> new ResourceLocation(JSONUtils.getString(e, "entity name")), new HashSet<>());
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		MagicUtil.writeColl(json, "blacklist", blacklist, r -> new JsonPrimitive(r.toString()));
	}

	@Override
	public void hitEntity(World world, PlayerEntity player, Entity target) {
		if (blacklist.contains(target.getType().getRegistryName())) {
			target.playSound(ModSounds.POOF, 1, soundPitch(player));
			return;
		}

		SpawnEggItem egg = null;
		for (SpawnEggItem e : SpawnEggItem.getEggs()) {
			if (e.getType(null) == target.getType()) {
				egg = e;
				break;
			}
		}
		if (egg != null) {
			target.playSound(ModSounds.PLOP, 1, soundPitch(player));
			ItemEntity eggEntity = new ItemEntity(world, target.getPosX(), target.getPosY(), target.getPosZ(),
					new ItemStack(egg));
			world.addEntity(eggEntity);
			target.remove();
			cost(player);
		} else {
			target.playSound(ModSounds.POOF, 1, soundPitch(player));
		}
	}

	@Override
	protected IParticleData generateParticle(World world, PlayerEntity player, ItemStack staff, int count) {
		Random rand = player.getRNG();
		return new MagicDustParticleData(0.8f + rand.nextFloat() * 0.2f, rand.nextFloat() * 0.2f,
				0.8f + rand.nextFloat() * 0.2f, 1);
	}
}
