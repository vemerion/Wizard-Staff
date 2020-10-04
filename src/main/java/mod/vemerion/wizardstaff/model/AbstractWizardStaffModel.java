package mod.vemerion.wizardstaff.model;

import java.util.function.Function;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.util.ResourceLocation;

public abstract class AbstractWizardStaffModel extends Model {

	public AbstractWizardStaffModel(Function<ResourceLocation, RenderType> renderTypeIn) {
		super(renderTypeIn);
	}
	
	public abstract ResourceLocation getTexture();

	public abstract float getMagicScale();

}
