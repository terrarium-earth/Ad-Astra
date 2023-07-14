package earth.terrarium.ad_astra.common.registry;

import earth.terrarium.ad_astra.common.recipe.condition.IRecipeConditionSerializer;
import earth.terrarium.ad_astra.common.recipe.condition.LunarianDefaultTradesCondition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModRecipeConditionSerializers {
    private static final List<IRecipeConditionSerializer<?>> SERIALIZERS = new ArrayList<>();

    public static final IRecipeConditionSerializer<LunarianDefaultTradesCondition> LUNARIAN_DEFAULT_TRADES = register(new LunarianDefaultTradesCondition.Serializer());

    public static <T extends IRecipeConditionSerializer<?>> T register(T condition) {
        SERIALIZERS.add(condition);
        return condition;
    }

    public static List<IRecipeConditionSerializer<?>> getSerializers() {
        return Collections.unmodifiableList(SERIALIZERS);
    }

    public static void init() {

    }
}
