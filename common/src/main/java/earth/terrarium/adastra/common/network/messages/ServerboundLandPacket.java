package earth.terrarium.adastra.common.network.messages;

import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.bytecodecs.ExtraByteCodecs;
import com.teamresourceful.resourcefullib.common.networking.base.CodecPacketHandler;
import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.entities.vehicles.Lander;
import earth.terrarium.adastra.common.entities.vehicles.Rocket;
import earth.terrarium.adastra.common.menus.PlanetsMenu;
import earth.terrarium.adastra.common.planets.AdAstraData;
import earth.terrarium.adastra.common.planets.Planet;
import earth.terrarium.adastra.common.registry.ModEntityTypes;
import earth.terrarium.adastra.common.utils.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

public record ServerboundLandPacket(ResourceLocation dimensionLocation,
                                    BlockPos targetPos) implements Packet<ServerboundLandPacket> {

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
                ServerboundLandPacket::new
            ));
        }

        @Override
        public PacketContext handle(ServerboundLandPacket packet) {
            return (player, level) -> {
                if (!(level instanceof ServerLevel serverLevel)) return;
                if (!(player.containerMenu instanceof PlanetsMenu)) return;
                Planet planet = AdAstraData.getPlanet(packet.dimensionLocation);
                if (planet == null) return;
                var server = serverLevel.getServer();
                ServerLevel targetLevel = server.getLevel(planet.dimension());
                if (targetLevel == null)
                    throw new IllegalStateException("Dimension %s does not exist! Try restarting your %s!"
                        .formatted(planet.dimension(), server.isDedicatedServer() ? "server" : "singleplayer world"));
                Entity vehicle = player.getVehicle();

                if (player.blockPosition().distManhattan(packet.targetPos()) < 16) {
                    player.moveTo(packet.targetPos().getX(), packet.targetPos().getY(), packet.targetPos().getZ());
                }

                var teleportedPlayer = ModUtils.teleportToDimension(player, targetLevel);
                if (!(vehicle instanceof Rocket rocket)) return;
                Lander lander = ModEntityTypes.LANDER.get().create(targetLevel);
                if (lander == null) return;
                lander.setPos(teleportedPlayer.getX(), teleportedPlayer.getY(), teleportedPlayer.getZ());
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