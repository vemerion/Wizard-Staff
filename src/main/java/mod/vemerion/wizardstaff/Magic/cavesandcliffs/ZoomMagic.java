package mod.vemerion.wizardstaff.Magic.cavesandcliffs;

import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.capability.Wizard;
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

public class ZoomMagic extends Magic {

	int fov;

	public ZoomMagic(MagicType<? extends ZoomMagic> type) {
		super(type);
	}

	public ZoomMagic setAdditionalParams(int fov) {
		this.fov = fov;
		return this;
	}

	@Override
	protected void readAdditional(JsonObject json) {
		fov = GsonHelper.getAsInt(json, "fov");
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		json.addProperty("fov", fov);
	}

	@Override
	protected void decodeAdditional(FriendlyByteBuf buffer) {
		fov = buffer.readInt();
	}

	@Override
	protected void encodeAdditional(FriendlyByteBuf buffer) {
		buffer.writeInt(fov);
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
	public void magicStart(Level level, Player player, ItemStack staff) {
		Wizard.getWizardOptional(player).ifPresent(w -> {
			w.setFov(fov);
		});
	}

	@Override
	public void magicTick(Level level, Player player, ItemStack staff, int count) {
		if (!level.isClientSide)
			cost(player);
	}

}
