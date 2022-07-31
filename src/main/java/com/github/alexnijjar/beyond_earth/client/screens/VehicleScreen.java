package com.github.alexnijjar.beyond_earth.client.screens;

import java.awt.Rectangle;

import com.github.alexnijjar.beyond_earth.screen.handler.VehicleScreenHandler;
import com.github.alexnijjar.beyond_earth.util.ModIdentifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class VehicleScreen extends AbstractVehicleScreen<VehicleScreenHandler> {

    private static final Identifier TEXTURE = new ModIdentifier("textures/gui/screens/vehicle_small.png");

    public static final int INPUT_TANK_LEFT = 50;
    public static final int INPUT_TANK_TOP = 23;

    public VehicleScreen(VehicleScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, TEXTURE);
        this.backgroundWidth = 177;
        this.backgroundHeight = 174;
        this.playerInventoryTitleY = this.backgroundHeight - 93;
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {

        super.drawBackground(matrices, delta, mouseX, mouseY);

        GuiUtil.drawFluidTank(matrices, this.x + INPUT_TANK_LEFT, this.y + INPUT_TANK_TOP, this.vehicle.getFluidAmount(), this.vehicle.inputTank.getCapacity(), this.vehicle.getFluidVariant());
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);

        if (GuiUtil.isHovering(this.getInputTankBounds(), mouseX, mouseY)) {
            GuiUtil.drawTankTooltip(this, matrices, this.vehicle.getFluidAmount(), this.vehicle.inputTank.getCapacity(), this.vehicle.getFluidVariant(), mouseX, mouseY);
        }
    }

    public Rectangle getInputTankBounds() {
        return GuiUtil.getFluidTankBounds(this.x + INPUT_TANK_LEFT, this.y + INPUT_TANK_TOP);
    }

    @Override
    public int getTextColour() {
        return 0x2C282E;
    }
}