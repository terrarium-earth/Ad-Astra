package earth.terrarium.ad_astra.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.ad_astra.block.machine.entity.AbstractMachineBlockEntity;
import earth.terrarium.ad_astra.screen.menu.AbstractMachineMenu;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

@Environment(EnvType.CLIENT)
public abstract class AbstractMachineScreen<M extends AbstractMachineBlockEntity, H extends AbstractMachineMenu<M>> extends AbstractContainerScreen<H> {

    final M machine;
    final ResourceLocation texture;

    public AbstractMachineScreen(H handler, Inventory inventory, Component title, ResourceLocation texture) {
        super(handler, inventory, title);
        this.texture = texture;
        this.inventoryLabelY = this.imageHeight - 92;
        machine = this.getMenu().getMachine();
    }

    @Override
    protected void renderBg(PoseStack poseStack, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, texture);
        GuiComponent.blit(poseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
        renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, delta);
        renderTooltip(poseStack, mouseX, mouseY);
    }

    public int getTextColour() {
        return 0x404040;
    }

    @Override
    protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
        this.font.draw(poseStack, this.title, (float) this.titleLabelX, (float) this.titleLabelY, this.getTextColour());
        this.font.draw(poseStack, this.playerInventoryTitle, (float) this.inventoryLabelX, (float) this.inventoryLabelY, this.getTextColour());
    }

    public int getLeftPos() {
        return this.leftPos;
    }

    public int getTopPos() {
        return this.topPos;
    }
}