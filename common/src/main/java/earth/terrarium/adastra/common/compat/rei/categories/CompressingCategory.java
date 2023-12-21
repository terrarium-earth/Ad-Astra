package earth.terrarium.adastra.common.compat.rei.categories;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.screens.machines.CompressorScreen;
import earth.terrarium.adastra.client.utils.GuiUtils;
import earth.terrarium.adastra.common.compat.rei.displays.CompressingDisplay;
import earth.terrarium.adastra.common.compat.rei.widgets.EnergyBarWidget;
import earth.terrarium.adastra.common.compat.rei.widgets.EtaWidget;
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

public class CompressingCategory implements DisplayCategory<CompressingDisplay> {
    public static final CategoryIdentifier<CompressingDisplay> ID = CategoryIdentifier.of(AdAstra.MOD_ID, "compressing");

    @Override
    public CategoryIdentifier<? extends CompressingDisplay> getCategoryIdentifier() {
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
    public int getDisplayWidth(CompressingDisplay display) {
        return 179;
    }

    @Override
    public int getDisplayHeight() {
        return 117;
    }

    @Override
    public List<Widget> setupDisplay(CompressingDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();

        widgets.add(Widgets.createTexturedWidget(CompressorScreen.TEXTURE, bounds.x, bounds.y, 0, 0, 177, 110, 177, 196));
        widgets.add(Widgets.createTexturedWidget(CompressorScreen.TEXTURE, bounds.x, bounds.y + 110, 0, 189, 177, 7, 177, 196));

        widgets.add(Widgets.createSlot(new Point(bounds.x + 53, bounds.y + 56)).backgroundEnabled(false).entries(display.getInputEntries().get(0)).markInput());
        widgets.add(Widgets.createSlot(new Point(bounds.x + 100, bounds.y + 56)).backgroundEnabled(false).entries(display.getOutputEntries().get(0)).markOutput());

        widgets.add(new EtaWidget(new Point(bounds.x + 78, bounds.y + 57), display.recipe().cookingTime(), GuiUtils.HAMMER, 13, 13));
        widgets.add(new EnergyBarWidget(new Point(bounds.x + 141, bounds.y + 61), -display.recipe().energy(), 10_000, 2_000, 0));

        return widgets;
    }
}
