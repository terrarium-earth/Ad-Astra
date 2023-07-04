package earth.terrarium.adastra.common.utils.floodfill;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public final class FloodFill3D {

    public static final BlockPredicate TEST_SEALED = (level, pos, direction) -> {
        BlockState state = level.getBlockState(pos);
        if (state.isAir()) return true;
        VoxelShape collisionShape = state.getCollisionShape(level, pos);
        if (collisionShape.isEmpty()) return true;
        if (!isSideSolid(collisionShape, direction)) return true;
        return !isFaceSturdy(collisionShape, direction);
    };

    public static Set<BlockPos> run(Level level, BlockPos start, int limit, BlockPredicate predicate) {
        level.getProfiler().push("adastra-floodfill");

        LongOpenHashSet positions = new LongOpenHashSet(limit);
        Queue<Long> queue = new ArrayDeque<>();
        queue.add(start.asLong());

        while (!queue.isEmpty() && positions.size() < limit) {
            long pos = queue.poll();
            BlockPos blockPos = BlockPos.of(pos);
            if (positions.contains(pos)) continue;
            positions.add(pos);

            for (Direction direction : Direction.values()) {
                BlockPos neighbor = blockPos.relative(direction);
                if (!predicate.test(level, neighbor, direction)) continue;
                long neighborPos = neighbor.asLong();
                if (!positions.contains(neighborPos)) {
                    queue.add(neighborPos);
                }
            }
        }

        level.getProfiler().pop();
        return positions.longStream()
            .mapToObj(BlockPos::of)
            .collect(Collectors.toSet());
    }

    private static boolean isSideSolid(VoxelShape collisionShape, Direction dir) {
        return checkBounds(collisionShape.bounds(), dir.getAxis());
    }

    private static boolean isFaceSturdy(VoxelShape collisionShape, Direction dir) {
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

    @FunctionalInterface
    public interface BlockPredicate {
        boolean test(Level level, BlockPos pos, Direction direction);
    }
}