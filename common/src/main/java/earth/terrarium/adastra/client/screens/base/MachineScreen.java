package earth.terrarium.adastra.client.screens.base;

import com.teamresourceful.resourcefullib.client.screens.AbstractContainerCursorScreen;
import earth.terrarium.adastra.client.components.PressableImageButton;
import earth.terrarium.adastra.client.utils.GuiUtils;
import earth.terrarium.adastra.common.blockentities.base.ContainerMachineBlockEntity;
import earth.terrarium.adastra.common.blockentities.base.RedstoneControl;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.Configuration;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationEntry;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationType;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.menus.base.BasicContainerMenu;
import earth.terrarium.adastra.common.networking.NetworkHandler;
import earth.terrarium.adastra.common.networking.messages.ServerboundClearFluidTankPacket;
import earth.terrarium.adastra.common.networking.messages.ServerboundResetSideConfigPacket;
import earth.terrarium.adastra.common.networking.messages.ServerboundSetRedstoneControlPacket;
import earth.terrarium.adastra.common.networking.messages.ServerboundSetSideConfigPacket;
import earth.terrarium.adastra.common.utils.ComponentUtils;
import earth.terrarium.adastra.common.utils.ModUtils;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import earth.terrarium.botarium.common.fluid.impl.WrappedBlockFluidContainer;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class MachineScreen<T extends BasicContainerMenu<U>, U extends ContainerMachineBlockEntity> extends AbstractContainerCursorScreen<T> {
    private final ResourceLocation texture;
    private final List<ClearOnClick> clearOnClicks = new ArrayList<>();
    protected final List<List<? extends Button>> sideConfigButtons = new ArrayList<>(6);
    private Button resetToDefaultButton;
    private Button previousArrow;
    private Button nextArrow;
    private int arrowIndex;
    protected Component sideConfigTitle = Component.empty();

    protected U entity;
    protected boolean showSideConfig = false;

    public MachineScreen(T menu, Inventory inventory, Component component, ResourceLocation texture, int width, int height) {
        super(menu, inventory, component);
        this.texture = texture;
        this.imageWidth = width;
        this.imageHeight = height;
        this.entity = menu.getEntity();

        this.inventoryLabelX = width - 180;
        this.inventoryLabelY = height - 98;
        this.titleLabelX = width - 180;
        this.titleLabelY = 43;
    }

    @Override
    protected void init() {
        super.init();

        sideConfigButtons.clear();
        var configurableEntries = entity.getSideConfig();
        for (int i = 0; i < configurableEntries.size(); i++) {
            var entry = configurableEntries.get(i);
            List<PressableImageButton> buttons = new ArrayList<>();
            final int configIndex = i;
            entry.sides().forEach((direction, configuration) -> {
                PressableImageButton button = getSideConfigButton(configIndex, direction, configuration, entry);
                buttons.add(button);
                addRenderableWidget(button);
                button.visible = false;
            });
            sideConfigButtons.add(buttons);
            sideConfigTitle = getSideConfigTitle();
        }

        resetToDefaultButton = addRenderableWidget(new PressableImageButton(this.leftPos + getSideConfigButtonXOffset() - 43, this.topPos + getSideConfigButtonYOffset() + 25, 18, 18, 0, 0, 18, GuiUtils.SQUARE_BUTTON, 18, 54,
            button -> {
                entity.resetToDefault(arrowIndex);
                NetworkHandler.CHANNEL.sendToServer(new ServerboundResetSideConfigPacket(entity.getBlockPos(), arrowIndex));
                rebuildWidgets();
                sideConfigButtons.get(arrowIndex).forEach(b -> b.visible = true);
            }
        ));
        resetToDefaultButton.setTooltip(Tooltip.create(ConstantComponents.RESET_TO_DEFAULT.copy().withStyle(ChatFormatting.DARK_RED)));
        if (!showSideConfig) resetToDefaultButton.visible = false;

        previousArrow = addRenderableWidget(new ImageButton(this.leftPos + getSideConfigButtonXOffset() - 42, this.topPos + getSideConfigButtonYOffset() - 38, 7, 11, 7, 0, 11, GuiUtils.ARROWS, 14, 22,
            button -> {
                sideConfigButtons.forEach(all -> all.forEach(b -> b.visible = false));
                int size = sideConfigButtons.size();
                arrowIndex = (arrowIndex - 1 + size) % size;
                sideConfigButtons.get(arrowIndex).forEach(b -> b.visible = true);
                sideConfigTitle = getSideConfigTitle();
            }
        ));
        previousArrow.setTooltip(Tooltip.create(ConstantComponents.PREVIOUS));
        if (!showSideConfig) previousArrow.visible = false;

        nextArrow = addRenderableWidget(new ImageButton(this.leftPos + getSideConfigButtonXOffset() - 32, this.topPos + getSideConfigButtonYOffset() - 38, 7, 11, 0, 0, 11, GuiUtils.ARROWS, 14, 22,
            button -> {
                sideConfigButtons.forEach(all -> all.forEach(b -> b.visible = false));
                int size = sideConfigButtons.size();
                arrowIndex = (arrowIndex + 1) % size;
                sideConfigButtons.get(arrowIndex).forEach(b -> b.visible = true);
                sideConfigTitle = getSideConfigTitle();
            }
        ));
        nextArrow.setTooltip(Tooltip.create(ConstantComponents.NEXT));
        if (!showSideConfig) nextArrow.visible = false;
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
        if (showSideConfig) {
            this.renderSideConfig(graphics, mouseX, mouseY);
            this.highlightSideConfigElement(graphics, arrowIndex);
        }
    }

    public abstract void renderSideConfig(GuiGraphics graphics, int mouseX, int mouseY);

    public int getGuiColor() {
        return 0xff4d4a4e;
    }

    public int getTextColor() {
        return 0x2a262b;
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.drawString(font, this.title, this.titleLabelX, this.titleLabelY, this.getTextColor(), false);
        if (!this.showSideConfig) {
            graphics.drawString(font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, this.getTextColor(), false);
        } else {
            graphics.drawString(font, this.sideConfigTitle, this.inventoryLabelX + 18, this.inventoryLabelY + 11, this.getTextColor(), false);
        }
    }

    public void addRedstoneButton(int xOffset, int yOffset) {
        addRenderableWidget(new PressableImageButton(this.leftPos + xOffset, this.topPos + yOffset, 18, 18, 0, 0, 18, entity.getRedstoneControl().icon(), 18, 54,
            button -> {
                RedstoneControl next = hasShiftDown() ? entity.getRedstoneControl().previous() : entity.getRedstoneControl().next();
                entity.setRedstoneControl(next);
                NetworkHandler.CHANNEL.sendToServer(new ServerboundSetRedstoneControlPacket(entity.getBlockPos(), next));
                button.setTooltip(Tooltip.create(getRedstoneControlTooltip(next)));
                ((PressableImageButton) button).setTexture(next.icon());
            },
            getRedstoneControlTooltip(entity.getRedstoneControl())
        ));
    }

    public void addSideConfigButton(int xOffset, int yOffset) {
        addRenderableWidget(new PressableImageButton(this.leftPos + xOffset, this.topPos + yOffset, 18, 18, 0, 0, 18, GuiUtils.SETTINGS_BUTTON, 18, 54,
            button -> toggleSideConfig(),
            ConstantComponents.SIDE_CONFIG
        ));
    }

    protected void toggleSideConfig() {
        this.menu.togglePlayerInvSlots();
        showSideConfig = !showSideConfig;
        sideConfigButtons.forEach(all -> all.forEach(b -> b.visible = false));
        sideConfigButtons.get(arrowIndex).forEach(b -> b.visible = showSideConfig);
        resetToDefaultButton.visible = showSideConfig;
        previousArrow.visible = showSideConfig;
        nextArrow.visible = showSideConfig;
    }

    public void drawEnergyBar(GuiGraphics graphics, int mouseX, int mouseY, int xOffset, int yOffset, WrappedBlockEnergyContainer energy, long energyDifference) {
        GuiUtils.drawEnergyBar(
            graphics, mouseX,
            mouseY, font,
            this.leftPos + xOffset,
            this.topPos + yOffset,
            energy,
            ComponentUtils.getEnergyDifferenceComponent(energyDifference),
            ComponentUtils.getMaxEnergyInComponent(energy.maxInsert()),
            ComponentUtils.getMaxEnergyOutComponent(energy.maxExtract()));
    }

    public void drawFluidBar(GuiGraphics graphics, int mouseX, int mouseY, int xOffset, int yOffset, WrappedBlockFluidContainer fluid, int tank, long fluidDifference) {
        int x = this.leftPos + xOffset;
        int y = this.topPos + yOffset;
        GuiUtils.drawFluidBar(
            graphics, mouseX,
            mouseY, font,
            x, y,
            fluid, tank,
            ComponentUtils.getFluidDifferenceComponent(fluidDifference),
            !fluid.isEmpty() ? ConstantComponents.CLEAR_FLUID_TANK : Component.empty());

        clearOnClicks.add(new ClearOnClick(x + 6, y - 31, x + 22, y + 17, tank));
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (var clearOnClick : clearOnClicks) {
            if (mouseX >= clearOnClick.minX() && mouseX <= clearOnClick.maxX() && mouseY >= clearOnClick.minY() && mouseY <= clearOnClick.maxY()) {
                if (button == 1 && Screen.hasShiftDown()) {
                    NetworkHandler.CHANNEL.sendToServer(new ServerboundClearFluidTankPacket(entity.getBlockPos(), clearOnClick.tank()));
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        setFocused(null);
        return super.mouseReleased(mouseX, mouseY, button);
    }

    private PressableImageButton getSideConfigButton(int configIndex, Direction direction, Configuration initial, ConfigurationEntry entry) {
        IntIntPair pos = getButtonPosForDirection(direction);
        return new PressableImageButton(
            leftPos + 5 + getSideConfigButtonXOffset() + pos.leftInt(),
            topPos + getSideConfigButtonYOffset() - pos.rightInt(),
            16, 16,
            0, 0,
            16,
            initial.icon(),
            16, 32, b -> {
            Configuration configuration = entity.getSideConfig().get(configIndex).get(direction);
            Configuration next = hasShiftDown() ? configuration.previous() : configuration.next();
            entry.set(direction, next);
            NetworkHandler.CHANNEL.sendToServer(new ServerboundSetSideConfigPacket(entity.getBlockPos(), configIndex, direction, next));

            b.setTooltip(Tooltip.create(getSideConfigTooltip(entry.type(), direction, next)));
            ((PressableImageButton) b).setTexture(next.icon());
        }, getSideConfigTooltip(entry.type(), direction, initial));
    }

    public Component getSideConfigTooltip(ConfigurationType type, Direction direction, Configuration action) {
        Direction relative = ModUtils.relative(entity, direction);

        return Component.empty()
            .append(Component.translatable("side_config.adastra.type.type", type.translation().getString()).withStyle(ChatFormatting.GREEN))
            .append("\n")
            .append(Component.translatable("side_config.adastra.type.direction", ComponentUtils.getRelativeDirectionComponent(direction).getString(), ComponentUtils.getDirectionComponent(relative).getString()).withStyle(ChatFormatting.GOLD))
            .append("\n")
            .append(Component.translatable("side_config.adastra.type.action", action.translation().getString()).withStyle(ChatFormatting.GOLD));
    }

    public Component getRedstoneControlTooltip(RedstoneControl redstoneControl) {
        return Component.empty()
            .append(ConstantComponents.REDSTONE_CONTROL).withStyle(ChatFormatting.RED)
            .append("\n")
            .append(Component.translatable("tooltip.adastra.redstone_control.mode", redstoneControl.translation().getString()).withStyle(ChatFormatting.GOLD));
    }

    private IntIntPair getButtonPosForDirection(Direction direction) {
        return switch (direction) {
            case UP -> IntIntPair.of(0, 19);
            case DOWN -> IntIntPair.of(0, -19);
            case NORTH -> IntIntPair.of(0, 0);
            case EAST -> IntIntPair.of(-19, 0);
            case SOUTH -> IntIntPair.of(-19, -19);
            case WEST -> IntIntPair.of(19, 0);
        };
    }

    public abstract void highlightSideConfigElement(GuiGraphics graphics, int index);

    public Component getSideConfigTitle() {
        return Component.translatable("side_config.adastra.title", entity.getSideConfig().get(arrowIndex).title().getString());
    }

    public abstract int getSideConfigButtonXOffset();

    public abstract int getSideConfigButtonYOffset();

    public int leftPos() {
        return this.leftPos;
    }

    public int topPos() {
        return this.topPos;
    }

    @SuppressWarnings("unused")
    public void testClickArea(GuiGraphics graphics, Rect2i clickArea) {
        graphics.fill(leftPos + clickArea.getX(), topPos + clickArea.getY(), leftPos + clickArea.getX() + clickArea.getWidth(), topPos + clickArea.getY() + clickArea.getHeight(), 0x40ffff00);
    }

    private record ClearOnClick(int minX, int minY, int maxX, int maxY, int tank) {}

    @Override
    public void onClose() {
        if (showSideConfig) toggleSideConfig();
        else super.onClose();
    }
}
