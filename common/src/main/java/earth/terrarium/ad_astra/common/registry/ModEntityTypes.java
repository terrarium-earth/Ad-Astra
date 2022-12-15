package earth.terrarium.ad_astra.common.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.entity.SpacePainting;
import earth.terrarium.ad_astra.common.entity.mob.*;
import earth.terrarium.ad_astra.common.entity.projectile.IceSpit;
import earth.terrarium.ad_astra.common.entity.vehicle.*;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.levelgen.Heightmap;
import org.apache.commons.lang3.NotImplementedException;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ModEntityTypes {

    // Mobs
    public static final Supplier<EntityType<Lunarian>> LUNARIAN = register("lunarian", () -> EntityType.Builder.of(Lunarian::new, MobCategory.CREATURE).sized(0.75f, 2.5f).build(AdAstra.MOD_ID));
    public static final Supplier<EntityType<CorruptedLunarian>> CORRUPTED_LUNARIAN = register("corrupted_lunarian", () -> EntityType.Builder.of(CorruptedLunarian::new, MobCategory.MONSTER).sized(0.6f, 2.4f).build(AdAstra.MOD_ID));

    public static final Supplier<EntityType<StarCrawler>> STAR_CRAWLER = register("star_crawler", () -> EntityType.Builder.of(StarCrawler::new, MobCategory.MONSTER).sized(1.3f, 1.0f).build(AdAstra.MOD_ID));
    public static final Supplier<EntityType<MartianRaptor>> MARTIAN_RAPTOR = register("martian_raptor", () -> EntityType.Builder.of(MartianRaptor::new, MobCategory.MONSTER).sized(0.75f, 2.0f).build(AdAstra.MOD_ID));

    public static final Supplier<EntityType<Pygro>> PYGRO = register("pygro", () -> EntityType.Builder.of(Pygro::new, MobCategory.MONSTER).sized(0.6f, 1.8f).build(AdAstra.MOD_ID));
    public static final Supplier<EntityType<ZombifiedPygro>> ZOMBIFIED_PYGRO = register("zombified_pygro", () -> EntityType.Builder.of(ZombifiedPygro::new, MobCategory.MONSTER).sized(0.6f, 1.8f).fireImmune().build(AdAstra.MOD_ID));
    public static final Supplier<EntityType<PygroBrute>> PYGRO_BRUTE = register("pygro_brute", () -> EntityType.Builder.of(PygroBrute::new, MobCategory.MONSTER).sized(0.6f, 1.8f).fireImmune().build(AdAstra.MOD_ID));
    public static final Supplier<EntityType<Mogler>> MOGLER = register("mogler", () -> EntityType.Builder.of(Mogler::new, MobCategory.MONSTER).sized(1.4f, 1.4f).fireImmune().build(AdAstra.MOD_ID));
    public static final Supplier<EntityType<ZombifiedMogler>> ZOMBIFIED_MOGLER = register("zombified_mogler", () -> EntityType.Builder.of(ZombifiedMogler::new, MobCategory.MONSTER).sized(1.4f, 1.4f).fireImmune().build(AdAstra.MOD_ID));

    public static final Supplier<EntityType<LunarianWanderingTrader>> LUNARIAN_WANDERING_TRADER = register("lunarian_wandering_trader", () -> EntityType.Builder.of(LunarianWanderingTrader::new, MobCategory.CREATURE).sized(0.6f, 1.95f).fireImmune().build(AdAstra.MOD_ID));

    public static final Supplier<EntityType<SulfurCreeper>> SULFUR_CREEPER = register("sulfur_creeper", () -> EntityType.Builder.of(SulfurCreeper::new, MobCategory.MONSTER).sized(0.6f, 1.7f).clientTrackingRange(8).fireImmune().build(AdAstra.MOD_ID));
    public static final Supplier<EntityType<GlacianRam>> GLACIAN_RAM = register("glacian_ram", () -> EntityType.Builder.of(GlacianRam::new, MobCategory.CREATURE).sized(0.9f, 1.3f).clientTrackingRange(10).build(AdAstra.MOD_ID));

    // Machines
    public static final Supplier<EntityType<RocketTier1>> TIER_1_ROCKET = register("tier_1_rocket", () -> EntityType.Builder.<RocketTier1>of(RocketTier1::new, MobCategory.MISC).sized(1.1f, 4.6f).fireImmune().build(AdAstra.MOD_ID));
    public static final Supplier<EntityType<RocketTier2>> TIER_2_ROCKET = register("tier_2_rocket", () -> EntityType.Builder.<RocketTier2>of(RocketTier2::new, MobCategory.MISC).sized(1.1f, 4.8f).fireImmune().build(AdAstra.MOD_ID));
    public static final Supplier<EntityType<RocketTier3>> TIER_3_ROCKET = register("tier_3_rocket", () -> EntityType.Builder.<RocketTier3>of(RocketTier3::new, MobCategory.MISC).sized(1.1f, 5.5f).fireImmune().build(AdAstra.MOD_ID));
    public static final Supplier<EntityType<RocketTier4>> TIER_4_ROCKET = register("tier_4_rocket", () -> EntityType.Builder.<RocketTier4>of(RocketTier4::new, MobCategory.MISC).sized(1.1f, 7.0f).fireImmune().build(AdAstra.MOD_ID));

    public static final Supplier<EntityType<Rover>> TIER_1_ROVER = register("tier_1_rover", () -> EntityType.Builder.of(Rover::new, MobCategory.MISC).sized(1.8f, 1.5f).fireImmune().build(AdAstra.MOD_ID));
    public static final Supplier<EntityType<Lander>> LANDER = register("lander", () -> EntityType.Builder.of(Lander::new, MobCategory.MISC).sized(1.2f, 2.0f).fireImmune().build(AdAstra.MOD_ID));

    public static final Supplier<EntityType<SpacePainting>> SPACE_PAINTING = register("space_painting", () -> EntityType.Builder.of(SpacePainting::new, MobCategory.MISC).sized(0.5f, 0.5f).clientTrackingRange(10).updateInterval(Integer.MAX_VALUE).build(AdAstra.MOD_ID));

    // Projectiles
    public static final Supplier<EntityType<IceSpit>> ICE_SPIT = register("ice_spit", () -> EntityType.Builder.<IceSpit>of(IceSpit::new, MobCategory.MISC).sized(0.5f, 0.5f).build(AdAstra.MOD_ID));

    private static <T extends EntityType<E>, E extends Entity> Supplier<T> register(String id, Supplier<T> object) {
        return ModRegistryHelpers.register(Registry.ENTITY_TYPE, id, object);
    }

    public static void init() {
    }

    public static void registerSpawnPlacements() {
        registerSpawnPlacement(ModEntityTypes.LUNARIAN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Lunarian::checkMobSpawnRules);
        registerSpawnPlacement(ModEntityTypes.CORRUPTED_LUNARIAN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CorruptedLunarian::checkMonsterSpawnRules);
        registerSpawnPlacement(ModEntityTypes.STAR_CRAWLER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, StarCrawler::checkMonsterSpawnRules);
        registerSpawnPlacement(ModEntityTypes.MARTIAN_RAPTOR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MartianRaptor::checkMonsterSpawnRules);
        registerSpawnPlacement(ModEntityTypes.PYGRO.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Pygro::checkMonsterSpawnRules);
        registerSpawnPlacement(ModEntityTypes.ZOMBIFIED_PYGRO.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ZombifiedPygro::checkMonsterSpawnRules);
        registerSpawnPlacement(ModEntityTypes.PYGRO_BRUTE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PygroBrute::checkMonsterSpawnRules);
        registerSpawnPlacement(ModEntityTypes.MOGLER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mogler::checkMobSpawnRules);
        registerSpawnPlacement(ModEntityTypes.ZOMBIFIED_MOGLER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ZombifiedMogler::checkMonsterSpawnRules);
        registerSpawnPlacement(ModEntityTypes.LUNARIAN_WANDERING_TRADER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Lunarian::checkMobSpawnRules);
        registerSpawnPlacement(ModEntityTypes.SULFUR_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SulfurCreeper::checkMonsterSpawnRules);
        registerSpawnPlacement(ModEntityTypes.GLACIAN_RAM.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GlacianRam::checkMobSpawnRules);
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

    @ExpectPlatform
    public static <T extends Mob> void registerSpawnPlacement(EntityType<T> entityType, SpawnPlacements.Type decoratorType, Heightmap.Types heightMapType, SpawnPlacements.SpawnPredicate<T> decoratorPredicate) {
        throw new NotImplementedException();
    }
}