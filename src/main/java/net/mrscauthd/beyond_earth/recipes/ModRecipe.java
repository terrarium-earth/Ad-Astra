package net.mrscauthd.beyond_earth.recipes;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.function.Predicate;

public abstract class ModRecipe implements Recipe<Inventory>, Predicate<ItemStack> {

    protected Identifier id;
    protected Ingredient input;
    protected ItemStack output;
    protected short cookTime;

    public ModRecipe(Identifier id, Ingredient input, ItemStack output, short cookTime) {
        this.id = id;
        this.input = input;
        this.output = output;
        this.cookTime = cookTime;
    }


    @Override
    public boolean matches(Inventory inventory, World world) {
        // Unused.
        return true;
    }

    @Override
    public ItemStack craft(Inventory inventory) {
        // Unused.
        return ItemStack.EMPTY;
    }

    @Override
    public boolean fits(int width, int height) {
        // Unused.
        return true;
    }

    @Override
    public ItemStack getOutput() {
        return this.output.copy();
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public boolean test(ItemStack itemStack) {
        return this.input.test(itemStack);
    }

    public Ingredient getInputIngredient() {
        return this.input;
    }

    public short getCookTime() {
        return this.cookTime;
    }
}
