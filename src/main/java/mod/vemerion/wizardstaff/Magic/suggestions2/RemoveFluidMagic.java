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
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
		match = RegistryMatch.read(ForgeRegistries.FLUIDS, JSONUtils.getJsonObject(json, "fluid_match"));
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		json.add("fluid_match", match.write());
	}

	@Override
	protected void encodeAdditional(PacketBuffer buffer) {
		match.encode(buffer);
	}

	@Override
	protected void decodeAdditional(PacketBuffer buffer) {
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
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
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
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		if (!world.isRemote) {
			int count = 0;
			BlockPos pos = player.getPosition();
			for (BlockPos p : BlockPos.getAllInBoxMutable(pos.add(-2, -1, -2), pos.add(2, 2, 2))) {
				BlockState blockstate = world.getBlockState(p);
				Block block = blockstate.getBlock();
				if (match.test(blockstate.getFluidState().getFluid()) && block instanceof IBucketPickupHandler
						&& ((IBucketPickupHandler) block).pickupFluid(world, p, blockstate) != Fluids.EMPTY) {
					count++;
				}
			}
			cost(player, count);
			if (count > 0)
				playSoundServer(world, player, ModSounds.EVAPORATE, 1, soundPitch(player));
		}
		return super.magicFinish(world, player, staff);
	}

}
