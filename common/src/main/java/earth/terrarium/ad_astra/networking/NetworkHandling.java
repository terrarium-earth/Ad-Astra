package earth.terrarium.ad_astra.networking;

import com.teamresourceful.resourcefullib.common.networking.NetworkChannel;
import com.teamresourceful.resourcefullib.common.networking.base.NetworkDirection;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.data.PlanetData;
import earth.terrarium.ad_astra.networking.packet.client.*;
import earth.terrarium.ad_astra.networking.packet.server.DatapackPlanetsPacket;
import earth.terrarium.ad_astra.networking.packet.server.MachineInfoPacket;
import earth.terrarium.ad_astra.networking.packet.server.StartRocketPacket;
import net.minecraft.world.entity.player.Player;

public class NetworkHandling {
    public static final NetworkChannel CHANNEL = new NetworkChannel(AdAstra.MOD_ID, 0, "main");

    public static void register() {
        CHANNEL.registerPacket(NetworkDirection.CLIENT_TO_SERVER, KeybindPacket.ID, KeybindPacket.HANDLER, KeybindPacket.class);
        CHANNEL.registerPacket(NetworkDirection.CLIENT_TO_SERVER, LaunchRocketPacket.ID, LaunchRocketPacket.HANDLER, LaunchRocketPacket.class);
        CHANNEL.registerPacket(NetworkDirection.CLIENT_TO_SERVER, ToggleDistributorPacket.ID, ToggleDistributorPacket.HANDLER, ToggleDistributorPacket.class);
        CHANNEL.registerPacket(NetworkDirection.CLIENT_TO_SERVER, TeleportToPlanetPacket.ID, TeleportToPlanetPacket.HANDLER, TeleportToPlanetPacket.class);
        CHANNEL.registerPacket(NetworkDirection.CLIENT_TO_SERVER, CreateSpaceStationPacket.ID, CreateSpaceStationPacket.HANDLER, CreateSpaceStationPacket.class);
        CHANNEL.registerPacket(NetworkDirection.CLIENT_TO_SERVER, FlagUrlPacket.ID, FlagUrlPacket.HANDLER, FlagUrlPacket.class);

        CHANNEL.registerPacket(NetworkDirection.SERVER_TO_CLIENT, StartRocketPacket.ID, StartRocketPacket.HANDLER, StartRocketPacket.class);
        CHANNEL.registerPacket(NetworkDirection.SERVER_TO_CLIENT, DatapackPlanetsPacket.ID, DatapackPlanetsPacket.HANDLER, DatapackPlanetsPacket.class);
        CHANNEL.registerPacket(NetworkDirection.SERVER_TO_CLIENT, MachineInfoPacket.ID, MachineInfoPacket.HANDLER, MachineInfoPacket.class);
    }

    public static void onPlayerJoin(Player player) {
        NetworkHandling.CHANNEL.sendToPlayer(new DatapackPlanetsPacket(PlanetData.planets()), player);
    }
}
