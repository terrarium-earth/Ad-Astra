package earth.terrarium.adastra.common.compat.rei.categories;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.screens.machines.CryoFreezerScreen;
import earth.terrarium.adastra.client.utils.GuiUtils;
import earth.terrarium.adastra.common.compat.rei.displays.CryoFreezingDisplay;
import earth.terrarium.adastra.common.compat.rei.widgets.EnergyBarWidget;
import earth.terrarium.adastra.common.compat.rei.widgets.EtaWidget;
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

public class CryoFreezingCategory implements DisplayCategory<CryoFreezingDisplay> {
    public static final CategoryIdentifier<CryoFreezingDisplay> ID = CategoryIdentifier.of(AdAstra.MOD_ID, "cryo_freezing");

    @Override
    public CategoryIdentifier<? extends CryoFreezingDisplay> getCategoryIdentifier() {
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
    public int getDisplayWidth(CryoFreezingDisplay display) {
        return 179;
    }

    @Override
    public int getDisplayHeight() {
        return 102;
    }

    @Override
    public List<Widget> setupDisplay(CryoFreezingDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();

        widgets.add(Widgets.createTexturedWidget(CryoFreezerScreen.TEXTURE, bounds.x, bounds.y, 0, 0, 177, 100, 177, 181));
        widgets.add(Widgets.createTexturedWidget(CryoFreezerScreen.TEXTURE, bounds.x, bounds.y + 95, 0, 177, 177, 7, 177, 181));

        widgets.add(Widgets.createSlot(new Point(bounds.x + 26, bounds.y + 70)).backgroundEnabled(false).entries(display.getInputEntries().get(0)).markInput());
        widgets.add(Widgets.createSlot(new Point(bounds.x + 113, bounds.y + 42)).backgroundEnabled(false));
        widgets.add(Widgets.createSlot(new Point(bounds.x + 113, bounds.y + 70)).backgroundEnabled(false));

        widgets.add(new EnergyBarWidget(new Point(bounds.x + 143, bounds.y + 58), -display.recipe().energy(), 100_000, 2_000, 0));

        widgets.add(new EtaWidget(new Point(bounds.x + 54, bounds.y + 71), display.recipe().cookingTime(), GuiUtils.SNOWFLAKE, 13, 13));
        int cookTime = display.recipe().cookingTime();
        long capacity = FluidHooks.buckets(6);
        widgets.add(new FluidBarWidget(new Point(bounds.x + 80, bounds.y + 69), true, capacity, cookTime, display.recipe().resultFluid()));

        return widgets;
    }
}
