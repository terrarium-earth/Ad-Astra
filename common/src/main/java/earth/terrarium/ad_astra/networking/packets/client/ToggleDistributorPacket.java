package earth.terrarium.ad_astra.networking.packets.client;

import earth.terrarium.ad_astra.blocks.machines.entity.OxygenDistributorBlockEntity;
import earth.terrarium.ad_astra.util.ModIdentifier;
import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public record ToggleDistributorPacket(BlockPos pos) implements Packet<ToggleDistributorPacket> {

    public static final Identifier ID = new ModIdentifier("toggle_distributor");
    public static final Handler HANDLER = new Handler();

    @Override
    public Identifier getID() {
        return ID;
    }

    @Override
    public PacketHandler<ToggleDistributorPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<ToggleDistributorPacket> {
        @Override
        public void encode(ToggleDistributorPacket keybindPacket, PacketByteBuf buf) {
            buf.writeBlockPos(keybindPacket.pos());
        }

        @Override
        public ToggleDistributorPacket decode(PacketByteBuf buf) {
            return new ToggleDistributorPacket(buf.readBlockPos());
        }

        @Override
        public PacketContext handle(ToggleDistributorPacket message) {
            return (player, world) -> {
                if (world.getBlockEntity(message.pos()) instanceof OxygenDistributorBlockEntity oxygenDistributor) {
                    oxygenDistributor.setShowOxygen(!oxygenDistributor.shouldShowOxygen());
                }
            };
        }
    }
}
