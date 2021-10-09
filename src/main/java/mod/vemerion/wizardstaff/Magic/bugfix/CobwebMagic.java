package mod.vemerion.wizardstaff.Magic.bugfix;

import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.RayMagic;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.particle.MagicDustParticleData;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CobwebMagic extends RayMagic {

	public CobwebMagic(MagicType<? extends CobwebMagic> type) {
		super(type);
	}

	@Override
	protected IParticleData generateParticle(World world, PlayerEntity player, ItemStack staff, int count) {
		return new MagicDustParticleData(1, 1, 1, 1);
	}

	@Override
	protected void hitEntity(World world, PlayerEntity player, Entity target) {
		BlockPos pos = target.getPosition();
		if (world.isAirBlock(pos) && !world.isRemote) {
			cost(player);
			world.setBlockState(pos, Blocks.COBWEB.getDefaultState());
			playSoundServer(world, player, ModSounds.SPRAY, 1, soundPitch(player));
		}
	}

}
