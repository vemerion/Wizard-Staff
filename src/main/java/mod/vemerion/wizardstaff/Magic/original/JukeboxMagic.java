package mod.vemerion.wizardstaff.Magic.original;

import java.util.List;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import mod.vemerion.wizardstaff.sound.WizardStaffTickableSound;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

public class JukeboxMagic extends Magic {
	public JukeboxMagic(MagicType type) {
		super(type);
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
	}
	
	@Override
	public void magicStart(World world, PlayerEntity player, ItemStack staff) {
		if (world.isRemote) {
			DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> PlayMusic.play(player, staff));
		}
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
		if (!world.isRemote && !entities.isEmpty())
			cost(player);
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::swinging;
	}
	
	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::swinging;
	}

	private static class PlayMusic {
		private static DistExecutor.SafeRunnable play(PlayerEntity player, ItemStack staff) {
			return new DistExecutor.SafeRunnable() {
				private static final long serialVersionUID = 1L;

				@Override
				public void run() {
					WizardStaffTickableSound sound = new WizardStaffTickableSound(player, staff);
					Minecraft.getInstance().getSoundHandler().play(sound);
				}
			};
		}
	}

}
