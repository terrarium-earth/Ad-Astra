package net.mrscauthd.beyond_earth.client.screens;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.mrscauthd.beyond_earth.gui.GuiUtil;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

@Environment(EnvType.CLIENT)
public class PlayerOverlayScreen {

    private static final Identifier OXYGEN_TANK_EMPTY_TEXTURE = new ModIdentifier("textures/overlay/oxygen_tank_empty.png");
    private static final Identifier OXYGEN_TANK_FULL_TEXTURE = new ModIdentifier("textures/overlay/oxygen_tank_full.png");

    public static double ratio;
    public static boolean shouldRender;

    public static void render(MatrixStack matrices, float delta) {
        if (shouldRender) {

            MinecraftClient client = MinecraftClient.getInstance();

            int x = 5;
            int y = 25;

            int textureWidth = 62;
            int textureHeight = 52;

            GuiUtil.drawVerticalReverse(matrices, x, y, textureWidth, textureHeight, OXYGEN_TANK_EMPTY_TEXTURE, ratio);
            GuiUtil.drawVertical(matrices, x, y, textureWidth, textureHeight, OXYGEN_TANK_FULL_TEXTURE, ratio);

            // Oxygen text.
            Text text = Text.of((Math.round(ratio * 100)) + "%");
            int textWidth = client.textRenderer.getWidth(text);
            client.textRenderer.drawWithShadow(matrices, text, (x + (textureWidth - textWidth) / 2), y + textureHeight + 3, 0xFFFFFF);
        }
    }
}
