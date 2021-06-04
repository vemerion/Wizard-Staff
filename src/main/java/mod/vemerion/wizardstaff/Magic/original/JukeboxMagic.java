package mod.vemerion.wizardstaff.Magic.original;

import java.util.List;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.network.JukeboxMagicMessage;
import mod.vemerion.wizardstaff.network.Network;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

public class JukeboxMagic extends Magic {
	public JukeboxMagic(MagicType<? extends JukeboxMagic> type) {
		super(type);
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
	}

	@Override
	public void magicStart(World world, PlayerEntity player, ItemStack staff) {
		if (!world.isRemote)
			Network.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player),
					new JukeboxMagicMessage(player.getUniqueID()));
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

}
