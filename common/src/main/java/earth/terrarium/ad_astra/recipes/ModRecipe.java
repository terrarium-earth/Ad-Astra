package earth.terrarium.ad_astra.recipes;

import earth.terrarium.ad_astra.util.ModInventory;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class ModRecipe implements Recipe<Container>, Predicate<ItemStack> {

    protected final ResourceLocation id;
    protected final List<Ingredient> inputs = new ArrayList<>();
    protected List<Integer> stackCounts;

    public ModRecipe(ResourceLocation id) {
        this.id = id;
    }

    public ModRecipe(ResourceLocation id, Ingredient input) {
        this.id = id;
        this.inputs.add(input);
    }

    public ModRecipe(ResourceLocation id, List<Ingredient> input, List<Integer> stackCounts) {
        this.id = id;
        this.inputs.addAll(input);
        this.stackCounts = stackCounts;
    }

    @Override
    public boolean matches(Container inventory, Level level) {
        // Unused.
        return true;
    }

    @Override
    public ItemStack assemble(Container inventory) {
        // Unused.
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getResultItem() {
        // Unused.
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        // Unused.
        return true;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> defaultedList = NonNullList.create();
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