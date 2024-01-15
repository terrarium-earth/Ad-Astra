package earth.terrarium.adastra.common.network.messages;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.bytecodecs.ExtraByteCodecs;
import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import com.teamresourceful.resourcefullib.common.network.base.ServerboundPacketType;
import com.teamresourceful.resourcefullib.common.network.defaults.CodecPacketType;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.blockentities.base.RedstoneControl;
import earth.terrarium.adastra.common.utils.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.function.Consumer;

public record ServerboundSetRedstoneControlPacket(
    BlockPos machine, RedstoneControl redstoneControl
) implements Packet<ServerboundSetRedstoneControlPacket> {

    public static final ServerboundPacketType<ServerboundSetRedstoneControlPacket> TYPE = new Type();

    @Override
    public PacketType<ServerboundSetRedstoneControlPacket> type() {
        return TYPE;
    }

    private static class Type extends CodecPacketType<ServerboundSetRedstoneControlPacket> implements ServerboundPacketType<ServerboundSetRedstoneControlPacket> {

        public Type() {
            super(
                ServerboundSetRedstoneControlPacket.class,
                new ResourceLocation(AdAstra.MOD_ID, "set_redstone_control"),
                ObjectByteCodec.create(
                    ExtraByteCodecs.BLOCK_POS.fieldOf(ServerboundSetRedstoneControlPacket::machine),
                    ByteCodec.ofEnum(RedstoneControl.class).fieldOf(ServerboundSetRedstoneControlPacket::redstoneControl),
                    ServerboundSetRedstoneControlPacket::new
                )
            );
        }

        @Override
        public Consumer<Player> handle(ServerboundSetRedstoneControlPacket packet) {
            return player -> ModUtils.getMachineFromMenuPacket(packet.machine(), player, player.level()).ifPresent(
                machine -> machine.setRedstoneControl(packet.redstoneControl())
            );
        }
    }
}
