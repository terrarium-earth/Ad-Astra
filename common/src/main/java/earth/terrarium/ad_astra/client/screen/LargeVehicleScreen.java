package earth.terrarium.ad_astra.client.screen;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.screen.menu.LargeVehicleMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.awt.*;

public class LargeVehicleScreen extends AbstractVehicleScreen<LargeVehicleMenu> {

    public static final int INPUT_TANK_LEFT = 50;
    public static final int INPUT_TANK_TOP = 25;
    private static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/screens/vehicle_large.png");

    public LargeVehicleScreen(LargeVehicleMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title, TEXTURE);
        this.imageWidth = 177;
        this.imageHeight = 181;
        this.inventoryLabelY = this.imageHeight - 93;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float delta, int mouseX, int mouseY) {

        super.renderBg(graphics, delta, mouseX, mouseY);

        GuiUtil.drawFluidTank(graphics, this.leftPos + INPUT_TANK_LEFT, this.topPos + INPUT_TANK_TOP, this.vehicle.getTankSize(), this.menu.getFluids().get(0));
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        super.render(graphics, mouseX, mouseY, delta);

        if (GuiUtil.isHovering(this.getInputTankBounds(), mouseX, mouseY)) {
            GuiUtil.drawTankTooltip(graphics, this.menu.getFluids().get(0), this.vehicle.getTankSize(), mouseX, mouseY);
        }
    }

    public Rectangle getInputTankBounds() {
        return GuiUtil.getFluidTankBounds(this.leftPos + INPUT_TANK_LEFT, this.topPos + INPUT_TANK_TOP);
    }

    @Override
    public int getTextColour() {
        return 0x2C282E;
    }
}