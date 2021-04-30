package mod.vemerion.wizardstaff.Magic.suggestions2;

import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicUtil;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceContext.BlockMode;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

public class PushBlockMagic extends Magic {

	private static final int MAX_DISTANCE = 10;

	private Block block;

	public PushBlockMagic(String name) {
		super(name);
	}

	@Override
	protected void readAdditional(JsonObject json) {
		block = MagicUtil.read(json, ForgeRegistries.BLOCKS, "block");
	}

	@Override
	public void encodeAdditional(PacketBuffer buffer) {
		MagicUtil.encode(buffer, block);
	}

	@Override
	protected void decodeAdditional(PacketBuffer buffer) {
		block = MagicUtil.decode(buffer, ForgeRegistries.BLOCKS);
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::forwardBuildup;
	}

	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::forwardShake;
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BLOCK;
	}

	@Override
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		if (!world.isRemote) {
			Direction direction = Direction.getFacingDirections(player)[0];
			BlockPos start = ray(world, player);

			if (start != null) {
				BlockPos p = new BlockPos(start);
				BlockState state = world.getBlockState(p);
				TileEntity tileEntity = world.getTileEntity(start);
				if (tileEntity != null)
					tileEntity = TileEntity.readTileEntity(state, tileEntity.serializeNBT());

				for (int i = 0; i < MAX_DISTANCE; i++) {
					if (!(world.isAirBlock(p.offset(direction)) && state.isValidPosition(world, p.offset(direction))))
						break;
					p = p.offset(direction);
				}

				if (p.equals(start)) {
					playSoundServer(world, player, ModSounds.POOF, 1, soundPitch(player));
				} else {
					playSoundServer(world, player, ModSounds.WARP, 1, soundPitch(player));
					cost(player);
					world.setBlockState(p, state);
					if (tileEntity != null) {
						world.removeTileEntity(start);
						tileEntity.setPos(p);
						world.setTileEntity(p, tileEntity);
						tileEntity.markDirty();
					}
					world.setBlockState(start, Blocks.AIR.getDefaultState());
				}
			} else {
				playSoundServer(world, player, ModSounds.POOF, 1, soundPitch(player));
			}
		}
		return super.magicFinish(world, player, staff);
	}

	private BlockPos ray(World world, PlayerEntity player) {
		Vector3d direction = Vector3d.fromPitchYaw(player.getPitchYaw());
		Vector3d start = player.getPositionVec().add(0, 1.5, 0).add(direction.scale(0.2));
		Vector3d stop = start.add(direction.scale(5));
		BlockRayTraceResult result = world
				.rayTraceBlocks(new RayTraceContext(start, stop, BlockMode.COLLIDER, FluidMode.NONE, player));
		if (result.getType() == Type.BLOCK && world.getBlockState(result.getPos()).getBlock() == block)
			return result.getPos();

		return null;
	}

}
