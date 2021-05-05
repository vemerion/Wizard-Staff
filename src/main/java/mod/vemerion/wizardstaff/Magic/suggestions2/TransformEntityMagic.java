package mod.vemerion.wizardstaff.Magic.suggestions2;

import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.Helper.Helper;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.MagicUtil;
import mod.vemerion.wizardstaff.Magic.RayMagic;
import mod.vemerion.wizardstaff.particle.MagicDustParticleData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

public class TransformEntityMagic extends RayMagic {

	private EntityType<?> from;
	private EntityType<?> to;
	private int particleColor;
	private Vector4f particleColorComponents;

	public TransformEntityMagic(MagicType<? extends TransformEntityMagic> type) {
		super(type);
	}

	public TransformEntityMagic setAdditionalParams(EntityType<?> from, EntityType<?> to, int particleColor) {
		this.from = from;
		this.to = to;
		this.particleColor = particleColor;
		return this;
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		MagicUtil.write(json, from, "from");
		MagicUtil.write(json, to, "to");
		json.addProperty("particle_color", particleColor);
	}

	@Override
	protected void readAdditional(JsonObject json) {
		from = MagicUtil.read(json, ForgeRegistries.ENTITIES, "from");
		to = MagicUtil.read(json, ForgeRegistries.ENTITIES, "to");
		particleColor = JSONUtils.getInt(json, "particle_color");
	}

	@Override
	protected void encodeAdditional(PacketBuffer buffer) {
		MagicUtil.encode(buffer, from);
		MagicUtil.encode(buffer, to);
		buffer.writeInt(particleColor);
	}

	@Override
	protected void decodeAdditional(PacketBuffer buffer) {
		from = MagicUtil.decode(buffer, ForgeRegistries.ENTITIES);
		to = MagicUtil.decode(buffer, ForgeRegistries.ENTITIES);
		particleColor = buffer.readInt();
	}

	@Override
	protected IParticleData generateParticle(World world, PlayerEntity player, ItemStack staff, int count) {
		if (particleColorComponents == null) {
			particleColorComponents = new Vector4f(Helper.red(particleColor) / 255f, Helper.green(particleColor) / 255f,
					Helper.blue(particleColor) / 255f, Helper.alfa(particleColor) / 255f);
		}
		return new MagicDustParticleData(particleColorComponents.getX(), particleColorComponents.getY(),
				particleColorComponents.getZ(), particleColorComponents.getW());
	}

	@Override
	protected void hitEntity(World world, PlayerEntity player, Entity target) {
		if (target.getType() == from) {
			if (!world.isRemote) {
				cost(player);
				Entity entity = to.create(world);
				entity.setPositionAndRotation(target.getPosX(), target.getPosY(), target.getPosZ(), target.rotationYaw,
						target.rotationPitch);
				world.addEntity(entity);

				target.remove();
			}
		}
	}
}
