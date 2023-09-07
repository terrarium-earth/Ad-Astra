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
    private static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/container/battery.png");

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
        this.drawEnergyBar(graphics, mouseX, mouseY, 156, 95, menu.getEntity().getEnergyStorage(), menu.getEntity().energyDifference());
    }
}
