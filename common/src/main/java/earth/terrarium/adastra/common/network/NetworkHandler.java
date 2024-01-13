package earth.terrarium.adastra.common.network;

import com.teamresourceful.resourcefullib.common.networking.NetworkChannel;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.network.messages.*;
import net.minecraft.network.protocol.PacketFlow;

public final class NetworkHandler {
    public static final NetworkChannel CHANNEL = new NetworkChannel(AdAstra.MOD_ID, 1, "main");

    public static void init() {
        CHANNEL.registerPacket(PacketFlow.CLIENTBOUND, ClientboundSyncPlanetsPacket.ID, ClientboundSyncPlanetsPacket.HANDLER, ClientboundSyncPlanetsPacket.class);
        CHANNEL.registerPacket(PacketFlow.CLIENTBOUND, ClientboundSyncLocalPlanetDataPacket.ID, ClientboundSyncLocalPlanetDataPacket.HANDLER, ClientboundSyncLocalPlanetDataPacket.class);
        CHANNEL.registerPacket(PacketFlow.CLIENTBOUND, ClientboundSendStationsPacket.ID, ClientboundSendStationsPacket.HANDLER, ClientboundSendStationsPacket.class);
        CHANNEL.registerPacket(PacketFlow.CLIENTBOUND, ClientboundPlayStationPacket.ID, ClientboundPlayStationPacket.HANDLER, ClientboundPlayStationPacket.class);

        CHANNEL.registerPacket(PacketFlow.SERVERBOUND, ServerboundRequestStationsPacket.ID, ServerboundRequestStationsPacket.HANDLER, ServerboundRequestStationsPacket.class);
        CHANNEL.registerPacket(PacketFlow.SERVERBOUND, ServerboundSetStationPacket.ID, ServerboundSetStationPacket.HANDLER, ServerboundSetStationPacket.class);
        CHANNEL.registerPacket(PacketFlow.SERVERBOUND, ServerboundClearFluidTankPacket.ID, ServerboundClearFluidTankPacket.HANDLER, ServerboundClearFluidTankPacket.class);
        CHANNEL.registerPacket(PacketFlow.SERVERBOUND, ServerboundSetSideConfigPacket.ID, ServerboundSetSideConfigPacket.HANDLER, ServerboundSetSideConfigPacket.class);
        CHANNEL.registerPacket(PacketFlow.SERVERBOUND, ServerboundResetSideConfigPacket.ID, ServerboundResetSideConfigPacket.HANDLER, ServerboundResetSideConfigPacket.class);
        CHANNEL.registerPacket(PacketFlow.SERVERBOUND, ServerboundSetRedstoneControlPacket.ID, ServerboundSetRedstoneControlPacket.HANDLER, ServerboundSetRedstoneControlPacket.class);
        CHANNEL.registerPacket(PacketFlow.SERVERBOUND, ServerboundSetFurnaceModePacket.ID, ServerboundSetFurnaceModePacket.HANDLER, ServerboundSetFurnaceModePacket.class);
        CHANNEL.registerPacket(PacketFlow.SERVERBOUND, ServerboundSetGravityNormalizerTargetParget.ID, ServerboundSetGravityNormalizerTargetParget.HANDLER, ServerboundSetGravityNormalizerTargetParget.class);
        CHANNEL.registerPacket(PacketFlow.SERVERBOUND, ServerboundSetFlagUrlPacket.ID, ServerboundSetFlagUrlPacket.HANDLER, ServerboundSetFlagUrlPacket.class);
        CHANNEL.registerPacket(PacketFlow.SERVERBOUND, ServerboundVehicleControlPacket.ID, ServerboundVehicleControlPacket.HANDLER, ServerboundVehicleControlPacket.class);
        CHANNEL.registerPacket(PacketFlow.SERVERBOUND, ServerboundLandPacket.ID, ServerboundLandPacket.HANDLER, ServerboundLandPacket.class);
        CHANNEL.registerPacket(PacketFlow.SERVERBOUND, ServerboundLandOnSpaceStationPacket.ID, ServerboundLandOnSpaceStationPacket.HANDLER, ServerboundLandOnSpaceStationPacket.class);
        CHANNEL.registerPacket(PacketFlow.SERVERBOUND, ServerboundConstructSpaceStationPacket.ID, ServerboundConstructSpaceStationPacket.HANDLER, ServerboundConstructSpaceStationPacket.class);
        CHANNEL.registerPacket(PacketFlow.SERVERBOUND, ServerboundSyncKeybindPacket.ID, ServerboundSyncKeybindPacket.HANDLER, ServerboundSyncKeybindPacket.class);
    }
}
