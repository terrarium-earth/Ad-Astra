package earth.terrarium.adastra.common.blockentities.pipes;

import earth.terrarium.adastra.common.blocks.pipes.PipeBlock;
import earth.terrarium.adastra.common.blocks.pipes.TransferablePipe;
import earth.terrarium.adastra.common.blocks.properties.PipeProperty;
import it.unimi.dsi.fastutil.longs.LongArrayFIFOQueue;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface Pipe {

    /**
     * Performs a breadth-first search to find all connected nodes.
     *
     * @param level    The level to search in.
     * @param startPos The position to start the search from.
     */
    default void findNodes(ServerLevel level, BlockPos startPos) {
        LongSet visitedNodes = new LongOpenHashSet();
        LongArrayFIFOQueue queue = new LongArrayFIFOQueue();
        queue.enqueue(startPos.asLong());

        while (!queue.isEmpty()) {
            long currentPosLong = queue.dequeueLong();
            BlockPos pipePos = BlockPos.of(currentPosLong);
            if (!(level.getBlockEntity(pipePos) instanceof PipeBlockEntity pipeEntity)) continue;
            BlockState pipeState = pipeEntity.getBlockState();

            Direction[] directions = pipeEntity.connectedDirections();
            if (directions == null) continue;
            for (var direction : directions) {
                BlockPos pos = pipePos.relative(direction);
                long longPos = pos.asLong();
                if (!visitedNodes.add(longPos)) continue;

                if (level.getBlockState(pos).getBlock() instanceof TransferablePipe) {
                    queue.enqueue(longPos);
                } else {
                    BlockEntity entity = level.getBlockEntity(pos);
                    if (entity == null || !isValid(entity, direction)) continue;
                    addNode(entity, pipeState.getValue(PipeBlock.DIRECTION_TO_CONNECTION.get(direction)), direction, pos);
                }
            }
        }
    }

    /**
     * Adds a node to the list of sources or consumers.
     *
     * @param entity       The block entity to add.
     * @param pipeProperty The pipe property of the pipe that connects to the node.
     * @param direction    The direction of the pipe that connects to the node.
     * @param pos          The position of the node.
     */
    void addNode(@NotNull BlockEntity entity, PipeProperty pipeProperty, Direction direction, BlockPos pos);

    /**
     * Transfers from the sources to consumers.
     *
     * @param level The level to transfer in.
     */
    default void transfer(ServerLevel level, long transferRate, Map<BlockPos, Direction> sources, Map<BlockPos, Direction> consumers) {
        for (var sourceEntry : sources.entrySet()) {
            var sourceEntity = level.getBlockEntity(sourceEntry.getKey());
            if (sourceEntity == null || !isValid(sourceEntity, sourceEntry.getValue())) return;

            long rate = transferRate / consumers.size();
            for (var consumerEntry : consumers.entrySet()) {
                var pos = consumerEntry.getKey();
                var direction = consumerEntry.getValue();
                var consumerEntity = level.getBlockEntity(pos);
                if (consumerEntity == null || !isValid(consumerEntity, direction)) continue;
                moveContents(rate, sourceEntity, consumerEntity, direction);
            }
        }
    }

    /**
     * Moves the contents from the source to the consumer.
     *
     * @param transferRate The transfer rate.
     * @param source       The source block entity.
     * @param consumer     The consumer block entity.
     * @param direction    The direction of the consumer.
     */
    void moveContents(long transferRate, @NotNull BlockEntity source, @NotNull BlockEntity consumer, Direction direction);

    /**
     * Checks if a block entity is valid for the pipe type.
     *
     * @param entity    The block entity to check.
     * @param direction The direction of the block entity.
     * @return True if the block entity is valid, false otherwise.
     */
    boolean isValid(@NotNull BlockEntity entity, Direction direction);
}
