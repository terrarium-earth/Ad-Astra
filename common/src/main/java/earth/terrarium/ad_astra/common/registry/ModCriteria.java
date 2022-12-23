package earth.terrarium.ad_astra.common.registry;

import earth.terrarium.ad_astra.common.advancement.FoodCookedInAtmosphereCriterion;
import earth.terrarium.ad_astra.common.advancement.RocketDestroyedCriterion;
import net.minecraft.advancements.CriteriaTriggers;

public class ModCriteria {
    public static RocketDestroyedCriterion ROCKET_DESTROYED;
    public static FoodCookedInAtmosphereCriterion FOOD_COOKED_IN_ATMOSPHERE;

    public static void init() {
        ROCKET_DESTROYED = CriteriaTriggers.register(new RocketDestroyedCriterion());
        FOOD_COOKED_IN_ATMOSPHERE = CriteriaTriggers.register(new FoodCookedInAtmosphereCriterion());
    }
}
