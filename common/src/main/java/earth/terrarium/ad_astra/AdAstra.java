package earth.terrarium.ad_astra;

import com.teamresourceful.resourcefulconfig.common.config.Configurator;
import earth.terrarium.ad_astra.common.config.AdAstraConfig;
import earth.terrarium.ad_astra.common.data.PlanetData;
import earth.terrarium.ad_astra.common.networking.NetworkHandling;
import earth.terrarium.ad_astra.common.registry.*;
import earth.terrarium.ad_astra.common.util.PlatformUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.BiConsumer;

public class AdAstra {
    public static final String MOD_ID = "ad_astra";
    public static final Logger LOGGER = LoggerFactory.getLogger("Ad Astra");
    public static final Configurator CONFIGURATOR = new Configurator();

    public static void init() {
        CONFIGURATOR.registerConfig(AdAstraConfig.class);

        ModFluidProperties.FLUID_TYPES.initialize();
        ModFluids.FLUIDS.initialize();
        ModEntityTypes.ENTITY_TYPES.initialize();
        ModBlocks.BLOCKS.initialize();
        ModItems.ITEMS.initialize();
        ModBlockEntities.BLOCK_ENTITIES.initialize();
        ModRecipeTypes.RECIPE_TYPES.initialize();
        ModRecipeSerializers.RECIPE_SERIALIZERS.initialize();
        ModMenuTypes.MENU_TYPES.initialize();
        ModSoundEvents.SOUND_EVENTS.initialize();
        ModParticleTypes.PARTICLE_TYPES.initialize();
        ModPaintings.PAINTING_VARIENTS.initialize();
        ModFeatures.FEATURES.initialize();
        ModStructures.STRUCTURE_TYPES.initialize();
        ModCriteria.init();
        NetworkHandling.init();
    }

    public static void onRegisterReloadListeners(BiConsumer<ResourceLocation, PreparableReloadListener> registry) {
        registry.accept(new ResourceLocation(AdAstra.MOD_ID, "planet_data"), new PlanetData());
    }

    public static void postInit() {
        PlatformUtils.registerStrippedLog(ModBlocks.GLACIAN_LOG.get(), ModBlocks.STRIPPED_GLACIAN_LOG.get());
        ModBlockEntities.postInit();
    }
}
