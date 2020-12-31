package mod.vemerion.wizardstaff.staff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.Magics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;

public class SpellbookGui extends AbstractGui implements IRenderable, IGuiEventListener {
	private static final ResourceLocation GUI = new ResourceLocation("textures/gui/recipe_book.png");
	private static final ResourceLocation BUTTON = new ResourceLocation(Main.MODID, "textures/gui/spellbook.png");
	private static final int X_OFFSET = 86;
	private static final int X_SIZE = 147;
	private static final int Y_SIZE = 166;
	private static final int BORDER_X = 16;
	private static final int BORDER_Y = 10;
	private static final int ITEM_SIZE = 20;
	private static final int ITEMS_PER_ROW = 6;
	private static final int ITEMS_PER_COLUMN = 7;
	private static final int ITEMS_PER_PAGE = ITEMS_PER_ROW * ITEMS_PER_COLUMN;
	private static final int BUTTON_SIZE = 10;

	private boolean isActive;
	private int width;
	private int height;
	private List<ItemButton> buttons;
	private Button next;
	private Button prev;
	private int page;

	public SpellbookGui() {
	}

	public void init(int width, int height) {
		this.width = width;
		this.height = height;

		int left = (width - X_SIZE) / 2 - X_OFFSET;
		int top = (height - Y_SIZE) / 2;
		int bottom = top + Y_SIZE;
		int right = left + X_SIZE;

		this.buttons = new ArrayList<>();
		int i = 0;
		for (ItemStack stack : Magics.getInstance().getMagicItems()) {
			buttons.add(new ItemButton(left + BORDER_X + (i % ITEMS_PER_ROW) * ITEM_SIZE,
					top + BORDER_Y + (i / ITEMS_PER_ROW) * ITEM_SIZE, ITEM_SIZE, ITEM_SIZE, stack));
			i = (i + 1) % ITEMS_PER_PAGE;
		}

		next = new ImageButton(left + X_SIZE / 2 + 20, bottom - 8 - BUTTON_SIZE, BUTTON_SIZE, BUTTON_SIZE, 0, 0,
				0, BUTTON, (b) -> {
					if ((page + 1) * ITEMS_PER_PAGE < buttons.size())
						page++;
				});

		prev = new ImageButton(left + X_SIZE / 2 - 20 - BUTTON_SIZE, bottom - 8 - BUTTON_SIZE, BUTTON_SIZE,
				BUTTON_SIZE, 0, 0, 0, BUTTON, (b) -> {
					if (page > 0)
						page--;
				});
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		if (isActive) {
			Minecraft mc = Minecraft.getInstance();
			mc.getTextureManager().bindTexture(GUI);
			blit(matrixStack, (width - X_SIZE) / 2 - X_OFFSET, (height - Y_SIZE) / 2, 0, 0, X_SIZE, Y_SIZE);

			for (int i = page * ITEMS_PER_PAGE; i < Math.min(buttons.size(), (page + 1) * ITEMS_PER_PAGE); i++)
				buttons.get(i).render(matrixStack, mouseX, mouseY, partialTicks);

			next.render(matrixStack, mouseX, mouseY, partialTicks);
			prev.render(matrixStack, mouseX, mouseY, partialTicks);

			// Draw page number
			String pageText = (page + 1) + "/" + (buttons.size() / ITEMS_PER_PAGE + 1);
			int textWidth = mc.fontRenderer.getStringWidth(pageText);
			mc.fontRenderer.drawString(matrixStack, pageText,
					(width - X_SIZE) / 2f - X_OFFSET + X_SIZE / 2 - textWidth / 2f,
					(height - Y_SIZE) / 2f + Y_SIZE - 17, -1);

		}
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		for (int i = page * ITEMS_PER_PAGE; i < Math.min(buttons.size(), (page + 1) * ITEMS_PER_PAGE); i++)
			if (buttons.get(i).mouseClicked(mouseX, mouseY, button))
				return true;

		if (next.mouseClicked(mouseX, mouseY, button))
			return true;
		if (prev.mouseClicked(mouseX, mouseY, button))
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
