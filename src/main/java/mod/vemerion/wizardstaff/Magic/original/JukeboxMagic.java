package mod.vemerion.wizardstaff.Magic.original;

import java.util.List;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import mod.vemerion.wizardstaff.sound.WizardStaffTickableSound;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;

public class JukeboxMagic extends Magic {

	@Override
	public int getUseDuration(ItemStack staff) {
		return HOUR;
	}

	@Override
	public boolean isMagicItem(Item item) {
		return item == Items.JUKEBOX;
	}
	
	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
	}
	
	@Override
	public void magicStart(World world, PlayerEntity player, ItemStack staff) {
		if (world.isRemote) {
			DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
				playMusic(player, staff);
			});
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	private void playMusic(PlayerEntity player, ItemStack staff) {
		WizardStaffTickableSound sound = new WizardStaffTickableSound(player, staff);
		Minecraft.getInstance().getSoundHandler().play(sound);
	}
	
	@Override
	public void magicTick(World world, PlayerEntity player, ItemStack staff, int count) {
		List<Entity> entities = world.getEntitiesInAABBexcluding(player, player.getBoundingBox().grow(4),
				(e) -> e instanceof LivingEntity && e.isNonBoss());
		for (Entity e : entities) {
			LivingEntity living = (LivingEntity) e;
			if (!world.isRemote) {
				living.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 2, 100));
				if (e.ticksExisted % 5 == 0)
					living.swingArm(Hand.MAIN_HAND);
				if (e.ticksExisted % 7 == 0)
					living.swingArm(Hand.OFF_HAND);
			} else {
				if (e.ticksExisted % 5 == 0)
					living.limbSwingAmount = 1 + player.getRNG().nextFloat() - 0.5f;
			}
		}
		if (!world.isRemote)
			cost(player, 2 + entities.size());
	}
	
	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::swinging;
	}
	
	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::swinging;
	}

}
