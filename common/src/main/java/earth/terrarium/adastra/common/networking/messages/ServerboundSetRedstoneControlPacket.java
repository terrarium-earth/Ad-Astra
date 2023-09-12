package earth.terrarium.adastra.common.networking.messages;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.blockentities.base.ContainerMachineBlockEntity;
import earth.terrarium.adastra.common.blockentities.base.RedstoneControl;
import earth.terrarium.adastra.common.menus.base.BasicContainerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntity;

public record ServerboundSetRedstoneControlPacket(BlockPos machine,
                                                  RedstoneControl redstoneControl) implements Packet<ServerboundSetRedstoneControlPacket> {

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

    private static class Handler implements PacketHandler<ServerboundSetRedstoneControlPacket> {
        @Override
        public void encode(ServerboundSetRedstoneControlPacket packet, FriendlyByteBuf buf) {
            buf.writeBlockPos(packet.machine());
            buf.writeEnum(packet.redstoneControl());
        }

        @Override
        public ServerboundSetRedstoneControlPacket decode(FriendlyByteBuf buf) {
            return new ServerboundSetRedstoneControlPacket(
                buf.readBlockPos(),
                buf.readEnum(RedstoneControl.class)
            );
        }

        @Override
        public PacketContext handle(ServerboundSetRedstoneControlPacket packet) {
            return (player, level) -> {
                if (!(player.containerMenu instanceof BasicContainerMenu<?>)) return;
                if (player.distanceToSqr(packet.machine.getCenter()) > Mth.square(8)) return;
                BlockEntity machine = level.getBlockEntity(packet.machine());
                if (!(machine instanceof ContainerMachineBlockEntity container)) return;
                container.setRedstoneControl(packet.redstoneControl());
            };
        }
    }
}
