package net.mrscauthd.beyond_earth.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

import java.awt.*;

public class GuiUtil {

    public static final Identifier FIRE_PATH = new ModIdentifier("textures/fire_on.png");
    public static final int FIRE_WIDTH = 14;
    public static final int FIRE_HEIGHT = 14;
    public static final Identifier ARROW_PATH = new ModIdentifier("textures/animated_arrow_full.png");
    public static final int ARROW_WIDTH = 24;
    public static final int ARROW_HEIGHT = 17;
    public static final Identifier OXYGEN_CONTENT_PATH = new ModIdentifier("textures/oxygen.png");
    public static final Identifier OXYGEN_TANK_PATH = new ModIdentifier("textures/fluid_tank_fore.png");
    public static final int OXYGEN_TANK_WIDTH = 14;
    public static final int OXYGEN_TANK_HEIGHT = 48;
    public static final Identifier ENERGY_PATH = new ModIdentifier("textures/energy_full.png");
    public static final int ENERGY_WIDTH = 24;
    public static final int ENERGY_HEIGHT = 48;
    public static final int FUEL_WIDTH = 48;
    public static final int FUEL_HEIGHT = 48;
    public static final Identifier FLUID_TANK_PATH = new ModIdentifier("textures/fluid_tank_fore.png");
    public static final int FLUID_TANK_WIDTH = 14;
    public static final int FLUID_TANK_HEIGHT = 48;

    public static boolean isHover(Rectangle bounds, double x, double y) {
        double left = bounds.getX();
        double right = left + bounds.getWidth();
        double top = bounds.getY();
        double bottom = top + bounds.getHeight();
        return left <= x && x < right && top <= y && y < bottom;
    }

    public static Rectangle getEnergyBounds(int left, int top) {
        return new Rectangle(left, top, ENERGY_WIDTH, ENERGY_HEIGHT);
    }

    public static Rectangle getFireBounds(int left, int top) {
        return new Rectangle(left, top, FIRE_WIDTH, FIRE_HEIGHT);
    }

    public static void drawEnergy(MatrixStack matrixStack, int left, int top, long energy, long maxEnergy) {
        double ratio = maxEnergy > 0 ? createRatio(energy, maxEnergy) : 0;
        drawVertical(matrixStack, left, top, ENERGY_WIDTH, ENERGY_HEIGHT, ENERGY_PATH, ratio);
    }

    public static void drawFire(MatrixStack matrixStack, int left, int top, short burnTime, short totalBurnTime) {
        double ratio = totalBurnTime > 0 ? createRatio(burnTime, totalBurnTime) : 0;
        drawVertical(matrixStack, left, top, FIRE_WIDTH, FIRE_HEIGHT, FIRE_PATH, ratio);
    }

    public static void drawVertical(MatrixStack matrixStack, int left, int top, int width, int height, Identifier resource, double ratio) {
        int ratioHeight = (int) Math.ceil(height * ratio);
        int remainHeight = height - ratioHeight;
        RenderSystem.setShaderTexture(0, resource);
        DrawableHelper.drawTexture(matrixStack, left, top + remainHeight, 0, remainHeight, width, ratioHeight, width, height);
    }

    private static double createRatio(long a, long b) {
        return (double) a / (double) b;
    }

    private static double createRatio(short a, short b) {
        return (float) a / (float) b;
    }
}
