package earth.terrarium.ad_astra.common.recipe;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class CookingRecipe extends ModRecipe {

    private final ItemStack output;
    private int cookTime;

    public CookingRecipe(ResourceLocation id, IngredientHolder input, ItemStack output, int cookTime) {
        super(id, input);
        this.output = output;
        this.cookTime = cookTime;
    }

    public CookingRecipe(ResourceLocation id, List<IngredientHolder> input, ItemStack output) {
        super(id, input);
        this.output = output;
    }

    @Override
    public @NotNull ItemStack getResultItem() {
        return this.output.copy();
    }

    public Ingredient getInputIngredient() {
        return this.inputs.get(0).ingredient();
    }

    public int getCookTime() {
        return this.cookTime;
    }
}