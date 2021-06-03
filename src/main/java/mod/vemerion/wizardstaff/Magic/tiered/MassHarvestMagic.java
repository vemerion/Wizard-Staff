package mod.vemerion.wizardstaff.Magic.tiered;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.Helper.Helper;
import mod.vemerion.wizardstaff.Magic.BlockMatch;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class MassHarvestMagic extends Magic {

	private BlockMatch match;
	private int harvestLimit;

	public MassHarvestMagic(MagicType<? extends MassHarvestMagic> type) {
		super(type);
	}

	public MassHarvestMagic setAdditionalParams(BlockMatch match, int harvestLimit) {
		this.match = match;
		this.harvestLimit = harvestLimit;
		return this;
	}

	@Override
	protected void readAdditional(JsonObject json) {
		match = BlockMatch.read(JSONUtils.getJsonObject(json, "block_match"));
		harvestLimit = JSONUtils.getInt(json, "harvest_limit");
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		json.add("block_match", match.write());
		json.addProperty("harvest_limit", harvestLimit);
	}

	@Override
	protected void encodeAdditional(PacketBuffer buffer) {
		match.encode(buffer);
		buffer.writeInt(harvestLimit);
	}

	@Override
	protected void decodeAdditional(PacketBuffer buffer) {
		match = BlockMatch.decode(buffer);
		harvestLimit = buffer.readInt();
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::drill;
	}

	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::forwardShake;
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BLOCK;
	}

	protected Object[] getNameArgs() {
		return new Object[] { match.getName() };
	}

	@Override
	protected Object[] getDescrArgs() {
		return new Object[] { new StringTextComponent(String.valueOf(harvestLimit)), match.getName() };
	}

	@Override
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		BlockRayTraceResult result = Helper.blockRay(world, player, 4);
		if (result.getType() == Type.BLOCK) {
			BlockState state = world.getBlockState(result.getPos());
			if (match.test(state.getBlock())) {
				player.playSound(state.getSoundType().getBreakSound(), 1, soundPitch(player));
				if (!world.isRemote)
					cost(player, havestBlocks(result.getPos(), world));
			} else {
				player.playSound(ModSounds.POOF, 1, soundPitch(player));
			}
		}

		return super.magicFinish(world, player, staff);
	}

	private int havestBlocks(BlockPos start, World world) {
		List<BlockPos> positions = new ArrayList<>();
		Set<BlockPos> found = new HashSet<>();
		positions.add(start);
		while (!positions.isEmpty()) {
			BlockPos pos = positions.remove(0);
			BlockPos.getAllInBox(pos.add(-1, -1, -1), pos.add(1, 1, 1)).forEach(p -> {
				if (found.size() < harvestLimit && !found.contains(p)
						&& match.test(world.getBlockState(p).getBlock())) {
					BlockPos immutable = p.toImmutable();
					positions.add(immutable);
					found.add(immutable);
				}
			});
			destroyBlock(world, pos);
		}
		return found.size();
	}

	private void destroyBlock(World world, BlockPos pos) {
		FluidState fluidstate = world.getFluidState(pos);
		BlockState state = world.getBlockState(pos);
		TileEntity tileentity = state.hasTileEntity() ? world.getTileEntity(pos) : null;
		Block.spawnDrops(state, world, pos, tileentity, null, ItemStack.EMPTY);
		world.setBlockState(pos, fluidstate.getBlockState());
	}

}
