package earth.terrarium.adastra.client.components;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

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
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        ResourceLocation texture = sprites.get(!isFocused(), isHovered());
        graphics.blitSprite(texture, getX(), getY(), width, height);
    }
}
