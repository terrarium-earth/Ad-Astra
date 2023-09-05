package earth.terrarium.adastra;

import com.teamresourceful.resourcefulconfig.common.config.Configurator;
import earth.terrarium.adastra.api.systems.GravityApi;
import earth.terrarium.adastra.api.systems.OxygenApi;
import earth.terrarium.adastra.api.systems.TemperatureApi;
import earth.terrarium.adastra.common.handlers.PlanetData;
import earth.terrarium.adastra.common.items.Ti69Item;
import earth.terrarium.adastra.common.items.upgrades.Upgrades;
import earth.terrarium.adastra.common.networking.NetworkHandler;
import earth.terrarium.adastra.common.networking.messages.ClientboundSyncLocalPlanetDataPacket;
import earth.terrarium.adastra.common.networking.messages.ClientboundSyncPlanetsPacket;
import earth.terrarium.adastra.common.networking.messages.ClientboundSyncWeatherPacket;
import earth.terrarium.adastra.common.planets.AdAstraData;
import earth.terrarium.adastra.common.registry.*;
import earth.terrarium.adastra.common.utils.radio.StationLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.storage.ServerLevelData;

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
    }

    public static void onServerTick(MinecraftServer server) {
        server.getPlayerList().getPlayers().forEach(player -> {
            if (player.level().getGameTime() % 5 == 0) {
                boolean oxygen = OxygenApi.API.hasOxygen(player);
                short temperature = TemperatureApi.API.getTemperature(player);
                float gravity = GravityApi.API.getGravity(player);
                NetworkHandler.CHANNEL.sendToPlayer(new ClientboundSyncLocalPlanetDataPacket(new PlanetData(oxygen, temperature, gravity)), player);
            }
            if (player.level().getGameTime() % 20 == 0) {
                if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof Ti69Item || player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof Ti69Item) {
                    ServerLevelData data = server.getWorldData().overworldData();
                    int clearTime = data.getClearWeatherTime();
                    int rainTime = data.getRainTime();
                    int thunderTime = data.getThunderTime();
                    boolean raining = data.isRaining();
                    boolean thundering = data.isThundering();
                    NetworkHandler.CHANNEL.sendToPlayer(new ClientboundSyncWeatherPacket(clearTime, rainTime, thunderTime, raining, thundering), player);
                }
            }
        });
    }
}
