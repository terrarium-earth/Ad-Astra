package earth.terrarium.adastra.common.networking.messages;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.SideConfigurable;
import earth.terrarium.adastra.common.menus.base.BasicContainerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntity;

public record ServerboundResetSideConfigPacket(BlockPos machine,
                                               int configIndex) implements Packet<ServerboundResetSideConfigPacket> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "reset_side_config");
    public static final Handler HANDLER = new ServerboundResetSideConfigPacket.Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<ServerboundResetSideConfigPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<ServerboundResetSideConfigPacket> {
        @Override
        public void encode(ServerboundResetSideConfigPacket packet, FriendlyByteBuf buf) {
            buf.writeBlockPos(packet.machine());
            buf.writeByte(packet.configIndex());
        }

        @Override
        public ServerboundResetSideConfigPacket decode(FriendlyByteBuf buf) {
            return new ServerboundResetSideConfigPacket(
                buf.readBlockPos(),
                buf.readVarInt());
        }

        @Override
        public PacketContext handle(ServerboundResetSideConfigPacket packet) {
            return (player, level) -> {
                if (!(player.containerMenu instanceof BasicContainerMenu<?>)) return;
                if (player.distanceToSqr(packet.machine.getCenter()) > Mth.square(8)) return;
                BlockEntity machine = level.getBlockEntity(packet.machine());
                if (!(machine instanceof SideConfigurable sideConfigurable)) return;
                sideConfigurable.resetToDefault(packet.configIndex());
            };
        }
    }
}
