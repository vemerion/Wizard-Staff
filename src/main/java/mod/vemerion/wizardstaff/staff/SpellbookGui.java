package mod.vemerion.wizardstaff.staff;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.math.DoubleMath;
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

	private static final TranslationTextComponent COST = new TranslationTextComponent("gui." + Main.MODID + ".cost");
	private static final TranslationTextComponent EXP = new TranslationTextComponent("gui." + Main.MODID + ".exp");
	private static final TranslationTextComponent DURATION = new TranslationTextComponent(
			"gui." + Main.MODID + ".duration");
	private static final TranslationTextComponent INFINITY = new TranslationTextComponent(
			"gui." + Main.MODID + ".infinity");
	private static final TranslationTextComponent SECONDS = new TranslationTextComponent(
			"gui." + Main.MODID + ".seconds");

	private class SpellDescription implements IRenderable, IGuiEventListener {
		private ItemStack stack;
		private Button back;
		private int left;
		private Button next;
		private Button prev;
		private int top;
		private Magic.Description magicDescr;
		private int page;
		List<IReorderingProcessor> title;
		List<IReorderingProcessor> text;
		String cost, duration;
		private int linesPerPage;
		private int pageCount;

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

			next = new ImageButton(left + X_SIZE / 2 + 20, top + Y_SIZE - BORDER_Y - BUTTON_SIZE, BUTTON_SIZE,
					BUTTON_SIZE, 0, Y_SIZE, BUTTON_SIZE, GUI, (b) -> {
						if (page < pageCount - 1) {
							page++;
						}
					});

			prev = new ImageButton(left + X_SIZE / 2 - 20 - BUTTON_SIZE, top + Y_SIZE - BORDER_Y - BUTTON_SIZE,
					BUTTON_SIZE, BUTTON_SIZE, BUTTON_SIZE, Y_SIZE, BUTTON_SIZE, GUI, (b) -> {
						if (page > 0)
							page--;
					});

			title = Minecraft.getInstance().fontRenderer.trimStringToWidth(magicDescr.getName(), X_SIZE - BORDER_X * 2);
			text = Minecraft.getInstance().fontRenderer.trimStringToWidth(magicDescr.getDescription(),
					X_SIZE - BORDER_X * 2);
			linesPerPage = 7 - title.size();
			pageCount = (int) Math.ceil(text.size() / (float) linesPerPage);

			DecimalFormat decimalFormat = new DecimalFormat("#.#");
			cost = COST.getString() + ": " + decimalFormat.format(magicDescr.getCost()) + " " + EXP.getString();

			float durationSeconds = magicDescr.getDuration() / 20f;
			String durationTime = magicDescr.getDuration() >= Magic.HOUR ? INFINITY.getString()
					: decimalFormat.format(durationSeconds);
			duration = DURATION.getString() + ": " + durationTime + " " + SECONDS.getString();

		}

		@Override
		public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
			back.render(matrixStack, mouseX, mouseY, partialTicks);
			if (pageCount > 1) {
				next.render(matrixStack, mouseX, mouseY, partialTicks);
				prev.render(matrixStack, mouseX, mouseY, partialTicks);
			}

			Minecraft mc = Minecraft.getInstance();

			int y = top + BORDER_Y;
			int textWidth = 0;

			// Item
			mc.getItemRenderer().renderItemAndEffectIntoGUI(stack, left + X_SIZE / 2 - 8, y);
			y += 20;

			// Magic name
			for (IReorderingProcessor line : title) {
				textWidth = mc.fontRenderer.func_243245_a(line);
				mc.fontRenderer.func_238422_b_(matrixStack, line, left + X_SIZE / 2 - textWidth / 2f, y, -1);
				y += mc.fontRenderer.FONT_HEIGHT;
			}
			y += 10;

			// Cost

			mc.fontRenderer.drawString(matrixStack, cost, left + BORDER_X, y, -1);
			y += 10;

			// Duration
			mc.fontRenderer.drawString(matrixStack, duration, left + BORDER_X, y, -1);
			y += 20;

			// Description
			for (int i = page * linesPerPage; i < Math.min(page * linesPerPage + linesPerPage, text.size()); i++) {
				mc.fontRenderer.func_238422_b_(matrixStack, text.get(i), left + BORDER_X, y, -1);
				y += mc.fontRenderer.FONT_HEIGHT;
			}
		}

		@Override
		public boolean mouseClicked(double mouseX, double mouseY, int button) {
			if (back.mouseClicked(mouseX, mouseY, button))
				return true;
			else if (pageCount > 1 && next.mouseClicked(mouseX, mouseY, button))
				return true;
			else if (pageCount > 1 && prev.mouseClicked(mouseX, mouseY, button))
				return true;

			return false;
		}

	}
}
