package earth.terrarium.adastra.datagen.builder;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import com.teamresourceful.resourcefullib.common.lib.Constants;
import earth.terrarium.adastra.common.recipes.SeparatingRecipe;
import earth.terrarium.adastra.common.registry.ModRecipeSerializers;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class SeparatingRecipeBuilder implements RecipeBuilder {
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    private final FluidHolder ingredient;
    private final FluidHolder resultFluid1;
    private final FluidHolder resultFluid2;

    private int cookingtime = 2;
    private int energy = 10;

    public SeparatingRecipeBuilder(FluidHolder ingredient, FluidHolder resultFluid1, FluidHolder resultFluid2) {
        this.ingredient = ingredient;
        this.resultFluid1 = resultFluid1;
        this.resultFluid2 = resultFluid2;
    }

    public SeparatingRecipeBuilder cookingTime(int cookingtime) {
        this.cookingtime = cookingtime;
        return this;
    }

    public SeparatingRecipeBuilder energy(int energy) {
        this.energy = energy;
        return this;
    }

    @Override
    public @NotNull RecipeBuilder unlockedBy(@NotNull String criterionName, @NotNull CriterionTriggerInstance criterionTrigger) {
        this.advancement.addCriterion(criterionName, criterionTrigger);
        return this;
    }

    @Override
    public @NotNull RecipeBuilder group(@Nullable String groupName) {
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        return Items.AIR;
    }

    @Override
    public void save(@NotNull Consumer<FinishedRecipe> finishedRecipeConsumer, @NotNull ResourceLocation recipeId) {
        this.advancement.parent(ROOT_RECIPE_ADVANCEMENT)
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId))
            .rewards(net.minecraft.advancements.AdvancementRewards.Builder.recipe(recipeId))
            .requirements(RequirementsStrategy.OR);

        finishedRecipeConsumer.accept(new Result(
            recipeId, this.ingredient,
            this.resultFluid1, this.resultFluid2,
            this.cookingtime, this.energy,
            this.advancement, new ResourceLocation(recipeId.getNamespace(), "recipes/separating/" + recipeId.getPath()))
        );
    }

    public record Result(
        ResourceLocation id,
        FluidHolder ingredient,
        FluidHolder resultFluid1,
        FluidHolder resultFluid2,
        int cookingtime,
        int energy,
        Advancement.Builder advancement, ResourceLocation advancementId
    ) implements FinishedRecipe {

        @Override
        public void serializeRecipeData(@NotNull JsonObject json) {
            SeparatingRecipe.codec(id)
                .encodeStart(JsonOps.INSTANCE, new SeparatingRecipe(id, cookingtime, energy, ingredient, resultFluid1, resultFluid2))
                .resultOrPartial(Constants.LOGGER::error)
                .ifPresent(out ->
                    out.getAsJsonObject().entrySet().forEach(entry -> json.add(entry.getKey(), entry.getValue()))
                );
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public @NotNull RecipeSerializer<?> getType() {
            return ModRecipeSerializers.SEPARATING.get();
        }

        @Override
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Override
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}
