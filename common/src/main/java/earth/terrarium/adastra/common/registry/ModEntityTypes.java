package earth.terrarium.adastra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.entities.AirVortex;
import earth.terrarium.adastra.common.entities.mob.*;
import earth.terrarium.adastra.common.entities.mob.projectiles.IceSpit;
import earth.terrarium.adastra.common.entities.vehicles.Lander;
import earth.terrarium.adastra.common.entities.vehicles.Rocket;
import earth.terrarium.adastra.common.entities.vehicles.Rover;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.levelgen.Heightmap;

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

    // LEGACY ENTITIES. WILL BE REPLACED IN THE FUTURE
    public static final RegistryEntry<EntityType<Lunarian>> LUNARIAN = ENTITY_TYPES.register("lunarian", () ->
        EntityType.Builder.of(Lunarian::new, MobCategory.CREATURE)
            .sized(0.75f, 2.5f)
            .build("lunarian"));

    public static final RegistryEntry<EntityType<CorruptedLunarian>> CORRUPTED_LUNARIAN = ENTITY_TYPES.register("corrupted_lunarian", () ->
        EntityType.Builder.of(CorruptedLunarian::new, MobCategory.MONSTER)
            .sized(0.6f, 2.4f)
            .build("corrupted_lunarian"));

    public static final RegistryEntry<EntityType<StarCrawler>> STAR_CRAWLER = ENTITY_TYPES.register("star_crawler", () ->
        EntityType.Builder.of(StarCrawler::new, MobCategory.MONSTER)
            .sized(1.3f, 1.0f)
            .build("star_crawler"));

    public static final RegistryEntry<EntityType<MartianRaptor>> MARTIAN_RAPTOR = ENTITY_TYPES.register("martian_raptor", () ->
        EntityType.Builder.of(MartianRaptor::new, MobCategory.MONSTER)
            .sized(0.75f, 2.0f)
            .build("martian_raptor"));

    public static final RegistryEntry<EntityType<Pygro>> PYGRO = ENTITY_TYPES.register("pygro", () ->
        EntityType.Builder.of(Pygro::new, MobCategory.MONSTER)
            .sized(0.6f, 1.8f)
            .build("pygro"));

    public static final RegistryEntry<EntityType<ZombifiedPygro>> ZOMBIFIED_PYGRO = ENTITY_TYPES.register("zombified_pygro", () ->
        EntityType.Builder.of(ZombifiedPygro::new, MobCategory.MONSTER)
            .sized(0.6f, 1.8f)
            .fireImmune()
            .build("zombified_pygro"));

    public static final RegistryEntry<EntityType<PygroBrute>> PYGRO_BRUTE = ENTITY_TYPES.register("pygro_brute", () ->
        EntityType.Builder.of(PygroBrute::new, MobCategory.MONSTER)
            .sized(0.6f, 1.8f)
            .fireImmune()
            .build("pygro_brute"));

    public static final RegistryEntry<EntityType<Mogler>> MOGLER = ENTITY_TYPES.register("mogler", () ->
        EntityType.Builder.of(Mogler::new, MobCategory.MONSTER)
            .sized(1.4f, 1.4f)
            .fireImmune()
            .build("mogler"));

    public static final RegistryEntry<EntityType<ZombifiedMogler>> ZOMBIFIED_MOGLER = ENTITY_TYPES.register("zombified_mogler", () ->
        EntityType.Builder.of(ZombifiedMogler::new, MobCategory.MONSTER)
            .sized(1.4f, 1.4f)
            .fireImmune()
            .build("zombified_mogler"));

    public static final RegistryEntry<EntityType<LunarianWanderingTrader>> LUNARIAN_WANDERING_TRADER = ENTITY_TYPES.register("lunarian_wandering_trader", () ->
        EntityType.Builder.of(LunarianWanderingTrader::new, MobCategory.CREATURE)
            .sized(0.6f, 1.95f)
            .fireImmune()
            .build("lunarian_wandering_trader"));


    public static final RegistryEntry<EntityType<SulfurCreeper>> SULFUR_CREEPER = ENTITY_TYPES.register("sulfur_creeper", () ->
        EntityType.Builder.of(SulfurCreeper::new, MobCategory.MONSTER)
            .sized(0.6f, 1.7f)
            .clientTrackingRange(8)
            .fireImmune()
            .build("sulfur_creeper"));

    public static final RegistryEntry<EntityType<GlacianRam>> GLACIAN_RAM = ENTITY_TYPES.register("glacian_ram", () ->
        EntityType.Builder.of(GlacianRam::new, MobCategory.CREATURE).sized(0.9f, 1.3f)
            .clientTrackingRange(10)
            .build("glacian_ram"));

    public static final RegistryEntry<EntityType<IceSpit>> ICE_SPIT = ENTITY_TYPES.register("ice_spit", () ->
        EntityType.Builder.<IceSpit>of(IceSpit::new, MobCategory.MISC)
            .sized(0.5f, 0.5f)
            .build("ice_spit"));

    public static void registerSpawnPlacements() {
        SpawnPlacements.register(ModEntityTypes.LUNARIAN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Lunarian::checkMobSpawnRules);
        SpawnPlacements.register(ModEntityTypes.CORRUPTED_LUNARIAN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CorruptedLunarian::checkMonsterSpawnRules);
        SpawnPlacements.register(ModEntityTypes.STAR_CRAWLER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, StarCrawler::checkMonsterSpawnRules);
        SpawnPlacements.register(ModEntityTypes.MARTIAN_RAPTOR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MartianRaptor::checkMonsterSpawnRules);
        SpawnPlacements.register(ModEntityTypes.PYGRO.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Pygro::checkMonsterSpawnRules);
        SpawnPlacements.register(ModEntityTypes.ZOMBIFIED_PYGRO.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ZombifiedPygro::checkMonsterSpawnRules);
        SpawnPlacements.register(ModEntityTypes.PYGRO_BRUTE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PygroBrute::checkMonsterSpawnRules);
        SpawnPlacements.register(ModEntityTypes.MOGLER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mogler::checkMobSpawnRules);
        SpawnPlacements.register(ModEntityTypes.ZOMBIFIED_MOGLER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ZombifiedMogler::checkMonsterSpawnRules);
        SpawnPlacements.register(ModEntityTypes.LUNARIAN_WANDERING_TRADER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Lunarian::checkMobSpawnRules);
        SpawnPlacements.register(ModEntityTypes.SULFUR_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SulfurCreeper::checkMonsterSpawnRules);
        SpawnPlacements.register(ModEntityTypes.GLACIAN_RAM.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GlacianRam::checkMobSpawnRules);
    }

    public static void registerAttributes(BiConsumer<Supplier<? extends EntityType<? extends LivingEntity>>, Supplier<AttributeSupplier.Builder>> attributes) {
        attributes.accept(LUNARIAN, Lunarian::createMobAttributes);
        attributes.accept(CORRUPTED_LUNARIAN, CorruptedLunarian::createMobAttributes);
        attributes.accept(STAR_CRAWLER, StarCrawler::createMobAttributes);
        attributes.accept(MARTIAN_RAPTOR, MartianRaptor::createMobAttributes);
        attributes.accept(PYGRO, Pygro::createMobAttributes);
        attributes.accept(ZOMBIFIED_PYGRO, ZombifiedPygro::createMobAttributes);
        attributes.accept(PYGRO_BRUTE, PygroBrute::createMobAttributes);
        attributes.accept(MOGLER, Mogler::createMobAttributes);
        attributes.accept(ZOMBIFIED_MOGLER, ZombifiedMogler::createMobAttributes);
        attributes.accept(LUNARIAN_WANDERING_TRADER, Lunarian::createMobAttributes);
        attributes.accept(SULFUR_CREEPER, SulfurCreeper::createMobAttributes);
        attributes.accept(GLACIAN_RAM, GlacianRam::createMobAttributes);
    }
}