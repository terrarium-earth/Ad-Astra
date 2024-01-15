package earth.terrarium.adastra.common.network.messages;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.bytecodecs.ExtraByteCodecs;
import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import com.teamresourceful.resourcefullib.common.network.base.ServerboundPacketType;
import com.teamresourceful.resourcefullib.common.network.defaults.CodecPacketType;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.blockentities.machines.GravityNormalizerBlockEntity;
import earth.terrarium.adastra.common.utils.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

import java.util.function.Consumer;

public record ServerboundSetGravityNormalizerTargetParget(
    BlockPos machine, float target
) implements Packet<ServerboundSetGravityNormalizerTargetParget> {

    public static final ServerboundPacketType<ServerboundSetGravityNormalizerTargetParget> TYPE = new Type();

    @Override
    public PacketType<ServerboundSetGravityNormalizerTargetParget> type() {
        return TYPE;
    }

    private static class Type extends CodecPacketType<ServerboundSetGravityNormalizerTargetParget> implements ServerboundPacketType<ServerboundSetGravityNormalizerTargetParget> {

        public Type() {
            super(
                ServerboundSetGravityNormalizerTargetParget.class,
                new ResourceLocation(AdAstra.MOD_ID, "set_gravity_normalizer_target"),
                ObjectByteCodec.create(
                    ExtraByteCodecs.BLOCK_POS.fieldOf(ServerboundSetGravityNormalizerTargetParget::machine),
                    ByteCodec.FLOAT.fieldOf(ServerboundSetGravityNormalizerTargetParget::target),
                    ServerboundSetGravityNormalizerTargetParget::new
                )
            );
        }

        @Override
        public Consumer<Player> handle(ServerboundSetGravityNormalizerTargetParget packet) {
            return player -> ModUtils.getMachineFromMenuPacket(packet.machine(), player, player.level()).ifPresent(
                machine -> {
                    if (machine instanceof GravityNormalizerBlockEntity entity) {
                        entity.setTargetGravity(Mth.clamp(packet.target(), 0, 2.04f));
                    }
                }
            );
        }
    }
}
