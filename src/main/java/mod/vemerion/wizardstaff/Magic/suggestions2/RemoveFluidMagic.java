package mod.vemerion.wizardstaff.Magic.suggestions2;

import com.google.gson.JsonObject;

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
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;

public class RemoveFluidMagic extends Magic {

	private RegistryMatch<Fluid> match;

	public RemoveFluidMagic(MagicType<?> type) {
		super(type);
	}

	public RemoveFluidMagic setAdditionalParams(RegistryMatch<Fluid> match) {
		this.match = match;
		return this;
	}

	@Override
	protected void readAdditional(JsonObject json) {
		match = RegistryMatch.read(ForgeRegistries.FLUIDS, GsonHelper.getAsJsonObject(json, "fluid_match"));
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		json.add("fluid_match", match.write());
	}

	@Override
	protected void encodeAdditional(FriendlyByteBuf buffer) {
		match.encode(buffer);
	}

	@Override
	protected void decodeAdditional(FriendlyByteBuf buffer) {
		match = RegistryMatch.decode(ForgeRegistries.FLUIDS, buffer);
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
	protected Object[] getNameArgs() {
		return new Object[] { match.getName() };
	}

	@Override
	protected Object[] getDescrArgs() {
		return getNameArgs();
	}

	@Override
	public ItemStack magicFinish(Level level, Player player, ItemStack staff) {
		if (!level.isClientSide) {
			int count = 0;
			BlockPos pos = player.blockPosition();
			for (BlockPos p : BlockPos.betweenClosed(pos.offset(-2, -1, -2), pos.offset(2, 2, 2))) {
				BlockState blockstate = level.getBlockState(p);
				Block block = blockstate.getBlock();
				if (match.test(blockstate.getFluidState().getType()) && block instanceof BucketPickup
						&& ((BucketPickup) block).pickupBlock(level, p, blockstate) != ItemStack.EMPTY) {
					count++;
				}
			}
			cost(player, count);
			if (count > 0)
				playSoundServer(level, player, ModSounds.EVAPORATE, 1, soundPitch(player));
		}
		return super.magicFinish(level, player, staff);
	}

}
