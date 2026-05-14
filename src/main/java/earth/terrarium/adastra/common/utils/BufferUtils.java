package earth.terrarium.adastra.common.utils;

import java.nio.ByteBuffer;

public final class BufferUtils {

    public static int read(ByteBuffer buffer, byte[] bytes, int offset, int length, int count) {
        length = Math.min(buffer.remaining(), length - count);
        buffer.get(bytes, offset, length);
        return length;
    }

    public static int skip(ByteBuffer buffer, int length, int count) {
        int size = Math.min(buffer.remaining(), length - count);
        buffer.position(buffer.position() + size);
        return size;
    }
}
