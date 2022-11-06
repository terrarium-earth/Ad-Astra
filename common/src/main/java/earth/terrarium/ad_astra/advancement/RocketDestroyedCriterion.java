package earth.terrarium.ad_astra.advancement;

import com.google.gson.JsonObject;
import earth.terrarium.ad_astra.advancement.RocketDestroyedCriterion.Conditions;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;

public class RocketDestroyedCriterion extends SimpleCriterionTrigger<Conditions> {
    static final ResourceLocation ID = new ModResourceLocation("rocket_destroyed");

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