package mod.vemerion.wizardstaff.Magic.bugfix;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import mod.vemerion.wizardstaff.Magic.CreateEntityMagic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.MagicUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

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
		shape = MagicUtil.readColl(json, "shape", e -> JSONUtils.getString(e, "row"), new ArrayList<>());
		spawnPositions = calculateSpawnPositions(shape);
	}

	@Override
	protected Object[] getDescrArgs() {
		return getNameArgs();
	}

	@Override
	protected Object[] getNameArgs() {
		return new Object[] { entity.getName() };
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
	protected List<Entity> createEntities(World world, PlayerEntity player, ItemStack staff) {
		List<Entity> entities = new ArrayList<>();
		Vector3d forward = Vector3d.fromPitchYaw(0, player.rotationYaw);
		Vector3d right = forward.rotateYaw((float) Math.PI / 2);

		for (Pos p : spawnPositions) {
			Entity e = entity.create(world);
			Vector3d pos = findValidPosition(player.getPositionVec().add(forward.scale(p.row).add(right.scale(p.col))),
					world);
			if (pos == null)
				continue;
			e.setPositionAndRotation(pos.x, pos.y, pos.z, player.getRNG().nextFloat() * 360, 0);
			entities.add(e);
		}
		return entities;
	}

	private Vector3d findValidPosition(Vector3d position, World world) {
		int[] heights = new int[] { 0, -1, 1 };
		for (int height : heights) {
			BlockPos pos = new BlockPos(position).up(height);
			if (!world.getBlockState(pos).isSolid() && world.getBlockState(pos.down()).isSolid())
				return new Vector3d(position.x, pos.getY(), position.z);
		}
		return null;
	}

}
