package mod.vemerion.wizardstaff.Magic;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public abstract class RepairItemsMagic extends Magic {
	
	private int repairRate;

	public RepairItemsMagic(MagicType<? extends RepairItemsMagic> type) {
		super(type);
	}
	
	public RepairItemsMagic setAdditionalParams(int repairRate) {
		this.repairRate = repairRate;
		return this;
	}
	
	@Override
	protected void writeAdditional(JsonObject json) {
		json.addProperty("repair_rate", repairRate);
	}
	
	@Override
	protected void readAdditional(JsonObject json) {
		repairRate = GsonHelper.getAsInt(json, "repair_rate");
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
		if (!level.isClientSide) {
			List<ItemStack> broken = new ArrayList<>();
			for (ItemStack armor : getItems(level, player, staff, count))
				if (armor.isDamaged())
					broken.add(armor);
			if (!broken.isEmpty()) {
				ItemStack armor = broken.get(player.getRandom().nextInt(broken.size()));
				armor.setDamageValue(armor.getDamageValue() - repairRate);
				cost(player);
			}
		}
		
		if (count % 20 == 0)
			player.playSound(ModSounds.ANVIL, 1, soundPitch(player));
	}

	protected abstract Iterable<ItemStack> getItems(Level level, Player player, ItemStack staff, int count);

}
