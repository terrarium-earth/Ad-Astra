package earth.terrarium.adastra.common.network.messages;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.bytecodecs.ExtraByteCodecs;
import com.teamresourceful.resourcefullib.common.networking.base.CodecPacketHandler;
import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.Configuration;
import earth.terrarium.adastra.common.utils.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

public record ServerboundSetSideConfigPacket(
    BlockPos machine, int configIndex,
    Direction direction, Configuration configuration
) implements Packet<ServerboundSetSideConfigPacket> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "set_side_config");
    public static final Handler HANDLER = new Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<ServerboundSetSideConfigPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler extends CodecPacketHandler<ServerboundSetSideConfigPacket> {
        public Handler() {
            super(ObjectByteCodec.create(
                ExtraByteCodecs.BLOCK_POS.fieldOf(ServerboundSetSideConfigPacket::machine),
                ByteCodec.INT.fieldOf(ServerboundSetSideConfigPacket::configIndex),
                ByteCodec.ofEnum(Direction.class).fieldOf(ServerboundSetSideConfigPacket::direction),
                ByteCodec.ofEnum(Configuration.class).fieldOf(ServerboundSetSideConfigPacket::configuration),
                ServerboundSetSideConfigPacket::new
            ));
        }

        @Override
        public PacketContext handle(ServerboundSetSideConfigPacket packet) {
            return (player, level) -> ModUtils.getMachineFromMenuPacket(packet.machine(), player, level).ifPresent(
                machine -> {
                    var sideConfig = machine.getSideConfig();
                    sideConfig.get(packet.configIndex()).set(packet.direction(), packet.configuration());
                    machine.setChanged();
                }
            );
        }
    }
}
