package earth.terrarium.ad_astra.client.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.ad_astra.screen.menu.VehicleMenu;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.awt.*;

@Environment(EnvType.CLIENT)
public class VehicleScreen extends AbstractVehicleScreen<VehicleMenu> {

    public static final int INPUT_TANK_LEFT = 50;
    public static final int INPUT_TANK_TOP = 23;
    private static final ResourceLocation TEXTURE = new ModResourceLocation("textures/gui/screens/vehicle_small.png");

    public VehicleScreen(VehicleMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title, TEXTURE);
        this.imageWidth = 177;
        this.imageHeight = 174;
        this.inventoryLabelY = this.imageHeight - 93;
    }

    @Override
    protected void renderBg(PoseStack poseStack, float delta, int mouseX, int mouseY) {

        super.renderBg(poseStack, delta, mouseX, mouseY);

        GuiUtil.drawFluidTank(poseStack, this.leftPos + INPUT_TANK_LEFT, this.topPos + INPUT_TANK_TOP, this.vehicle.getTankSize(), this.menu.getFluids().get(0));
    }

    public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
        super.render(poseStack, mouseX, mouseY, delta);

        if (GuiUtil.isHovering(this.getInputTankBounds(), mouseX, mouseY)) {
            GuiUtil.drawTankTooltip(this, poseStack, this.menu.getFluids().get(0), this.vehicle.getTankSize(), mouseX, mouseY);
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