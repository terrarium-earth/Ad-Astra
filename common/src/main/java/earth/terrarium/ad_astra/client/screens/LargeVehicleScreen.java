package earth.terrarium.ad_astra.client.screens;

import earth.terrarium.ad_astra.screen.handler.LargeVehicleScreenHandler;
import earth.terrarium.ad_astra.util.ModIdentifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.*;

@Environment(EnvType.CLIENT)
public class LargeVehicleScreen extends AbstractVehicleScreen<LargeVehicleScreenHandler> {

    public static final int INPUT_TANK_LEFT = 50;
    public static final int INPUT_TANK_TOP = 25;
    private static final Identifier TEXTURE = new ModIdentifier("textures/gui/screens/vehicle_large.png");

    public LargeVehicleScreen(LargeVehicleScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, TEXTURE);
        this.backgroundWidth = 177;
        this.backgroundHeight = 181;
        this.playerInventoryTitleY = this.backgroundHeight - 93;
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {

        super.drawBackground(matrices, delta, mouseX, mouseY);

        GuiUtil.drawFluidTank(matrices, this.x + INPUT_TANK_LEFT, this.y + INPUT_TANK_TOP, this.vehicle.getTankSize(), this.handler.getFluids().get(0));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);

        if (GuiUtil.isHovering(this.getInputTankBounds(), mouseX, mouseY)) {
            GuiUtil.drawTankTooltip(this, matrices, this.handler.getFluids().get(0).getFluidAmount(), this.vehicle.getTankSize(), this.handler.getFluids().get(0).getFluid(), mouseX, mouseY);
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