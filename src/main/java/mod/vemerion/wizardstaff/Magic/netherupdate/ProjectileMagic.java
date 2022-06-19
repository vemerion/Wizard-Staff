package mod.vemerion.wizardstaff.Magic.netherupdate;

import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.MagicUtil;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class ProjectileMagic extends Magic {

	private EntityType<?> projectileType;
	private SoundEvent sound;
	private float speed;

	public ProjectileMagic(MagicType<? extends ProjectileMagic> type) {
		super(type);
	}
	
	public ProjectileMagic setAdditionalParams(EntityType<?> projectileType, SoundEvent sound, float speed) {
		this.projectileType = projectileType;
		this.sound = sound;
		this.speed = speed;
		return this;
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::forwardBuildup;
	}

	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::forwardShake;
	}

	@Override
	public UseAnim getUseAnim(ItemStack stack) {
		return UseAnim.BLOCK;
	}

	@Override
	protected void readAdditional(JsonObject json) {
		projectileType = MagicUtil.read(json, ForgeRegistries.ENTITIES, "projectile");
		sound = MagicUtil.read(json, ForgeRegistries.SOUND_EVENTS, "sound");
		speed = GsonHelper.getAsFloat(json, "speed");
	}
	
	@Override
	protected void writeAdditional(JsonObject json) {
		MagicUtil.write(json, projectileType, "projectile");
		MagicUtil.write(json, sound, "sound");
		json.addProperty("speed", speed);
	}

	@Override
	public void encodeAdditional(FriendlyByteBuf buffer) {
		MagicUtil.encode(buffer, projectileType);
		MagicUtil.encode(buffer, sound);
		buffer.writeFloat(speed);
	}
	

	@Override
	public void decodeAdditional(FriendlyByteBuf buffer) {
		projectileType = MagicUtil.decode(buffer);
		sound = MagicUtil.decode(buffer);
		speed = buffer.readFloat();
	}

	@Override
	protected Object[] getDescrArgs() {
		return new Object[] { projectileType.getDescription() };
	}

	@Override
	protected Object[] getNameArgs() {
		return new Object[] { projectileType.getDescription() };
	}

	@Override
	public ItemStack magicFinish(Level level, Player player, ItemStack staff) {
		player.playSound(sound, 1, soundPitch(player));
		if (!level.isClientSide) {
			Vec3 direction = Vec3.directionFromRotation(player.getRotationVector());
			Vec3 position = player.position().add(direction.x() * 1, 1.2, direction.z() * 1);
			cost(player);
			Entity entity = projectileType.create(level);
			entity.setPos(position.x, position.y, position.z);
			if (entity instanceof Projectile) {
				Projectile projectile = (Projectile) entity;
				projectile.setOwner(player);
				projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, speed, 0); // shoot()
			}
			level.addFreshEntity(entity);
		}

		return super.magicFinish(level, player, staff);
	}

}
