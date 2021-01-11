package mod.vemerion.wizardstaff.Magic.original;

import java.util.Random;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.RayMagic;
import mod.vemerion.wizardstaff.particle.MagicDustParticleData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.particles.IParticleData;
import net.minecraft.world.World;

public class EggMagic extends RayMagic {

	public EggMagic(String name) {
		super(name);
	}

	@Override
	public void hitEntity(World world, PlayerEntity player, Entity target) {
		target.playSound(Main.PLOP_SOUND, 1, soundPitch(player));
		SpawnEggItem egg = null;
		for (SpawnEggItem e : SpawnEggItem.getEggs()) {
			if (e.getType(null) == target.getType()) {
				egg = e;
				break;
			}
		}
		if (egg != null) {
			ItemEntity eggEntity = new ItemEntity(world, target.getPosX(), target.getPosY(), target.getPosZ(),
					new ItemStack(egg));
			world.addEntity(eggEntity);
			target.remove();
			cost(player);
		}
	}

	@Override
	protected IParticleData generateParticle(World world, PlayerEntity player, ItemStack staff, int count) {
		Random rand = player.getRNG();
		return new MagicDustParticleData(0.8f + rand.nextFloat() * 0.2f, rand.nextFloat() * 0.2f,
				0.8f + rand.nextFloat() * 0.2f, 1);
	}
}
