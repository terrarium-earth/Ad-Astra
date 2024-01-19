package earth.terrarium.adastra.datagen.builder;

import com.teamresourceful.resourcefullib.common.datagen.CodecRecipeBuilder;
import earth.terrarium.adastra.common.recipes.machines.CompressingRecipe;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class CompressingRecipeBuilder extends CodecRecipeBuilder {
    private final CompressingRecipe recipe;

    public CompressingRecipeBuilder(int cookingTime, int energy, Ingredient ingredient, ItemStack result) {
        recipe = new CompressingRecipe(cookingTime, energy, ingredient, result);
    }

    @Override
    public @NotNull Item getResult() {
        return recipe.result().getItem();
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        var builder = recipeOutput.advancement()
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
            .rewards(AdvancementRewards.Builder.recipe(id))
            .requirements(AdvancementRequirements.Strategy.OR);
        criteria.forEach(builder::addCriterion);
        recipeOutput.accept(id, recipe, builder
            .build(new ResourceLocation(id.getNamespace(), "recipes/compressing/" + id.getPath())));
    }
}
