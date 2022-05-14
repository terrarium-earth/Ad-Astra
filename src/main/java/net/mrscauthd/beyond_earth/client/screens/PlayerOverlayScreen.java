package net.mrscauthd.beyond_earth.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.mrscauthd.beyond_earth.gui.GuiUtil;
import net.mrscauthd.beyond_earth.util.ModIdentifier;
import net.mrscauthd.beyond_earth.util.ModUtils;

@Environment(EnvType.CLIENT)
public class PlayerOverlayScreen {

    private static final Identifier OXYGEN_TANK_EMPTY_TEXTURE = new ModIdentifier("textures/overlay/oxygen_tank_empty.png");
    private static final Identifier OXYGEN_TANK_FULL_TEXTURE = new ModIdentifier("textures/overlay/oxygen_tank_full.png");

    // Planet bar textures.
    private static final Identifier EARTH_PLANET_BAR_TEXTURE = new ModIdentifier("textures/planet_bar/earth_planet_bar.png");
    private static final Identifier MOON_PLANET_BAR_TEXTURE = new ModIdentifier("textures/planet_bar/moon_planet_bar.png");
    private static final Identifier MARS_PLANET_BAR_TEXTURE = new ModIdentifier("textures/planet_bar/mars_planet_bar.png");
    private static final Identifier VENUS_PLANET_BAR_TEXTURE = new ModIdentifier("textures/planet_bar/venus_planet_bar.png");
    private static final Identifier MERCURY_PLANET_BAR_TEXTURE = new ModIdentifier("textures/planet_bar/mercury_planet_bar.png");

    private static final Identifier GLACIO_PLANET_BAR_TEXTURE = new ModIdentifier("textures/planet_bar/glacio_planet_bar.png");

    private static final Identifier ORBIT_PLANET_BAR_TEXTURE = new ModIdentifier("textures/planet_bar/orbit_planet_bar.png");

    private static final Identifier ROCKET_PLANET_BAR_TEXTURE = new ModIdentifier("textures/planet_bar/rocket.png");

    // private static final Identifier WARNING_TEXTURE = new ModIdentifier("textures/overlay/warning.png");

    public static boolean shouldRenderOxygen;
    public static double oxygenRatio;

    public static int countdownSeconds;

    public static boolean shouldRenderBar;

    public static void render(MatrixStack matrices, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();
        int screenX = client.getWindow().getScaledWidth();
        int screenY = client.getWindow().getScaledHeight();
        ClientPlayerEntity player = client.player;

        // Oxygen.
        if (shouldRenderOxygen) {

            int x = 5;
            int y = 25;

            int textureWidth = 62;
            int textureHeight = 52;

            GuiUtil.drawVerticalReverse(matrices, x, y, textureWidth, textureHeight, OXYGEN_TANK_EMPTY_TEXTURE, oxygenRatio);
            GuiUtil.drawVertical(matrices, x, y, textureWidth, textureHeight, OXYGEN_TANK_FULL_TEXTURE, oxygenRatio);

            // Oxygen text.
            Text text = Text.of((Math.round(oxygenRatio * 100)) + "%");
            int textWidth = client.textRenderer.getWidth(text);
            client.textRenderer.drawWithShadow(matrices, text, (x + (textureWidth - textWidth) / 2), y + textureHeight + 3, 0xFFFFFF);
        }

        // Timer.
        if (countdownSeconds > 0) {

            int x = screenX / 2 - 31;
            int y = screenY / 2 / 2;

            RenderSystem.setShaderTexture(0, getTimerTexture());
            DrawableHelper.drawTexture(matrices, x, y, 0, 0, 60, 38, 60, 38);
        }

        if (shouldRenderBar) {
            int rocketHeight = (int) (player.getY() / 5.3f);
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

            // Draw planet.
            RenderSystem.setShaderTexture(0, planet);
            DrawableHelper.drawTexture(matrices, x, y, 0, 0, 16, 128, 16, 128);

            // Draw rocket.
            RenderSystem.setShaderTexture(0, ROCKET_PLANET_BAR_TEXTURE);
            DrawableHelper.drawTexture(matrices, 4, (screenY / 2) + (103 / 2) - rocketHeight, 0, 0, 8, 11, 8, 11);
        }
    }

    public static Identifier getTimerTexture() {
        return new ModIdentifier("textures/timer/timer" + countdownSeconds + ".png");
    }
}
