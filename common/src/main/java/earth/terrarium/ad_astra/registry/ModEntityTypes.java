package earth.terrarium.ad_astra.registry;

import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.entities.SpacePainting;
import earth.terrarium.ad_astra.entities.mobs.*;
import earth.terrarium.ad_astra.entities.projectiles.IceSpit;
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
    public static final Supplier<EntityType<Lunarian>> LUNARIAN = ENTITY_TYPES.register("lunarian", () -> EntityType.Builder.of(Lunarian::new, MobCategory.CREATURE).sized(0.75f, 2.5f).build("lunarian"));
    public static final Supplier<EntityType<CorruptedLunarian>> CORRUPTED_LUNARIAN = ENTITY_TYPES.register("corrupted_lunarian", () -> EntityType.Builder.of(CorruptedLunarian::new, MobCategory.MONSTER).sized(0.6f, 2.4f).build("corrputed_lunarian"));

    public static final Supplier<EntityType<StarCrawler>> STAR_CRAWLER = ENTITY_TYPES.register("star_crawler", () -> EntityType.Builder.of(StarCrawler::new, MobCategory.MONSTER).sized(1.3f, 1.0f).build("star_crawler"));
    public static final Supplier<EntityType<MartianRaptor>> MARTIAN_RAPTOR = ENTITY_TYPES.register("martian_raptor", () -> EntityType.Builder.of(MartianRaptor::new, MobCategory.MONSTER).sized(0.75f, 2.0f).build("martian_raptor"));

    public static final Supplier<EntityType<Pygro>> PYGRO = ENTITY_TYPES.register("pygro", () -> EntityType.Builder.of(Pygro::new, MobCategory.MONSTER).sized(0.6f, 1.8f).build("pygro"));
    public static final Supplier<EntityType<ZombifiedPygro>> ZOMBIFIED_PYGRO = ENTITY_TYPES.register("zombified_pygro", () -> EntityType.Builder.of(ZombifiedPygro::new, MobCategory.MONSTER).sized(0.6f, 1.8f).fireImmune().build("zombified_pygro"));
    public static final Supplier<EntityType<PygroBrute>> PYGRO_BRUTE = ENTITY_TYPES.register("pygro_brute", () -> EntityType.Builder.of(PygroBrute::new, MobCategory.MONSTER).sized(0.6f, 1.8f).fireImmune().build("pygro_brute"));
    public static final Supplier<EntityType<Mogler>> MOGLER = ENTITY_TYPES.register("mogler", () -> EntityType.Builder.of(Mogler::new, MobCategory.MONSTER).sized(1.4f, 1.4f).fireImmune().build("mogler"));
    public static final Supplier<EntityType<ZombifiedMogler>> ZOMBIFIED_MOGLER = ENTITY_TYPES.register("zombified_mogler", () -> EntityType.Builder.of(ZombifiedMogler::new, MobCategory.MONSTER).sized(1.4f, 1.4f).fireImmune().build("zombified_mogler"));

    public static final Supplier<EntityType<LunarianWanderingTrader>> LUNARIAN_WANDERING_TRADER = ENTITY_TYPES.register("lunarian_wandering_trader", () -> EntityType.Builder.of(LunarianWanderingTrader::new, MobCategory.CREATURE).sized(0.6f, 1.95f).fireImmune().build("lunarian_wandering_trader"));

    public static final Supplier<EntityType<SulfurCreeper>> SULFUR_CREEPER = ENTITY_TYPES.register("sulfur_creeper", () -> EntityType.Builder.of(SulfurCreeper::new, MobCategory.MONSTER).sized(0.6f, 1.7f).clientTrackingRange(8).fireImmune().build("sulfur_creeper"));
    public static final Supplier<EntityType<GlacianRam>> GLACIAN_RAM = ENTITY_TYPES.register("glacian_ram", () -> EntityType.Builder.of(GlacianRam::new, MobCategory.CREATURE).sized(0.9f, 1.3f).clientTrackingRange(10).build("glacian_ram"));

    // Machines
    public static final Supplier<EntityType<RocketTier1>> TIER_1_ROCKET = ENTITY_TYPES.register("tier_1_rocket", () -> EntityType.Builder.of(RocketTier1::new, MobCategory.MISC).sized(1.1f, 4.6f).fireImmune().build("tier_1_rocket"));
    public static final Supplier<EntityType<RocketTier2>> TIER_2_ROCKET = ENTITY_TYPES.register("tier_2_rocket", () -> EntityType.Builder.of(RocketTier2::new, MobCategory.MISC).sized(1.1f, 4.8f).fireImmune().build("tier_2_rocket"));
    public static final Supplier<EntityType<RocketTier3>> TIER_3_ROCKET = ENTITY_TYPES.register("tier_3_rocket", () -> EntityType.Builder.of(RocketTier3::new, MobCategory.MISC).sized(1.1f, 5.5f).fireImmune().build("tier_3_rocket"));
    public static final Supplier<EntityType<RocketTier4>> TIER_4_ROCKET = ENTITY_TYPES.register("tier_4_rocket", () -> EntityType.Builder.of(RocketTier4::new, MobCategory.MISC).sized(1.1f, 7.0f).fireImmune().build("tier_4_rocket"));

    public static final Supplier<EntityType<Rover>> TIER_1_ROVER = ENTITY_TYPES.register("tier_1_rover", () -> EntityType.Builder.of(Rover::new, MobCategory.MISC).sized(1.8f, 1.5f).fireImmune().build("tier_1_rover"));
    public static final Supplier<EntityType<Lander>> LANDER = ENTITY_TYPES.register("lander", () -> EntityType.Builder.of(Lander::new, MobCategory.MISC).sized(1.2f, 2.0f).fireImmune().build("lander"));

    public static final Supplier<EntityType<SpacePainting>> SPACE_PAINTING = ENTITY_TYPES.register("space_painting", () -> EntityType.Builder.of(SpacePainting::new, MobCategory.MISC).sized(0.5f, 0.5f).clientTrackingRange(10).updateInterval(Integer.MAX_VALUE).build("space_painting"));

    // Projectiles
    public static final Supplier<EntityType<IceSpit>> ICE_SPIT = ENTITY_TYPES.register("ice_spit", () -> EntityType.Builder.<IceSpit>of(IceSpit::new, MobCategory.MISC).sized(0.5f, 0.5f).build("ice_spit"));

    public static void register() {
        ENTITY_TYPES.register();

        // Mob Attributes
        EntityAttributeRegistry.register(LUNARIAN, Lunarian::createMobAttributes);
        EntityAttributeRegistry.register(CORRUPTED_LUNARIAN, CorruptedLunarian::createMobAttributes);
        EntityAttributeRegistry.register(STAR_CRAWLER, StarCrawler::createMobAttributes);
        EntityAttributeRegistry.register(MARTIAN_RAPTOR, MartianRaptor::createMobAttributes);
        EntityAttributeRegistry.register(PYGRO, Pygro::createMobAttributes);
        EntityAttributeRegistry.register(ZOMBIFIED_PYGRO, ZombifiedPygro::createMobAttributes);
        EntityAttributeRegistry.register(PYGRO_BRUTE, PygroBrute::createMobAttributes);
        EntityAttributeRegistry.register(MOGLER, Mogler::createMobAttributes);
        EntityAttributeRegistry.register(ZOMBIFIED_MOGLER, ZombifiedMogler::createMobAttributes);
        EntityAttributeRegistry.register(LUNARIAN_WANDERING_TRADER, Lunarian::createMobAttributes);
        EntityAttributeRegistry.register(SULFUR_CREEPER, SulfurCreeper::createMobAttributes);
        EntityAttributeRegistry.register(GLACIAN_RAM, GlacianRam::createMobAttributes);
    }
}