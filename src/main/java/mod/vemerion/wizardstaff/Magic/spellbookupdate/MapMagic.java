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
import mod.vemerion.wizardstaff.staff.WizardStaffItem;
import mod.vemerion.wizardstaff.staff.WizardStaffItemHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapDecoration;

public class MapMagic extends Magic {

	private static final ImmutableList<StructureInfo> VALID_STRUCTURES = ImmutableList.of(
			new StructureInfo(Structure.VILLAGE, MapDecoration.Type.MANSION));

	public MapMagic(MagicType type) {
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
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.CROSSBOW;
	}

	@Override
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		if (!world.isRemote) {
			ServerWorld serverworld = (ServerWorld) world;
			StructureInfo structureInfo = VALID_STRUCTURES.get(player.getRNG().nextInt(VALID_STRUCTURES.size()));
			BlockPos pos = serverworld.func_241117_a_(structureInfo.structure, randPos(player), 2, true);
			if (pos != null) {
				cost(player);
				playSoundServer(world, player, ModSounds.SCRIBBLE, 1, soundPitch(player));
				ItemStack map = FilledMapItem.setupNewMap(serverworld, pos.getX(), pos.getZ(), (byte) 2, true, true);
				FilledMapItem.func_226642_a_(serverworld, map);
				MapData.addTargetDecoration(map, pos, "+", structureInfo.decoration);
				map.setDisplayName(Items.FILLED_MAP.getName());

				WizardStaffItemHandler handler = WizardStaffItem.getHandler(staff);
				handler.extractItem(0, 1, false);
				handler.insertItem(0, map, false);
			} else {
				playSoundServer(world, player, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, 1, soundPitch(player));
			}
		}
		return super.magicFinish(world, player, staff);
	}

	private BlockPos randPos(PlayerEntity player) {
		Random rand = player.getRNG();
		return player.getPosition().add(rand.nextInt(1000) - 500, rand.nextInt(1000) - 500,
				rand.nextInt(1000) - 500);
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
