package mod.vemerion.wizardstaff.Magic.wild;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class DeathTeleportMagic extends Magic {
	public DeathTeleportMagic(MagicType<? extends DeathTeleportMagic> type) {
		super(type);
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::buildup;
	}

	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::buildup;
	}

	@Override
	public UseAnim getUseAnim(ItemStack stack) {
		return UseAnim.CROSSBOW;
	}

	@Override
	public ItemStack magicFinish(Level level, Player player, ItemStack staff) {
		if (!level.isClientSide) {
			player.getLastDeathLocation().ifPresentOrElse(p -> {
				if (p.dimension() == level.dimension()) {
					var pos = p.pos();
					((ServerPlayer) player).teleportTo((ServerLevel) level, pos.getX(), pos.getY() + 1, pos.getZ(),
							player.getYRot(), player.getXRot());
					cost(player);
				} else {
					playSoundServer(level, player, ModSounds.POOF.get(), 1, soundPitch(player));
				}
			}, () -> playSoundServer(level, player, ModSounds.POOF.get(), 1, soundPitch(player)));
		}
		return super.magicFinish(level, player, staff);
	}
}
