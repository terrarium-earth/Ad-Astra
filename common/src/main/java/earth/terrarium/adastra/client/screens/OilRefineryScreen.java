package earth.terrarium.adastra.client.screens;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.screens.base.MachineScreen;
import earth.terrarium.adastra.client.utils.GuiUtils;
import earth.terrarium.adastra.common.blockentities.machines.OilRefineryBlockEntity;
import earth.terrarium.adastra.common.menus.machines.OilRefineryMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class OilRefineryScreen extends MachineScreen<OilRefineryMenu, OilRefineryBlockEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/container/oil_refinery.png");
    public static final Rect2i CLICK_AREA = new Rect2i(55, 111, 26, 25);

    public OilRefineryScreen(OilRefineryMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component, TEXTURE, 184, 255);
    }

    @Override
    protected void init() {
        super.init();
        this.addRedstoneButton(131, 6);
        this.addSideConfigButton(110, 6);
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float f) {
        super.render(graphics, mouseX, mouseY, f);
        this.drawEnergyBar(graphics, mouseX, mouseY, 138, 103, entity.getEnergyStorage(), entity.energyDifference());
        this.drawFluidBar(graphics, mouseX, mouseY, 14, 105, entity.getFluidContainer(), 0, entity.fluidDifference(0));
        this.drawFluidBar(graphics, mouseX, mouseY, 94, 105, entity.getFluidContainer(), 1, entity.fluidDifference(1));
    }

    @Override
    public void renderSideConfig(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.fill(leftPos, topPos + 157, leftPos + 170, topPos + 245, getGuiColor());
        graphics.blit(TEXTURE, leftPos - 8, topPos + 157, 0, 33, 184, 9, this.imageWidth, this.imageHeight);
    }

    @Override
    public int getSideConfigButtonXOffset() {
        return 44;
    }

    @Override
    public int getSideConfigButtonYOffset() {
        return imageHeight - 50;
    }

    @Override
    public void highlightSideConfigElement(GuiGraphics graphics, int index) {
        switch (index) {
            case 0 -> graphics.renderOutline(leftPos + 9, topPos + 136, 20, 20, 0xFF00FF00);
            case 1 -> graphics.renderOutline(leftPos + 89, topPos + 136, 20, 20, 0xFF00FF00);
            case 2 -> {
                graphics.renderOutline(leftPos + 27, topPos + 136, 20, 20, 0xFF00FF00);
                graphics.renderOutline(leftPos + 107, topPos + 136, 20, 20, 0xFF00FF00);
            }
            case 3 ->
                graphics.renderOutline(leftPos + 143, topPos + 71, GuiUtils.ENERGY_BAR_WIDTH + 2, GuiUtils.ENERGY_BAR_HEIGHT + 2, 0xFF00FF00);
            case 4 ->
                graphics.renderOutline(leftPos + 19, topPos + 73, GuiUtils.FLUID_BAR_WIDTH + 2, GuiUtils.FLUID_BAR_HEIGHT + 2, 0xFF00FF00);
            case 5 ->
                graphics.renderOutline(leftPos + 99, topPos + 73, GuiUtils.FLUID_BAR_WIDTH + 2, GuiUtils.FLUID_BAR_HEIGHT + 2, 0xFF00FF00);
        }
    }
}
