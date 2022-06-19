package mod.vemerion.wizardstaff.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

public abstract class MagicArmorModel<T extends LivingEntity> extends HumanoidModel<T> {

	public MagicArmorModel(ModelPart part) {
		super(part);
	}

	public abstract void setVisibility(EquipmentSlot armorSlot);

}
