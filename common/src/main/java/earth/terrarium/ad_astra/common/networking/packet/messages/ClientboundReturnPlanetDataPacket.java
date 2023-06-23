package earth.terrarium.ad_astra.common.networking.packet.messages;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.client.AdAstraClient;
import earth.terrarium.ad_astra.common.data.PlanetData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record ClientboundReturnPlanetDataPacket() implements Packet<ClientboundReturnPlanetDataPacket> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "return_planet_data");
    public static final Handler HANDLER = new Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<ClientboundReturnPlanetDataPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<ClientboundReturnPlanetDataPacket> {
        @Override
        public void encode(ClientboundReturnPlanetDataPacket packet, FriendlyByteBuf buf) {
            PlanetData.writePlanetData(buf);
        }

        @Override
        public ClientboundReturnPlanetDataPacket decode(FriendlyByteBuf buf) {
            PlanetData.readPlanetData(buf);
            AdAstraClient.hasUpdatedPlanets = true;
            return new ClientboundReturnPlanetDataPacket();
        }

        @Override
        public PacketContext handle(ClientboundReturnPlanetDataPacket packet) {
            return (player, level) -> {
            };
        }
    }
}
