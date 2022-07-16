package mod.vemerion.wizardstaff.Magic.suggestions2;

import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.Helper.Helper;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.MagicUtil;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.registries.ForgeRegistries;

public class PushBlockMagic extends Magic {

	private static final int MAX_DISTANCE = 10;

	private Block block;

	public PushBlockMagic(MagicType<? extends PushBlockMagic> type) {
		super(type);
	}
	
	public PushBlockMagic setAdditionalParams(Block block) {
		this.block = block;
		return this;
	}

	@Override
	protected void readAdditional(JsonObject json) {
		block = MagicUtil.read(json, ForgeRegistries.BLOCKS, "block");
	}
	
	@Override
	protected void writeAdditional(JsonObject json) {
		MagicUtil.write(json, block, "block");
	}

	@Override
	public void encodeAdditional(FriendlyByteBuf buffer) {
		MagicUtil.encode(buffer, block);
	}

	@Override
	protected void decodeAdditional(FriendlyByteBuf buffer) {
		block = MagicUtil.decode(buffer);
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::forwardBuildup;
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
	protected Object[] getNameArgs() {
		return new Object[] { block.getName() };
	}
	
	@Override
	protected Object[] getDescrArgs() {
		return getNameArgs();
	}
	
	@Override
	public boolean magicPreventOtherUse(Level level, Player player, ItemStack staff) {
		return ray(level, player) != null;
	}

	@Override
	public ItemStack magicFinish(Level level, Player player, ItemStack staff) {
		if (!level.isClientSide) {
			Direction direction = Direction.orderedByNearest(player)[0];
			BlockPos start = ray(level, player);

			if (start != null) {
				BlockPos p = new BlockPos(start);
				BlockState state = level.getBlockState(p);
				BlockEntity tileEntity = level.getBlockEntity(start);

				for (int i = 0; i < MAX_DISTANCE; i++) {
					if (!(level.isEmptyBlock(p.relative(direction)) && state.canSurvive(level, p.relative(direction))))
						break;
					p = p.relative(direction);
				}
				
				if (tileEntity != null)
					tileEntity = BlockEntity.loadStatic(p, state, tileEntity.serializeNBT());

				if (p.equals(start)) {
					playSoundServer(level, player, ModSounds.POOF, 1, soundPitch(player));
				} else {
					playSoundServer(level, player, ModSounds.PUSH, 1, soundPitch(player));
					cost(player);
					level.setBlockAndUpdate(p, state);
					if (tileEntity != null) {
						level.removeBlockEntity(start);
						level.setBlockEntity(tileEntity);
						tileEntity.setChanged();
					}
					level.setBlockAndUpdate(start, Blocks.AIR.defaultBlockState());
				}
			} else {
				playSoundServer(level, player, ModSounds.POOF, 1, soundPitch(player));
			}
		}
		return super.magicFinish(level, player, staff);
	}

	private BlockPos ray(Level level, Player player) {
		BlockHitResult result = Helper.blockRay(level, player, 5);
		if (result.getType() == HitResult.Type.BLOCK && level.getBlockState(result.getBlockPos()).getBlock() == block)
			return result.getBlockPos();

		return null;
	}

}
