package mod.vemerion.wizardstaff.Magic.wild;

import java.util.Optional;

import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.RayMagic;
import mod.vemerion.wizardstaff.particle.MagicDustParticleData;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.entity.npc.VillagerType;
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
		if (target instanceof VariantHolder<?> holder) {
			if (cycle(holder)) {
				cost(player);
			}
		}
	}

	@SuppressWarnings("unchecked")
	<T> boolean cycle(VariantHolder<T> holder) {
		var variant = holder.getVariant();

		// Enum
		if (variant instanceof Enum<?> e) {
			Enum<?>[] values = e.getClass().getEnumConstants();
			for (int i = 0; i < values.length; i++) {
				if (values[i] == e) {
					holder.setVariant((T) values[(i + 1) % values.length]);
					return true;
				}
			}
		}

		// Registry
		if (variant instanceof CatVariant)
			return cycleReg(holder, BuiltInRegistries.CAT_VARIANT, variant);
		else if (variant instanceof FrogVariant)
			return cycleReg(holder, BuiltInRegistries.FROG_VARIANT, variant);
		else if (variant instanceof VillagerType)
			return cycleReg(holder, BuiltInRegistries.VILLAGER_TYPE, variant);

		return false;
	}

	@SuppressWarnings("unchecked")
	<A, B> boolean cycleReg(VariantHolder<A> holder, Registry<B> reg, A value) {
		var next = nextFromReg(reg, value);
		if (next != null) {
			holder.setVariant((A) next);
			return true;
		}
		return false;
	}

	<T> T nextFromReg(Registry<T> reg, Object value) {
		T first = null;
		boolean next = false;
		for (var v : reg) {
			if (next)
				return v;
			if (first == null)
				first = v;
			if (v == value)
				next = true;
		}
		if (next)
			return first;

		return null;
	}
}
