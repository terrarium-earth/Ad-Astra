package earth.terrarium.adastra.client.screens.base;

import com.teamresourceful.resourcefullib.client.screens.AbstractContainerCursorScreen;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.components.base.TickableWidget;
import earth.terrarium.adastra.client.components.machines.*;
import earth.terrarium.adastra.client.utils.GuiUtils;
import earth.terrarium.adastra.common.blockentities.base.ContainerMachineBlockEntity;
import earth.terrarium.adastra.common.menus.base.BaseConfigurableContainerMenu;
import earth.terrarium.adastra.common.menus.base.BaseContainerMenu;
import earth.terrarium.adastra.common.menus.base.MachineMenu;
import earth.terrarium.adastra.common.menus.configuration.EnergyConfiguration;
import earth.terrarium.adastra.common.menus.configuration.FluidConfiguration;
import earth.terrarium.adastra.common.menus.configuration.MenuConfiguration;
import earth.terrarium.adastra.common.menus.configuration.SlotConfiguration;
import earth.terrarium.adastra.common.menus.slots.ImageSlot;
import earth.terrarium.adastra.common.menus.slots.InventorySlot;
import earth.terrarium.adastra.common.utils.MoveableSlot;
import earth.terrarium.adastra.common.utils.TooltipUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.NotNull;

public abstract class MachineScreen<T extends BaseContainerMenu<U>, U extends ContainerMachineBlockEntity> extends AbstractContainerCursorScreen<T> implements ConfigurationScreen, AbstractContainerScreenExtension {

    public static final ResourceLocation IRON_SLOT = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/container/slots/iron.png");
    public static final ResourceLocation STEEL_SLOT = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/container/slots/steel.png");

    private final ResourceLocation texture;
    private final ResourceLocation slotTexture;

    private final Rect2i inventoryRect;


    protected SidedConfigWidget sideConfigWidget;
    protected OptionsBarWidget optionsBarWidget;
    protected U entity;

    public MachineScreen(
        T menu, Inventory inventory, Component component,
        ResourceLocation texture, ResourceLocation slotTexture,
        int width, int height
    ) {
        super(menu, inventory, component);
        this.texture = texture;
        this.slotTexture = slotTexture;
        this.imageWidth = width;
        this.imageHeight = height;
        this.entity = menu.getEntity();

        var inventorySlots = this.menu.slots.stream().filter(slot -> slot instanceof InventorySlot).toList();

        int minXInventorySlot = inventorySlots.stream().mapToInt(slot -> slot.x).min().orElse(0);
        int maxXInventorySlot = inventorySlots.stream().mapToInt(slot -> slot.x).max().orElse(0);
        int minYInventorySlot = inventorySlots.stream().mapToInt(slot -> slot.y).min().orElse(0);
        int maxYInventorySlot = inventorySlots.stream().mapToInt(slot -> slot.y).max().orElse(0);

        this.inventoryRect = new Rect2i(
            minXInventorySlot - 1, minYInventorySlot - 1,
            maxXInventorySlot - minXInventorySlot + 18,
            maxYInventorySlot - minYInventorySlot + 18
        );

        this.inventoryLabelY = this.inventoryRect.getY() - 4 - Minecraft.getInstance().font.lineHeight;
    }

    @Override
    protected void init() {
        super.init();

        if (this.menu instanceof BaseConfigurableContainerMenu<?> configurableMenu) {
            for (MenuConfiguration configuration : configurableMenu.getConfigurations()) {
                LayoutElement element = switch (configuration.type()) {
                    case SLOT -> addRenderableWidget(new SlotWidget(((SlotConfiguration) configuration)));
                    case ENERGY -> addRenderableWidget(new EnergyBarWidget(((EnergyConfiguration) configuration)));
                    case FLUID -> addRenderableWidget(new FluidBarWidget(entity.getBlockPos(), (FluidConfiguration) configuration));
                };

                element.setPosition(this.leftPos + configuration.x(), this.topPos + configuration.y());
            }
        }

        this.sideConfigWidget = addRenderableWidget(new SidedConfigWidget(
            this.leftPos + this.inventoryRect.getX(), this.topPos + this.inventoryRect.getY(),
            this.inventoryRect.getWidth(), this.inventoryRect.getHeight(),
            this.menu
        ));

        this.optionsBarWidget = this.addRenderableWidget(new OptionsBarWidget(
            this.leftPos + this.imageWidth, this.topPos - 2,
            this.sideConfigWidget::toggle,
            this.entity,
            this.menu instanceof MachineMenu<?> machineMenu && machineMenu.getBatterySlot() != null
        ));
        moveBatterySlot();
    }

    protected void moveBatterySlot() {
        if (this.menu instanceof MachineMenu<?> machineMenu && machineMenu.getBatterySlot() != null) {
            ((MoveableSlot)machineMenu.getBatterySlot()).adastra$moveTo(
                this.optionsBarWidget.getX() + this.optionsBarWidget.getWidth() - OptionsBarWidget.PADDING - this.leftPos + 1 - 18,
                this.optionsBarWidget.getY() + OptionsBarWidget.PADDING - this.topPos + 1
            );
        }
    }

    @Override
    protected void containerTick() {
        for (GuiEventListener child : this.children()) {
            if (child instanceof TickableWidget tickable) {
                tickable.tick();
            }
        }
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int left = (this.width - this.imageWidth) / 2;
        int top = (this.height - this.imageHeight) / 2;
        graphics.blit(this.texture, left, top, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
    }

    @Override
    public void adastra$renderPreSlot(GuiGraphics graphics, Slot slot) {
        ResourceLocation texture = null;
        if (slot.isActive() && slot instanceof InventorySlot) {
            texture = this.slotTexture;
        } else if (slot instanceof ImageSlot imageSlot) {
            texture = imageSlot.getSlotTexture();
        }

        if (texture != null) {
            graphics.blit(texture, slot.x - 1, slot.y - 1, 0, 0, 18, 18, 18, 18);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.drawString(font, this.title, this.titleLabelX, this.titleLabelY, 0x2a262b, false);
        Component label = canConfigure() ? this.sideConfigWidget.getMessage() : this.playerInventoryTitle;
        graphics.drawString(font, label, this.inventoryLabelX, this.inventoryLabelY, 0x2a262b, false);
    }

    public void drawHorizontalProgressBar(GuiGraphics graphics, ResourceLocation texture, int mouseX, int mouseY, int xOffset, int yOffset, int width, int height, int progress, int maxProgress, boolean reverse) {
        GuiUtils.drawHorizontalProgressBar(
            graphics, texture,
            mouseX, mouseY,
            font,
            this.leftPos + xOffset,
            this.topPos + yOffset,
            width, height,
            progress, maxProgress, reverse,
            TooltipUtils.getProgressComponent(progress, maxProgress),
            TooltipUtils.getEtaComponent(progress, maxProgress, reverse));
    }

    public void drawVerticalProgressBar(GuiGraphics graphics, ResourceLocation texture, int mouseX, int mouseY, int xOffset, int yOffset, int width, int height, int progress, int maxProgress, boolean reverse) {
        GuiUtils.drawVerticalProgressBar(
            graphics, texture,
            mouseX, mouseY,
            font,
            this.leftPos + xOffset,
            this.topPos + yOffset,
            width, height,
            progress, maxProgress, reverse,
            TooltipUtils.getProgressComponent(progress, maxProgress),
            TooltipUtils.getEtaComponent(progress, maxProgress, reverse));
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        setFocused(null);
        return super.mouseReleased(mouseX, mouseY, button);
    }

    public int leftPos() {
        return this.leftPos;
    }

    public int topPos() {
        return this.topPos;
    }

    @Override
    public void onClose() {
        if (canConfigure()) {
            this.sideConfigWidget.toggle();
        } else {
            super.onClose();
        }
    }

    @Override
    protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeft, int guiTop, int mouseButton) {
        if (this.optionsBarWidget.isMouseOver(mouseX, mouseY)) {
            return false;
        }
        return super.hasClickedOutside(mouseX, mouseY, guiLeft, guiTop, mouseButton);
    }

    @Override
    public SidedConfigWidget getSideConfigWidget() {
        return this.sideConfigWidget;
    }
}
