package earth.terrarium.adastra;

import earth.terrarium.adastra.api.systems.GravityApi;
import earth.terrarium.adastra.common.networking.NetworkHandler;
import earth.terrarium.adastra.common.networking.messages.ClientboundSyncPlanetsPacket;
import earth.terrarium.adastra.common.networking.messages.ClientboundSyncPlayerGravityPacket;
import earth.terrarium.adastra.common.planets.PlanetData;
import earth.terrarium.adastra.common.registry.ModBlockEntityTypes;
import earth.terrarium.adastra.common.registry.ModBlocks;
import earth.terrarium.adastra.common.registry.ModDamageSources;
import earth.terrarium.adastra.common.registry.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.PreparableReloadListener;

import java.util.function.BiConsumer;

public class AdAstra {
    public static final String MOD_ID = "adastra";

    public static void init() {
        NetworkHandler.init();

        ModBlocks.BLOCKS.init();
        ModItems.ITEMS.init();
        ModBlockEntityTypes.BLOCK_ENTITY_TYPES.init();
    }

    public static void onAddReloadListener(BiConsumer<ResourceLocation, PreparableReloadListener> registry) {
        registry.accept(new ResourceLocation(AdAstra.MOD_ID, "planets"), new PlanetData());
    }

    public static void onDatapackSync(ServerPlayer player) {
        NetworkHandler.CHANNEL.sendToPlayer(new ClientboundSyncPlanetsPacket(PlanetData.planets()), player);
        ModDamageSources.clear();
    }

    public static void onServerTick(MinecraftServer server) {
        server.getPlayerList().getPlayers().forEach(player -> {
            if (player.level().getGameTime() % 5 == 0) {
                float gravity = GravityApi.API.getGravity(player);
                NetworkHandler.CHANNEL.sendToPlayer(new ClientboundSyncPlayerGravityPacket(gravity), player);
            }
        });
    }
}
