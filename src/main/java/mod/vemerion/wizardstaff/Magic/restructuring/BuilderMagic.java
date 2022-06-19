package mod.vemerion.wizardstaff.Magic.restructuring;

import java.util.Optional;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.MagicUtil;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class BuilderMagic extends Magic {

	private ResourceLocation name;
	private Direction front;
	private BlockPos center;
	private BlockPos playerOffset;

	public BuilderMagic(MagicType<? extends BuilderMagic> type) {
		super(type);
	}

	public BuilderMagic setAdditionalParams(ResourceLocation name, Direction front, BlockPos center,
			BlockPos playerOffset) {
		this.name = name;
		this.front = front;
		this.center = center;
		this.playerOffset = playerOffset;
		return this;
	}

	@Override
	protected void readAdditional(JsonObject json) {
		name = new ResourceLocation(GsonHelper.getAsString(json, "template"));
		String direction = GsonHelper.getAsString(json, "front");
		front = Direction.byName(direction);
		if (front == null)
			throw new JsonParseException("Invalid direction " + direction + " for front attribute");
		if (!Direction.Plane.HORIZONTAL.test(front))
			throw new JsonParseException("Direction must be horizontal for front attribute");
		center = MagicUtil.readBlockPos(json, "center");
		playerOffset = MagicUtil.readBlockPos(json, "player_offset");
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		json.addProperty("template", name.toString());
		json.addProperty("front", front.getName());
		MagicUtil.writeBlockPos(json, "center", center);
		MagicUtil.writeBlockPos(json, "player_offset", playerOffset);
	}

	@Override
	protected void decodeAdditional(FriendlyByteBuf buffer) {
		int len = buffer.readInt();
		name = new ResourceLocation(buffer.readUtf(len));
	}

	@Override
	protected void encodeAdditional(FriendlyByteBuf buffer) {
		buffer.writeInt(name.toString().length());
		buffer.writeUtf(name.toString());
	}

	@Override
	protected Object[] getNameArgs() {
		return new Object[] { new TextComponent(name.getPath()) };
	}

	@Override
	protected Object[] getDescrArgs() {
		return new Object[] { new TextComponent(name.getPath()) };
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
		if (!level.isClientSide && generateStructure((ServerLevel) level, player)) {
			cost(player);
			playSoundServer(level, player, ModSounds.BUILDING, 1, soundPitch(player));
		}
		return super.magicFinish(level, player, staff);
	}

	private boolean generateStructure(ServerLevel level, Player player) {
		Optional<StructureTemplate> templateOpt = level.getStructureManager().get(name);
		if (templateOpt.isEmpty()) {
			Main.LOGGER.error("Invalid template name " + name);
			return false;
		}

		Direction direction = getDirection(player);
		BlockPos offset = BlockPos.ZERO.relative(direction, playerOffset.getZ())
				.relative(direction.getClockWise(), playerOffset.getX()).relative(Direction.UP, playerOffset.getY());
		BlockPos pos = player.blockPosition().subtract(new BlockPos(center.getX(), 0, center.getY())).offset(offset);
		Rotation rotation = calculateRotation(player);
		StructurePlaceSettings settings = new StructurePlaceSettings().setRotation(rotation).setMirror(Mirror.NONE)
				.setRotationPivot(center).addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK)
				.addProcessor(new AirStructureProcessor());
		return templateOpt.get().placeInWorld(level, pos, pos, settings, player.getRandom(), 2);
	}

	private Rotation calculateRotation(Player player) {
		Direction direction = getDirection(player).getOpposite();

		if (front == direction) {
			return Rotation.NONE;
		} else if (front == direction.getClockWise())
			return Rotation.COUNTERCLOCKWISE_90;
		else if (front == direction.getCounterClockWise()) {
			return Rotation.CLOCKWISE_90;
		} else {
			return Rotation.CLOCKWISE_180;
		}
	}

	private Direction getDirection(Player player) {
		Direction[] directions = Direction.orderedByNearest(player);
		for (Direction d : directions) {
			if (Direction.Plane.HORIZONTAL.test(d))
				return d;
		}
		return Direction.NORTH;
	}

	// Processor for checking that we are only replacing air, nothing else
	private static class AirStructureProcessor extends StructureProcessor {
		@Override
		protected StructureProcessorType<?> getType() {
			return null;
		}

		@Override
		public StructureTemplate.StructureBlockInfo process(LevelReader world, BlockPos center1, BlockPos center2,
				StructureTemplate.StructureBlockInfo infoRelative, StructureTemplate.StructureBlockInfo infoAbsolute,
				StructurePlaceSettings settings, StructureTemplate template) {
			return !world.isEmptyBlock(infoAbsolute.pos) ? null
					: super.process(world, center1, center2, infoRelative, infoAbsolute, settings, template);
		}

	}

}
