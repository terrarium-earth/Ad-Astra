package com.github.alexnijjar.ad_astra.compat.emi.recipes;

import java.util.List;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.compat.emi.EmiCategories;
import com.github.alexnijjar.ad_astra.compat.emi.EmiTextures;
import com.github.alexnijjar.ad_astra.recipes.FuelConversionRecipe;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.stack.FluidEmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.Identifier;

public class EmiFuelConversionRecipe implements EmiRecipe {
    private final Identifier id;
    FuelConversionRecipe recipe;
    private final FluidEmiStack input;
    private final FluidEmiStack output;

    public EmiFuelConversionRecipe(FuelConversionRecipe recipe) {
        this.id = recipe.getId();
        this.recipe = recipe;
        this.input = new FluidEmiStack(FluidVariant.of(recipe.getFluidInput()), FluidConstants.BUCKET);
        this.output = new FluidEmiStack(FluidVariant.of(recipe.getFluidOutput()), FluidConstants.BUCKET);
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return EmiCategories.FUEL_CONVERSION_CATEGORY;
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
        return List.of(this.output);
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

        widgets.addFillingArrow(26 + xOffset, 1 + yOffset, 20 * 50);

        EmiTextures.createFluidWidget(widgets, -10 + xOffset, -17 + yOffset, FluidVariant.of((Fluid)this.input.getKey()), 150 * 50, false);
        EmiTextures.createFluidWidget(widgets, 60 + xOffset, -17 + yOffset, FluidVariant.of((Fluid) this.output.getKey()), 150 * 50, true);
        
        EmiTextures.createEnergyWidget(widgets, 100 + xOffset, -20 + yOffset, (int) AdAstra.CONFIG.fuelRefinery.energyPerTick, false);
    }
}
