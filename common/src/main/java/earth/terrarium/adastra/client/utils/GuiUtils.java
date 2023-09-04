package earth.terrarium.adastra.client.utils;

import com.teamresourceful.resourcefullib.client.utils.RenderUtils;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.utils.ComponentUtils;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GuiUtils {
    public static final ResourceLocation ENERGY_BAR = new ResourceLocation(AdAstra.MOD_ID, "textures/icons/energy_bar.png");
    public static final ResourceLocation SIDE_SETTINGS_ICON = new ResourceLocation(AdAstra.MOD_ID, "textures/icons/side_settings_icon.png");
    public static final ResourceLocation SQUARE_BUTTON = new ResourceLocation(AdAstra.MOD_ID, "textures/icons/square_button.png");

    public static void drawEnergyBar(GuiGraphics graphics, int mouseX, int mouseY, Font font, int x, int y, WrappedBlockEnergyContainer energyContainer, Component... tooltips) {
        drawEnergyBar(graphics, mouseX, mouseY, font, x, y, energyContainer.getStoredEnergy(), energyContainer.getMaxCapacity(), tooltips);
    }

    public static void drawEnergyBar(GuiGraphics graphics, int mouseX, int mouseY, Font font, int x, int y, long energy, long capacity, Component... tooltips) {
        float ratio = energy / (float) capacity;
        try (var ignored = RenderUtils.createScissorBox(Minecraft.getInstance(), graphics.pose(), x + 6, y - 31 + 55 - (int) (55 * ratio), 10, 55)) {
            graphics.blit(ENERGY_BAR, x + 6, y - 31, 0, 0, 10, 55, 10, 55);
        }
        if (mouseX >= x + 6 && mouseX <= x + 16 && mouseY >= y - 31 && mouseY <= y + 24) {
            List<Component> tooltipList = new ArrayList<>();
            tooltipList.add(ComponentUtils.getEnergyComponent(energy, capacity));
            Collections.addAll(tooltipList, tooltips);
            graphics.renderComponentTooltip(font, tooltipList, mouseX, mouseY);
        }
    }
}
