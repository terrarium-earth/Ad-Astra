package earth.terrarium.adastra.datagen.provider.client;


import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.registry.ModBlocks;
import earth.terrarium.adastra.common.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public static final ResourceLocation RENDERED_ITEM = new ResourceLocation(AdAstra.MOD_ID, "item/rendered_item");

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, AdAstra.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        getBuilder(ModBlocks.OXYGEN_DISTRIBUTOR.getId().getPath())
            .parent(getExistingFile(RENDERED_ITEM));
        basicItem(ModItems.TI_69.get());
    }

    private void createBlockItem(RegistryEntry<Block> item) {
        ResourceLocation id = item.getId();
        getBuilder(id.getPath())
            .parent(getExistingFile(modLoc("block/" + id.getPath())));
    }
}
