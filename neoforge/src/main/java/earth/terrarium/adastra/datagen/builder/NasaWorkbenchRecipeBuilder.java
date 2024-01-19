package earth.terrarium.adastra.datagen.builder;

import com.teamresourceful.resourcefullib.common.datagen.CodecRecipeBuilder;
import earth.terrarium.adastra.common.recipes.machines.NasaWorkbenchRecipe;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NasaWorkbenchRecipeBuilder extends CodecRecipeBuilder {
    private final NasaWorkbenchRecipe recipe;

    public NasaWorkbenchRecipeBuilder(List<Ingredient> ingredients, ItemStack result) {
        recipe = new NasaWorkbenchRecipe(ingredients, result);
    }

    @Override
    public @NotNull Item getResult() {
        return recipe.result().getItem();
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        recipeOutput.accept(id, recipe, recipeOutput.advancement()
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
            .rewards(AdvancementRewards.Builder.recipe(id))
            .requirements(AdvancementRequirements.Strategy.OR)
            .build(new ResourceLocation(id.getNamespace(), "recipes/nasa_workbench/" + id.getPath())));
    }
}