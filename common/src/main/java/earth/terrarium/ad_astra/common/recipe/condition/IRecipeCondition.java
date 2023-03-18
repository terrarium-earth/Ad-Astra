package earth.terrarium.ad_astra.common.recipe.condition;

public interface IRecipeCondition {

    IRecipeConditionSerializer<?> getSerializer();

    boolean test();
}
