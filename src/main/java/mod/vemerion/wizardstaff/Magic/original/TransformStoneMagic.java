package mod.vemerion.wizardstaff.Magic.original;

import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicUtil;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceContext.BlockMode;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

public class TransformStoneMagic extends Magic {

	private Block block;

	public TransformStoneMagic(String name) {
		super(name);
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BLOCK;
	}

	@Override
	protected void decodeAdditional(PacketBuffer buffer) {
		block = MagicUtil.decode(buffer, ForgeRegistries.BLOCKS);
	}

	@Override
	protected void encodeAdditional(PacketBuffer buffer) {
		MagicUtil.encode(buffer, block);
	}

	@Override
	protected void readAdditional(JsonObject json) {
		block = MagicUtil.read(json, ForgeRegistries.BLOCKS, "block");
	}

	@Override
	protected Object[] getNameArgs() {
		return new Object[] { block.getTranslatedName() };
	}
	
	@Override
	protected Object[] getDescrArgs() {
		return new Object[] { block.getTranslatedName() };
	}

	@Override
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		if (!world.isRemote) {
			Vector3d direction = Vector3d.fromPitchYaw(player.getPitchYaw());
			Vector3d start = player.getPositionVec().add(0, 1.5, 0).add(direction.scale(0.2));
			Vector3d stop = start.add(direction.scale(4.5));
			BlockRayTraceResult result = world
					.rayTraceBlocks(new RayTraceContext(start, stop, BlockMode.COLLIDER, FluidMode.NONE, player));
			if (result.getType() == Type.BLOCK
					&& world.getBlockState(result.getPos()).getBlock().isIn(Tags.Blocks.STONE)) {
				BlockPos pos = result.getPos();
				world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_STONE_BREAK,
						SoundCategory.PLAYERS, 1.5f, soundPitch(player));
				cost(player);
				world.setBlockState(pos, block.getDefaultState());
			}

		}
		return super.magicFinish(world, player, staff);
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::forwardBuildup;
	}

	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::forwardShake;
	}

}
