package mod.vemerion.wizardstaff.Magic.suggestions;

import java.util.List;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

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
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BLOCK;
	}
	
	@Override
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		if (!world.isRemote) {
			List<LivingEntity> nearby = world.getEntitiesWithinAABB(LivingEntity.class, player.getBoundingBox().grow(4), e -> e != player);
			if (!nearby.isEmpty()) {
				LivingEntity target = nearby.get(player.getRNG().nextInt(nearby.size()));
				ShulkerBulletEntity bullet = new ShulkerBulletEntity(world, player, target, Axis.Y);
				world.addEntity(bullet);
				cost(player);
				playSoundServer(world, player, SoundEvents.ENTITY_SHULKER_SHOOT, 1, soundPitch(player));
			}
		}
		
		return super.magicFinish(world, player, staff);
	}

}
