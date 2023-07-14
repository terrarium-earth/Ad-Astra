package earth.terrarium.ad_astra.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.teamresourceful.resourcefullib.client.utils.RenderUtils;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.client.ClientPlatformUtils;
import earth.terrarium.botarium.api.fluid.ClientFluidHooks;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;

import java.awt.*;

@Environment(EnvType.CLIENT)
public class GuiUtil {

    public static final int FIRE_WIDTH = 14;
    public static final int FIRE_HEIGHT = 14;
    public static final int SNOWFLAKE_WIDTH = 13;
    public static final int SNOWFLAKE_HEIGHT = 13;
    public static final int SUN_WIDTH = 21;
    public static final int SUN_HEIGHT = 21;
    public static final int HAMMER_WIDTH = 13;
    public static final int HAMMER_HEIGHT = 13;
    public static final int ENERGY_WIDTH = 13;
    public static final int ENERGY_HEIGHT = 46;
    public static final int FLUID_TANK_WIDTH = 14;
    public static final int FLUID_TANK_HEIGHT = 48;
    public static final int ARROW_WIDTH = 24;
    public static final int ARROW_HEIGHT = 17;

    public static final ResourceLocation FIRE_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/fire_on.png");
    public static final ResourceLocation SNOWFLAKE_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/snowflake.png");
    public static final ResourceLocation SUN_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/sun.png");
    public static final ResourceLocation HAMMER_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/hammer.png");
    public static final ResourceLocation ENERGY_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/energy_full.png");
    public static final ResourceLocation FLUID_TANK_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/fluid_tank.png");
    public static final ResourceLocation ARROW_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/arrow.png");

    public static boolean isHovering(Rectangle bounds, double x, double y) {
        double left = bounds.getX();
        double right = left + bounds.getWidth();
        double top = bounds.getY();
        double bottom = top + bounds.getHeight();
        return left <= x && x < right && top <= y && y < bottom;
    }

    public static Rectangle getFluidTankBounds(int x, int y) {
        return new Rectangle(x, y, FLUID_TANK_WIDTH, FLUID_TANK_HEIGHT);
    }

    public static Rectangle getHammerBounds(int x, int y) {
        return new Rectangle(x, y, HAMMER_WIDTH, HAMMER_HEIGHT);
    }

    public static Rectangle getEnergyBounds(int x, int y) {
        return new Rectangle(x, y, ENERGY_WIDTH, ENERGY_HEIGHT);
    }

    public static Rectangle getFireBounds(int x, int y) {
        return new Rectangle(x, y, FIRE_WIDTH, FIRE_HEIGHT);
    }

    public static void drawEnergy(PoseStack poseStack, int x, int y, long energy, long maxEnergy) {
        double ratio = maxEnergy > 0 ? (energy / (float) maxEnergy) : 0;
        drawVertical(poseStack, x, y, ENERGY_WIDTH, ENERGY_HEIGHT, ENERGY_TEXTURE, ratio);
    }

    public static void drawFluidTank(PoseStack poseStack, int x, int y, long fluidCapacity, FluidHolder fluid) {
        double ratio = fluidCapacity > 0 ? (fluid.getFluidAmount() / (float) fluidCapacity) : 0;
        drawFluidTank(poseStack, x, y, ratio, fluid);
    }

    public static void drawFluidTank(PoseStack poseStack, int x, int y, double ratio, FluidHolder fluid) {
        // Draw the fluid
        drawFluid(poseStack, x, y, ratio, fluid);
        // Draw the fluid tank
        RenderSystem.enableBlend();
        drawVertical(poseStack, x, y, FLUID_TANK_WIDTH, FLUID_TANK_HEIGHT, FLUID_TANK_TEXTURE, 1.0);
        RenderSystem.disableBlend();
    }

    private static void drawFluid(PoseStack poseStack, int x, int y, double ratio, FluidHolder fluid) {

        if (fluid.isEmpty()) {
            return;
        }

        TextureAtlasSprite sprite = ClientFluidHooks.getFluidSprite(fluid);
        int colour = ClientFluidHooks.getFluidColor(fluid);
        int spriteHeight = sprite.getHeight();

        RenderSystem.setShaderColor((colour >> 16 & 255) / 255.0f, (float) (colour >> 8 & 255) / 255.0f, (float) (colour & 255) / 255.0f, 1.0f);
        RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);

        int calcHeight =  (int)((FLUID_TANK_HEIGHT + 1) * ratio);
        try (var ignored = RenderUtils.createScissorBox(Minecraft.getInstance(), poseStack, x, y + FLUID_TANK_HEIGHT - calcHeight, FLUID_TANK_WIDTH, calcHeight)) {
            for (int i = 1; i < 4; i++) {
                GuiComponent.blit(poseStack, x + 1, FLUID_TANK_HEIGHT + y - (spriteHeight * i), 0, FLUID_TANK_WIDTH - 2, spriteHeight, sprite);
            }
        }
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void drawFire(PoseStack poseStack, int x, int y, int burnTime, int totalBurnTime) {
        double ratio = totalBurnTime > 0 ? (burnTime / (float) totalBurnTime) : 0;
        drawVertical(poseStack, x, y, FIRE_WIDTH, FIRE_HEIGHT, FIRE_TEXTURE, ratio);
    }

    public static void drawSnowflake(PoseStack poseStack, int x, int y, int burnTime, int totalBurnTime) {
        double ratio = totalBurnTime > 0 ? (burnTime / (float) totalBurnTime) : 0;
        drawHorizontal(poseStack, x, y, SNOWFLAKE_WIDTH, SNOWFLAKE_HEIGHT, SNOWFLAKE_TEXTURE, ratio);
    }

    public static void drawSun(PoseStack poseStack, int x, int y) {
        drawHorizontal(poseStack, x, y, SUN_WIDTH, SUN_HEIGHT, SUN_TEXTURE, 1.0);
    }

    public static void drawHammer(PoseStack poseStack, int x, int y, int burnTime, int totalBurnTime) {
        double ratio = totalBurnTime > 0 ? (burnTime / (float) totalBurnTime) : 0;
        drawHorizontal(poseStack, x, y, HAMMER_WIDTH, HAMMER_HEIGHT, HAMMER_TEXTURE, ratio);
    }

    public static void drawVertical(PoseStack poseStack, int x, int y, int width, int height, ResourceLocation resource, double ratio) {
        int ratioHeight = (int) Math.ceil(height * ratio);
        int remainHeight = height - ratioHeight;
        RenderSystem.setShaderTexture(0, resource);
        GuiComponent.blit(poseStack, x, y + remainHeight, 0, remainHeight, width, ratioHeight, width, height);
    }

    public static void drawVerticalReverse(PoseStack poseStack, int x, int y, int width, int height, ResourceLocation resource, double ratio) {
        int ratioHeight = (int) Math.ceil(height * ratio);
        int remainHeight = height - ratioHeight;
        RenderSystem.setShaderTexture(0, resource);
        GuiComponent.blit(poseStack, x, y, 0, 0, width, remainHeight, width, height);
    }

    public static void drawHorizontal(PoseStack poseStack, int x, int y, int width, int height, ResourceLocation resource, double ratio) {
        int ratioWidth = (int) Math.ceil(width * ratio);

        RenderSystem.setShaderTexture(0, resource);
        GuiComponent.blit(poseStack, x, y, 0, 0, ratioWidth, height, width, height);
    }

    public static void drawEnergyTooltip(Screen screen, PoseStack poseStack, long energy, long energyCapacity, int mouseX, int mouseY) {
        screen.renderTooltip(poseStack, Component.translatable("gauge_text.ad_astra.storage", Mth.clamp(energy, 0, energyCapacity), energyCapacity).setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)), mouseX, mouseY);
    }


    public static void drawTankTooltip(Screen screen, PoseStack poseStack, FluidHolder tank, long capacity, int mouseX, int mouseY) {
        screen.renderTooltip(poseStack, Component.translatable("gauge_text.ad_astra.liquid_storage", FluidHooks.toMillibuckets(tank.getFluidAmount()), FluidHooks.toMillibuckets(capacity)).setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)).append(Component.nullToEmpty(", ")).append(ClientPlatformUtils.getFluidTranslation(tank.getFluid())), mouseX, mouseY);
    }

    public static class FloatGuiComponent {
        public static void drawTexture(PoseStack poseStack, float x, float y, float u, float v, int width, int height, int textureWidth, int textureHeight) {
            FloatGuiComponent.drawTexture(poseStack, x, y, width, height, u, v, width, height, textureWidth, textureHeight);
        }

        public static void drawTexture(PoseStack poseStack, float x, float y, int width, int height, float u, float v, int regionWidth, int regionHeight, int textureWidth, int textureHeight) {
            FloatGuiComponent.drawTexture(poseStack, x, x + width, y, y + height, 0, regionWidth, regionHeight, u, v, textureWidth, textureHeight);
        }

        private static void drawTexture(PoseStack poseStack, float x0, float x1, float y0, float y1, int z, int regionWidth, int regionHeight, float u, float v, int textureWidth, int textureHeight) {
            FloatGuiComponent.drawTexturedQuad(poseStack.last().pose(), x0, x1, y0, y1, z, (u + 0.0f) / (float) textureWidth, (u + (float) regionWidth) / (float) textureWidth, (v + 0.0f) / (float) textureHeight, (v + (float) regionHeight) / (float) textureHeight);
        }

        private static void drawTexturedQuad(Matrix4f matrix, float x0, float x1, float y0, float y1, int z, float u0, float u1, float v0, float v1) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
            bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            bufferBuilder.vertex(matrix, x0, y1, z).uv(u0, v1).endVertex();
            bufferBuilder.vertex(matrix, x1, y1, z).uv(u1, v1).endVertex();
            bufferBuilder.vertex(matrix, x1, y0, z).uv(u1, v0).endVertex();
            bufferBuilder.vertex(matrix, x0, y0, z).uv(u0, v0).endVertex();
            BufferUploader.drawWithShader(bufferBuilder.end());
        }
    }
}