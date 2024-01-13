package earth.terrarium.adastra.common.network.messages;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.bytecodecs.ExtraByteCodecs;
import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import com.teamresourceful.resourcefullib.common.network.base.ServerboundPacketType;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.network.CodecPacketType;
import earth.terrarium.adastra.common.utils.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.function.Consumer;

public record ServerboundResetSideConfigPacket(
    BlockPos machine, int configIndex
) implements Packet<ServerboundResetSideConfigPacket> {

    public static final ServerboundPacketType<ServerboundResetSideConfigPacket> TYPE = new Type();

    @Override
    public PacketType<ServerboundResetSideConfigPacket> type() {
        return TYPE;
    }

    private static class Type extends CodecPacketType<ServerboundResetSideConfigPacket> implements ServerboundPacketType<ServerboundResetSideConfigPacket> {

        public Type() {
            super(
                ServerboundResetSideConfigPacket.class,
                new ResourceLocation(AdAstra.MOD_ID, "reset_side_config"),
                ObjectByteCodec.create(
                    ExtraByteCodecs.BLOCK_POS.fieldOf(ServerboundResetSideConfigPacket::machine),
                    ByteCodec.INT.fieldOf(ServerboundResetSideConfigPacket::configIndex),
                    ServerboundResetSideConfigPacket::new
                )
            );
        }

        @Override
        public Consumer<Player> handle(ServerboundResetSideConfigPacket packet) {
            return player -> ModUtils.getMachineFromMenuPacket(packet.machine(), player, player.level()).ifPresent(
                machine -> machine.resetToDefault(packet.configIndex())
            );
        }
    }
}
