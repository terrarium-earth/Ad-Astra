package earth.terrarium.ad_astra.client.screen;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.block.machine.AbstractMachineBlock;
import earth.terrarium.ad_astra.common.block.machine.entity.SolarPanelBlockEntity;
import earth.terrarium.ad_astra.common.screen.menu.SolarPanelMenu;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.awt.*;

@Environment(EnvType.CLIENT)
public class SolarPanelScreen extends AbstractMachineScreen<SolarPanelBlockEntity, SolarPanelMenu> {

    public static final int SUN_LEFT = 35;
    public static final int SUN_TOP = 59;
    public static final int ENERGY_LEFT = 108;
    public static final int ENERGY_TOP = 69;
    private static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/screens/solar_panel.png");

    public SolarPanelScreen(SolarPanelMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title, TEXTURE);
        this.imageWidth = 177;
        this.imageHeight = 228;
        this.titleLabelY = 46;
        this.inventoryLabelY = this.imageHeight - 93;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float delta, int mouseX, int mouseY) {
        super.renderBg(graphics, delta, mouseX, mouseY);

        GuiUtil.drawEnergy(graphics, this.leftPos + ENERGY_LEFT, this.topPos + ENERGY_TOP, this.menu.getEnergyAmount(), this.machine.getMaxCapacity());
        if (this.machine.getBlockState().getValue(AbstractMachineBlock.LIT)) {
            GuiUtil.drawSun(graphics, this.leftPos + SUN_LEFT, this.topPos + SUN_TOP);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        super.renderLabels(graphics, mouseX, mouseY);

        graphics.drawString(font, Component.translatable("gauge_text.ad_astra.max_generation"), this.titleLabelY - 20, 8, 0x68d975);
        graphics.drawString(font, Component.translatable("gauge_text.ad_astra.energy_per_tick", this.machine.getEnergyPerTick()), this.titleLabelY - 21, 18, 0x68d975);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        super.render(graphics, mouseX, mouseY, delta);

        if (GuiUtil.isHovering(this.getEnergyBounds(), mouseX, mouseY)) {
            GuiUtil.drawEnergyTooltip(graphics, this.menu.getEnergyAmount(), machine.getMaxCapacity(), mouseX, mouseY);
        }
    }

    public Rectangle getEnergyBounds() {
        return GuiUtil.getEnergyBounds(this.leftPos + ENERGY_LEFT, this.topPos + ENERGY_TOP);
    }

    @Override
    public int getTextColour() {
        return 0x2C282E;
    }
}