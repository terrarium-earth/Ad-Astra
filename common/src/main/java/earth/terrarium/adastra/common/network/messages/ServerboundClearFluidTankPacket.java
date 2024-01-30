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
import earth.terrarium.botarium.common.fluid.base.BotariumFluidBlock;
import earth.terrarium.botarium.common.fluid.base.FluidContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

import java.util.function.Consumer;

public record ServerboundClearFluidTankPacket(
    BlockPos machine, int tank
) implements Packet<ServerboundClearFluidTankPacket> {

    public static final ServerboundPacketType<ServerboundClearFluidTankPacket> TYPE = new Type();

    @Override
    public PacketType<ServerboundClearFluidTankPacket> type() {
        return TYPE;
    }

    private static class Type extends CodecPacketType<ServerboundClearFluidTankPacket> implements ServerboundPacketType<ServerboundClearFluidTankPacket> {

        public Type() {
            super(
                ServerboundClearFluidTankPacket.class,
                new ResourceLocation(AdAstra.MOD_ID, "clear_fluid_tank"),
                ObjectByteCodec.create(
                    ExtraByteCodecs.BLOCK_POS.fieldOf(ServerboundClearFluidTankPacket::machine),
                    ByteCodec.INT.fieldOf(ServerboundClearFluidTankPacket::tank),
                    ServerboundClearFluidTankPacket::new
                )
            );
        }

        @Override
        public Consumer<Player> handle(ServerboundClearFluidTankPacket packet) {
            return player -> ModUtils.getMachineFromMenuPacket(packet.machine(), player, player.level()).ifPresent(
                machine -> {
                    if (!(machine instanceof BotariumFluidBlock<?>)) return;
                    FluidContainer container = FluidContainer.of(machine, null);
                    if (container == null) return;
                    int tank = Mth.clamp(packet.tank(), 0, container.getSize() - 1);
                    container.internalExtract(container.getFluids().get(tank), false);
                    container.extractFluid(container.getFluids().get(tank), false);
                }
            );
        }
    }
}
