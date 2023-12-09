package earth.terrarium.adastra.common.network.messages;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.bytecodecs.ExtraByteCodecs;
import com.teamresourceful.resourcefullib.common.networking.base.CodecPacketHandler;
import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.blockentities.base.RedstoneControl;
import earth.terrarium.adastra.common.utils.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public record ServerboundSetRedstoneControlPacket(
    BlockPos machine, RedstoneControl redstoneControl
) implements Packet<ServerboundSetRedstoneControlPacket> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "set_redstone_control");
    public static final Handler HANDLER = new ServerboundSetRedstoneControlPacket.Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<ServerboundSetRedstoneControlPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler extends CodecPacketHandler<ServerboundSetRedstoneControlPacket> {
        public Handler() {
            super(ObjectByteCodec.create(
                ExtraByteCodecs.BLOCK_POS.fieldOf(ServerboundSetRedstoneControlPacket::machine),
                ByteCodec.ofEnum(RedstoneControl.class).fieldOf(ServerboundSetRedstoneControlPacket::redstoneControl),
                ServerboundSetRedstoneControlPacket::new
            ));
        }

        @Override
        public PacketContext handle(ServerboundSetRedstoneControlPacket packet) {
            return (player, level) -> ModUtils.getMachineFromMenuPacket(packet.machine(), player, level).ifPresent(
                machine -> machine.setRedstoneControl(packet.redstoneControl())
            );
        }
    }
}
