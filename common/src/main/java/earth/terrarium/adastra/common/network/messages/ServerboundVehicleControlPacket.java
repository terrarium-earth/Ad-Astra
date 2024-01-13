package earth.terrarium.adastra.common.network.messages;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import com.teamresourceful.resourcefullib.common.network.base.ServerboundPacketType;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.entities.vehicles.Vehicle;
import earth.terrarium.adastra.common.network.CodecPacketType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

import java.util.function.Consumer;

public record ServerboundVehicleControlPacket(float xxa, float zza) implements Packet<ServerboundVehicleControlPacket> {

    public static final ServerboundPacketType<ServerboundVehicleControlPacket> TYPE = new Type();

    @Override
    public PacketType<ServerboundVehicleControlPacket> type() {
        return TYPE;
    }

    private static class Type extends CodecPacketType<ServerboundVehicleControlPacket> implements ServerboundPacketType<ServerboundVehicleControlPacket> {

        public Type() {
            super(
                ServerboundVehicleControlPacket.class,
                new ResourceLocation(AdAstra.MOD_ID, "vehicle_control"),
                ObjectByteCodec.create(
                    ByteCodec.FLOAT.fieldOf(ServerboundVehicleControlPacket::xxa),
                    ByteCodec.FLOAT.fieldOf(ServerboundVehicleControlPacket::zza),
                    ServerboundVehicleControlPacket::new
                )
            );
        }

        @Override
        public Consumer<Player> handle(ServerboundVehicleControlPacket packet) {
            return player -> {
                if (player.getVehicle() instanceof Vehicle vehicle) {
                    vehicle.updateInput(
                        Mth.clamp(packet.xxa(), -1.0f, 1.0f),
                        Mth.clamp(packet.zza(), -1.0f, 1.0f));
                }
            };
        }
    }
}
