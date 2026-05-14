package earth.terrarium.adastra.common.network.packets;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.bytecodecs.ExtraByteCodecs;
import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import com.teamresourceful.resourcefullib.common.network.base.ServerboundPacketType;
import com.teamresourceful.resourcefullib.common.network.defaults.CodecPacketType;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.api.planets.PlanetApi;
import earth.terrarium.adastra.common.config.AdAstraConfig;
import earth.terrarium.adastra.common.entities.vehicles.Rocket;
import earth.terrarium.adastra.common.handlers.LaunchingDimensionHandler;
import earth.terrarium.adastra.common.utils.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.function.Consumer;

public record ServerboundLandPacket(ResourceKey<Level> dimension,
                                    boolean tryPreviousLocation) implements Packet<ServerboundLandPacket> {

    public static final ServerboundPacketType<ServerboundLandPacket> TYPE = new Type();

    @Override
    public PacketType<ServerboundLandPacket> type() {
        return TYPE;
    }

    private static class Type extends CodecPacketType<ServerboundLandPacket> implements ServerboundPacketType<ServerboundLandPacket> {

        public Type() {
            super(
                ServerboundLandPacket.class,
                new ResourceLocation(AdAstra.MOD_ID, "land"),
                ObjectByteCodec.create(
                    ExtraByteCodecs.DIMENSION.fieldOf(ServerboundLandPacket::dimension),
                    ByteCodec.BOOLEAN.fieldOf(ServerboundLandPacket::tryPreviousLocation),
                    ServerboundLandPacket::new
                )
            );
        }

        @Override
        public Consumer<Player> handle(ServerboundLandPacket packet) {
            return player -> {
                if (!(player.level() instanceof ServerLevel serverLevel)) return;
                var planet = PlanetApi.API.getPlanet(packet.dimension);
                if (planet == null) return; // Only allow teleporting to registered planets.

                if (planet.isSpace()) return; // Shouldn't be able to land in any orbit.
                if (!ModUtils.canTeleportToPlanet(player, planet)) return;

                boolean landingNormally = packet.tryPreviousLocation() && player.getVehicle() instanceof Rocket;
                GlobalPos newPos = landingNormally ? LaunchingDimensionHandler.getSpawningLocation(player, serverLevel, planet)
                    .orElse(null) : null;

                var server = serverLevel.getServer();
                ServerLevel targetLevel = newPos == null ? server.getLevel(planet.dimension()) : server.getLevel(newPos.dimension());
                if (targetLevel == null) {
                    throw new IllegalStateException(String.format("Dimension %s does not exist! Try restarting your %s!",
                        planet.dimension(), server.isDedicatedServer() ? "server" : "singleplayer world"));
                }

                LaunchingDimensionHandler.addSpawnLocation(player, serverLevel);
                BlockPos targetPos = newPos != null ? newPos.pos() : player.blockPosition();
                ModUtils.land((ServerPlayer) player, targetLevel, new Vec3(targetPos.getX(), AdAstraConfig.atmosphereLeave, targetPos.getZ()));
            };
        }
    }
}