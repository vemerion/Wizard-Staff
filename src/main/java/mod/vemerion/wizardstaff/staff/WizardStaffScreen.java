package mod.vemerion.wizardstaff.staff;

import java.awt.Color;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.capability.ScreenAnimations;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public class WizardStaffScreen extends AbstractContainerScreen<WizardStaffContainer> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(Main.MODID,
			"textures/gui/staff_screen_new.png");

	private static final ResourceLocation ANIMATION_BUTTON = new ResourceLocation(Main.MODID,
			"textures/gui/animation_button.png");

	private static final ResourceLocation SPELLBOOK_BUTTON = new ResourceLocation(Main.MODID,
			"textures/gui/spellbook_button.png");

	private static final int ANIMATION_BOOK_Y = 173;
	private static final int BOOK_WIDTH = 50;
	private static final int BOOK_HEIGHT = 34;
	private static final int BOOK_X = 113;
	private static final int BOOK_Y = 16;

	private static final int ANIMATION_FACE_X = 176;
	private static final int FACE_WIDTH = 13;
	private static final int FACE_HEIGHT = 16;
	private static final int FACE_X = 27;
	private static final int FACE_Y = 40;

	private boolean buttonPressed, shouldAnimate;
	private Button toggleAnimationsButton;
	private Button spellbookButton;

	private SpellbookGui spellbook;

	public WizardStaffScreen(WizardStaffContainer screenContainer, Inventory inv, Component titleIn) {
		super(screenContainer, inv, titleIn);
		shouldAnimate = menu.shouldAnimate();
		spellbook = new SpellbookGui();
	}

	@Override
	protected void init() {
		super.init();
		spellbook.init(this, width, height);
		leftPos = spellbook.updatePosition(width, imageWidth);

		toggleAnimationsButton = addRenderableWidget(
				new ImageButton(leftPos + imageWidth - 18, topPos + 9, 9, 9, 0, 0, 9, ANIMATION_BUTTON, (button) -> {
					buttonPressed = true;
					shouldAnimate = !shouldAnimate;
				}));

		spellbookButton = addRenderableWidget(new ImageButton(leftPos + imageWidth / 2 - 8,
				(int) (topPos + imageHeight * 0.37), 16, 16, 0, 0, 16, SPELLBOOK_BUTTON, (button) -> {
					spellbook.toggleActive();
					leftPos = spellbook.updatePosition(width, imageWidth);
					((ImageButton) button).setPosition(leftPos + imageWidth / 2 - 8,
							(int) (topPos + imageHeight * 0.37));
					((ImageButton) toggleAnimationsButton).setPosition(leftPos + imageWidth - 18, topPos + 9);
				}));
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (spellbook.mouseClicked(mouseX, mouseY, button))
			return true;
		return super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if (spellbook.keyPressed(keyCode, scanCode, modifiers))
			return true;
		return super.keyPressed(keyCode, scanCode, modifiers);
	}

	@Override
	public boolean charTyped(char codePoint, int modifiers) {
		if (spellbook.charTyped(codePoint, modifiers))
			return true;
		return super.charTyped(codePoint, modifiers);
	}

	@Override
	public void removed() {
		if (buttonPressed && shouldAnimate != menu.shouldAnimate())
			ScreenAnimations.sendMessage(minecraft.player, shouldAnimate);
		spellbook.onClose();
		super.removed();
	}

	@Override
	public void containerTick() {
		super.containerTick();
		spellbook.tick();
	}

	@Override
	protected void renderBg(PoseStack matrix, float partialTicks, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.setShaderTexture(0, TEXTURE);
		blit(matrix, leftPos, topPos, 0, 0, imageWidth, imageHeight);

		if ((!buttonPressed && menu.shouldAnimate()) || (buttonPressed && shouldAnimate))
			animations(matrix, partialTicks);
	}

	private void animations(PoseStack matrix, float partialTicks) {
		int ticks = minecraft.player.tickCount;

		// Book
		if (ticks / 10 % 10 == 0) {
			blit(matrix, leftPos + BOOK_X, topPos + BOOK_Y, 0 + BOOK_WIDTH * (ticks % 5),
					ANIMATION_BOOK_Y + (ticks % 10 / 5) * BOOK_HEIGHT, BOOK_WIDTH, BOOK_HEIGHT);
		}

		// Face
		RenderSystem.enableBlend();
		RenderSystem.setShaderColor(1, 1, 1,
				Math.max(0, Mth.sin((ticks + partialTicks) / 160 * (float) Math.PI * 2) * 0.3f));
		blit(matrix, leftPos + FACE_X, topPos + FACE_Y, ANIMATION_FACE_X, 0, FACE_WIDTH, FACE_HEIGHT);
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.disableBlend();
	}

	@Override
	protected void renderLabels(PoseStack matrix, int mouseX, int mouseY) {
		font.draw(matrix, title.getString(), 6, 12, Color.DARK_GRAY.getRGB());
		font.draw(matrix, playerInventoryTitle.getString(), 6, 70, Color.DARK_GRAY.getRGB());
	}

	@Override
	public void render(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
		super.render(matrix, mouseX, mouseY, partialTicks);
		renderTooltip(matrix, mouseX, mouseY);
		spellbook.render(matrix, mouseX, mouseY, partialTicks);
		if (toggleAnimationsButton.isHoveredOrFocused())
			renderTooltip(matrix, guiComponent("toggle_animations"), mouseX, mouseY);

		if (spellbookButton.isHoveredOrFocused())
			renderTooltip(matrix, guiComponent("toggle_spellbook"), mouseX, mouseY);
	}

	static Component guiComponent(String key) {
		return new TranslatableComponent("gui." + Main.MODID + "." + key);
	}

}
