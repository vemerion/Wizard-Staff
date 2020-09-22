package mod.vemerion.wizardstaff.staff;

import java.awt.Color;

import mod.vemerion.wizardstaff.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class WizardStaffScreen extends ContainerScreen<WizardStaffContainer> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/staff_screen_new.png");

	public WizardStaffScreen(WizardStaffContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		Minecraft.getInstance().getTextureManager().bindTexture(TEXTURE);
		blit((width - xSize) / 2, (height - ySize) / 2, 0, 0, xSize, ySize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		font.drawString(Main.WIZARD_STAFF_ITEM.getName().getFormattedText(), 6, 12, Color.DARK_GRAY.getRGB());
		font.drawString(playerInventory.getDisplayName().getFormattedText(), 6, 70, Color.DARK_GRAY.getRGB());
	}

}
