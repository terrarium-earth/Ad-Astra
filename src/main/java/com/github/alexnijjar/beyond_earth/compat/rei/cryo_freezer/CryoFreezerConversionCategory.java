package com.github.alexnijjar.beyond_earth.compat.rei.cryo_freezer;

import java.util.ArrayList;
import java.util.List;

import com.github.alexnijjar.beyond_earth.blocks.machines.entity.CryoFreezerBlockEntity;
import com.github.alexnijjar.beyond_earth.compat.rei.REICategories;
import com.github.alexnijjar.beyond_earth.compat.rei.widgets.EnergyBarWidget;
import com.github.alexnijjar.beyond_earth.compat.rei.widgets.FluidBarWidget;
import com.github.alexnijjar.beyond_earth.registry.ModBlocks;

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
import net.minecraft.text.TranslatableText;

@Environment(EnvType.CLIENT)
public class CryoFreezerConversionCategory implements DisplayCategory<CryoFreezerConversionDisplay> {

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.CRYO_FREEZER.asItem());
    }

    @Override
    public Text getTitle() {
        return new TranslatableText(ModBlocks.CRYO_FREEZER.getTranslationKey());
    }

    @Override
    public int getDisplayWidth(CryoFreezerConversionDisplay display) {
        return 147;
    }

    @Override
    public int getDisplayHeight() {
        return 80;
    }

    @Override
    public CategoryIdentifier<? extends CryoFreezerConversionDisplay> getCategoryIdentifier() {
        return REICategories.CRYO_FREEZER_CONVERSION_CATEGORY;
    }

    @Override
    public List<Widget> setupDisplay(CryoFreezerConversionDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 60, bounds.getCenterY() - 35);

        List<Widget> widgets = new ArrayList<>();
        List<EntryIngredient> inputs = display.getInputEntries();
        List<EntryIngredient> outputs = display.getOutputEntries();

        widgets.add(Widgets.createRecipeBase(bounds));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 4, startPoint.y + 15)).entries(inputs.get(0)).markInput());

        widgets.add(Widgets.createArrow(new Point(startPoint.x + 30, startPoint.y + 15)).animationDurationTicks(20));

        Widget fluidWidget2 = new FluidBarWidget(new Point(startPoint.x + 70, startPoint.y - 0), true, FluidVariant.of(((FluidStack) outputs.get(0).get(0).getValue()).getFluid())).animationDurationTicks(150 / display.recipe().getConversionRatio());
        widgets.add(fluidWidget2);
        widgets.add(Widgets.withTooltip(Widgets.withBounds(fluidWidget2, bounds), new TranslatableText(((FluidStack) outputs.get(0).get(0).getValue()).getTranslationKey())));

        Text ratioText = new TranslatableText("rei.text.beyond_earth.amount", 1000 * display.recipe().getConversionRatio());
        widgets.add(Widgets.createLabel(new Point(startPoint.x + 60, startPoint.y + 60), ratioText).centered().noShadow().color(0xFF404040, 0xFFBBBBBB));

        Widget widget = new EnergyBarWidget(new Point(startPoint.x + 90, startPoint.y), false).animationDurationTicks(150);
        widgets.add(widget);
        widgets.add(Widgets.withTooltip(Widgets.withBounds(widget, bounds), new TranslatableText("rei.tooltip.beyond_earth.energy_using", CryoFreezerBlockEntity.ENERGY_PER_TICK)));

        return widgets;
    }
}