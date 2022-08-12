package com.github.alexnijjar.ad_astra.compat.rei.nasa_workbench;

import com.github.alexnijjar.ad_astra.compat.rei.REICategories;
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

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class NasaWorkbenchCategory implements DisplayCategory<NasaWorkbenchDisplay> {

	@Override
	public Renderer getIcon() {
		return EntryStacks.of(ModBlocks.NASA_WORKBENCH.asItem());
	}

	@Override
	public Text getTitle() {
		return new TranslatableText(ModBlocks.NASA_WORKBENCH.getTranslationKey());
	}

	@Override
	public int getDisplayWidth(NasaWorkbenchDisplay display) {
		return 176;
	}

	@Override
	public int getDisplayHeight() {
		return 130;
	}

	@Override
	public CategoryIdentifier<? extends NasaWorkbenchDisplay> getCategoryIdentifier() {
		return REICategories.NASA_WORKBENCH_CATEGORY;
	}

	@Override
	public List<Widget> setupDisplay(NasaWorkbenchDisplay display, Rectangle bounds) {
		Point startPoint = new Point(bounds.getCenterX() - 45, bounds.getCenterY() - 55);

		List<Widget> widgets = new ArrayList<>();
		List<EntryIngredient> inputs = display.getInputEntries();
		List<EntryIngredient> outputs = display.getOutputEntries();

		widgets.add(Widgets.createRecipeBase(bounds));

		widgets.add(Widgets.createSlot(startPoint).entries(inputs.get(0)).markInput());
		widgets.add(Widgets.createSlot(new Point(startPoint.x - 9, startPoint.y + 18)).entries(inputs.get(1)).markInput());
		widgets.add(Widgets.createSlot(new Point(startPoint.x + 9, startPoint.y + 18)).entries(inputs.get(2)).markInput());
		widgets.add(Widgets.createSlot(new Point(startPoint.x - 9, startPoint.y + 18 * 2)).entries(inputs.get(3)).markInput());
		widgets.add(Widgets.createSlot(new Point(startPoint.x + 9, startPoint.y + 18 * 2)).entries(inputs.get(4)).markInput());
		widgets.add(Widgets.createSlot(new Point(startPoint.x - 9, startPoint.y + 18 * 3)).entries(inputs.get(5)).markInput());
		widgets.add(Widgets.createSlot(new Point(startPoint.x + 9, startPoint.y + 18 * 3)).entries(inputs.get(6)).markInput());
		widgets.add(Widgets.createSlot(new Point(startPoint.x - 27, startPoint.y + 18 * 4)).entries(inputs.get(7)).markInput());
		widgets.add(Widgets.createSlot(new Point(startPoint.x - 9, startPoint.y + 18 * 4)).entries(inputs.get(8)).markInput());
		widgets.add(Widgets.createSlot(new Point(startPoint.x + 9, startPoint.y + 18 * 4)).entries(inputs.get(9)).markInput());
		widgets.add(Widgets.createSlot(new Point(startPoint.x + 27, startPoint.y + 18 * 4)).entries(inputs.get(10)).markInput());
		widgets.add(Widgets.createSlot(new Point(startPoint.x - 27, startPoint.y + 18 * 5)).entries(inputs.get(11)).markInput());
		widgets.add(Widgets.createSlot(new Point(startPoint.x, startPoint.y + 18 * 5)).entries(inputs.get(12)).markInput());
		widgets.add(Widgets.createSlot(new Point(startPoint.x + 27, startPoint.y + 18 * 5)).entries(inputs.get(13)).markInput());

		widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 90, startPoint.y + 70)));

		widgets.add(Widgets.createArrow(new Point(startPoint.x + 53, startPoint.y + 70)));

		widgets.add(Widgets.createSlot(new Point(startPoint.x + 90, startPoint.y + 70)).entries(outputs.get(0)).disableBackground().markOutput());

		return widgets;
	}
}