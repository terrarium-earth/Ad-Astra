package earth.terrarium.adastra.common.networking.messages;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.menus.base.BasicContainerMenu;
import earth.terrarium.botarium.common.fluid.base.BotariumFluidBlock;
import earth.terrarium.botarium.common.fluid.base.FluidContainer;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.base.PlatformFluidHandler;
import earth.terrarium.botarium.common.fluid.impl.WrappedBlockFluidContainer;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntity;

public record ServerboundClearFluidTankPacket(BlockPos machine,
                                              int tank) implements Packet<ServerboundClearFluidTankPacket> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "clear_fluid_tank");
    public static final ServerboundClearFluidTankPacket.Handler HANDLER = new ServerboundClearFluidTankPacket.Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<ServerboundClearFluidTankPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<ServerboundClearFluidTankPacket> {
        @Override
        public void encode(ServerboundClearFluidTankPacket packet, FriendlyByteBuf buf) {
            buf.writeBlockPos(packet.machine());
            buf.writeByte(packet.tank());
        }

        @Override
        public ServerboundClearFluidTankPacket decode(FriendlyByteBuf buf) {
            return new ServerboundClearFluidTankPacket(
                buf.readBlockPos(),
                buf.readByte());
        }

        @Override
        public PacketContext handle(ServerboundClearFluidTankPacket packet) {
            return (player, level) -> {
                if (!(player.containerMenu instanceof BasicContainerMenu<?>)) return;
                if (player.distanceToSqr(packet.machine.getCenter()) > Mth.square(8)) return;
                BlockEntity machine = level.getBlockEntity(packet.machine());
                if (!(machine instanceof BotariumFluidBlock<?> fluidBlock)) return;
                FluidContainer container = fluidBlock.getFluidContainer();
                if (packet.tank() > container.getSize()) return;
                container.internalExtract(container.getFluids().get(packet.tank()), false);
                container.extractFluid(container.getFluids().get(packet.tank()), false);
            };
        }
    }
}
