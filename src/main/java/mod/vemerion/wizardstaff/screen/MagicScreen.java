package mod.vemerion.wizardstaff.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import mod.vemerion.wizardstaff.container.MagicContainer;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class MagicScreen extends AbstractContainerScreen<MagicContainer> {

	private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");

	public MagicScreen(MagicContainer screenContainer, Inventory inv, Component titleIn) {
		super(screenContainer, inv, titleIn);
		this.imageHeight = 114 + 3 * 18;
		this.inventoryLabelY = this.imageHeight - 94;
	}

	@Override
	protected void renderBg(PoseStack matrixStack, float partialTicks, int x, int y) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.setShaderTexture(0, TEXTURE);
		blit(matrixStack, leftPos, topPos, 0, 0, imageWidth, 3 * 18 + 17);
		blit(matrixStack, leftPos, topPos + 3 * 18 + 17, 0, 126, imageWidth, 96);
	}

	@Override
	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		renderTooltip(matrixStack, mouseX, mouseY);
	}

}
