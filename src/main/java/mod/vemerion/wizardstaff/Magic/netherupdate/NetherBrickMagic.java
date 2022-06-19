package mod.vemerion.wizardstaff.Magic.netherupdate;

import mod.vemerion.wizardstaff.Magic.LookAtMagic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.init.ModConfiguredStructureTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class NetherBrickMagic extends LookAtMagic {

	public NetherBrickMagic(MagicType<? extends NetherBrickMagic> type) {
		super(type);
	}

	@Override
	protected BlockPos getPosition(ServerLevel level, ServerPlayer player, ItemStack staff) {
		BlockPos pos = null;
		if (level.dimension() == Level.NETHER) {
			pos = level.findNearestMapFeature(ModConfiguredStructureTags.NETHER_FORTRESS,
					new BlockPos(player.position()), 100, false);
		}
		return pos;
	}

}
