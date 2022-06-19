package mod.vemerion.wizardstaff.Magic.restructuring;

import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.MagicUtil;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

public class PillarMagic extends Magic {

	private Block block;

	public PillarMagic(MagicType<? extends PillarMagic> type) {
		super(type);
	}
	
	public PillarMagic setAdditionalParams(Block block) {
		this.block = block;
		return this;
	}

	@Override
	protected void decodeAdditional(FriendlyByteBuf buffer) {
		block = MagicUtil.decode(buffer);
	}

	@Override
	protected void encodeAdditional(FriendlyByteBuf buffer) {
		MagicUtil.encode(buffer, block);
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
	protected Object[] getDescrArgs() {
		return new Object[] { block.getName() };
	}

	@Override
	protected Object[] getNameArgs() {
		return new Object[] { block.getName() };
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::swinging;
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
		if (player.isOnGround()) {
			player.jumpFromGround();
		} else {
			BlockPos below = player.blockPosition().below();
			if (level.isEmptyBlock(below)
					&& !level.getBlockState(below.below()).getCollisionShape(level, below.below()).isEmpty()) {
				BlockState state = block.defaultBlockState();
				SoundType soundType = state.getSoundType();
				player.playSound(soundType.getPlaceSound(), soundType.getVolume(), soundType.getPitch());
				if (!level.isClientSide) {
					cost(player);
					level.setBlockAndUpdate(below, state);
				}
			}
		}
	}
}
