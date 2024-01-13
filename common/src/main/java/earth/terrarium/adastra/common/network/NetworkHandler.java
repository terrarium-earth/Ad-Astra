package earth.terrarium.adastra.common.network;

import com.teamresourceful.resourcefullib.common.network.NetworkChannel;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.network.messages.*;

public final class NetworkHandler {
    public static final NetworkChannel CHANNEL = new NetworkChannel(AdAstra.MOD_ID, 1, "main");

    public static void init() {
        CHANNEL.register(ClientboundSyncPlanetsPacket.TYPE);
        CHANNEL.register(ClientboundSyncLocalPlanetDataPacket.TYPE);
        CHANNEL.register(ClientboundSendStationsPacket.TYPE);
        CHANNEL.register(ClientboundPlayStationPacket.TYPE);

        CHANNEL.register(ServerboundRequestStationsPacket.TYPE);
        CHANNEL.register(ServerboundSetStationPacket.TYPE);
        CHANNEL.register(ServerboundClearFluidTankPacket.TYPE);
        CHANNEL.register(ServerboundSetSideConfigPacket.TYPE);
        CHANNEL.register(ServerboundResetSideConfigPacket.TYPE);
        CHANNEL.register(ServerboundSetRedstoneControlPacket.TYPE);
        CHANNEL.register(ServerboundSetFurnaceModePacket.TYPE);
        CHANNEL.register(ServerboundSetGravityNormalizerTargetParget.TYPE);
        CHANNEL.register(ServerboundSetFlagUrlPacket.TYPE);
        CHANNEL.register(ServerboundVehicleControlPacket.TYPE);
        CHANNEL.register(ServerboundLandPacket.TYPE);
        CHANNEL.register(ServerboundLandOnSpaceStationPacket.TYPE);
        CHANNEL.register(ServerboundConstructSpaceStationPacket.TYPE);
        CHANNEL.register(ServerboundSyncKeybindPacket.TYPE);
    }
}
