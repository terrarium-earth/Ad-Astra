package earth.terrarium.ad_astra.common.advancement;

import com.google.gson.JsonObject;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.advancement.FoodCookedInAtmosphereCriterion.Conditions;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

@MethodsReturnNonnullByDefault
public class FoodCookedInAtmosphereCriterion extends SimpleCriterionTrigger<Conditions> {
    static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "food_cooked_in_atmosphere");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public Conditions createInstance(JsonObject jsonObject, EntityPredicate.Composite extended, DeserializationContext advancementEntityPredicateDeserializer) {
        return new Conditions(extended);
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, conditions -> true);
    }

    public static class Conditions extends AbstractCriterionTriggerInstance {

        public Conditions(EntityPredicate.Composite player) {
            super(ID, player);
        }

        @Override
        public JsonObject serializeToJson(SerializationContext predicateSerializer) {
            return super.serializeToJson(predicateSerializer);
        }
    }
}