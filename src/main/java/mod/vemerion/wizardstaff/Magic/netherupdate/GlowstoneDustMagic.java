package mod.vemerion.wizardstaff.Magic.netherupdate;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class GlowstoneDustMagic extends Magic {

	public GlowstoneDustMagic(String name) {
		super(name);
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
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
	}
	
	@Override
	public void magicTick(World world, PlayerEntity player, ItemStack staff, int count) {
		if (count % 11 == 0)
			player.playSound(Main.RADAR_SOUND, 0.35f, 1);
		super.magicTick(world, player, staff, count);
	}

	@Override
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		if (!world.isRemote) {
			cost(player);
			for (LivingEntity e : world.getEntitiesWithinAABB(LivingEntity.class, player.getBoundingBox().grow(10),
					e -> e != player)) {
				e.addPotionEffect(new EffectInstance(Effects.GLOWING, 20 * 10));
			}
		}
		return super.magicFinish(world, player, staff);
	}

}
