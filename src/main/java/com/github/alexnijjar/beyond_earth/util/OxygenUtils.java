package com.github.alexnijjar.beyond_earth.util;

import java.util.ArrayList;
import java.util.List;

import com.github.alexnijjar.beyond_earth.registry.ModBlocks;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;

public class OxygenUtils {

    public static List<BlockPos> oxygenLocations = new ArrayList<>();

    static {
        ServerTickEvents.START_SERVER_TICK.register(server -> {
        });
    }

    public static boolean posHasOxygen(BlockPos pos) {
        return oxygenLocations.contains(pos);
    }

    public static void removeEntries(ServerWorld world, List<BlockPos> entries) {
        for (BlockPos pos : entries) {
            BlockState state = world.getBlockState(pos);

            if (state.isAir()) {
                continue;
            }
            
            if (state.getBlock() instanceof TorchBlock && !state.getBlock().equals(Blocks.SOUL_TORCH)) {
                world.setBlockState(pos, ModBlocks.COAL_TORCH.getDefaultState());
                continue;
            }

            if (state.getBlock() instanceof WallTorchBlock && !state.getBlock().equals(Blocks.SOUL_WALL_TORCH)) {
                world.setBlockState(pos, ModBlocks.WALL_COAL_TORCH.getDefaultState().with(WallTorchBlock.FACING, state.get(WallTorchBlock.FACING)));
                continue;
            }

            if (state.getBlock() instanceof FireBlock) {
                world.removeBlock(pos, false);
                continue;
            }

            if (state.getFluidState().isIn(FluidTags.WATER)) {
                if (state.getBlock().equals(Blocks.WATER)) {
                    world.setBlockState(pos, Blocks.AIR.getDefaultState());
                } else {
                    world.setBlockState(pos, state.with(Properties.WATERLOGGED, false));
                }
                continue;
            }
        }
        oxygenLocations.removeAll(entries);
    }
}
