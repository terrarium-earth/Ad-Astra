package com.github.alexnijjar.ad_astra.blocks.pipes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public interface InteractablePipe<T> {

    boolean supportsAutoExtract();

    boolean canTakeFrom(T source);

    boolean canInsertInto(T consumer);

    boolean canConnectTo(BlockEntity next, Direction direction, BlockPos pos);

    void insertInto(T consumer, Direction direction, BlockPos pos);

    T getInteraction(World world, BlockPos pos, Direction direction);

    Node<T> getSource();

    void setSource(Node<T> source);

    void clearSource();

    List<Node<T>> getConsumers();

    World getPipeWorld();

    BlockPos getPipePos();

    int getWorkTime();

    long getTransferAmount();

    default void handleSourceFinding() {

    }

    default void pipeTick() {
        if (!getPipeWorld().isClient()) {
            if (getPipeWorld().getTime() % getWorkTime() == 0) {
                clearSource();
                this.getConsumers().clear();
                Set<BlockPos> visitedNodes = new HashSet<>();
                Set<BlockPos> availableNodes = new HashSet<>();
                if (supportsAutoExtract()) {
                    availableNodes.add(this.getPipePos());
                    for (Direction direction : Direction.values()) {
                        BlockPos offset = this.getPipePos().offset(direction);
                        T potentialSource = getInteraction(getPipeWorld(), offset, direction);
                        if (potentialSource != null && canTakeFrom(potentialSource)) {
                            this.setSource(new Node<>(potentialSource, direction, offset));
                            break;
                        }
                    }
                } else {
                    handleSourceFinding();
                }
                if (getSource() != null) {
                    while (!availableNodes.isEmpty()) {
                        List<BlockPos> temporaryOpenNodes = new ArrayList<>();

                        for (BlockPos node : availableNodes) {
                            BlockEntity current = getPipeWorld().getBlockEntity(node);
                            for (Direction direction : Direction.values()) {
                                BlockPos offset = node.offset(direction);
                                BlockEntity entity = getPipeWorld().getBlockEntity(offset);
                                if (!visitedNodes.contains(offset) && entity instanceof InteractablePipe<?> pipe) {
                                    // Additional if statement to optimize performance; If it's a pipe but cant connect, it shouldn't check if it is a consumer (because it's already a pipe)
                                    if (pipe.canConnectTo(current, direction, offset)) {
                                        temporaryOpenNodes.add(offset);
                                    }
                                } else {
                                    T potentialConsumer = getInteraction(getPipeWorld(), offset, direction);
                                    if (potentialConsumer != null && this.canInsertInto(potentialConsumer)) {
                                        this.getConsumers().add(new Node<>(potentialConsumer, direction, node));
                                    }
                                }
                            }
                            visitedNodes.add(node);
                        }
                        availableNodes.clear();
                        availableNodes.addAll(temporaryOpenNodes);
                    }
                }
            } else if (getSource() != null && !getConsumers().isEmpty()) {
                for (Node<T> consumer : getConsumers()) {
                    insertInto(consumer.storage(), consumer.direction(), consumer.pos());
                }
            }
        }
    }

    record Node<T> (T storage, Direction direction, BlockPos pos) {

    }
}
