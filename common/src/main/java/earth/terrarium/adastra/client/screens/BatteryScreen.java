package earth.terrarium.adastra.client.screens;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.screens.base.MachineScreen;
import earth.terrarium.adastra.client.utils.GuiUtils;
import earth.terrarium.adastra.common.blockentities.BatteryBlockEntity;
import earth.terrarium.adastra.common.menus.BatteryMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class BatteryScreen extends MachineScreen<BatteryMenu, BatteryBlockEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/container/battery.png");

    public BatteryScreen(BatteryMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component, TEXTURE, 215, 231);
    }

    @Override
    protected void init() {
        super.init();
        this.addRedstoneButton(162, 6);
        this.addSideConfigButton(141, 6);
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float f) {
        super.render(graphics, mouseX, mouseY, f);
        this.drawGear(graphics, 141, 6);
        this.drawEnergyBar(graphics, mouseX, mouseY, 156, 95, entity.getEnergyStorage(), entity.energyDifference());
    }

    @Override
    public void renderSideConfig(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.fill(leftPos + 30, topPos + 133, leftPos + 200, topPos + 222, getGuiColor());
        graphics.blit(TEXTURE, leftPos - 8, topPos + 133, 0, 33, 215, 9, this.imageWidth, this.imageHeight);
    }

    @Override
    public int getSideConfigButtonXOffset() {
        return 75;
    }

    @Override
    public int getSideConfigButtonYOffset() {
        return imageHeight - 50;
    }

    @Override
    public void highlightSideConfigElement(GuiGraphics graphics, int index) {
        switch (index) {
            case 0 -> {
                graphics.renderOutline(leftPos + 62, topPos + 64, 24, 24, 0xFF00FF00);
                graphics.renderOutline(leftPos + 92, topPos + 64, 24, 24, 0xFF00FF00);
                graphics.renderOutline(leftPos + 62, topPos + 94, 24, 24, 0xFF00FF00);
                graphics.renderOutline(leftPos + 92, topPos + 94, 24, 24, 0xFF00FF00);
            }
            case 1 ->
                graphics.renderOutline(leftPos + 161, topPos + 63, GuiUtils.ENERGY_BAR_WIDTH + 2, GuiUtils.ENERGY_BAR_HEIGHT + 2, 0xFF00FF00);
        }
    }
}
