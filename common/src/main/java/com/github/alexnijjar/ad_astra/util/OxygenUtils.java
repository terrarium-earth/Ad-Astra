package com.github.alexnijjar.ad_astra.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.registry.ModBlocks;
import com.github.alexnijjar.ad_astra.registry.ModFluids;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CactusBlock;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.CandleBlock;
import net.minecraft.block.CandleCakeBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.block.GrassBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.VineBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class OxygenUtils {

	// Contains every pos in all dimensions with oxygen.
	public static final Map<Pair<RegistryKey<World>, BlockPos>, Set<BlockPos>> OXYGEN_LOCATIONS = new HashMap<>();

	/**
	 * Checks if a world has oxygen, regardless of position.
	 */
	public static boolean worldHasOxygen(World world) {
		if (!AdAstra.worldsWithOxygen.contains(world.getRegistryKey())) {
			// Ensure all non-Ad Astra dimensions have oxygen by default
			if (!ModUtils.isSpaceWorld(world)) {
				return true;
			}
			return false;
		}
		return true;
	}

	/**
	 * Checks if an entity has oxygen.
	 */
	public static boolean entityHasOxygen(World world, LivingEntity entity) {
		return posHasOxygen(world, new BlockPos(entity.getEyePos()));
	}

	/**
	 * Checks if there is oxygen in a specific block in a specific dimension.
	 */
	@SuppressWarnings("deprecation")
	public static boolean posHasOxygen(World world, BlockPos pos) {

		if (!world.isChunkLoaded(pos)) {
			return true;
		}

		if (worldHasOxygen(world)) {
			return true;
		}

		return inDistributorBubble(world, pos);
	}

	public static boolean inDistributorBubble(World world, BlockPos pos) {
		for (Map.Entry<Pair<RegistryKey<World>, BlockPos>, Set<BlockPos>> entry : OXYGEN_LOCATIONS.entrySet()) {
			if (world.getRegistryKey().equals(entry.getKey().getLeft())) {
				if (entry.getValue().contains(pos)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Gets the amount of blocks that an oxygen distributor is distributing.
	 *
	 * @param world  The world to check for oxygen in
	 * @param source The oxygen distributor position
	 * @return The amount of blocks that an oxygen distributor is distributing oxygen to
	 */
	public static int getOxygenBlocksCount(World world, BlockPos source) {
		return OXYGEN_LOCATIONS.getOrDefault(getOxygenSource(world, source), Set.of()).size();
	}

	public static void setEntry(World world, BlockPos source, Set<BlockPos> entries) {
		// Get all the entries that have changed. If they are have been removed, deoxygenate their pos.
		if (!world.isClient) {
			if (OXYGEN_LOCATIONS.containsKey(getOxygenSource(world, source))) {
				Set<BlockPos> changedPositions = new HashSet<>(OXYGEN_LOCATIONS.get(getOxygenSource(world, source)));
				if (changedPositions != null && !changedPositions.isEmpty()) {
					changedPositions.removeAll(entries);
					deoxygenizeBlocks((ServerWorld) world, changedPositions, source);
				}
			}
		}
		OXYGEN_LOCATIONS.put(getOxygenSource(world, source), entries);
	}

	public static void removeEntry(World world, BlockPos source) {
		OxygenUtils.setEntry(world, source, Set.of());
	}

	/**
	 * Removes the oxygen from a set of blocks. For example, turns water into ice or air, converts torches into extinguished torches, puts out flames, kills plants etc.
	 */
	public static void deoxygenizeBlocks(ServerWorld world, Set<BlockPos> entries, BlockPos source) {
		try {
			if (entries == null) {
				return;
			}
			if (entries.isEmpty()) {
				return;
			}

			if (worldHasOxygen(world)) {
				OXYGEN_LOCATIONS.remove(getOxygenSource(world, source));
				return;
			}

			for (BlockPos pos : new HashSet<>(entries)) {

				BlockState state = world.getBlockState(pos);

				OXYGEN_LOCATIONS.get(getOxygenSource(world, source)).remove(pos);
				if (posHasOxygen(world, pos)) {
					continue;
				}

				if (state.isAir()) {
					continue;
				}

				Block block = state.getBlock();
				if (block instanceof WallTorchBlock && !block.equals(Blocks.SOUL_WALL_TORCH)) {
					world.setBlockState(pos, ModBlocks.WALL_EXTINGUISHED_TORCH.get().getDefaultState().with(WallTorchBlock.FACING, state.get(WallTorchBlock.FACING)));
					continue;
				}

				if (block instanceof TorchBlock && !block.equals(Blocks.SOUL_TORCH) && !block.equals(Blocks.SOUL_WALL_TORCH)) {
					world.setBlockState(pos, ModBlocks.EXTINGUISHED_TORCH.get().getDefaultState());
					continue;
				}

				if (block instanceof CandleCakeBlock) {
					world.setBlockState(pos, block.getDefaultState().with(CandleCakeBlock.LIT, false));
					continue;
				}

				if (block instanceof CandleBlock) {
					world.setBlockState(pos, block.getDefaultState().with(CandleBlock.CANDLES, state.get(CandleBlock.CANDLES)).with(CandleBlock.LIT, false));
					continue;
				}

				if (block instanceof FireBlock) {
					world.removeBlock(pos, false);
					continue;
				}

				if (block instanceof CampfireBlock) {
					world.setBlockState(pos, state.with(CampfireBlock.LIT, false).with(CampfireBlock.FACING, state.get(CampfireBlock.FACING)));
					continue;
				}

				if (block instanceof GrassBlock) {
					world.setBlockState(pos, Blocks.DIRT.getDefaultState());
					continue;
				}

				if (block instanceof PlantBlock || block instanceof CactusBlock || block instanceof VineBlock) {
					world.removeBlock(pos, true);
					continue;
				}

				if (block instanceof FarmlandBlock) {
					world.setBlockState(pos, state.with(FarmlandBlock.MOISTURE, 0));
					continue;
				}

				if (state.getFluidState().isIn(FluidTags.WATER)) {
					if (!block.equals(ModFluids.CRYO_FUEL_BLOCK)) {
						if (ModUtils.getWorldTemperature(world) < 0) {
							world.setBlockState(pos, Blocks.ICE.getDefaultState());
						} else {
							world.setBlockState(pos, Blocks.AIR.getDefaultState());
						}
					} else if (state.contains(Properties.WATERLOGGED)) {
						world.setBlockState(pos, state.with(Properties.WATERLOGGED, false));
					}
				}
			}
		} catch (UnsupportedOperationException e) {
			AdAstra.LOGGER.error("Error deoxygenizing blocks");
			e.printStackTrace();
		}
	}

	private static Pair<RegistryKey<World>, BlockPos> getOxygenSource(World world, BlockPos source) {
		return Pair.of(world.getRegistryKey(), source);
	}
}