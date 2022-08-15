package com.github.alexnijjar.ad_astra.blocks.cables;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.EnergyStorageUtil;

import java.util.*;

public interface InteractablePipe<T> {
    boolean supportsAutoExtract();
    boolean canTakeFrom(T source);
    boolean canInsertInto(T consumer);
    boolean canConnectTo(BlockEntity next);
    int insertInto(T consumer, int leftover);
    T getInteraction(World world, BlockPos pos, Direction direction);
    @Nullable T getSource();
    void setSource(T source);
    void clearSource();
    List<T> getConsumers();
    World getWorld();
    BlockPos getPos();
    int getWorkTime();
    int getTransferAmount();
    default void handleSourceFinding() {

    }

    default void pipeTick() {
        if (!getWorld().isClient()) {
            if (getWorld().getTime() % getWorkTime() == 0) {
                clearSource();
                getConsumers().clear();
                Set<BlockPos> visitedNodes = new HashSet<>();
                Set<BlockPos> availableNodes = new HashSet<>();
                if(supportsAutoExtract()) {
                    availableNodes.add(this.getPos());
                    for (int i = 0; i < 6; i++) {
                        Direction direction = Direction.byId(i);
                        BlockPos offset = this.getPos().offset(direction);
                        T potentialSource = getInteraction(getWorld(), offset, direction);
                        if (potentialSource != null && canTakeFrom(potentialSource)) {
                            setSource(potentialSource);
                            break;
                        }
                    }
                } else {
                    handleSourceFinding();
                }
                if (getSource() != null) {
                    while(!availableNodes.isEmpty()) {
                        List<BlockPos> temporaryOpenNodes = new ArrayList<>();
                        Iterator<BlockPos> iterator = availableNodes.iterator();
                        while (iterator.hasNext()) {
                            BlockPos node = iterator.next();
                            BlockEntity current = getWorld().getBlockEntity(node);
                            for (int i = 0; i < 6; i++) {
                                Direction direction = Direction.byId(i);
                                BlockPos offset = node.offset(direction);
                                BlockEntity entity = getWorld().getBlockEntity(offset);
                                if (!visitedNodes.contains(offset) && entity instanceof InteractablePipe<?> pipe) {
                                    //Additional if statement to optimize performance; If it's a pipe but cant connect, it shouldn't check if it is a consumer (because it's already a pipe)
                                    if(pipe.canConnectTo(current)) {
                                        temporaryOpenNodes.add(offset);
                                    }
                                } else {
                                    T potentialConsumer = getInteraction(getWorld(), offset, direction);
                                    if (potentialConsumer != null && this.canInsertInto(potentialConsumer)) {
                                        getConsumers().add(potentialConsumer);
                                    }
                                }
                            }
                            iterator.remove();
                            visitedNodes.add(node);
                        }
                        availableNodes.addAll(temporaryOpenNodes);
                    }
                }
            } else if (getSource() != null && !getConsumers().isEmpty()) {
                int leftover = getTransferAmount();
                for (T consumer : getConsumers()) {
                    leftover = insertInto(consumer, leftover);
                }
            }
        }
    }
}
