package com.github.alexnijjar.beyond_earth.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.HorizontalConnectingBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

// A 3d flood fill algorithm that will fill an entire structure with oxygen.
public class OxygenFillerAlgorithm {

    private World world;
    private int maxBlockChecks;
    private boolean oxygenLeak;
    private List<BlockPos> positions = new ArrayList<>();

    public OxygenFillerAlgorithm(World world, int maxBlockChecks) {
        this.world = world;
        this.maxBlockChecks = maxBlockChecks;
    }

    public boolean oxygenLeakDetected() {
        return this.oxygenLeak;
    }

    public List<BlockPos> runAlgorithm(BlockPos start) {
        run(start);
        return this.positions;
    }

    private void run(BlockPos start) {

        // Cancel if the the amount of oxygen exceeds the limit. This is the case if there was an oxygen leak or the room was too large
        // to support the oxygen.
        if (this.positions.size() >= this.maxBlockChecks) {
            oxygenLeak = true;
            return;
        }

        // Don't have oxygen above the world height limit.
        if (start.getY() > this.world.getHeight()) {
            oxygenLeak = true;
            return;
        }

        BlockState state = this.world.getBlockState(start);

        // Cancel for solid blocks but still let things like slabs, torches and ladders through.
        if (state.isFullCube(this.world, start)) {
            return;
        }

        // Remove Duplicates.
        if (this.positions.contains(start)) {
            return;
        }
        
        // At this point, this oxygen block has past the filters and is allowed to be placed in the current position.
        this.positions.add(start);
        
        // Allow oxygen to stay in closed doors but exit when they are open.
        if (this.checkDoor(state)) {
            return;
        }
        
        // Prevent oxygen from escaping from glass panes.
        if (state.getBlock() instanceof HorizontalConnectingBlock) {
            if (!state.isOpaque()) {
                return;
            }
        }

        // A custom direction order with Direction.UP first to ensure that any leaking oxygen will always travel into the sky.
        for (Direction dir : new Direction[] { Direction.UP, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.DOWN }) {
            BlockPos offsetPos = start.offset(dir);
            // Rinse and repeat until the entire room is filled or it has reached the limit.
            // Maybe change this to a while loop? Potential stack overflow.
            run(offsetPos);
        }
    }

    // Lets oxygen pass through doors when they are open.
    private boolean checkDoor(BlockState state) {
        return state.contains(DoorBlock.OPEN) && !state.get(DoorBlock.OPEN) || state.contains(TrapdoorBlock.OPEN) && !state.get(TrapdoorBlock.OPEN);
    }
}