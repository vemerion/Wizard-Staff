package mod.vemerion.wizardstaff.Magic.wild;

import java.util.List;
import java.util.Optional;

import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.RayMagic;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.particle.MagicDustParticleData;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class CycleVariantMagic extends RayMagic {

	public CycleVariantMagic(MagicType<? extends CycleVariantMagic> type) {
		super(type);
	}

	@Override
	protected Optional<ParticleOptions> generateParticle(Level level, Player player, ItemStack staff, int count,
			Vec3 pos, Entity target) {
		var rand = player.getRandom();
		return Optional.of(new MagicDustParticleData(rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), 1));
	}

	@Override
	protected void hitEntity(Level level, Player player, Entity target) {
		if (target instanceof Frog frog) {
			frog.setVariant(
					cycle(frog.getVariant(), List.of(FrogVariant.COLD, FrogVariant.TEMPERATE, FrogVariant.WARM)));
			cost(player);
			playSoundServer(level, player, ModSounds.SWAP.get(), 1, soundPitch(player));
		} else if (target instanceof Cat cat) {
			cat.setCatVariant(cycle(cat.getCatVariant(),
					List.of(CatVariant.TABBY, CatVariant.BLACK, CatVariant.RED, CatVariant.SIAMESE,
							CatVariant.BRITISH_SHORTHAIR, CatVariant.CALICO, CatVariant.PERSIAN, CatVariant.RAGDOLL,
							CatVariant.WHITE, CatVariant.JELLIE, CatVariant.ALL_BLACK)));
			cost(player);
			playSoundServer(level, player, ModSounds.SWAP.get(), 1, soundPitch(player));
		}
	}

	private <T> T cycle(T current, List<T> variants) {
		for (int i = 0; i < variants.size(); i++) {
			if (current == variants.get(i)) {
				return variants.get((i + 1) % variants.size());
			}
		}
		return current;
	}
}
