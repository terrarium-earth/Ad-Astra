package earth.terrarium.adastra.common.network;

import com.teamresourceful.resourcefullib.common.network.Network;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.network.messages.*;
import net.minecraft.resources.ResourceLocation;

public final class NetworkHandler {
    public static final Network CHANNEL = new Network(new ResourceLocation(AdAstra.MOD_ID, "main"), 1);

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
        CHANNEL.register(ServerboundSetGravityNormalizerTargetPacket.TYPE);
        CHANNEL.register(ServerboundSetFlagUrlPacket.TYPE);
        CHANNEL.register(ServerboundVehicleControlPacket.TYPE);
        CHANNEL.register(ServerboundLandPacket.TYPE);
        CHANNEL.register(ServerboundLandOnSpaceStationPacket.TYPE);
        CHANNEL.register(ServerboundConstructSpaceStationPacket.TYPE);
        CHANNEL.register(ServerboundSyncKeybindPacket.TYPE);
    }
}
