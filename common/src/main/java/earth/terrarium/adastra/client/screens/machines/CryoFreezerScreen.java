package earth.terrarium.adastra.client.screens.machines;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.screens.base.MachineScreen;
import earth.terrarium.adastra.client.utils.GuiUtils;
import earth.terrarium.adastra.common.blockentities.machines.CryoFreezerBlockEntity;
import earth.terrarium.adastra.common.menus.machines.CryoFreezerMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class CryoFreezerScreen extends MachineScreen<CryoFreezerMenu, CryoFreezerBlockEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/container/cryo_freezer.png");
    public static final Rect2i CLICK_AREA = new Rect2i(100, 10, 26, 25);

    public CryoFreezerScreen(CryoFreezerMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component, TEXTURE, 177, 181);
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
        this.drawEnergyBar(graphics, mouseX, mouseY, 135, 58, entity.getEnergyStorage(), entity.energyDifference());
        this.drawFluidBar(graphics, mouseX, mouseY, 72, 69, entity.getFluidContainer(), 0, entity.fluidDifference());
        this.drawHorizontalProgressBar(graphics, GuiUtils.SNOWFLAKE, mouseX, mouseY, 46, 71, 13, 13, entity.cookTime(), entity.cookTimeTotal(), false);
    }

    @Override
    public int getGuiColor() {
        return 0xff74525f;
    }

    @Override
    public void renderSideConfig(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.fill(leftPos - 1, topPos + 95, leftPos + 161, topPos + 175, getGuiColor());
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
            case 0 -> graphics.renderOutline(leftPos + 16, topPos + 68, 20, 20, 0xFF00FF00);
            case 1 -> graphics.renderOutline(leftPos + 103, topPos + 40, 20, 20, 0xFF00FF00);
            case 2 -> graphics.renderOutline(leftPos + 103, topPos + 68, 20, 20, 0xFF00FF00);
            case 3 ->
                graphics.renderOutline(leftPos + 140, topPos + 26, GuiUtils.ENERGY_BAR_WIDTH + 2, GuiUtils.ENERGY_BAR_HEIGHT + 2, 0xFF00FF00);
            case 4 ->
                graphics.renderOutline(leftPos + 77, topPos + 37, GuiUtils.FLUID_BAR_WIDTH + 2, GuiUtils.FLUID_BAR_HEIGHT + 2, 0xFF00FF00);
        }
    }
}
