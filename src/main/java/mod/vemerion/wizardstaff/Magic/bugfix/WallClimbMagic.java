package mod.vemerion.wizardstaff.Magic.bugfix;

import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

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
		speed = JSONUtils.getFloat(json, "speed");
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		json.addProperty("speed", speed);
	}

	@Override
	public void encodeAdditional(PacketBuffer buffer) {
		buffer.writeFloat(speed);
	}

	@Override
	public void decodeAdditional(PacketBuffer buffer) {
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
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
	}

	@Override
	public void magicTick(World world, PlayerEntity player, ItemStack staff, int count) {
		if (player.collidedHorizontally) {
			Vector3d motion = player.getMotion();
			player.setMotion(motion.getX(), speed, motion.getZ());
			if (!world.isRemote)
				cost(player);
		}
	}

}
