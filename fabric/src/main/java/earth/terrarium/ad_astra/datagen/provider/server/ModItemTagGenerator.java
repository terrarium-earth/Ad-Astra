package earth.terrarium.ad_astra.datagen.provider.server;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import earth.terrarium.ad_astra.common.registry.ModBlocks;
import earth.terrarium.ad_astra.common.registry.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends FabricTagProvider<Item> {
    /**
     * Constructs a new {@link FabricTagProvider} with the default computed path.
     *
     * <p>Common implementations of this class are provided.
     *
     * @param output           the {@link FabricDataOutput} instance
     * @param registriesFuture the backing registry for the tag type
     */
    public ModItemTagGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, BuiltInRegistries.ITEM.key(), registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        fabric(ModItems.ETRIUM_BLOCK);
        fabric(ModItems.STEEL_BLOCK);
        fabric(ModItems.DESMIUM_BLOCK);
        fabric(ModItems.XEBRIUM_BLOCK);
        fabric(ModItems.VESNIUM_BLOCK);
        fabric(ModItems.AEROLYTE_BLOCK);

        fabric(ModItems.IRON_PLATE);
        fabric(ModItems.IRON_ROD);

        fabric(ModItems.STEEL_PLATE);
        fabric(ModItems.STEEL_ROD);
        fabric(ModItems.STEEL_NUGGET);
        fabric(ModItems.STEEL_INGOT);

        fabric(ModItems.DESMIUM_PLATE);
        fabric(ModItems.DESMIUM_NUGGET);
        fabric(ModItems.DESMIUM_INGOT);

        fabric(ModItems.XEBRIUM_PLATE);
        fabric(ModItems.XEBRIUM_NUGGET);
        fabric(ModItems.XEBRIUM_INGOT);

        fabric(ModItems.AEROLYTE_PLATE);
        fabric(ModItems.AEROLYTE_NUGGET);
        fabric(ModItems.AEROLYTE_INGOT);

        fabric(ModItems.ETRIUM_PLATE);
        fabric(ModItems.ETRIUM_NUGGET);
        fabric(ModItems.ETRIUM_INGOT);
    }

    private void fabric(RegistryEntry<Item> item) {
        getOrCreateTagBuilder(TagKey.create(BuiltInRegistries.ITEM.key(), new ResourceLocation("c", item.getId().getPath() + "s"))).add(item.get());
    }
}
