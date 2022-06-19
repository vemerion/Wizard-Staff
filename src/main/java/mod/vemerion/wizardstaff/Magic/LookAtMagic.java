package mod.vemerion.wizardstaff.Magic;

import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class LookAtMagic extends Magic {

	public LookAtMagic(MagicType<? extends LookAtMagic> type) {
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
	public UseAnim getUseAnim(ItemStack stack) {
		return UseAnim.NONE;
	}

	protected abstract BlockPos getPosition(ServerLevel level, ServerPlayer player, ItemStack staff);

	@Override
	public ItemStack magicFinish(Level level, Player player, ItemStack staff) {
		if (!level.isClientSide) {
			BlockPos pos = getPosition((ServerLevel) level, (ServerPlayer) player, staff);
			if (pos != null) {
				playSoundServer(level, player, ModSounds.WARP, 0.8f, soundPitch(player));
				cost(player);
				player.lookAt(EntityAnchorArgument.Anchor.EYES, Vec3.atCenterOf(pos));
			} else {
				playSoundServer(level, player, ModSounds.POOF, 1, soundPitch(player));
			}
		}
		return super.magicFinish(level, player, staff);
	}
}
