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
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

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
				e -> new ResourceLocation(JSONUtils.getString(e, "entity name")), new HashSet<>());
		range = JSONUtils.getFloat(json, "range");
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		MagicUtil.writeColl(json, "blacklist", blacklist, r -> new JsonPrimitive(r.toString()));
		json.addProperty("range", range);
	}

	@Override
	protected void encodeAdditional(PacketBuffer buffer) {
		buffer.writeFloat(range);
	}

	@Override
	protected void decodeAdditional(PacketBuffer buffer) {
		range = buffer.readFloat();
	}

	@Override
	protected float getRange() {
		return range;
	}

	@Override
	protected IParticleData generateParticle(World world, PlayerEntity player, ItemStack staff, int count) {
		Random rand = player.getRNG();
		float duration = getUseDuration(staff);
		float progress = (duration - count) / duration;
		float alfa = Math.min(1, progress * 1.5f);
		return new MagicDustParticleData(0.9f + rand.nextFloat() * 0.1f, 0.9f + rand.nextFloat() * 0.1f, 0, alfa);
	}

	@Override
	protected void hitEntity(World world, PlayerEntity player, Entity target) {
		if (!(target instanceof LivingEntity) || blacklist.contains(target.getType().getRegistryName())) {
			playSoundServer(world, player, ModSounds.POOF, 1, soundPitch(player));
			return;
		}

		cost(player);
		Vector3d start = player.getPositionVec();
		player.setPositionAndUpdate(target.getPosX(), target.getPosY(), target.getPosZ());
		target.setPositionAndUpdate(start.x, start.y, start.z);
		playSoundServer(world, player, ModSounds.SWAP, 1, soundPitch(player));
	}

}
