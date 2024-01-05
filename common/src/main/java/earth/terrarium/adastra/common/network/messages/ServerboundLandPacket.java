package earth.terrarium.adastra.common.network.messages;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.bytecodecs.ExtraByteCodecs;
import com.teamresourceful.resourcefullib.common.networking.base.CodecPacketHandler;
import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.api.planets.PlanetApi;
import earth.terrarium.adastra.common.entities.vehicles.Lander;
import earth.terrarium.adastra.common.entities.vehicles.Rocket;
import earth.terrarium.adastra.common.handlers.LaunchingDimensionHandler;
import earth.terrarium.adastra.common.menus.PlanetsMenu;
import earth.terrarium.adastra.common.planets.Planet;
import earth.terrarium.adastra.common.registry.ModEntityTypes;
import earth.terrarium.adastra.common.utils.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

public record ServerboundLandPacket(ResourceLocation dimensionLocation,
                                    BlockPos targetPos,
                                    boolean tryPreviousLocation) implements Packet<ServerboundLandPacket> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "land");
    public static final Handler HANDLER = new Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<ServerboundLandPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler extends CodecPacketHandler<ServerboundLandPacket> {
        public Handler() {
            super(ObjectByteCodec.create(
                ExtraByteCodecs.RESOURCE_LOCATION.fieldOf(ServerboundLandPacket::dimensionLocation),
                ExtraByteCodecs.BLOCK_POS.fieldOf(ServerboundLandPacket::targetPos),
                ByteCodec.BOOLEAN.fieldOf(ServerboundLandPacket::tryPreviousLocation),
                ServerboundLandPacket::new
            ));
        }

        @Override
        public PacketContext handle(ServerboundLandPacket packet) {
            return (player, level) -> {
                if (!(level instanceof ServerLevel serverLevel)) return;
                if (!(player.containerMenu instanceof PlanetsMenu)) return;
                Planet planet = PlanetApi.API.getPlanet(packet.dimensionLocation);
                if (planet == null) return;
                var server = serverLevel.getServer();
                boolean landingNormally = packet.tryPreviousLocation() && player.getVehicle() instanceof Rocket;
                GlobalPos newPos = landingNormally ? LaunchingDimensionHandler.getSpawningLocation(player, planet)
                    .orElse(null) : null;
                ServerLevel targetLevel = null;
                BlockPos targetPos = null;

                if (newPos != null) {
                    targetLevel = server.getLevel(newPos.dimension());
                    targetPos = newPos.pos();
                }

                if (targetLevel == null) {
                    targetLevel = server.getLevel(planet.dimension());
                    targetPos = null;
                }

                if (targetLevel == null) {
                    throw new IllegalStateException("Dimension %s does not exist! Try restarting your %s!"
                        .formatted(planet.dimension(), server.isDedicatedServer() ? "server" : "singleplayer world"));
                }

                LaunchingDimensionHandler.addSpawnLocation(player);
                Entity vehicle = player.getVehicle();

                if (targetPos != null) {
                    player.moveTo(targetPos.getX(), packet.targetPos().getY(), targetPos.getZ());
                } else if (player.blockPosition().distManhattan(packet.targetPos()) < 16) {
                    player.moveTo(packet.targetPos().getX(), packet.targetPos().getY(), packet.targetPos().getZ());
                }

                var teleportedPlayer = ModUtils.teleportToDimension(player, targetLevel);
                if (!(vehicle instanceof Rocket rocket)) return;
                Lander lander = ModEntityTypes.LANDER.get().create(targetLevel);
                if (lander == null) return;
                lander.setPos(vehicle.getX(), vehicle.getY(), vehicle.getZ());
                targetLevel.addFreshEntity(lander);
                teleportedPlayer.startRiding(lander);

                var rocketInventory = rocket.inventory();
                var landerInventory = lander.inventory();
                for (int i = 0; i < rocketInventory.getContainerSize(); i++) {
                    landerInventory.setItem(i + 1, rocketInventory.getItem(i));
                }
                landerInventory.setItem(0, rocket.getDropStack());
                rocket.discard();
            };
        }
    }
}