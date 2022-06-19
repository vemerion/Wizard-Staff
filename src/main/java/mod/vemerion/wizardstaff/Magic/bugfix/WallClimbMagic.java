package mod.vemerion.wizardstaff.Magic.bugfix;

import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class WallClimbMagic extends Magic {

	private float speed;

	public WallClimbMagic(MagicType<? extends WallClimbMagic> type) {
		super(type);
	}

	public WallClimbMagic setAdditionalParams(float speed) {
		this.speed = speed;
		return this;
	}

	@Override
	protected void readAdditional(JsonObject json) {
		speed = GsonHelper.getAsFloat(json, "speed");
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		json.addProperty("speed", speed);
	}

	@Override
	public void encodeAdditional(FriendlyByteBuf buffer) {
		buffer.writeFloat(speed);
	}

	@Override
	public void decodeAdditional(FriendlyByteBuf buffer) {
		speed = buffer.readFloat();
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::spinMagic;
	}

	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::spinMagic;
	}

	@Override
	public UseAnim getUseAnim(ItemStack stack) {
		return UseAnim.NONE;
	}

	@Override
	public void magicTick(Level level, Player player, ItemStack staff, int count) {
		if (player.horizontalCollision) {
			Vec3 motion = player.getDeltaMovement();
			player.setDeltaMovement(motion.x(), speed, motion.z());
		}
		if (!level.isClientSide)
			cost(player);
	}

}
