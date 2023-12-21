package earth.terrarium.adastra.common.network.messages;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.planets.AdAstraData;
import earth.terrarium.adastra.common.planets.Planet;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public record ClientboundSyncPlanetsPacket(
    Map<ResourceLocation, Planet> planets
) implements Packet<ClientboundSyncPlanetsPacket> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "sync_planets");
    public static final Handler HANDLER = new Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<ClientboundSyncPlanetsPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<ClientboundSyncPlanetsPacket> {
        @Override
        public void encode(ClientboundSyncPlanetsPacket packet, FriendlyByteBuf buf) {
            AdAstraData.encodePlanets(buf);
        }

        @Override
        public ClientboundSyncPlanetsPacket decode(FriendlyByteBuf buf) {
            return new ClientboundSyncPlanetsPacket(AdAstraData.decodePlanets(buf).stream()
                .collect(Collectors.toMap(Planet::dimensionLocation, Function.identity())));
        }

        @Override
        public PacketContext handle(ClientboundSyncPlanetsPacket packet) {
            return (player, level) -> AdAstraData.setPlanets(packet.planets);
        }
    }
}