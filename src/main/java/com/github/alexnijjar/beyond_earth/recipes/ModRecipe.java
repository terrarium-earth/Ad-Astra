package com.github.alexnijjar.beyond_earth.recipes;

import java.util.List;
import java.util.function.Predicate;

import com.github.alexnijjar.beyond_earth.util.SimpleInventory;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public abstract class ModRecipe implements Recipe<Inventory>, Predicate<ItemStack> {

    protected Identifier id;
    protected Ingredient[] inputs = new Ingredient[1];
    protected List<Integer> stackCounts;

    public ModRecipe(Identifier id) {
        this.id = id;
    }
    
    public ModRecipe(Identifier id, Ingredient input) {
        this.id = id;
        this.inputs[0] = input;
    }

    public ModRecipe(Identifier id, Ingredient[] input, List<Integer> stackCounts) {
        this.id = id;
        this.inputs = input;
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

    public Ingredient[] getInputs() {
        return this.inputs;
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
    public boolean test(SimpleInventory inventory) {

        for (int i = 0; i < this.inputs.length; i++) {
            if (!inputs[i].test(inventory.getItems().get(i))) {
                return false;
            }
        }
        
        return true;
    }
}