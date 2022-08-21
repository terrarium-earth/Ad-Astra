package com.github.alexnijjar.ad_astra.compat.rei.fuel_conversion;

import java.util.ArrayList;
import java.util.List;

import com.github.alexnijjar.ad_astra.blocks.machines.entity.FuelRefineryBlockEntity;
import com.github.alexnijjar.ad_astra.compat.rei.REICategories;
import com.github.alexnijjar.ad_astra.compat.rei.widgets.EnergyBarWidget;
import com.github.alexnijjar.ad_astra.compat.rei.widgets.FluidBarWidget;
import com.github.alexnijjar.ad_astra.registry.ModBlocks;

import dev.architectury.fluid.FluidStack;
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
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class FuelConversionCategory implements DisplayCategory<FuelConversionDisplay> {

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.FUEL_REFINERY.asItem());
    }

    @Override
    public Text getTitle() {
        return Text.translatable(ModBlocks.FUEL_REFINERY.getTranslationKey());
    }

    @Override
    public int getDisplayWidth(FuelConversionDisplay display) {
        return 147;
    }

    @Override
    public int getDisplayHeight() {
        return 80;
    }

    @Override
    public CategoryIdentifier<? extends FuelConversionDisplay> getCategoryIdentifier() {
        return REICategories.FUEL_CONVERSION_CATEGORY;
    }

    @Override
    public List<Widget> setupDisplay(FuelConversionDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 60, bounds.getCenterY() - 35);

        List<Widget> widgets = new ArrayList<>();
        List<EntryIngredient> inputs = display.getInputEntries();
        List<EntryIngredient> outputs = display.getOutputEntries();

        widgets.add(Widgets.createRecipeBase(bounds));

        Widget fluidWidget1 = new FluidBarWidget(startPoint, false, FluidVariant.of(((FluidStack) inputs.get(0).get(0).getValue()).getFluid())).animationDurationTicks(150);
        widgets.add(fluidWidget1);
        widgets.add(Widgets.withTooltip(Widgets.withBounds(fluidWidget1, bounds), Text.translatable(((FluidStack) inputs.get(0).get(0).getValue()).getTranslationKey())));

        widgets.add(Widgets.createArrow(new Point(startPoint.x + 30, startPoint.y + 15)).animationDurationTicks(20));

        Widget fluidWidget2 = new FluidBarWidget(new Point(startPoint.x + 70, startPoint.y - 0), true, FluidVariant.of(((FluidStack) outputs.get(0).get(0).getValue()).getFluid())).animationDurationTicks(150 / display.recipe().getConversionRatio());
        widgets.add(fluidWidget2);
        widgets.add(Widgets.withTooltip(Widgets.withBounds(fluidWidget2, bounds), Text.translatable(((FluidStack) outputs.get(0).get(0).getValue()).getTranslationKey())));

        Text ratioText = Text.translatable("rei.text.ad_astra.conversion_ratio", display.recipe().getConversionRatio() * 100.0);
        widgets.add(Widgets.createLabel(new Point(startPoint.x + 60, startPoint.y + 60), ratioText).centered().noShadow().color(0xFF404040, 0xFFBBBBBB));

        Widget widget = new EnergyBarWidget(new Point(startPoint.x + 90, startPoint.y), false).animationDurationTicks(150);
        widgets.add(widget);
        widgets.add(Widgets.withTooltip(Widgets.withBounds(widget, bounds), Text.translatable("rei.tooltip.ad_astra.energy_using", FuelRefineryBlockEntity.ENERGY_PER_TICK)));

        return widgets;
    }
}