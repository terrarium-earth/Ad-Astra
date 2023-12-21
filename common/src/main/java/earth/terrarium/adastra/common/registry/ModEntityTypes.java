package earth.terrarium.adastra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.entities.AirVortex;
import earth.terrarium.adastra.common.entities.vehicles.Lander;
import earth.terrarium.adastra.common.entities.vehicles.Rocket;
import earth.terrarium.adastra.common.entities.vehicles.Rover;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ModEntityTypes {
    public static final ResourcefulRegistry<EntityType<?>> ENTITY_TYPES = ResourcefulRegistries.create(BuiltInRegistries.ENTITY_TYPE, AdAstra.MOD_ID);

    public static final RegistryEntry<EntityType<AirVortex>> AIR_VORTEX = ENTITY_TYPES.register("air_vortex", () ->
        EntityType.Builder.<AirVortex>of(AirVortex::new, MobCategory.MISC)
            .fireImmune()
            .noSummon()
            .noSave()
            .build("air_vortex"));

    public static final RegistryEntry<EntityType<Rover>> ROVER = ENTITY_TYPES.register("tier_1_rover", () ->
        EntityType.Builder.of(Rover::new, MobCategory.MISC)
            .fireImmune()
            .clientTrackingRange(10)
            .sized(2.2f, 0.9f)
            .build("tier_1_rover"));

    public static final RegistryEntry<EntityType<Rocket>> TIER_1_ROCKET = ENTITY_TYPES.register("tier_1_rocket", () ->
        EntityType.Builder.<Rocket>of(Rocket::new, MobCategory.MISC)
            .fireImmune()
            .clientTrackingRange(10)
            .sized(1.1f, 4.6f)
            .build("tier_1_rocket"));

    public static final RegistryEntry<EntityType<Rocket>> TIER_2_ROCKET = ENTITY_TYPES.register("tier_2_rocket", () ->
        EntityType.Builder.<Rocket>of(Rocket::new, MobCategory.MISC)
            .fireImmune()
            .clientTrackingRange(10)
            .sized(1.1f, 4.8f)
            .build("tier_2_rocket"));

    public static final RegistryEntry<EntityType<Rocket>> TIER_3_ROCKET = ENTITY_TYPES.register("tier_3_rocket", () ->
        EntityType.Builder.<Rocket>of(Rocket::new, MobCategory.MISC)
            .fireImmune()
            .clientTrackingRange(10)
            .sized(1.1f, 5.5f)
            .build("tier_3_rocket"));

    public static final RegistryEntry<EntityType<Rocket>> TIER_4_ROCKET = ENTITY_TYPES.register("tier_4_rocket", () ->
        EntityType.Builder.<Rocket>of(Rocket::new, MobCategory.MISC)
            .fireImmune()
            .clientTrackingRange(10)
            .sized(1.1f, 7.0f)
            .build("tier_4_rocket"));

    public static final RegistryEntry<EntityType<Lander>> LANDER = ENTITY_TYPES.register("lander", () ->
        EntityType.Builder.of(Lander::new, MobCategory.MISC)
            .fireImmune()
            .clientTrackingRange(10)
            .sized(1.2f, 2.0f)
            .build("lander"));

    public static void registerAttributes(BiConsumer<Supplier<? extends EntityType<? extends LivingEntity>>, Supplier<AttributeSupplier.Builder>> attributes) {
    }
}