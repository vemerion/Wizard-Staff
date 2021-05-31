package mod.vemerion.wizardstaff.staff;

import java.awt.Color;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.capability.ScreenAnimations;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class WizardStaffScreen extends ContainerScreen<WizardStaffContainer> {

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

	public WizardStaffScreen(WizardStaffContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		shouldAnimate = container.shouldAnimate();
		spellbook = new SpellbookGui();
	}

	@Override
	protected void init() {
		super.init();
		spellbook.init(width, height);
		guiLeft = spellbook.updatePosition(width, xSize);
		
		toggleAnimationsButton = addButton(
				new ImageButton(guiLeft + xSize - 18, guiTop + 9, 9, 9, 0, 0, 9, ANIMATION_BUTTON, (button) -> {
					buttonPressed = true;
					shouldAnimate = !shouldAnimate;
				}));

		spellbookButton = addButton(
				new ImageButton(guiLeft + xSize / 2 - 8, (int) (guiTop + ySize * 0.37), 16, 16, 0, 0, 16, SPELLBOOK_BUTTON, (button) -> {
					spellbook.toggleActive();
					guiLeft = spellbook.updatePosition(width, xSize);
					((ImageButton) button).setPosition(guiLeft + xSize / 2 - 8, (int) (guiTop + ySize * 0.37));
					((ImageButton) toggleAnimationsButton).setPosition(guiLeft + xSize - 18, guiTop + 9);
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
	public void onClose() {
		if (buttonPressed && shouldAnimate != container.shouldAnimate())
			ScreenAnimations.sendMessage(Minecraft.getInstance().player, shouldAnimate);
		spellbook.onClose();
		super.onClose();
	}
	
	@Override
	public void tick() {
		super.tick();
		spellbook.tick();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrix, float partialTicks, int mouseX, int mouseY) {
		Minecraft.getInstance().getTextureManager().bindTexture(TEXTURE);
		blit(matrix, guiLeft, guiTop, 0, 0, xSize, ySize);

		if ((!buttonPressed && container.shouldAnimate()) || (buttonPressed && shouldAnimate))
			animations(matrix, partialTicks);
	}

	@SuppressWarnings("deprecation")
	private void animations(MatrixStack matrix, float partialTicks) {
		int ticks = minecraft.player.ticksExisted;

		// Book
		if (ticks / 10 % 10 == 0) {
			blit(matrix, guiLeft + BOOK_X, guiTop + BOOK_Y, 0 + BOOK_WIDTH * (ticks % 5),
					ANIMATION_BOOK_Y + (ticks % 10 / 5) * BOOK_HEIGHT, BOOK_WIDTH, BOOK_HEIGHT);
		}

		// Face
		RenderSystem.enableBlend();
		RenderSystem.alphaFunc(516, 0);
		RenderSystem.color4f(1, 1, 1,
				Math.max(0, MathHelper.sin((ticks + partialTicks) / 160 * (float) Math.PI * 2) * 0.3f));
		blit(matrix, guiLeft + FACE_X, guiTop + FACE_Y, ANIMATION_FACE_X, 0, FACE_WIDTH, FACE_HEIGHT);
		RenderSystem.color4f(1, 1, 1, 1);
		RenderSystem.defaultAlphaFunc();
		RenderSystem.disableBlend();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrix, int mouseX, int mouseY) {
		font.drawString(matrix, title.getString(), 6, 12, Color.DARK_GRAY.getRGB());
		font.drawString(matrix, playerInventory.getDisplayName().getString(), 6, 70, Color.DARK_GRAY.getRGB());
	}

	@Override
	public void render(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
		super.render(matrix, mouseX, mouseY, partialTicks);
		renderHoveredTooltip(matrix, mouseX, mouseY);
		spellbook.render(matrix, mouseX, mouseY, partialTicks);
		if (toggleAnimationsButton.isHovered())
			renderTooltip(matrix, new TranslationTextComponent("gui.wizard-staff.toggle_animations"), mouseX, mouseY);
		
		if (spellbookButton.isHovered())
			renderTooltip(matrix, new TranslationTextComponent("gui.wizard-staff.toggle_spellbook"), mouseX, mouseY);
	}

}
