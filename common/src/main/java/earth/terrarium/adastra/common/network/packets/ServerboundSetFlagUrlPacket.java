package earth.terrarium.adastra.common.network.packets;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.bytecodecs.ExtraByteCodecs;
import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import com.teamresourceful.resourcefullib.common.network.base.ServerboundPacketType;
import com.teamresourceful.resourcefullib.common.network.defaults.CodecPacketType;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.blockentities.flag.FlagBlockEntity;
import earth.terrarium.adastra.common.blockentities.flag.content.UrlContent;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;
import java.util.regex.Pattern;

public record ServerboundSetFlagUrlPacket(BlockPos pos, String url) implements Packet<ServerboundSetFlagUrlPacket> {

    private static final Pattern URL_REGEX = Pattern.compile("^https://i\\.imgur\\.com/(\\w+)\\.(png|jpeg|jpg|webp)$");

    public static final ServerboundPacketType<ServerboundSetFlagUrlPacket> TYPE = new Type();

    @Override
    public PacketType<ServerboundSetFlagUrlPacket> type() {
        return TYPE;
    }

    private static class Type extends CodecPacketType<ServerboundSetFlagUrlPacket> implements ServerboundPacketType<ServerboundSetFlagUrlPacket> {

        public Type() {
            super(
                ServerboundSetFlagUrlPacket.class,
                new ResourceLocation(AdAstra.MOD_ID, "set_flag_url"),
                ObjectByteCodec.create(
                    ExtraByteCodecs.BLOCK_POS.fieldOf(ServerboundSetFlagUrlPacket::pos),
                    ByteCodec.STRING.fieldOf(ServerboundSetFlagUrlPacket::url),
                    ServerboundSetFlagUrlPacket::new
                )
            );
        }

        @Override
        public Consumer<Player> handle(ServerboundSetFlagUrlPacket packet) {
            return player -> {
                if (URL_REGEX.matcher(packet.url()).matches()
                    && player.distanceToSqr(packet.pos().getCenter()) <= 64
                    && player.level().getBlockEntity(packet.pos()) instanceof FlagBlockEntity flag
                    && flag.getOwner() != null
                    && player.getUUID().equals(flag.getOwner().getId())
                ) {
                    flag.setContent(UrlContent.of(packet.url()));
                    var blockState = player.level().getBlockState(packet.pos());
                    player.level().sendBlockUpdated(packet.pos(), blockState, blockState, Block.UPDATE_ALL);
                }
            };
        }
    }
}