package earth.terrarium.adastra.client.components.machines;

import com.teamresourceful.resourcefullib.client.components.CursorWidget;
import com.teamresourceful.resourcefullib.client.screens.CursorScreen;
import com.teamresourceful.resourcefullib.client.utils.RenderUtils;
import earth.terrarium.adastra.client.components.base.TickableWidget;
import earth.terrarium.adastra.client.utils.GuiUtils;
import earth.terrarium.adastra.common.menus.configuration.EnergyConfiguration;
import earth.terrarium.adastra.common.utils.TooltipUtils;
import earth.terrarium.botarium.common.energy.base.EnergyContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.CommonComponents;

public class EnergyBarWidget extends ConfigurationWidget implements CursorWidget, TickableWidget {

    protected final EnergyContainer container;
    protected long lastStoredEnergy;
    protected long difference;

    public EnergyBarWidget(EnergyConfiguration configuration) {
        super(configuration, GuiUtils.ENERGY_BAR_WIDTH, GuiUtils.ENERGY_BAR_HEIGHT);
        this.container = configuration.container();
    }

    @Override
    public void tick() {
        this.difference = this.container.getStoredEnergy() - this.lastStoredEnergy;
        this.lastStoredEnergy = this.container.getStoredEnergy();
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.renderWidget(graphics, mouseX, mouseY, partialTick);
        long capacity = this.container.getMaxCapacity();
        long energy = this.container.getStoredEnergy();
        float ratio = energy / (float) capacity;
        int x = this.getX();
        int y = this.getY();
        try (var ignored = RenderUtils.createScissorBox(Minecraft.getInstance(), graphics.pose(), x, y + GuiUtils.ENERGY_BAR_HEIGHT - (int) (GuiUtils.ENERGY_BAR_HEIGHT * ratio), GuiUtils.ENERGY_BAR_WIDTH, GuiUtils.ENERGY_BAR_HEIGHT)) {
            graphics.blitSprite(GuiUtils.ENERGY_BAR, x, y, GuiUtils.ENERGY_BAR_WIDTH, GuiUtils.ENERGY_BAR_HEIGHT);
        }

        if (this.isHoveredOrFocused()) {
            setTooltip(Tooltip.create(CommonComponents.joinLines(
                TooltipUtils.getEnergyComponent(energy, capacity),
                TooltipUtils.getEnergyDifferenceComponent(this.difference),
                TooltipUtils.getMaxEnergyInComponent(container.maxInsert()),
                TooltipUtils.getMaxEnergyOutComponent(container.maxExtract())
            )));
            setTooltipDelay(-1);
        }
    }

    @Override
    public CursorScreen.Cursor getCursor() {
        return CursorScreen.Cursor.DEFAULT;
    }
}
