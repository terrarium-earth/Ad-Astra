package earth.terrarium.ad_astra.common.networking.packet.server;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.client.AdAstraClient;
import earth.terrarium.ad_astra.common.data.PlanetData;
import earth.terrarium.ad_astra.common.screen.menu.AbstractMachineMenu;
import earth.terrarium.ad_astra.common.screen.menu.AbstractVehicleMenu;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record ReturnPlanetDataPacket() implements Packet<ReturnPlanetDataPacket> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "return_planet_data_packet");
    public static final Handler HANDLER = new Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<ReturnPlanetDataPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<ReturnPlanetDataPacket> {
        @Override
        public void encode(ReturnPlanetDataPacket packet, FriendlyByteBuf buf) {
            PlanetData.writePlanetData(buf);
        }

        @Override
        public ReturnPlanetDataPacket decode(FriendlyByteBuf buf) {
            PlanetData.readPlanetData(buf);
            AdAstraClient.hasUpdatedPlanets = true;
            return new ReturnPlanetDataPacket();
        }

        @Override
        public PacketContext handle(ReturnPlanetDataPacket packet) {
            return (player, level) -> {
            };
        }
    }
}
