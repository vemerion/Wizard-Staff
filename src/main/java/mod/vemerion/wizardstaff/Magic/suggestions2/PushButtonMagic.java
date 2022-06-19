package mod.vemerion.wizardstaff.Magic.suggestions2;

import mod.vemerion.wizardstaff.Helper.Helper;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class PushButtonMagic extends Magic {
	
	private static final int DISTANCE = 15;

	public PushButtonMagic(MagicType<? extends PushButtonMagic> type) {
		super(type);
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::forward;
	}

	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::forwardShake;
	}

	@Override
	public UseAnim getUseAnim(ItemStack stack) {
		return UseAnim.BLOCK;
	}

	@Override
	public void magicTick(Level level, Player player, ItemStack staff, int count) {
		if (!level.isClientSide) {
			BlockHitResult result = Helper.blockRay(level, player, DISTANCE);
			if (result.getType() == HitResult.Type.BLOCK) {
				BlockPos pos = result.getBlockPos();
				BlockState state = level.getBlockState(pos);
				Block block = state.getBlock();
				if (state.hasProperty(BlockStateProperties.POWERED) && !state.getValue(BlockStateProperties.POWERED)
						&& block instanceof ButtonBlock) {
					cost(player);
					((ButtonBlock) block).press(state, level, pos);
					playSoundServer(level, player, SoundEvents.STONE_BUTTON_CLICK_ON, 1, soundPitch(player));
				}
			}
		}
	}

}
