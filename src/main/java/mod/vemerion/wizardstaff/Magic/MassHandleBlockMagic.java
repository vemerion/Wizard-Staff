package mod.vemerion.wizardstaff.Magic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.Helper.Helper;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class MassHandleBlockMagic extends Magic {

	private RegistryMatch<Block> match;
	private int harvestLimit;

	public MassHandleBlockMagic(MagicType<? extends MassHandleBlockMagic> type) {
		super(type);
	}

	public MassHandleBlockMagic setAdditionalParams(RegistryMatch<Block> match, int harvestLimit) {
		this.match = match;
		this.harvestLimit = harvestLimit;
		return this;
	}

	@Override
	protected void readAdditional(JsonObject json) {
		match = RegistryMatch.read(ForgeRegistries.BLOCKS, GsonHelper.getAsJsonObject(json, "block_match"));
		harvestLimit = GsonHelper.getAsInt(json, "block_limit");
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		json.add("block_match", match.write());
		json.addProperty("block_limit", harvestLimit);
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
	public boolean magicPreventOtherUse(Level level, Player player, ItemStack staff) {
		BlockHitResult result = Helper.blockRay(level, player, 4);
		if (result.getType() == Type.BLOCK)
			return match.test(level.getBlockState(result.getBlockPos()).getBlock());

		return false;
	}

	@Override
	public ItemStack magicFinish(Level level, Player player, ItemStack staff) {
		BlockHitResult result = Helper.blockRay(level, player, 4);
		if (result.getType() == HitResult.Type.BLOCK) {
			BlockState state = level.getBlockState(result.getBlockPos());
			if (match.test(state.getBlock())) {
				player.playSound(state.getSoundType().getBreakSound(), 1, soundPitch(player));
				if (!level.isClientSide)
					cost(player, handleBlocks(result.getBlockPos(), state.getBlock(), level, player, staff));
			} else {
				player.playSound(ModSounds.POOF, 1, soundPitch(player));
			}
		}

		return super.magicFinish(level, player, staff);
	}

	private int handleBlocks(BlockPos start, Block type, Level level, Player player, ItemStack staff) {
		List<BlockPos> positions = new ArrayList<>();
		Set<BlockPos> found = new HashSet<>();
		positions.add(start);
		while (!positions.isEmpty()) {
			BlockPos pos = positions.remove(0);
			BlockPos.betweenClosedStream(pos.offset(searchOffset().multiply(-1)), pos.offset(searchOffset()))
					.forEach(p -> {
						if (found.size() < harvestLimit && !found.contains(p)
								&& type == level.getBlockState(p).getBlock()) {
							BlockPos immutable = p.immutable();
							positions.add(immutable);
							found.add(immutable);
						}
					});
			handleBlock(level, pos, player, staff);
		}
		return found.size();
	}

	protected abstract void handleBlock(Level level, BlockPos pos, Player player, ItemStack staff);

	protected abstract Vec3i searchOffset();
}
