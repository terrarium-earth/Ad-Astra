package earth.terrarium.adastra.common.blockentities.flag.content;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;

public record UrlContent(String url, HashCode hash) implements FlagContent {

    public static final String TYPE = "url";

    public static UrlContent of(String url) {
        return new UrlContent(url, Hashing.sha1().hashUnencodedChars(url));
    }

    @Override
    public String type() {
        return TYPE;
    }

    @Override
    public Tag toTag() {
        return StringTag.valueOf(url);
    }
}
