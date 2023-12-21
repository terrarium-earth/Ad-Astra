package earth.terrarium.adastra.common.network.messages;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.bytecodecs.ExtraByteCodecs;
import com.teamresourceful.resourcefullib.common.networking.base.CodecPacketHandler;
import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.utils.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public record ServerboundResetSideConfigPacket(
    BlockPos machine, int configIndex
) implements Packet<ServerboundResetSideConfigPacket> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "reset_side_config");
    public static final Handler HANDLER = new Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<ServerboundResetSideConfigPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler extends CodecPacketHandler<ServerboundResetSideConfigPacket> {
        public Handler() {
            super(ObjectByteCodec.create(
                ExtraByteCodecs.BLOCK_POS.fieldOf(ServerboundResetSideConfigPacket::machine),
                ByteCodec.INT.fieldOf(ServerboundResetSideConfigPacket::configIndex),
                ServerboundResetSideConfigPacket::new
            ));
        }

        @Override
        public PacketContext handle(ServerboundResetSideConfigPacket packet) {
            return (player, level) -> ModUtils.getMachineFromMenuPacket(packet.machine(), player, level).ifPresent(
                machine -> machine.resetToDefault(packet.configIndex())
            );
        }
    }
}
