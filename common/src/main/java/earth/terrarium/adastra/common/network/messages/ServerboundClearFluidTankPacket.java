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
import earth.terrarium.botarium.common.fluid.base.BotariumFluidBlock;
import earth.terrarium.botarium.common.fluid.base.FluidContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public record ServerboundClearFluidTankPacket(
    BlockPos machine, int tank
) implements Packet<ServerboundClearFluidTankPacket> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "clear_fluid_tank");
    public static final Handler HANDLER = new ServerboundClearFluidTankPacket.Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<ServerboundClearFluidTankPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler extends CodecPacketHandler<ServerboundClearFluidTankPacket> {
        public Handler() {
            super(ObjectByteCodec.create(
                ExtraByteCodecs.BLOCK_POS.fieldOf(ServerboundClearFluidTankPacket::machine),
                ByteCodec.INT.fieldOf(ServerboundClearFluidTankPacket::tank),
                ServerboundClearFluidTankPacket::new
            ));
        }

        @Override
        public PacketContext handle(ServerboundClearFluidTankPacket packet) {
            return (player, level) -> ModUtils.getMachineFromMenuPacket(packet.machine(), player, level).ifPresent(
                machine -> {
                    if (!(machine instanceof BotariumFluidBlock<?> fluidBlock)) return;
                    FluidContainer container = fluidBlock.getFluidContainer();
                    if (packet.tank() > container.getSize()) return;
                    container.internalExtract(container.getFluids().get(packet.tank()), false);
                    container.extractFluid(container.getFluids().get(packet.tank()), false);
                }
            );
        }
    }
}
