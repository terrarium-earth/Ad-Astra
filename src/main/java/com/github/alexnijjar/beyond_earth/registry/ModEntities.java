package com.github.alexnijjar.beyond_earth.registry;

import com.github.alexnijjar.beyond_earth.entities.SpacePaintingEntity;
import com.github.alexnijjar.beyond_earth.entities.mobs.AlienEntity;
import com.github.alexnijjar.beyond_earth.entities.mobs.AlienWanderingTraderEntity;
import com.github.alexnijjar.beyond_earth.entities.mobs.AlienZombieEntity;
import com.github.alexnijjar.beyond_earth.entities.mobs.MartianRaptorEntity;
import com.github.alexnijjar.beyond_earth.entities.mobs.MoglerEntity;
import com.github.alexnijjar.beyond_earth.entities.mobs.PygroBruteEntity;
import com.github.alexnijjar.beyond_earth.entities.mobs.PygroEntity;
import com.github.alexnijjar.beyond_earth.entities.mobs.StarCrawlerEntity;
import com.github.alexnijjar.beyond_earth.entities.mobs.ZombifiedMoglerEntity;
import com.github.alexnijjar.beyond_earth.entities.projectiles.IceSpitEntity;
import com.github.alexnijjar.beyond_earth.entities.vehicles.LanderEntity;
import com.github.alexnijjar.beyond_earth.entities.vehicles.RocketEntityTier1;
import com.github.alexnijjar.beyond_earth.entities.vehicles.RocketEntityTier2;
import com.github.alexnijjar.beyond_earth.entities.vehicles.RocketEntityTier3;
import com.github.alexnijjar.beyond_earth.entities.vehicles.RocketEntityTier4;
import com.github.alexnijjar.beyond_earth.entities.vehicles.RoverEntity;
import com.github.alexnijjar.beyond_earth.util.ModIdentifier;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

public class ModEntities {

        // Mobs.
        public static final EntityType<AlienEntity> ALIEN = Registry.register(Registry.ENTITY_TYPE, new ModIdentifier("alien"), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, AlienEntity::new).dimensions(EntityDimensions.fixed(0.75f, 2.5f)).build());
        public static final EntityType<AlienZombieEntity> ALIEN_ZOMBIE = Registry.register(Registry.ENTITY_TYPE, new ModIdentifier("alien_zombie"),
                        FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, AlienZombieEntity::new).dimensions(EntityDimensions.fixed(0.6f, 2.4f)).build());
        public static final EntityType<StarCrawlerEntity> STAR_CRAWLER = Registry.register(Registry.ENTITY_TYPE, new ModIdentifier("star_crawler"),
                        FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, StarCrawlerEntity::new).dimensions(EntityDimensions.fixed(1.3f, 1.0f)).build());
        public static final EntityType<MartianRaptorEntity> MARTIAN_RAPTOR = Registry.register(Registry.ENTITY_TYPE, new ModIdentifier("martian_raptor"),
                        FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, MartianRaptorEntity::new).dimensions(EntityDimensions.fixed(0.75f, 2.0f)).build());
        public static final EntityType<PygroEntity> PYGRO = Registry.register(Registry.ENTITY_TYPE, new ModIdentifier("pygro"), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, PygroEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.8f)).build());
        public static final EntityType<PygroBruteEntity> PYGRO_BRUTE = Registry.register(Registry.ENTITY_TYPE, new ModIdentifier("pygro_brute"),
                        FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, PygroBruteEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.8f)).build());
        public static final EntityType<MoglerEntity> MOGLER = Registry.register(Registry.ENTITY_TYPE, new ModIdentifier("mogler"),
                        FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, MoglerEntity::new).dimensions(EntityDimensions.fixed(1.4f, 1.4f)).build());
        public static final EntityType<ZombifiedMoglerEntity> ZOMBIFIED_MOGLER = Registry.register(Registry.ENTITY_TYPE, new ModIdentifier("zombified_mogler"),
                        FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ZombifiedMoglerEntity::new).dimensions(EntityDimensions.fixed(1.4f, 1.4f)).build());
        public static final EntityType<AlienWanderingTraderEntity> ALIEN_WANDERING_TRADER = Registry.register(Registry.ENTITY_TYPE, new ModIdentifier("alien_wandering_trader"),
                        FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, AlienWanderingTraderEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.95f)).build());

        // Machines.
        public static final EntityType<RocketEntityTier1> ROCKET_TIER_1 = Registry.register(Registry.ENTITY_TYPE, new ModIdentifier("tier_1_rocket"),
                        FabricEntityTypeBuilder.create(SpawnGroup.MISC, RocketEntityTier1::new).dimensions(EntityDimensions.fixed(1.1f, 4.4f)).fireImmune().build());
        public static final EntityType<RocketEntityTier2> ROCKET_TIER_2 = Registry.register(Registry.ENTITY_TYPE, new ModIdentifier("tier_2_rocket"),
                        FabricEntityTypeBuilder.create(SpawnGroup.MISC, RocketEntityTier2::new).dimensions(EntityDimensions.fixed(1.1f, 4.6f)).fireImmune().build());
        public static final EntityType<RocketEntityTier3> ROCKET_TIER_3 = Registry.register(Registry.ENTITY_TYPE, new ModIdentifier("tier_3_rocket"),
                        FabricEntityTypeBuilder.create(SpawnGroup.MISC, RocketEntityTier3::new).dimensions(EntityDimensions.fixed(1.1f, 4.8f)).fireImmune().build());
        public static final EntityType<RocketEntityTier4> ROCKET_TIER_4 = Registry.register(Registry.ENTITY_TYPE, new ModIdentifier("tier_4_rocket"),
                        FabricEntityTypeBuilder.create(SpawnGroup.MISC, RocketEntityTier4::new).dimensions(EntityDimensions.fixed(1.1f, 6.1f)).fireImmune().build());

        public static final EntityType<RoverEntity> ROVER_TIER_1 = Registry.register(Registry.ENTITY_TYPE, new ModIdentifier("tier_1_rover"),
                        FabricEntityTypeBuilder.create(SpawnGroup.MISC, RoverEntity::new).dimensions(EntityDimensions.fixed(2.5f, 1.0f)).fireImmune().build());
        public static final EntityType<LanderEntity> LANDER = Registry.register(Registry.ENTITY_TYPE, new ModIdentifier("lander"),
                        FabricEntityTypeBuilder.create(SpawnGroup.MISC, LanderEntity::new).dimensions(EntityDimensions.fixed(1.0f, 2.0f)).fireImmune().build());

        public static final EntityType<SpacePaintingEntity> SPACE_PAINTING = Registry.register(Registry.ENTITY_TYPE, new ModIdentifier("space_painting"),
                        FabricEntityTypeBuilder.<SpacePaintingEntity>create(SpawnGroup.MISC, SpacePaintingEntity::new).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());

        // Projectiles.
        public static final EntityType<IceSpitEntity> ICE_SPIT_ENTITY = Registry.register(Registry.ENTITY_TYPE, new ModIdentifier("ice_spit_entity"),
                        FabricEntityTypeBuilder.<IceSpitEntity>create(SpawnGroup.MISC, IceSpitEntity::new).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());

        public static void register() {
                // Mob Attributes.
                FabricDefaultAttributeRegistry.register(ALIEN, AlienEntity.createMobAttributes());
                FabricDefaultAttributeRegistry.register(ALIEN_ZOMBIE, AlienZombieEntity.createMobAttributes());
                FabricDefaultAttributeRegistry.register(STAR_CRAWLER, StarCrawlerEntity.createMobAttributes());
                FabricDefaultAttributeRegistry.register(MARTIAN_RAPTOR, MartianRaptorEntity.createMobAttributes());
                FabricDefaultAttributeRegistry.register(PYGRO, PygroEntity.createMobAttributes());
                FabricDefaultAttributeRegistry.register(PYGRO_BRUTE, PygroBruteEntity.createMobAttributes());
                FabricDefaultAttributeRegistry.register(MOGLER, MoglerEntity.createMobAttributes());
                FabricDefaultAttributeRegistry.register(ZOMBIFIED_MOGLER, ZombifiedMoglerEntity.createMobAttributes());
                FabricDefaultAttributeRegistry.register(ALIEN_WANDERING_TRADER, AlienEntity.createMobAttributes());
        }
}
