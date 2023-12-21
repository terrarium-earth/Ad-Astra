package earth.terrarium.adastra.client.screens.machines;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.screens.base.MachineScreen;
import earth.terrarium.adastra.client.utils.GuiUtils;
import earth.terrarium.adastra.common.blockentities.machines.CompressorBlockEntity;
import earth.terrarium.adastra.common.menus.machines.CompressorMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class CompressorScreen extends MachineScreen<CompressorMenu, CompressorBlockEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/container/compressor.png");
    public static final Rect2i CLICK_AREA = new Rect2i(40, 25, 26, 25);

    public CompressorScreen(CompressorMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component, TEXTURE, 177, 196);
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
        this.drawEnergyBar(graphics, mouseX, mouseY, 133, 61, entity.getEnergyStorage(), entity.energyDifference());
        this.drawHorizontalProgressBar(graphics, GuiUtils.HAMMER, mouseX, mouseY, 70, 57, 13, 13, entity.cookTime(), entity.cookTimeTotal(), false);
    }

    @Override
    public int getGuiColor() {
        return 0xff94959d;
    }

    @Override
    public void renderSideConfig(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.fill(leftPos - 1, topPos + 100, leftPos + 161, topPos + 189, getGuiColor());
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
            case 0 -> graphics.renderOutline(leftPos + 43, topPos + 54, 20, 20, 0xFF00FF00);
            case 1 -> graphics.renderOutline(leftPos + 90, topPos + 54, 20, 20, 0xFF00FF00);
            case 2 ->
                graphics.renderOutline(leftPos + 138, topPos + 29, GuiUtils.ENERGY_BAR_WIDTH + 2, GuiUtils.ENERGY_BAR_HEIGHT + 2, 0xFF00FF00);
        }
    }
}
