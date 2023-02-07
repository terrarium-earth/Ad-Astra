package earth.terrarium.ad_astra.common.networking.packet.client;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.util.ModUtils;
import earth.terrarium.ad_astra.common.config.AdAstraConfig;
import earth.terrarium.ad_astra.common.entity.vehicle.Rocket;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

import java.util.List;

public record TeleportToPlanetPacket(ResourceLocation id) implements Packet<TeleportToPlanetPacket> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "teleport_to_planet_packet");
    public static final Handler HANDLER = new Handler();

    private static ResourceKey<Level> getlevel(ResourceLocation id) {
        ResourceKey<Level> targetDimension = ResourceKey.create(Registry.DIMENSION_REGISTRY, id);
        // Change the "earth" registry key to the "overworld" registry key.
        if (targetDimension.location().equals(new ResourceLocation(AdAstra.MOD_ID, "earth"))) {
            targetDimension = Level.OVERWORLD;
        }
        return targetDimension;
    }

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<TeleportToPlanetPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<TeleportToPlanetPacket> {
        @Override
        public void encode(TeleportToPlanetPacket packet, FriendlyByteBuf buf) {
            buf.writeResourceLocation(packet.id);
        }

        @Override
        public TeleportToPlanetPacket decode(FriendlyByteBuf buf) {
            return new TeleportToPlanetPacket(buf.readResourceLocation());
        }

        @Override
        public PacketContext handle(TeleportToPlanetPacket packet) {
            return (player, level) -> {
                List<String> disabledPlanets = List.of(AdAstraConfig.disabledPlanets.split(","));

                if (!disabledPlanets.contains(packet.id().toString())) {
                    if (player.getVehicle() instanceof Rocket) {
                        ModUtils.teleportToLevel(getlevel(packet.id()), player);
                    } else if (player.canUseGameMasterBlocks()) {
                        ModUtils.teleportPlayer(getlevel(packet.id()), (ServerPlayer) player);
                    }
                }
            };
        }
    }
}
