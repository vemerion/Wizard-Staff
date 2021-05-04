package mod.vemerion.wizardstaff.Helper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
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
}
