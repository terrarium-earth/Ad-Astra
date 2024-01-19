package earth.terrarium.adastra.client.components.machines;

import earth.terrarium.adastra.client.components.PressableImageButton;
import earth.terrarium.adastra.client.components.base.ContainerWidget;
import earth.terrarium.adastra.client.utils.GuiUtils;
import earth.terrarium.adastra.common.blockentities.base.ContainerMachineBlockEntity;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationEntry;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.menus.base.BaseContainerMenu;
import earth.terrarium.adastra.common.menus.slots.InventorySlot;
import earth.terrarium.adastra.common.network.NetworkHandler;
import earth.terrarium.adastra.common.network.messages.ServerboundResetSideConfigPacket;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.network.chat.Component;

public class SidedConfigWidget extends ContainerWidget {

    private final BaseContainerMenu<? extends ContainerMachineBlockEntity> menu;
    private final ContainerMachineBlockEntity entity;

    private int index;

    public SidedConfigWidget(int x, int y, int width, int height, BaseContainerMenu<? extends ContainerMachineBlockEntity> menu) {
        super(x, y, width, height);
        this.menu = menu;
        this.entity = menu.getEntity();

        init();

        setActive(false);
    }

    public void init() {
        this.renderables.clear();
        this.children.clear();

        if (this.index >= this.entity.getSideConfig().size()) {
            this.index = 0;
        } else if (this.index < 0) {
            this.index = this.entity.getSideConfig().size() - 1;
        } else if (this.entity.getSideConfig().isEmpty()) {
            return;
        }

        SideConfigButtons buttons = createSideButtons(this.index);

        addRenderableWidget(new PressableImageButton(this.getX(), this.getY(), 18, 18, GuiUtils.RESET_BUTTON_SPRITES,
            button -> {
                entity.resetToDefault(this.index);
                NetworkHandler.CHANNEL.sendToServer(new ServerboundResetSideConfigPacket(entity.getBlockPos(), this.index));
                init();
            }
        )).setTooltip(Tooltip.create(ConstantComponents.RESET_TO_DEFAULT));

        buttons.center(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    private SideConfigButtons createSideButtons(int i) {
        GridLayout layout = new GridLayout().spacing(3);
        ConfigurationEntry entry = this.entity.getSideConfig().get(i);

        for (var side : entry.sides().entrySet()) {
            ConfigurationButton button = new ConfigurationButton(this.entity, i, entry, side.getKey(), side.getValue());
            button.visible = this.index == i;

            switch (side.getKey()) {
                case UP -> layout.addChild(button, 0, 1);
                case DOWN -> layout.addChild(button, 2, 1);
                case NORTH -> layout.addChild(button, 1, 1);
                case SOUTH -> layout.addChild(button, 2, 0);
                case EAST -> layout.addChild(button, 1, 0);
                case WEST -> layout.addChild(button, 1, 2);
            }
        }

        layout.visitWidgets(this::addRenderableWidget);
        return new SideConfigButtons(layout);
    }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);
        this.menu.slots.stream().filter(slot -> slot instanceof InventorySlot).forEach(slot -> ((InventorySlot) slot).setActive(!active));
    }

    public void toggle() {
        setActive(!this.isActive());
    }

    public void setIndex(int index) {
        this.index = index;
        init();
    }

    public int getIndex() {
        return this.index;
    }

    public Component getMessage() {
        return Component.translatable("side_config.ad_astra.title", entity.getSideConfig().get(index).title());
    }

    private record SideConfigButtons(GridLayout layout) {

        public void center(int x, int y, int width, int height) {
            this.layout.arrangeElements();
            this.layout.setPosition(
                x + (width - this.layout.getWidth()) / 2,
                y + (height - this.layout.getHeight()) / 2
            );
        }
    }
}
