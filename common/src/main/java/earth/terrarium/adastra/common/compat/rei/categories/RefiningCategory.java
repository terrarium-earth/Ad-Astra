package earth.terrarium.adastra.common.compat.rei.categories;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.screens.machines.OxygenLoaderScreen;
import earth.terrarium.adastra.common.compat.rei.displays.RefiningDisplay;
import earth.terrarium.adastra.common.compat.rei.widgets.EnergyBarWidget;
import earth.terrarium.adastra.common.compat.rei.widgets.FluidBarWidget;
import earth.terrarium.adastra.common.registry.ModBlocks;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
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

public class RefiningCategory implements DisplayCategory<RefiningDisplay> {
    public static final CategoryIdentifier<RefiningDisplay> ID = CategoryIdentifier.of(AdAstra.MOD_ID, "fuel_refining");

    @Override
    public CategoryIdentifier<? extends RefiningDisplay> getCategoryIdentifier() {
        return ID;
    }

    @Override
    public Component getTitle() {
        return Component.translatable(ModBlocks.OXYGEN_LOADER.get().getDescriptionId());
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.OXYGEN_LOADER.get());
    }

    @Override
    public int getDisplayWidth(RefiningDisplay display) {
        return 179;
    }

    @Override
    public int getDisplayHeight() {
        return 117;
    }

    @Override
    public List<Widget> setupDisplay(RefiningDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();

        widgets.add(Widgets.createTexturedWidget(OxygenLoaderScreen.TEXTURE, bounds.x, bounds.y, 0, 0, 177, 100, 177, 184));
        widgets.add(Widgets.createTexturedWidget(OxygenLoaderScreen.TEXTURE, bounds.x, bounds.y + 100, 0, 177, 177, 7, 177, 184));

        widgets.add(Widgets.createSlot(new Point(bounds.x + 12, bounds.y + 22)).backgroundEnabled(false));
        widgets.add(Widgets.createSlot(new Point(bounds.x + 12, bounds.y + 52)).backgroundEnabled(false));
        widgets.add(Widgets.createSlot(new Point(bounds.x + 127, bounds.y + 22)).backgroundEnabled(false));
        widgets.add(Widgets.createSlot(new Point(bounds.x + 127, bounds.y + 52)).backgroundEnabled(false));

        widgets.add(new EnergyBarWidget(new Point(bounds.x + 144, bounds.y + 54), -display.recipe().energy(), 20_000, 500, 0));

        int cookTime = display.recipe().cookingTime();
        long capacity = FluidHooks.buckets(6);
        widgets.add(new FluidBarWidget(new Point(bounds.x + 37, bounds.y + 53), false, capacity, cookTime, display.recipe().ingredient()));
        widgets.add(new FluidBarWidget(new Point(bounds.x + 94, bounds.y + 53), true, capacity, cookTime, display.recipe().resultFluid()));

        return widgets;
    }
}
