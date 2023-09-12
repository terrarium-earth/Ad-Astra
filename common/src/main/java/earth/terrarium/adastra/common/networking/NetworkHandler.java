package earth.terrarium.adastra.common.networking;

import com.teamresourceful.resourcefullib.common.networking.NetworkChannel;
import com.teamresourceful.resourcefullib.common.networking.base.NetworkDirection;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.networking.messages.*;

public final class NetworkHandler {
    public static final NetworkChannel CHANNEL = new NetworkChannel(AdAstra.MOD_ID, 1, "main");

    public static void init() {
        CHANNEL.registerPacket(NetworkDirection.SERVER_TO_CLIENT, ClientboundSyncPlanetsPacket.ID, ClientboundSyncPlanetsPacket.HANDLER, ClientboundSyncPlanetsPacket.class);
        CHANNEL.registerPacket(NetworkDirection.SERVER_TO_CLIENT, ClientboundSyncLocalPlanetDataPacket.ID, ClientboundSyncLocalPlanetDataPacket.HANDLER, ClientboundSyncLocalPlanetDataPacket.class);
        CHANNEL.registerPacket(NetworkDirection.SERVER_TO_CLIENT, ClientboundSyncWeatherPacket.ID, ClientboundSyncWeatherPacket.HANDLER, ClientboundSyncWeatherPacket.class);
        CHANNEL.registerPacket(NetworkDirection.SERVER_TO_CLIENT, ServerboundSyncKeybindPacket.ID, ServerboundSyncKeybindPacket.HANDLER, ServerboundSyncKeybindPacket.class);
        CHANNEL.registerPacket(NetworkDirection.SERVER_TO_CLIENT, ClientboundSendStationsPacket.ID, ClientboundSendStationsPacket.HANDLER, ClientboundSendStationsPacket.class);
        CHANNEL.registerPacket(NetworkDirection.SERVER_TO_CLIENT, ClientboundPlayStationPacket.ID, ClientboundPlayStationPacket.HANDLER, ClientboundPlayStationPacket.class);

        CHANNEL.registerPacket(NetworkDirection.CLIENT_TO_SERVER, ServerboundRequestStationsPacket.ID, ServerboundRequestStationsPacket.HANDLER, ServerboundRequestStationsPacket.class);
        CHANNEL.registerPacket(NetworkDirection.CLIENT_TO_SERVER, ServerboundSetStationPacket.ID, ServerboundSetStationPacket.HANDLER, ServerboundSetStationPacket.class);
        CHANNEL.registerPacket(NetworkDirection.CLIENT_TO_SERVER, ServerboundClearFluidTankPacket.ID, ServerboundClearFluidTankPacket.HANDLER, ServerboundClearFluidTankPacket.class);
        CHANNEL.registerPacket(NetworkDirection.CLIENT_TO_SERVER, ServerboundSetSideConfigPacket.ID, ServerboundSetSideConfigPacket.HANDLER, ServerboundSetSideConfigPacket.class);
        CHANNEL.registerPacket(NetworkDirection.CLIENT_TO_SERVER, ServerboundResetSideConfigPacket.ID, ServerboundResetSideConfigPacket.HANDLER, ServerboundResetSideConfigPacket.class);
        CHANNEL.registerPacket(NetworkDirection.CLIENT_TO_SERVER, ServerboundSetRedstoneControlPacket.ID, ServerboundSetRedstoneControlPacket.HANDLER, ServerboundSetRedstoneControlPacket.class);
    }
}
