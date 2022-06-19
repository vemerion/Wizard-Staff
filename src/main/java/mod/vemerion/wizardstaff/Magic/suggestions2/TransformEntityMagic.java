package mod.vemerion.wizardstaff.Magic.suggestions2;

import com.google.gson.JsonObject;
import com.mojang.math.Vector4f;

import mod.vemerion.wizardstaff.Helper.Helper;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.MagicUtil;
import mod.vemerion.wizardstaff.Magic.RayMagic;
import mod.vemerion.wizardstaff.Magic.RegistryMatch;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.particle.MagicDustParticleData;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

public class TransformEntityMagic extends RayMagic {

	private RegistryMatch<EntityType<?>> match;
	private EntityType<?> to;
	private int particleColor;
	private Vector4f particleColorComponents;

	public TransformEntityMagic(MagicType<? extends TransformEntityMagic> type) {
		super(type);
	}

	public TransformEntityMagic setAdditionalParams(RegistryMatch<EntityType<?>> match, EntityType<?> to, int particleColor) {
		this.match = match;
		this.to = to;
		this.particleColor = particleColor;
		return this;
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		json.add("entity_match", match.write());
		MagicUtil.write(json, to, "to");
		json.addProperty("particle_color", particleColor);
	}

	@Override
	protected void readAdditional(JsonObject json) {
		match = RegistryMatch.read(ForgeRegistries.ENTITIES, GsonHelper.getAsJsonObject(json, "entity_match"));
		to = MagicUtil.read(json, ForgeRegistries.ENTITIES, "to");
		particleColor = GsonHelper.getAsInt(json, "particle_color");
	}

	@Override
	protected void encodeAdditional(FriendlyByteBuf buffer) {
		match.encode(buffer);
		MagicUtil.encode(buffer, to);
		buffer.writeInt(particleColor);
	}

	@Override
	protected void decodeAdditional(FriendlyByteBuf buffer) {
		match = RegistryMatch.decode(ForgeRegistries.ENTITIES, buffer);
		to = MagicUtil.decode(buffer);
		particleColor = buffer.readInt();
	}

	@Override
	protected Object[] getDescrArgs() {
		return new Object[] { match.getName(), to.getDescription() };
	}

	@Override
	protected ParticleOptions generateParticle(Level level, Player player, ItemStack staff, int count) {
		if (particleColorComponents == null) {
			particleColorComponents = new Vector4f(Helper.red(particleColor) / 255f, Helper.green(particleColor) / 255f,
					Helper.blue(particleColor) / 255f, Helper.alfa(particleColor) / 255f);
		}
		return new MagicDustParticleData(particleColorComponents.x(), particleColorComponents.y(),
				particleColorComponents.z(), particleColorComponents.w());
	}

	@Override
	protected void hitEntity(Level level, Player player, Entity target) {
		if (match.test(target.getType())) {
			playSoundServer(level, player, ModSounds.TRANSFORM, 0.7f, soundPitch(player));
			if (!level.isClientSide) {
				cost(player);
				Entity entity = to.create(level);
				if (target.hasCustomName())
					entity.setCustomName(target.getCustomName());
				
				entity.absMoveTo(target.getX(), target.getY(), target.getZ(), target.getYRot(),
						target.getXRot());
				level.addFreshEntity(entity);

				target.discard();
			}
		}
	}
}
