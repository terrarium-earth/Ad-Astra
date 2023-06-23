package earth.terrarium.ad_astra.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import earth.terrarium.ad_astra.common.entity.vehicle.Vehicle;
import earth.terrarium.ad_astra.common.screen.menu.AbstractVehicleMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public abstract class AbstractVehicleScreen<T extends AbstractVehicleMenu> extends AbstractContainerScreen<T> {

    final Vehicle vehicle;
    final ResourceLocation texture;

    public AbstractVehicleScreen(T handler, Inventory inventory, Component title, ResourceLocation texture) {
        super(handler, inventory, title);
        this.texture = texture;
        this.imageWidth = 176;
        this.imageHeight = 166;
        this.inventoryLabelY = this.imageHeight - 92;
        this.titleLabelX = imageWidth / 2;
        vehicle = this.getMenu().getVehicleEntity();
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        graphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, delta);
        renderTooltip(graphics, mouseX, mouseY);
    }

    public int getTextColour() {
        return 0x404040;
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.drawString(font, this.title, this.titleLabelX - font.width(this.title) / 2, this.titleLabelY, this.getTextColour(), false);
        graphics.drawString(font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, this.getTextColour(), false);
    }
}