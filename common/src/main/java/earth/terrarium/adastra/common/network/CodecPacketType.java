package earth.terrarium.adastra.common.network;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

/**
 * Backport of resourcefullib 1.20.4 codec packet type
 */
public abstract class CodecPacketType<T extends Packet<T>> implements PacketType<T> {

    protected ByteCodec<T> codec;
    protected final Class<T> clazz;
    protected final ResourceLocation id;

    public CodecPacketType(Class<T> clazz, ResourceLocation id, ByteCodec<T> codec) {
        this.codec = codec;
        this.clazz = clazz;
        this.id = id;
    }

    @Override
    public Class<T> type() {
        return clazz;
    }

    @Override
    public ResourceLocation id() {
        return id;
    }

    @Override
    public void encode(T message, FriendlyByteBuf buffer) {
        codec.encode(message, buffer);
    }

    @Override
    public T decode(FriendlyByteBuf buffer) {
        return codec.decode(buffer);
    }
}
