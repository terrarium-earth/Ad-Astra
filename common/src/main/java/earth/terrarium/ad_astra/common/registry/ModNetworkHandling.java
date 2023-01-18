package earth.terrarium.ad_astra.common.registry;

import com.teamresourceful.resourcefullib.common.networking.NetworkChannel;
import com.teamresourceful.resourcefullib.common.networking.base.NetworkDirection;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.networking.packet.client.FlagUrlPacket;

public class ModNetworkHandling {
    public static final NetworkChannel CHANNEL = new NetworkChannel(AdAstra.MOD_ID, 0, "main");

    public static void init() {
        CHANNEL.registerPacket(NetworkDirection.CLIENT_TO_SERVER, FlagUrlPacket.ID, FlagUrlPacket.HANDLER, FlagUrlPacket.class);
    }
}
