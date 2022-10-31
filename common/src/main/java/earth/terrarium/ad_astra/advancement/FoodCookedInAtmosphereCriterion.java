package earth.terrarium.ad_astra.advancement;

import com.google.gson.JsonObject;
import earth.terrarium.ad_astra.advancement.FoodCookedInAtmosphereCriterion.Conditions;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;

public class FoodCookedInAtmosphereCriterion extends SimpleCriterionTrigger<Conditions> {
    static final ResourceLocation ID = new ModResourceLocation("food_cooked_in_atmosphere");

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

        public static Conditions create(Block block, ItemPredicate.Builder itemPredicateBuilder, MinMaxBounds.Ints beeCountRange) {
            return new Conditions(EntityPredicate.Composite.ANY);
        }

        @Override
        public JsonObject serializeToJson(SerializationContext predicateSerializer) {
            return super.serializeToJson(predicateSerializer);
        }
    }
}