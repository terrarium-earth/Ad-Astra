package earth.terrarium.ad_astra.common.networking.packet.messages;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.block.flag.FlagBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public record ServerboundFlagUrlPacket(BlockPos pos, String url) implements Packet<ServerboundFlagUrlPacket> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "flag_url");
    public static final Handler HANDLER = new Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<ServerboundFlagUrlPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<ServerboundFlagUrlPacket> {
        @Override
        public void encode(ServerboundFlagUrlPacket packet, FriendlyByteBuf buf) {
            buf.writeBlockPos(packet.pos());
            buf.writeUtf(packet.url());
        }

        @Override
        public ServerboundFlagUrlPacket decode(FriendlyByteBuf buf) {
            return new ServerboundFlagUrlPacket(buf.readBlockPos(), buf.readUtf());
        }

        @Override
        public PacketContext handle(ServerboundFlagUrlPacket packet) {
            return (player, level) -> {
                if (player.level().getBlockEntity(packet.pos()) instanceof FlagBlockEntity flag && flag.getOwner() != null && player.getUUID().equals(flag.getOwner().getId())) {
                    flag.setId(packet.url());
                    var blockState = player.level().getBlockState(packet.pos());
                    player.level().sendBlockUpdated(packet.pos(), blockState, blockState, Block.UPDATE_ALL);
                }
            };
        }
    }
}
