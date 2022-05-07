package net.mrscauthd.beyond_earth.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

public abstract class CookingRecipe extends ModRecipe {

    protected ItemStack output;
    protected short cookTime;

    public CookingRecipe(Identifier id, Ingredient input, ItemStack output, short cookTime) {
        super(id, input);
        this.output = output;
        this.cookTime = cookTime;
    }

    @Override
    public ItemStack getOutput() {
        return this.output.copy();
    }

    public Ingredient getInputIngredient() {
        return this.inputs[0];
    }

    public short getCookTime() {
        return this.cookTime;
    }
}