package com.github.alexnijjar.beyond_earth.client.screens;

import com.github.alexnijjar.beyond_earth.BeyondEarth;
import com.github.alexnijjar.beyond_earth.client.screens.GuiUtil.FloatDrawableHelper;
import com.github.alexnijjar.beyond_earth.util.ModIdentifier;
import com.github.alexnijjar.beyond_earth.util.ModUtils;
import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class PlayerOverlayScreen {

    private static final Identifier OXYGEN_TANK_EMPTY_TEXTURE = new ModIdentifier("textures/gui/overlay/oxygen_tank_empty.png");
    private static final Identifier OXYGEN_TANK_FULL_TEXTURE = new ModIdentifier("textures/gui/overlay/oxygen_tank_full.png");

    // Planet bar textures.
    private static final Identifier EARTH_PLANET_BAR_TEXTURE = new ModIdentifier("textures/gui/planet_bar/earth_planet_bar.png");
    private static final Identifier MOON_PLANET_BAR_TEXTURE = new ModIdentifier("textures/gui/planet_bar/moon_planet_bar.png");
    private static final Identifier MARS_PLANET_BAR_TEXTURE = new ModIdentifier("textures/gui/planet_bar/mars_planet_bar.png");
    private static final Identifier VENUS_PLANET_BAR_TEXTURE = new ModIdentifier("textures/gui/planet_bar/venus_planet_bar.png");
    private static final Identifier MERCURY_PLANET_BAR_TEXTURE = new ModIdentifier("textures/gui/planet_bar/mercury_planet_bar.png");

    private static final Identifier GLACIO_PLANET_BAR_TEXTURE = new ModIdentifier("textures/gui/planet_bar/glacio_planet_bar.png");

    private static final Identifier ORBIT_PLANET_BAR_TEXTURE = new ModIdentifier("textures/gui/planet_bar/orbit_planet_bar.png");

    private static final Identifier ROCKET_PLANET_BAR_TEXTURE = new ModIdentifier("textures/gui/planet_bar/rocket.png");

    private static final Identifier WARNING_TEXTURE = new ModIdentifier("textures/gui/overlay/warning.png");

    private static final Identifier BATTERY_TEXTURE = new ModIdentifier("textures/gui/overlay/battery.png");
    private static final Identifier BATTERY_EMPTY_TEXTURE = new ModIdentifier("textures/gui/overlay/battery_empty.png");

    public static boolean shouldRenderOxygen;
    public static double oxygenRatio;
    public static boolean doesNotNeedOxygen;

    public static int countdownSeconds;

    public static boolean shouldRenderBar;

    public static boolean shouldRenderWarning;
    public static double speed;

    public static boolean shouldRenderBattery;
    public static double batteryRatio;

    public static void render(MatrixStack matrices, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();
        int screenX = client.getWindow().getScaledWidth();
        int screenY = client.getWindow().getScaledHeight();
        ClientPlayerEntity player = client.player;

        if (player.isSpectator()) {
            return;
        }

        // Oxygen
        if (shouldRenderOxygen && !client.options.debugEnabled) {

            int x = 5 + BeyondEarth.CONFIG.general.oxygenBarXOffset;
            int y = 25 + BeyondEarth.CONFIG.general.oxygenBarYOffset;

            int textureWidth = 62;
            int textureHeight = 52;

            GuiUtil.drawVerticalReverse(matrices, x, y, textureWidth, textureHeight, OXYGEN_TANK_EMPTY_TEXTURE, oxygenRatio);
            GuiUtil.drawVertical(matrices, x, y, textureWidth, textureHeight, OXYGEN_TANK_FULL_TEXTURE, oxygenRatio);

            // Oxygen text.
            double oxygen = Math.round(oxygenRatio * 1000) / 10.0;
            Text text = Text.of((oxygen) + "%");
            int textWidth = client.textRenderer.getWidth(text);
            if (doesNotNeedOxygen) {
                client.textRenderer.drawWithShadow(matrices, text, (x + (textureWidth - textWidth) / 2), y + textureHeight + 3, 0x7FFF00);
            } else {
                client.textRenderer.drawWithShadow(matrices, text, (x + (textureWidth - textWidth) / 2), y + textureHeight + 3, oxygen <= 0.0f ? 0xDC143C : 0xFFFFFF);
            }
        }

        // Battery
        if (shouldRenderBattery && !client.options.debugEnabled) {

            int x = screenX - 75 - BeyondEarth.CONFIG.general.energyBarXOffset;
            int y = 25 + BeyondEarth.CONFIG.general.energyBarYOffset;

            int textureWidth = (int)(49 * 1.4);
            int textureHeight = (int)(27 * 1.4);
            GuiUtil.drawHorizontal(matrices, x, y, textureWidth, textureHeight, BATTERY_EMPTY_TEXTURE, 1.0);
            GuiUtil.drawHorizontal(matrices, x, y, textureWidth, textureHeight, BATTERY_TEXTURE, batteryRatio);

            double energy = Math.round(batteryRatio * 1000) / 10.0;
            Text text = Text.of((energy) + "%");
            int textWidth = client.textRenderer.getWidth(text);
            client.textRenderer.drawWithShadow(matrices, text, (x + (textureWidth - textWidth) / 2), y + textureHeight + 3, 0x6082B6);
        }

        // Timer
        if (countdownSeconds > 0) {

            int x = screenX / 2 - 31;
            int y = screenY / 2 / 2;

            RenderSystem.setShaderTexture(0, getTimerTexture());
            DrawableHelper.drawTexture(matrices, x, y, 0, 0, 60, 38, 60, 38);
        }

        // Planet bar
        if (shouldRenderBar && !client.options.debugEnabled) {
            float rocketHeight = (float) (player.getY() / (BeyondEarth.CONFIG.rocket.atmosphereLeave / 113.0f));
            rocketHeight = MathHelper.clamp(rocketHeight, 0, 113);

            int x = 0;
            int y = screenY / 2 - 128 / 2;

            Identifier planet;
            RegistryKey<World> currentWorld = player.getWorld().getRegistryKey();
            if (currentWorld.equals(World.OVERWORLD)) {
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
            DrawableHelper.drawTexture(matrices, x, y, 0, 0, 16, 128, 16, 128);

            // Draw rocket indicator
            RenderSystem.setShaderTexture(0, ROCKET_PLANET_BAR_TEXTURE);
            FloatDrawableHelper.drawTexture(matrices, 4.0f, (screenY / 2) + (103 / 2) - rocketHeight, 0, 0, 8, 11, 8, 11);
        }

        // Warning screen
        if (shouldRenderWarning) {

            matrices.push();
            RenderSystem.enableBlend();
            RenderSystem.setShader(GameRenderer::getPositionTexShader);

            // Flashing.
            float sine = (float) Math.sin((client.world.getTime() + client.getTickDelta()) / 2.0f);

            sine = MathHelper.clamp(sine, 0.0f, 1.0f);
            RenderSystem.setShaderColor(sine, sine, sine, sine);

            // Warning image.
            RenderSystem.setShaderTexture(0, WARNING_TEXTURE);
            DrawableHelper.drawTexture(matrices, screenX / 2 - 58, 50, 0, 0, 116, 21, 116, 21);

            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

            // Speed text.
            Text text = new TranslatableText("message." + BeyondEarth.MOD_ID + ".speed", Math.round(speed * 10.0) / 10.0);
            client.textRenderer.drawWithShadow(matrices, text, screenX / 2 - 29, 80, -3407872);

            RenderSystem.disableBlend();
            matrices.pop();
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

    public static Identifier getTimerTexture() {
        return new ModIdentifier("textures/gui/countdown_numbers/" + countdownSeconds + ".png");
    }
}
