package earth.terrarium.adastra.common.compat.rei.categories;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.screens.machines.NasaWorkbenchScreen;
import earth.terrarium.adastra.common.compat.rei.displays.NasaWorkbenchDisplay;
import earth.terrarium.adastra.common.registry.ModBlocks;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class NasaWorkbenchCategory implements DisplayCategory<NasaWorkbenchDisplay> {
    public static final CategoryIdentifier<NasaWorkbenchDisplay> ID = CategoryIdentifier.of(AdAstra.MOD_ID, "nasa_workbench");

    @Override
    public CategoryIdentifier<? extends NasaWorkbenchDisplay> getCategoryIdentifier() {
        return ID;
    }

    @Override
    public Component getTitle() {
        return Component.translatable(ModBlocks.NASA_WORKBENCH.get().getDescriptionId());
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.NASA_WORKBENCH.get());
    }

    @Override
    public int getDisplayWidth(NasaWorkbenchDisplay display) {
        return 179;
    }

    @Override
    public int getDisplayHeight() {
        return 142;
    }

    @Override
    public List<Widget> setupDisplay(NasaWorkbenchDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();

        widgets.add(Widgets.createTexturedWidget(NasaWorkbenchScreen.TEXTURE, bounds.x, bounds.y, 0, 0, 177, 140, 177, 224));
        widgets.add(Widgets.createTexturedWidget(NasaWorkbenchScreen.TEXTURE, bounds.x, bounds.y + 135, 0, 217, 177, 7, 177, 224));

        slot(widgets, display, bounds, 56, 20, 0);
        slot(widgets, display, bounds, 47, 38, 1);
        slot(widgets, display, bounds, 65, 38, 2);
        slot(widgets, display, bounds, 47, 56, 3);
        slot(widgets, display, bounds, 65, 56, 4);
        slot(widgets, display, bounds, 47, 74, 5);
        slot(widgets, display, bounds, 65, 74, 6);
        slot(widgets, display, bounds, 29, 92, 7);
        slot(widgets, display, bounds, 47, 92, 8);
        slot(widgets, display, bounds, 65, 92, 9);
        slot(widgets, display, bounds, 83, 92, 10);
        slot(widgets, display, bounds, 29, 110, 11);
        slot(widgets, display, bounds, 56, 110, 12);
        slot(widgets, display, bounds, 83, 110, 13);

        widgets.add(Widgets.createSlot(new Point(bounds.x + 129, bounds.y + 56)).backgroundEnabled(false).entries(display.getOutputEntries().get(0)).markOutput());

        return widgets;
    }

    private void slot(List<Widget> widgets, NasaWorkbenchDisplay display, Rectangle bounds, int x, int y, int index) {
        var slot = Widgets.createSlot(new Point(bounds.x + x, bounds.y + y)).backgroundEnabled(false);
        if (index < display.getInputEntries().size()) {
            slot.entries(display.getInputEntries().get(index)).markInput();
        }
        widgets.add(slot);
    }
}
