package mod.vemerion.wizardstaff.Magic;

import com.mojang.blaze3d.matrix.MatrixStack;

import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderMagic;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;

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
		return new RenderMagic() {
			@Override
			public void render(WizardStaffTileEntityRenderer renderer, float duration, int maxDuration, ItemStack stack,
					MatrixStack matrix, IRenderTypeBuffer buffer, int light, int combinedOverlayIn, float partialTicks,
					HandSide hand) {
			}
		};
	}

}
