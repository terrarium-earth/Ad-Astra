package com.github.alexnijjar.ad_astra.compat.emi.recipes;

import java.util.List;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.compat.emi.EmiCategories;
import com.github.alexnijjar.ad_astra.compat.emi.EmiTextures;
import com.github.alexnijjar.ad_astra.recipes.GeneratingRecipe;

import dev.emi.emi.EmiPort;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.text.OrderedText;
import net.minecraft.util.Identifier;

public class EmiGeneratingRecipe implements EmiRecipe {
    private final Identifier id;
    GeneratingRecipe recipe;
    private final EmiIngredient input;

    public EmiGeneratingRecipe(GeneratingRecipe recipe) {
        this.id = recipe.getId();
        this.recipe = recipe;
        this.input = EmiIngredient.of(recipe.getIngredients().get(0));
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return EmiCategories.COAL_GENERATOR_CATEGORY;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return List.of(this.input);
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of();
    }

    @Override
    public int getDisplayWidth() {
        return 144;
    }

    @Override
    public int getDisplayHeight() {
        return 90;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        int xOffset = 20;
        int yOffset = 30;

        widgets.addSlot(this.input, 35 + xOffset, 0 + yOffset);

        widgets.addTexture(EmiTexture.EMPTY_FLAME, 36 + xOffset, 19 + yOffset);
		widgets.addAnimatedTexture(EmiTexture.FULL_FLAME, 36 + xOffset, 19 + yOffset, this.recipe.getCookTime() * 50, false, true, true);

        EmiTextures.createEnergyWidget(widgets, 100 + xOffset, -20 + yOffset, (int) AdAstra.CONFIG.coalGenerator.energyPerTick, true);

        OrderedText ratioText = EmiPort.ordered(EmiPort.translatable("rei.text.ad_astra.generates", this.recipe.getCookTime() * AdAstra.CONFIG.coalGenerator.energyPerTick));
        widgets.addText(ratioText, -17 + xOffset, 50 + yOffset, 0xFF404040, false);
    }
}
