package earth.terrarium.ad_astra.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.client.screen.GuiUtil.FloatGuiComponent;
import earth.terrarium.ad_astra.common.config.AdAstraConfig;
import earth.terrarium.ad_astra.common.config.VehiclesConfig;
import earth.terrarium.ad_astra.common.util.ModUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;

public class PlayerOverlayScreen {

    private static final ResourceLocation OXYGEN_TANK_EMPTY_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/overlay/oxygen_tank_empty.png");
    private static final ResourceLocation OXYGEN_TANK_FULL_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/overlay/oxygen_tank_full.png");

    // Planet bar textures.
    private static final ResourceLocation EARTH_PLANET_BAR_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/planet_bar/earth_planet_bar.png");
    private static final ResourceLocation MOON_PLANET_BAR_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/planet_bar/moon_planet_bar.png");
    private static final ResourceLocation MARS_PLANET_BAR_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/planet_bar/mars_planet_bar.png");
    private static final ResourceLocation VENUS_PLANET_BAR_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/planet_bar/venus_planet_bar.png");
    private static final ResourceLocation MERCURY_PLANET_BAR_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/planet_bar/mercury_planet_bar.png");

    private static final ResourceLocation GLACIO_PLANET_BAR_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/planet_bar/glacio_planet_bar.png");

    private static final ResourceLocation ORBIT_PLANET_BAR_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/planet_bar/orbit_planet_bar.png");

    private static final ResourceLocation ROCKET_PLANET_BAR_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/planet_bar/rocket.png");

    private static final Component WARNING_TEXT = Component.translatable("gui.ad_astra.text.warning");

    private static final ResourceLocation BATTERY_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/overlay/battery.png");
    private static final ResourceLocation BATTERY_EMPTY_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/overlay/battery_empty.png");

    public static boolean shouldRenderOxygen;
    public static double oxygenRatio;
    public static boolean doesNotNeedOxygen;

    public static int countdownSeconds;

    public static boolean shouldRenderBar;

    public static boolean shouldRenderWarning;
    public static double speed;

    public static boolean shouldRenderBattery;
    public static double batteryRatio;

    public static void render(GuiGraphics graphics, float delta) {
        PoseStack poseStack = graphics.pose();
        Minecraft minecraft = Minecraft.getInstance();
        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int screenHeight = minecraft.getWindow().getGuiScaledHeight();
        LocalPlayer player = minecraft.player;

        if (player == null) return;
        if (player.isSpectator()) {
            return;
        }

        // Oxygen
        if (shouldRenderOxygen && !minecraft.options.renderDebug) {

            poseStack.pushPose();
            poseStack.scale(AdAstraConfig.oxygenBarScale, AdAstraConfig.oxygenBarScale, AdAstraConfig.oxygenBarScale);
            int x = 5 + AdAstraConfig.oxygenBarXOffset;
            int y = 25 + AdAstraConfig.oxygenBarYOffset;

            int textureWidth = 62;
            int textureHeight = 52;

            GuiUtil.drawVerticalReverse(graphics, x, y, textureWidth, textureHeight, OXYGEN_TANK_EMPTY_TEXTURE, oxygenRatio);
            GuiUtil.drawVertical(graphics, x, y, textureWidth, textureHeight, OXYGEN_TANK_FULL_TEXTURE, oxygenRatio);

            // Oxygen text
            double oxygen = Math.round(oxygenRatio * 1000) / 10.0;
            Component text = Component.nullToEmpty((oxygen) + "%");
            int textWidth = minecraft.font.width(text);
            if (doesNotNeedOxygen) {
                graphics.drawString(minecraft.font, text, (int) (x + (textureWidth - textWidth) / 2.0f), y + textureHeight + 3, 0x7FFF00);
            } else {
                graphics.drawString(minecraft.font, text, (int) (x + (textureWidth - textWidth) / 2.0f), y + textureHeight + 3, oxygen <= 0.0f ? 0xDC143C : 0xFFFFFF);
            }
            poseStack.popPose();
        }

        // Battery
        if (shouldRenderBattery && !minecraft.options.renderDebug) {

            poseStack.pushPose();
            poseStack.scale(AdAstraConfig.energyBarScale, AdAstraConfig.energyBarScale, AdAstraConfig.energyBarScale);
            int x = screenWidth - 75 - AdAstraConfig.energyBarXOffset;
            int y = 25 + AdAstraConfig.energyBarYOffset;

            int textureWidth = (int) (49 * 1.4);
            int textureHeight = (int) (27 * 1.4);
            GuiUtil.drawHorizontal(graphics, x, y, textureWidth, textureHeight, BATTERY_EMPTY_TEXTURE, 1.0);
            GuiUtil.drawHorizontal(graphics, x, y, textureWidth, textureHeight, BATTERY_TEXTURE, batteryRatio);

            double energy = Math.round(batteryRatio * 1000) / 10.0;
            Component text = Component.nullToEmpty((energy) + "%");
            int textWidth = minecraft.font.width(text);
            graphics.drawString(minecraft.font, text, (int) (x + (textureWidth - textWidth) / 2.0f), y + textureHeight + 3, 0x6082B6);
            poseStack.popPose();
        }

        // Timer
        if (countdownSeconds > 0) {
            poseStack.pushPose();
            poseStack.translate(screenWidth / 2.0f, screenHeight / 2.0f, 0.0);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            poseStack.pushPose();
            poseStack.scale(4.0F, 4.0F, 4.0F);
            var m = minecraft.font.width(countdownSeconds + "");
            graphics.drawString(minecraft.font, countdownSeconds + "", (int) (-m / 2), (int) -10.0F, 0xe53253);
            poseStack.popPose();
            RenderSystem.disableBlend();
            poseStack.popPose();
        }

        // Planet bar
        if (shouldRenderBar && !minecraft.options.renderDebug) {
            float rocketHeight = (float) (player.getY() / (VehiclesConfig.RocketConfig.atmosphereLeave / 113.0f));
            rocketHeight = Mth.clamp(rocketHeight, 0, 113);

            int x = 0;
            int y = screenHeight / 2 - 128 / 2;

            ResourceLocation planet;
            ResourceKey<Level> currentWorld = player.level().dimension();
            if (currentWorld.equals(Level.OVERWORLD)) {
                planet = EARTH_PLANET_BAR_TEXTURE;
            } else if (currentWorld.equals(ModUtils.MOON_KEY)) {
                planet = MOON_PLANET_BAR_TEXTURE;
            } else if (currentWorld.equals(ModUtils.MARS_KEY)) {
                planet = MARS_PLANET_BAR_TEXTURE;
            } else if (currentWorld.equals(ModUtils.VENUS_KEY)) {
                planet = VENUS_PLANET_BAR_TEXTURE;
            } else if (currentWorld.equals(ModUtils.MERCURY_KEY)) {
                planet = MERCURY_PLANET_BAR_TEXTURE;
            } else if (currentWorld.equals(ModUtils.GLACIO_KEY)) {
                planet = GLACIO_PLANET_BAR_TEXTURE;
            } else {
                planet = ORBIT_PLANET_BAR_TEXTURE;
            }

            // Draw planet
            graphics.blit(planet, x, y, 0, 0, 16, 128, 16, 128);

            // Draw rocket indicator
            RenderSystem.setShaderTexture(0, ROCKET_PLANET_BAR_TEXTURE);
            FloatGuiComponent.drawTexture(graphics, 4.0f, (screenHeight / 2.0f) + (103 / 2.0f) - rocketHeight, 0, 0, 8, 11, 8, 11);
        }

        // Warning screen
        if (shouldRenderWarning) {

            poseStack.pushPose();
            RenderSystem.enableBlend();
            RenderSystem.setShader(GameRenderer::getPositionTexShader);

            // Flashing
            float sine = (float) Math.sin((minecraft.level.getGameTime() + (minecraft.isPaused() ? 0 : minecraft.getFrameTime())) / 5.0f);

            sine = Mth.clamp(sine, 0.0f, 1.0f);
            RenderSystem.setShaderColor(sine, sine, sine, sine);

            poseStack.pushPose();
            poseStack.translate(screenWidth / 2.0f, screenHeight / 2.0f / 1.5f, 0.0);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            poseStack.pushPose();
            poseStack.scale(4.0F, 4.0F, 4.0F);
            var m = minecraft.font.width(WARNING_TEXT);
            graphics.drawString(minecraft.font, WARNING_TEXT, -m / 2, (int) -10.0F, FastColor.ARGB32.color((int) (sine * 255), (int) (sine * 255), (int) (sine * 255), (int) (sine * 255)));
            poseStack.popPose();
            RenderSystem.disableBlend();
            poseStack.popPose();
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    public static void disableAllOverlays() {
        shouldRenderOxygen = false;
        shouldRenderBattery = false;
        shouldRenderBar = false;
        shouldRenderWarning = false;
    }

    public static void disableAllVehicleOverlays() {
        PlayerOverlayScreen.shouldRenderBar = false;
        PlayerOverlayScreen.countdownSeconds = 0;
        PlayerOverlayScreen.shouldRenderWarning = false;
    }
}
