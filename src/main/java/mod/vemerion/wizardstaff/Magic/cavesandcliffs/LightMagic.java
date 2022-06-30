package mod.vemerion.wizardstaff.Magic.cavesandcliffs;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.init.ModBlocks;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class LightMagic extends Magic {

	public LightMagic(MagicType<? extends LightMagic> type) {
		super(type);
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
	public UseAnim getUseAnim(ItemStack stack) {
		return UseAnim.NONE;
	}

	@Override
	public void magicTick(Level level, Player player, ItemStack staff, int count) {
		var pos = player.eyeBlockPosition();
		var state = level.getBlockState(pos);
		if (!level.isClientSide && (state.isAir() || state.getBlock() == Blocks.WATER)
				&& state.getBlock() != ModBlocks.MAGIC_LIGHT) {
			cost(player);
			level.setBlockAndUpdate(pos, ModBlocks.MAGIC_LIGHT.defaultBlockState()
					.setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(state.getBlock() == Blocks.WATER)));
		}
	}

}
