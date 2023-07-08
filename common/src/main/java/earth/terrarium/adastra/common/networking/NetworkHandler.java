package earth.terrarium.adastra.common.networking;

import com.teamresourceful.resourcefullib.common.networking.NetworkChannel;
import com.teamresourceful.resourcefullib.common.networking.base.NetworkDirection;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.networking.messages.ClientboundSyncLocalPlanetDataPacket;
import earth.terrarium.adastra.common.networking.messages.ClientboundSyncPlanetsPacket;
import earth.terrarium.adastra.common.networking.messages.ServerboundSyncKeybindPacket;

public final class NetworkHandler {
    public static final NetworkChannel CHANNEL = new NetworkChannel(AdAstra.MOD_ID, 1, "main");

    public static void init() {
        CHANNEL.registerPacket(NetworkDirection.SERVER_TO_CLIENT, ClientboundSyncPlanetsPacket.ID, ClientboundSyncPlanetsPacket.HANDLER, ClientboundSyncPlanetsPacket.class);
        CHANNEL.registerPacket(NetworkDirection.SERVER_TO_CLIENT, ClientboundSyncLocalPlanetDataPacket.ID, ClientboundSyncLocalPlanetDataPacket.HANDLER, ClientboundSyncLocalPlanetDataPacket.class);

        CHANNEL.registerPacket(NetworkDirection.SERVER_TO_CLIENT, ServerboundSyncKeybindPacket.ID, ServerboundSyncKeybindPacket.HANDLER, ServerboundSyncKeybindPacket.class);
    }
}
