package earth.terrarium.adastra.client.screens.machines;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.screens.base.MachineScreen;
import earth.terrarium.adastra.client.utils.GuiUtils;
import earth.terrarium.adastra.common.blockentities.machines.WaterPumpBlockEntity;
import earth.terrarium.adastra.common.menus.machines.WaterPumpMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class WaterPumpScreen extends MachineScreen<WaterPumpMenu, WaterPumpBlockEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/container/water_pump.png");

    public WaterPumpScreen(WaterPumpMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component, TEXTURE, 177, 180);
    }

    @Override
    protected void init() {
        super.init();
        this.addRedstoneButton(124, -26);
        this.addSideConfigButton(103, -26);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        super.renderBg(graphics, partialTick, mouseX, mouseY);
        this.drawOptionsBar(graphics, 97, -32);
        this.drawEnergyBar(graphics, mouseX, mouseY, 132, 61, entity.getEnergyStorage(), entity.energyDifference());
        this.drawFluidBar(graphics, mouseX, mouseY, 67, 62, entity.getFluidContainer(), 0, entity.fluidDifference());
    }

    @Override
    public void renderSideConfig(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.fill(leftPos - 1, topPos + 95, leftPos + 161, topPos + 173, getGuiColor());
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
        switch (index) {
            case 0 ->
                graphics.renderOutline(leftPos + 137, topPos + 29, GuiUtils.ENERGY_BAR_WIDTH + 2, GuiUtils.ENERGY_BAR_HEIGHT + 2, 0xFF00FF00);
            case 1 ->
                graphics.renderOutline(leftPos + 72, topPos + 30, GuiUtils.FLUID_BAR_WIDTH + 2, GuiUtils.FLUID_BAR_HEIGHT + 2, 0xFF00FF00);
        }
    }
}
