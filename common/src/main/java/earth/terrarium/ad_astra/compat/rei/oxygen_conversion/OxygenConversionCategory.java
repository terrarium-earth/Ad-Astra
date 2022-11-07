package earth.terrarium.ad_astra.compat.rei.oxygen_conversion;

import dev.architectury.fluid.FluidStack;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.compat.rei.REICategories;
import earth.terrarium.ad_astra.compat.rei.widgets.EnergyBarWidget;
import earth.terrarium.ad_astra.compat.rei.widgets.FluidBarWidget;
import earth.terrarium.ad_astra.registry.ModBlocks;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class OxygenConversionCategory implements DisplayCategory<OxygenConversionDisplay> {

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.OXYGEN_LOADER.get().asItem());
    }

    @Override
    public Component getTitle() {
        return Component.translatable(ModBlocks.OXYGEN_LOADER.get().getDescriptionId());
    }

    @Override
    public int getDisplayWidth(OxygenConversionDisplay display) {
        return 144;
    }

    @Override
    public int getDisplayHeight() {
        return 90;
    }

    @Override
    public CategoryIdentifier<? extends OxygenConversionDisplay> getCategoryIdentifier() {
        return REICategories.OXYGEN_CONVERSION_CATEGORY;
    }

    @Override
    public List<Widget> setupDisplay(OxygenConversionDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 60, bounds.getCenterY() - 35);

        List<Widget> widgets = new ArrayList<>();
        List<EntryIngredient> inputs = display.getInputEntries();
        List<EntryIngredient> outputs = display.getOutputEntries();

        widgets.add(Widgets.createRecipeBase(bounds));

        Widget fluidWidget1 = new FluidBarWidget(startPoint, false, FluidHooks.newFluidHolder((((FluidStack) inputs.get(0).get(0).getValue()).getFluid()), FluidHooks.buckets(1), null)).animationDurationTicks(150);
        widgets.add(fluidWidget1);
        widgets.add(Widgets.withTooltip(Widgets.withBounds(fluidWidget1, bounds), Component.translatable(((FluidStack) inputs.get(0).get(0).getValue()).getTranslationKey())));

        widgets.add(Widgets.createArrow(new Point(startPoint.x + 30, startPoint.y + 15)).animationDurationTicks(20));

        Widget fluidWidget2 = new FluidBarWidget(new Point(startPoint.x + 70, startPoint.y), true, FluidHooks.newFluidHolder(((FluidStack) outputs.get(0).get(0).getValue()).getFluid(), FluidHooks.buckets(1), null)).animationDurationTicks(150 / display.recipe().getConversionRatio());
        widgets.add(fluidWidget2);
        widgets.add(Widgets.withTooltip(Widgets.withBounds(fluidWidget2, bounds), Component.translatable(((FluidStack) outputs.get(0).get(0).getValue()).getTranslationKey())));

        Component ratioText = Component.translatable("rei.text.ad_astra.conversion_ratio", display.recipe().getConversionRatio() * 100.0);
        widgets.add(Widgets.createLabel(new Point(startPoint.x + 60, startPoint.y + 60), ratioText).centered().noShadow().color(0xFF404040, 0xFFBBBBBB));

        Widget widget = new EnergyBarWidget(new Point(startPoint.x + 110, startPoint.y + 2), false).animationDurationTicks(150);
        widgets.add(widget);

        widgets.add(Widgets.withTooltip(Widgets.withBounds(widget, bounds), Component.translatable("rei.tooltip.ad_astra.energy_using", AdAstra.CONFIG.oxygenLoader.energyPerTick)));

        return widgets;
    }
}