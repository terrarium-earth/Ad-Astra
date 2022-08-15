package com.github.alexnijjar.ad_astra.blocks.cables;

import com.github.alexnijjar.ad_astra.registry.ModBlockEntities;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiCache;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.EnergyStorageUtil;
import team.reborn.energy.api.base.SimpleSidedEnergyContainer;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public class CableBlockEntity extends BlockEntity {
    private final Map<Direction, BlockApiCache<EnergyStorage, Direction>> adjacentCaches = new EnumMap<>(Direction.class);
    private final Map<Direction, BlockPos> consumers = new HashMap<>();
    private BlockPos source;
    private Direction sourceDirection;

    public CableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CABLE, pos, state);
    }

    public void tick() {
        if(this.world != null && !this.world.isClient()) {
            if (world.getTime() % 5 == 0) {
                this.source = null;
                this.sourceDirection = null;
                this.consumers.clear();
                Set<BlockPos> visitedNodes = new HashSet<>();
                List<BlockPos> openNodes = new ArrayList<>();
                openNodes.add(this.getPos());
                visitedNodes.add(this.getPos());
                for(Direction direction : Direction.values()) {
                    BlockPos offset = this.getPos().offset(direction);
                    EnergyStorage potentialSource = EnergyStorage.SIDED.find(world, offset, direction);
                    if(potentialSource != null && potentialSource.supportsExtraction()) {
                        source = offset;
                        sourceDirection = direction;
                        break;
                    }
                }
                if(source != null) {
                    while (!openNodes.isEmpty()) {
                        BlockPos openNode = openNodes.get(openNodes.size() - 1);
                        for (Direction direction : Direction.values()) {
                            BlockPos offset = openNode.offset(direction).toImmutable();
                            BlockEntity entity = world.getBlockEntity(offset);
                            if (!visitedNodes.contains(offset) && entity instanceof CableBlockEntity) {
                                openNodes.add(offset);
                            } else {
                                EnergyStorage potentialConsumer = EnergyStorage.SIDED.find(world, offset, direction);
                                if (potentialConsumer != null && potentialConsumer.supportsInsertion()) {
                                    consumers.put(direction, offset);
                                }
                            }
                        }
                        openNodes.remove(openNode);
                        visitedNodes.add(openNode);
                    }
                }
            } else if(source != null && !consumers.isEmpty() && this.getCachedState().getBlock() instanceof CableBlock cableBlock){
                int notTransfered = cableBlock.getTransferRate();
                int transferAmount = cableBlock.getTransferRate() / consumers.size();
                for (var consumer : consumers.entrySet()) {
                    EnergyStorage source = EnergyStorage.SIDED.find(world, this.source, this.sourceDirection);
                    EnergyStorage consumerEnergy = EnergyStorage.SIDED.find(world, consumer.getValue(), consumer.getKey());
                    var leftover = EnergyStorageUtil.move(source, consumerEnergy, Math.min(notTransfered, transferAmount), null);
                    notTransfered -= leftover;
                    if(notTransfered == 0) break;
                }
            }
        }
    }
}