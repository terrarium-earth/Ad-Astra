package earth.terrarium.adastra.common.compat.rei.categories;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.screens.machines.EtrionicBlastFurnaceScreen;
import earth.terrarium.adastra.client.utils.GuiUtils;
import earth.terrarium.adastra.common.compat.rei.displays.AlloyingDisplay;
import earth.terrarium.adastra.common.compat.rei.widgets.EnergyBarWidget;
import earth.terrarium.adastra.common.compat.rei.widgets.EtaWidget;
import earth.terrarium.adastra.common.config.MachineConfig;
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

public class AlloyingCategory implements DisplayCategory<AlloyingDisplay> {
    public static final CategoryIdentifier<AlloyingDisplay> ID = CategoryIdentifier.of(AdAstra.MOD_ID, "alloying");

    @Override
    public CategoryIdentifier<? extends AlloyingDisplay> getCategoryIdentifier() {
        return ID;
    }

    @Override
    public Component getTitle() {
        return Component.translatable(ModBlocks.ETRIONIC_BLAST_FURNACE.get().getDescriptionId());
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.ETRIONIC_BLAST_FURNACE.get());
    }

    @Override
    public int getDisplayWidth(AlloyingDisplay display) {
        return 184;
    }

    @Override
    public int getDisplayHeight() {
        return 117;
    }

    @Override
    public List<Widget> setupDisplay(AlloyingDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();

        widgets.add(Widgets.createTexturedWidget(EtrionicBlastFurnaceScreen.TEXTURE, bounds.x, bounds.y, 0, 0, 184, 110, 184, 201));
        widgets.add(Widgets.createTexturedWidget(EtrionicBlastFurnaceScreen.TEXTURE, bounds.x, bounds.y + 110, 0, 194, 184, 7, 184, 201));
        widgets.add(Widgets.createTexturedWidget(EtrionicBlastFurnaceScreen.FURNACE_OVERLAY, bounds.x + 30, bounds.y + 52, 0, 0, 32, 43, 32, 43));

        widgets.add(Widgets.createSlot(new Point(bounds.x + 29, bounds.y + 38)).backgroundEnabled(false).entries(display.getInputEntries().get(0)).markInput());
        widgets.add(Widgets.createSlot(new Point(bounds.x + 47, bounds.y + 38)).backgroundEnabled(false).entries(display.getInputEntries().get(1)).markInput());
        if (display.getInputEntries().size() > 2) {
            widgets.add(Widgets.createSlot(new Point(bounds.x + 29, bounds.y + 58)).backgroundEnabled(false).entries(display.getInputEntries().get(2)).markInput());
        } else {
            widgets.add(Widgets.createSlot(new Point(bounds.x + 29, bounds.y + 58)).backgroundEnabled(false));
        }
        if (display.getInputEntries().size() > 3) {
            widgets.add(Widgets.createSlot(new Point(bounds.x + 47, bounds.y + 58)).backgroundEnabled(false).entries(display.getInputEntries().get(3)).markInput());
        } else {
            widgets.add(Widgets.createSlot(new Point(bounds.x + 47, bounds.y + 58)).backgroundEnabled(false));
        }

        widgets.add(Widgets.createSlot(new Point(bounds.x + 101, bounds.y + 38)).backgroundEnabled(false).entries(display.getOutputEntries().get(0)).markOutput());
        widgets.add(Widgets.createSlot(new Point(bounds.x + 119, bounds.y + 38)).backgroundEnabled(false));
        widgets.add(Widgets.createSlot(new Point(bounds.x + 101, bounds.y + 57)).backgroundEnabled(false));
        widgets.add(Widgets.createSlot(new Point(bounds.x + 119, bounds.y + 57)).backgroundEnabled(false));

        widgets.add(new EtaWidget(new Point(bounds.x + 75, bounds.y + 50), display.recipe().cookingTime(), GuiUtils.ARROW, 20, 12));
        widgets.add(new EnergyBarWidget(new Point(bounds.x + 146, bounds.y + 67), -display.recipe().energy(), MachineConfig.steelTierEnergyCapacity, MachineConfig.steelTierMaxEnergyInOut, 0));

        return widgets;
    }
}
