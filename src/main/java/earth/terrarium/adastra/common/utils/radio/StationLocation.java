package earth.terrarium.adastra.common.utils.radio;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

import java.util.function.IntFunction;

public enum StationLocation implements StringRepresentable {
    UNKNOWN(0, "UNKNOWN"),
    CANADA(1, "CANADA"),
    USA(2, "USA");

    public static final Codec<StationLocation> CODEC = StringRepresentable.fromEnum(StationLocation::values);
    public static final IntFunction<StationLocation> BY_ID = ByIdMap.continuous(s -> s.id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
    public static final StreamCodec<ByteBuf, StationLocation> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, s -> s.id);
    private final String name;
    private final int id;

    StationLocation(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
