package earth.terrarium.ad_astra;

import earth.terrarium.ad_astra.client.resourcepack.PlanetResources;
import earth.terrarium.ad_astra.config.AdAstraConfig;
import earth.terrarium.ad_astra.data.Planet;
import earth.terrarium.ad_astra.data.PlanetData;
import earth.terrarium.ad_astra.entities.mobs.*;
import earth.terrarium.ad_astra.mixin.BlockEntityTypeAccessor;
import earth.terrarium.ad_astra.networking.NetworkHandling;
import earth.terrarium.ad_astra.registry.*;
import earth.terrarium.ad_astra.util.ModIdentifier;
import earth.terrarium.ad_astra.util.PlatformUtils;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.resource.ResourceReloader;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class AdAstra {
	public static final String MOD_ID = "ad_astra";
	public static final Logger LOGGER = LoggerFactory.getLogger("Ad Astra");
	public static AdAstraConfig CONFIG;

	public static Set<Planet> planets = new HashSet<>();
	public static Set<RegistryKey<World>> adAstraWorlds = new HashSet<>();
	public static Set<RegistryKey<World>> orbitWorlds = new HashSet<>();
	public static Set<RegistryKey<World>> planetWorlds = new HashSet<>();
	public static Set<RegistryKey<World>> worldsWithOxygen = new HashSet<>();

	public static void init() {
		// Register config
		AutoConfig.register(AdAstraConfig.class, Toml4jConfigSerializer::new);
		CONFIG = AutoConfig.getConfigHolder(AdAstraConfig.class).getConfig();

		// Registry
		ModFluids.register();
		ModEntityTypes.register();
		ModBlocks.register();
		ModItems.register();
		ModBlockEntities.register();
		ModRecipes.register();
		ModScreenHandlers.register();
		ModCommands.register();
		ModSoundEvents.register();
		ModParticleTypes.register();
		ModPaintings.register();

		// Worldgen
		ModFeatures.register();
		ModStructures.register();

		// Packets
		NetworkHandling.register();

		ModCriteria.register();
	}

	public static void onRegisterReloadListeners(BiConsumer<Identifier, ResourceReloader> registry) {
		registry.accept(new ModIdentifier("planet_data"), new PlanetData());
	}

	public static void postInit() {
		PlatformUtils.registerStrippedLog(ModBlocks.GLACIAN_LOG.get(), ModBlocks.STRIPPED_GLACIAN_LOG.get());

		// Add custom signs to the sign block entity registry
		BlockEntityTypeAccessor signRegistry = ((BlockEntityTypeAccessor) BlockEntityType.SIGN);
		Set<Block> signBlocks = new HashSet<>(signRegistry.getBlocks());
		signBlocks.add(ModBlocks.GLACIAN_SIGN.get());
		signBlocks.add(ModBlocks.GLACIAN_WALL_SIGN.get());
		signRegistry.setBlocks(signBlocks);

		// Add custom chests to the chest block entity registry
		BlockEntityTypeAccessor chestRegistry = ((BlockEntityTypeAccessor) BlockEntityType.CHEST);
		Set<Block> chestBlocks = new HashSet<>(chestRegistry.getBlocks());
		chestBlocks.add(ModBlocks.AERONOS_CHEST.get());
		chestBlocks.add(ModBlocks.STROPHAR_CHEST.get());
		chestRegistry.setBlocks(chestBlocks);

		SpawnRestriction.register(ModEntityTypes.LUNARIAN.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, LunarianEntity::canMobSpawn);
		SpawnRestriction.register(ModEntityTypes.CORRUPTED_LUNARIAN.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CorruptedLunarianEntity::canSpawnInDark);
		SpawnRestriction.register(ModEntityTypes.STAR_CRAWLER.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, StarCrawlerEntity::canSpawnInDark);
		SpawnRestriction.register(ModEntityTypes.MARTIAN_RAPTOR.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MartianRaptorEntity::canSpawnInDark);
		SpawnRestriction.register(ModEntityTypes.PYGRO.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PygroEntity::canSpawnInDark);
		SpawnRestriction.register(ModEntityTypes.ZOMBIFIED_PYGRO.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ZombifiedPygroEntity::canSpawnInDark);
		SpawnRestriction.register(ModEntityTypes.PYGRO_BRUTE.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PygroBruteEntity::canSpawnInDark);
		SpawnRestriction.register(ModEntityTypes.MOGLER.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MoglerEntity::canMobSpawn);
		SpawnRestriction.register(ModEntityTypes.ZOMBIFIED_MOGLER.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ZombifiedMoglerEntity::canSpawnInDark);
		SpawnRestriction.register(ModEntityTypes.LUNARIAN_WANDERING_TRADER.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, LunarianEntity::canMobSpawn);
		SpawnRestriction.register(ModEntityTypes.SULFUR_CREEPER.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SulfurCreeperEntity::canSpawnInDark);
		SpawnRestriction.register(ModEntityTypes.GLACIAN_RAM.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, GlacianRamEntity::canMobSpawn);
	}
}
