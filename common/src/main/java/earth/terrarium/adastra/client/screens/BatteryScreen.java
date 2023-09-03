package earth.terrarium.adastra.client.screens;

import com.teamresourceful.resourcefullib.client.screens.AbstractContainerCursorScreen;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.components.PressableImageButton;
import earth.terrarium.adastra.client.utils.GuiUtils;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.menus.BatteryMenu;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class BatteryScreen extends AbstractContainerCursorScreen<BatteryMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/container/battery.png");
    private static final int WIDTH = 215;
    private static final int HEIGHT = 231;

    public BatteryScreen(BatteryMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.imageWidth = 231;
        this.imageHeight = 115;
        this.inventoryLabelY = this.imageHeight - 40;
        this.titleLabelY = -15;
    }

    @Override
    protected void init() {
        super.init();

        // Redstone
        addRenderableWidget(new PressableImageButton(this.leftPos + 170, this.topPos - 52, 18, 18, 0, 0, 18, GuiUtils.SQUARE_BUTTON, 18, 36,
            button -> {},
            ConstantComponents.REDSTONE_CONTROL
        ));

        // Side Config
        addRenderableWidget(new PressableImageButton(this.leftPos + 149, this.topPos - 52, 18, 18, 0, 0, 18, GuiUtils.SQUARE_BUTTON, 18, 36,
            button -> {},
            ConstantComponents.SIDE_CONFIG
        ));
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float f) {
        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, f);
        renderTooltip(graphics, mouseX, mouseY);

        GuiUtils.drawEnergyBar(
            graphics,
            mouseX,
            mouseY,
            font,
            this.leftPos + 164,
            this.topPos + 38,
            menu.getEntity().getEnergyStorage(),
            Component.translatable(
                "tooltip.adastra.energy_%s".formatted(menu.getEntity().energyDifference() < 0 ? "out" : "in"),
                GuiUtils.getFormattedEnergy(Math.abs(menu.getEntity().energyDifference()))).withStyle(ChatFormatting.GOLD),
            Component.translatable("tooltip.adastra.max_energy_in",
                GuiUtils.getFormattedEnergy(menu.getEntity().getEnergyStorage().maxInsert())).withStyle(ChatFormatting.GREEN),
            Component.translatable("tooltip.adastra.max_energy_out",
                GuiUtils.getFormattedEnergy(menu.getEntity().getEnergyStorage().maxExtract())).withStyle(ChatFormatting.GREEN));

        // Gear
        graphics.blit(GuiUtils.SIDE_SETTINGS_ICON, this.leftPos + 149, this.topPos - 52, 0, 0, 18, 18, 18, 18);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int left = (this.width - WIDTH) / 2;
        int top = (this.height - HEIGHT) / 2;
        graphics.blit(TEXTURE, left - 8, top, 0, 0, WIDTH, HEIGHT, WIDTH, HEIGHT);
    }

    public int getTextColor() {
        return 0x2a262b;
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.drawString(font, this.title, this.titleLabelX + 36, this.titleLabelY, this.getTextColor(), false);
        graphics.drawString(font, this.playerInventoryTitle, this.inventoryLabelX + 36, this.inventoryLabelY, this.getTextColor(), false);
    }
}
