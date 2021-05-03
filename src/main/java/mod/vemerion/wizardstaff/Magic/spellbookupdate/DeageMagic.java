package mod.vemerion.wizardstaff.Magic.spellbookupdate;

import java.util.Random;

import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.RayMagic;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.particle.MagicDustParticleData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.IParticleData;
import net.minecraft.world.World;

public class DeageMagic extends RayMagic {

	public DeageMagic(MagicType type) {
		super(type);
	}

	@Override
	protected IParticleData generateParticle(World world, PlayerEntity player, ItemStack staff, int count) {
		float duration = getUseDuration(staff);
		float progress = (duration - count) / duration;
		float green = Math.min(1, progress * 1.5f);
		Random rand = player.getRNG();
		return new MagicDustParticleData(rand.nextFloat() * 0.1f, green, 0.2f + rand.nextFloat() * 0.1f, 1);
	}

	@Override
	protected void hitEntity(World world, PlayerEntity player, Entity target) {
		if (target instanceof MobEntity) {
			target.playSound(ModSounds.DEAGE, 1, soundPitch(player));
			cost(player);
			MobEntity mob = (MobEntity) target;
			mob.setChild(true);
		}
	}

}
