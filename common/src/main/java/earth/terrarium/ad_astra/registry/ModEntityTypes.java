package earth.terrarium.ad_astra.registry;

import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.entities.SpacePaintingEntity;
import earth.terrarium.ad_astra.entities.mobs.*;
import earth.terrarium.ad_astra.entities.projectiles.IceSpitEntity;
import earth.terrarium.ad_astra.entities.vehicles.*;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

// \.build\(".*"\)
// .build(null)
public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(AdAstra.MOD_ID, Registry.ENTITY_TYPE_REGISTRY);

    // Mobs
    public static final Supplier<EntityType<LunarianEntity>> LUNARIAN = ENTITY_TYPES.register("lunarian", () -> EntityType.Builder.of(LunarianEntity::new, MobCategory.CREATURE).sized(0.75f, 2.5f).build("lunarian"));
    public static final Supplier<EntityType<CorruptedLunarianEntity>> CORRUPTED_LUNARIAN = ENTITY_TYPES.register("corrupted_lunarian", () -> EntityType.Builder.of(CorruptedLunarianEntity::new, MobCategory.MONSTER).sized(0.6f, 2.4f).build("corrputed_lunarian"));

    public static final Supplier<EntityType<StarCrawlerEntity>> STAR_CRAWLER = ENTITY_TYPES.register("star_crawler", () -> EntityType.Builder.of(StarCrawlerEntity::new, MobCategory.MONSTER).sized(1.3f, 1.0f).build("star_crawler"));
    public static final Supplier<EntityType<MartianRaptorEntity>> MARTIAN_RAPTOR = ENTITY_TYPES.register("martian_raptor", () -> EntityType.Builder.of(MartianRaptorEntity::new, MobCategory.MONSTER).sized(0.75f, 2.0f).build("martian_raptor"));

    public static final Supplier<EntityType<PygroEntity>> PYGRO = ENTITY_TYPES.register("pygro", () -> EntityType.Builder.of(PygroEntity::new, MobCategory.MONSTER).sized(0.6f, 1.8f).build("pygro"));
    public static final Supplier<EntityType<ZombifiedPygroEntity>> ZOMBIFIED_PYGRO = ENTITY_TYPES.register("zombified_pygro", () -> EntityType.Builder.of(ZombifiedPygroEntity::new, MobCategory.MONSTER).sized(0.6f, 1.8f).fireImmune().build("zombified_pygro"));
    public static final Supplier<EntityType<PygroBruteEntity>> PYGRO_BRUTE = ENTITY_TYPES.register("pygro_brute", () -> EntityType.Builder.of(PygroBruteEntity::new, MobCategory.MONSTER).sized(0.6f, 1.8f).fireImmune().build("pygro_brute"));
    public static final Supplier<EntityType<MoglerEntity>> MOGLER = ENTITY_TYPES.register("mogler", () -> EntityType.Builder.of(MoglerEntity::new, MobCategory.MONSTER).sized(1.4f, 1.4f).fireImmune().build("mogler"));
    public static final Supplier<EntityType<ZombifiedMoglerEntity>> ZOMBIFIED_MOGLER = ENTITY_TYPES.register("zombified_mogler", () -> EntityType.Builder.of(ZombifiedMoglerEntity::new, MobCategory.MONSTER).sized(1.4f, 1.4f).fireImmune().build("zombified_mogler"));

    public static final Supplier<EntityType<LunarianWanderingTraderEntity>> LUNARIAN_WANDERING_TRADER = ENTITY_TYPES.register("lunarian_wandering_trader", () -> EntityType.Builder.of(LunarianWanderingTraderEntity::new, MobCategory.CREATURE).sized(0.6f, 1.95f).fireImmune().build("lunarian_wandering_trader"));

    public static final Supplier<EntityType<SulfurCreeperEntity>> SULFUR_CREEPER = ENTITY_TYPES.register("sulfur_creeper", () -> EntityType.Builder.of(SulfurCreeperEntity::new, MobCategory.MONSTER).sized(0.6f, 1.7f).clientTrackingRange(8).fireImmune().build("sulfur_creeper"));
    public static final Supplier<EntityType<GlacianRamEntity>> GLACIAN_RAM = ENTITY_TYPES.register("glacian_ram", () -> EntityType.Builder.of(GlacianRamEntity::new, MobCategory.CREATURE).sized(0.9f, 1.3f).clientTrackingRange(10).build("glacian_ram"));

    // Machines
    public static final Supplier<EntityType<RocketEntityTier1>> TIER_1_ROCKET = ENTITY_TYPES.register("tier_1_rocket", () -> EntityType.Builder.of(RocketEntityTier1::new, MobCategory.MISC).sized(1.1f, 4.6f).fireImmune().build("tier_1_rocket"));
    public static final Supplier<EntityType<RocketEntityTier2>> TIER_2_ROCKET = ENTITY_TYPES.register("tier_2_rocket", () -> EntityType.Builder.of(RocketEntityTier2::new, MobCategory.MISC).sized(1.1f, 4.8f).fireImmune().build("tier_2_rocket"));
    public static final Supplier<EntityType<RocketEntityTier3>> TIER_3_ROCKET = ENTITY_TYPES.register("tier_3_rocket", () -> EntityType.Builder.of(RocketEntityTier3::new, MobCategory.MISC).sized(1.1f, 5.5f).fireImmune().build("tier_3_rocket"));
    public static final Supplier<EntityType<RocketEntityTier4>> TIER_4_ROCKET = ENTITY_TYPES.register("tier_4_rocket", () -> EntityType.Builder.of(RocketEntityTier4::new, MobCategory.MISC).sized(1.1f, 7.0f).fireImmune().build("tier_4_rocket"));

    public static final Supplier<EntityType<RoverEntity>> TIER_1_ROVER = ENTITY_TYPES.register("tier_1_rover", () -> EntityType.Builder.of(RoverEntity::new, MobCategory.MISC).sized(1.8f, 1.5f).fireImmune().build("tier_1_rover"));
    public static final Supplier<EntityType<LanderEntity>> LANDER = ENTITY_TYPES.register("lander", () -> EntityType.Builder.of(LanderEntity::new, MobCategory.MISC).sized(1.2f, 2.0f).fireImmune().build("lander"));

    public static final Supplier<EntityType<SpacePaintingEntity>> SPACE_PAINTING = ENTITY_TYPES.register("space_painting", () -> EntityType.Builder.of(SpacePaintingEntity::new, MobCategory.MISC).sized(0.5f, 0.5f).clientTrackingRange(10).updateInterval(Integer.MAX_VALUE).build("space_painting"));

    // Projectiles
    public static final Supplier<EntityType<IceSpitEntity>> ICE_SPIT = ENTITY_TYPES.register("ice_spit", () -> EntityType.Builder.<IceSpitEntity>of(IceSpitEntity::new, MobCategory.MISC).sized(0.5f, 0.5f).build("ice_spit"));

    public static void register() {
        ENTITY_TYPES.register();

        // Mob Attributes
        EntityAttributeRegistry.register(LUNARIAN, LunarianEntity::createMobAttributes);
        EntityAttributeRegistry.register(CORRUPTED_LUNARIAN, CorruptedLunarianEntity::createMobAttributes);
        EntityAttributeRegistry.register(STAR_CRAWLER, StarCrawlerEntity::createMobAttributes);
        EntityAttributeRegistry.register(MARTIAN_RAPTOR, MartianRaptorEntity::createMobAttributes);
        EntityAttributeRegistry.register(PYGRO, PygroEntity::createMobAttributes);
        EntityAttributeRegistry.register(ZOMBIFIED_PYGRO, ZombifiedPygroEntity::createMobAttributes);
        EntityAttributeRegistry.register(PYGRO_BRUTE, PygroBruteEntity::createMobAttributes);
        EntityAttributeRegistry.register(MOGLER, MoglerEntity::createMobAttributes);
        EntityAttributeRegistry.register(ZOMBIFIED_MOGLER, ZombifiedMoglerEntity::createMobAttributes);
        EntityAttributeRegistry.register(LUNARIAN_WANDERING_TRADER, LunarianEntity::createMobAttributes);
        EntityAttributeRegistry.register(SULFUR_CREEPER, SulfurCreeperEntity::createMobAttributes);
        EntityAttributeRegistry.register(GLACIAN_RAM, GlacianRamEntity::createMobAttributes);
    }
}