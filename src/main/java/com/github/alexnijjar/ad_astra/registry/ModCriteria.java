package com.github.alexnijjar.ad_astra.registry;

import com.github.alexnijjar.ad_astra.advancement.FoodCookedInAtmosphereCriterion;
import com.github.alexnijjar.ad_astra.advancement.RocketDestroyedCriterion;
import net.minecraft.advancement.criterion.Criteria;

public class ModCriteria {
	public static RocketDestroyedCriterion ROCKET_DESTROYED;
	public static FoodCookedInAtmosphereCriterion FOOD_COOKED_IN_ATMOSPHERE;

	public static void register() {
		ROCKET_DESTROYED = Criteria.register(new RocketDestroyedCriterion());
		FOOD_COOKED_IN_ATMOSPHERE = Criteria.register(new FoodCookedInAtmosphereCriterion());
	}
}
