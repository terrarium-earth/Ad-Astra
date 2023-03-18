package earth.terrarium.ad_astra.common.entity;

import earth.terrarium.ad_astra.common.recipe.lunarian.LunarianTradeRecipe;

public interface ILunarianTradeRecipeBuilderProvider {

    String getRecipeNameSuffix();

    LunarianTradeRecipe.Builder<?> provideRecipeBuilder();
}
