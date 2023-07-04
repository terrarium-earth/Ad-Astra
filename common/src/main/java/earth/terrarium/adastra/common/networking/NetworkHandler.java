package earth.terrarium.adastra.common.networking;

import com.teamresourceful.resourcefullib.common.networking.NetworkChannel;
import com.teamresourceful.resourcefullib.common.networking.base.NetworkDirection;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.networking.messages.ClientboundSyncPlanetsPacket;
import earth.terrarium.adastra.common.networking.messages.ClientboundSyncPlayerGravityPacket;

public final class NetworkHandler {
    public static final NetworkChannel CHANNEL = new NetworkChannel(AdAstra.MOD_ID, 1, "main");

    public static void init() {
        CHANNEL.registerPacket(NetworkDirection.SERVER_TO_CLIENT, ClientboundSyncPlanetsPacket.ID, ClientboundSyncPlanetsPacket.HANDLER, ClientboundSyncPlanetsPacket.class);
        CHANNEL.registerPacket(NetworkDirection.SERVER_TO_CLIENT, ClientboundSyncPlayerGravityPacket.ID, ClientboundSyncPlayerGravityPacket.HANDLER, ClientboundSyncPlayerGravityPacket.class);
    }
}
