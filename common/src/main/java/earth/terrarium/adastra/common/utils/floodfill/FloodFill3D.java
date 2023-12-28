package earth.terrarium.adastra.common.utils.floodfill;

import earth.terrarium.adastra.common.blocks.SlidingDoorBlock;
import earth.terrarium.adastra.common.tags.ModBlockTags;
import it.unimi.dsi.fastutil.longs.LongArrayFIFOQueue;
import it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public final class FloodFill3D {

    private static final Direction[] DIRECTIONS = Direction.values();

    public static final SolidBlockPredicate TEST_FULL_SEAL = (level, pos, direction) -> {
        BlockState state = level.getBlockState(pos);
        if (state.isAir()) return FillResult.AIR;
        if (state.is(ModBlockTags.PASSES_FLOOD_FILL)) return FillResult.AIR;
        if (state.is(ModBlockTags.BLOCKS_FLOOD_FILL)) return FillResult.CUBE;

        if (state.isCollisionShapeFullBlock(level, pos)) return FillResult.CUBE;
        VoxelShape collisionShape = state.getCollisionShape(level, pos);
        if (state.getBlock() instanceof SlidingDoorBlock block) {
            collisionShape = block.getCollisionShape(state, level, pos, CollisionContext.empty());
        }

        if (collisionShape.isEmpty()) return FillResult.AIR;
        if (!isSideSolid(collisionShape, direction)) return FillResult.AIR;
        return isFaceSturdy(collisionShape, direction) ? FillResult.PARTIAL : FillResult.AIR;
    };

    public static Set<BlockPos> run(Level level, BlockPos start, int limit, SolidBlockPredicate predicate, boolean retainOrder) {
        level.getProfiler().push("adastra-floodfill");

        LongSet positions = retainOrder ? new LongLinkedOpenHashSet(limit) : new LongOpenHashSet(limit);
        LongArrayFIFOQueue queue = new LongArrayFIFOQueue(limit);
        queue.enqueue(start.asLong());

        while (!queue.isEmpty() && positions.size() < limit) {
            long pos = queue.dequeueLong();
            if (positions.contains(pos)) continue;
            positions.add(pos);

            BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos(BlockPos.getX(pos), BlockPos.getY(pos), BlockPos.getZ(pos));
            for (Direction direction : DIRECTIONS) {
                mutable.set(pos);
                mutable.move(direction);

                FillResult result = predicate.test(level, mutable, direction);
                switch (result) {
                    case CUBE -> {continue;}
                    case PARTIAL -> positions.add(mutable.asLong());
                }

                queue.enqueue(mutable.asLong());
            }
        }

        Set<BlockPos> result = retainOrder ? new LinkedHashSet<>(positions.size()) : new HashSet<>(positions.size());
        for (long pos : positions) {
            result.add(BlockPos.of(pos));
        }

        level.getProfiler().pop();
        return result;
    }

    private static boolean isSideSolid(VoxelShape collisionShape, Direction dir) {
        return switch (dir.getAxis()) {
            case X -> {
                double minY = collisionShape.min(Direction.Axis.Y);
                double maxY = collisionShape.max(Direction.Axis.Y);
                double minZ = collisionShape.min(Direction.Axis.Z);
                double maxZ = collisionShape.max(Direction.Axis.Z);
                yield minY <= 0 && maxY >= 1 && minZ <= 0 && maxZ >= 1;
            }
            case Y -> {
                double minX = collisionShape.min(Direction.Axis.X);
                double maxX = collisionShape.max(Direction.Axis.X);
                double minZ = collisionShape.min(Direction.Axis.Z);
                double maxZ = collisionShape.max(Direction.Axis.Z);
                yield minX <= 0 && maxX >= 1 && minZ <= 0 && maxZ >= 1;
            }
            case Z -> {
                double minX = collisionShape.min(Direction.Axis.X);
                double maxX = collisionShape.max(Direction.Axis.X);
                double minY = collisionShape.min(Direction.Axis.Y);
                double maxY = collisionShape.max(Direction.Axis.Y);
                yield minX <= 0 && maxX >= 1 && minY <= 0 && maxY >= 1;
            }
        };
    }

    private static boolean isFaceSturdy(VoxelShape collisionShape, Direction dir) {
        VoxelShape faceShape = collisionShape.getFaceShape(dir);
        if (faceShape.isEmpty()) return true;
        var aabbs = faceShape.toAabbs();
        if (aabbs.isEmpty()) return true;
        return checkBounds(aabbs.get(0), dir.getAxis());
    }

    private static boolean checkBounds(AABB bounds, Direction.Axis axis) {
        return switch (axis) {
            case X -> bounds.minY <= 0 && bounds.maxY >= 1 && bounds.minZ <= 0 && bounds.maxZ >= 1;
            case Y -> bounds.minX <= 0 && bounds.maxX >= 1 && bounds.minZ <= 0 && bounds.maxZ >= 1;
            case Z -> bounds.minX <= 0 && bounds.maxX >= 1 && bounds.minY <= 0 && bounds.maxY >= 1;
        };
    }

    @FunctionalInterface
    public interface SolidBlockPredicate {
        FillResult test(Level level, BlockPos pos, Direction direction);
    }

    public enum FillResult {
        CUBE,
        PARTIAL,
        AIR
    }
}