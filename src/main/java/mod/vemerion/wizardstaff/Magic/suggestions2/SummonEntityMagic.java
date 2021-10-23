package mod.vemerion.wizardstaff.Magic.suggestions2;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.Magic.CreateEntityMagic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class SummonEntityMagic extends CreateEntityMagic {

	private int count;

	public SummonEntityMagic(MagicType<? extends SummonEntityMagic> type) {
		super(type);
	}

	public SummonEntityMagic setAdditionalParams(int count) {
		this.count = count;
		return this;
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		super.writeAdditional(json);
		json.addProperty("count", count);
	}

	@Override
	protected void readAdditional(JsonObject json) {
		super.readAdditional(json);
		count = JSONUtils.getInt(json, "count");
	}

	@Override
	protected void encodeAdditional(PacketBuffer buffer) {
		super.encodeAdditional(buffer);
		buffer.writeInt(count);
	}

	@Override
	protected void decodeAdditional(PacketBuffer buffer) {
		super.decodeAdditional(buffer);
		count = buffer.readInt();
	}

	@Override
	protected Object[] getNameArgs() {
		return new Object[] { entity.getName() };
	}

	@Override
	protected Object[] getDescrArgs() {
		return new Object[] { new StringTextComponent(String.valueOf(count)), entity.getName() };
	}

	private Vector3d nearby(PlayerEntity player) {
		return player.getPositionVec().add(randCoord(player), randCoord(player) + 1, randCoord(player));
	}

	private double randCoord(PlayerEntity player) {
		return (player.getRNG().nextDouble() - 0.5) * 2;
	}

	@Override
	protected List<Entity> createEntities(World world, PlayerEntity player, ItemStack staff) {
		List<Entity> entities = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			Entity e = entity.create(world);
			Vector3d pos = nearby(player);
			e.setPositionAndRotation(pos.x, pos.y, pos.z, player.getRNG().nextFloat() * 360, 0);
			entities.add(e);
		}
		return entities;
	}
}
