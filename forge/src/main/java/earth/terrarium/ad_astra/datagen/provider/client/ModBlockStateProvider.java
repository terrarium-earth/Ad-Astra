package earth.terrarium.ad_astra.datagen.provider.client;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.block.pipe.PipeBlock;
import earth.terrarium.ad_astra.common.registry.ModBlocks;
import earth.terrarium.ad_astra.common.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockStateProvider extends BlockStateProvider {

    private static final ResourceLocation SOLAR_PANEL = new ResourceLocation(AdAstra.MOD_ID, "block/solar_panel");
    private static final ResourceLocation WATER_STILL = new ResourceLocation("block/water_still");

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, AdAstra.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        ModBlocks.SOLAR_PANELS.stream().map(RegistryEntry::get).forEach(block -> horizontalBlock(block, SOLAR_PANEL, "texture"));
        ModBlocks.FLUIDS.stream().map(RegistryEntry::get).forEach(fluid -> block(fluid, "particle", WATER_STILL.toString()));

        simpleBlock(ModBlocks.SKY_STONE.get());
        simpleBlock(ModBlocks.ETRIUM_ORE.get());

        ModItems.ITEMS.getEntries().forEach(item -> {
            if (item.get() instanceof BlockItem blockItem) {
                Block block = ForgeRegistries.BLOCKS.getValue(ForgeRegistries.ITEMS.getKey(blockItem));
                if (block instanceof TrapDoorBlock) {
                    simpleBlockItem(block, new ModelFile.UncheckedModelFile(extend(blockTexture(block), "_bottom")));
                } else if (block instanceof FenceBlock) {
                    simpleBlockItem(block, new ModelFile.UncheckedModelFile(extend(blockTexture(block), "_inventory")));
                } else if (block instanceof DoorBlock) {
                } else if (block instanceof SaplingBlock) {
                } else if (block instanceof SignBlock) {
                } else if (block instanceof ChainBlock) {
                } else if (block instanceof PipeBlock) {
                } else if (block.equals(ModBlocks.OXYGEN_SENSOR.get())) {
                    simpleBlockItem(block, new ModelFile.UncheckedModelFile(extend(blockTexture(block), "_on")));
                } else if (block instanceof LanternBlock) {
                } else {
                    simpleBlockItem(block, new ModelFile.UncheckedModelFile(blockTexture(block)));
                }
            }
        });
    }

    private ResourceLocation prefix(Block block, String prefix) {
        final ResourceLocation id = key(block);
        return new ResourceLocation(id.getNamespace(), prefix + id.getPath());
    }

    private ResourceLocation extend(ResourceLocation rl, String suffix) {
        return new ResourceLocation(rl.getNamespace(), rl.getPath() + suffix);
    }

    // references a parent model
    private void block(Block block, ResourceLocation parent) {
        simpleBlock(block, models().withExistingParent(name(block), parent));
    }

    // references a parent model with a texture
    private void block(Block block, ResourceLocation parent, String texture) {
        simpleBlock(block, models().getBuilder(name(block)).texture(texture, blockTexture(block)).texture("particle", blockTexture(block)).parent(models().getExistingFile(parent)));
    }

    private void horizontalBlock(Block block, ResourceLocation parent, String texture) {
        horizontalBlock(block, models().getBuilder(name(block)).texture(texture, blockTexture(block)).texture("particle", blockTexture(block)).parent(models().getExistingFile(parent)));
    }

    // creates a model with a texture and no parentfourWayBlock
    private void block(Block block, String texture, String loc) {
        simpleBlock(block, models().getBuilder(name(block)).texture(texture, loc));
    }

    private ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    private String name(Block block) {
        return key(block).getPath();
    }
}