package mod.vemerion.wizardstaff.Helper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class Helper {
	public static Entity findTargetLine(Vector3d start, Vector3d direction, float distance, World world, LivingEntity caster) {
		AxisAlignedBB box = new AxisAlignedBB(start, start).grow(0.25);
		for (int i = 0; i < distance * 2; i++) {
			for (Entity e : world.getEntitiesInAABBexcluding(caster, box, (e) -> e instanceof LivingEntity)) {
				return e;
			}
			box = box.offset(direction);
		}
		return null;
	}
}
