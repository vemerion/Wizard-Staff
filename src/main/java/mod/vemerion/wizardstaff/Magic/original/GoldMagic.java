package mod.vemerion.wizardstaff.Magic.original;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
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

public class GoldMagic extends Magic {

	private static final ResourceLocation GOLD = new ResourceLocation("forge", "ingots/gold");

	@Override
	public int getUseDuration(ItemStack staff) {
		return 25;
	}

	@Override
	public boolean isMagicItem(Item item) {
		return ItemTags.getCollection().getTagByID(GOLD).contains(item);
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BLOCK;
	}
	
	@Override
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		if (!world.isRemote) {
			Vector3d direction = Vector3d.fromPitchYaw(player.getPitchYaw());
			Vector3d start = player.getPositionVec().add(0, 1.5, 0).add(direction.scale(0.2));
			Vector3d stop = start.add(direction.scale(4.5));
			BlockRayTraceResult result = world
					.rayTraceBlocks(new RayTraceContext(start, stop, BlockMode.COLLIDER, FluidMode.NONE, player));
			if (result.getType() == Type.BLOCK && world.getBlockState(result.getPos()).getBlock() == Blocks.STONE) {
				BlockPos pos = result.getPos();
				world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_STONE_BREAK,
						SoundCategory.PLAYERS, 1.5f, soundPitch(player));
				cost(player, 50);
				world.setBlockState(pos, Blocks.GOLD_ORE.getDefaultState());
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
