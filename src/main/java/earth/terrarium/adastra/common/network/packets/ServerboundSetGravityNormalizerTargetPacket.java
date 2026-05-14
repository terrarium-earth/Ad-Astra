package earth.terrarium.adastra.common.network.packets;

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

public record ServerboundSetGravityNormalizerTargetPacket(
    BlockPos machine, float target
) implements Packet<ServerboundSetGravityNormalizerTargetPacket> {

    public static final ServerboundPacketType<ServerboundSetGravityNormalizerTargetPacket> TYPE = new Type();

    @Override
    public PacketType<ServerboundSetGravityNormalizerTargetPacket> type() {
        return TYPE;
    }

    private static class Type extends CodecPacketType<ServerboundSetGravityNormalizerTargetPacket> implements ServerboundPacketType<ServerboundSetGravityNormalizerTargetPacket> {

        public Type() {
            super(
                ServerboundSetGravityNormalizerTargetPacket.class,
                new ResourceLocation(AdAstra.MOD_ID, "set_gravity_normalizer_target"),
                ObjectByteCodec.create(
                    ExtraByteCodecs.BLOCK_POS.fieldOf(ServerboundSetGravityNormalizerTargetPacket::machine),
                    ByteCodec.FLOAT.fieldOf(ServerboundSetGravityNormalizerTargetPacket::target),
                    ServerboundSetGravityNormalizerTargetPacket::new
                )
            );
        }

        @Override
        public Consumer<Player> handle(ServerboundSetGravityNormalizerTargetPacket packet) {
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
