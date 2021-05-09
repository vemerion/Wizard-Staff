package mod.vemerion.wizardstaff.Magic.suggestions2;

import mod.vemerion.wizardstaff.Helper.Helper;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

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
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BLOCK;
	}

	@Override
	public void magicTick(World world, PlayerEntity player, ItemStack staff, int count) {
		if (!world.isRemote) {
			BlockRayTraceResult result = Helper.blockRay(world, player, DISTANCE);
			if (result.getType() == Type.BLOCK) {
				BlockPos pos = result.getPos();
				BlockState state = world.getBlockState(pos);
				Block block = state.getBlock();
				if (state.hasProperty(BlockStateProperties.POWERED) && !state.get(BlockStateProperties.POWERED)
						&& block instanceof AbstractButtonBlock) {
					cost(player);
					((AbstractButtonBlock) block).powerBlock(state, world, pos);
					playSoundServer(world, player, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, 1, soundPitch(player));
				}
			}
		}
	}

}
