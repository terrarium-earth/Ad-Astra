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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends TagsProvider<Block> {

    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, ExistingFileHelper existingFileHelper) {
        super(output, Registries.BLOCK, completableFuture, AdAstra.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModBlockTags.PASSES_FLOOD_FILL).add(TagEntry.tag(BlockTags.FENCES.location()));
        tag(ModBlockTags.PASSES_FLOOD_FILL).add(TagEntry.element(ForgeRegistries.BLOCKS.getKey(Blocks.IRON_BARS)));
        tag(ModBlockTags.PASSES_FLOOD_FILL).add(TagEntry.element(ForgeRegistries.BLOCKS.getKey(Blocks.TNT)));

        tag(ModBlockTags.MOON_CARVER_REPLACEABLES).add(TagEntry.element(ForgeRegistries.BLOCKS.getKey(ModBlocks.MOON_SAND.get())));
        tag(ModBlockTags.MOON_CARVER_REPLACEABLES).add(TagEntry.element(ForgeRegistries.BLOCKS.getKey(ModBlocks.MOON_STONE.get())));
    }
}
