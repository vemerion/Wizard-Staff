package mod.vemerion.wizardstaff.Magic.netherupdate;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderMagic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.World;

public class NetherrackMagic extends Magic {

	@Override
	public int getUseDuration(ItemStack staff) {
		return HOUR;
	}

	@Override
	public boolean isMagicItem(Item item) {
		return item == Items.NETHERRACK;
	}

	@Override
	public RenderMagic renderer() {
		return WizardStaffTileEntityRenderer::swinging;
	}

	@Override
	public void magicTick(World world, PlayerEntity player, ItemStack staff, int count) {
		if (!world.isRemote && player.ticksExisted % 10 == 0) {
			cost(player, rackify(world, player));
		}
	}

	// Derived from FrostWalkerEnchantment.freezeNearby()
	private int rackify(World world, PlayerEntity player) {
		int i = 0;
		if (player.onGround) {
			BlockState netherrack = Blocks.NETHERRACK.getDefaultState();
			float range = 2;
			BlockPos start = player.getPosition().add(-range, -1, -range);
			BlockPos stop = player.getPosition().add(range, -1, range);
			for (BlockPos pos : BlockPos.getAllInBoxMutable(start, stop)) {
				if (canBeRackified(world, pos)) {
					if (world.func_226663_a_(netherrack, pos, ISelectionContext.dummy())
							&& !net.minecraftforge.event.ForgeEventFactory.onBlockPlace(player,
									new net.minecraftforge.common.util.BlockSnapshot(world, pos,
											world.getBlockState(pos)),
									net.minecraft.util.Direction.UP)) {
						world.setBlockState(pos, netherrack);
						i++;
					}
				}
			}
		}
		return i;
	}

	private boolean canBeRackified(World world, BlockPos pos) {
		IFluidState fluidState = world.getFluidState(pos);
		return world.isAirBlock(pos.up()) && fluidState.isTagged(FluidTags.LAVA) && fluidState.isSource();
	}

}
