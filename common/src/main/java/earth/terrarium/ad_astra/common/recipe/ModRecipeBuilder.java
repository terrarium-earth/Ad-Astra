package earth.terrarium.ad_astra.common.recipe;

import com.google.gson.JsonObject;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;

public abstract class ModRecipeBuilder<RECIPE extends ModRecipe> {

    public ModRecipeBuilder() {
    }

    public static abstract class ModFinishedRecipe implements FinishedRecipe {

        private final ResourceLocation id;

        public ModFinishedRecipe(ModRecipeBuilder<?> builder, ResourceLocation id) {
            this.id = id;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {

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
