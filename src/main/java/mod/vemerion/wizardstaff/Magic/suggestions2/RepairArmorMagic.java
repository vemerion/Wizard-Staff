package mod.vemerion.wizardstaff.Magic.suggestions2;

import java.util.ArrayList;
import java.util.List;

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
import net.minecraft.util.JSONUtils;
import net.minecraft.world.World;

public class RepairArmorMagic extends Magic {
	
	private int repairRate;

	public RepairArmorMagic(MagicType<?> type) {
		super(type);
	}
	
	public RepairArmorMagic setAdditionalParams(int repairRate) {
		this.repairRate = repairRate;
		return this;
	}
	
	@Override
	protected void writeAdditional(JsonObject json) {
		json.addProperty("repair_rate", repairRate);
	}
	
	@Override
	protected void readAdditional(JsonObject json) {
		repairRate = JSONUtils.getInt(json, "repair_rate");
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
		if (!world.isRemote) {
			List<ItemStack> broken = new ArrayList<>();
			for (ItemStack armor : player.getArmorInventoryList())
				if (armor.isDamaged())
					broken.add(armor);
			if (!broken.isEmpty()) {
				ItemStack armor = broken.get(player.getRNG().nextInt(broken.size()));
				armor.setDamage(armor.getDamage() - repairRate);
				cost(player);
			}
		}
	}

}
