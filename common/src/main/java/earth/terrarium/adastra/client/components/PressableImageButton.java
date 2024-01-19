package earth.terrarium.adastra.client.components;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class PressableImageButton extends ImageButton {
    private WidgetSprites sprites;

    public PressableImageButton(int x, int y, int width, int height, WidgetSprites sprites, OnPress onPress) {
        super(x, y, width, height, sprites, onPress);
        this.sprites = sprites;
    }

    public PressableImageButton(int x, int y, int width, int height, WidgetSprites sprites, OnPress onPress, Component message) {
        super(x, y, width, height, sprites, onPress, message);
        this.setTooltip(Tooltip.create(message));
        this.sprites = sprites;
    }

    public void setSprites(WidgetSprites sprites) {
        this.sprites = sprites;
    }

    @Override
	public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		ResourceLocation texture = sprites.get(isActive(), isHoveredOrFocused());
		guiGraphics.blitSprite(texture, getX(), getY(), width, height);
	}

//    @Override
//    public void renderTexture(@NotNull GuiGraphics graphics, @NotNull ResourceLocation texture, int x, int y, int uOffset, int vOffset, int textureDifference, int width, int height, int textureWidth, int textureHeight) {
//        int v = vOffset + textureDifference;
//        if (!this.isActive()) {
//            v = vOffset + textureDifference * 2;
//        } else if (this.isFocused()) {
//            v = vOffset;
//        } else if (this.isHovered()) {
//            v = vOffset - textureDifference;
//        }
//
//        RenderSystem.enableDepthTest();
//        graphics.blit(this.texture != null ? this.texture : texture, x, y, uOffset, v, width, height, textureWidth, textureHeight);
//    }
}
