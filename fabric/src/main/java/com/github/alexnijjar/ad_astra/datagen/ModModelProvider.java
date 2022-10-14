package com.github.alexnijjar.ad_astra.datagen;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.registry.ModBlocks;
import com.github.alexnijjar.ad_astra.registry.ModItems;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;

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
		blockStateModelGenerator.registerTorch(ModBlocks.EXTINGUISHED_TORCH.get(), ModBlocks.WALL_EXTINGUISHED_TORCH.get());
		blockStateModelGenerator.registerDoor(ModBlocks.STEEL_DOOR.get());
		blockStateModelGenerator.registerTrapdoor(ModBlocks.STEEL_TRAPDOOR.get());
		blockStateModelGenerator.registerDoor(ModBlocks.GLACIAN_DOOR.get());
		blockStateModelGenerator.registerTrapdoor(ModBlocks.GLACIAN_TRAPDOOR.get());

		blockStateModelGenerator.registerBuiltin(new ModIdentifier("block/aeronos_chest"), ModBlocks.AERONOS_PLANKS.get()).includeWithoutItem(ModBlocks.AERONOS_CHEST.get());
		blockStateModelGenerator.registerBuiltin(new ModIdentifier("block/strophar_chest"), ModBlocks.STROPHAR_PLANKS.get()).includeWithoutItem(ModBlocks.STROPHAR_CHEST.get());
		blockStateModelGenerator.registerDoor(ModBlocks.AERONOS_DOOR.get());
		blockStateModelGenerator.registerTrapdoor(ModBlocks.AERONOS_TRAPDOOR.get());
		blockStateModelGenerator.registerDoor(ModBlocks.STROPHAR_DOOR.get());
		blockStateModelGenerator.registerTrapdoor(ModBlocks.STROPHAR_TRAPDOOR.get());

		ModBlocks.BLOCKS.iterator().forEachRemaining(block -> {
			Identifier id = Registry.BLOCK.getId(block.get());
			if (block instanceof StairsBlock stair) {
				TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(stair.baseBlock);
				BlockTexturePool pool = blockStateModelGenerator.new BlockTexturePool(texturedModel.getTexture());
				pool.stairs(block.get());
			}

			else if (block instanceof PillarBlock) {
				blockStateModelGenerator.registerAxisRotated(block.get(), TexturedModel.END_FOR_TOP_CUBE_COLUMN, TexturedModel.END_FOR_TOP_CUBE_COLUMN_HORIZONTAL);
			}

			else if (block instanceof WallBlock) {
				TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(getReplacedPathBlock(id, "_wall", "s"));
				BlockTexturePool pool = blockStateModelGenerator.new BlockTexturePool(texturedModel.getTexture());
				pool.wall(block.get());
			}

			else if (block instanceof StoneButtonBlock) {
				TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(getReplacedPathBlock(id, "_button", ""));
				BlockTexturePool pool = blockStateModelGenerator.new BlockTexturePool(texturedModel.getTexture());
				pool.button(block.get());
			}

			else if (block instanceof WoodenButtonBlock) {
				TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(getReplacedPathBlock(id, "_button", "_planks"));
				BlockTexturePool pool = blockStateModelGenerator.new BlockTexturePool(texturedModel.getTexture());
				pool.button(block.get());
			}

			else if (block instanceof PressurePlateBlock) {
				if (block != ModBlocks.GLACIAN_PRESSURE_PLATE) {
					TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(getReplacedPathBlock(id, "_pressure_plate", ""));
					BlockTexturePool pool = blockStateModelGenerator.new BlockTexturePool(texturedModel.getTexture());
					pool.pressurePlate(block.get());
				} else {
					TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(getReplacedPathBlock(id, "_pressure_plate", "_planks"));
					BlockTexturePool pool = blockStateModelGenerator.new BlockTexturePool(texturedModel.getTexture());
					pool.pressurePlate(block.get());
				}
			}

			else if (block instanceof FenceBlock) {
				TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(getReplacedPathBlock(id, "_fence", "_planks"));
				BlockTexturePool pool = blockStateModelGenerator.new BlockTexturePool(texturedModel.getTexture());
				pool.fence(block.get());
			}

			else if (block instanceof FenceGateBlock) {
				TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(getReplacedPathBlock(id, "_fence_gate", "_planks"));
				BlockTexturePool pool = blockStateModelGenerator.new BlockTexturePool(texturedModel.getTexture());
				pool.fenceGate(block.get());
			}
		});
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
