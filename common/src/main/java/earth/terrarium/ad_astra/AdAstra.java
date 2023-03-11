package earth.terrarium.ad_astra;

import com.mojang.logging.LogUtils;
import com.teamresourceful.resourcefulconfig.common.config.Configurator;
import earth.terrarium.ad_astra.common.config.AdAstraConfig;
import earth.terrarium.ad_astra.common.data.PlanetData;
import earth.terrarium.ad_astra.common.entity.LunarianMerchantListener;
import earth.terrarium.ad_astra.common.networking.NetworkHandling;
import earth.terrarium.ad_astra.common.registry.*;
import earth.terrarium.ad_astra.common.util.PlatformUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import org.slf4j.Logger;

import java.util.function.BiConsumer;

public class AdAstra {
    public static final String MOD_ID = "ad_astra";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final Configurator CONFIGURATOR = new Configurator(true);

    public static void init() {
        CONFIGURATOR.registerConfig(AdAstraConfig.class);

        ModFluidProperties.FLUID_TYPES.initialize();
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
        ModStructures.STRUCTURE_PROCESSORS.init();
        ModCriteria.init();
        NetworkHandling.init();
    }

    public static void onRegisterReloadListeners(BiConsumer<ResourceLocation, PreparableReloadListener> registry) {
        registry.accept(new ResourceLocation(AdAstra.MOD_ID, "planet_data"), new PlanetData());
        registry.accept(new ResourceLocation(AdAstra.MOD_ID, "lunarian_merchant_listener"), new LunarianMerchantListener());
    }

    public static void postInit() {
        PlatformUtils.registerStrippedLog(ModBlocks.GLACIAN_LOG.get(), ModBlocks.STRIPPED_GLACIAN_LOG.get());
    }
}
