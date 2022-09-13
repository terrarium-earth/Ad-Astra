package com.github.alexnijjar.ad_astra.datagen;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.registry.ModBlocks;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.StoneButtonBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.WoodenButtonBlock;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.model.BlockStateModelGenerator;
import net.minecraft.data.client.model.BlockStateModelGenerator.BlockTexturePool;
import net.minecraft.data.client.model.Models;
import net.minecraft.data.client.model.TexturedModel;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModModelProvider extends FabricModelProvider {

	public ModModelProvider(FabricDataGenerator dataGenerator) {
		super(dataGenerator);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
		blockStateModelGenerator.registerTorch(ModBlocks.COAL_TORCH, ModBlocks.WALL_COAL_TORCH);
		blockStateModelGenerator.registerDoor(ModBlocks.STEEL_DOOR);
		blockStateModelGenerator.registerTrapdoor(ModBlocks.STEEL_TRAPDOOR);
		blockStateModelGenerator.registerDoor(ModBlocks.GLACIAN_DOOR);
		blockStateModelGenerator.registerTrapdoor(ModBlocks.GLACIAN_TRAPDOOR);

		// blockStateModelGenerator.registerBuiltin(new ModIdentifier("block/aeronos_chest"), ModBlocks.AERONOS_PLANKS).includeWithoutItem(ModBlocks.AERONOS_CHEST);
		// blockStateModelGenerator.registerBuiltin(new ModIdentifier("block/strophar_chest"), ModBlocks.STROPHAR_PLANKS).includeWithoutItem(ModBlocks.STROPHAR_CHEST);
		blockStateModelGenerator.registerDoor(ModBlocks.AERONOS_DOOR);
		blockStateModelGenerator.registerTrapdoor(ModBlocks.AERONOS_TRAPDOOR);
		blockStateModelGenerator.registerDoor(ModBlocks.STROPHAR_DOOR);
		blockStateModelGenerator.registerTrapdoor(ModBlocks.STROPHAR_TRAPDOOR);

		for (Block block : ModBlocks.blocks) {
			Identifier id = Registry.BLOCK.getId(block);
			if (block instanceof StairsBlock stair) {
				TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(stair.baseBlock);
				BlockTexturePool pool = blockStateModelGenerator.new BlockTexturePool(texturedModel.getTexture());
				pool.stairs(block);
			}

			else if (block instanceof PillarBlock) {
				blockStateModelGenerator.registerAxisRotated(block, TexturedModel.END_FOR_TOP_CUBE_COLUMN, TexturedModel.END_FOR_TOP_CUBE_COLUMN_HORIZONTAL);
			}

			else if (block instanceof WallBlock) {
				TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(getReplacedPathBlock(id, "_wall", "s"));
				BlockTexturePool pool = blockStateModelGenerator.new BlockTexturePool(texturedModel.getTexture());
				pool.wall(block);
			}

			else if (block instanceof StoneButtonBlock) {
				TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(getReplacedPathBlock(id, "_button", ""));
				BlockTexturePool pool = blockStateModelGenerator.new BlockTexturePool(texturedModel.getTexture());
				pool.button(block);
			}

			else if (block instanceof WoodenButtonBlock) {
				TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(getReplacedPathBlock(id, "_button", "_planks"));
				BlockTexturePool pool = blockStateModelGenerator.new BlockTexturePool(texturedModel.getTexture());
				pool.button(block);
			}

			else if (block instanceof PressurePlateBlock) {
				if (block != ModBlocks.GLACIAN_PRESSURE_PLATE) {
					TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(getReplacedPathBlock(id, "_pressure_plate", ""));
					BlockTexturePool pool = blockStateModelGenerator.new BlockTexturePool(texturedModel.getTexture());
					pool.pressurePlate(block);
				} else {
					TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(getReplacedPathBlock(id, "_pressure_plate", "_planks"));
					BlockTexturePool pool = blockStateModelGenerator.new BlockTexturePool(texturedModel.getTexture());
					pool.pressurePlate(block);
				}
			}

			else if (block instanceof FenceBlock) {
				TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(getReplacedPathBlock(id, "_fence", "_planks"));
				BlockTexturePool pool = blockStateModelGenerator.new BlockTexturePool(texturedModel.getTexture());
				pool.fence(block);
			}

			else if (block instanceof FenceGateBlock) {
				TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(getReplacedPathBlock(id, "_fence_gate", "_planks"));
				BlockTexturePool pool = blockStateModelGenerator.new BlockTexturePool(texturedModel.getTexture());
				pool.fenceGate(block);
			}
		}
		registerSlab(blockStateModelGenerator, ModBlocks.IRON_PLATING_SLAB, ModBlocks.IRON_PLATING);
		registerSlab(blockStateModelGenerator, ModBlocks.STEEL_PLATING_SLAB, ModBlocks.STEEL_PLATING);
		registerSlab(blockStateModelGenerator, ModBlocks.DESH_PLATING_SLAB, ModBlocks.DESH_PLATING);
		registerSlab(blockStateModelGenerator, ModBlocks.OSTRUM_PLATING_SLAB, ModBlocks.OSTRUM_PLATING);
		registerSlab(blockStateModelGenerator, ModBlocks.CALORITE_PLATING_SLAB, ModBlocks.CALORITE_PLATING);
		registerSlab(blockStateModelGenerator, ModBlocks.MOON_STONE_BRICK_SLAB, ModBlocks.MOON_STONE_BRICKS);
		registerSlab(blockStateModelGenerator, ModBlocks.CHISELED_MOON_STONE_SLAB, ModBlocks.CHISELED_MOON_STONE_BRICKS);
		registerSlab(blockStateModelGenerator, ModBlocks.POLISHED_MOON_STONE_SLAB, ModBlocks.POLISHED_MOON_STONE);
		registerSlab(blockStateModelGenerator, ModBlocks.MARS_STONE_BRICK_SLAB, ModBlocks.MARS_STONE_BRICKS);
		registerSlab(blockStateModelGenerator, ModBlocks.CHISELED_MARS_STONE_SLAB, ModBlocks.CHISELED_MARS_STONE_BRICKS);
		registerSlab(blockStateModelGenerator, ModBlocks.POLISHED_MARS_STONE_SLAB, ModBlocks.POLISHED_MARS_STONE);
		registerSlab(blockStateModelGenerator, ModBlocks.VENUS_STONE_BRICK_SLAB, ModBlocks.VENUS_STONE_BRICKS);
		registerSlab(blockStateModelGenerator, ModBlocks.CHISELED_VENUS_STONE_SLAB, ModBlocks.CHISELED_VENUS_STONE_BRICKS);
		registerSlab(blockStateModelGenerator, ModBlocks.POLISHED_VENUS_STONE_SLAB, ModBlocks.POLISHED_VENUS_STONE);
		registerSlab(blockStateModelGenerator, ModBlocks.MERCURY_STONE_BRICK_SLAB, ModBlocks.MERCURY_STONE_BRICKS);
		registerSlab(blockStateModelGenerator, ModBlocks.CHISELED_MERCURY_STONE_SLAB, ModBlocks.CHISELED_MERCURY_STONE_BRICKS);
		registerSlab(blockStateModelGenerator, ModBlocks.POLISHED_MERCURY_STONE_SLAB, ModBlocks.POLISHED_MERCURY_STONE);
		registerSlab(blockStateModelGenerator, ModBlocks.GLACIO_STONE_BRICK_SLAB, ModBlocks.GLACIO_STONE_BRICKS);
		registerSlab(blockStateModelGenerator, ModBlocks.CHISELED_GLACIO_STONE_SLAB, ModBlocks.CHISELED_GLACIO_STONE_BRICKS);
		registerSlab(blockStateModelGenerator, ModBlocks.POLISHED_GLACIO_STONE_SLAB, ModBlocks.POLISHED_GLACIO_STONE);
		registerSlab(blockStateModelGenerator, ModBlocks.PERMAFROST_BRICK_SLAB, ModBlocks.PERMAFROST_BRICKS);
		registerSlab(blockStateModelGenerator, ModBlocks.POLISHED_PERMAFROST_SLAB, ModBlocks.POLISHED_PERMAFROST);
		registerSlab(blockStateModelGenerator, ModBlocks.CHISELED_PERMAFROST_BRICK_SLAB, ModBlocks.CHISELED_PERMAFROST_BRICKS);
		registerSlab(blockStateModelGenerator, ModBlocks.MOON_COBBLESTONE_SLAB, ModBlocks.MOON_COBBLESTONE);
		registerSlab(blockStateModelGenerator, ModBlocks.MOON_STONE_SLAB, ModBlocks.MOON_STONE);
		registerSlab(blockStateModelGenerator, ModBlocks.MARS_COBBLESTONE_SLAB, ModBlocks.MARS_COBBLESTONE);
		registerSlab(blockStateModelGenerator, ModBlocks.MARS_STONE_SLAB, ModBlocks.MARS_STONE);
		registerSlab(blockStateModelGenerator, ModBlocks.VENUS_COBBLESTONE_SLAB, ModBlocks.VENUS_COBBLESTONE);
		registerSlab(blockStateModelGenerator, ModBlocks.VENUS_STONE_SLAB, ModBlocks.VENUS_STONE);
		registerSlab(blockStateModelGenerator, ModBlocks.MERCURY_COBBLESTONE_SLAB, ModBlocks.MERCURY_COBBLESTONE);
		registerSlab(blockStateModelGenerator, ModBlocks.MERCURY_STONE_SLAB, ModBlocks.MERCURY_STONE);
		registerSlab(blockStateModelGenerator, ModBlocks.GLACIO_COBBLESTONE_SLAB, ModBlocks.GLACIO_COBBLESTONE);
		registerSlab(blockStateModelGenerator, ModBlocks.GLACIO_STONE_SLAB, ModBlocks.GLACIO_STONE);
		registerSlab(blockStateModelGenerator, ModBlocks.GLACIAN_SLAB, ModBlocks.GLACIAN_PLANKS);
		registerSlab(blockStateModelGenerator, ModBlocks.AERONOS_SLAB, ModBlocks.AERONOS_PLANKS);
		registerSlab(blockStateModelGenerator, ModBlocks.STROPHAR_SLAB, ModBlocks.STROPHAR_PLANKS);
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
			AdAstra.LOGGER.error("Could not find block for id: " + newId + " id: " + id + " text: " + text + " replacement: " + replacement);
		}
		return Registry.BLOCK.get(newId);
	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {
	}
}
