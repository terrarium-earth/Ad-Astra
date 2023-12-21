package earth.terrarium.adastra.client.screens.machines;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.screens.base.MachineScreen;
import earth.terrarium.adastra.client.utils.GuiUtils;
import earth.terrarium.adastra.common.blockentities.machines.FuelRefineryBlockEntity;
import earth.terrarium.adastra.common.menus.machines.FuelRefineryMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class FuelRefineryScreen extends MachineScreen<FuelRefineryMenu, FuelRefineryBlockEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/container/fuel_refinery.png");
    public static final Rect2i CLICK_AREA = new Rect2i(56, 34, 26, 25);

    public FuelRefineryScreen(FuelRefineryMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component, TEXTURE, 177, 184);
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
        this.drawEnergyBar(graphics, mouseX, mouseY, 136, 53, entity.getEnergyStorage(), entity.energyDifference());
        this.drawFluidBar(graphics, mouseX, mouseY, 29, 53, entity.getFluidContainer(), 0, entity.fluidDifference(0));
        this.drawFluidBar(graphics, mouseX, mouseY, 86, 53, entity.getFluidContainer(), 1, entity.fluidDifference(1));
    }

    @Override
    public void renderSideConfig(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.fill(leftPos - 1, topPos + 100, leftPos + 161, topPos + 178, getGuiColor());
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
            case 0 -> graphics.renderOutline(leftPos + 2, topPos + 20, 20, 20, 0xFF00FF00);
            case 1 -> graphics.renderOutline(leftPos + 117, topPos + 20, 20, 20, 0xFF00FF00);
            case 2 -> {
                graphics.renderOutline(leftPos + 2, topPos + 50, 20, 20, 0xFF00FF00);
                graphics.renderOutline(leftPos + 117, topPos + 50, 20, 20, 0xFF00FF00);
            }
            case 3 ->
                graphics.renderOutline(leftPos + 141, topPos + 21, GuiUtils.ENERGY_BAR_WIDTH + 2, GuiUtils.ENERGY_BAR_HEIGHT + 2, 0xFF00FF00);
            case 4 ->
                graphics.renderOutline(leftPos + 34, topPos + 21, GuiUtils.FLUID_BAR_WIDTH + 2, GuiUtils.FLUID_BAR_HEIGHT + 2, 0xFF00FF00);
            case 5 ->
                graphics.renderOutline(leftPos + 91, topPos + 21, GuiUtils.FLUID_BAR_WIDTH + 2, GuiUtils.FLUID_BAR_HEIGHT + 2, 0xFF00FF00);
        }
    }
}
