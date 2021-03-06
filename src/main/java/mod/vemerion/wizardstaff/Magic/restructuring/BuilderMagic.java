package mod.vemerion.wizardstaff.Magic.restructuring;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.serialization.Codec;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.MagicUtil;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.Template.BlockInfo;
import net.minecraft.world.server.ServerWorld;

public class BuilderMagic extends Magic {

	private ResourceLocation name;
	private Direction front;
	private BlockPos center;
	private BlockPos playerOffset;

	public BuilderMagic(MagicType<? extends BuilderMagic> type) {
		super(type);
	}
	
	public BuilderMagic setAdditionalParams(ResourceLocation name, Direction front, BlockPos center, BlockPos playerOffset) {
		this.name = name;
		this.front = front;
		this.center = center;
		this.playerOffset = playerOffset;
		return this;
	}

	@Override
	protected void readAdditional(JsonObject json) {
		name = new ResourceLocation(JSONUtils.getString(json, "template"));
		String direction = JSONUtils.getString(json, "front");
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
		json.addProperty("front", front.getName2());
		MagicUtil.writeBlockPos(json, "center", center);
		MagicUtil.writeBlockPos(json, "player_offset", playerOffset);
	}

	@Override
	protected void decodeAdditional(PacketBuffer buffer) {
		int len = buffer.readInt();
		name = new ResourceLocation(buffer.readString(len));
	}

	@Override
	protected void encodeAdditional(PacketBuffer buffer) {
		buffer.writeInt(name.toString().length());
		buffer.writeString(name.toString());
	}

	@Override
	protected Object[] getNameArgs() {
		return new Object[] { new StringTextComponent(name.getPath()) };
	}

	@Override
	protected Object[] getDescrArgs() {
		return new Object[] { new StringTextComponent(name.getPath()) };
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
		if (!world.isRemote && generateStructure((ServerWorld) world, player)) {
			cost(player);
			playSoundServer(world, player, ModSounds.BUILDING, 1, soundPitch(player));
		}
		return super.magicFinish(world, player, staff);
	}

	private boolean generateStructure(ServerWorld world, PlayerEntity player) {
		Template template = world.getStructureTemplateManager().getTemplate(name);
		if (template == null) {
			Main.LOGGER.error("Invalid template name " + name);
			return false;
		}

		Direction direction = getDirection(player);
		BlockPos offset = BlockPos.ZERO.offset(direction, playerOffset.getZ())
				.offset(direction.rotateY(), playerOffset.getX()).offset(Direction.UP, playerOffset.getY());
		BlockPos pos = player.getPosition().subtract(new BlockPos(center.getX(), 0, center.getY())).add(offset);
		Rotation rotation = calculateRotation(player);
		PlacementSettings settings = new PlacementSettings().setRotation(rotation).setMirror(Mirror.NONE)
				.setCenterOffset(center).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK)
				.addProcessor(AirStructureProcessor.INSTANCE);
		return template.func_237146_a_(world, pos, pos, settings, player.getRNG(), 2);
	}

	private Rotation calculateRotation(PlayerEntity player) {
		Direction direction = getDirection(player).getOpposite();

		if (front == direction) {
			return Rotation.NONE;
		} else if (front == direction.rotateY())
			return Rotation.COUNTERCLOCKWISE_90;
		else if (front == direction.rotateYCCW()) {
			return Rotation.CLOCKWISE_90;
		} else {
			return Rotation.CLOCKWISE_180;
		}
	}

	private Direction getDirection(PlayerEntity player) {
		Direction[] directions = Direction.getFacingDirections(player);
		for (Direction d : directions) {
			if (Direction.Plane.HORIZONTAL.test(d))
				return d;
		}
		return Direction.NORTH;
	}

	// Processor for checking that we are only replacing air, nothing else
	private static final IStructureProcessorType<AirStructureProcessor> AIR = Registry.register(
			Registry.STRUCTURE_PROCESSOR, new ResourceLocation(Main.MODID, "air"), () -> AirStructureProcessor.CODEC);

	private static class AirStructureProcessor extends StructureProcessor {

		public static final AirStructureProcessor INSTANCE = new AirStructureProcessor();
		public static final Codec<AirStructureProcessor> CODEC = Codec.unit(() -> {
			return INSTANCE;
		});

		@Override
		protected IStructureProcessorType<?> getType() {
			return AIR;
		}

		@Override
		public BlockInfo process(IWorldReader world, BlockPos center1, BlockPos center2, BlockInfo infoRelative,
				BlockInfo infoAbsolute, PlacementSettings settings, Template template) {
			return !world.isAirBlock(infoAbsolute.pos) ? null
					: super.process(world, center1, center2, infoRelative, infoAbsolute, settings, template);
		}

	}

}
