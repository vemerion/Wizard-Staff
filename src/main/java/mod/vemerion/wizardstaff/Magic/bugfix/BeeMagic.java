package mod.vemerion.wizardstaff.Magic.bugfix;

import java.lang.reflect.Method;
import java.util.List;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.BlockRayMagic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

public class BeeMagic extends BlockRayMagic {

	private static final int STAY_OUT_OF_HIVE_TIME = 20 * 40;

	private static Method tryReleaseBee;

	public BeeMagic(MagicType<? extends BeeMagic> type) {
		super(type);
	}

	@Override
	protected void hitBlock(Level level, Player player, BlockPos pos) {
		if (!level.getBlockState(pos).is(BlockTags.BEEHIVES))
			return;

		BlockState state = level.getBlockState(pos);
		BlockEntity tile = level.getBlockEntity(pos);
		if (!(tile instanceof BeehiveBlockEntity))
			return;

		BeehiveBlockEntity hive = (BeehiveBlockEntity) tile;

		try {
			if (tryReleaseBee == null)
				tryReleaseBee = ObfuscationReflectionHelper.findMethod(BeehiveBlockEntity.class, "m_58759_",
						BlockState.class, BeehiveBlockEntity.BeeReleaseStatus.class);

			List<?> bees = (List<?>) tryReleaseBee.invoke(hive, state, BeehiveBlockEntity.BeeReleaseStatus.EMERGENCY);
			boolean didRelease = false;
			for (Object bee : bees) {
				if (bee instanceof Bee) {
					((Bee) bee).setStayOutOfHiveCountdown(STAY_OUT_OF_HIVE_TIME);
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
