package mod.vemerion.wizardstaff.Magic;

import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderMagic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class NoMagic extends Magic {

	@Override
	public int getUseDuration(ItemStack stack) {
		return 0;
	}

	@Override
	public boolean isMagicItem(Item item) {
		return true;
	}

	@Override
	public RenderMagic renderer() {
		return null;
	}

}
