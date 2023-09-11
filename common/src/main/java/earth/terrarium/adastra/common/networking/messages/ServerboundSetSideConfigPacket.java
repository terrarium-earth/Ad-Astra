package earth.terrarium.adastra.common.networking.messages;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.Configuration;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.SideConfigurable;
import earth.terrarium.adastra.common.menus.base.BasicContainerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntity;

public record ServerboundSetSideConfigPacket(BlockPos machine,
                                             int configIndex,
                                             Direction direction,
                                             Configuration configuration) implements Packet<ServerboundSetSideConfigPacket> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "set_side_config");
    public static final Handler HANDLER = new ServerboundSetSideConfigPacket.Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<ServerboundSetSideConfigPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<ServerboundSetSideConfigPacket> {
        @Override
        public void encode(ServerboundSetSideConfigPacket packet, FriendlyByteBuf buf) {
            buf.writeBlockPos(packet.machine());
            buf.writeByte(packet.configIndex());
            buf.writeEnum(packet.direction());
            buf.writeEnum(packet.configuration());
        }

        @Override
        public ServerboundSetSideConfigPacket decode(FriendlyByteBuf buf) {
            return new ServerboundSetSideConfigPacket(
                buf.readBlockPos(),
                buf.readByte(),
                buf.readEnum(Direction.class),
                buf.readEnum(Configuration.class)
            );
        }

        @Override
        public PacketContext handle(ServerboundSetSideConfigPacket packet) {
            return (player, level) -> {
                if (!(player.containerMenu instanceof BasicContainerMenu<?>)) return;
                if (player.distanceToSqr(packet.machine.getCenter()) > Mth.square(8)) return;
                BlockEntity machine = level.getBlockEntity(packet.machine());
                if (!(machine instanceof SideConfigurable sideConfigurable)) return;
                var sideConfig = sideConfigurable.getSideConfig();
                sideConfig.get(packet.configIndex()).set(packet.direction(), packet.configuration());
                machine.setChanged();
            };
        }
    }
}
