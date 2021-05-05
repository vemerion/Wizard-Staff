package mod.vemerion.wizardstaff.Magic.suggestions2;

import mod.vemerion.wizardstaff.Magic.LookAtMagic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class LocateSpawnMagic extends LookAtMagic {

	public LocateSpawnMagic(MagicType<? extends LocateSpawnMagic> type) {
		super(type);
	}

	@Override
	protected BlockPos getPosition(ServerWorld world, ServerPlayerEntity player, ItemStack staff) {
		BlockPos pos = null;

		RegistryKey<World> respawnDim = player.func_241141_L_();
		ServerWorld respawnWorld = world.getServer().getWorld(respawnDim);
		BlockPos respawnPos = player.func_241140_K_();
		if (respawnPos != null && respawnWorld != null)
			respawnPos = PlayerEntity.func_242374_a(respawnWorld, respawnPos, player.func_242109_L(), false, true)
					.map(BlockPos::new).orElse(null);

		if (respawnDim == world.getDimensionKey())
			pos = respawnPos;

		if (respawnPos == null && world.getDimensionKey() == World.OVERWORLD)
			pos = world.getSpawnPoint();

		return pos;
	}

}
