package mod.vemerion.wizardstaff.staff;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.util.ResourceLocation;

public class SpellbookGui extends AbstractGui implements IRenderable {
	private static final ResourceLocation GUI = new ResourceLocation("textures/gui/recipe_book.png");
	private static final int X_OFFSET = 86;
	private static final int X_SIZE = 147;
	private static final int Y_SIZE = 166;

	private boolean isActive;
	private int width;
	private int height;

	public void init(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		if (isActive) {
			Minecraft.getInstance().getTextureManager().bindTexture(GUI);
			blit(matrixStack, (width - X_SIZE) / 2 - X_OFFSET, (height - Y_SIZE) / 2, 0, 0, X_SIZE, Y_SIZE);
		}
	}

	public void toggleActive() {
		isActive = !isActive;
	}

	public int updatePosition(int width, int sizeX) {
		if (isActive) {
			return X_OFFSET + (width - sizeX) / 2;
		} else {
			return (width - sizeX) / 2;
		}
	}
}
