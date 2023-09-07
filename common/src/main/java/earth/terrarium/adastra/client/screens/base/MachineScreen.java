package earth.terrarium.adastra.client.screens.base;

import com.teamresourceful.resourcefullib.client.screens.AbstractContainerCursorScreen;
import earth.terrarium.adastra.client.components.PressableImageButton;
import earth.terrarium.adastra.client.utils.GuiUtils;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.menus.base.BasicContainerMenu;
import earth.terrarium.adastra.common.utils.ComponentUtils;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import earth.terrarium.botarium.common.fluid.impl.WrappedBlockFluidContainer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

public abstract class MachineScreen<T extends BasicContainerMenu<U>, U extends BlockEntity> extends AbstractContainerCursorScreen<T> {
    private final ResourceLocation texture;

    public MachineScreen(T abstractContainerMenu, Inventory inventory, Component component, ResourceLocation texture, int width, int height) {
        super(abstractContainerMenu, inventory, component);
        this.texture = texture;
        this.imageWidth = width;
        this.imageHeight = height;

        this.inventoryLabelX = width - 180;
        this.inventoryLabelY = height - 98;
        this.titleLabelX = width - 180;
        this.titleLabelY = 43;
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float f) {
        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, f);
        renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int left = (this.width - this.imageWidth) / 2;
        int top = (this.height - this.imageHeight) / 2;
        graphics.blit(this.texture, left - 8, top, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
    }

    public int getTextColor() {
        return 0x2a262b;
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.drawString(font, this.title, this.titleLabelX, this.titleLabelY, this.getTextColor(), false);
        graphics.drawString(font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, this.getTextColor(), false);
    }

    public void addRedstoneButton(int xOffset, int yOffset) {
        addRenderableWidget(new PressableImageButton(this.leftPos + xOffset, this.topPos + yOffset, 18, 18, 0, 0, 18, GuiUtils.SQUARE_BUTTON, 18, 36,
            button -> {},
            ConstantComponents.REDSTONE_CONTROL
        ));
    }

    public void addSideConfigButton(int xOffset, int yOffset) {
        addRenderableWidget(new PressableImageButton(this.leftPos + xOffset, this.topPos + yOffset, 18, 18, 0, 0, 18, GuiUtils.SQUARE_BUTTON, 18, 36,
            button -> {},
            ConstantComponents.SIDE_CONFIG
        ));
    }

    public void drawGear(GuiGraphics graphics, int xOffset, int yOffset) {
        graphics.blit(GuiUtils.SIDE_SETTINGS_ICON, this.leftPos + xOffset, this.topPos + yOffset, 0, 0, 18, 18, 18, 18);
    }

    public void drawEnergyBar(GuiGraphics graphics, int mouseX, int mouseY, int xOffset, int yOffset, WrappedBlockEnergyContainer energy, long energyDifference) {
        GuiUtils.drawEnergyBar(
            graphics,
            mouseX,
            mouseY,
            font,
            this.leftPos + xOffset,
            this.topPos + yOffset,
            energy,
            ComponentUtils.getEnergyDifferenceComponent(energyDifference),
            ComponentUtils.getMaxEnergyInComponent(energy.maxInsert()),
            ComponentUtils.getMaxEnergyOutComponent(energy.maxExtract()));
    }

    public void drawFluidBar(GuiGraphics graphics, int mouseX, int mouseY, int xOffset, int yOffset, WrappedBlockFluidContainer fluid, int tank, long fluidDifference) {
        GuiUtils.drawFluidBar(
            graphics,
            mouseX,
            mouseY,
            font,
            this.leftPos + xOffset,
            this.topPos + yOffset,
            fluid,
            tank,
            ComponentUtils.getFluidDifferenceComponent(fluidDifference));
    }
}
