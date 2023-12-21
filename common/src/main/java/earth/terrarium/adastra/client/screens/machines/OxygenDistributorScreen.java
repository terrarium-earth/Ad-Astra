package earth.terrarium.adastra.client.screens.machines;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.config.AdAstraConfigClient;
import earth.terrarium.adastra.client.screens.base.MachineScreen;
import earth.terrarium.adastra.client.utils.GuiUtils;
import earth.terrarium.adastra.common.blockentities.machines.OxygenDistributorBlockEntity;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.menus.machines.OxygenDistributorMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class OxygenDistributorScreen extends MachineScreen<OxygenDistributorMenu, OxygenDistributorBlockEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/container/oxygen_distributor.png");
    public static final Rect2i CLICK_AREA = new Rect2i(68, 95, 26, 25);

    public OxygenDistributorScreen(OxygenDistributorMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component, TEXTURE, 177, 244);
        this.titleLabelY = 65;
    }

    @Override
    protected void init() {
        super.init();
        this.addRedstoneButton(124, 11);
        this.addSideConfigButton(103, 11);
        addRenderableWidget(new ImageButton(
            leftPos + 90,
            topPos + 39,
            71,
            20,
            0,
            0,
            20,
            GuiUtils.BUTTON,
            71,
            40,
            button -> {
                AdAstraConfigClient.showOxygenDistributorArea = !AdAstraConfigClient.showOxygenDistributorArea;
                Minecraft.getInstance().tell(() -> AdAstra.CONFIGURATOR.saveConfig(AdAstraConfigClient.class));
            },
            AdAstraConfigClient.showOxygenDistributorArea ? ConstantComponents.HIDE : ConstantComponents.SHOW
        )).setTooltip(Tooltip.create(ConstantComponents.OXYGEN_DISTRIBUTION_AREA));

    }

    @Override
    protected void renderBg(@NotNull GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        super.renderBg(graphics, partialTick, mouseX, mouseY);
        this.drawOptionsBar(graphics, 97, 5);
        this.drawEnergyBar(graphics, mouseX, mouseY, 133, 113, entity.getEnergyStorage(), entity.energyDifference());
        this.drawFluidBar(graphics, mouseX, mouseY, 37, 113, entity.getFluidContainer(), 0, entity.fluidDifference(0));
        this.drawFluidBar(graphics, mouseX, mouseY, 101, 113, entity.getFluidContainer(), 1, entity.fluidDifference(1));

        graphics.drawString(
            font,
            Component.translatable("tooltip.ad_astra.energy_per_tick", entity.energyPerTick()),
            leftPos + 3,
            topPos + 9,
            0x68d975
        );
        graphics.drawString(
            font,
            Component.translatable("tooltip.ad_astra.fluid_per_tick", Math.round(entity.fluidPerTick() * 1000.0f) / 1000.0f),
            leftPos + 3,
            topPos + 20,
            0x68d975
        );
        graphics.drawString(
            font,
            Component.translatable("tooltip.ad_astra.blocks_distributed", entity.distributedBlocksCount(), OxygenDistributorBlockEntity.MAX_BLOCKS),
            leftPos + 3,
            topPos + 31,
            0x68d975
        );

        graphics.drawCenteredString(
            font,
            AdAstraConfigClient.showOxygenDistributorArea ? ConstantComponents.HIDE : ConstantComponents.SHOW,
            leftPos + 123,
            topPos + 45,
            0x68d975
        );
    }

    @Override
    public void renderSideConfig(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.fill(leftPos - 1, topPos + 161, leftPos + 161, topPos + 237, getGuiColor());
    }

    @Override
    public int getSideConfigButtonXOffset() {
        return 43;
    }

    @Override
    public int getSideConfigButtonYOffset() {
        return imageHeight - 50;
    }

    @Override
    public void highlightSideConfigElement(GuiGraphics graphics, int index) {
        switch (index) {
            case 0 -> graphics.renderOutline(leftPos + 7, topPos + 80, 20, 20, 0xFF00FF00);
            case 1 -> graphics.renderOutline(leftPos + 7, topPos + 110, 20, 20, 0xFF00FF00);
            case 2 ->
                graphics.renderOutline(leftPos + 138, topPos + 81, GuiUtils.ENERGY_BAR_WIDTH + 2, GuiUtils.ENERGY_BAR_HEIGHT + 2, 0xFF00FF00);
            case 3 ->
                graphics.renderOutline(leftPos + 42, topPos + 81, GuiUtils.FLUID_BAR_WIDTH + 2, GuiUtils.FLUID_BAR_HEIGHT + 2, 0xFF00FF00);
            case 4 ->
                graphics.renderOutline(leftPos + 106, topPos + 81, GuiUtils.FLUID_BAR_WIDTH + 2, GuiUtils.FLUID_BAR_HEIGHT + 2, 0xFF00FF00);
        }
    }
}
