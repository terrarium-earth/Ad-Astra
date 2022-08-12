package com.github.alexnijjar.ad_astra.recipes;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

public abstract class ConversionRecipe extends ModRecipe {

    private Fluid input;
    private ItemStack itemInput;
    private Fluid output;
    private double conversionRatio;

    public ConversionRecipe(Identifier id, Fluid input, Fluid output, double conversionRatio) {
        super(id);
        this.input = input;
        this.output = output;
        this.conversionRatio = conversionRatio;
    }

    public ConversionRecipe(Identifier id, ItemStack input, Fluid output, double conversionRatio) {
        super(id);
        this.inputs.add(Ingredient.ofStacks(input));
        this.itemInput = input;
        this.output = output;
        this.conversionRatio = conversionRatio;
    }

    public Fluid getFluidInput() {
        return this.input;
    }

    public ItemStack getItemInput() {
        return this.itemInput;
    }

    public Fluid getFluidOutput() {
        return this.output;
    }

    public double getConversionRatio() {
        return this.conversionRatio;
    }
}