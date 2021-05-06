package mod.vemerion.wizardstaff.Helper;

import mod.vemerion.wizardstaff.staff.WizardStaffItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceContext.BlockMode;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class Helper {
	public static Entity findTargetLine(Vector3d start, Vector3d direction, float distance, World world,
			LivingEntity caster) {
		AxisAlignedBB box = new AxisAlignedBB(start, start).grow(0.25);
		for (int i = 0; i < distance * 2; i++) {
			for (Entity e : world.getEntitiesInAABBexcluding(caster, box, (e) -> e instanceof LivingEntity)) {
				return e;
			}
			box = box.offset(direction);
		}
		return null;
	}

	public static BlockRayTraceResult blockRay(World world, PlayerEntity player, float distance) {
		Vector3d direction = Vector3d.fromPitchYaw(player.getPitchYaw());
		Vector3d start = player.getPositionVec().add(0, player.getEyeHeight(), 0).add(direction.scale(0.2));
		Vector3d stop = start.add(direction.scale(distance));
		return world.rayTraceBlocks(new RayTraceContext(start, stop, BlockMode.OUTLINE, FluidMode.NONE, player));
	}

	public static int color(int r, int g, int b, int a) {
		a = (a << 24) & 0xFF000000;
		r = (r << 16) & 0x00FF0000;
		g = (g << 8) & 0x0000FF00;
		b &= 0x000000FF;

		return a | r | g | b;
	}

	public static int red(int color) {
		return (color >> 16) & 255;
	}

	public static int green(int color) {
		return (color >> 8) & 255;
	}

	public static int blue(int color) {
		return color & 255;
	}

	public static int alfa(int color) {
		return color >>> 24;
	}
	
	public static boolean isHoldingStaff(PlayerEntity player) {
		for (Hand h : Hand.values())
			if (player.getHeldItem(h).getItem() instanceof WizardStaffItem)
				return true;
		return false;
	}
}
