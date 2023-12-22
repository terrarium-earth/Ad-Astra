package earth.terrarium.adastra.common.network.messages;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.networking.base.CodecPacketHandler;
import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.entities.vehicles.Vehicle;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public record ServerboundVehicleControlPacket(float xxa, float zza) implements Packet<ServerboundVehicleControlPacket> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "vehicle_control");
    public static final Handler HANDLER = new Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<ServerboundVehicleControlPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler extends CodecPacketHandler<ServerboundVehicleControlPacket> {
        public Handler() {
            super(ObjectByteCodec.create(
                ByteCodec.FLOAT.fieldOf(ServerboundVehicleControlPacket::xxa),
                ByteCodec.FLOAT.fieldOf(ServerboundVehicleControlPacket::zza),
                ServerboundVehicleControlPacket::new
            ));
        }

        @Override
        public PacketContext handle(ServerboundVehicleControlPacket packet) {
            return (player, level) -> {
                if (player.getVehicle() instanceof Vehicle vehicle) {
                    vehicle.updateInput(
                        Mth.clamp(packet.xxa(), -1.0f, 1.0f),
                        Mth.clamp(packet.zza(), -1.0f, 1.0f));
                }
            };
        }
    }
}