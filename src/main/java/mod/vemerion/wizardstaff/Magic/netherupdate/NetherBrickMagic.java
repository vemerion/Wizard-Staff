package mod.vemerion.wizardstaff.Magic.netherupdate;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.LookAtMagic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;

public class NetherBrickMagic extends LookAtMagic {

	public static final TagKey<Structure> NETHER_FORTRESS = TagKey.create(Registry.STRUCTURE_REGISTRY,
			new ResourceLocation(Main.MODID, "nether_fortress"));

	public NetherBrickMagic(MagicType<? extends NetherBrickMagic> type) {
		super(type);
	}

	@Override
	protected BlockPos getPosition(ServerLevel level, ServerPlayer player, ItemStack staff) {
		BlockPos pos = null;
		if (level.dimension() == Level.NETHER) {
			pos = level.findNearestMapStructure(NETHER_FORTRESS, player.blockPosition(), 100, false);
		}
		return pos;
	}

}
