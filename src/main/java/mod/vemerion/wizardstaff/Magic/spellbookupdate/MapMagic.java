package mod.vemerion.wizardstaff.Magic.spellbookupdate;

import java.util.Locale;

import com.google.common.collect.ImmutableList;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import mod.vemerion.wizardstaff.staff.WizardStaffItem;
import mod.vemerion.wizardstaff.staff.WizardStaffItemHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapDecoration;

public class MapMagic extends Magic {

	private static final ImmutableList<StructureInfo> VALID_STRUCTURES = ImmutableList.of(
			new StructureInfo(Structure.MONUMENT, MapDecoration.Type.MONUMENT),
			new StructureInfo(Structure.WOODLAND_MANSION, MapDecoration.Type.MANSION));

	public MapMagic(String name) {
		super(name);
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
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.CROSSBOW;
	}

	@Override
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		if (!world.isRemote) {
			ServerWorld serverworld = (ServerWorld) world;
			StructureInfo structureInfo = VALID_STRUCTURES.get(player.getRNG().nextInt(VALID_STRUCTURES.size()));
			BlockPos pos = serverworld.func_241117_a_(structureInfo.structure, player.getPosition(), 100, true);
			if (pos != null) {
				cost(player);
				ItemStack map = FilledMapItem.setupNewMap(serverworld, pos.getX(), pos.getZ(), (byte) 2, true, true);
				FilledMapItem.func_226642_a_(serverworld, map);
				MapData.addTargetDecoration(map, pos, "+", structureInfo.decoration);
				map.setDisplayName(new TranslationTextComponent(
						"filled_map." + structureInfo.structure.getStructureName().toLowerCase(Locale.ROOT)));

				WizardStaffItemHandler handler = WizardStaffItem.getHandler(staff);
				handler.extractItem(0, 1, false);
				handler.insertItem(0, map, false);
			}
		}
		return super.magicFinish(world, player, staff);
	}

	private static class StructureInfo {
		private Structure<?> structure;
		private MapDecoration.Type decoration;

		private StructureInfo(Structure<?> structure, MapDecoration.Type decoration) {
			this.structure = structure;
			this.decoration = decoration;
		}
	}

}
