package mod.vemerion.wizardstaff.Magic.suggestions2;

import mod.vemerion.wizardstaff.Magic.LookAtMagic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
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

		ResourceKey<Level> respawnDim = player.getRespawnDimension();
		ServerLevel respawnWorld = level.getServer().getLevel(respawnDim);
		BlockPos respawnPos = player.getRespawnPosition();
		if (respawnPos != null && respawnWorld != null)
			respawnPos = Player.findRespawnPositionAndUseSpawnBlock(respawnWorld, respawnPos, player.getRespawnAngle(), false, true)
					.map(BlockPos::new).orElse(null);

		if (respawnDim == level.dimension())
			pos = respawnPos;

		if (respawnPos == null && level.dimension() == Level.OVERWORLD)
			pos = level.getSharedSpawnPos();

		return pos;
	}

}
