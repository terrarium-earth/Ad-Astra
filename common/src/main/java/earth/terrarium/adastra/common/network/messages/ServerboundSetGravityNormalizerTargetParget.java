package earth.terrarium.adastra.common.network.messages;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.bytecodecs.ExtraByteCodecs;
import com.teamresourceful.resourcefullib.common.networking.base.CodecPacketHandler;
import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.blockentities.machines.GravityNormalizerBlockEntity;
import earth.terrarium.adastra.common.utils.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public record ServerboundSetGravityNormalizerTargetParget(
    BlockPos machine, float target
) implements Packet<ServerboundSetGravityNormalizerTargetParget> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "set_gravity_normalizer_target");
    public static final Handler HANDLER = new Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<ServerboundSetGravityNormalizerTargetParget> getHandler() {
        return HANDLER;
    }

    private static class Handler extends CodecPacketHandler<ServerboundSetGravityNormalizerTargetParget> {
        public Handler() {
            super(ObjectByteCodec.create(
                ExtraByteCodecs.BLOCK_POS.fieldOf(ServerboundSetGravityNormalizerTargetParget::machine),
                ByteCodec.FLOAT.fieldOf(ServerboundSetGravityNormalizerTargetParget::target),
                ServerboundSetGravityNormalizerTargetParget::new
            ));
        }

        @Override
        public PacketContext handle(ServerboundSetGravityNormalizerTargetParget packet) {
            return (player, level) -> ModUtils.getMachineFromMenuPacket(packet.machine(), player, level).ifPresent(
                machine -> {
                    if (machine instanceof GravityNormalizerBlockEntity entity) {
                        entity.setTargetGravity(Mth.clamp(packet.target(), 0, 2.04f));
                    }
                }
            );
        }
    }
}
