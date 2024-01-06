package earth.terrarium.adastra.client.components;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class LabeledImageButton extends ImageButton {

    public LabeledImageButton(int x, int y, int width, int height, int xTexStart, int yTexStart, int yDiffTex, ResourceLocation resourceLocation, int textureWidth, int textureHeight, OnPress onPress) {
        super(x, y, width, height, xTexStart, yTexStart, yDiffTex, resourceLocation, textureWidth, textureHeight, onPress);
    }

    public LabeledImageButton(int x, int y, int width, int height, int xTexStart, int yTexStart, int yDiffTex, ResourceLocation resourceLocation, int textureWidth, int textureHeight, OnPress onPress, Component message) {
        super(x, y, width, height, xTexStart, yTexStart, yDiffTex, resourceLocation, textureWidth, textureHeight, onPress, message);
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
         if (!isActive()) {
            RenderSystem.setShaderColor(0.5f, 0.5f, 0.5f, 1);
        }

        RenderSystem.enableBlend();
        renderTexture(graphics, resourceLocation, getX(), getY(), xTexStart, yTexStart, yDiffTex, width, height, textureWidth, textureHeight);
        RenderSystem.disableBlend();

        if (!isActive()) {
            RenderSystem.setShaderColor(1, 1, 1, 1);
        }

        int color = active ? 0xffffff : 0xa0a0a0;
        graphics.drawCenteredString(Minecraft.getInstance().font, getMessage(), getX() + width / 2, getY() + (height - 8) / 2, color | Mth.ceil(alpha * 255.0F) << 24);
    }
}
