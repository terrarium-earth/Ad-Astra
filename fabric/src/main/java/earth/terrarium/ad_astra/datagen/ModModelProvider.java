package earth.terrarium.ad_astra.datagen;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.mixin.fabric.StairBlockAccessor;
import earth.terrarium.ad_astra.registry.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.core.Registry;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.BlockModelGenerators.BlockFamilyProvider;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;

public class ModModelProvider extends FabricModelProvider {

    public ModModelProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    public static void registerSlab(BlockModelGenerators blockStateModelGenerator, Block slab, Block source) {
        TexturedModel texturedModel = TexturedModel.CUBE.get(source);
        BlockFamilyProvider pool = blockStateModelGenerator.new BlockFamilyProvider(texturedModel.getMapping());
        pool.fullBlock(source, ModelTemplates.CUBE_ALL);
        pool.slab(slab);
    }

    public static Block getReplacedPathBlock(ResourceLocation id, String text, String replacement) {
        ResourceLocation newId = new ResourceLocation(id.getNamespace(), id.getPath().replace(text, replacement));
        if (Registry.BLOCK.get(newId).equals(Blocks.AIR)) {
            AdAstra.LOGGER.error("Could not find block for id: " + newId + " id: " + id + " text: " + text + " replacement: " + replacement);
        }
        return Registry.BLOCK.get(newId);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        blockStateModelGenerator.createNormalTorch(ModBlocks.EXTINGUISHED_TORCH.get(), ModBlocks.WALL_EXTINGUISHED_TORCH.get());
        blockStateModelGenerator.createDoor(ModBlocks.STEEL_DOOR.get());
        blockStateModelGenerator.createTrapdoor(ModBlocks.STEEL_TRAPDOOR.get());
        blockStateModelGenerator.createDoor(ModBlocks.GLACIAN_DOOR.get());
        blockStateModelGenerator.createTrapdoor(ModBlocks.GLACIAN_TRAPDOOR.get());

        blockStateModelGenerator.blockEntityModels(new ResourceLocation(AdAstra.MOD_ID, "block/aeronos_chest"), ModBlocks.AERONOS_PLANKS.get()).createWithoutBlockItem(ModBlocks.AERONOS_CHEST.get());
        blockStateModelGenerator.blockEntityModels(new ResourceLocation(AdAstra.MOD_ID, "block/strophar_chest"), ModBlocks.STROPHAR_PLANKS.get()).createWithoutBlockItem(ModBlocks.STROPHAR_CHEST.get());
        blockStateModelGenerator.createDoor(ModBlocks.AERONOS_DOOR.get());
        blockStateModelGenerator.createTrapdoor(ModBlocks.AERONOS_TRAPDOOR.get());
        blockStateModelGenerator.createDoor(ModBlocks.STROPHAR_DOOR.get());
        blockStateModelGenerator.createTrapdoor(ModBlocks.STROPHAR_TRAPDOOR.get());

        for (Block block : Registry.BLOCK) {
            if (AdAstra.MOD_ID.equals(Registry.BLOCK.getKey(block).getNamespace())) {
                ResourceLocation id = Registry.BLOCK.getKey(block);
                if (block instanceof StairBlockAccessor stair) {
                    TexturedModel texturedModel = TexturedModel.CUBE.get(stair.getBase());
                    BlockFamilyProvider pool = blockStateModelGenerator.new BlockFamilyProvider(texturedModel.getMapping());
                    pool.stairs(block);
                } else if (block instanceof RotatedPillarBlock) {
                    blockStateModelGenerator.createRotatedPillarWithHorizontalVariant(block, TexturedModel.COLUMN_ALT, TexturedModel.COLUMN_HORIZONTAL_ALT);
                } else if (block instanceof WallBlock) {
                    TexturedModel texturedModel = TexturedModel.CUBE.get(getReplacedPathBlock(id, "_wall", "s"));
                    BlockFamilyProvider pool = blockStateModelGenerator.new BlockFamilyProvider(texturedModel.getMapping());
                    pool.wall(block);
                } else if (block instanceof StoneButtonBlock) {
                    TexturedModel texturedModel = TexturedModel.CUBE.get(getReplacedPathBlock(id, "_button", ""));
                    BlockFamilyProvider pool = blockStateModelGenerator.new BlockFamilyProvider(texturedModel.getMapping());
                    pool.button(block);
                } else if (block instanceof WoodButtonBlock) {
                    TexturedModel texturedModel = TexturedModel.CUBE.get(getReplacedPathBlock(id, "_button", "_planks"));
                    BlockFamilyProvider pool = blockStateModelGenerator.new BlockFamilyProvider(texturedModel.getMapping());
                    pool.button(block);
                } else if (block instanceof PressurePlateBlock) {
                    if (block != ModBlocks.GLACIAN_PRESSURE_PLATE) {
                        TexturedModel texturedModel = TexturedModel.CUBE.get(getReplacedPathBlock(id, "_pressure_plate", ""));
                        BlockFamilyProvider pool = blockStateModelGenerator.new BlockFamilyProvider(texturedModel.getMapping());
                        pool.pressurePlate(block);
                    } else {
                        TexturedModel texturedModel = TexturedModel.CUBE.get(getReplacedPathBlock(id, "_pressure_plate", "_planks"));
                        BlockFamilyProvider pool = blockStateModelGenerator.new BlockFamilyProvider(texturedModel.getMapping());
                        pool.pressurePlate(block);
                    }
                } else if (block instanceof FenceBlock) {
                    TexturedModel texturedModel = TexturedModel.CUBE.get(getReplacedPathBlock(id, "_fence", "_planks"));
                    BlockFamilyProvider pool = blockStateModelGenerator.new BlockFamilyProvider(texturedModel.getMapping());
                    pool.fence(block);
                } else if (block instanceof FenceGateBlock) {
                    TexturedModel texturedModel = TexturedModel.CUBE.get(getReplacedPathBlock(id, "_fence_gate", "_planks"));
                    BlockFamilyProvider pool = blockStateModelGenerator.new BlockFamilyProvider(texturedModel.getMapping());
                    pool.fenceGate(block);
                }
            }
        }
        registerSlab(blockStateModelGenerator, ModBlocks.IRON_PLATING_SLAB.get(), ModBlocks.IRON_PLATING.get());
        registerSlab(blockStateModelGenerator, ModBlocks.STEEL_PLATING_SLAB.get(), ModBlocks.STEEL_PLATING.get());
        registerSlab(blockStateModelGenerator, ModBlocks.DESH_PLATING_SLAB.get(), ModBlocks.DESH_PLATING.get());
        registerSlab(blockStateModelGenerator, ModBlocks.OSTRUM_PLATING_SLAB.get(), ModBlocks.OSTRUM_PLATING.get());
        registerSlab(blockStateModelGenerator, ModBlocks.CALORITE_PLATING_SLAB.get(), ModBlocks.CALORITE_PLATING.get());
        registerSlab(blockStateModelGenerator, ModBlocks.MOON_STONE_BRICK_SLAB.get(), ModBlocks.MOON_STONE_BRICKS.get());
        registerSlab(blockStateModelGenerator, ModBlocks.CHISELED_MOON_STONE_SLAB.get(), ModBlocks.CHISELED_MOON_STONE_BRICKS.get());
        registerSlab(blockStateModelGenerator, ModBlocks.POLISHED_MOON_STONE_SLAB.get(), ModBlocks.POLISHED_MOON_STONE.get());
        registerSlab(blockStateModelGenerator, ModBlocks.MARS_STONE_BRICK_SLAB.get(), ModBlocks.MARS_STONE_BRICKS.get());
        registerSlab(blockStateModelGenerator, ModBlocks.CHISELED_MARS_STONE_SLAB.get(), ModBlocks.CHISELED_MARS_STONE_BRICKS.get());
        registerSlab(blockStateModelGenerator, ModBlocks.POLISHED_MARS_STONE_SLAB.get(), ModBlocks.POLISHED_MARS_STONE.get());
        registerSlab(blockStateModelGenerator, ModBlocks.VENUS_STONE_BRICK_SLAB.get(), ModBlocks.VENUS_STONE_BRICKS.get());
        registerSlab(blockStateModelGenerator, ModBlocks.CHISELED_VENUS_STONE_SLAB.get(), ModBlocks.CHISELED_VENUS_STONE_BRICKS.get());
        registerSlab(blockStateModelGenerator, ModBlocks.POLISHED_VENUS_STONE_SLAB.get(), ModBlocks.POLISHED_VENUS_STONE.get());
        registerSlab(blockStateModelGenerator, ModBlocks.MERCURY_STONE_BRICK_SLAB.get(), ModBlocks.MERCURY_STONE_BRICKS.get());
        registerSlab(blockStateModelGenerator, ModBlocks.CHISELED_MERCURY_STONE_SLAB.get(), ModBlocks.CHISELED_MERCURY_STONE_BRICKS.get());
        registerSlab(blockStateModelGenerator, ModBlocks.POLISHED_MERCURY_STONE_SLAB.get(), ModBlocks.POLISHED_MERCURY_STONE.get());
        registerSlab(blockStateModelGenerator, ModBlocks.GLACIO_STONE_BRICK_SLAB.get(), ModBlocks.GLACIO_STONE_BRICKS.get());
        registerSlab(blockStateModelGenerator, ModBlocks.CHISELED_GLACIO_STONE_SLAB.get(), ModBlocks.CHISELED_GLACIO_STONE_BRICKS.get());
        registerSlab(blockStateModelGenerator, ModBlocks.POLISHED_GLACIO_STONE_SLAB.get(), ModBlocks.POLISHED_GLACIO_STONE.get());
        registerSlab(blockStateModelGenerator, ModBlocks.PERMAFROST_BRICK_SLAB.get(), ModBlocks.PERMAFROST_BRICKS.get());
        registerSlab(blockStateModelGenerator, ModBlocks.POLISHED_PERMAFROST_SLAB.get(), ModBlocks.POLISHED_PERMAFROST.get());
        registerSlab(blockStateModelGenerator, ModBlocks.CHISELED_PERMAFROST_BRICK_SLAB.get(), ModBlocks.CHISELED_PERMAFROST_BRICKS.get());
        registerSlab(blockStateModelGenerator, ModBlocks.MOON_COBBLESTONE_SLAB.get(), ModBlocks.MOON_COBBLESTONE.get());
        registerSlab(blockStateModelGenerator, ModBlocks.MOON_STONE_SLAB.get(), ModBlocks.MOON_STONE.get());
        registerSlab(blockStateModelGenerator, ModBlocks.MARS_COBBLESTONE_SLAB.get(), ModBlocks.MARS_COBBLESTONE.get());
        registerSlab(blockStateModelGenerator, ModBlocks.MARS_STONE_SLAB.get(), ModBlocks.MARS_STONE.get());
        registerSlab(blockStateModelGenerator, ModBlocks.VENUS_COBBLESTONE_SLAB.get(), ModBlocks.VENUS_COBBLESTONE.get());
        registerSlab(blockStateModelGenerator, ModBlocks.VENUS_STONE_SLAB.get(), ModBlocks.VENUS_STONE.get());
        registerSlab(blockStateModelGenerator, ModBlocks.MERCURY_COBBLESTONE_SLAB.get(), ModBlocks.MERCURY_COBBLESTONE.get());
        registerSlab(blockStateModelGenerator, ModBlocks.MERCURY_STONE_SLAB.get(), ModBlocks.MERCURY_STONE.get());
        registerSlab(blockStateModelGenerator, ModBlocks.GLACIO_COBBLESTONE_SLAB.get(), ModBlocks.GLACIO_COBBLESTONE.get());
        registerSlab(blockStateModelGenerator, ModBlocks.GLACIO_STONE_SLAB.get(), ModBlocks.GLACIO_STONE.get());
        registerSlab(blockStateModelGenerator, ModBlocks.GLACIAN_SLAB.get(), ModBlocks.GLACIAN_PLANKS.get());
        registerSlab(blockStateModelGenerator, ModBlocks.AERONOS_SLAB.get(), ModBlocks.AERONOS_PLANKS.get());
        registerSlab(blockStateModelGenerator, ModBlocks.STROPHAR_SLAB.get(), ModBlocks.STROPHAR_PLANKS.get());
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
    }
}
