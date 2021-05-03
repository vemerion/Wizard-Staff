package mod.vemerion.wizardstaff.Magic.spellbookupdate;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class BookshelfMagic extends Magic {

	public BookshelfMagic(MagicType type) {
		super(type);
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::spinMagic;
	}

	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::spinMagic;
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
	}

	@Override
	public void magicTick(World world, PlayerEntity player, ItemStack staff, int count) {
		if (!world.isRemote) {
			cost(player);
			if (count % 10 == 0) {
				BrainUtil.spawnItemNearEntity(player, new ItemStack(Items.BOOK), nearbyPosition(player));
				playSoundServer(world, player, ModSounds.PAGE_TURN, 1, soundPitch(player));
			}
		} else {
			addParticle(world, player);
		}
	}

	private void addParticle(World world, PlayerEntity player) {
		Vector3d pos = player.getPositionVec().add((player.getRNG().nextDouble() - 0.5) * 2,
				player.getRNG().nextDouble() * 1.5, (player.getRNG().nextDouble() - 0.5) * 2);
		double speedX = (player.getRNG().nextDouble() - 0.5) * 2;
		double speedY = player.getRNG().nextDouble() - 0.5;
		double speedZ = (player.getRNG().nextDouble() - 0.5) * 2;

		world.addParticle(ParticleTypes.ENCHANT, pos.getX(), pos.getY() + 1, pos.getZ(), speedX, speedY, speedZ);
	}

	private Vector3d nearbyPosition(PlayerEntity player) {
		double x = (player.getRNG().nextDouble() - 0.5) * 0.3;
		double y = 0;
		double z = (player.getRNG().nextDouble() - 0.5) * 0.3;
		return player.getPositionVec().add(x, y, z);
	}

}
