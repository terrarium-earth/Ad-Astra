package earth.terrarium.adastra.datagen.provider.server.tags;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.tags.ModItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagEntry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends TagsProvider<Item> {

    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, ExistingFileHelper existingFileHelper) {
        super(output, Registries.ITEM, completableFuture, AdAstra.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModItemTags.RECYCLABLE).add(TagEntry.element(ForgeRegistries.BLOCKS.getKey(Blocks.ANVIL)));
        tag(ModItemTags.RECYCLABLE).add(TagEntry.element(ForgeRegistries.BLOCKS.getKey(Blocks.DISPENSER)));
        tag(ModItemTags.RECYCLABLE).add(TagEntry.element(ForgeRegistries.BLOCKS.getKey(Blocks.DROPPER)));
    }
}
