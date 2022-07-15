package com.github.alexnijjar.beyond_earth.util;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.HorizontalConnectingBlock;
import net.minecraft.block.IceBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

/**
 * A 3D flood fill algorithm that will fill an entire structure with oxygen.
 */
public class OxygenFillerAlgorithm {

    private World world;
    private int maxBlockChecks;

    public OxygenFillerAlgorithm(World world, int maxBlockChecks) {
        this.world = world;
        this.maxBlockChecks = maxBlockChecks;
    }

    public Set<BlockPos> runAlgorithm(BlockPos start) {

        Set<BlockPos> positions = new HashSet<>();
        // This is a non-recursive flood fill algorithm, because it has better performance and avoids stack-overflow errors
        LinkedList<BlockPos> queue = new LinkedList<>();
        queue.add(start);
        while (!queue.isEmpty()) {

            // Cancel if the the amount of oxygen exceeds the limit. This is the case if there was an oxygen leak or the room was too
            // large to support the oxygen
            if (positions.size() >= this.maxBlockChecks) {
                break;
            }

            BlockPos pos = queue.poll();

            // Don't have oxygen above the world height limit
            if (pos.getY() > this.world.getHeight()) {
                break;
            }

            BlockState state = this.world.getBlockState(pos); 

            // Cancel for solid blocks but still let things like slabs, torches and ladders through
            if (state.isFullCube(this.world, pos)) {
                // Allow oxygen to go through ice blocks, so that they still turn into water when broken
                if (!(state.getBlock() instanceof IceBlock)) {
                    continue;
                }
            }

            positions.add(pos);

            // Allow oxygen to stay in closed doors but exit when they are open
            if (this.checkDoor(state)) {
                continue;
            }

            // Prevent oxygen from escaping from glass panes
            if (state.getBlock() instanceof HorizontalConnectingBlock) {
                if (!state.isOpaque() && !state.getBlock().equals(Blocks.IRON_BARS)) {
                    continue;
                }
            }

            for (Direction dir : Direction.values()) {
                BlockPos offsetPos = pos.offset(dir);
                if (!queue.contains(offsetPos) && !positions.contains(offsetPos)) {
                    queue.add(offsetPos);
                }
            }
        }
        return positions;
    }

    // Lets oxygen pass through doors when they are open
    private boolean checkDoor(BlockState state) {
        return state.contains(DoorBlock.OPEN) && !state.get(DoorBlock.OPEN) || state.contains(TrapdoorBlock.OPEN) && !state.get(TrapdoorBlock.OPEN);
    }
}