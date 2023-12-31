package earth.terrarium.adastra.client.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import com.teamresourceful.resourcefullib.client.utils.RenderUtils;
import com.teamresourceful.resourcefullib.client.utils.ScreenUtils;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.utils.TooltipUtils;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.utils.ClientFluidHooks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class GuiUtils {
    public static final ResourceLocation ENERGY_BAR = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/sprites/energy_bar.png");
    public static final int ENERGY_BAR_WIDTH = 13;
    public static final int ENERGY_BAR_HEIGHT = 46;

    public static final ResourceLocation FLUID_BAR = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/sprites/fluid_bar.png");
    public static final int FLUID_BAR_WIDTH = 12;
    public static final int FLUID_BAR_HEIGHT = 46;

    public static final ResourceLocation BUTTON = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/sprites/button.png");
    public static final ResourceLocation SQUARE_BUTTON = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/sprites/square_button.png");
    public static final ResourceLocation SETTINGS_BUTTON = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/sprites/settings_button.png");

    public static final ResourceLocation NONE_BUTTON = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/sprites/side_config/none.png");
    public static final ResourceLocation PUSH_BUTTON = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/sprites/side_config/push.png");
    public static final ResourceLocation PULL_BUTTON = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/sprites/side_config/pull.png");
    public static final ResourceLocation PUSH_PULL_BUTTON = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/sprites/side_config/push_pull.png");

    public static final ResourceLocation REDSTONE_ALWAYS_ON = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/sprites/redstone/always_on_button.png");
    public static final ResourceLocation REDSTONE_ON_WHEN_POWERED = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/sprites/redstone/on_when_powered_button.png");
    public static final ResourceLocation REDSTONE_ON_WHEN_NOT_POWERED = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/sprites/redstone/on_when_not_powered_button.png");
    public static final ResourceLocation REDSTONE_NEVER_ON = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/sprites/redstone/never_on_button.png");

    public static final ResourceLocation HAMMER = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/sprites/hammer.png");
    public static final ResourceLocation SNOWFLAKE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/sprites/snowflake.png");
    public static final ResourceLocation SUN = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/sprites/sun.png");
    public static final ResourceLocation FIRE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/sprites/fire.png");

    public static void drawEnergyBar(GuiGraphics graphics, int mouseX, int mouseY, int x, int y, long energy, long capacity, Component... tooltips) {
        float ratio = energy / (float) capacity;
        try (var ignored = RenderUtils.createScissorBox(Minecraft.getInstance(), graphics.pose(), x + 6, y - 31 + ENERGY_BAR_HEIGHT - (int) (ENERGY_BAR_HEIGHT * ratio), ENERGY_BAR_WIDTH, ENERGY_BAR_HEIGHT)) {
            graphics.blit(ENERGY_BAR, x + 6, y - 31, 0, 0, ENERGY_BAR_WIDTH, ENERGY_BAR_HEIGHT, ENERGY_BAR_WIDTH, ENERGY_BAR_HEIGHT);
        }

        drawTooltips(mouseX, mouseY, x + 6, x + 19, y - 31, y + 15, list -> {
            list.add(TooltipUtils.getEnergyComponent(energy, capacity));
            Collections.addAll(list, tooltips);
            return list;
        });
    }

    public static void drawFluidBar(GuiGraphics graphics, int mouseX, int mouseY, int x, int y, FluidHolder fluid, long capacity, Component... tooltips) {
        if (!fluid.isEmpty()) {
            float ratio = fluid.getFluidAmount() / (float) capacity;
            TextureAtlasSprite sprite = ClientFluidHooks.getFluidSprite(fluid);
            int color = ClientFluidHooks.getFluidColor(fluid);
            float r = FastColor.ARGB32.red(color) / 255f;
            float g = FastColor.ARGB32.green(color) / 255f;
            float b = FastColor.ARGB32.blue(color) / 255f;

            try (var ignored = RenderUtils.createScissorBox(Minecraft.getInstance(), graphics.pose(), x + 6, y - 31 + FLUID_BAR_HEIGHT - (int) (FLUID_BAR_HEIGHT * ratio), FLUID_BAR_WIDTH, FLUID_BAR_HEIGHT)) {
                for (int j = 1; j < 5; j++) {
                    graphics.blit(x + 7, y - 34 + FLUID_BAR_HEIGHT - j * 12, 0, 14, 14, sprite, r, g, b, 1);
                }
            }
        }

        RenderSystem.enableBlend();
        graphics.blit(FLUID_BAR, x + 6, y - 31, 0, 0, FLUID_BAR_WIDTH, FLUID_BAR_HEIGHT, FLUID_BAR_WIDTH, FLUID_BAR_HEIGHT);
        RenderSystem.disableBlend();

        drawTooltips(mouseX, mouseY, x + 6, x + 18, y - 31, y + 15, list -> {
            list.add(TooltipUtils.getFluidComponent(fluid, capacity));
            Collections.addAll(list, tooltips);
            return list;
        });
    }

    public static void drawHorizontalProgressBar(GuiGraphics graphics, ResourceLocation texture, int mouseX, int mouseY, int x, int y, int width, int height, int progress, int maxProgress, boolean reverse, Component... tooltips) {
        int widthProgress = (int) (width * (progress / (float) maxProgress));
        if (reverse) widthProgress = width - widthProgress;
        graphics.blit(texture, x, y, 0, 0, widthProgress, height, width, height);

        drawTooltips(mouseX, mouseY, x, x + width, y, y + height, list -> {
            Collections.addAll(list, tooltips);
            return list;
        });
    }

    public static void drawVerticalProgressBar(GuiGraphics graphics, ResourceLocation texture, int mouseX, int mouseY, int x, int y, int width, int height, int progress, int maxProgress, Component... tooltips) {
        int heightProgress = (int) (height * (progress / (float) maxProgress));
        heightProgress = height - heightProgress;
        graphics.blit(texture, x, y + heightProgress, 0, heightProgress, width, height - heightProgress, width, height);

        drawTooltips(mouseX, mouseY, x, x + width, y, y + height, list -> {
            Collections.addAll(list, tooltips);
            return list;
        });
    }

    public static void drawTooltips(int mouseX, int mouseY, int minX, int maxX, int minY, int maxY, Function<List<Component>, List<Component>> tooltips) {
        if (mouseX >= minX && mouseX <= maxX && mouseY >= minY && mouseY <= maxY) {
            List<Component> lines = tooltips.apply(new ArrayList<>());
            lines.removeIf(c -> c.getString().isEmpty());
            ScreenUtils.setTooltip(lines);
        }
    }
}
