package com.github.alexnijjar.beyond_earth.datagen;

import com.github.alexnijjar.beyond_earth.BeyondEarth;
import com.github.alexnijjar.beyond_earth.registry.ModBlocks;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.StoneButtonBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.model.BlockStateModelGenerator;
import net.minecraft.data.client.model.BlockStateModelGenerator.BlockTexturePool;
import net.minecraft.data.client.model.Models;
import net.minecraft.data.client.model.TexturedModel;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModModelProvider extends FabricModelProvider implements ModBlocks {

    public ModModelProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerTorch(COAL_TORCH, WALL_COAL_TORCH);
        blockStateModelGenerator.registerDoor(STEEL_DOOR);
        for (Block block : blocks) {
            Identifier id = Registry.BLOCK.getId(block);
            if (block instanceof StairsBlock stair) {
                TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(stair.baseBlock);
                BlockTexturePool pool = blockStateModelGenerator.new BlockTexturePool(texturedModel.getTexture());
                pool.stairs(block);
            }

            if (block instanceof PillarBlock) {
                blockStateModelGenerator.registerAxisRotated(block, TexturedModel.END_FOR_TOP_CUBE_COLUMN, TexturedModel.END_FOR_TOP_CUBE_COLUMN_HORIZONTAL);
            }

            if (block instanceof WallBlock) {
                TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(getReplacedPathBlock(id, "_wall", "s"));
                BlockTexturePool pool = blockStateModelGenerator.new BlockTexturePool(texturedModel.getTexture());
                pool.wall(block);
            }

            if (block instanceof StoneButtonBlock) {
                TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(getReplacedPathBlock(id, "_button", ""));
                BlockTexturePool pool = blockStateModelGenerator.new BlockTexturePool(texturedModel.getTexture());
                pool.button(block);
            }

            if (block instanceof PressurePlateBlock) {
                TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(getReplacedPathBlock(id, "_pressure_plate", ""));
                BlockTexturePool pool = blockStateModelGenerator.new BlockTexturePool(texturedModel.getTexture());
                pool.pressurePlate(block);
            }
        }
        registerSlab(blockStateModelGenerator, IRON_PLATING_SLAB, IRON_PLATING);
        registerSlab(blockStateModelGenerator, STEEL_PLATING_SLAB, STEEL_PLATING);
        registerSlab(blockStateModelGenerator, DESH_PLATING_SLAB, DESH_PLATING);
        registerSlab(blockStateModelGenerator, OSTRUM_PLATING_SLAB, OSTRUM_PLATING);
        registerSlab(blockStateModelGenerator, CALORITE_PLATING_SLAB, CALORITE_PLATING);
        registerSlab(blockStateModelGenerator, MOON_STONE_BRICK_SLAB, MOON_STONE_BRICKS);
        registerSlab(blockStateModelGenerator, CHISELED_MOON_STONE_SLAB, CHISELED_MOON_STONE_BRICKS);
        registerSlab(blockStateModelGenerator, POLISHED_MOON_STONE_SLAB, POLISHED_MOON_STONE);
        registerSlab(blockStateModelGenerator, MARS_STONE_BRICK_SLAB, MARS_STONE_BRICKS);
        registerSlab(blockStateModelGenerator, CHISELED_MARS_STONE_SLAB, CHISELED_MARS_STONE_BRICKS);
        registerSlab(blockStateModelGenerator, POLISHED_MARS_STONE_SLAB, POLISHED_MARS_STONE);
        registerSlab(blockStateModelGenerator, VENUS_STONE_BRICK_SLAB, VENUS_STONE_BRICKS);
        registerSlab(blockStateModelGenerator, CHISELED_VENUS_STONE_SLAB, CHISELED_VENUS_STONE_BRICKS);
        registerSlab(blockStateModelGenerator, POLISHED_VENUS_STONE_SLAB, POLISHED_VENUS_STONE);
        registerSlab(blockStateModelGenerator, MERCURY_STONE_BRICK_SLAB, MERCURY_STONE_BRICKS);
        registerSlab(blockStateModelGenerator, CHISELED_MERCURY_STONE_SLAB, CHISELED_MERCURY_STONE_BRICKS);
        registerSlab(blockStateModelGenerator, POLISHED_MERCURY_STONE_SLAB, POLISHED_MERCURY_STONE);
        registerSlab(blockStateModelGenerator, GLACIO_STONE_BRICK_SLAB, GLACIO_STONE_BRICKS);
        registerSlab(blockStateModelGenerator, CHISELED_GLACIO_STONE_SLAB, CHISELED_GLACIO_STONE_BRICKS);
        registerSlab(blockStateModelGenerator, POLISHED_GLACIO_STONE_SLAB, POLISHED_GLACIO_STONE);
        registerSlab(blockStateModelGenerator, PERMAFROST_BRICK_SLAB, PERMAFROST_BRICKS);
    }

    public static void registerSlab(BlockStateModelGenerator blockStateModelGenerator, Block slab, Block source) {
        TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(source);
        BlockTexturePool pool = blockStateModelGenerator.new BlockTexturePool(texturedModel.getTexture());
        pool.base(source, Models.CUBE_ALL);
        pool.slab(slab);
    }

    public static Block getReplacedPathBlock(Identifier id, String text, String replacement) {
        Identifier newId = new Identifier(id.getNamespace(), id.getPath().replace(text, replacement));
        if (Registry.BLOCK.get(newId).equals(Blocks.AIR)) {
            BeyondEarth.LOGGER.error("Could not find block for id: " + newId);
        }
        return Registry.BLOCK.get(newId);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
    }
}
