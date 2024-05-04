package mod.vemerion.wizardstaff.Magic.suggestions2;

import mod.vemerion.wizardstaff.Magic.LookAtMagic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class LocateSpawnMagic extends LookAtMagic {

	public LocateSpawnMagic(MagicType<? extends LocateSpawnMagic> type) {
		super(type);
	}

	@Override
	protected BlockPos getPosition(ServerLevel level, ServerPlayer player, ItemStack staff) {
		BlockPos pos = null;

		var respawnDim = player.getRespawnDimension();
		var respawnWorld = level.getServer().getLevel(respawnDim);
		var respawnPos = player.getRespawnPosition();
		if (respawnPos != null && respawnWorld != null)
			respawnPos = Player.findRespawnPositionAndUseSpawnBlock(respawnWorld, respawnPos, player.getRespawnAngle(),
					false, true).map(v -> new BlockPos((int) v.x, (int) v.y, (int) v.z)).orElse(null);

		if (respawnDim == level.dimension())
			pos = respawnPos;

		if (respawnPos == null && level.dimension() == Level.OVERWORLD)
			pos = level.getSharedSpawnPos();

		return pos;
	}

}
