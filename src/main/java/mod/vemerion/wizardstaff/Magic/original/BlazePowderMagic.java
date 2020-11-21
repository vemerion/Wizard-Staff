package mod.vemerion.wizardstaff.Magic.original;

import java.util.Random;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class BlazePowderMagic extends Magic {
	
	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::forwardShake;
	}
	
	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BLOCK;
	}

	@Override
	public void magicTick(World world, PlayerEntity player, ItemStack staff, int count) {
		if (count % 7 == 0)
			player.playSound(Main.BURNING_SOUND, 1, soundPitch(player));
		if (!world.isRemote) {
			ServerWorld serverWorld = (ServerWorld) world;
			Random rand = player.getRNG();
			cost(player);
			Vec3d offset = Vec3d.fromPitchYaw(player.getPitchYaw());
			for (Entity e : world.getEntitiesInAABBexcluding(player, player.getBoundingBox().grow(0.3).offset(offset),
					(e) -> e instanceof LivingEntity)) {
				e.attackEntityFrom(DamageSource.causePlayerDamage(player), 2);
				e.setFire(2);
			}

			for (int i = 0; i < 3; i++) {
				double distance = rand.nextDouble() + 1;
				Vec3d direction = Vec3d.fromPitchYaw(player.rotationPitch + rand.nextFloat() * 30 - 15,
						player.rotationYaw + rand.nextFloat() * 30 - 15);
				Vec3d particlePos = player.getPositionVec().add(0, 1.5, 0).add(direction.scale(distance));
				serverWorld.spawnParticle(Main.MAGIC_FLAME_PARTICLE_TYPE, particlePos.x, particlePos.y, particlePos.z,
						0, 0, 0, 0, 1);
			}
		}
	}
	
	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::forward;
	}

}
