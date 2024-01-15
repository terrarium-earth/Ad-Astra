package earth.terrarium.adastra.common.network.messages;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.bytecodecs.ExtraByteCodecs;
import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import com.teamresourceful.resourcefullib.common.network.base.ServerboundPacketType;
import com.teamresourceful.resourcefullib.common.network.defaults.CodecPacketType;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.blockentities.machines.EtrionicBlastFurnaceBlockEntity;
import earth.terrarium.adastra.common.utils.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.function.Consumer;

public record ServerboundSetFurnaceModePacket(
    BlockPos machine, EtrionicBlastFurnaceBlockEntity.Mode mode
) implements Packet<ServerboundSetFurnaceModePacket> {

    public static final ServerboundPacketType<ServerboundSetFurnaceModePacket> TYPE = new Type();

    @Override
    public PacketType<ServerboundSetFurnaceModePacket> type() {
        return TYPE;
    }

    private static class Type extends CodecPacketType<ServerboundSetFurnaceModePacket> implements ServerboundPacketType<ServerboundSetFurnaceModePacket> {

        public Type() {
            super(
                ServerboundSetFurnaceModePacket.class,
                new ResourceLocation(AdAstra.MOD_ID, "set_furnace_mode"),
                ObjectByteCodec.create(
                    ExtraByteCodecs.BLOCK_POS.fieldOf(ServerboundSetFurnaceModePacket::machine),
                    ByteCodec.ofEnum(EtrionicBlastFurnaceBlockEntity.Mode.class).fieldOf(ServerboundSetFurnaceModePacket::mode),
                    ServerboundSetFurnaceModePacket::new
                )
            );
        }

        @Override
        public Consumer<Player> handle(ServerboundSetFurnaceModePacket packet) {
            return player -> ModUtils.getMachineFromMenuPacket(packet.machine(), player, player.level()).ifPresent(
                machine -> {
                    if (machine instanceof EtrionicBlastFurnaceBlockEntity entity) {
                        entity.setMode(packet.mode());
                        entity.clearAlloyingRecipe();
                        entity.update();
                    }
                }
            );
        }
    }
}
