package mod.vemerion.wizardstaff.Magic.suggestions;

import java.util.List;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.core.Direction.Axis;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class ShulkerBulletMagic extends Magic {

	public ShulkerBulletMagic(MagicType<? extends ShulkerBulletMagic> type) {
		super(type);
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::buildupMagic;
	}

	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::buildupMagic;
	}

	@Override
	public UseAnim getUseAnim(ItemStack stack) {
		return UseAnim.BLOCK;
	}
	
	@Override
	public ItemStack magicFinish(Level level, Player player, ItemStack staff) {
		if (!level.isClientSide) {
			List<LivingEntity> nearby = level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(4), e -> e != player);
			if (!nearby.isEmpty()) {
				LivingEntity target = nearby.get(player.getRandom().nextInt(nearby.size()));
				ShulkerBullet bullet = new ShulkerBullet(level, player, target, Axis.Y);
				level.addFreshEntity(bullet);
				cost(player);
				playSoundServer(level, player, SoundEvents.SHULKER_SHOOT, 1, soundPitch(player));
			}
		}
		
		return super.magicFinish(level, player, staff);
	}

}
