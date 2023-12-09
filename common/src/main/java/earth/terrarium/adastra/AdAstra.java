package earth.terrarium.adastra;

import com.teamresourceful.resourcefulconfig.common.config.Configurator;
import earth.terrarium.adastra.api.systems.GravityApi;
import earth.terrarium.adastra.api.systems.OxygenApi;
import earth.terrarium.adastra.api.systems.TemperatureApi;
import earth.terrarium.adastra.common.blockentities.machines.RecyclerBlockEntity;
import earth.terrarium.adastra.common.handlers.PlanetData;
import earth.terrarium.adastra.common.items.upgrades.Upgrades;
import earth.terrarium.adastra.common.network.NetworkHandler;
import earth.terrarium.adastra.common.network.messages.ClientboundSyncLocalPlanetDataPacket;
import earth.terrarium.adastra.common.network.messages.ClientboundSyncPlanetsPacket;
import earth.terrarium.adastra.common.planets.AdAstraData;
import earth.terrarium.adastra.common.registry.*;
import earth.terrarium.adastra.common.utils.radio.StationLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.PreparableReloadListener;

import java.util.function.BiConsumer;

public class AdAstra {

    public static final String MOD_ID = "adastra";
    public static final Configurator CONFIGURATOR = new Configurator();

    public static void init() {
        NetworkHandler.init();
        StationLoader.init();

        ModFluidProperties.FLUID_PROPERTIES.initialize();
        ModFluids.FLUIDS.init();
        ModBlocks.BLOCKS.init();
        ModItems.ITEMS.init();
        ModEntityTypes.ENTITY_TYPES.init();
        ModBlockEntityTypes.BLOCK_ENTITY_TYPES.init();
        ModMenus.MENUS.init();
        ModRecipeTypes.RECIPE_TYPES.init();
        ModRecipeSerializers.RECIPE_SERIALIZERS.init();
        ModWorldCarvers.WORLD_CARVERS.init();
        ModBiomeSources.BIOME_SOURCES.init();
        ModDensityFunctionTypes.DENSITY_FUNCTION_TYPES.init();

        Upgrades.init();
    }

    public static void onAddReloadListener(BiConsumer<ResourceLocation, PreparableReloadListener> registry) {
        registry.accept(new ResourceLocation(AdAstra.MOD_ID, "planets"), new AdAstraData());
    }

    public static void onDatapackSync(ServerPlayer player) {
        NetworkHandler.CHANNEL.sendToPlayer(new ClientboundSyncPlanetsPacket(AdAstraData.planets()), player);
        ModDamageSources.clear();
        RecyclerBlockEntity.clearCache();
    }

    public static void onServerTick(MinecraftServer server) {
        server.getPlayerList().getPlayers().forEach(player -> {
            if (player.level().getGameTime() % 5 == 0) {
                boolean oxygen = OxygenApi.API.hasOxygen(player);
                short temperature = TemperatureApi.API.getTemperature(player);
                float gravity = GravityApi.API.getGravity(player);
                NetworkHandler.CHANNEL.sendToPlayer(new ClientboundSyncLocalPlanetDataPacket(new PlanetData(oxygen, temperature, gravity)), player);
            }
        });
    }
}
