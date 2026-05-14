package earth.terrarium.adastra.common.utils.radio;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.codecs.EnumCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

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

    public static final StreamCodec<RegistryFriendlyByteBuf, StationInfo> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.STRING_UTF8,
        o -> o.url,
        ByteBufCodecs.STRING_UTF8,
        o -> o.title,
        ByteBufCodecs.STRING_UTF8,
        o -> o.name,
        StationLocation.STREAM_CODEC,
        o -> o.location,
        StationInfo::new
    );
}
