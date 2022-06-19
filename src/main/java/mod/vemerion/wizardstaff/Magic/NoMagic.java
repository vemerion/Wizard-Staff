package mod.vemerion.wizardstaff.Magic;

import java.util.stream.Stream;

import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.crafting.Ingredient;

public class NoMagic extends Magic {

	public NoMagic(MagicType<? extends NoMagic> type) {
		super(type);
		this.cost = 0;
		this.duration = 0;
		this.ingredient = new Ingredient(Stream.empty()) {
			@Override
			public boolean test(ItemStack stack) {
				return true;
			}
		};
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::noRender;
	}

	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::noRender;
	}

	@Override
	public UseAnim getUseAnim(ItemStack stack) {
		return UseAnim.NONE;
	}

}
