package com.github.alexnijjar.ad_astra.recipes;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import com.github.alexnijjar.ad_astra.util.ModInventory;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public abstract class ModRecipe implements Recipe<Inventory>, Predicate<ItemStack> {

    protected Identifier id;
    protected final List<Ingredient> inputs = new ArrayList<>();
    protected List<Integer> stackCounts;

    public ModRecipe(Identifier id) {
        this.id = id;
    }

    public ModRecipe(Identifier id, Ingredient input) {
        this.id = id;
        this.inputs.add(input);
    }

    public ModRecipe(Identifier id, List<Ingredient> input, List<Integer> stackCounts) {
        this.id = id;
        this.inputs.addAll(input);
        this.stackCounts = stackCounts;
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
    public ItemStack getOutput() {
        // Unused.
        return ItemStack.EMPTY;
    }

    @Override
    public boolean fits(int width, int height) {
        // Unused.
        return true;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> defaultedList = DefaultedList.of();
        defaultedList.addAll(this.inputs);
        return defaultedList;
    }

    public List<Integer> getStackCounts() {
        return this.stackCounts;
    }

    // Checks if the stack matches the recipe inputs.
    @Override
    public boolean test(ItemStack itemStack) {
        for (Ingredient input : this.inputs) {
            if (input.test(itemStack)) {
                return true;
            }
        }
        return false;
    }

    // Tests if everything in the inventory matches the recipe in the correct order.
    public boolean test(ModInventory inventory) {

        for (int i = 0; i < this.inputs.size(); i++) {
            if (!inputs.get(i).test(inventory.getItems().get(i))) {
                return false;
            }
        }

        return true;
    }
}