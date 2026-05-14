package earth.terrarium.adastra.common.blockentities.flag.content;

import com.google.common.hash.HashCode;
import earth.terrarium.adastra.AdAstra;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;

public interface FlagContent {

    HashCode hash();

    String type();

    Tag toTag();

    default ResourceLocation toTexture() {
        return new ResourceLocation(AdAstra.MOD_ID, "flagtextures/" + type() + "/" + hash());
    }

    default CompoundTag toFullTag() {
        CompoundTag tag = new CompoundTag();
        tag.putString("type", type());
        tag.put("content", toTag());
        return tag;
    }

    static FlagContent fromTag(CompoundTag tag) {
        String type = tag.getString("type");
        Tag content = tag.get("content");
        if (content == null) return null;
        return switch (type) {
            case ImageContent.TYPE -> ImageContent.of(content.getAsString());
            case UrlContent.TYPE -> UrlContent.of(content.getAsString());
            default -> null;
        };
    }
}
