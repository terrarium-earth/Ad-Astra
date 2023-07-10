package earth.terrarium.adastra.common.utils.radio;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.codecs.EnumCodec;
import net.minecraft.network.FriendlyByteBuf;

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

    public void toNetwork(FriendlyByteBuf buf) {
        buf.writeUtf(url);
        buf.writeUtf(title);
        buf.writeUtf(name);
        buf.writeEnum(location);
    }

    public static StationInfo fromNetwork(FriendlyByteBuf buf) {
        return new StationInfo(buf.readUtf(), buf.readUtf(), buf.readUtf(), buf.readEnum(StationLocation.class));
    }
}
