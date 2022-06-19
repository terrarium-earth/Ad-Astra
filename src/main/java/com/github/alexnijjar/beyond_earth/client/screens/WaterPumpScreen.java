package com.github.alexnijjar.beyond_earth.client.screens;

import java.awt.Rectangle;

import com.github.alexnijjar.beyond_earth.blocks.machines.entity.FluidMachineBlockEntity;
import com.github.alexnijjar.beyond_earth.gui.screen_handlers.WaterPumpScreenHandler;
import com.github.alexnijjar.beyond_earth.util.FluidUtils;
import com.github.alexnijjar.beyond_earth.util.ModIdentifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class WaterPumpScreen extends AbstractMachineScreen<WaterPumpScreenHandler> {

    private static final Identifier TEXTURE = new ModIdentifier("textures/screens/water_pump.png");

    public static final int INPUT_TANK_LEFT = 75;
    public static final int INPUT_TANK_TOP = 21;

    public static final int ENERGY_LEFT = 144;
    public static final int ENERGY_TOP = 21;

    public WaterPumpScreen(WaterPumpScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, TEXTURE);
        this.backgroundWidth = 177;
        this.backgroundHeight = 172;
        this.playerInventoryTitleY = this.backgroundHeight - 92;
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {

        super.drawBackground(matrices, delta, mouseX, mouseY);

        FluidMachineBlockEntity entity = (FluidMachineBlockEntity) blockEntity;

        GuiUtil.drawEnergy(matrices, this.x + ENERGY_LEFT, this.y + ENERGY_TOP, this.blockEntity.getEnergy(), this.blockEntity.getMaxGeneration());
        GuiUtil.drawFluidTank(matrices, this.x + INPUT_TANK_LEFT, this.y + INPUT_TANK_TOP, entity.inputTank.getAmount(), entity.inputTank.getCapacity(), entity.inputTank.getResource(), 37);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);

        FluidMachineBlockEntity entity = (FluidMachineBlockEntity) blockEntity;

        // Energy tooltip.
        if (GuiUtil.isHovering(this.getEnergyBounds(), mouseX, mouseY)) {
            this.renderTooltip(matrices, Text.translatable("gauge_text.beyond_earth.storage", this.blockEntity.getEnergy(), this.blockEntity.getMaxGeneration()), mouseX, mouseY);
        }

        // Tank tooltip.
        if (GuiUtil.isHovering(this.getInputTankBounds(), mouseX, mouseY)) {
            this.renderTooltip(matrices, Text.translatable("gauge_text.beyond_earth.liquid_storage", FluidUtils.dropletsToMillibuckets(entity.inputTank.getAmount()), FluidUtils.dropletsToMillibuckets(entity.inputTank.getCapacity())), mouseX, mouseY);
        }
    }

    public Rectangle getInputTankBounds() {
        return GuiUtil.getFluidTankBounds(this.x + INPUT_TANK_LEFT, this.y + INPUT_TANK_TOP);
    }

    public Rectangle getEnergyBounds() {
        return GuiUtil.getEnergyBounds(this.x + ENERGY_LEFT, this.y + ENERGY_TOP);
    }
}