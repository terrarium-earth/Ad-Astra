package earth.terrarium.adastra.client.components;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PressableImageButton extends ImageButton {
    @Nullable
    private ResourceLocation texture;

    public PressableImageButton(int x, int y, int width, int height, int xTexStart, int yTexStart, int yDiffTex, ResourceLocation resourceLocation, int textureWidth, int textureHeight, OnPress onPress) {
        super(x, y, width, height, xTexStart, yTexStart, yDiffTex, resourceLocation, textureWidth, textureHeight, onPress);
    }

    public PressableImageButton(int x, int y, int width, int height, int xTexStart, int yTexStart, int yDiffTex, ResourceLocation resourceLocation, int textureWidth, int textureHeight, OnPress onPress, Component message) {
        super(x, y, width, height, xTexStart, yTexStart, yDiffTex, resourceLocation, textureWidth, textureHeight, onPress, message);
        this.setTooltip(Tooltip.create(message));
    }

    public void setTexture(@Nullable ResourceLocation texture) {
        this.texture = texture;
    }

    @Override
    public void renderTexture(@NotNull GuiGraphics graphics, @NotNull ResourceLocation texture, int x, int y, int uOffset, int vOffset, int textureDifference, int width, int height, int textureWidth, int textureHeight) {
        int v = vOffset + textureDifference;
        if (!this.isActive()) {
            v = vOffset + textureDifference * 2;
        } else if (this.isFocused()) {
            v = vOffset;
        } else if (this.isHovered()) {
            v = vOffset - textureDifference;
        }

        RenderSystem.enableDepthTest();
        graphics.blit(this.texture != null ? this.texture : texture, x, y, uOffset, v, width, height, textureWidth, textureHeight);
    }
}
