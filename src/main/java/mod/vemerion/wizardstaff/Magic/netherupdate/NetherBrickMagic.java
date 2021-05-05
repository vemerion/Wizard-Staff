package mod.vemerion.wizardstaff.Magic.netherupdate;

import mod.vemerion.wizardstaff.Magic.LookAtMagic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.server.ServerWorld;

public class NetherBrickMagic extends LookAtMagic {

	public NetherBrickMagic(MagicType<? extends NetherBrickMagic> type) {
		super(type);
	}

	@Override
	protected BlockPos getPosition(ServerWorld world, ServerPlayerEntity player, ItemStack staff) {
		BlockPos pos = null;
		if (world.getDimensionKey() == World.THE_NETHER) {
			pos = world.func_241117_a_(Structure.FORTRESS, new BlockPos(player.getPositionVec()), 100, false);
		}

		return pos;
	}

}
