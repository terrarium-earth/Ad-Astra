package earth.terrarium.adastra.datagen.provider.server.tags;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.registry.ModBlocks;
import earth.terrarium.adastra.common.tags.ModBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends TagsProvider<Block> {

    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, ExistingFileHelper existingFileHelper) {
        super(output, Registries.BLOCK, completableFuture, AdAstra.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModBlockTags.PASSES_FLOOD_FILL).add(TagEntry.tag(BlockTags.FENCES.location()));

        add(ModBlockTags.PASSES_FLOOD_FILL, Blocks.IRON_BARS);
        add(ModBlockTags.PASSES_FLOOD_FILL, Blocks.TNT);
        add(ModBlockTags.PASSES_FLOOD_FILL, ModBlocks.OXYGEN_DISTRIBUTOR.get());
        add(ModBlockTags.PASSES_FLOOD_FILL, ModBlocks.GRAVITY_NORMALIZER.get());

        add(ModBlockTags.MOON_CARVER_REPLACEABLES, ModBlocks.MOON_SAND.get());
        add(ModBlockTags.MOON_CARVER_REPLACEABLES, ModBlocks.MOON_STONE.get());

        add(ModBlockTags.STEEL_BLOCKS, ModBlocks.BLOCK_OF_STEEL.get());
        add(ModBlockTags.ETRIUM_BLOCKS, ModBlocks.BLOCK_OF_ETRIUM.get());
    }

    private void add(TagKey<Block> tag, Block block) {
        tag(tag).add(TagEntry.element(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block))));
    }
}
