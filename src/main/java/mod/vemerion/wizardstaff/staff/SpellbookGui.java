package mod.vemerion.wizardstaff.staff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import mod.vemerion.wizardstaff.Magic.Magics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.gui.GuiUtils;

public class SpellbookGui extends AbstractGui implements IRenderable, IGuiEventListener {
	private static final ResourceLocation GUI = new ResourceLocation("textures/gui/recipe_book.png");
	private static final int X_OFFSET = 86;
	private static final int X_SIZE = 147;
	private static final int Y_SIZE = 166;
	private static final int BORDER_X = 16;
	private static final int BORDER_Y = 12;
	private static final int BUTTON_SIZE = 20;
	private static final int ITEMS_PER_ROW = 6;
	private static final int ITEMS_PER_COLUMN = 7;
	private static final int ITEMS_PER_PAGE = ITEMS_PER_ROW * ITEMS_PER_COLUMN;

	private boolean isActive;
	private int width;
	private int height;
	private List<ItemButton> buttons;

	public SpellbookGui() {
	}

	public void init(int width, int height) {
		this.width = width;
		this.height = height;

		int left = (width - X_SIZE) / 2 - X_OFFSET + BORDER_X;
		int top = (height - Y_SIZE) / 2 + BORDER_Y;

		this.buttons = new ArrayList<>();
		int i = 0;
		for (ItemStack stack : Magics.getInstance().getMagicItems()) {
			buttons.add(new ItemButton(left + (i % ITEMS_PER_ROW) * BUTTON_SIZE,
					top + (i / ITEMS_PER_ROW) * BUTTON_SIZE, BUTTON_SIZE, BUTTON_SIZE, stack));
			i++;
		}
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		if (isActive) {
			Minecraft.getInstance().getTextureManager().bindTexture(GUI);
			blit(matrixStack, (width - X_SIZE) / 2 - X_OFFSET, (height - Y_SIZE) / 2, 0, 0, X_SIZE, Y_SIZE);

			for (ItemButton b : buttons)
				b.render(matrixStack, mouseX, mouseY, partialTicks);
		}
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		for (ItemButton b : buttons)
			if (b.mouseClicked(mouseX, mouseY, button))
				return true;

		return false;
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

	private class ItemButton extends AbstractButton {

		private ItemStack stack;

		public ItemButton(int x, int y, int width, int height, ItemStack stack) {
			super(x, y, width, height, stack.getDisplayName());
			this.stack = stack;
		}

		@Override
		public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
			Minecraft minecraft = Minecraft.getInstance();
			if (isHovered()) {
				GuiUtils.drawHoveringText(matrixStack, Arrays.asList(getMessage()), mouseX, mouseY,
						SpellbookGui.this.width, SpellbookGui.this.height, -1, minecraft.fontRenderer);
			}

			minecraft.getItemRenderer().renderItemAndEffectIntoGUI(stack, x, y);
		}

		@Override
		public void onPress() {

		}

	}
}
