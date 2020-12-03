package mod.vemerion.wizardstaff.Magic.suggestions;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class WaterBucketMagic extends Magic {

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::forwardWaving;
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
		Vector3d start = player.getEyePosition(1);
		Vector3d end = start.add(Vector3d.fromPitchYaw(player.getPitchYaw()).scale(4.5));
		RayTraceResult raytrace = world.rayTraceBlocks(new RayTraceContext(start, end,
				RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, player));
		if (raytrace.getType() == RayTraceResult.Type.BLOCK) {
			BlockPos pos = new BlockPos(raytrace.getHitVec());
			BucketItem bucket = (BucketItem) Items.WATER_BUCKET;
			if (bucket.tryPlaceContainedLiquid(player, world, pos, (BlockRayTraceResult) raytrace)) {
				bucket.onLiquidPlaced(world, new ItemStack(bucket), pos);
				if (!world.isRemote) {
					cost(player);
				}
			}
		}

		return super.magicFinish(world, player, staff);
	}

}
