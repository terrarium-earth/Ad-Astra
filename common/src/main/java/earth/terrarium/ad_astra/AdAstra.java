package earth.terrarium.ad_astra;

import com.teamresourceful.resourcefulconfig.common.config.Configurator;
import earth.terrarium.ad_astra.common.config.AdAstraConfig;
import earth.terrarium.ad_astra.common.networking.NetworkHandling;
import earth.terrarium.ad_astra.common.registry.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;

import java.util.function.BiConsumer;

public class AdAstra {
    public static final String MOD_ID = "ad_astra";
    public static final Configurator CONFIGURATOR = new Configurator();
    public static final int ETRIUM_COLOR = 0x63dcc2; //TODO make configurable

    public static void init() {
        CONFIGURATOR.registerConfig(AdAstraConfig.class);

        ModFluidProperties.FLUID_PROPERTIES.initialize();
        ModFluids.FLUIDS.init();
        ModEntityTypes.ENTITY_TYPES.init();
        ModBlocks.BLOCKS.init();
        ModItems.ITEMS.init();
        ModBlockEntityTypes.BLOCK_ENTITY_TYPES.init();
        ModRecipeTypes.RECIPE_TYPES.init();
        ModRecipeSerializers.RECIPE_SERIALIZERS.init();
        ModMenus.MENUS.init();
        ModSoundEvents.SOUND_EVENTS.init();
        ModParticleTypes.PARTICLE_TYPES.init();
        ModPaintings.PAINTING_VARIANTS.init();
        ModFeatures.FEATURES.init();
        ModStructures.STRUCTURE_TYPES.init();
        NetworkHandling.init();
    }

    public static void onRegisterReloadListeners(BiConsumer<ResourceLocation, PreparableReloadListener> registry) {
    }

    public static void postInit() {
    }
}
