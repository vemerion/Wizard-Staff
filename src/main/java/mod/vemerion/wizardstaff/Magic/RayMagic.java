package mod.vemerion.wizardstaff.Magic;

import mod.vemerion.wizardstaff.Helper.Helper;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public abstract class RayMagic extends Magic {

	public RayMagic(MagicType<? extends RayMagic> type) {
		super(type);
	}
	
	protected float getRange() {
		return 7;
	}

	@Override
	public void magicTick(Level level, Player player, ItemStack staff, int count) {
		if (count % 10 == 0) {
			player.playSound(ModSounds.RAY, 0.6f, 0.95f + player.getRandom().nextFloat() * 0.05f);
		}
		if (level.isClientSide) {
			Vec3 direction = Vec3.directionFromRotation(player.getRotationVector());
			Entity target = Helper.findTargetLine(player.position().add(0, 1.5, 0), direction, getRange(), level, player);
			if (target != null) {
				Vec3 pos = player.position().add(0, 1.5, 0).add(direction);
				for (int i = 0; i < 25; i++) {
					level.addParticle(generateParticle(level, player, staff, count), pos.x, pos.y, pos.z, 0.1, 0.1, 0.1);
					pos = pos.add(direction.scale(0.3));
					if (target.getBoundingBox().intersects(new AABB(pos, pos).inflate(0.25)))
						break;
				}
			}
		}
	}
	
	protected abstract ParticleOptions generateParticle(Level level, Player player, ItemStack staff, int count);
	
	@Override
	public ItemStack magicFinish(Level level, Player player, ItemStack staff) {
		if (!level.isClientSide) {
			Entity target = Helper.findTargetLine(player.position().add(0, 1.5, 0),
					Vec3.directionFromRotation(player.getRotationVector()), getRange(), level, player);
			if (target != null) {
				hitEntity(level, player, target);
			}
		}
		return super.magicFinish(level, player, staff);
	}
	
	protected abstract void hitEntity(Level level, Player player, Entity target);

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::forward;
	}

	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::forwardShake;
	}

	@Override
	public UseAnim getUseAnim(ItemStack stack) {
		return UseAnim.BLOCK;
	}

}
