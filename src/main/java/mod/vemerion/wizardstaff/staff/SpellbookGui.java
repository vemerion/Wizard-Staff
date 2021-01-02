package mod.vemerion.wizardstaff.staff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.Magics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;

public class SpellbookGui extends AbstractGui implements IRenderable, IGuiEventListener {
	private static final ResourceLocation GUI = new ResourceLocation(Main.MODID, "textures/gui/spellbook.png");
	private static final int X_OFFSET = 86;
	private static final int X_SIZE = 147;
	private static final int Y_SIZE = 166;
	private static final int BORDER_X = 16;
	private static final int BORDER_Y = 12;
	private static final int ITEM_SIZE = 20;
	private static final int ITEMS_PER_ROW = 6;
	private static final int ITEMS_PER_COLUMN = 6;
	private static final int ITEMS_PER_PAGE = ITEMS_PER_ROW * ITEMS_PER_COLUMN;
	private static final int BUTTON_SIZE = 20;

	private boolean isActive;
	private int left;
	private int top;
	private int width;
	private int height;
	private List<ItemButton> buttons;
	private Button next;
	private Button prev;
	private int page;
	private SpellDescription description;

	public SpellbookGui() {
	}

	public void init(int width, int height) {
		this.width = width;
		this.height = height;
		left = (width - X_SIZE) / 2 - X_OFFSET;
		top = (height - Y_SIZE) / 2;
		int bottom = top + Y_SIZE;

		this.buttons = new ArrayList<>();
		int i = 0;
		for (ItemStack stack : Magics.getInstance().getMagicItems()) {
			buttons.add(new ItemButton(left + BORDER_X + (i % ITEMS_PER_ROW) * ITEM_SIZE,
					top + BORDER_Y + (i / ITEMS_PER_ROW) * ITEM_SIZE, ITEM_SIZE, ITEM_SIZE, stack));
			i = (i + 1) % ITEMS_PER_PAGE;
		}

		next = new ImageButton(left + X_SIZE / 2 + 20, bottom - BORDER_Y - BUTTON_SIZE, BUTTON_SIZE, BUTTON_SIZE, 0,
				Y_SIZE, BUTTON_SIZE, GUI, (b) -> {
					if ((page + 1) * ITEMS_PER_PAGE < buttons.size())
						page++;
				});

		prev = new ImageButton(left + X_SIZE / 2 - 20 - BUTTON_SIZE, bottom - BORDER_Y - BUTTON_SIZE, BUTTON_SIZE,
				BUTTON_SIZE, BUTTON_SIZE, Y_SIZE, BUTTON_SIZE, GUI, (b) -> {
					if (page > 0)
						page--;
				});

		if (description != null)
			description.init(left, top);
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		if (!isActive)
			return;

		Minecraft mc = Minecraft.getInstance();
		mc.getTextureManager().bindTexture(GUI);
		blit(matrixStack, left, top, 0, 0, X_SIZE, Y_SIZE);

		if (description == null) {
			for (int i = page * ITEMS_PER_PAGE; i < Math.min(buttons.size(), (page + 1) * ITEMS_PER_PAGE); i++)
				buttons.get(i).render(matrixStack, mouseX, mouseY, partialTicks);

			next.render(matrixStack, mouseX, mouseY, partialTicks);
			prev.render(matrixStack, mouseX, mouseY, partialTicks);

			// Draw page number
			String pageText = (page + 1) + "/" + (buttons.size() / ITEMS_PER_PAGE + 1);
			int textWidth = mc.fontRenderer.getStringWidth(pageText);
			mc.fontRenderer.drawString(matrixStack, pageText, left + X_SIZE / 2 - textWidth / 2f, top + Y_SIZE - 24,
					-1);
		} else {
			description.render(matrixStack, mouseX, mouseY, partialTicks);
		}
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (!isActive)
			return false;

		if (description == null) {
			for (int i = page * ITEMS_PER_PAGE; i < Math.min(buttons.size(), (page + 1) * ITEMS_PER_PAGE); i++)
				if (buttons.get(i).mouseClicked(mouseX, mouseY, button))
					return true;

			if (next.mouseClicked(mouseX, mouseY, button))
				return true;
			if (prev.mouseClicked(mouseX, mouseY, button))
				return true;
		} else {
			if (description.mouseClicked(mouseX, mouseY, button))
				return true;
		}

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
			description = new SpellDescription(stack, left, top);
		}

	}

	private class SpellDescription implements IRenderable, IGuiEventListener {
		private ItemStack stack;
		private Button back;
		private int left;
		private int top;
		private Magic.Description magicDescr;

		public SpellDescription(ItemStack stack, int left, int top) {
			this.stack = stack;
			this.magicDescr = Magics.getInstance().get(stack).getDescription();
			init(left, top);
		}

		public void init(int left, int top) {
			this.left = left;
			this.top = top;

			back = new ImageButton(left + BORDER_X, top + BORDER_Y, BUTTON_SIZE, BUTTON_SIZE, BUTTON_SIZE, Y_SIZE,
					BUTTON_SIZE, GUI, (b) -> {
						description = null;
					});
		}

		@Override
		public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
			back.render(matrixStack, mouseX, mouseY, partialTicks);
			Minecraft mc = Minecraft.getInstance();

			// Item
			mc.getItemRenderer().renderItemAndEffectIntoGUI(stack, left + X_SIZE / 2 - 8, top + BORDER_Y);

			// Magic name
			int textWidth = mc.fontRenderer.getStringWidth(magicDescr.getName().getString());
			mc.fontRenderer.drawString(matrixStack, magicDescr.getName().getString(),
					left + X_SIZE / 2 - textWidth / 2f, top + BORDER_Y + 20, -1);

			// Cost
			String cost = new TranslationTextComponent("gui." + Main.MODID + ".cost").getString() + ": "
					+ magicDescr.getCost();
			mc.fontRenderer.drawString(matrixStack, cost, left + BORDER_X, top + BORDER_Y + 35, -1);

			// Duration
			String duration = new TranslationTextComponent("gui." + Main.MODID + ".duration").getString() + ": "
					+ magicDescr.getDuration();
			mc.fontRenderer.drawString(matrixStack, duration, left + BORDER_X, top + BORDER_Y + 50, -1);

			// Description
			List<IReorderingProcessor> lines = mc.fontRenderer.trimStringToWidth(magicDescr.getDescription(),
					X_SIZE - BORDER_X * 2);
			for (int i = 0; i < lines.size(); i++) {
				mc.fontRenderer.func_238422_b_(matrixStack, lines.get(i), left + BORDER_X,
						top + BORDER_Y + 70 + mc.fontRenderer.FONT_HEIGHT * i, -1);
			}
		}

		@Override
		public boolean mouseClicked(double mouseX, double mouseY, int button) {
			if (back.mouseClicked(mouseX, mouseY, button))
				return true;

			return false;
		}

	}
}
