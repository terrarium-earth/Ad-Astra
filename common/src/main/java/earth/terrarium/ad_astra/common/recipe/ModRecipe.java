package earth.terrarium.ad_astra.common.recipe;

import com.teamresourceful.resourcefullib.common.recipe.CodecRecipe;
import earth.terrarium.ad_astra.common.util.ModInventory;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class ModRecipe implements CodecRecipe<Container>, Predicate<ItemStack> {

    protected final ResourceLocation id;
    protected final List<IngredientHolder> inputs = new ArrayList<>();

    public ModRecipe(ResourceLocation id) {
        this.id = id;
    }

    public ModRecipe(ResourceLocation id, IngredientHolder input) {
        this.id = id;
        this.inputs.add(input);
    }

    public ModRecipe(ResourceLocation id, List<IngredientHolder> input) {
        this.id = id;
        this.inputs.addAll(input);
    }

    public List<IngredientHolder> getHolders() {
        return this.inputs;
    }

    @Override
    public boolean matches(Container inventory, Level level) {
        // Unused
        return false;
    }

    @Override
    public @NotNull ResourceLocation id() {
        return this.id;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> defaultedList = NonNullList.create();
        this.inputs.stream().map(IngredientHolder::ingredient).forEach(defaultedList::add);
        return defaultedList;
    }

    // Checks if the stack matches the recipe inputs.
    @Override
    public boolean test(ItemStack itemStack) {
        for (IngredientHolder input : this.inputs) {
            if (input.ingredient().test(itemStack)) {
                return true;
            }
        }
        return false;
    }

    // Tests if everything in the inventory matches the recipe in the correct order.
    public boolean test(ModInventory inventory) {
        for (int i = 0; i < this.inputs.size(); i++) {
            if (!inputs.get(i).ingredient().test(inventory.getItems().get(i))) {
                return false;
            }
        }

        return true;
    }
}