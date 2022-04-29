package net.mrscauthd.beyond_earth.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;
import net.mrscauthd.beyond_earth.entities.vehicles.LanderEntity;
import net.mrscauthd.beyond_earth.entities.vehicles.RocketEntity;
import net.mrscauthd.beyond_earth.entities.vehicles.RoverEntity;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

public class ModEntities {

    public static final EntityType<RocketEntity> TIER_1_ROCKET = Registry.register(Registry.ENTITY_TYPE, new ModIdentifier("rocket_t1"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, RocketEntity::new).dimensions(EntityDimensions.fixed(1.1f, 4.4f)).fireImmune().build());
    public static final EntityType<RocketEntity> TIER_2_ROCKET = Registry.register(Registry.ENTITY_TYPE, new ModIdentifier("rocket_t2"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, RocketEntity::new).dimensions(EntityDimensions.fixed(1.1f, 4.6f)).fireImmune().build());
    public static final EntityType<RocketEntity> TIER_3_ROCKET = Registry.register(Registry.ENTITY_TYPE, new ModIdentifier("rocket_t3"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, RocketEntity::new).dimensions(EntityDimensions.fixed(1.1f, 4.8f)).fireImmune().build());
    public static final EntityType<RocketEntity> TIER_4_ROCKET = Registry.register(Registry.ENTITY_TYPE, new ModIdentifier("rocket_t4"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, RocketEntity::new).dimensions(EntityDimensions.fixed(1.1f, 6.1f)).fireImmune().build());

    public static final EntityType<RoverEntity> ROVER = Registry.register(Registry.ENTITY_TYPE, new ModIdentifier("rover"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, RoverEntity::new).dimensions(EntityDimensions.fixed(2.5f, 1.0f)).fireImmune().build());
    public static final EntityType<LanderEntity> LANDER = Registry.register(Registry.ENTITY_TYPE, new ModIdentifier("lander"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, LanderEntity::new).dimensions(EntityDimensions.fixed(1.0f, 2.0f)).fireImmune().build());

    public static void register() {

    }
}
