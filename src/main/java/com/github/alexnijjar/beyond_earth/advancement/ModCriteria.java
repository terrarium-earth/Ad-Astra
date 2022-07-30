package com.github.alexnijjar.beyond_earth.advancement;

import net.minecraft.advancement.criterion.Criteria;

public class ModCriteria {
    public static final RocketDestroyedCriterion ROCKET_DESTROYED = Criteria.register(new RocketDestroyedCriterion());
    public static final FoodCookedInAtmosphereCriterion FOOD_COOKED_IN_ATMOSPHERE = Criteria.register(new FoodCookedInAtmosphereCriterion());
}
