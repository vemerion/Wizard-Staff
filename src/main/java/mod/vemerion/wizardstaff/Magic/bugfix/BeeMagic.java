package mod.vemerion.wizardstaff.Magic.bugfix;

import java.lang.reflect.Method;
import java.util.List;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.BlockRayMagic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.BeehiveTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class BeeMagic extends BlockRayMagic {

	private static final int STAY_OUT_OF_HIVE_TIME = 20 * 40;

	private static Method tryReleaseBee;

	public BeeMagic(MagicType<? extends BeeMagic> type) {
		super(type);
	}

	@Override
	protected void hitBlock(World world, PlayerEntity player, BlockPos pos) {
		if (!world.getBlockState(pos).getBlock().isIn(BlockTags.BEEHIVES))
			return;

		BlockState state = world.getBlockState(pos);
		TileEntity tile = world.getTileEntity(pos);
		if (!(tile instanceof BeehiveTileEntity))
			return;

		BeehiveTileEntity hive = (BeehiveTileEntity) tile;

		try {
			if (tryReleaseBee == null)
				tryReleaseBee = ObfuscationReflectionHelper.findMethod(BeehiveTileEntity.class, "func_226965_a_",
						BlockState.class, BeehiveTileEntity.State.class);

			List<?> bees = (List<?>) tryReleaseBee.invoke(hive, state, BeehiveTileEntity.State.EMERGENCY);
			boolean didRelease = false;
			for (Object bee : bees) {
				if (bee instanceof BeeEntity) {
					((BeeEntity) bee).setStayOutOfHiveCountdown(STAY_OUT_OF_HIVE_TIME);
					didRelease = true;
				}
			}
			if (didRelease)
				cost(player);
		} catch (Exception e) {
			Main.LOGGER.error("Unable to access method 'tryReleaseBee()' via reflection! " + e);
		}
	}

}
