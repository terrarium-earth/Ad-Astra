package earth.terrarium.adastra.client.screens.machines;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.api.planets.PlanetApi;
import earth.terrarium.adastra.client.screens.base.MachineScreen;
import earth.terrarium.adastra.client.utils.GuiUtils;
import earth.terrarium.adastra.common.blockentities.machines.SolarPanelBlockEntity;
import earth.terrarium.adastra.common.menus.machines.SolarPanelMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class SolarPanelScreen extends MachineScreen<SolarPanelMenu, SolarPanelBlockEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/container/solar_panel.png");

    public SolarPanelScreen(SolarPanelMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component, TEXTURE, 177, 228);
    }

    @Override
    protected void init() {
        super.init();
        this.addRedstoneButton(124, -26);
        this.addSideConfigButton(103, -26);
        this.titleLabelY = 45;
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        super.renderBg(graphics, partialTick, mouseX, mouseY);
        this.drawOptionsBar(graphics, 97, -32);
        this.drawEnergyBar(graphics, mouseX, mouseY, 94, 100, entity.getEnergyStorage(), entity.energyDifference());
        if (entity.isDay()) {
            graphics.blit(GuiUtils.SUN, leftPos + 27, topPos + 59, 0, 0, 21, 21, 21, 21);
        }
        boolean full = entity.getEnergyStorage().getStoredEnergy() >= entity.getEnergyStorage().getMaxCapacity();
        graphics.drawString(
            font,
            Component.translatable("tooltip.ad_astra.energy_per_tick", entity.isDay() && !full ? PlanetApi.API.getSolarPower(Minecraft.getInstance().level) : 0),
            leftPos + 19,
            topPos + 9,
            0x68d975
        );

        graphics.drawString(
            font,
            Component.translatable("tooltip.ad_astra.max_generation", PlanetApi.API.getSolarPower(Minecraft.getInstance().level)),
            leftPos + 19,
            topPos + 19,
            0x68d975
        );
    }

    @Override
    public void renderSideConfig(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.fill(leftPos - 1, topPos + 145, leftPos + 161, topPos + 221, getGuiColor());
    }

    @Override
    public int getSideConfigButtonXOffset() {
        return 43;
    }

    @Override
    public int getSideConfigButtonYOffset() {
        return imageHeight - 50;
    }

    @Override
    public void highlightSideConfigElement(GuiGraphics graphics, int index) {
        graphics.renderOutline(leftPos + 99, topPos + 68, GuiUtils.ENERGY_BAR_WIDTH + 2, GuiUtils.ENERGY_BAR_HEIGHT + 2, 0xFF00FF00);
    }
}
