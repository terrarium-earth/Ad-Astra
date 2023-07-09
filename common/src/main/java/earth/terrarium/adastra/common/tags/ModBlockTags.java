package earth.terrarium.adastra.common.tags;

import earth.terrarium.adastra.AdAstra;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class ModBlockTags {
    public static final TagKey<Block> PASSES_FLOOD_FILL = tag("passes_flood_fill");
    public static final TagKey<Block> BLOCKS_FLOOD_FILL = tag("blocks_flood_fill");

    private static TagKey<Block> tag(String name) {
        return TagKey.create(Registries.BLOCK, new ResourceLocation(AdAstra.MOD_ID, name));
    }
}
