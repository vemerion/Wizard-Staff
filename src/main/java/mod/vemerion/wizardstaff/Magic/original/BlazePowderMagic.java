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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class BlazePowderMagic extends Magic {
	
	public BlazePowderMagic(MagicType<? extends BlazePowderMagic> type) {
		super(type);
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
	public void magicTick(Level level, Player player, ItemStack staff, int count) {
		if (count % 7 == 0)
			player.playSound(ModSounds.BURNING, 1, soundPitch(player));
		if (!level.isClientSide) {
			ServerLevel serverWorld = (ServerLevel) level;
			Random rand = player.getRandom();
			Vec3 offset = Vec3.directionFromRotation(player.getRotationVector());
			cost(player);
			for (Entity e : level.getEntities(player, player.getBoundingBox().inflate(0.3).move(offset),
					(e) -> e instanceof LivingEntity)) {
				e.hurt(Magic.magicDamage(player), 2);
				e.setSecondsOnFire(2);
			}

			for (int i = 0; i < 3; i++) {
				double distance = rand.nextDouble() + 1;
				Vec3 direction = Vec3.directionFromRotation(player.getXRot() + rand.nextFloat() * 30 - 15,
						player.getYRot() + rand.nextFloat() * 30 - 15);
				Vec3 particlePos = player.position().add(0, 1.5, 0).add(direction.scale(distance));
				serverWorld.sendParticles(ModParticles.MAGIC_FLAME_PARTICLE, particlePos.x, particlePos.y, particlePos.z,
						0, 0, 0, 0, 1);
			}
		}
	}
	
	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::forward;
	}

}
