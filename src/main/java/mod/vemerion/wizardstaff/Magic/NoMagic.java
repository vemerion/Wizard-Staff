package mod.vemerion.wizardstaff.Magic;

import java.util.stream.Stream;

import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.item.crafting.Ingredient;

public class NoMagic extends Magic {

	public NoMagic() {
		super("no_magic");
		this.init(0, 0, new Ingredient(Stream.empty()) {
			@Override
			public boolean test(ItemStack p_test_1_) {
				return true;
			}
		});
	}

	@Override
	public void init(float cost, int duration, Ingredient ingredient) {
		super.init(0, 0, new Ingredient(Stream.empty()) {
			@Override
			public boolean test(ItemStack p_test_1_) {
				return true;
			}
		});
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
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
	}

}
