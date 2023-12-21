package earth.terrarium.adastra.common.tags;

import earth.terrarium.adastra.AdAstra;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.decoration.PaintingVariant;

public final class ModPaintingVariantTags {
    public static final TagKey<PaintingVariant> SPACE_PAINTINGS = tag("space_paintings");

    private static TagKey<PaintingVariant> tag(String name) {
        return TagKey.create(Registries.PAINTING_VARIANT, new ResourceLocation(AdAstra.MOD_ID, name));
    }
}
