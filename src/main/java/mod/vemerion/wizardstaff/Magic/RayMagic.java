package mod.vemerion.wizardstaff.Magic;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Helper.Helper;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public abstract class RayMagic extends Magic {

	public RayMagic(String name) {
		super(name);
	}

	@Override
	public void magicTick(World world, PlayerEntity player, ItemStack staff, int count) {
		if (count % 10 == 0) {
			player.playSound(Main.RAY_SOUND, 0.6f, 0.95f + player.getRNG().nextFloat() * 0.05f);
		}
		if (world.isRemote) {
			Vector3d direction = Vector3d.fromPitchYaw(player.getPitchYaw());
			Entity target = Helper.findTargetLine(player.getPositionVec().add(0, 1.5, 0), direction, 7, world, player);
			if (target != null) {
				Vector3d pos = player.getPositionVec().add(0, 1.5, 0).add(direction);
				for (int i = 0; i < 25; i++) {
					world.addParticle(generateParticle(world, player, staff, count), pos.x, pos.y, pos.z, 0.1, 0.1, 0.1);
					pos = pos.add(direction.scale(0.3));
					if (target.getBoundingBox().intersects(new AxisAlignedBB(pos, pos).grow(0.25)))
						break;
				}
			}
		}
	}
	
	protected abstract IParticleData generateParticle(World world, PlayerEntity player, ItemStack staff, int count);
	
	@Override
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		if (!world.isRemote) {
			Entity target = Helper.findTargetLine(player.getPositionVec().add(0, 1.5, 0),
					Vector3d.fromPitchYaw(player.getPitchYaw()), 7, world, player);
			if (target != null) {
				hitEntity(world, player, target);
			}
		}
		return super.magicFinish(world, player, staff);
	}
	
	protected abstract void hitEntity(World world, PlayerEntity player, Entity target);

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::forward;
	}

	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::forwardShake;
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BLOCK;
	}

}
