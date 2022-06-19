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
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
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
	public UseAnim getUseAnim(ItemStack stack) {
		return UseAnim.BLOCK;
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
	protected void decodeAdditional(FriendlyByteBuf buffer) {
		bucket = (BucketItem) MagicUtil.decode(buffer);
	}

	@Override
	protected void encodeAdditional(FriendlyByteBuf buffer) {
		MagicUtil.encode(buffer, bucket);
	}

	@Override
	protected Object[] getNameArgs() {
		return new Object[] { bucket.getName(bucket.getDefaultInstance()) };
	}

	@Override
	protected Object[] getDescrArgs() {
		return new Object[] { bucket.getName(bucket.getDefaultInstance()) };
	}

	@Override
	public ItemStack magicFinish(Level level, Player player, ItemStack staff) {
		Vec3 start = player.getEyePosition(1);
		Vec3 end = start.add(Vec3.directionFromRotation(player.getRotationVector()).scale(4.5));
		HitResult raytrace = level.clip(new ClipContext(start, end,
				ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
		if (raytrace.getType() == HitResult.Type.BLOCK) {
			BlockPos pos = new BlockPos(raytrace.getLocation());
			if (bucket.emptyContents(player, level, pos, (BlockHitResult) raytrace)) {
				bucket.checkExtraContent(player, level, new ItemStack(bucket), pos);
				if (!level.isClientSide) {
					cost(player);
				}
			}
		}

		return super.magicFinish(level, player, staff);
	}

}
