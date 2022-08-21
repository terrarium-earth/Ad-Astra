package com.github.alexnijjar.beyond_earth.recipes;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

public abstract class CookingRecipe extends ModRecipe {

    private ItemStack output;
    private short cookTime;

    public CookingRecipe(Identifier id, Ingredient input, ItemStack output, short cookTime) {
        super(id, input);
        this.output = output;
        this.cookTime = cookTime;
    }

    public CookingRecipe(Identifier id, List<Ingredient> input, List<Integer> stackCounts, ItemStack output) {
        super(id, input, stackCounts);
        this.output = output;
    }

    @Override
    public ItemStack getOutput() {
        return this.output.copy();
    }

    public Ingredient getInputIngredient() {
        return this.inputs.get(0);
    }

    public short getCookTime() {
        return this.cookTime;
    }
}