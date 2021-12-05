package net.mrscauthd.boss_tools.gui.helper;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.gui.components.Button;

@OnlyIn(Dist.CLIENT)
public class ImageButtonPlacer extends net.minecraft.client.gui.components.Button {
    private ResourceLocation resourceLocation;
    private final int xTexStart;
    private final int yTexStart;
    private final int yDiffText;
    private final int textureWidth;
    private final int textureHeight;

    public ImageButtonPlacer(int xIn, int yIn, int widthIn, int heightIn, int xTexStartIn, int yTexStartIn, int yDiffTextIn, ResourceLocation resourceLocationIn, Button.OnPress onPressIn) {
        this(xIn, yIn, widthIn, heightIn, xTexStartIn, yTexStartIn, yDiffTextIn, resourceLocationIn, 256, 256, onPressIn);
    }

    public ImageButtonPlacer(int xIn, int yIn, int widthIn, int heightIn, int xTexStartIn, int yTexStartIn, int yDiffTextIn, ResourceLocation resourceLocationIn, int p_i51135_9_, int p_i51135_10_, Button.OnPress onPressIn) {
        this(xIn, yIn, widthIn, heightIn, xTexStartIn, yTexStartIn, yDiffTextIn, resourceLocationIn, p_i51135_9_, p_i51135_10_, onPressIn, TextComponent.EMPTY);
    }

    public ImageButtonPlacer(int x, int y, int width, int height, int xTexStart, int yTexStart, int yDiffText, ResourceLocation resourceLocation, int textureWidth, int textureHeight, Button.OnPress onPress, Component title) {
        this(x, y, width, height, xTexStart, yTexStart, yDiffText, resourceLocation, textureWidth, textureHeight, onPress, Button.NO_TOOLTIP, title);
    }

    public ImageButtonPlacer(int p_i244513_1_, int p_i244513_2_, int p_i244513_3_, int p_i244513_4_, int p_i244513_5_, int p_i244513_6_, int p_i244513_7_, ResourceLocation p_i244513_8_, int p_i244513_9_, int p_i244513_10_, Button.OnPress p_i244513_11_, net.minecraft.client.gui.components.Button.OnTooltip p_i244513_12_, Component p_i244513_13_) {
        super(p_i244513_1_, p_i244513_2_, p_i244513_3_, p_i244513_4_, p_i244513_13_, p_i244513_11_, p_i244513_12_);
        this.textureWidth = p_i244513_9_;
        this.textureHeight = p_i244513_10_;
        this.xTexStart = p_i244513_5_;
        this.yTexStart = p_i244513_6_;
        this.yDiffText = p_i244513_7_;
        this.resourceLocation = p_i244513_8_;
    }

    public void setPosition(int xIn, int yIn) {
        this.x = xIn;
        this.y = yIn;
    }

    public void setTexture(ResourceLocation texture) {
        this.resourceLocation = texture;
    }

    public void renderButton(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();

        minecraft.getTextureManager().bindForSetup(this.resourceLocation);
        int i = this.yTexStart;
        if (this.isHoveredOrFocused()) {
            i += this.yDiffText;
        }

        RenderSystem.enableDepthTest();
        blit(matrixStack, this.x, this.y, (float)this.xTexStart, (float)i, this.width, this.height, this.textureWidth, this.textureHeight);

        Font fontrenderer = minecraft.font;
        int j = getFGColor();
        drawCenteredString(matrixStack, fontrenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | Mth.ceil(this.alpha * 255.0F) << 24);
    }
}