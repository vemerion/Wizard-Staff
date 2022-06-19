package mod.vemerion.wizardstaff.Magic.swap;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.MagicUtil;
import mod.vemerion.wizardstaff.Magic.RayMagic;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.particle.MagicDustParticleData;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SwapPositionMagic extends RayMagic {

	private Set<ResourceLocation> blacklist;
	private float range;

	public SwapPositionMagic(MagicType<? extends SwapPositionMagic> type) {
		super(type);
	}

	public SwapPositionMagic setAdditionalParams(Set<ResourceLocation> blacklist, float range) {
		this.blacklist = blacklist;
		this.range = range;
		return this;
	}

	@Override
	protected void readAdditional(JsonObject json) {
		blacklist = MagicUtil.readColl(json, "blacklist",
				e -> new ResourceLocation(GsonHelper.convertToString(e, "entity name")), new HashSet<>());
		range = GsonHelper.getAsFloat(json, "range");
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		MagicUtil.writeColl(json, "blacklist", blacklist, r -> new JsonPrimitive(r.toString()));
		json.addProperty("range", range);
	}

	@Override
	protected void encodeAdditional(FriendlyByteBuf buffer) {
		buffer.writeFloat(range);
	}

	@Override
	protected void decodeAdditional(FriendlyByteBuf buffer) {
		range = buffer.readFloat();
	}

	@Override
	protected float getRange() {
		return range;
	}

	@Override
	protected ParticleOptions generateParticle(Level level, Player player, ItemStack staff, int count) {
		Random rand = player.getRandom();
		float duration = getUseDuration(staff);
		float progress = (duration - count) / duration;
		float alfa = Math.min(1, progress * 1.5f);
		return new MagicDustParticleData(0.9f + rand.nextFloat() * 0.1f, 0.9f + rand.nextFloat() * 0.1f, 0, alfa);
	}

	@Override
	protected void hitEntity(Level level, Player player, Entity target) {
		if (!(target instanceof LivingEntity) || blacklist.contains(target.getType().getRegistryName())) {
			playSoundServer(level, player, ModSounds.POOF, 1, soundPitch(player));
			return;
		}

		cost(player);
		Vec3 start = player.position();
		player.teleportTo(target.getX(), target.getY(), target.getZ());
		target.teleportTo(start.x, start.y, start.z);
		playSoundServer(level, player, ModSounds.SWAP, 1, soundPitch(player));
	}

}
