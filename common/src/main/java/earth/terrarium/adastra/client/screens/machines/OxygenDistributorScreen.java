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
    public static final Rect2i CLICK_AREA = new Rect2i(76, 95, 26, 25);

    public OxygenDistributorScreen(OxygenDistributorMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component, TEXTURE, STEEL_SLOT, 177, 244);
        this.titleLabelY = 65;
    }

    @Override
    protected void init() {
        super.init();
        addRenderableWidget(new ImageButton(
            leftPos + 98,
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

        this.optionsBarWidget.setY(topPos);
        this.optionsBarWidget.setX(leftPos + 98);
        moveBatterySlot();
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        super.renderBg(graphics, partialTick, mouseX, mouseY);
        graphics.drawString(
            font,
            Component.translatable("tooltip.ad_astra.energy_per_tick", entity.energyPerTick()),
            leftPos + 11,
            topPos + 9,
            0x68d975
        );
        graphics.drawString(
            font,
            Component.translatable("tooltip.ad_astra.fluid_per_tick", Math.round(entity.fluidPerTick() * 1000.0f) / 1000.0f),
            leftPos + 11,
            topPos + 20,
            0x68d975
        );
        graphics.drawString(
            font,
            Component.translatable("tooltip.ad_astra.blocks_distributed", entity.distributedBlocksCount(), entity.distributedBlocksLimit()),
            leftPos + 11,
            topPos + 31,
            0x68d975
        );

        graphics.drawCenteredString(
            font,
            AdAstraConfigClient.showOxygenDistributorArea ? ConstantComponents.HIDE : ConstantComponents.SHOW,
            leftPos + 131,
            topPos + 45,
            0x68d975
        );
    }
}
