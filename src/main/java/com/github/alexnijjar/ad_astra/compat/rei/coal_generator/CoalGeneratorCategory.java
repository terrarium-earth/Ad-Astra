package com.github.alexnijjar.ad_astra.compat.rei.coal_generator;

import java.util.ArrayList;
import java.util.List;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.compat.rei.REICategories;
import com.github.alexnijjar.ad_astra.compat.rei.widgets.EnergyBarWidget;
import com.github.alexnijjar.ad_astra.registry.ModBlocks;

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
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

@Environment(EnvType.CLIENT)
public class CoalGeneratorCategory implements DisplayCategory<CoalGeneratorDisplay> {

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.COAL_GENERATOR.asItem());
    }

    @Override
    public Text getTitle() {
        return new TranslatableText(ModBlocks.COAL_GENERATOR.getTranslationKey());
    }

    @Override
    public int getDisplayWidth(CoalGeneratorDisplay display) {
        return 144;
    }

    @Override
    public int getDisplayHeight() {
        return 90;
    }

    @Override
    public CategoryIdentifier<? extends CoalGeneratorDisplay> getCategoryIdentifier() {
        return REICategories.COAL_GENERATOR_CATEGORY;
    }

    @Override
    public List<Widget> setupDisplay(CoalGeneratorDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 30, bounds.getCenterY() - 20);
        short cookTime = display.recipe().getCookTime();

        List<Widget> widgets = new ArrayList<>();
        List<EntryIngredient> inputs = display.getInputEntries();

        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createSlot(startPoint).entries(inputs.get(0)).markInput());
        widgets.add(Widgets.createBurningFire(new Point(startPoint.x + 1, startPoint.y + 20)).animationDurationTicks(cookTime));

        Widget widget = new EnergyBarWidget(new Point(startPoint.x + 80, startPoint.y - 13), true).animationDurationTicks(150);
        widgets.add(widget);
        widgets.add(Widgets.withTooltip(Widgets.withBounds(widget, bounds), new TranslatableText("rei.tooltip.ad_astra.energy_generating", AdAstra.CONFIG.coalGenerator.energyPerTick)));

        Text ratioText = new TranslatableText("rei.text.ad_astra.generates", display.recipe().getCookTime() * AdAstra.CONFIG.coalGenerator.energyPerTick);
        widgets.add(Widgets.createLabel(new Point(startPoint.x + 30, startPoint.y + 45), ratioText).centered().noShadow().color(0xFF404040, 0xFFBBBBBB));

        return widgets;
    }
}