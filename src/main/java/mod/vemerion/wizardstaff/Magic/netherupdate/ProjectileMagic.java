package mod.vemerion.wizardstaff.Magic.netherupdate;

import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.MagicUtil;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

public class ProjectileMagic extends Magic {

	private EntityType<?> projectileType;
	private SoundEvent sound;
	private float speed;

	public ProjectileMagic(MagicType type) {
		super(type);
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
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BLOCK;
	}

	@Override
	protected void readAdditional(JsonObject json) {
		projectileType = MagicUtil.read(json, ForgeRegistries.ENTITIES, "projectile");
		sound = MagicUtil.read(json, ForgeRegistries.SOUND_EVENTS, "sound");
		speed = JSONUtils.getFloat(json, "speed");
	}

	@Override
	public void encodeAdditional(PacketBuffer buffer) {
		MagicUtil.encode(buffer, projectileType);
		MagicUtil.encode(buffer, sound);
		buffer.writeFloat(speed);
	}
	

	@Override
	public void decodeAdditional(PacketBuffer buffer) {
		projectileType = MagicUtil.decode(buffer, ForgeRegistries.ENTITIES);
		sound = MagicUtil.decode(buffer, ForgeRegistries.SOUND_EVENTS);
		speed = buffer.readFloat();
	}

	@Override
	protected Object[] getDescrArgs() {
		return new Object[] { projectileType.getName() };
	}

	@Override
	protected Object[] getNameArgs() {
		return new Object[] { projectileType.getName() };
	}

	@Override
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		player.playSound(sound, 1, soundPitch(player));
		if (!world.isRemote) {
			Vector3d direction = Vector3d.fromPitchYaw(player.getPitchYaw());
			Vector3d position = player.getPositionVec().add(direction.getX() * 1, 1.2, direction.getZ() * 1);
			cost(player);
			Entity entity = projectileType.create(world);
			entity.setPosition(position.x, position.y, position.z);
			if (entity instanceof ProjectileEntity) {
				ProjectileEntity projectile = (ProjectileEntity) entity;
				projectile.setShooter(player);
				projectile.func_234612_a_(player, player.rotationPitch, player.rotationYaw, 0, speed, 0); // shoot()
			}
			world.addEntity(entity);
		}

		return super.magicFinish(world, player, staff);
	}

}
