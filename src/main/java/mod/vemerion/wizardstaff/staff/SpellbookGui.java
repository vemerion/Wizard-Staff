package mod.vemerion.wizardstaff.staff;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.Magics;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;

public class SpellbookGui extends GuiComponent implements Widget, GuiEventListener {
	private static final Component SEARCH_HINT = new TranslatableComponent("gui." + Main.MODID + ".search_hint")
			.withStyle(ChatFormatting.ITALIC);
	private static final ResourceLocation GUI = new ResourceLocation(Main.MODID, "textures/gui/spellbook.png");
	private static final int X_OFFSET = 86;
	private static final int X_SIZE = 147;
	private static final int Y_SIZE = 166;
	private static final int BORDER_X = 16;
	private static final int BORDER_Y = 12;
	private static final int ITEM_BORDER_Y = 24;
	private static final int ITEM_SIZE = 20;
	private static final int ITEMS_PER_ROW = 6;
	private static final int ITEMS_PER_COLUMN = 6;
	private static final int ITEMS_PER_PAGE = ITEMS_PER_ROW * ITEMS_PER_COLUMN;
	private static final int BUTTON_SIZE = 20;
	private static final int BUTTON_BOTTOM_OFFSET = 26;
	private static final int SEARCH_WIDTH = X_SIZE - BUTTON_SIZE * 2;
	private static final int SEARCH_HEIGHT = 14;

	private boolean isActive;
	private int left;
	private int top;
	private List<ItemButton> buttons;
	private List<ItemButton> searched;
	private Button next;
	private Button prev;
	private int page;
	private SpellDescription description;
	private EditBox search;
	WizardStaffScreen parent;
	Minecraft mc;

	public SpellbookGui() {
	}

	public void init(WizardStaffScreen parent, int width, int height) {
		this.parent = parent;
		this.mc = parent.getMinecraft();
		left = (width - X_SIZE) / 2 - X_OFFSET;
		top = (height - Y_SIZE) / 2;

		this.buttons = new ArrayList<>();
		this.searched = new ArrayList<>();
		int i = 0;
		for (ItemStack stack : Magics.getInstance(true).getMagicItems()) {
			buttons.add(new ItemButton(left + BORDER_X + (i % ITEMS_PER_ROW) * ITEM_SIZE,
					top + ITEM_BORDER_Y + (i / ITEMS_PER_ROW) * ITEM_SIZE, ITEM_SIZE, ITEM_SIZE, stack));
			i = (i + 1) % ITEMS_PER_PAGE;
		}

		next = new ImageButton(left + X_SIZE / 2 + 20, top + Y_SIZE - BUTTON_BOTTOM_OFFSET, BUTTON_SIZE, BUTTON_SIZE, 0,
				Y_SIZE, BUTTON_SIZE, GUI, (b) -> {
					if ((page + 1) * ITEMS_PER_PAGE < searched.size())
						page++;
				});

		prev = new ImageButton(left + X_SIZE / 2 - 20 - BUTTON_SIZE, top + Y_SIZE - BUTTON_BOTTOM_OFFSET, BUTTON_SIZE,
				BUTTON_SIZE, BUTTON_SIZE, Y_SIZE, BUTTON_SIZE, GUI, (b) -> {
					if (page > 0)
						page--;
				});

		initSearch();
		mc.keyboardHandler.setSendRepeatsToGui(true);

		if (description != null)
			description.init(left, top);
	}

	private void initSearch() {
		String text = search == null ? "" : search.getValue();
		search = new EditBox(mc.font, left + 26, top + 13, SEARCH_WIDTH, SEARCH_HEIGHT, SEARCH_HINT);
		search.setMaxLength(50);
		search.setValue(text);
		search.setBordered(false);
		search.setVisible(true);
		search.setTextColor(-1);
		filterButtons();
	}

	public void tick() {
		search.tick();
	}

	public void onClose() {
		search = null;
		mc.keyboardHandler.setSendRepeatsToGui(false);
	}

	@Override
	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		if (!isActive)
			return;

		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.setShaderTexture(0, GUI);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();
		blit(matrixStack, left, top, 0, 0, X_SIZE, Y_SIZE);

		if (description == null) {
			for (int i = page * ITEMS_PER_PAGE; i < Math.min(searched.size(), (page + 1) * ITEMS_PER_PAGE); i++)
				searched.get(i).render(matrixStack, mouseX, mouseY, partialTicks);

			next.render(matrixStack, mouseX, mouseY, partialTicks);
			prev.render(matrixStack, mouseX, mouseY, partialTicks);

			// Search
			blit(matrixStack, left + 12, top + 8, 0, 0, Y_SIZE + BUTTON_SIZE * 2, 16, 16, 256, 256);
			if (!search.isFocused() && search.getValue().isEmpty())
				drawString(matrixStack, mc.font, SEARCH_HINT, left + 26, top + 13, -1);
			else
				search.render(matrixStack, mouseX, mouseY, partialTicks);

			// Draw page number
			int maxPageNbr = Math.max(1, (int) Math.ceil((searched.size() / (float) ITEMS_PER_PAGE)));
			String pageText = (page + 1) + "/" + maxPageNbr;
			int textWidth = mc.font.width(pageText);
			mc.font.draw(matrixStack, pageText, left + X_SIZE / 2 - textWidth / 2f, top + Y_SIZE - 22, -1);
		} else {
			description.render(matrixStack, mouseX, mouseY, partialTicks);
		}
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (!isActive)
			return false;

		if (description == null) {
			for (int i = page * ITEMS_PER_PAGE; i < Math.min(searched.size(), (page + 1) * ITEMS_PER_PAGE); i++)
				if (searched.get(i).mouseClicked(mouseX, mouseY, button))
					return true;

			if (search.mouseClicked(mouseX, mouseY, button))
				return true;
			else if (next.mouseClicked(mouseX, mouseY, button))
				return true;
			else if (prev.mouseClicked(mouseX, mouseY, button))
				return true;
		} else {
			if (description.mouseClicked(mouseX, mouseY, button))
				return true;
		}

		return false;
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if (description == null)
			if (search.keyPressed(keyCode, scanCode, modifiers)) {
				filterButtons();
				return true;
			} else if (search.isFocused() && keyCode != GLFW.GLFW_KEY_ESCAPE)
				return true;
		return false;
	}

	private void filterButtons() {
		String filter = search.getValue().toLowerCase();
		searched = new ArrayList<>();
		int i = 0;
		for (ItemButton b : buttons)
			if (b.stack.getHoverName().getString().toLowerCase().contains(filter)
					|| b.magicDescr.getName().getString().toLowerCase().contains(filter)) {
				searched.add(b);
				b.x = left + BORDER_X + (i % ITEMS_PER_ROW) * ITEM_SIZE;
				b.y = top + ITEM_BORDER_Y + (i / ITEMS_PER_ROW) * ITEM_SIZE;
				i = (i + 1) % ITEMS_PER_PAGE;
			}
		page = 0;
	}

	@Override
	public boolean charTyped(char codePoint, int modifiers) {
		if (description == null && search.charTyped(codePoint, modifiers)) {
			filterButtons();
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
		private Magic.Description magicDescr;

		public ItemButton(int x, int y, int width, int height, ItemStack stack) {
			super(x, y, width, height, stack.getHoverName());
			this.stack = stack;
			this.magicDescr = Magics.getInstance(true).get(stack).getDescription();
		}

		@Override
		public void renderButton(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
			if (isHoveredOrFocused())
				parent.renderTooltip(matrixStack, getMessage(), mouseX, mouseY);

			mc.getItemRenderer().renderAndDecorateItem(stack, x, y);
		}

		@Override
		public void onPress() {
			description = new SpellDescription(magicDescr, stack, left, top);
		}

		@Override
		public void updateNarration(NarrationElementOutput pNarrationElementOutput) {
		}
	}

	private static final TranslatableComponent COST = new TranslatableComponent("gui." + Main.MODID + ".cost");
	private static final TranslatableComponent EXP = new TranslatableComponent("gui." + Main.MODID + ".exp");
	private static final TranslatableComponent DURATION = new TranslatableComponent("gui." + Main.MODID + ".duration");
	private static final TranslatableComponent INFINITY = new TranslatableComponent("gui." + Main.MODID + ".infinity");
	private static final TranslatableComponent SECONDS = new TranslatableComponent("gui." + Main.MODID + ".seconds");

	private class SpellDescription implements Widget, GuiEventListener {
		private ItemStack stack;
		private Button back;
		private int left;
		private Button next;
		private Button prev;
		private int top;
		private Magic.Description magicDescr;
		private int page;
		List<FormattedCharSequence> title;
		List<FormattedCharSequence> text;
		String cost, duration;
		private int linesPerPage;
		private int pageCount;

		public SpellDescription(Magic.Description magicDescr, ItemStack stack, int left, int top) {
			this.magicDescr = magicDescr;
			this.stack = stack;
			init(left, top);
		}

		public void init(int left, int top) {
			this.left = left;
			this.top = top;

			back = new ImageButton(left + BORDER_X, top + BORDER_Y, BUTTON_SIZE, BUTTON_SIZE, BUTTON_SIZE, Y_SIZE,
					BUTTON_SIZE, GUI, (b) -> {
						description = null;
					});

			next = new ImageButton(left + X_SIZE / 2 + 20, top + Y_SIZE - BUTTON_BOTTOM_OFFSET, BUTTON_SIZE,
					BUTTON_SIZE, 0, Y_SIZE, BUTTON_SIZE, GUI, (b) -> {
						if (page < pageCount - 1) {
							page++;
						}
					});

			prev = new ImageButton(left + X_SIZE / 2 - 20 - BUTTON_SIZE, top + Y_SIZE - BUTTON_BOTTOM_OFFSET,
					BUTTON_SIZE, BUTTON_SIZE, BUTTON_SIZE, Y_SIZE, BUTTON_SIZE, GUI, (b) -> {
						if (page > 0)
							page--;
					});

			title = mc.font.split(magicDescr.getName(), X_SIZE - BORDER_X * 2);
			text = mc.font.split(magicDescr.getDescription(), X_SIZE - BORDER_X * 2);
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
		public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
			back.render(matrixStack, mouseX, mouseY, partialTicks);
			if (pageCount > 1) {
				next.render(matrixStack, mouseX, mouseY, partialTicks);
				prev.render(matrixStack, mouseX, mouseY, partialTicks);
			}

			int y = top + BORDER_Y;
			int textWidth = 0;

			// Item
			mc.getItemRenderer().renderAndDecorateItem(stack, left + X_SIZE / 2 - 8, y);
			y += 20;

			// Magic name
			for (FormattedCharSequence line : title) {
				textWidth = mc.font.width(line);
				mc.font.draw(matrixStack, line, left + X_SIZE / 2 - textWidth / 2f, y, -1);
				y += mc.font.lineHeight;
			}
			y += 10;

			// Cost
			mc.font.draw(matrixStack, cost, left + BORDER_X, y, -1);
			y += 10;

			// Duration
			mc.font.draw(matrixStack, duration, left + BORDER_X, y, -1);
			y += 20;

			// Description
			for (int i = page * linesPerPage; i < Math.min(page * linesPerPage + linesPerPage, text.size()); i++) {
				mc.font.draw(matrixStack, text.get(i), left + BORDER_X, y, -1);
				y += mc.font.lineHeight;
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
