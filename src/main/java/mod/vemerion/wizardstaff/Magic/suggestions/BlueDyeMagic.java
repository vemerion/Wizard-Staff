package mod.vemerion.wizardstaff.Magic.suggestions;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class BlueDyeMagic extends Magic {

	public BlueDyeMagic(String name) {
		super(name);
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
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		if (!world.isRemote) {
			cost(player);
			ServerWorld serverWorld = (ServerWorld) world;
			if (world.isRaining()) {
				serverWorld.func_241113_a_(0, 0, false, false);
			} else {
				serverWorld.func_241113_a_(0, player.getRNG().nextInt(12000) + 12000, true, player.getRNG().nextDouble() < 0.3);
			}
		}
		player.playSound(ModSounds.CHIRP, 1, soundPitch(player));

		return super.magicFinish(world, player, staff);
	}

}
