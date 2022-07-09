package com.github.alexnijjar.beyond_earth.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import com.github.alexnijjar.beyond_earth.BeyondEarth;
import com.github.alexnijjar.beyond_earth.registry.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CactusBlock;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.CandleBlock;
import net.minecraft.block.CandleCakeBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class OxygenUtils {

    private static Map<Pair<RegistryKey<World>, BlockPos>, Set<BlockPos>> oxygenLocations = new HashMap<>();

    // Checks if there is oxygen in a specific block in a specific dimension.
    public static boolean posHasOxygen(ServerWorld world, BlockPos pos) {
        if (world.getServer().getTicks() <= BeyondEarth.CONFIG.oxygenDistributor.oxygenGracePeriodTicks) {
            return true;
        }
        for (Map.Entry<Pair<RegistryKey<World>, BlockPos>, Set<BlockPos>> entry : oxygenLocations.entrySet()) {
            if (world.getRegistryKey().equals(entry.getKey().getLeft())) {
                if (entry.getValue().contains(pos)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int getOxygenBlocksCount(World world, BlockPos source) {
        if (oxygenLocations.containsKey(Pair.of(Pair.of(world.getRegistryKey(), source), source))) {
            return oxygenLocations.get(Pair.of(Pair.of(world.getRegistryKey(), source), source)).size();
        } else {
            return 0;
        }
    }

    public static void setEntry(World world, BlockPos source, Set<BlockPos> entries) {
        if (oxygenLocations.containsKey(Pair.of(world.getRegistryKey(), source))) {
            Set<BlockPos> locations = oxygenLocations.get(Pair.of(world.getRegistryKey(), source));
            if (!entries.equals(locations)) {
                Set<BlockPos> removedLocations = new HashSet<>(locations);
                removedLocations.removeAll(entries);
                deoxygenizeBlocks(world, removedLocations);
            }
        }

        oxygenLocations.put(Pair.of(world.getRegistryKey(), source), entries);

    }

    public static void removeEntry(World world, BlockPos source) {
        if (!oxygenLocations.containsKey(Pair.of(world.getRegistryKey(), source))) {
            return;
        }
        deoxygenizeBlocks(world, oxygenLocations.get(Pair.of(world.getRegistryKey(), source)));
        oxygenLocations.remove(Pair.of(world.getRegistryKey(), source));
    }

    public static void deoxygenizeBlocks(World world, Set<BlockPos> entries) {
        for (BlockPos pos : entries) {

            BlockState state = world.getBlockState(pos);

            if (state.isAir()) {
                continue;
            }

            Block block = state.getBlock();
            if (block instanceof WallTorchBlock && !block.equals(Blocks.SOUL_WALL_TORCH)) {
                world.setBlockState(pos, ModBlocks.WALL_COAL_TORCH.getDefaultState().with(WallTorchBlock.FACING, state.get(WallTorchBlock.FACING)));
                continue;
            }

            if (block instanceof TorchBlock && !block.equals(Blocks.SOUL_TORCH) && !block.equals(Blocks.SOUL_WALL_TORCH)) {
                world.setBlockState(pos, ModBlocks.COAL_TORCH.getDefaultState());
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

            if (block instanceof PlantBlock || block instanceof CactusBlock) {
                world.removeBlock(pos, true);
                continue;
            }

            if (block instanceof FarmlandBlock) {
                world.setBlockState(pos, state.with(FarmlandBlock.MOISTURE, 0));
                continue;
            }

            if (state.getFluidState().isIn(FluidTags.WATER)) {
                if (block.equals(Blocks.WATER)) {
                    world.setBlockState(pos, Blocks.AIR.getDefaultState());
                } else if (state.contains(Properties.WATERLOGGED)) {
                    world.setBlockState(pos, state.with(Properties.WATERLOGGED, false));
                }
            }
        }
    }
}
