package mod.vemerion.wizardstaff.Magic.netherupdate;

import java.util.Random;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class GhastTearMagic extends Magic {

	@Override
	public int getUseDuration(ItemStack staff) {
		return HOUR;
	}

	@Override
	public boolean isMagicItem(Item item) {
		return item == Items.GHAST_TEAR;
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::spinMagic;
	}

	@Override
	public void magicTick(World world, PlayerEntity player, ItemStack staff, int count) {
		if (count % 13 == 0)
			player.playSound(Main.SNIFFLE_SOUND, 0.6f, soundPitch(player) * 2);
		if (!world.isRemote) {
			cost(player, 0.5);
			player.extinguish();
			if (player.ticksExisted % 15 == 0)
				player.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 20, 0, true, false, false));
			if (player.ticksExisted % 5 == 0)
				cry(player);
		}
	}

	private void cry(PlayerEntity player) {
		Random rand = player.getRNG();
		Vec3d direction = Vec3d.fromPitchYaw(player.getPitchYaw());
		Vec3d right = direction.rotateYaw(-90);
		Vec3d center = player.getPositionVec().add(direction.x * 0.4, 1.6 + direction.y * 0.6, direction.z * 0.4);
		Vec3d origin = center.add(right.x * (rand.nextDouble() - 0.5), rand.nextDouble() - 0.5,
				right.z * (rand.nextDouble() - 0.5));
		for (int j = 0; j < 10; j++) {
			Vec3d position = origin.add(right.x * (rand.nextDouble() * 0.2 - 0.1), rand.nextDouble() * 0.01 - 0.005,
					right.z * (rand.nextDouble() * 0.2 - 0.1));
			((ServerWorld) player.world).spawnParticle(ParticleTypes.SPLASH, position.x, position.y, position.z, 1, 0,
					0, 0, 0.2);
		}
	}

	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::spinMagic;
	}
	
	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
	}
}
