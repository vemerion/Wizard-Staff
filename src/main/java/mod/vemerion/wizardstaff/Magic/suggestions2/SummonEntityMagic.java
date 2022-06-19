package mod.vemerion.wizardstaff.Magic.suggestions2;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.Magic.CreateEntityMagic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

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
		count = GsonHelper.getAsInt(json, "count");
	}

	@Override
	protected void encodeAdditional(FriendlyByteBuf buffer) {
		super.encodeAdditional(buffer);
		buffer.writeInt(count);
	}

	@Override
	protected void decodeAdditional(FriendlyByteBuf buffer) {
		super.decodeAdditional(buffer);
		count = buffer.readInt();
	}

	@Override
	protected Object[] getNameArgs() {
		return new Object[] { entity.getDescription() };
	}

	@Override
	protected Object[] getDescrArgs() {
		return new Object[] { new TextComponent(String.valueOf(count)), entity.getDescription() };
	}

	private Vec3 nearby(Player player) {
		return player.position().add(randCoord(player), randCoord(player) + 1, randCoord(player));
	}

	private double randCoord(Player player) {
		return (player.getRandom().nextDouble() - 0.5) * 2;
	}

	@Override
	protected List<Entity> createEntities(Level level, Player player, ItemStack staff) {
		List<Entity> entities = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			Entity e = entity.create(level);
			Vec3 pos = nearby(player);
			e.absMoveTo(pos.x, pos.y, pos.z, player.getRandom().nextFloat() * 360, 0);
			entities.add(e);
		}
		return entities;
	}
}
