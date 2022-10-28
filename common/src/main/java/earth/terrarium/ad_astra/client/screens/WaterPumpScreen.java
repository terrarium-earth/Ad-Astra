package earth.terrarium.ad_astra.client.screens;

import earth.terrarium.ad_astra.blocks.machines.entity.FluidMachineBlockEntity;
import earth.terrarium.ad_astra.blocks.machines.entity.WaterPumpBlockEntity;
import earth.terrarium.ad_astra.screen.handler.WaterPumpScreenHandler;
import earth.terrarium.ad_astra.util.ModIdentifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.*;

@Environment(EnvType.CLIENT)
public class WaterPumpScreen extends AbstractMachineScreen<WaterPumpScreenHandler> {

    public static final int INPUT_TANK_LEFT = 80;
    public static final int INPUT_TANK_TOP = 30;
    public static final int ENERGY_LEFT = 146;
    public static final int ENERGY_TOP = 30;
    private static final Identifier TEXTURE = new ModIdentifier("textures/gui/screens/water_pump.png");

    public WaterPumpScreen(WaterPumpScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, TEXTURE);
        this.backgroundWidth = 177;
        this.backgroundHeight = 180;
        this.playerInventoryTitleY = this.backgroundHeight - 92;
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        super.drawBackground(matrices, delta, mouseX, mouseY);

        WaterPumpBlockEntity entity = (WaterPumpBlockEntity) this.entity;

        GuiUtil.drawEnergy(matrices, this.x + ENERGY_LEFT, this.y + ENERGY_TOP, this.handler.getEnergyAmount(), this.entity.getCapacity());
        GuiUtil.drawFluidTank(matrices, this.x + INPUT_TANK_LEFT, this.y + INPUT_TANK_TOP, entity.getFluidContainer().getTankCapacity(0), this.handler.getFluids().get(0));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);

        FluidMachineBlockEntity entity = (FluidMachineBlockEntity) this.entity;

        if (GuiUtil.isHovering(this.getEnergyBounds(), mouseX, mouseY)) {
            GuiUtil.drawEnergyTooltip(this, matrices, this.handler.getEnergyAmount(), entity.getCapacity(), mouseX, mouseY);
        }

        if (GuiUtil.isHovering(this.getInputTankBounds(), mouseX, mouseY)) {
            GuiUtil.drawTankTooltip(this, matrices, this.handler.getFluids().get(0), entity.getInputTankCapacity(), mouseX, mouseY);
        }
    }

    public Rectangle getInputTankBounds() {
        return GuiUtil.getFluidTankBounds(this.x + INPUT_TANK_LEFT, this.y + INPUT_TANK_TOP);
    }

    public Rectangle getEnergyBounds() {
        return GuiUtil.getEnergyBounds(this.x + ENERGY_LEFT, this.y + ENERGY_TOP);
    }

    @Override
    public int getTextColour() {
        return 0x2C282E;
    }
}