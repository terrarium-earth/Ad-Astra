package earth.terrarium.ad_astra;

import com.teamresourceful.resourcefulconfig.common.config.Configurator;
import earth.terrarium.ad_astra.common.config.AdAstraConfig;
import earth.terrarium.ad_astra.common.registry.*;

public class AdAstra {
    public static final String MOD_ID = "ad_astra";
    public static final Configurator CONFIGURATOR = new Configurator(true);
    public static final int ETRIUM_COLOR = 0x63dcc2; //TODO make configurable

    public static void init() {
        CONFIGURATOR.registerConfig(AdAstraConfig.class);

        ModNetworkHandling.init();
        ModSoundEvents.SOUND_EVENTS.init();
        ModFluidProperties.FLUID_PROPERTIES.initialize();
        ModFluids.FLUIDS.init();
        ModBlocks.BLOCKS.init();
        ModParticleTypes.PARTICLE_TYPES.init();
        ModItems.ITEMS.init();
        ModEntityTypes.ENTITY_TYPES.init();
        ModBlockEntityTypes.BLOCK_ENTITY_TYPES.init();
        ModPaintings.PAINTING_VARIANTS.init();
        ModMenus.MENUS.init();
        ModRecipeTypes.RECIPE_TYPES.init();
        ModRecipeSerializers.RECIPE_SERIALIZERS.init();
        ModFeatures.FEATURES.init();
        ModStructures.STRUCTURE_TYPES.init();
        ModWorldCarvers.WORLD_CARVERS.init();
        ModBiomeSources.BIOME_SOURCES.init();
        ModDensityFunctionTypes.DENSITY_FUNCTION_TYPES.init();
    }

    public static void postInit() {
    }
}
