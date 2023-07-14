package earth.terrarium.ad_astra.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import earth.terrarium.ad_astra.common.recipe.condition.IRecipeCondition;
import earth.terrarium.ad_astra.common.recipe.condition.IRecipeConditionSerializer;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public abstract class ModRecipeBuilder<RECIPE extends ModRecipe> {

    private final List<IRecipeCondition> conditions;

    public ModRecipeBuilder() {
        this.conditions = new ArrayList<>();
    }

    public void addCondition(IRecipeCondition condition) {
        this.conditions.add(condition);
    }

    public static abstract class ModFinishedRecipe implements FinishedRecipe {

        private final ResourceLocation id;
        private final List<IRecipeCondition> conditions;

        public ModFinishedRecipe(ModRecipeBuilder<?> builder, ResourceLocation id) {
            this.id = id;
            this.conditions = new ArrayList<>(builder.conditions);
        }

        @Override
        public void serializeRecipeData(JsonObject json) {

            if (!this.conditions.isEmpty()) {
                JsonArray fabricConditionArray = new JsonArray();
                JsonArray forgeConditionArray = new JsonArray();

                for (IRecipeCondition recipeCondition : this.conditions) {
                    fabricConditionArray.add(IRecipeConditionSerializer.serializeFabricCondition(recipeCondition));
                    forgeConditionArray.add(IRecipeConditionSerializer.serializeForgeCondition(recipeCondition));
                }

                json.add("fabric:load_conditions", fabricConditionArray);
                json.add("conditions", forgeConditionArray);
            }
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }

    }
}
