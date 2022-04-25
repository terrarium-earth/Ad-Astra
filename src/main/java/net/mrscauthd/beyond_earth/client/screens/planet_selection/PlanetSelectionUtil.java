package net.mrscauthd.beyond_earth.client.screens.planet_selection;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import net.mrscauthd.beyond_earth.BeyondEarth;

@Environment(EnvType.CLIENT)
public class PlanetSelectionUtil {

    public static Text createText(String text) {
        return new TranslatableText("gui." + BeyondEarth.MOD_ID + ".planet_selection." + text);
    }

    public static void addTexture(MatrixStack matrices, int x, int y, int width, int height, Identifier texture) {
        RenderSystem.setShaderTexture(0, texture);
        DrawableHelper.drawTexture(matrices, x, y, 0, 0, width, height, width, height);
    }

    public static void addRotatingTexture(PlanetSelectionScreen screen, MatrixStack matrices, int x, int y, int width, int height, Identifier texture, float speed) {

        matrices.push();

        matrices.translate(screen.width / 2.0f, screen.height / 2.0f, 0);
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(screen.getGuiTime() * (speed / 3.0f)));

        addTexture(matrices, x, y, width, height, texture);

        matrices.translate(-screen.width / 2.0f, -screen.height / 2.0f, 0);
        matrices.pop();
    }
}