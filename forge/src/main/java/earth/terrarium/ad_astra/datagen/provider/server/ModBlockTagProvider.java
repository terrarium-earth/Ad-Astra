package earth.terrarium.ad_astra.datagen.provider.server;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.registry.ModBlocks;
import earth.terrarium.ad_astra.common.registry.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagEntry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.CompletableFuture;

@ParametersAreNonnullByDefault
public class ModBlockTagProvider extends TagsProvider<Block> {

    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, ExistingFileHelper existingFileHelper) {
        super(output, ForgeRegistries.BLOCKS.getRegistryKey(), completableFuture, AdAstra.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        addVanillaTags();
    }

    private void addVanillaTags() {
        ModBlocks.FLAGS.stream().forEach(b -> tag(ModTags.Blocks.FLAGS).add(TagEntry.element(b.getId())));

        ModBlocks.STAIRS.stream().forEach(b -> tag(BlockTags.STAIRS).add(TagEntry.element(b.getId())));
        ModBlocks.SLABS.stream().forEach(b -> tag(BlockTags.SLABS).add(TagEntry.element(b.getId())));
        ModBlocks.WALLS.stream().forEach(b -> tag(BlockTags.WALLS).add(TagEntry.element(b.getId())));
        ModBlocks.BUTTONS.stream().forEach(b -> tag(BlockTags.BUTTONS).add(TagEntry.element(b.getId())));
        ModBlocks.PRESSURE_PLATES.stream().forEach(b -> tag(BlockTags.PRESSURE_PLATES).add(TagEntry.element(b.getId())));
        ModBlocks.SANDS.stream().forEach(b -> tag(BlockTags.SAND).add(TagEntry.element(b.getId())));
        ModBlocks.STONE_BRICKS.stream().forEach(b -> tag(BlockTags.STONE_BRICKS).add(TagEntry.element(b.getId())));

        ModBlocks.BLOCKS.stream().forEach(b -> {
            Material material = b.get().defaultBlockState().getMaterial();
            if (material == Material.STONE || material == Material.METAL || material == Material.AMETHYST || material == Material.HEAVY_METAL) {
                tag(BlockTags.MINEABLE_WITH_PICKAXE).add(TagEntry.element(b.getId()));
            }
            if (material == Material.WOOD) {
                tag(BlockTags.MINEABLE_WITH_AXE).add(TagEntry.element(b.getId()));
            }
            if (material == Material.SAND || material == Material.SNOW || material == Material.GRASS || material == Material.DIRT) {
                tag(BlockTags.MINEABLE_WITH_SHOVEL).add(TagEntry.element(b.getId()));
            }
        });

    }
}
