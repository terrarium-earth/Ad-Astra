package earth.terrarium.ad_astra.common.networking;

import com.teamresourceful.resourcefullib.common.networking.NetworkChannel;
import com.teamresourceful.resourcefullib.common.networking.base.NetworkDirection;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.networking.packet.messages.*;

public class NetworkHandler {
    public static final NetworkChannel CHANNEL = new NetworkChannel(AdAstra.MOD_ID, 0, "main");

    public static void init() {
        CHANNEL.registerPacket(NetworkDirection.CLIENT_TO_SERVER, ServerboundKeybindPacket.ID, ServerboundKeybindPacket.HANDLER, ServerboundKeybindPacket.class);
        CHANNEL.registerPacket(NetworkDirection.CLIENT_TO_SERVER, ServerboundLaunchRocketPacket.ID, ServerboundLaunchRocketPacket.HANDLER, ServerboundLaunchRocketPacket.class);
        CHANNEL.registerPacket(NetworkDirection.CLIENT_TO_SERVER, ServerboundToggleDistributorPacket.ID, ServerboundToggleDistributorPacket.HANDLER, ServerboundToggleDistributorPacket.class);
        CHANNEL.registerPacket(NetworkDirection.CLIENT_TO_SERVER, ServerboundTeleportToPlanetPacket.ID, ServerboundTeleportToPlanetPacket.HANDLER, ServerboundTeleportToPlanetPacket.class);
        CHANNEL.registerPacket(NetworkDirection.CLIENT_TO_SERVER, ServerboundCreateSpaceStationPacket.ID, ServerboundCreateSpaceStationPacket.HANDLER, ServerboundCreateSpaceStationPacket.class);
        CHANNEL.registerPacket(NetworkDirection.CLIENT_TO_SERVER, ServerboundFlagUrlPacket.ID, ServerboundFlagUrlPacket.HANDLER, ServerboundFlagUrlPacket.class);
        CHANNEL.registerPacket(NetworkDirection.CLIENT_TO_SERVER, ServerboundNotifyRecipeTransferPacket.ID, ServerboundNotifyRecipeTransferPacket.HANDLER, ServerboundNotifyRecipeTransferPacket.class);
        CHANNEL.registerPacket(NetworkDirection.CLIENT_TO_SERVER, ServerboundRequestPlanetDataPacket.ID, ServerboundRequestPlanetDataPacket.HANDLER, ServerboundRequestPlanetDataPacket.class);

        CHANNEL.registerPacket(NetworkDirection.SERVER_TO_CLIENT, ClientboundStartRocketPacket.ID, ClientboundStartRocketPacket.HANDLER, ClientboundStartRocketPacket.class);
        CHANNEL.registerPacket(NetworkDirection.SERVER_TO_CLIENT, ClientboundMachineInfoPacket.ID, ClientboundMachineInfoPacket.HANDLER, ClientboundMachineInfoPacket.class);
        CHANNEL.registerPacket(NetworkDirection.SERVER_TO_CLIENT, ClientboundReturnPlanetDataPacket.ID, ClientboundReturnPlanetDataPacket.HANDLER, ClientboundReturnPlanetDataPacket.class);
    }
}
