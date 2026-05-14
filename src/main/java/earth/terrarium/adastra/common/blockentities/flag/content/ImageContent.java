package earth.terrarium.adastra.common.blockentities.flag.content;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;

import java.util.Base64;

public record ImageContent(byte[] data, HashCode hash) implements FlagContent {

    public static final String TYPE = "image";

    public static ImageContent of(String base64) {
        byte[] data = Base64.getDecoder().decode(base64);
        if (data.length == 176) {
            return new ImageContent(data, Hashing.sha1().hashBytes(data));
        }
        return null;
    }

    @Override
    public String type() {
        return TYPE;
    }

    @Override
    public Tag toTag() {
        return StringTag.valueOf(Base64.getEncoder().encodeToString(data));
    }
}
