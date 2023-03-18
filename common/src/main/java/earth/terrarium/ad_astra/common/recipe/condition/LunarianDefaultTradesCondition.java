package earth.terrarium.ad_astra.common.recipe.condition;

import com.google.gson.JsonObject;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.config.AdAstraConfig;
import earth.terrarium.ad_astra.common.registry.ModRecipeConditionSerializers;
import net.minecraft.resources.ResourceLocation;

public class LunarianDefaultTradesCondition implements IRecipeCondition {

    public static final LunarianDefaultTradesCondition INSTANCE = new LunarianDefaultTradesCondition();

    @Override
    public boolean test() {
        return AdAstraConfig.enabledLunarianDefaultTrades;
    }

    @Override
    public IRecipeConditionSerializer<?> getSerializer() {
        return ModRecipeConditionSerializers.LUNARIAN_DEFAULT_TRADES;
    }

    public static class Serializer implements IRecipeConditionSerializer<LunarianDefaultTradesCondition> {

        public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "lunarian_default_trades");

        @Override
        public LunarianDefaultTradesCondition readJson(JsonObject json) {
            return new LunarianDefaultTradesCondition();
        }

        @Override
        public void writeJson(JsonObject json, LunarianDefaultTradesCondition condition) {

        }

        @Override
        public ResourceLocation getId() {
            return ID;
        }
    }
}
