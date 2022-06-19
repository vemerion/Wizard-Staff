package mod.vemerion.wizardstaff.Magic.spellbookupdate;

import java.util.Random;

import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.RayMagic;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.particle.MagicDustParticleData;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DeageMagic extends RayMagic {

	public DeageMagic(MagicType<? extends DeageMagic> type) {
		super(type);
	}

	@Override
	protected ParticleOptions generateParticle(Level level, Player player, ItemStack staff, int count) {
		float duration = getUseDuration(staff);
		float progress = (duration - count) / duration;
		float green = Math.min(1, progress * 1.5f);
		Random rand = player.getRandom();
		return new MagicDustParticleData(rand.nextFloat() * 0.1f, green, 0.2f + rand.nextFloat() * 0.1f, 1);
	}

	@Override
	protected void hitEntity(Level level, Player player, Entity target) {
		if (target instanceof Mob) {
			target.playSound(ModSounds.DEAGE, 1, soundPitch(player));
			cost(player);
			Mob mob = (Mob) target;
			mob.setBaby(true);
		}
	}

}
