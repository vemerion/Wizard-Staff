package mod.vemerion.wizardstaff.Helper;

import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class Helper {
	public static Entity findTargetLine(Vec3 start, Vec3 direction, float distance, Level level, LivingEntity caster) {
		AABB box = new AABB(start, start).inflate(0.25);
		for (int i = 0; i < distance * 2; i++) {
			for (Entity e : level.getEntities(caster, box, (e) -> e instanceof LivingEntity)) {
				return e;
			}
			box = box.move(direction);
		}
		return null;
	}

	public static BlockHitResult blockRay(Level level, Player player, float distance) {
		Vec3 direction = Vec3.directionFromRotation(player.getRotationVector());
		Vec3 start = player.position().add(0, player.getEyeHeight(), 0).add(direction.scale(0.2));
		Vec3 stop = start.add(direction.scale(distance));
		return level.clip(new ClipContext(start, stop, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
	}

	public static BlockPos randPosInBox(AABB box, Random rand) {
		return new BlockPos(rand.nextInt((int) box.minX, (int) box.maxX), rand.nextInt((int) box.minY, (int) box.maxY),
				rand.nextInt((int) box.minZ, (int) box.maxZ));
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
}
