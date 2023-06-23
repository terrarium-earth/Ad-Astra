package earth.terrarium.ad_astra.common.networking.packet.messages;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.block.machine.entity.OxygenDistributorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record ServerboundToggleDistributorPacket(BlockPos pos) implements Packet<ServerboundToggleDistributorPacket> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "toggle_distributor");
    public static final Handler HANDLER = new Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<ServerboundToggleDistributorPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<ServerboundToggleDistributorPacket> {
        @Override
        public void encode(ServerboundToggleDistributorPacket packet, FriendlyByteBuf buf) {
            buf.writeBlockPos(packet.pos());
        }

        @Override
        public ServerboundToggleDistributorPacket decode(FriendlyByteBuf buf) {
            return new ServerboundToggleDistributorPacket(buf.readBlockPos());
        }

        @Override
        public PacketContext handle(ServerboundToggleDistributorPacket packet) {
            return (player, level) -> {
                if (level.getBlockEntity(packet.pos()) instanceof OxygenDistributorBlockEntity oxygenDistributor) {
                    oxygenDistributor.setShowOxygen(!oxygenDistributor.shouldShowOxygen());
                }
            };
        }
    }
}
