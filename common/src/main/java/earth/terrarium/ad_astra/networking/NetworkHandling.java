package earth.terrarium.ad_astra.networking;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.networking.packets.client.*;
import earth.terrarium.ad_astra.networking.packets.server.DatapackPlanetsPacket;
import earth.terrarium.ad_astra.networking.packets.server.StartRocketPacket;
import com.teamresourceful.resourcefullib.common.networking.NetworkChannel;
import com.teamresourceful.resourcefullib.common.networking.base.NetworkDirection;
import dev.architectury.event.events.common.PlayerEvent;

public class NetworkHandling {
    public static final NetworkChannel CHANNEL = new NetworkChannel(AdAstra.MOD_ID, 0, "main");

    public static void register() {
        CHANNEL.registerPacket(NetworkDirection.CLIENT_TO_SERVER, KeybindPacket.ID, KeybindPacket.HANDLER, KeybindPacket.class);
        CHANNEL.registerPacket(NetworkDirection.CLIENT_TO_SERVER, LaunchRocketPacket.ID, LaunchRocketPacket.HANDLER, LaunchRocketPacket.class);
        CHANNEL.registerPacket(NetworkDirection.CLIENT_TO_SERVER, ToggleDistributorPacket.ID, ToggleDistributorPacket.HANDLER, ToggleDistributorPacket.class);
        CHANNEL.registerPacket(NetworkDirection.CLIENT_TO_SERVER, TeleportToPlanetPacket.ID, TeleportToPlanetPacket.HANDLER, TeleportToPlanetPacket.class);
        CHANNEL.registerPacket(NetworkDirection.CLIENT_TO_SERVER, CreateSpaceStationPacket.ID, CreateSpaceStationPacket.HANDLER, CreateSpaceStationPacket.class);

        CHANNEL.registerPacket(NetworkDirection.SERVER_TO_CLIENT, StartRocketPacket.ID, StartRocketPacket.HANDLER, StartRocketPacket.class);
        CHANNEL.registerPacket(NetworkDirection.SERVER_TO_CLIENT, DatapackPlanetsPacket.ID, DatapackPlanetsPacket.HANDLER, DatapackPlanetsPacket.class);

        PlayerEvent.PLAYER_JOIN.register(player -> {
            try {
                NetworkHandling.CHANNEL.sendToPlayer(new DatapackPlanetsPacket(AdAstra.planets), player);
            } catch (Exception e) {
                AdAstra.LOGGER.error("Failed to send datapack values to client: " + e);
                e.printStackTrace();
            }
        });
    }
}
