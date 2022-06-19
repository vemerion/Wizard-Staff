package mod.vemerion.wizardstaff.Magic.spellbookupdate;

import java.util.Random;

import com.google.common.collect.ImmutableList;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import mod.vemerion.wizardstaff.staff.WizardStaffItemHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ConfiguredStructureTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

public class MapMagic extends Magic {

	private static final ImmutableList<StructureInfo> VALID_STRUCTURES = ImmutableList
			.of(new StructureInfo(ConfiguredStructureTags.VILLAGE, MapDecoration.Type.MANSION));

	public MapMagic(MagicType<? extends MapMagic> type) {
		super(type);
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::buildup;
	}

	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::buildup;
	}

	@Override
	public UseAnim getUseAnim(ItemStack stack) {
		return UseAnim.CROSSBOW;
	}

	@Override
	public ItemStack magicFinish(Level level, Player player, ItemStack staff) {
		if (!level.isClientSide) {
			ServerLevel serverworld = (ServerLevel) level;
			StructureInfo structureInfo = VALID_STRUCTURES.get(player.getRandom().nextInt(VALID_STRUCTURES.size()));
			BlockPos pos = serverworld.findNearestMapFeature(structureInfo.structure, randPos(player), 2, true);
			if (pos != null) {
				cost(player);
				playSoundServer(level, player, ModSounds.SCRIBBLE, 1, soundPitch(player));
				ItemStack map = MapItem.create(serverworld, pos.getX(), pos.getZ(), (byte) 2, true, true);
				MapItem.renderBiomePreviewMap(serverworld, map);
				MapItemSavedData.addTargetDecoration(map, pos, "+", structureInfo.decoration);
				map.setHoverName(Items.FILLED_MAP.getDescription());

				WizardStaffItemHandler.getOptional(staff).ifPresent(h -> {
					h.extractCurrent();
					h.insertCurrent(map);
				});
			} else {
				playSoundServer(level, player, SoundEvents.GENERIC_EXTINGUISH_FIRE, 1, soundPitch(player));
			}
		}
		return super.magicFinish(level, player, staff);
	}

	private BlockPos randPos(Player player) {
		Random rand = player.getRandom();
		return player.blockPosition().offset(rand.nextInt(1000) - 500, rand.nextInt(1000) - 500,
				rand.nextInt(1000) - 500);
	}

	private static class StructureInfo {
		private TagKey<ConfiguredStructureFeature<?, ?>> structure;
		private MapDecoration.Type decoration;

		private StructureInfo(TagKey<ConfiguredStructureFeature<?, ?>> structure, MapDecoration.Type decoration) {
			this.structure = structure;
			this.decoration = decoration;
		}
	}

}
