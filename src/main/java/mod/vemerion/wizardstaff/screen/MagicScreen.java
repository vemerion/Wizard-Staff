package mod.vemerion.wizardstaff.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import mod.vemerion.wizardstaff.container.MagicContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class MagicScreen extends ContainerScreen<MagicContainer> {

	private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");

	public MagicScreen(MagicContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		this.ySize = 114 + 3 * 18;
		this.playerInventoryTitleY = this.ySize - 94;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
		Minecraft.getInstance().getTextureManager().bindTexture(TEXTURE);
		blit(matrixStack, guiLeft, guiTop, 0, 0, xSize, 3 * 18 + 17);
		blit(matrixStack, guiLeft, guiTop + 3 * 18 + 17, 0, 126, xSize, 96);
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		renderHoveredTooltip(matrixStack, mouseX, mouseY);
	}

}
