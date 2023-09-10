package earth.terrarium.adastra.client.screens;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.screens.base.MachineScreen;
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
        super.renderSideConfig(graphics, mouseX, mouseY);
    }

    @Override
    public int getSideConfigButtonXOffset() {
        return 75;
    }

    @Override
    public int getSideConfigButtonYOffset() {
        return imageHeight - 50;
    }
}
