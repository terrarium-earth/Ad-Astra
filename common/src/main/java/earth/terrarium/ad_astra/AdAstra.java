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
        // Register config
        CONFIGURATOR.registerConfig(AdAstraConfig.class);

        // Registry
        ModFluidProperties.init();
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
        registry.accept(new ResourceLocation(AdAstra.MOD_ID, "planet_data"), new PlanetData());
    }

    public static void postInit() {
        PlatformUtils.registerStrippedLog(ModBlocks.GLACIAN_LOG.get(), ModBlocks.STRIPPED_GLACIAN_LOG.get());
        ModBlockEntities.postInit();
    }
}
