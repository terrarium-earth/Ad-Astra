package earth.terrarium.ad_astra.util.algorithm;

import earth.terrarium.ad_astra.block.door.SlidingDoorBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A 3D flood fill algorithm that will fill an entire structure with oxygen.
 */
public class OxygenFillerAlgorithm {

    private final Level level;
    private final int maxBlockChecks;

    public OxygenFillerAlgorithm(Level level, int maxBlockChecks) {
        this.level = level;
        this.maxBlockChecks = maxBlockChecks;
    }

    public Set<BlockPos> runAlgorithm(BlockPos start) {

        Set<BlockPos> positions = new HashSet<>();
        Set<BlockPos> queue = new LinkedHashSet<>();
        queue.add(start);
        main:
        while (!queue.isEmpty()) {

            // Cancel if the the amount of oxygen exceeds the limit. This is the case if there was an oxygen leak or the room was too
            // large to support the oxygen
            if (positions.size() >= this.maxBlockChecks) {
                break;
            }

            Iterator<BlockPos> iterator = queue.iterator();
            BlockPos pos = iterator.next();
            iterator.remove();

            // Don't have oxygen above the level height limit
            if (pos.getY() > this.level.getHeight()) {
                break;
            }

            BlockState state = this.level.getBlockState(pos);

            // Cancel for solid blocks but still let things like slabs, torches and ladders through
            if (state.isCollisionShapeFullBlock(this.level, pos)) {
                if (!(state.getBlock() instanceof IceBlock) && !(state.getBlock() instanceof GrassBlock)) {
                    continue;
                }
            }

            positions.add(pos);

            // Prevent oxygen from escaping from glass panes
            if (state.getBlock() instanceof CrossCollisionBlock) {
                if (!state.canOcclude() && !state.getBlock().equals(Blocks.IRON_BARS)) {
                    continue;
                }
            }

            // Make airlocks work
            if (state.getBlock() instanceof SlidingDoorBlock door) {
                BlockState mainState = level.getBlockState(door.getMainPos(state, pos));
                if (mainState.hasProperty(SlidingDoorBlock.OPEN) && !mainState.getValue(SlidingDoorBlock.OPEN)) {
                    continue;
                }
            }

            for (Direction dir : Direction.values()) {
                if (state.isFaceSturdy(level, pos, dir)) {

                    if (state.getBlock() instanceof LadderBlock) {
                        continue;
                    }

                    // Allow oxygen to stay in closed doors but exit when they are open
                    if (this.checkDoor(state)) {
                        continue;
                    }

                    continue main;
                }
            }

            for (Direction dir : Direction.values()) {
                BlockPos offsetPos = pos.relative(dir);
                if (!positions.contains(offsetPos)) {
                    queue.add(offsetPos);
                }
            }
        }
        return positions;
    }

    // Lets oxygen pass through doors when they are open
    private boolean checkDoor(BlockState state) {
        return state.hasProperty(DoorBlock.OPEN) && state.getValue(DoorBlock.OPEN) || state.hasProperty(TrapDoorBlock.OPEN) && state.getValue(TrapDoorBlock.OPEN);
    }
}