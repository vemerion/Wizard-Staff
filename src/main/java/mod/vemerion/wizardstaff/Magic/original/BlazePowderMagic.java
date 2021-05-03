package mod.vemerion.wizardstaff.Magic.original;

import java.util.Random;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.init.ModParticles;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class BlazePowderMagic extends Magic {
	
	public BlazePowderMagic(MagicType type) {
		super(type);
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
	public void magicTick(World world, PlayerEntity player, ItemStack staff, int count) {
		if (count % 7 == 0)
			player.playSound(ModSounds.BURNING, 1, soundPitch(player));
		if (!world.isRemote) {
			ServerWorld serverWorld = (ServerWorld) world;
			Random rand = player.getRNG();
			Vector3d offset = Vector3d.fromPitchYaw(player.getPitchYaw());
			cost(player);
			for (Entity e : world.getEntitiesInAABBexcluding(player, player.getBoundingBox().grow(0.3).offset(offset),
					(e) -> e instanceof LivingEntity)) {
				e.attackEntityFrom(Magic.magicDamage(player), 2);
				e.setFire(2);
			}

			for (int i = 0; i < 3; i++) {
				double distance = rand.nextDouble() + 1;
				Vector3d direction = Vector3d.fromPitchYaw(player.rotationPitch + rand.nextFloat() * 30 - 15,
						player.rotationYaw + rand.nextFloat() * 30 - 15);
				Vector3d particlePos = player.getPositionVec().add(0, 1.5, 0).add(direction.scale(distance));
				serverWorld.spawnParticle(ModParticles.MAGIC_FLAME_PARTICLE, particlePos.x, particlePos.y, particlePos.z,
						0, 0, 0, 0, 1);
			}
		}
	}
	
	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::forward;
	}

}
