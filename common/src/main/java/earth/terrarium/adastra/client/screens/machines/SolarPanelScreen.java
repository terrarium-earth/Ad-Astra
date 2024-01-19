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
        super(menu, inventory, component, TEXTURE, STEEL_SLOT, 177, 230);
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelY = 45;
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        super.renderBg(graphics, partialTick, mouseX, mouseY);
        if (entity.isDay()) {
            graphics.blitSprite(GuiUtils.SUN, leftPos + 35, topPos + 59, 21, 21);
        }
        boolean full = entity.getEnergyStorage().getStoredEnergy() >= entity.getEnergyStorage().getMaxCapacity();
        graphics.drawString(
            font,
            Component.translatable("tooltip.ad_astra.energy_per_tick", entity.isDay() && !full ? PlanetApi.API.getSolarPower(Minecraft.getInstance().level) : 0),
            leftPos + 27,
            topPos + 9,
            0x68d975
        );

        graphics.drawString(
            font,
            Component.translatable("tooltip.ad_astra.max_generation", PlanetApi.API.getSolarPower(Minecraft.getInstance().level)),
            leftPos + 27,
            topPos + 19,
            0x68d975
        );
    }
}
