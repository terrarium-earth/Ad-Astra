package earth.terrarium.ad_astra.recipes;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public abstract class CookingRecipe extends ModRecipe {

    private final ItemStack output;
    private short cookTime;

    public CookingRecipe(ResourceLocation id, Ingredient input, ItemStack output, short cookTime) {
        super(id, input);
        this.output = output;
        this.cookTime = cookTime;
    }

    public CookingRecipe(ResourceLocation id, List<Ingredient> input, List<Integer> stackCounts, ItemStack output) {
        super(id, input, stackCounts);
        this.output = output;
    }

    @Override
    public ItemStack getResultItem() {
        return this.output.copy();
    }

    public Ingredient getInputIngredient() {
        return this.inputs.get(0);
    }

    public short getCookTime() {
        return this.cookTime;
    }
}