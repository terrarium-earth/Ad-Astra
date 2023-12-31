package earth.terrarium.adastra.client.components.base;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class ContainerWidget extends AbstractContainerEventHandler implements Renderable, LayoutElement, NarratableEntry {

    protected final List<Renderable> renderables = new ArrayList<>();
    protected final List<GuiEventListener> children = new ArrayList<>();

    protected int x, y;
    protected int width, height;

    private boolean active = true;

    public ContainerWidget(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public @NotNull List<? extends GuiEventListener> children() {
        if (!this.active) return List.of();
        return children;
    }

    protected <T extends GuiEventListener & Renderable> T addRenderableWidget(T widget) {
        this.renderables.add(widget);
        this.children.add(widget);
        return widget;
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        if (!this.active) return;
        for (Renderable renderable : renderables) {
            renderable.render(graphics, mouseX, mouseY, partialTicks);
        }
    }

    @Override
    public @NotNull NarrationPriority narrationPriority() {
        return NarrationPriority.NONE;
    }

    @Override
    public void updateNarration(@NotNull NarrationElementOutput output) {

    }

    @Override
    public void setFocused(boolean focused) {
        if (!focused) setFocused(null);
    }

    @Override
    public void setX(int x) {
        this.x = x;
        onDimensionsChanged();
    }

    @Override
    public void setY(int y) {
        this.y = y;
        onDimensionsChanged();
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    public void setWidth(int width) {
        this.width = width;
        onDimensionsChanged();
    }

    public void setHeight(int height) {
        this.height = height;
        onDimensionsChanged();
    }

    @Override
    public void visitWidgets(Consumer<AbstractWidget> consumer) {
        for (GuiEventListener child : this.children()) {
            if (child instanceof AbstractWidget widget) {
                consumer.accept(widget);
            }
            if (child instanceof LayoutElement layout) {
                layout.visitWidgets(consumer);
            }
        }
    }

    @Override
    public @NotNull ScreenRectangle getRectangle() {
        return LayoutElement.super.getRectangle();
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return this.active && (mouseX >= this.x && mouseX < this.x + this.width && mouseY >= this.y && mouseY < this.y + this.height);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return this.active && super.mouseClicked(mouseX, mouseY, button);
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    public void onDimensionsChanged() {

    }
}
