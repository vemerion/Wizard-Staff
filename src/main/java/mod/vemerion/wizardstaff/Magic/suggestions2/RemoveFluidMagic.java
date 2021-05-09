package mod.vemerion.wizardstaff.Magic.suggestions2;

import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.MagicUtil;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

public class RemoveFluidMagic extends Magic {

	private Fluid fluid;

	public RemoveFluidMagic(MagicType<?> type) {
		super(type);
	}

	public RemoveFluidMagic setAdditionalParams(Fluid fluid) {
		this.fluid = fluid;
		return this;
	}

	@Override
	protected void readAdditional(JsonObject json) {
		fluid = MagicUtil.read(json, ForgeRegistries.FLUIDS, "fluid");
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		MagicUtil.write(json, fluid, "fluid");
	}

	@Override
	protected void encodeAdditional(PacketBuffer buffer) {
		MagicUtil.encode(buffer, fluid);
	}

	@Override
	protected void decodeAdditional(PacketBuffer buffer) {
		fluid = MagicUtil.decode(buffer, ForgeRegistries.FLUIDS);
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
		return new Object[] { new StringTextComponent(fluid.getRegistryName().getPath()) };
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
				if (blockstate.getFluidState().getFluid().isEquivalentTo(fluid) && block instanceof IBucketPickupHandler
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
