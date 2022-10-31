package earth.terrarium.ad_astra.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.ad_astra.entities.vehicles.VehicleEntity;
import earth.terrarium.ad_astra.screen.handler.AbstractVehicleScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

@Environment(EnvType.CLIENT)
public abstract class AbstractVehicleScreen<T extends AbstractVehicleScreenHandler> extends AbstractContainerScreen<T> {

    final VehicleEntity vehicle;
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
    protected void renderBg(PoseStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, texture);
        GuiComponent.blit(matrices, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(PoseStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        renderTooltip(matrices, mouseX, mouseY);
    }

    public int getTextColour() {
        return 0x404040;
    }

    @Override
    protected void renderLabels(PoseStack matrices, int mouseX, int mouseY) {
        this.font.draw(matrices, this.title, (float) (this.titleLabelX - font.width(this.title) / 2), (float) this.titleLabelY, this.getTextColour());
        this.font.draw(matrices, this.playerInventoryTitle, (float) this.inventoryLabelX, (float) this.inventoryLabelY, this.getTextColour());
    }
}