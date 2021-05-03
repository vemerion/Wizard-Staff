package mod.vemerion.wizardstaff.Magic.suggestions;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.MagicUtil;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

public class BucketMagic extends Magic {

	private BucketItem bucket;

	public BucketMagic(MagicType<? extends BucketMagic> type) {
		super(type);
	}
	
	public BucketMagic setAdditionalParams(BucketItem bucket) {
		this.bucket = bucket;
		return this;
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::forwardWaving;
	}

	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::forwardShake;
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BLOCK;
	}

	@Override
	protected void readAdditional(JsonObject json) {
		Item item = MagicUtil.read(json, ForgeRegistries.ITEMS, "bucket");
		if (item instanceof BucketItem)
			bucket = (BucketItem) item;
		else
			throw new JsonParseException("bucket attribute must be instance of BucketItem");
	}
	
	@Override
	protected void writeAdditional(JsonObject json) {
		MagicUtil.write(json, bucket, "bucket");
	}

	@Override
	protected void decodeAdditional(PacketBuffer buffer) {
		bucket = (BucketItem) MagicUtil.decode(buffer, ForgeRegistries.ITEMS);
	}

	@Override
	protected void encodeAdditional(PacketBuffer buffer) {
		MagicUtil.encode(buffer, bucket);
	}

	@Override
	protected Object[] getNameArgs() {
		return new Object[] { bucket.getDisplayName(bucket.getDefaultInstance()) };
	}

	@Override
	protected Object[] getDescrArgs() {
		return new Object[] { bucket.getDisplayName(bucket.getDefaultInstance()) };
	}

	@Override
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		Vector3d start = player.getEyePosition(1);
		Vector3d end = start.add(Vector3d.fromPitchYaw(player.getPitchYaw()).scale(4.5));
		RayTraceResult raytrace = world.rayTraceBlocks(new RayTraceContext(start, end,
				RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, player));
		if (raytrace.getType() == RayTraceResult.Type.BLOCK) {
			BlockPos pos = new BlockPos(raytrace.getHitVec());
			if (bucket.tryPlaceContainedLiquid(player, world, pos, (BlockRayTraceResult) raytrace)) {
				bucket.onLiquidPlaced(world, new ItemStack(bucket), pos);
				if (!world.isRemote) {
					cost(player);
				}
			}
		}

		return super.magicFinish(world, player, staff);
	}

}
