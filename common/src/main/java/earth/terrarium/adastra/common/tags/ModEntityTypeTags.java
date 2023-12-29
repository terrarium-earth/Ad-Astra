package earth.terrarium.adastra.common.tags;

import earth.terrarium.adastra.AdAstra;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public final class ModEntityTypeTags {
    public static final TagKey<EntityType<?>> LIVES_WITHOUT_OXYGEN = tag("lives_without_oxygen");
    public static final TagKey<EntityType<?>> CAN_SURVIVE_EXTREME_COLD = tag("can_survive_extreme_cold");
    public static final TagKey<EntityType<?>> CAN_SURVIVE_EXTREME_HEAT = tag("can_survive_extreme_heat");
    public static final TagKey<EntityType<?>> CAN_SURVIVE_IN_SPACE = tag("can_survive_in_space");
    public static final TagKey<EntityType<?>> CAN_SURVIVE_ACID_RAIN = tag("can_survive_in_acid_rain");
    public static final TagKey<EntityType<?>> ACID_RAIN_IMMUNE = tag("acid_rain_immune");

    public static final TagKey<EntityType<?>> IGNORES_AIR_VORTEX = tag("ignores_air_vortex");

    private static TagKey<EntityType<?>> tag(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(AdAstra.MOD_ID, name));
    }
}
