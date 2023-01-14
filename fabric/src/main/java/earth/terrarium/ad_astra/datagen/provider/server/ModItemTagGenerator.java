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
        getOrCreateTagBuilder(fabric("etrium_blocks")).add(ModItems.ETRIUM_BLOCK.get());
        getOrCreateTagBuilder(fabric("steel_blocks")).add(ModItems.STEEL_BLOCK.get());
        getOrCreateTagBuilder(fabric("desmium_blocks")).add(ModItems.DESMIUM_BLOCK.get());
        getOrCreateTagBuilder(fabric("xebrium_blocks")).add(ModItems.XEBRIUM_BLOCK.get());
        getOrCreateTagBuilder(fabric("vesnium_blocks")).add(ModItems.VESNIUM_BLOCK.get());
        getOrCreateTagBuilder(fabric("aerolyte_blocks")).add(ModItems.AEROLYTE_BLOCK.get());

        getOrCreateTagBuilder(fabric("iron_plates")).add(ModItems.IRON_PLATE.get());
        getOrCreateTagBuilder(fabric("iron_rods")).add(ModItems.IRON_ROD.get());

        getOrCreateTagBuilder(fabric("steel_plates")).add(ModItems.STEEL_PLATE.get());
        getOrCreateTagBuilder(fabric("steel_rods")).add(ModItems.STEEL_ROD.get());
        getOrCreateTagBuilder(fabric("steel_nuggets")).add(ModItems.STEEL_NUGGET.get());
        getOrCreateTagBuilder(fabric("steel_ingots")).add(ModItems.STEEL_INGOT.get());

        getOrCreateTagBuilder(fabric("desmium_plates")).add(ModItems.DESMIUM_PLATE.get());
        getOrCreateTagBuilder(fabric("desmium_nuggets")).add(ModItems.DESMIUM_NUGGET.get());
        getOrCreateTagBuilder(fabric("desmium_ingots")).add(ModItems.DESMIUM_INGOT.get());

        getOrCreateTagBuilder(fabric("xebrium_plates")).add(ModItems.XEBRIUM_PLATE.get());
        getOrCreateTagBuilder(fabric("xebrium_nuggets")).add(ModItems.XEBRIUM_NUGGET.get());
        getOrCreateTagBuilder(fabric("xebrium_ingots")).add(ModItems.XEBRIUM_INGOT.get());

        getOrCreateTagBuilder(fabric("aerolyte_plates")).add(ModItems.AEROLYTE_PLATE.get());
        getOrCreateTagBuilder(fabric("aerolyte_nuggets")).add(ModItems.AEROLYTE_NUGGET.get());
        getOrCreateTagBuilder(fabric("aerolyte_ingots")).add(ModItems.AEROLYTE_INGOT.get());

        getOrCreateTagBuilder(fabric("etrium_plates")).add(ModItems.ETRIUM_PLATE.get());
        getOrCreateTagBuilder(fabric("etrium_nuggets")).add(ModItems.ETRIUM_NUGGET.get());
        getOrCreateTagBuilder(fabric("etrium_ingots")).add(ModItems.ETRIUM_INGOT.get());
    }

    private TagKey<Item> fabric(String name) {
        return TagKey.create(BuiltInRegistries.ITEM.key(), new ResourceLocation("c", name));
    }
}
