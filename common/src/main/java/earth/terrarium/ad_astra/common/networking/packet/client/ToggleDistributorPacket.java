package earth.terrarium.ad_astra.common.networking.packet.client;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.block.machine.entity.OxygenDistributorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record ToggleDistributorPacket(BlockPos pos) implements Packet<ToggleDistributorPacket> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "toggle_distributor_packet");
    public static final Handler HANDLER = new Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<ToggleDistributorPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<ToggleDistributorPacket> {
        @Override
        public void encode(ToggleDistributorPacket packet, FriendlyByteBuf buf) {
            buf.writeBlockPos(packet.pos());
        }

        @Override
        public ToggleDistributorPacket decode(FriendlyByteBuf buf) {
            return new ToggleDistributorPacket(buf.readBlockPos());
        }

        @Override
        public PacketContext handle(ToggleDistributorPacket packet) {
            return (player, level) -> {
                if (level.getBlockEntity(packet.pos()) instanceof OxygenDistributorBlockEntity oxygenDistributor) {
                    oxygenDistributor.setShowOxygen(!oxygenDistributor.shouldShowOxygen());
                }
            };
        }
    }
}
