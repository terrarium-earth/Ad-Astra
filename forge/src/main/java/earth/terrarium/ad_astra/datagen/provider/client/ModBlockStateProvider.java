package earth.terrarium.ad_astra.datagen.provider.client;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.block.globe.GlobeBlock;
import earth.terrarium.ad_astra.common.block.pipe.PipeBlock;
import earth.terrarium.ad_astra.common.registry.ModBlocks;
import earth.terrarium.ad_astra.common.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockStateProvider extends BlockStateProvider {

    private static final ResourceLocation SOLAR_PANEL = new ResourceLocation(AdAstra.MOD_ID, "block/solar_panel");
    private static final ResourceLocation FLAG = new ResourceLocation(AdAstra.MOD_ID, "block/flag");
    private static final ResourceLocation GLOBE = new ResourceLocation(AdAstra.MOD_ID, "block/globe");
    private static final ResourceLocation GLOBE_CUBE = new ResourceLocation(AdAstra.MOD_ID, "block/globe_cube");
    private static final ResourceLocation GLOBE_ITEM = new ResourceLocation(AdAstra.MOD_ID, "item/globe");
    private static final ResourceLocation WALL_INVENTORY = new ResourceLocation("minecraft:block/wall_inventory");
    private static final ResourceLocation BUTTON_INVENTORY = new ResourceLocation("minecraft:block/button_inventory");
    private static final ResourceLocation WATER_STILL = new ResourceLocation("minecraft:block/water_still");

    protected static final ExistingFileHelper.ResourceType TEXTURE = new ExistingFileHelper.ResourceType(PackType.CLIENT_RESOURCES, ".png", "textures");
    private final ExistingFileHelper exFileHelper;

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, AdAstra.MOD_ID, exFileHelper);
        this.exFileHelper = exFileHelper;
    }

    @Override
    protected void registerStatesAndModels() {

        ModBlocks.FLUIDS.stream().map(RegistryEntry::get).forEach(fluid -> block(fluid, "particle", WATER_STILL.toString()));

        horizontalBlock(ModBlocks.ETRIONIC_SOLAR_PANEL.get(), SOLAR_PANEL, "texture");
        horizontalBlock(ModBlocks.VESNIUM_SOLAR_PANEL.get(), SOLAR_PANEL, "texture");

        ModBlocks.FLAGS.stream().forEach(b -> block(b.get(), FLAG, new ResourceLocation(AdAstra.MOD_ID, "block/flag/" + b.getId().getPath()), "texture"));
        ModBlocks.GLOBES.stream().forEach(b -> {
            ResourceLocation texture = new ResourceLocation(AdAstra.MOD_ID, "block/globe/" + b.getId().getPath());
            block(b.get(), GLOBE, texture, "texture");
            blockNoState(b.get(), GLOBE_CUBE, "block/" + b.getId().getPath() + "_cube", texture, "texture");
        });

        ModBlocks.CUBES.stream().map(RegistryEntry::get).forEach(this::simpleBlock);
        ModBlocks.PILLARS.stream().map(RegistryEntry::get).forEach(b -> logBlock((RotatedPillarBlock) b));
        ModBlocks.STAIRS.stream().map(RegistryEntry::get).forEach(b -> stairsBlock((StairBlock) b, replaceAndCheckPlural(b, "_stairs")));
        ModBlocks.SLABS.stream().map(RegistryEntry::get).forEach(b -> slabBlock((SlabBlock) b, replaceAndCheckPlural(b, "_slab"), replaceAndCheckPlural(b, "_slab")));
        ModBlocks.WALLS.stream().map(RegistryEntry::get).forEach(b -> {
            wallBlock((WallBlock) b, replaceAndCheckPlural(b, "_wall"));
            blockNoState(b, replaceAndCheckPlural(b, "_wall"), WALL_INVENTORY, "wall");
        });

        buttonBlock((ButtonBlock) ModBlocks.STEEL_BUTTON.get(), blockTexture(ModBlocks.STEEL_BLOCK.get()));
        buttonBlock((ButtonBlock) ModBlocks.ETRIUM_BUTTON.get(), blockTexture(ModBlocks.ETRIUM_BLOCK.get()));
        buttonBlock((ButtonBlock) ModBlocks.DESMIUM_BUTTON.get(), blockTexture(ModBlocks.DESMIUM_BLOCK.get()));
        buttonBlock((ButtonBlock) ModBlocks.THERMALYTE_BUTTON.get(), blockTexture(ModBlocks.THERMALYTE_BLOCK.get()));
        buttonBlock((ButtonBlock) ModBlocks.AEROLYTE_BUTTON.get(), blockTexture(ModBlocks.AEROLYTE_BLOCK.get()));

        blockNoState(name(ModBlocks.STEEL_BUTTON.get()) + "_inventory", replaceAndCheckPlural(ModBlocks.STEEL_BUTTON.get(), "_button", "_block"), BUTTON_INVENTORY, "texture");
        blockNoState(name(ModBlocks.ETRIUM_BUTTON.get()) + "_inventory", replaceAndCheckPlural(ModBlocks.ETRIUM_BUTTON.get(), "_button", "_block"), BUTTON_INVENTORY, "texture");
        blockNoState(name(ModBlocks.DESMIUM_BUTTON.get()) + "_inventory", replaceAndCheckPlural(ModBlocks.DESMIUM_BUTTON.get(), "_button", "_block"), BUTTON_INVENTORY, "texture");
        blockNoState(name(ModBlocks.THERMALYTE_BUTTON.get()) + "_inventory", replaceAndCheckPlural(ModBlocks.THERMALYTE_BUTTON.get(), "_button", "_block"), BUTTON_INVENTORY, "texture");
        blockNoState(name(ModBlocks.AEROLYTE_BUTTON.get()) + "_inventory", replaceAndCheckPlural(ModBlocks.AEROLYTE_BUTTON.get(), "_button", "_block"), BUTTON_INVENTORY, "texture");

        pressurePlateBlock((PressurePlateBlock) ModBlocks.STEEL_PRESSURE_PLATE.get(), blockTexture(ModBlocks.STEEL_BLOCK.get()));
        pressurePlateBlock((PressurePlateBlock) ModBlocks.ETRIUM_PRESSURE_PLATE.get(), blockTexture(ModBlocks.ETRIUM_BLOCK.get()));
        pressurePlateBlock((PressurePlateBlock) ModBlocks.DESMIUM_PRESSURE_PLATE.get(), blockTexture(ModBlocks.DESMIUM_BLOCK.get()));
        pressurePlateBlock((PressurePlateBlock) ModBlocks.THERMALYTE_PRESSURE_PLATE.get(), blockTexture(ModBlocks.THERMALYTE_BLOCK.get()));
        pressurePlateBlock((PressurePlateBlock) ModBlocks.AEROLYTE_PRESSURE_PLATE.get(), blockTexture(ModBlocks.AEROLYTE_BLOCK.get()));

        ModItems.ITEMS.getEntries().forEach(item -> {
            if (item.get() instanceof BlockItem blockItem) {
                Block block = ForgeRegistries.BLOCKS.getValue(ForgeRegistries.ITEMS.getKey(blockItem));
                if (block instanceof TrapDoorBlock) {
                    simpleBlockItem(block, new ModelFile.UncheckedModelFile(extend(blockTexture(block), "_bottom")));
                } else if (block instanceof FenceBlock) {
                    simpleBlockItem(block, new ModelFile.UncheckedModelFile(extend(blockTexture(block), "_inventory")));
                } else if (block instanceof ButtonBlock) {
                    simpleBlockItem(block, new ModelFile.UncheckedModelFile(extend(blockTexture(block), "_inventory")));
                } else if (block instanceof GlobeBlock) {
                    ResourceLocation texture = new ResourceLocation(AdAstra.MOD_ID, "block/globe/" + ForgeRegistries.BLOCKS.getKey(block).getPath());
                    itemModels().getBuilder(name(block)).texture("texture", texture).texture("particle", texture).parent(models().getExistingFile(GLOBE_ITEM));
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

    private ResourceLocation replaceAndCheckPlural(Block block, String replace) {
        return replaceAndCheckPlural(block, replace, "");
    }

    private ResourceLocation replaceAndCheckPlural(Block block, String replace, String replacement) {
        ResourceLocation path = new ResourceLocation(AdAstra.MOD_ID, blockTexture(block).getPath().replace(replace, replacement));
        if (!exFileHelper.exists(path, TEXTURE)) {
            path = new ResourceLocation(AdAstra.MOD_ID, blockTexture(block).getPath().replace(replace, replacement + "s"));
        }
        return path;
    }

    private ResourceLocation prefix(Block block, String prefix) {
        final ResourceLocation id = key(block);
        return new ResourceLocation(id.getNamespace(), prefix + id.getPath());
    }

    private ResourceLocation extend(ResourceLocation rl, String suffix) {
        return new ResourceLocation(rl.getNamespace(), rl.getPath() + suffix);
    }

    private void blockNoState(Block block, ResourceLocation loc, ResourceLocation parent, String texture) {
        models().getBuilder(name(block)).texture(texture, loc).texture("particle", loc).parent(models().getExistingFile(parent));
    }

    private void blockNoState(Block block, ResourceLocation parent, String fileName, ResourceLocation loc, String texture) {
        models().getBuilder(fileName).texture(texture, loc).texture("particle", loc).parent(models().getExistingFile(parent));
    }

    private void blockNoState(String name, ResourceLocation loc, ResourceLocation parent, String texture) {
        models().getBuilder(name).texture(texture, loc).texture("particle", loc).parent(models().getExistingFile(parent));
    }

    // creates a model with a texture and no parent
    private void block(Block block, String texture, String loc) {
        simpleBlock(block, models().getBuilder(name(block)).texture(texture, loc));
    }

    private ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    private void horizontalBlock(Block block, ResourceLocation parent, String textureName) {
        horizontalBlock(block, parent, blockTexture(block), textureName);
    }

    private void horizontalBlock(Block block, ResourceLocation parent, ResourceLocation texture, String textureName) {
        horizontalBlock(block, models().getBuilder(name(block)).texture(textureName, texture).texture("particle", texture).parent(models().getExistingFile(parent)));
    }

    private void block(Block block, ResourceLocation parent, ResourceLocation texture, String textureName) {
        simpleBlock(block, models().getBuilder(name(block)).texture(textureName, texture).texture("particle", texture).parent(models().getExistingFile(parent)));
    }

    private String name(Block block) {
        return key(block).getPath();
    }
}
