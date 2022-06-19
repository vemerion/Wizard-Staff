package mod.vemerion.wizardstaff.Magic.swap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.Helper.Helper;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.RegistryMatch;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.registries.ForgeRegistries;

public class MassHarvestMagic extends Magic {

	private RegistryMatch<Block> match;
	private int harvestLimit;

	public MassHarvestMagic(MagicType<? extends MassHarvestMagic> type) {
		super(type);
	}

	public MassHarvestMagic setAdditionalParams(RegistryMatch<Block> match, int harvestLimit) {
		this.match = match;
		this.harvestLimit = harvestLimit;
		return this;
	}

	@Override
	protected void readAdditional(JsonObject json) {
		match = RegistryMatch.read(ForgeRegistries.BLOCKS, GsonHelper.getAsJsonObject(json, "block_match"));
		harvestLimit = GsonHelper.getAsInt(json, "harvest_limit");
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		json.add("block_match", match.write());
		json.addProperty("harvest_limit", harvestLimit);
	}

	@Override
	protected void encodeAdditional(FriendlyByteBuf buffer) {
		match.encode(buffer);
		buffer.writeInt(harvestLimit);
	}

	@Override
	protected void decodeAdditional(FriendlyByteBuf buffer) {
		match = RegistryMatch.decode(ForgeRegistries.BLOCKS, buffer);
		harvestLimit = buffer.readInt();
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::drill;
	}

	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::forwardShake;
	}

	@Override
	public UseAnim getUseAnim(ItemStack stack) {
		return UseAnim.BLOCK;
	}

	protected Object[] getNameArgs() {
		return new Object[] { match.getName() };
	}

	@Override
	protected Object[] getDescrArgs() {
		return new Object[] { new TextComponent(String.valueOf(harvestLimit)), match.getName() };
	}

	@Override
	public ItemStack magicFinish(Level level, Player player, ItemStack staff) {
		BlockHitResult result = Helper.blockRay(level, player, 4);
		if (result.getType() == HitResult.Type.BLOCK) {
			BlockState state = level.getBlockState(result.getBlockPos());
			if (match.test(state.getBlock())) {
				player.playSound(state.getSoundType().getBreakSound(), 1, soundPitch(player));
				if (!level.isClientSide)
					cost(player, havestBlocks(result.getBlockPos(), level));
			} else {
				player.playSound(ModSounds.POOF, 1, soundPitch(player));
			}
		}

		return super.magicFinish(level, player, staff);
	}

	private int havestBlocks(BlockPos start, Level level) {
		List<BlockPos> positions = new ArrayList<>();
		Set<BlockPos> found = new HashSet<>();
		positions.add(start);
		while (!positions.isEmpty()) {
			BlockPos pos = positions.remove(0);
			BlockPos.betweenClosedStream(pos.offset(-1, -1, -1), pos.offset(1, 1, 1)).forEach(p -> {
				if (found.size() < harvestLimit && !found.contains(p)
						&& match.test(level.getBlockState(p).getBlock())) {
					BlockPos immutable = p.immutable();
					positions.add(immutable);
					found.add(immutable);
				}
			});
			destroyBlock(level, pos);
		}
		return found.size();
	}

	private void destroyBlock(Level level, BlockPos pos) {
		FluidState fluidstate = level.getFluidState(pos);
		BlockState state = level.getBlockState(pos);
		BlockEntity tileentity = state.hasBlockEntity() ? level.getBlockEntity(pos) : null;
		Block.dropResources(state, level, pos, tileentity, null, ItemStack.EMPTY);
		level.setBlockAndUpdate(pos, fluidstate.createLegacyBlock());
	}

}
