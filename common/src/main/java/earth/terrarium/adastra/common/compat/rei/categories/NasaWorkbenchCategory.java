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
        return Component.translatable(ModBlocks.COMPRESSOR.get().getDescriptionId());
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.COMPRESSOR.get());
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

        widgets.add(Widgets.createSlot(new Point(bounds.x + 56, bounds.y + 20)).backgroundEnabled(false).entries(display.getInputEntries().get(0)).markInput());
        widgets.add(Widgets.createSlot(new Point(bounds.x + 47, bounds.y + 38)).backgroundEnabled(false).entries(display.getInputEntries().get(1)).markInput());
        widgets.add(Widgets.createSlot(new Point(bounds.x + 65, bounds.y + 38)).backgroundEnabled(false).entries(display.getInputEntries().get(2)).markInput());
        widgets.add(Widgets.createSlot(new Point(bounds.x + 47, bounds.y + 56)).backgroundEnabled(false).entries(display.getInputEntries().get(3)).markInput());
        widgets.add(Widgets.createSlot(new Point(bounds.x + 65, bounds.y + 56)).backgroundEnabled(false).entries(display.getInputEntries().get(4)).markInput());
        widgets.add(Widgets.createSlot(new Point(bounds.x + 47, bounds.y + 74)).backgroundEnabled(false).entries(display.getInputEntries().get(5)).markInput());
        widgets.add(Widgets.createSlot(new Point(bounds.x + 65, bounds.y + 74)).backgroundEnabled(false).entries(display.getInputEntries().get(6)).markInput());
        widgets.add(Widgets.createSlot(new Point(bounds.x + 29, bounds.y + 92)).backgroundEnabled(false).entries(display.getInputEntries().get(7)).markInput());
        widgets.add(Widgets.createSlot(new Point(bounds.x + 47, bounds.y + 92)).backgroundEnabled(false).entries(display.getInputEntries().get(8)).markInput());
        widgets.add(Widgets.createSlot(new Point(bounds.x + 65, bounds.y + 92)).backgroundEnabled(false).entries(display.getInputEntries().get(9)).markInput());
        widgets.add(Widgets.createSlot(new Point(bounds.x + 83, bounds.y + 92)).backgroundEnabled(false).entries(display.getInputEntries().get(10)).markInput());
        widgets.add(Widgets.createSlot(new Point(bounds.x + 29, bounds.y + 110)).backgroundEnabled(false).entries(display.getInputEntries().get(11)).markInput());
        widgets.add(Widgets.createSlot(new Point(bounds.x + 56, bounds.y + 110)).backgroundEnabled(false).entries(display.getInputEntries().get(12)).markInput());
        widgets.add(Widgets.createSlot(new Point(bounds.x + 83, bounds.y + 110)).backgroundEnabled(false).entries(display.getInputEntries().get(13)).markInput());

        widgets.add(Widgets.createSlot(new Point(bounds.x + 129, bounds.y + 56)).backgroundEnabled(false).entries(display.getOutputEntries().get(0)).markOutput());

        return widgets;
    }
}
