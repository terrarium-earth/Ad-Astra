package earth.terrarium.ad_astra;

import earth.terrarium.ad_astra.config.AdAstraConfig;
import earth.terrarium.ad_astra.data.Planet;
import earth.terrarium.ad_astra.data.PlanetData;
import earth.terrarium.ad_astra.mixin.BlockEntityTypeAccessor;
import earth.terrarium.ad_astra.networking.NetworkHandling;
import earth.terrarium.ad_astra.registry.*;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import earth.terrarium.ad_astra.util.PlatformUtils;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

public class AdAstra {
    public static final String MOD_ID = "ad_astra";
    public static final Logger LOGGER = LoggerFactory.getLogger("Ad Astra");
    public static AdAstraConfig CONFIG;

    public static Set<Planet> planets = new HashSet<>();
    public static Set<ResourceKey<Level>> adAstraWorlds = new HashSet<>();
    public static Set<ResourceKey<Level>> orbitWorlds = new HashSet<>();
    public static Set<ResourceKey<Level>> planetWorlds = new HashSet<>();
    public static Set<ResourceKey<Level>> levelsWithOxygen = new HashSet<>();

    public static void init() {
        // Register config
        AutoConfig.register(AdAstraConfig.class, Toml4jConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(AdAstraConfig.class).getConfig();

        // Registry
        ModFluids.init();
        ModEntityTypes.init();
        ModBlocks.init();
        ModItems.init();
        ModBlockEntities.init();
        ModRecipeTypes.init();
        ModRecipeSerializers.init();
        ModMenuTypes.init();
        ModSoundEvents.init();
        ModParticleTypes.init();
        ModPaintings.init();
        ModFeatures.init();
        ModStructures.init();
        ModCriteria.register();

        NetworkHandling.register();
    }

    public static void onRegisterReloadListeners(BiConsumer<ResourceLocation, PreparableReloadListener> registry) {
        registry.accept(new ModResourceLocation("planet_data"), new PlanetData());
    }

    public static void postInit() {
        PlatformUtils.registerStrippedLog(ModBlocks.GLACIAN_LOG.get(), ModBlocks.STRIPPED_GLACIAN_LOG.get());

        // Add custom signs to the sign block entity registry
        BlockEntityTypeAccessor signRegistry = ((BlockEntityTypeAccessor) BlockEntityType.SIGN);
        Set<Block> signBlocks = new HashSet<>(signRegistry.getValidBlocks());
        signBlocks.add(ModBlocks.GLACIAN_SIGN.get());
        signBlocks.add(ModBlocks.GLACIAN_WALL_SIGN.get());
        signRegistry.setValidBlocks(signBlocks);

        // Add custom chests to the chest block entity registry
        BlockEntityTypeAccessor chestRegistry = ((BlockEntityTypeAccessor) BlockEntityType.CHEST);
        Set<Block> chestBlocks = new HashSet<>(chestRegistry.getValidBlocks());
        chestBlocks.add(ModBlocks.AERONOS_CHEST.get());
        chestBlocks.add(ModBlocks.STROPHAR_CHEST.get());
        chestRegistry.setValidBlocks(chestBlocks);
        ModEntityTypes.registerSpawnPlacements();
    }
}
