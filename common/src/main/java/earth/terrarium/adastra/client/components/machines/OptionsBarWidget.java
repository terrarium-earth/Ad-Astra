package earth.terrarium.adastra.client.components.machines;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.components.base.ContainerWidget;
import earth.terrarium.adastra.common.blockentities.base.ContainerMachineBlockEntity;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.layouts.SpacerElement;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class OptionsBarWidget extends ContainerWidget {

    private static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "options_bar");
    public static final int PADDING = 6;
    public static final int SPACING = 3;

    protected final GridLayout layout = Util.make(new GridLayout(), layout -> {
        layout.columnSpacing(SPACING);
        layout.defaultCellSetting().alignVerticallyMiddle();
    });

    public OptionsBarWidget(int right, int top, List<LayoutElement> elements) {
        super(0, 0, 0, 0);

        for (int i = 0; i < elements.size(); i++) {
            layout.addChild(elements.get(i), 0, i);
        }

        layout.arrangeElements();
        layout.visitWidgets(this::addRenderableWidget);

        this.width = layout.getWidth() + PADDING * 2;
        this.height = layout.getHeight() + PADDING * 2;
        this.x = right - this.getWidth();
        this.y = top - this.getHeight();

        layout.setX(this.getX() + PADDING);
        layout.setY(this.getY() + PADDING);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        graphics.blitSprite(TEXTURE, getX(), getY(), getWidth(), getHeight());
        super.render(graphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public void onDimensionsChanged() {
        layout.setX(this.getX() + PADDING);
        layout.setY(this.getY() + PADDING);
    }

    public static class Builder {
        private final List<LayoutElement> elements = new ArrayList<>();

        private Builder() {}

        public Builder addElement(int index, LayoutElement element) {
            this.elements.add(index, element);
            return this;
        }

        public Builder addElement(LayoutElement element) {
            this.elements.add(element);
            return this;
        }

        public Builder addSettingsButton(Runnable runnable) {
            return addElement(OptionBarOptions.createSettings(runnable));
        }

        public Builder addRedstoneButton(ContainerMachineBlockEntity entity) {
            return addElement(OptionBarOptions.createRedstone(entity));
        }

        public Builder addBattery() {
            return addElement(new SpacerElement(18, 18));
        }

        public OptionsBarWidget build(int right, int top) {
            return new OptionsBarWidget(right, top, this.elements);
        }
    }
}
