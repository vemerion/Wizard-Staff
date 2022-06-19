package mod.vemerion.wizardstaff.Magic.bugfix;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import mod.vemerion.wizardstaff.Magic.CreateEntityMagic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.MagicUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ShapedCreateEntityMagic extends CreateEntityMagic {

	private static class Pos {
		int row, col;

		private Pos(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}

	List<String> shape = new ArrayList<>();
	private List<Pos> spawnPositions = new ArrayList<>();

	public ShapedCreateEntityMagic(MagicType<? extends ShapedCreateEntityMagic> type) {
		super(type);
	}

	public ShapedCreateEntityMagic setAdditionalParams(List<String> shape) {
		this.shape = shape;
		this.spawnPositions = calculateSpawnPositions(shape);
		return this;
	}

	@Override
	protected void readAdditional(JsonObject json) {
		super.readAdditional(json);
		shape = MagicUtil.readColl(json, "shape", e -> GsonHelper.convertToString(e, "row"), new ArrayList<>());
		spawnPositions = calculateSpawnPositions(shape);
	}

	@Override
	protected Object[] getDescrArgs() {
		return getNameArgs();
	}

	@Override
	protected Object[] getNameArgs() {
		return new Object[] { entity.getDescription() };
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		super.writeAdditional(json);
		MagicUtil.writeColl(json, "shape", shape, s -> new JsonPrimitive(s));
	}

	private List<Pos> calculateSpawnPositions(List<String> shape) {
		List<Pos> positions = new ArrayList<>();
		Pos playerPos = null;

		// Find player position
		for (int row = 0; row < shape.size(); row++) {
			for (int col = 0; col < shape.get(row).length(); col++) {
				char c = shape.get(row).charAt(col);
				if (c == 'p' || c == 'P') {
					if (playerPos != null)
						throw new IllegalArgumentException("Shape is only allowed to have one player position (p)");
					playerPos = new Pos(row, col);
				}
			}
		}

		if (playerPos == null)
			throw new IllegalArgumentException("Shape must have a player position (p)");

		// Find spawn positions
		for (int row = 0; row < shape.size(); row++) {
			for (int col = 0; col < shape.get(row).length(); col++) {
				char c = shape.get(row).charAt(col);
				if (c == 'x' || c == 'X')
					positions.add(new Pos(playerPos.row - row, playerPos.col - col));
			}
		}

		if (positions.isEmpty())
			throw new IllegalArgumentException("Shape must have at least on spawn position (x)");

		return positions;
	}

	@Override
	protected List<Entity> createEntities(Level level, Player player, ItemStack staff) {
		List<Entity> entities = new ArrayList<>();
		Vec3 forward = Vec3.directionFromRotation(0, player.getYRot());
		Vec3 right = forward.yRot((float) Math.PI / 2);

		for (Pos p : spawnPositions) {
			Entity e = entity.create(level);
			Vec3 pos = findValidPosition(player.position().add(forward.scale(p.row).add(right.scale(p.col))), level);
			if (pos == null)
				continue;
			e.absMoveTo(pos.x, pos.y, pos.z, player.getRandom().nextFloat() * 360, 0);
			entities.add(e);
		}
		return entities;
	}

	private Vec3 findValidPosition(Vec3 position, Level level) {
		int[] heights = new int[] { 0, -1, 1 };
		for (int height : heights) {
			BlockPos pos = new BlockPos(position).above(height);
			if (!level.getBlockState(pos).canOcclude() && level.getBlockState(pos.below()).canOcclude())
				return new Vec3(position.x, pos.getY(), position.z);
		}
		return null;
	}

}
