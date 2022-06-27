package com.github.alexnijjar.beyond_earth.datagen;

import com.github.alexnijjar.beyond_earth.registry.ModBlocks;
import com.github.alexnijjar.beyond_earth.registry.ModItems;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TexturedModel;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

// TODO: Don't use this! It needs more work.
public class ModModelProvider extends FabricModelProvider implements ModBlocks {

    public ModModelProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerTorch(COAL_TORCH, WALL_COAL_TORCH);
        for (Block block : blocks) {
            Identifier id = Registry.BLOCK.getId(block);
            if (block instanceof StairsBlock) {
                blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createStairsBlockState(block, getInnerStairsModel(id), getRegularStairsModel(id), getOuterStairsModel(id)));
            }

            if (block instanceof PillarBlock) {
                blockStateModelGenerator.registerAxisRotated(block, TexturedModel.END_FOR_TOP_CUBE_COLUMN, TexturedModel.END_FOR_TOP_CUBE_COLUMN_HORIZONTAL);
            }
        }

        registerSlab(blockStateModelGenerator, MOON_STONE_BRICK_SLAB, MOON_STONE);
        registerSlab(blockStateModelGenerator, CHISELED_MOON_STONE_SLAB, CHISELED_MOON_STONE_BRICKS);
        registerSlab(blockStateModelGenerator, POLISHED_MOON_STONE_SLAB, POLISHED_MOON_STONE);
        registerSlab(blockStateModelGenerator, MARS_STONE_BRICK_SLAB, MARS_STONE_BRICKS);
        registerSlab(blockStateModelGenerator, CHISELED_MARS_STONE_SLAB, CHISELED_MARS_STONE_BRICKS);
        registerSlab(blockStateModelGenerator, POLISHED_MARS_STONE_SLAB, POLISHED_MARS_STONE);
        registerSlab(blockStateModelGenerator, MERCURY_STONE_BRICK_SLAB, MERCURY_STONE_BRICKS);
        registerSlab(blockStateModelGenerator, CHISELED_MERCURY_STONE_SLAB, CHISELED_MERCURY_STONE_BRICKS);
        registerSlab(blockStateModelGenerator, POLISHED_MERCURY_STONE_SLAB, POLISHED_MERCURY_STONE);
        registerSlab(blockStateModelGenerator, VENUS_SANDSTONE_BRICK_SLAB, VENUS_SANDSTONE_BRICKS);
        registerSlab(blockStateModelGenerator, VENUS_STONE_BRICK_SLAB, VENUS_STONE_BRICKS);
        registerSlab(blockStateModelGenerator, CHISELED_VENUS_STONE_SLAB, CHISELED_VENUS_STONE_BRICKS);
        registerSlab(blockStateModelGenerator, POLISHED_VENUS_STONE_SLAB, POLISHED_VENUS_STONE);
        registerSlab(blockStateModelGenerator, GLACIO_STONE_BRICK_SLAB, GLACIO_STONE_BRICKS);
        registerSlab(blockStateModelGenerator, CHISELED_GLACIO_STONE_SLAB, CHISELED_GLACIO_STONE_BRICKS);
        registerSlab(blockStateModelGenerator, POLISHED_GLACIO_STONE_SLAB, POLISHED_GLACIO_STONE);

        blockStateModelGenerator.registerSimpleCubeAll(STEEL_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(DESH_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(OSTRUM_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(CALORITE_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(RAW_DESH_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(RAW_OSTRUM_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(RAW_CALORITE_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(IRON_PLATING_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(RUSTED_IRON_PLATING_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(SKY_STONE);
        blockStateModelGenerator.registerSimpleCubeAll(MOON_STONE);
        blockStateModelGenerator.registerSimpleCubeAll(CRACKED_MOON_STONE_BRICKS);
        blockStateModelGenerator.registerSimpleCubeAll(MOON_STONE_BRICKS);
        blockStateModelGenerator.registerSimpleCubeAll(CHISELED_MOON_STONE_BRICKS);
        blockStateModelGenerator.registerSimpleCubeAll(POLISHED_MOON_STONE);
        blockStateModelGenerator.registerSimpleCubeAll(MARS_STONE);
        blockStateModelGenerator.registerSimpleCubeAll(CRACKED_MARS_STONE_BRICKS);
        blockStateModelGenerator.registerSimpleCubeAll(MARS_STONE_BRICKS);
        blockStateModelGenerator.registerSimpleCubeAll(CHISELED_MARS_STONE_BRICKS);
        blockStateModelGenerator.registerSimpleCubeAll(POLISHED_MARS_STONE);
        blockStateModelGenerator.registerSimpleCubeAll(MERCURY_STONE);
        blockStateModelGenerator.registerSimpleCubeAll(CRACKED_MERCURY_STONE_BRICKS);
        blockStateModelGenerator.registerSimpleCubeAll(MERCURY_STONE_BRICKS);
        blockStateModelGenerator.registerSimpleCubeAll(CHISELED_MERCURY_STONE_BRICKS);
        blockStateModelGenerator.registerSimpleCubeAll(POLISHED_MERCURY_STONE);
        blockStateModelGenerator.registerSimpleCubeAll(VENUS_SANDSTONE);
        blockStateModelGenerator.registerSimpleCubeAll(VENUS_SANDSTONE_BRICKS);
        blockStateModelGenerator.registerSimpleCubeAll(VENUS_STONE);
        blockStateModelGenerator.registerSimpleCubeAll(CRACKED_VENUS_STONE_BRICKS);
        blockStateModelGenerator.registerSimpleCubeAll(VENUS_STONE_BRICKS);
        blockStateModelGenerator.registerSimpleCubeAll(CHISELED_VENUS_STONE_BRICKS);
        blockStateModelGenerator.registerSimpleCubeAll(POLISHED_VENUS_STONE);
        blockStateModelGenerator.registerSimpleCubeAll(GLACIO_STONE);
        blockStateModelGenerator.registerSimpleCubeAll(PERMAFROST_STONE);
        blockStateModelGenerator.registerSimpleCubeAll(CRACKED_GLACIO_STONE_BRICKS);
        blockStateModelGenerator.registerSimpleCubeAll(GLACIO_STONE_BRICKS);
        blockStateModelGenerator.registerSimpleCubeAll(CHISELED_GLACIO_STONE_BRICKS);
        blockStateModelGenerator.registerSimpleCubeAll(POLISHED_GLACIO_STONE);
        blockStateModelGenerator.registerSimpleCubeAll(INFERNAL_SPIRE_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(MOON_SAND);
        blockStateModelGenerator.registerSimpleCubeAll(MARS_SAND);
        blockStateModelGenerator.registerSimpleCubeAll(VENUS_SAND);
        blockStateModelGenerator.registerSimpleCubeAll(MOON_CHEESE_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(MOON_DESH_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(MOON_IRON_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(MOON_ICE_SHARD_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(MARS_IRON_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(MARS_DIAMOND_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(MARS_OSTRUM_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(MARS_ICE_SHARD_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(MERCURY_IRON_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(VENUS_COAL_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(VENUS_GOLD_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(VENUS_DIAMOND_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(VENUS_CALORITE_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(GLACIO_ICE_SHARD_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(GLACIO_COAL_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(GLACIO_COPPER_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(GLACIO_IRON_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(GLACIO_LAPIS_ORE);
    }

    public void registerSlab(BlockStateModelGenerator blockStateModelGenerator, Block slab, Block full) {
        Identifier id = Registry.BLOCK.getId(slab);
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSlabBlockState(slab, getBlockModel(id), getTopSlabModel(id), getBlockModel(full)));
    }

    public static Identifier getBlockModel(Block block) {
        Identifier id = Registry.BLOCK.getId(block);
        return new Identifier(id.getNamespace(), "block/" + id.getPath());
    }

    public static Identifier getBlockModel(Identifier id) {
        return new Identifier(id.getNamespace(), "block/" + id.getPath());
    }

    public static Identifier getInnerStairsModel(Identifier id) {
        return new Identifier(id.getNamespace(), "block/" + id.getPath() + "_inner");
    }

    public static Identifier getRegularStairsModel(Identifier id) {
        return new Identifier(id.getNamespace(), "block/" + id.getPath());
    }

    public static Identifier getOuterStairsModel(Identifier id) {
        return new Identifier(id.getNamespace(), "block/" + id.getPath() + "_outer");
    }

    public static Identifier getTopSlabModel(Identifier id) {
        return new Identifier(id.getNamespace(), "block/" + id.getPath() + "_top");
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        for (Item item : ModItems.items) {
            if (!(item instanceof BlockItem)) {
                if (item.getGroup().equals(ModItems.ITEM_GROUP_BASICS) || item.getGroup().equals(ModItems.ITEM_GROUP_MATERIALS) || item.getGroup().equals(ModItems.ITEM_GROUP_SPAWN_EGGS))
                    itemModelGenerator.register(item, Models.GENERATED);
            }
        }

        itemModelGenerator.register(ModItems.OXYGEN_TANK, Models.GENERATED);
        itemModelGenerator.register(ModItems.GUIDE_BOOK, Models.GENERATED);
        itemModelGenerator.register(ModItems.SPACE_PAINTING, Models.GENERATED);
        itemModelGenerator.register(ModItems.CHEESE, Models.GENERATED);
        itemModelGenerator.register(ModItems.OIL_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.FUEL_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.OXYGEN_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.SPACE_HELMET, Models.GENERATED);
        itemModelGenerator.register(ModItems.SPACE_SUIT, Models.GENERATED);
        itemModelGenerator.register(ModItems.SPACE_PANTS, Models.GENERATED);
        itemModelGenerator.register(ModItems.SPACE_BOOTS, Models.GENERATED);
        itemModelGenerator.register(ModItems.NETHERITE_SPACE_HELMET, Models.GENERATED);
        itemModelGenerator.register(ModItems.NETHERITE_SPACE_SUIT, Models.GENERATED);
        itemModelGenerator.register(ModItems.NETHERITE_SPACE_PANTS, Models.GENERATED);
        itemModelGenerator.register(ModItems.NETHERITE_SPACE_BOOTS, Models.GENERATED);
        itemModelGenerator.register(ModItems.JET_SUIT_SPACE_HELMET, Models.GENERATED);
        itemModelGenerator.register(ModItems.JET_SUIT, Models.GENERATED);
        itemModelGenerator.register(ModItems.JET_SUIT_PANTS, Models.GENERATED);
        itemModelGenerator.register(ModItems.JET_SUIT_BOOTS, Models.GENERATED);
    }
}
