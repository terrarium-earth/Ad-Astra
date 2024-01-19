package earth.terrarium.adastra.datagen.builder;

import com.teamresourceful.resourcefullib.common.datagen.CodecRecipeBuilder;
import earth.terrarium.adastra.common.recipes.machines.CryoFreezingRecipe;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class CryoFreezingRecipeBuilder extends CodecRecipeBuilder {
    private final CryoFreezingRecipe recipe;

    public CryoFreezingRecipeBuilder(int cookingTime, int energy, Ingredient input, FluidHolder result) {
        recipe = new CryoFreezingRecipe(cookingTime, energy, input, result);
    }

    @Override
    public @NotNull Item getResult() {
        return Items.AIR;
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        var builder = recipeOutput.advancement()
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
            .rewards(AdvancementRewards.Builder.recipe(id))
            .requirements(AdvancementRequirements.Strategy.OR);
        criteria.forEach(builder::addCriterion);
        recipeOutput.accept(id, recipe, builder
            .build(new ResourceLocation(id.getNamespace(), "recipes/cryo_freezing/" + id.getPath())));
    }
}