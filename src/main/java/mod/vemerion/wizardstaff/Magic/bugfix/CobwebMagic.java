package mod.vemerion.wizardstaff.Magic.bugfix;

import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.RayMagic;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.particle.MagicDustParticleData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class CobwebMagic extends RayMagic {

	public CobwebMagic(MagicType<? extends CobwebMagic> type) {
		super(type);
	}

	@Override
	protected ParticleOptions generateParticle(Level level, Player player, ItemStack staff, int count) {
		return new MagicDustParticleData(1, 1, 1, 1);
	}

	@Override
	protected void hitEntity(Level level, Player player, Entity target) {
		BlockPos pos = target.blockPosition();
		if (level.isEmptyBlock(pos) && !level.isClientSide) {
			cost(player);
			level.setBlockAndUpdate(pos, Blocks.COBWEB.defaultBlockState());
			playSoundServer(level, player, ModSounds.SPRAY, 1, soundPitch(player));
		}
	}

}
