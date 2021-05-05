package mod.vemerion.wizardstaff.Magic;

import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

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
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
	}

	protected abstract BlockPos getPosition(ServerWorld world, ServerPlayerEntity player, ItemStack staff);

	@Override
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		if (!world.isRemote) {
			BlockPos pos = getPosition((ServerWorld) world, (ServerPlayerEntity) player, staff);
			if (pos != null) {
				playSoundServer(world, player, ModSounds.WARP, 0.8f, soundPitch(player));
				cost(player);
				player.lookAt(EntityAnchorArgument.Type.EYES, Vector3d.copyCentered(pos));
			} else {
				playSoundServer(world, player, ModSounds.POOF, 1, soundPitch(player));
			}
		}
		return super.magicFinish(world, player, staff);
	}
}
