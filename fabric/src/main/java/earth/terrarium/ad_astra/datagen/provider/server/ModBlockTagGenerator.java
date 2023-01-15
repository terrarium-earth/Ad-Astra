package earth.terrarium.ad_astra.datagen.provider.server;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
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
        fabric(ModBlocks.ETRIUM_BLOCK);
        fabric(ModBlocks.STEEL_BLOCK);
        fabric(ModBlocks.DESMIUM_BLOCK);
        fabric(ModBlocks.XEBRIUM_BLOCK);
        fabric(ModBlocks.VESNIUM_BLOCK);
        fabric(ModBlocks.AEROLYTE_BLOCK);
    }

    private void fabric(RegistryEntry<Block> item) {
        getOrCreateTagBuilder(TagKey.create(BuiltInRegistries.BLOCK.key(), new ResourceLocation("c", item.getId().getPath() + "s"))).add(item.get());
    }
}
