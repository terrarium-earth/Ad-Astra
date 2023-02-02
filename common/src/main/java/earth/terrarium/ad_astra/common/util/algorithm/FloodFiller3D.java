package earth.terrarium.ad_astra.common.util.algorithm;

import com.mojang.datafixers.util.Pair;
import earth.terrarium.ad_astra.common.block.slidingdoor.SlidingDoorBlock;
import earth.terrarium.ad_astra.common.registry.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class FloodFiller3D {
    public static Set<BlockPos> run(Level level, BlockPos start) {
        Set<BlockPos> positions = new HashSet<>();
        Set<Pair<BlockPos, Direction>> queue = new LinkedHashSet<>();
        queue.add(Pair.of(start, Direction.UP));

        while (!queue.isEmpty()) {
            if (positions.size() >= 2000) break;

            var iterator = queue.iterator();
            var pair = iterator.next();
            BlockPos pos = pair.getFirst().relative(pair.getSecond());
            iterator.remove();

            BlockState state = level.getBlockState(pos);

            if (runAdditionalChecks(level, state, pos)) {
                continue;
            } else {
                if (state.is(ModTags.Blocks.BLOCKS_FLOOD_FILL)) continue;
                VoxelShape collisionShape = state.getCollisionShape(level, pos);
                if (!state.isAir() && !state.is(ModTags.Blocks.PASSES_FLOOD_FILL) && !collisionShape.isEmpty() && isSideSolid(collisionShape, pair.getSecond(), state) && (isFaceSturdy(collisionShape, pair.getSecond(), state) || isFaceSturdy(collisionShape, pair.getSecond().getOpposite(), state))) {
                    continue;
                }
            }

            positions.add(pos);

            for (Direction dir : Direction.values()) {
                if (!positions.contains(pos.relative(dir))) {
                    queue.add(Pair.of(pos, dir));
                }
            }
        }

        return positions;
    }

    private static boolean runAdditionalChecks(Level level, BlockState state, BlockPos pos) {
        if (state.getBlock() instanceof SlidingDoorBlock door) {
            return !level.getBlockState(door.getMainPos(state, pos)).getValue(SlidingDoorBlock.OPEN);
        }
        return false;
    }

    private static boolean isSideSolid(VoxelShape collisionShape, Direction dir, BlockState state) {
        return checkBounds(collisionShape.bounds(), dir.getAxis());
    }

    private static boolean isFaceSturdy(VoxelShape collisionShape, Direction dir, BlockState state) {
        VoxelShape faceShape = collisionShape.getFaceShape(dir);
        if (faceShape.isEmpty()) return true;
        return checkBounds(faceShape.toAabbs().get(0), dir.getAxis());
    }

    private static boolean checkBounds(AABB bounds, Direction.Axis axis) {
        return switch (axis) {
            case X -> bounds.minY <= 0 && bounds.maxY >= 1 && bounds.minZ <= 0 && bounds.maxZ >= 1;
            case Y -> bounds.minX <= 0 && bounds.maxX >= 1 && bounds.minZ <= 0 && bounds.maxZ >= 1;
            case Z -> bounds.minX <= 0 && bounds.maxX >= 1 && bounds.minY <= 0 && bounds.maxY >= 1;
        };
    }
}
