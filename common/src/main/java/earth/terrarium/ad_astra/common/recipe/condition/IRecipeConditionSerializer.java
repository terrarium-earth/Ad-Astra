package earth.terrarium.ad_astra.common.recipe.condition;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

public interface IRecipeConditionSerializer<T extends IRecipeCondition> {

    public static JsonObject serializeFabricCondition(IRecipeCondition recipeCondition) {
        @SuppressWarnings("unchecked")
        IRecipeConditionSerializer<IRecipeCondition> serializer = (IRecipeConditionSerializer<IRecipeCondition>) recipeCondition.getSerializer();

        JsonObject json = new JsonObject();
        json.addProperty("condition", serializer.getId().toString());
        serializer.writeJson(json, recipeCondition);
        return json;
    }

    public static JsonObject serializeForgeCondition(IRecipeCondition recipeCondition) {
        @SuppressWarnings("unchecked")
        IRecipeConditionSerializer<IRecipeCondition> serializer = (IRecipeConditionSerializer<IRecipeCondition>) recipeCondition.getSerializer();

        JsonObject json = new JsonObject();
        json.addProperty("type", serializer.getId().toString());
        serializer.writeJson(json, recipeCondition);
        return json;
    }

    T readJson(JsonObject json);

    void writeJson(JsonObject json, T condition);

    ResourceLocation getId();
}
