package earth.terrarium.adastra.common.tags;

import earth.terrarium.adastra.AdAstra;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

public final class ModFluidTags {
    public static final TagKey<Fluid> OXYGEN = tag("oxygen");
    public static final TagKey<Fluid> HYDROGEN = tag("hydrogen");
    public static final TagKey<Fluid> OIL = tag("oil");
    public static final TagKey<Fluid> FUEL = tag("fuel");
    public static final TagKey<Fluid> EFFICIENT_FUEL = tag("efficient_fuel");

    public static final TagKey<Fluid> TIER_1_ROCKET_FUEL = tag("tier_1_rocket_fuel");
    public static final TagKey<Fluid> TIER_2_ROCKET_FUEL = tag("tier_2_rocket_fuel");
    public static final TagKey<Fluid> TIER_3_ROCKET_FUEL = tag("tier_3_rocket_fuel");
    public static final TagKey<Fluid> TIER_4_ROCKET_FUEL = tag("tier_4_rocket_fuel");
    public static final TagKey<Fluid> TIER_1_ROVER_FUEL = tag("tier_1_rover_fuel");

    public static final TagKey<Fluid> ZIP_GUN_PROPELLANTS = tag("zip_gun_propellants");

    private static TagKey<Fluid> tag(String name) {
        return TagKey.create(Registries.FLUID, new ResourceLocation(AdAstra.MOD_ID, name));
    }
}
