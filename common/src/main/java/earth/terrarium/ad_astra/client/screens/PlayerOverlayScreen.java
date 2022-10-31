package earth.terrarium.ad_astra.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.client.screens.GuiUtil.FloatDrawableHelper;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import earth.terrarium.ad_astra.util.ModUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;

@Environment(EnvType.CLIENT)
public class PlayerOverlayScreen {

    private static final ResourceLocation OXYGEN_TANK_EMPTY_TEXTURE = new ModResourceLocation("textures/gui/overlay/oxygen_tank_empty.png");
    private static final ResourceLocation OXYGEN_TANK_FULL_TEXTURE = new ModResourceLocation("textures/gui/overlay/oxygen_tank_full.png");

    // Planet bar textures.
    private static final ResourceLocation EARTH_PLANET_BAR_TEXTURE = new ModResourceLocation("textures/gui/planet_bar/earth_planet_bar.png");
    private static final ResourceLocation MOON_PLANET_BAR_TEXTURE = new ModResourceLocation("textures/gui/planet_bar/moon_planet_bar.png");
    private static final ResourceLocation MARS_PLANET_BAR_TEXTURE = new ModResourceLocation("textures/gui/planet_bar/mars_planet_bar.png");
    private static final ResourceLocation VENUS_PLANET_BAR_TEXTURE = new ModResourceLocation("textures/gui/planet_bar/venus_planet_bar.png");
    private static final ResourceLocation MERCURY_PLANET_BAR_TEXTURE = new ModResourceLocation("textures/gui/planet_bar/mercury_planet_bar.png");

    private static final ResourceLocation GLACIO_PLANET_BAR_TEXTURE = new ModResourceLocation("textures/gui/planet_bar/glacio_planet_bar.png");

    private static final ResourceLocation ORBIT_PLANET_BAR_TEXTURE = new ModResourceLocation("textures/gui/planet_bar/orbit_planet_bar.png");

    private static final ResourceLocation ROCKET_PLANET_BAR_TEXTURE = new ModResourceLocation("textures/gui/planet_bar/rocket.png");

    private static final ResourceLocation WARNING_TEXTURE = new ModResourceLocation("textures/gui/overlay/warning.png");

    private static final ResourceLocation BATTERY_TEXTURE = new ModResourceLocation("textures/gui/overlay/battery.png");
    private static final ResourceLocation BATTERY_EMPTY_TEXTURE = new ModResourceLocation("textures/gui/overlay/battery_empty.png");

    public static boolean shouldRenderOxygen;
    public static double oxygenRatio;
    public static boolean doesNotNeedOxygen;

    public static int countdownSeconds;

    public static boolean shouldRenderBar;

    public static boolean shouldRenderWarning;
    public static double speed;

    public static boolean shouldRenderBattery;
    public static double batteryRatio;

    public static void render(PoseStack matrices, float delta) {
        Minecraft client = Minecraft.getInstance();
        int screenX = client.getWindow().getGuiScaledWidth();
        int screenY = client.getWindow().getGuiScaledHeight();
        LocalPlayer player = client.player;

        if (player.isSpectator()) {
            return;
        }

        // Oxygen
        if (shouldRenderOxygen && !client.options.renderDebug) {

            int x = 5 + AdAstra.CONFIG.general.oxygenBarXOffset;
            int y = 25 + AdAstra.CONFIG.general.oxygenBarYOffset;

            int textureWidth = 62;
            int textureHeight = 52;

            GuiUtil.drawVerticalReverse(matrices, x, y, textureWidth, textureHeight, OXYGEN_TANK_EMPTY_TEXTURE, oxygenRatio);
            GuiUtil.drawVertical(matrices, x, y, textureWidth, textureHeight, OXYGEN_TANK_FULL_TEXTURE, oxygenRatio);

            // Oxygen text
            double oxygen = Math.round(oxygenRatio * 1000) / 10.0;
            Component text = Component.nullToEmpty((oxygen) + "%");
            int textWidth = client.font.width(text);
            if (doesNotNeedOxygen) {
                client.font.drawShadow(matrices, text, (x + (textureWidth - textWidth) / 2.0f), y + textureHeight + 3, 0x7FFF00);
            } else {
                client.font.drawShadow(matrices, text, (x + (textureWidth - textWidth) / 2.0f), y + textureHeight + 3, oxygen <= 0.0f ? 0xDC143C : 0xFFFFFF);
            }
        }

        // Battery
        if (shouldRenderBattery && !client.options.renderDebug) {

            int x = screenX - 75 - AdAstra.CONFIG.general.energyBarXOffset;
            int y = 25 + AdAstra.CONFIG.general.energyBarYOffset;

            int textureWidth = (int) (49 * 1.4);
            int textureHeight = (int) (27 * 1.4);
            GuiUtil.drawHorizontal(matrices, x, y, textureWidth, textureHeight, BATTERY_EMPTY_TEXTURE, 1.0);
            GuiUtil.drawHorizontal(matrices, x, y, textureWidth, textureHeight, BATTERY_TEXTURE, batteryRatio);

            double energy = Math.round(batteryRatio * 1000) / 10.0;
            Component text = Component.nullToEmpty((energy) + "%");
            int textWidth = client.font.width(text);
            client.font.drawShadow(matrices, text, (x + (textureWidth - textWidth) / 2.0f), y + textureHeight + 3, 0x6082B6);
        }

        // Timer
        if (countdownSeconds > 0) {

            int x = screenX / 2 - 31;
            int y = screenY / 2 / 2;

            RenderSystem.setShaderTexture(0, getTimerTexture());
            GuiComponent.blit(matrices, x, y, 0, 0, 60, 38, 60, 38);
        }

        // Planet bar
        if (shouldRenderBar && !client.options.renderDebug) {
            float rocketHeight = (float) (player.getY() / (AdAstra.CONFIG.rocket.atmosphereLeave / 113.0f));
            rocketHeight = Mth.clamp(rocketHeight, 0, 113);

            int x = 0;
            int y = screenY / 2 - 128 / 2;

            ResourceLocation planet;
            ResourceKey<Level> currentWorld = player.getLevel().dimension();
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
            RenderSystem.setShaderTexture(0, planet);
            GuiComponent.blit(matrices, x, y, 0, 0, 16, 128, 16, 128);

            // Draw rocket indicator
            RenderSystem.setShaderTexture(0, ROCKET_PLANET_BAR_TEXTURE);
            FloatDrawableHelper.drawTexture(matrices, 4.0f, (screenY / 2.0f) + (103 / 2.0f) - rocketHeight, 0, 0, 8, 11, 8, 11);
        }

        // Warning screen
        if (shouldRenderWarning) {

            matrices.pushPose();
            RenderSystem.enableBlend();
            RenderSystem.setShader(GameRenderer::getPositionTexShader);

            // Flashing
            float sine = (float) Math.sin((client.level.getGameTime() + client.getFrameTime()) / 2.0f);

            sine = Mth.clamp(sine, 0.0f, 1.0f);
            RenderSystem.setShaderColor(sine, sine, sine, sine);

            // Warning image
            RenderSystem.setShaderTexture(0, WARNING_TEXTURE);
            GuiComponent.blit(matrices, screenX / 2 - 58, 50, 0, 0, 116, 21, 116, 21);

            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

            // Speed text
            Component text = Component.translatable("message." + AdAstra.MOD_ID + ".speed", Math.round(speed * 10.0) / 10.0);
            client.font.drawShadow(matrices, text, screenX / 2.0f - 29, 80, -3407872);

            RenderSystem.disableBlend();
            matrices.popPose();
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

    public static ResourceLocation getTimerTexture() {
        return new ModResourceLocation("textures/gui/countdown_numbers/" + countdownSeconds + ".png");
    }
}
