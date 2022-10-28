package earth.terrarium.ad_astra.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import earth.terrarium.ad_astra.entities.vehicles.VehicleEntity;
import earth.terrarium.ad_astra.screen.handler.AbstractVehicleScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public abstract class AbstractVehicleScreen<T extends AbstractVehicleScreenHandler> extends HandledScreen<T> {

    final VehicleEntity vehicle;
    final Identifier texture;

    public AbstractVehicleScreen(T handler, PlayerInventory inventory, Text title, Identifier texture) {
        super(handler, inventory, title);
        this.texture = texture;
        this.backgroundWidth = 176;
        this.backgroundHeight = 166;
        this.playerInventoryTitleY = this.backgroundHeight - 92;
        this.titleX = backgroundWidth / 2;
        vehicle = this.getScreenHandler().getVehicleEntity();
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, texture);
        DrawableHelper.drawTexture(matrices, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight, this.backgroundWidth, this.backgroundHeight);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    public int getTextColour() {
        return 0x404040;
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        this.textRenderer.draw(matrices, this.title, (float) (this.titleX - textRenderer.getWidth(this.title) / 2), (float) this.titleY, this.getTextColour());
        this.textRenderer.draw(matrices, this.playerInventoryTitle, (float) this.playerInventoryTitleX, (float) this.playerInventoryTitleY, this.getTextColour());
    }
}