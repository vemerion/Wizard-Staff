package mod.vemerion.wizardstaff.Magic.netherupdate;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class GlowstoneDustMagic extends Magic {

	public GlowstoneDustMagic(MagicType<? extends GlowstoneDustMagic> type) {
		super(type);
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::circling;
	}
	
	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::swinging;
	}
	
	@Override
	public UseAnim getUseAnim(ItemStack stack) {
		return UseAnim.NONE;
	}
	
	@Override
	public void magicTick(Level level, Player player, ItemStack staff, int count) {
		if (count % 11 == 0)
			player.playSound(ModSounds.RADAR, 0.35f, 1);
		super.magicTick(level, player, staff, count);
	}

	@Override
	public ItemStack magicFinish(Level level, Player player, ItemStack staff) {
		if (!level.isClientSide) {
			cost(player);
			for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(10),
					e -> e != player)) {
				e.addEffect(new MobEffectInstance(MobEffects.GLOWING, 20 * 10));
			}
		}
		return super.magicFinish(level, player, staff);
	}

}
