package earth.terrarium.ad_astra.common.compat.emi;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.emi.emi.api.widget.Bounds;
import dev.emi.emi.api.widget.Widget;
import earth.terrarium.ad_astra.client.screen.GuiUtil;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;

public class EmiFluidBarWidget extends Widget {
    private final int x;
    private final int y;
    private final FluidVariant fluid;
    private final int speed;
    private final boolean increase;

    public EmiFluidBarWidget(int x, int y, FluidVariant fluid, int speed, boolean increase) {
        this.x = x;
        this.y = y;
        this.fluid = fluid;
        this.speed = speed;
        this.increase = increase;

    }

    @Override
    public Bounds getBounds() {
        return new Bounds(0, 0, 1000, 1000);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {

        int tankX = x + 5;
        int tankY = y + 3;

        double ratio = (GuiUtil.FLUID_TANK_HEIGHT - Mth.ceil((System.currentTimeMillis() / (speed / GuiUtil.FLUID_TANK_HEIGHT) % GuiUtil.FLUID_TANK_HEIGHT))) / (double) GuiUtil.FLUID_TANK_HEIGHT;

        if (increase) {
            drawFluid(poseStack, tankX, tankY, 1.0 - ratio, fluid);
        } else {
            drawFluid(poseStack, tankX, tankY, ratio, fluid);
        }

        // Draw the lines texture.
        RenderSystem.enableBlend();
        GuiUtil.drawVertical(poseStack, tankX, tankY, GuiUtil.FLUID_TANK_WIDTH, GuiUtil.FLUID_TANK_HEIGHT, GuiUtil.FLUID_TANK_TEXTURE, 1.0);
        RenderSystem.disableBlend();
    }

    // Draws fluid in a bit of a weird way. Normally in all the other GUIs, the fluid is drawn in full, and then cut with
    // GL_SCISSOR. However, this is seems to be broken in the EMI gui, so instead, the sprite is scaled, causing the texture to look
    // pinched.
    private static void drawFluid(PoseStack poseStack, int x, int y, double ratio, FluidVariant fluid) {
        if (fluid.isBlank()) {
            return;
        }

        TextureAtlasSprite sprite = FluidVariantRendering.getSprite(fluid);
        int colour = FluidVariantRendering.getColor(fluid);
        int spriteHeight = sprite.contents().height();

        RenderSystem.setShaderColor((colour >> 16 & 255) / 255.0f, (float) (colour >> 8 & 255) / 255.0f, (float) (colour & 255) / 255.0f, 1.0f);
        RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);

        for (int i = 0; i < 3; i++) {

            int height = spriteHeight;

            if (i == 0 && ratio < 0.33) {
                height = (int) (spriteHeight * ratio * 3);
            }
            if (i == 1 && ratio < 0.66) {
                height = (int) (spriteHeight * ratio * 3) - spriteHeight;
            }
            if (i == 2) {
                height = (int) (spriteHeight * ratio * 3) - spriteHeight - spriteHeight;
            }

            int yOffset = y + spriteHeight - height;

            GuiComponent.blit(poseStack, x + 1, GuiUtil.FLUID_TANK_HEIGHT + yOffset - (spriteHeight * (i + 1)), 0, GuiUtil.FLUID_TANK_WIDTH - 2, height, sprite);
        }

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
