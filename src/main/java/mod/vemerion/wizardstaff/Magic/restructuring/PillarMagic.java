package mod.vemerion.wizardstaff.Magic.restructuring;

import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.MagicUtil;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
	protected void decodeAdditional(PacketBuffer buffer) {
		block = MagicUtil.decode(buffer, ForgeRegistries.BLOCKS);
	}

	@Override
	protected void encodeAdditional(PacketBuffer buffer) {
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
		return new Object[] { block.getTranslatedName() };
	}

	@Override
	protected Object[] getNameArgs() {
		return new Object[] { block.getTranslatedName() };
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
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
	}

	@Override
	public void magicTick(World world, PlayerEntity player, ItemStack staff, int count) {
		if (player.isOnGround()) {
			player.jump();
		} else {
			BlockPos below = player.getPosition().down();
			if (world.isAirBlock(below)
					&& !world.getBlockState(below.down()).getCollisionShape(world, below.down()).isEmpty()) {
				BlockState state = block.getDefaultState();
				SoundType soundType = state.getSoundType();
				player.playSound(soundType.getPlaceSound(), soundType.getVolume(), soundType.getPitch());
				if (!world.isRemote) {
					cost(player);
					world.setBlockState(below, state);
				}
			}
		}
	}
}
