package earth.terrarium.ad_astra.datagen.provider.server;

import earth.terrarium.ad_astra.common.registry.ModBlocks;
import earth.terrarium.ad_astra.common.registry.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends FabricTagProvider<Block> {
    /**
     * Constructs a new {@link FabricTagProvider} with the default computed path.
     *
     * <p>Common implementations of this class are provided.
     *
     * @param output           the {@link FabricDataOutput} instance
     * @param registriesFuture the backing registry for the tag type
     */
    public ModBlockTagGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, BuiltInRegistries.BLOCK.key(), registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        getOrCreateTagBuilder(fabric("etrium_blocks")).add(ModBlocks.ETRIUM_BLOCK.get());
        getOrCreateTagBuilder(fabric("steel_blocks")).add(ModBlocks.STEEL_BLOCK.get());
        getOrCreateTagBuilder(fabric("desmium_blocks")).add(ModBlocks.DESMIUM_BLOCK.get());
        getOrCreateTagBuilder(fabric("xebrium_blocks")).add(ModBlocks.XEBRIUM_BLOCK.get());
        getOrCreateTagBuilder(fabric("vesnium_blocks")).add(ModBlocks.VESNIUM_BLOCK.get());
        getOrCreateTagBuilder(fabric("aerolyte_blocks")).add(ModBlocks.AEROLYTE_BLOCK.get());

    }

    private TagKey<Block> fabric(String name) {
        return TagKey.create(BuiltInRegistries.BLOCK.key(), new ResourceLocation("c", name));
    }
}
