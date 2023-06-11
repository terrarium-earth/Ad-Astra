package earth.terrarium.ad_astra.client.screen;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.block.machine.entity.CryoFreezerBlockEntity;
import earth.terrarium.ad_astra.common.screen.menu.CryoFreezerMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.awt.*;

public class CryoFreezerScreen extends AbstractMachineScreen<CryoFreezerBlockEntity, CryoFreezerMenu> {

    public static final int SNOWFLAKE_LEFT = 54;
    public static final int SNOWFLAKE_TOP = 71;
    public static final int INPUT_TANK_LEFT = 85;
    public static final int INPUT_TANK_TOP = 37;
    public static final int ENERGY_LEFT = 149;
    public static final int ENERGY_TOP = 27;
    private static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/screens/cryo_freezer.png");

    public CryoFreezerScreen(CryoFreezerMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title, TEXTURE);
        this.imageWidth = 177;
        this.imageHeight = 181;
        this.inventoryLabelY = this.imageHeight - 93;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float delta, int mouseX, int mouseY) {

        super.renderBg(graphics, delta, mouseX, mouseY);

        GuiUtil.drawEnergy(graphics, this.leftPos + ENERGY_LEFT, this.topPos + ENERGY_TOP, this.menu.getEnergyAmount(), this.machine.getMaxCapacity());
        GuiUtil.drawFluidTank(graphics, this.leftPos + INPUT_TANK_LEFT, this.topPos + INPUT_TANK_TOP, this.machine.getFluidContainer(machine).getTankCapacity(0), this.menu.getFluids().get(0));
        GuiUtil.drawSnowflake(graphics, this.leftPos + SNOWFLAKE_LEFT, this.topPos + SNOWFLAKE_TOP, this.machine.getCookTime(), this.machine.getCookTimeTotal());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        super.render(graphics, mouseX, mouseY, delta);

        if (GuiUtil.isHovering(this.getEnergyBounds(), mouseX, mouseY)) {
            GuiUtil.drawEnergyTooltip(graphics, this.menu.getEnergyAmount(), this.machine.getMaxCapacity(), mouseX, mouseY);
        }

        if (GuiUtil.isHovering(this.getOutputTankBounds(), mouseX, mouseY)) {
            GuiUtil.drawTankTooltip(graphics, this.menu.getFluids().get(0), this.machine.getFluidContainer(machine).getTankCapacity(0), mouseX, mouseY);
        }
    }

    public Rectangle getSnowFlakeBounds() {
        return new Rectangle(this.leftPos + SNOWFLAKE_LEFT, this.topPos + SNOWFLAKE_TOP, GuiUtil.SNOWFLAKE_WIDTH, GuiUtil.SNOWFLAKE_HEIGHT);
    }

    public Rectangle getOutputTankBounds() {
        return GuiUtil.getFluidTankBounds(this.leftPos + INPUT_TANK_LEFT, this.topPos + INPUT_TANK_TOP);
    }

    public Rectangle getEnergyBounds() {
        return GuiUtil.getEnergyBounds(this.leftPos + ENERGY_LEFT, this.topPos + ENERGY_TOP);
    }

    @Override
    public int getTextColour() {
        return 0xccffff;
    }
}