package mod.vemerion.wizardstaff.Magic.original;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.entity.PumpkinMagicEntity;
import mod.vemerion.wizardstaff.init.ModEntities;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class CarvedPumpkinMagic extends Magic {
	
	public CarvedPumpkinMagic(MagicType<? extends CarvedPumpkinMagic> type) {
		super(type);
	}

	@Override
	public UseAnim getUseAnim(ItemStack stack) {
		return UseAnim.BLOCK;
	}

	@Override
	public ItemStack magicFinish(Level level, Player player, ItemStack staff) {
		player.playSound(ModSounds.PUMPKIN_MAGIC, 0.2f, soundPitch(player));
		if (!level.isClientSide) {
			cost(player);
			PumpkinMagicEntity entity = new PumpkinMagicEntity(ModEntities.PUMPKIN_MAGIC, level, player);
			entity.absMoveTo(player.getX(), player.getY() + 2, player.getZ(), player.getYRot(),
					0);
			level.addFreshEntity(entity);
		}
		return super.magicFinish(level, player, staff);
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::forward;
	}
	
	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::forwardShake;
	}

}
