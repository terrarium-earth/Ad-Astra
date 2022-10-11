package com.github.alexnijjar.ad_astra.registry;

import java.util.function.Supplier;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.entities.SpacePaintingEntity;
import com.github.alexnijjar.ad_astra.entities.mobs.CorruptedLunarianEntity;
import com.github.alexnijjar.ad_astra.entities.mobs.GlacianRamEntity;
import com.github.alexnijjar.ad_astra.entities.mobs.LunarianEntity;
import com.github.alexnijjar.ad_astra.entities.mobs.LunarianWanderingTraderEntity;
import com.github.alexnijjar.ad_astra.entities.mobs.MartianRaptorEntity;
import com.github.alexnijjar.ad_astra.entities.mobs.MoglerEntity;
import com.github.alexnijjar.ad_astra.entities.mobs.PygroBruteEntity;
import com.github.alexnijjar.ad_astra.entities.mobs.PygroEntity;
import com.github.alexnijjar.ad_astra.entities.mobs.StarCrawlerEntity;
import com.github.alexnijjar.ad_astra.entities.mobs.SulfurCreeperEntity;
import com.github.alexnijjar.ad_astra.entities.mobs.ZombifiedMoglerEntity;
import com.github.alexnijjar.ad_astra.entities.mobs.ZombifiedPygroEntity;
import com.github.alexnijjar.ad_astra.entities.projectiles.IceSpitEntity;
import com.github.alexnijjar.ad_astra.entities.vehicles.LanderEntity;
import com.github.alexnijjar.ad_astra.entities.vehicles.RocketEntityTier1;
import com.github.alexnijjar.ad_astra.entities.vehicles.RocketEntityTier2;
import com.github.alexnijjar.ad_astra.entities.vehicles.RocketEntityTier3;
import com.github.alexnijjar.ad_astra.entities.vehicles.RocketEntityTier4;
import com.github.alexnijjar.ad_astra.entities.vehicles.RoverEntity;

import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;

public class ModEntityTypes {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(AdAstra.MOD_ID, Registry.ENTITY_TYPE_KEY);

	// Mobs
	public static final RegistrySupplier<EntityType<LunarianEntity>> LUNARIAN = ENTITY_TYPES.register("lunarian", () -> EntityType.Builder.create(LunarianEntity::new, SpawnGroup.CREATURE).setDimensions(0.75f, 2.5f).build("lunarian"));
	public static final RegistrySupplier<EntityType<CorruptedLunarianEntity>> CORRUPTED_LUNARIAN = ENTITY_TYPES.register("corrupted_lunarian", () -> EntityType.Builder.create(CorruptedLunarianEntity::new, SpawnGroup.MONSTER).setDimensions(0.6f, 2.4f).build("corrputed_lunarian"));

	public static final RegistrySupplier<EntityType<StarCrawlerEntity>> STAR_CRAWLER = ENTITY_TYPES.register("star_crawler", () -> EntityType.Builder.create(StarCrawlerEntity::new, SpawnGroup.MONSTER).setDimensions(1.3f, 1.0f).build("star_crawler"));
	public static final RegistrySupplier<EntityType<MartianRaptorEntity>> MARTIAN_RAPTOR = ENTITY_TYPES.register("martian_raptor", () -> EntityType.Builder.create(MartianRaptorEntity::new, SpawnGroup.MONSTER).setDimensions(0.75f, 2.0f).build("martian_raptor"));

	public static final RegistrySupplier<EntityType<PygroEntity>> PYGRO = ENTITY_TYPES.register("pygro", () -> EntityType.Builder.create(PygroEntity::new, SpawnGroup.MONSTER).setDimensions(0.6f, 1.8f).build("pygro"));
	public static final RegistrySupplier<EntityType<ZombifiedPygroEntity>> ZOMBIFIED_PYGRO = ENTITY_TYPES.register("zombified_pygro", () -> EntityType.Builder.create(ZombifiedPygroEntity::new, SpawnGroup.MONSTER).setDimensions(0.6f, 1.8f).makeFireImmune().build("zombified_pygro"));
	public static final RegistrySupplier<EntityType<PygroBruteEntity>> PYGRO_BRUTE = ENTITY_TYPES.register("pygro_brute", () -> EntityType.Builder.create(PygroBruteEntity::new, SpawnGroup.MONSTER).setDimensions(0.6f, 1.8f).makeFireImmune().build("pygro_brute"));
	public static final RegistrySupplier<EntityType<MoglerEntity>> MOGLER = ENTITY_TYPES.register("mogler", () -> EntityType.Builder.create(MoglerEntity::new, SpawnGroup.MONSTER).setDimensions(1.4f, 1.4f).makeFireImmune().build("mogler"));
	public static final RegistrySupplier<EntityType<ZombifiedMoglerEntity>> ZOMBIFIED_MOGLER = ENTITY_TYPES.register("zombified_mogler", () -> EntityType.Builder.create(ZombifiedMoglerEntity::new, SpawnGroup.MONSTER).setDimensions(1.4f, 1.4f).makeFireImmune().build("zombified_mogler"));

	public static final RegistrySupplier<EntityType<LunarianWanderingTraderEntity>> LUNARIAN_WANDERING_TRADER = ENTITY_TYPES.register("lunarian_wandering_trader", () -> EntityType.Builder.create(LunarianWanderingTraderEntity::new, SpawnGroup.CREATURE).setDimensions(0.6f, 1.95f).makeFireImmune().build("lunarian_wandering_trader"));

	public static final RegistrySupplier<EntityType<SulfurCreeperEntity>> SULFUR_CREEPER = ENTITY_TYPES.register("sulfur_creeper", () -> EntityType.Builder.create(SulfurCreeperEntity::new, SpawnGroup.MONSTER).setDimensions(0.6f, 1.7f).maxTrackingRange(8).makeFireImmune().build("sulfur_creeper"));
	public static final RegistrySupplier<EntityType<GlacianRamEntity>> GLACIAN_RAM = ENTITY_TYPES.register("glacian_ram", () -> EntityType.Builder.create(GlacianRamEntity::new, SpawnGroup.CREATURE).setDimensions(0.9f, 1.3f).maxTrackingRange(10).build("glacian_ram"));

	// Machines
	public static final Supplier<EntityType<RocketEntityTier1>> TIER_1_ROCKET = ENTITY_TYPES.register("tier_1_rocket", () -> EntityType.Builder.create(RocketEntityTier1::new, SpawnGroup.MISC).setDimensions(1.1f, 4.6f).makeFireImmune().build("tier_1_rocket"));
	public static final Supplier<EntityType<RocketEntityTier2>> TIER_2_ROCKET = ENTITY_TYPES.register("tier_2_rocket", () -> EntityType.Builder.create(RocketEntityTier2::new, SpawnGroup.MISC).setDimensions(1.1f, 4.8f).makeFireImmune().build("tier_2_rocket"));
	public static final Supplier<EntityType<RocketEntityTier3>> TIER_3_ROCKET = ENTITY_TYPES.register("tier_3_rocket", () -> EntityType.Builder.create(RocketEntityTier3::new, SpawnGroup.MISC).setDimensions(1.1f, 5.5f).makeFireImmune().build("tier_3_rocket"));
	public static final Supplier<EntityType<RocketEntityTier4>> TIER_4_ROCKET = ENTITY_TYPES.register("tier_4_rocket", () -> EntityType.Builder.create(RocketEntityTier4::new, SpawnGroup.MISC).setDimensions(1.1f, 7.0f).makeFireImmune().build("tier_4_rocket"));

	public static final Supplier<EntityType<RoverEntity>> TIER_1_ROVER = ENTITY_TYPES.register("tier_4_rocket", () -> EntityType.Builder.create(RoverEntity::new, SpawnGroup.MISC).setDimensions(1.8f, 1.5f).makeFireImmune().build("tier_1_rover"));
	public static final Supplier<EntityType<LanderEntity>> LANDER = ENTITY_TYPES.register("lander", () -> EntityType.Builder.create(LanderEntity::new, SpawnGroup.MISC).setDimensions(1.2f, 2.0f).makeFireImmune().build("lander"));

	public static final Supplier<EntityType<SpacePaintingEntity>> SPACE_PAINTING = ENTITY_TYPES.register("space_painting", () -> EntityType.Builder.create(SpacePaintingEntity::new, SpawnGroup.MISC).setDimensions(0.5f, 0.5f).maxTrackingRange(10).trackingTickInterval(Integer.MAX_VALUE).build("space_painting"));

	// Projectiles
	public static final Supplier<EntityType<IceSpitEntity>> ICE_SPIT = ENTITY_TYPES.register("ice_spit", () -> EntityType.Builder.<IceSpitEntity>create(IceSpitEntity::new, SpawnGroup.MISC).setDimensions(0.5f, 0.5f).build("ice_spit"));

	public static void register() {
		// Mob Attributes
		EntityAttributeRegistry.register(LUNARIAN, () -> LunarianEntity.createMobAttributes());
		EntityAttributeRegistry.register(CORRUPTED_LUNARIAN, () -> CorruptedLunarianEntity.createMobAttributes());
		EntityAttributeRegistry.register(STAR_CRAWLER, () -> StarCrawlerEntity.createMobAttributes());
		EntityAttributeRegistry.register(MARTIAN_RAPTOR, () -> MartianRaptorEntity.createMobAttributes());
		EntityAttributeRegistry.register(PYGRO, () -> PygroEntity.createMobAttributes());
		EntityAttributeRegistry.register(ZOMBIFIED_PYGRO, () -> ZombifiedPygroEntity.createMobAttributes());
		EntityAttributeRegistry.register(PYGRO_BRUTE, () -> PygroBruteEntity.createMobAttributes());
		EntityAttributeRegistry.register(MOGLER, () -> MoglerEntity.createMobAttributes());
		EntityAttributeRegistry.register(ZOMBIFIED_MOGLER, () -> ZombifiedMoglerEntity.createMobAttributes());
		EntityAttributeRegistry.register(LUNARIAN_WANDERING_TRADER, () -> LunarianEntity.createMobAttributes());
		EntityAttributeRegistry.register(SULFUR_CREEPER, () -> SulfurCreeperEntity.createMobAttributes());
		EntityAttributeRegistry.register(GLACIAN_RAM, () -> GlacianRamEntity.createMobAttributes());

		SpawnRestriction.register(LUNARIAN.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, LunarianEntity::canMobSpawn);
		SpawnRestriction.register(CORRUPTED_LUNARIAN.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CorruptedLunarianEntity::canSpawnInDark);
		SpawnRestriction.register(STAR_CRAWLER.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, StarCrawlerEntity::canSpawnInDark);
		SpawnRestriction.register(MARTIAN_RAPTOR.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MartianRaptorEntity::canSpawnInDark);
		SpawnRestriction.register(PYGRO.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PygroEntity::canSpawnInDark);
		SpawnRestriction.register(ZOMBIFIED_PYGRO.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ZombifiedPygroEntity::canSpawnInDark);
		SpawnRestriction.register(PYGRO_BRUTE.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PygroBruteEntity::canSpawnInDark);
		SpawnRestriction.register(MOGLER.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MoglerEntity::canMobSpawn);
		SpawnRestriction.register(ZOMBIFIED_MOGLER.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ZombifiedMoglerEntity::canSpawnInDark);
		SpawnRestriction.register(LUNARIAN_WANDERING_TRADER.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, LunarianEntity::canMobSpawn);
		SpawnRestriction.register(SULFUR_CREEPER.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SulfurCreeperEntity::canSpawnInDark);
		SpawnRestriction.register(GLACIAN_RAM.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, GlacianRamEntity::canMobSpawn);

		ENTITY_TYPES.register();
	}
}