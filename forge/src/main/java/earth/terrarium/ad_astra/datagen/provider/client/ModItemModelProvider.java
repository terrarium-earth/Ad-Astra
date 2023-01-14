package earth.terrarium.ad_astra.datagen.provider.client;


import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.block.pipe.PipeBlock;
import earth.terrarium.ad_astra.common.item.EtrionicCapacitorItem;
import earth.terrarium.ad_astra.common.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SignBlock;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, AdAstra.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerModels() {
        ModItems.ITEMS.getEntries().forEach(item -> {
            if (item.get() instanceof BlockItem blockItem) {
                if (blockItem.getBlock() instanceof SaplingBlock) {
                    basicBlockItem(item.get());
                } else if (blockItem.getBlock() instanceof DoorBlock) {
                    basicItem(item.get());
                } else if (blockItem.getBlock() instanceof SignBlock) {
                    basicItem(item.get());
                } else if (blockItem.getBlock() instanceof PipeBlock) {
                    basicItem(item.get());
                }
            } else if (!(item.get() instanceof SignItem) && !(item.get() instanceof EtrionicCapacitorItem)) {
                basicItem(item.get());
            }
        });
    }

    public void basicBlockItem(Item item) {
        basicBlockItem(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)));
    }

    public void basicBlockItem(ResourceLocation item) {
        getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", new ResourceLocation(item.getNamespace(), "block/" + item.getPath()));
    }
}
