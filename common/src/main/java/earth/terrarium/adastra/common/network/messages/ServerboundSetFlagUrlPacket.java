package earth.terrarium.adastra.common.network.messages;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.blockentities.flag.FlagBlockEntity;
import earth.terrarium.adastra.common.blockentities.flag.content.UrlContent;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.regex.Pattern;

public record ServerboundSetFlagUrlPacket(BlockPos pos, String url) implements Packet<ServerboundSetFlagUrlPacket> {

    private static final Pattern URL_REGEX = Pattern.compile("^https://i\\.imgur\\.com/(\\w+)\\.(png|jpeg|jpg|webp)$");

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "set_flag_url");
    public static final Handler HANDLER = new Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<ServerboundSetFlagUrlPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<ServerboundSetFlagUrlPacket> {
        @Override
        public void encode(ServerboundSetFlagUrlPacket packet, FriendlyByteBuf buf) {
            buf.writeBlockPos(packet.pos());
            buf.writeUtf(packet.url());
        }

        @Override
        public ServerboundSetFlagUrlPacket decode(FriendlyByteBuf buf) {
            return new ServerboundSetFlagUrlPacket(buf.readBlockPos(), buf.readUtf());
        }

        @Override
        public PacketContext handle(ServerboundSetFlagUrlPacket packet) {
            return (player, level) -> {
                boolean valid = URL_REGEX.matcher(packet.url()).matches();
                if (valid && player.level().getBlockEntity(packet.pos()) instanceof FlagBlockEntity flag && flag.getOwner() != null && player.getUUID().equals(flag.getOwner().getId())) {
                    flag.setContent(UrlContent.of(packet.url()));
                    var blockState = player.level().getBlockState(packet.pos());
                    player.level().sendBlockUpdated(packet.pos(), blockState, blockState, Block.UPDATE_ALL);
                }
            };
        }
    }
}
