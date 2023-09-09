package earth.terrarium.adastra.client.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import com.teamresourceful.resourcefullib.client.utils.RenderUtils;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.utils.ComponentUtils;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.impl.WrappedBlockFluidContainer;
import earth.terrarium.botarium.common.fluid.utils.ClientFluidHooks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class GuiUtils {
    public static final ResourceLocation ENERGY_BAR = new ResourceLocation(AdAstra.MOD_ID, "textures/icons/energy_bar.png");
    public static final int ENERGY_BAR_WIDTH = 10;
    public static final int ENERGY_BAR_HEIGHT = 55;

    public static final ResourceLocation FLUID_BAR = new ResourceLocation(AdAstra.MOD_ID, "textures/icons/fluid_bar.png");
    public static final int FLUID_BAR_WIDTH = 16;
    public static final int FLUID_BAR_HEIGHT = 48;

    public static final ResourceLocation SIDE_SETTINGS_ICON = new ResourceLocation(AdAstra.MOD_ID, "textures/icons/side_settings_icon.png");
    public static final ResourceLocation SQUARE_BUTTON = new ResourceLocation(AdAstra.MOD_ID, "textures/icons/square_button.png");

    public static void drawEnergyBar(GuiGraphics graphics, int mouseX, int mouseY, Font font, int x, int y, WrappedBlockEnergyContainer energyContainer, Component... tooltips) {
        drawEnergyBar(graphics, mouseX, mouseY, font, x, y, energyContainer.getStoredEnergy(), energyContainer.getMaxCapacity(), tooltips);
    }

    public static void drawEnergyBar(GuiGraphics graphics, int mouseX, int mouseY, Font font, int x, int y, long energy, long capacity, Component... tooltips) {
        float ratio = energy / (float) capacity;
        try (var ignored = RenderUtils.createScissorBox(Minecraft.getInstance(), graphics.pose(), x + 6, y - 31 + ENERGY_BAR_HEIGHT - (int) (ENERGY_BAR_HEIGHT * ratio), ENERGY_BAR_WIDTH, ENERGY_BAR_HEIGHT)) {
            graphics.blit(ENERGY_BAR, x + 6, y - 31, 0, 0, ENERGY_BAR_WIDTH, ENERGY_BAR_HEIGHT, ENERGY_BAR_WIDTH, ENERGY_BAR_HEIGHT);
        }

        drawTooltips(graphics, font, mouseX, mouseY, x + 6, x + 16, y - 31, y + 24, list -> {
            list.add(ComponentUtils.getEnergyComponent(energy, capacity));
            Collections.addAll(list, tooltips);
            return list;
        });
    }

    public static void drawFluidBar(GuiGraphics graphics, int mouseX, int mouseY, Font font, int x, int y, WrappedBlockFluidContainer fluidContainer, int tank, Component... tooltips) {
        drawFluidBar(graphics, mouseX, mouseY, font, x, y, fluidContainer.getFluids().get(tank), fluidContainer.getTankCapacity(tank), tooltips);
    }

    public static void drawFluidBar(GuiGraphics graphics, int mouseX, int mouseY, Font font, int x, int y, FluidHolder fluid, long capacity, Component... tooltips) {
        if (!fluid.isEmpty()) {
            float ratio = fluid.getFluidAmount() / (float) capacity;
            TextureAtlasSprite sprite = ClientFluidHooks.getFluidSprite(fluid);
            int color = ClientFluidHooks.getFluidColor(fluid);
            float r = ((color >> 16) & 0xFF) / 255.0f;
            float g = ((color >> 8) & 0xFF) / 255.0f;
            float b = (color & 0xFF) / 255.0f;

            try (var ignored = RenderUtils.createScissorBox(Minecraft.getInstance(), graphics.pose(), x + 6, y - 31 + FLUID_BAR_HEIGHT - (int) (FLUID_BAR_HEIGHT * ratio), FLUID_BAR_WIDTH, FLUID_BAR_HEIGHT)) {
                for (int j = 1; j < 5; j++) {
                    graphics.blit(x + 7, y - 34 + FLUID_BAR_HEIGHT - j * 12, 0, 14, 14, sprite, r, g, b, 1);
                }
            }
        }

        RenderSystem.enableBlend();
        graphics.blit(FLUID_BAR, x + 6, y - 31, 0, 0, FLUID_BAR_WIDTH, FLUID_BAR_HEIGHT, FLUID_BAR_WIDTH, FLUID_BAR_HEIGHT);
        RenderSystem.disableBlend();

        drawTooltips(graphics, font, mouseX, mouseY, x + 6, x + 22, y - 31, y + 17, list -> {
            list.add(ComponentUtils.getFluidComponent(fluid, capacity));
            Collections.addAll(list, tooltips);
            return list;
        });
    }

    public static void drawTooltips(GuiGraphics graphics, Font font, int mouseX, int mouseY, int minX, int maxX, int minY, int maxY, Function<List<Component>, List<Component>> tooltips) {
        if (mouseX >= minX && mouseX <= maxX && mouseY >= minY && mouseY <= maxY) {
            List<Component> lines = tooltips.apply(new ArrayList<>());
            lines.removeIf(c -> c.getString().isEmpty());
            graphics.renderComponentTooltip(font, lines, mouseX, mouseY);
            graphics.flush();
        }
    }
}
