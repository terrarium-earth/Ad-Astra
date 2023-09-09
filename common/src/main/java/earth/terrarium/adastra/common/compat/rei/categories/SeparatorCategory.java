package earth.terrarium.adastra.common.compat.rei.categories;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.screens.SeparatorScreen;
import earth.terrarium.adastra.common.compat.rei.displays.SeparatorDisplay;
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

public class SeparatorCategory implements DisplayCategory<SeparatorDisplay> {
    public static final CategoryIdentifier<SeparatorDisplay> ID = CategoryIdentifier.of(AdAstra.MOD_ID, "separator");

    @Override
    public CategoryIdentifier<? extends SeparatorDisplay> getCategoryIdentifier() {
        return ID;
    }

    @Override
    public Component getTitle() {
        return Component.translatable(ModBlocks.SEPARATOR.get().getDescriptionId());
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.SEPARATOR.get());
    }

    @Override
    public int getDisplayWidth(SeparatorDisplay display) {
        return 144;
    }

    @Override
    public int getDisplayHeight() {
        return 139;
    }

    @Override
    public List<Widget> setupDisplay(SeparatorDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();

        widgets.add(Widgets.createTexturedWidget(SeparatorScreen.TEXTURE, bounds.x - 20, bounds.y - 2, 0, 33, 184, 134, 184, 255));
        widgets.add(Widgets.createTexturedWidget(SeparatorScreen.TEXTURE, bounds.x - 20, bounds.y + 132, 0, 246, 184, 9, 184, 255));

        widgets.add(new EnergyBarWidget(new Point(bounds.x + 126, bounds.y + 68), -display.recipe().energy(), 100_000, 2_500, 0));

        int cookTime = display.recipe().cookingTime();
        long capacity = FluidHooks.buckets(10);
        widgets.add(new FluidBarWidget(new Point(bounds.x + 42, bounds.y + 58), false, capacity, cookTime, display.recipe().ingredient()));
        widgets.add(new FluidBarWidget(new Point(bounds.x + 2, bounds.y + 70), true, capacity, cookTime, display.recipe().resultFluid1()));
        widgets.add(new FluidBarWidget(new Point(bounds.x + 82, bounds.y + 70), true, capacity, cookTime, display.recipe().resultFluid2()));

        widgets.add(Widgets.createSlot(new Point(bounds.x - 1, bounds.y + 103)).backgroundEnabled(false));
        widgets.add(Widgets.createSlot(new Point(bounds.x + 17, bounds.y + 103)).backgroundEnabled(false));
        widgets.add(Widgets.createSlot(new Point(bounds.x + 39, bounds.y + 103)).backgroundEnabled(false));
        widgets.add(Widgets.createSlot(new Point(bounds.x + 57, bounds.y + 103)).backgroundEnabled(false));
        widgets.add(Widgets.createSlot(new Point(bounds.x + 79, bounds.y + 103)).backgroundEnabled(false));
        widgets.add(Widgets.createSlot(new Point(bounds.x + 97, bounds.y + 103)).backgroundEnabled(false));

        return widgets;
    }
}
