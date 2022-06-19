package mod.vemerion.wizardstaff.Magic.netherupdate;

import java.util.Random;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class GhastTearMagic extends Magic {

	public GhastTearMagic(MagicType<? extends GhastTearMagic> type) {
		super(type);
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::spinMagic;
	}

	@Override
	public void magicTick(Level level, Player player, ItemStack staff, int count) {
		if (count % 13 == 0)
			player.playSound(ModSounds.SNIFFLE, 0.6f, soundPitch(player) * 2);
		if (!level.isClientSide) {
			cost(player);
			player.clearFire();
			if (player.tickCount % 5 == 0)
				fillCauldrons(level, player);
			if (player.tickCount % 15 == 0)
				player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20, 0, true, false, false));
			if (player.tickCount % 5 == 0)
				cry(player);
		}
	}

	private void fillCauldrons(Level level, Player player) {
		BlockPos.betweenClosedStream(player.getBoundingBox().inflate(1)).forEach(p -> {
			BlockState state = level.getBlockState(p);
			if (state.getBlock() instanceof AbstractCauldronBlock cauldron) {
				cauldron.handlePrecipitation(state, level, p, Biome.Precipitation.RAIN);
			}
		});
	}

	private void cry(Player player) {
		Random rand = player.getRandom();
		Vec3 direction = Vec3.directionFromRotation(player.getRotationVector());
		Vec3 right = direction.yRot(-90);
		Vec3 center = player.position().add(direction.x * 0.4, 1.6 + direction.y * 0.6, direction.z * 0.4);
		Vec3 origin = center.add(right.x * (rand.nextDouble() - 0.5), rand.nextDouble() - 0.5,
				right.z * (rand.nextDouble() - 0.5));
		for (int j = 0; j < 10; j++) {
			Vec3 position = origin.add(right.x * (rand.nextDouble() * 0.2 - 0.1), rand.nextDouble() * 0.01 - 0.005,
					right.z * (rand.nextDouble() * 0.2 - 0.1));
			((ServerLevel) player.level).sendParticles(ParticleTypes.SPLASH, position.x, position.y, position.z, 1, 0,
					0, 0, 0.2);
		}
	}

	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::spinMagic;
	}

	@Override
	public UseAnim getUseAnim(ItemStack stack) {
		return UseAnim.NONE;
	}
}
