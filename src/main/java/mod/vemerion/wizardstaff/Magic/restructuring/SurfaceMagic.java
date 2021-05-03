package mod.vemerion.wizardstaff.Magic.restructuring;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.capability.Wizard;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class SurfaceMagic extends Magic {

	public SurfaceMagic(MagicType type) {
		super(type);
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::buildupMagic;
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
	public ActionResultType magicInteractBlock(ItemUseContext context) {
		PlayerEntity player = context.getPlayer();
		if (!context.getWorld().isRemote)
			Wizard.getWizardOptional(player).ifPresent(w -> w.setSurfacePos(context.getPos()));
		player.playSound(SoundEvents.BLOCK_STONE_PLACE, 1, soundPitch(player));
		return ActionResultType.SUCCESS;
	}
	
	@Override
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		if (!world.isRemote)
			Wizard.getWizardOptional(player).ifPresent(w -> {
				int count = w.createSurface(world, player);
				if (count > 0)
					cost(player, count);
				else
					playSoundServer(world, player, ModSounds.POOF, 1, soundPitch(player));
			});
		return super.magicFinish(world, player, staff);
	}

}
