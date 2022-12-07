package earth.terrarium.ad_astra.networking.packet.client;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.block.flag.FlagBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public record FlagUrlPacket(BlockPos pos, String url) implements Packet<FlagUrlPacket> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "flag_url_packet");
    public static final Handler HANDLER = new Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<FlagUrlPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<FlagUrlPacket> {
        @Override
        public void encode(FlagUrlPacket packet, FriendlyByteBuf buf) {
            buf.writeBlockPos(packet.pos());
            buf.writeUtf(packet.url());
        }

        @Override
        public FlagUrlPacket decode(FriendlyByteBuf buf) {
            return new FlagUrlPacket(buf.readBlockPos(), buf.readUtf());
        }

        @Override
        public PacketContext handle(FlagUrlPacket packet) {
            return (player, level) -> {
                if (player.level.getBlockEntity(packet.pos()) instanceof FlagBlockEntity flag && flag.getOwner() != null && player.getUUID().equals(flag.getOwner().getId())) {
                    flag.setId(packet.url());
                    var blockState = player.level.getBlockState(packet.pos());
                    player.level.sendBlockUpdated(packet.pos(), blockState, blockState, Block.UPDATE_ALL);
                }
            };
        }
    }
}
