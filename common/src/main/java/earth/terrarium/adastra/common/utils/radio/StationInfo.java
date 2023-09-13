package earth.terrarium.adastra.common.utils.radio;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.codecs.EnumCodec;

public record StationInfo(
    String url,
    String title,
    String name,
    StationLocation location
) {

    public static final Codec<StationInfo> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.STRING.fieldOf("url").forGetter(StationInfo::url),
        Codec.STRING.fieldOf("title").orElse("0.00 N/A").forGetter(StationInfo::title),
        Codec.STRING.fieldOf("name").orElse("Unknown Station").forGetter(StationInfo::name),
        EnumCodec.of(StationLocation.class).fieldOf("location").orElse(StationLocation.UNKNOWN).forGetter(StationInfo::location)
    ).apply(instance, StationInfo::new));

    public static final ByteCodec<StationInfo> BYTE_CODEC = ObjectByteCodec.create(
        ByteCodec.STRING.fieldOf(StationInfo::url),
        ByteCodec.STRING.fieldOf(StationInfo::title),
        ByteCodec.STRING.fieldOf(StationInfo::name),
        new com.teamresourceful.bytecodecs.defaults.EnumCodec<>(StationLocation.class).fieldOf(StationInfo::location),
        StationInfo::new
    );
}
