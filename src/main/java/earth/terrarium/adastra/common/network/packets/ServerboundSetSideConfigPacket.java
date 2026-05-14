package earth.terrarium.adastra.common.network.packets;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.bytecodecs.ExtraByteCodecs;
import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import com.teamresourceful.resourcefullib.common.network.base.ServerboundPacketType;
import com.teamresourceful.resourcefullib.common.network.defaults.CodecPacketType;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.Configuration;
import earth.terrarium.adastra.common.utils.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.function.Consumer;

public record ServerboundSetSideConfigPacket(
    BlockPos machine, int configIndex,
    Direction direction, Configuration configuration
) implements Packet<ServerboundSetSideConfigPacket> {

    public static final ServerboundPacketType<ServerboundSetSideConfigPacket> TYPE = new Type();

    @Override
    public PacketType<ServerboundSetSideConfigPacket> type() {
        return TYPE;
    }

    private static class Type extends CodecPacketType<ServerboundSetSideConfigPacket> implements ServerboundPacketType<ServerboundSetSideConfigPacket> {

        public Type() {
            super(
                ServerboundSetSideConfigPacket.class,
                new ResourceLocation(AdAstra.MOD_ID, "set_side_config"),
                ObjectByteCodec.create(
                    ExtraByteCodecs.BLOCK_POS.fieldOf(ServerboundSetSideConfigPacket::machine),
                    ByteCodec.INT.fieldOf(ServerboundSetSideConfigPacket::configIndex),
                    ByteCodec.ofEnum(Direction.class).fieldOf(ServerboundSetSideConfigPacket::direction),
                    ByteCodec.ofEnum(Configuration.class).fieldOf(ServerboundSetSideConfigPacket::configuration),
                    ServerboundSetSideConfigPacket::new
                )
            );
        }

        @Override
        public Consumer<Player> handle(ServerboundSetSideConfigPacket packet) {
            return player -> ModUtils.getMachineFromMenuPacket(packet.machine(), player, player.level()).ifPresent(
                machine -> {
                    var sideConfig = machine.getSideConfig();
                    if (sideConfig.size() <= packet.configIndex()) return;
                    sideConfig.get(packet.configIndex()).set(packet.direction(), packet.configuration());
                    machine.setChanged();
                }
            );
        }
    }
}
